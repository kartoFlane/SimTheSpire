package com.kartoflane.spiresim.content;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.TemplatableState;
import com.kartoflane.spiresim.template.StateTemplate;
import com.kartoflane.spiresim.util.StringUtils;
import com.squareup.javapoet.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.stream.Collectors;

public class StateTypeFactory {
    public static final String BASE_OUTPUT_PACKAGE = "com.kartoflane.spiresim.content.state";
    public static final String STATE_CLASS_NAME_SUFFIX = "State";

    private static final String CONSTRUCTOR_GAME_PARAMETER_NAME = "gameController";
    private static final String CONSTRUCTOR_TEMPLATE_PARAMETER_NAME = "template";

    private final TypeMirrorHelper typeMirrorHelper;


    public StateTypeFactory(ProcessingEnvironment processingEnvironment) {
        this.typeMirrorHelper = new TypeMirrorHelper(processingEnvironment);
    }

    public JavaFile deriveState(TypeElement templateElement) {
        validate(templateElement);

        TypeSpec.Builder typeSpecBuilder = setupTypeSpec(templateElement);
        processMethods(typeSpecBuilder, templateElement);

        return JavaFile.builder(getPackagePath(templateElement), typeSpecBuilder.build()).build();
    }

    private void validate(TypeElement templateElement) {
        if (!TemplateTypes.isTemplateType(typeMirrorHelper, templateElement)) {
            throw new IllegalArgumentException("This element does not implement interface " + StateTemplate.class.getCanonicalName());
        }
    }

    private TypeSpec.Builder setupTypeSpec(TypeElement templateElement) {
        TypeSpec.Builder builder = prepareTypeSpec(templateElement);
        return addDerivedSuperclass(builder, templateElement);
    }

    private TypeSpec.Builder prepareTypeSpec(TypeElement templateElement) {
        final String commonName = templateElement.getSimpleName().toString().replace("Template", "");
        final TypeName templateTypeName = ClassName.get(templateElement);

        return TypeSpec.classBuilder(commonName + STATE_CLASS_NAME_SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("getTemplate")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(templateTypeName)
                        .addStatement("return ($T)super.getTemplate()", templateTypeName)
                        .build()
                );
    }

    private TypeSpec.Builder addDerivedSuperclass(TypeSpec.Builder builder, TypeElement templateElement) {
        TypeName superTypeName = deriveSuperclass(templateElement);
        return builder.superclass(superTypeName);
    }

    private TypeName deriveSuperclass(TypeElement templateElement) {
        TypeElement superTypeElement = (TypeElement) typeMirrorHelper.asElement(templateElement.getSuperclass());
        List<? extends TypeParameterElement> typeParameters = superTypeElement.getTypeParameters();

        TypeMirror templatableStateMirror = typeMirrorHelper.getTypeMirror(TemplatableState.class);
        for (TypeParameterElement typeParameter : typeParameters) {
            for (TypeMirror boundsType : typeParameter.getBounds()) {
                if (typeMirrorHelper.isAssignable(boundsType, templatableStateMirror)) {
                    if (boundsType.getKind() != TypeKind.DECLARED) {
                        // Generated state class, need to provide path manually
                        return ClassName.get(getPackagePath(superTypeElement), boundsType.toString());
                    } else {
                        // Predefined state class, resolved automatically
                        return ClassName.get(boundsType);
                    }
                }
            }
        }

        throw new IllegalArgumentException("This template's type parameters do not match the expected bounds.");
    }

    private void processMethods(TypeSpec.Builder builder, TypeElement templateElement) {
        final TypeName templateTypeName = ClassName.get(templateElement);
        MethodSpec.Builder constructorBuilder = setupConstructor(templateTypeName);

        List<ExecutableElement> customAttributeMethods = getCustomAttributeMethods(templateElement);
        for (ExecutableElement methodElement : customAttributeMethods) {
            processCustomAttributeMethod(builder, constructorBuilder, methodElement);
        }

        builder.addMethod(constructorBuilder.build());
    }

    private MethodSpec.Builder setupConstructor(TypeName templateTypeName) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(GameController.class, CONSTRUCTOR_GAME_PARAMETER_NAME)
                .addParameter(templateTypeName, CONSTRUCTOR_TEMPLATE_PARAMETER_NAME)
                .addStatement("super($L, $L)", CONSTRUCTOR_GAME_PARAMETER_NAME, CONSTRUCTOR_TEMPLATE_PARAMETER_NAME);
    }

    /**
     * @return a list of methods from the specified element that: <br/>
     * - have no annotations <br/>
     * - take no arguments or take a single GameController argument <br/>
     * - return something other than void or the template object
     * - have a name starting with 'get'
     */
    private List<ExecutableElement> getCustomAttributeMethods(TypeElement templateElement) {
        return templateElement.getEnclosedElements().stream()
                .filter(enclosedElement -> enclosedElement.getKind() == ElementKind.METHOD)
                .map(enclosedElement -> (ExecutableElement) enclosedElement)
                .filter(methodElement -> isValidCustomAttributeMethod(templateElement, methodElement))
                .collect(Collectors.toList());
    }

    private boolean isValidCustomAttributeMethod(TypeElement templateElement, ExecutableElement methodElement) {
        if (methodElement.getAnnotationMirrors().size() > 0) {
            return false;
        }
        if (!methodElement.getSimpleName().toString().startsWith("get")) {
            return false;
        }
        if (!hasNoParametersOrGameControllerParameter(methodElement)) {
            return false;
        }

        final TypeMirror voidType = typeMirrorHelper.getTypeMirror(TypeKind.VOID);
        if (typeMirrorHelper.isAssignable(methodElement.getReturnType(), voidType)) {
            return false;
        }
        if (typeMirrorHelper.isAssignable(templateElement.asType(), methodElement.getReturnType())) {
            return false;
        }

        return true;
    }

    private boolean hasNoParametersOrGameControllerParameter(ExecutableElement methodElement) {
        List<? extends VariableElement> parameters = methodElement.getParameters();
        return parameters.size() == 0 ||
                (parameters.size() == 1 && typeMirrorHelper.isAssignable(parameters.get(0).asType(), typeMirrorHelper.getTypeMirror(GameController.class)));
    }

    private void processCustomAttributeMethod(TypeSpec.Builder typeBuilder, MethodSpec.Builder constructorBuilder, ExecutableElement methodElement) {
        final TypeName attributeTypeName = ClassName.get(methodElement.getReturnType());
        final String attributeName = methodElement.getSimpleName().toString().replaceFirst("get", "");

        final FieldSpec field = FieldSpec.builder(attributeTypeName, StringUtils.lowercaseFirstLetter(attributeName))
                .addModifiers(Modifier.PRIVATE)
                .build();
        typeBuilder.addField(field);

        final MethodSpec getter = MethodSpec.methodBuilder("get" + attributeName)
                .addModifiers(Modifier.PUBLIC)
                .returns(attributeTypeName)
                .addStatement("return this.$N", field)
                .build();
        typeBuilder.addMethod(getter);

        final MethodSpec setter = MethodSpec.methodBuilder("set" + attributeName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(attributeTypeName, field.name)
                .addStatement("this.$N = $N", field, field)
                .build();
        typeBuilder.addMethod(setter);

        if (methodElement.getParameters().size() == 0) {
            constructorBuilder.addStatement("$N($L.$N())", setter, CONSTRUCTOR_TEMPLATE_PARAMETER_NAME, getter);
        } else {
            constructorBuilder.addStatement("$N($L.$N($L))", setter, CONSTRUCTOR_TEMPLATE_PARAMETER_NAME, getter, CONSTRUCTOR_GAME_PARAMETER_NAME);
        }
    }

    private String getPackagePath(TypeElement templateElement) {
        TemplateTypes templateType = TemplateTypes.valueOf(typeMirrorHelper, templateElement);
        String packagePath = BASE_OUTPUT_PACKAGE + "." + templateType.getPackageName();

        String subPackages = templateElement.getQualifiedName().toString()
                .replaceAll("\\.[^.]+$", "")
                .replaceAll("^.+?\\." + templateType.getPackageName(), "");
        packagePath += subPackages;

        return packagePath;
    }
}
