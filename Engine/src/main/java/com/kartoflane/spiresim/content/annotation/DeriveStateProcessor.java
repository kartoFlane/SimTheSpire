package com.kartoflane.spiresim.content.annotation;

import com.kartoflane.spiresim.content.StateTypeFactory;
import com.kartoflane.spiresim.template.StateTemplate;
import com.kartoflane.spiresim.util.ExceptionUtils;
import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("com.kartoflane.spiresim.content.annotation.DeriveState")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DeriveStateProcessor extends AbstractProcessor {

    private Types types;
    private Elements elements;
    private TypeMirror stateTemplateType;

    private StateTypeFactory stateTypeFactory;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        types = processingEnv.getTypeUtils();
        elements = processingEnv.getElementUtils();

        stateTemplateType = types.erasure(getTypeElement(StateTemplate.class).asType());

        stateTypeFactory = new StateTypeFactory(types, elements);
    }

    private TypeElement getTypeElement(Class<?> clazz) {
        return elements.getTypeElement(clazz.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(DeriveState.class);

        for (Element annotatedElement : annotatedElements) {
            processAnnotation((TypeElement) annotatedElement);
        }

        return false;
    }

    private void processAnnotation(TypeElement annotatedElement) {
        try {
            if (isTemplateType(annotatedElement)) {
                processCorrectAnnotation(annotatedElement);
            } else {
                processErroneousAnnotation(annotatedElement);
            }
        } catch (Exception e) {
            processException(annotatedElement, e);
        }
    }

    private boolean isTemplateType(TypeElement annotatedElement) {
        return types.isAssignable(annotatedElement.asType(), stateTemplateType);
    }

    private void processCorrectAnnotation(TypeElement annotatedElement) throws IOException {
        DeriveState deriveState = annotatedElement.getAnnotation(DeriveState.class);
        JavaFile javaFile = stateTypeFactory.deriveState(annotatedElement);
        javaFile.writeTo(processingEnv.getFiler());
    }

    private void processErroneousAnnotation(TypeElement annotatedElement) {
        processingEnv.getMessager().printMessage(
                Diagnostic.Kind.ERROR,
                "This element does not implement interface " + StateTemplate.class.getCanonicalName(),
                annotatedElement
        );
    }

    private void processException(Element element, Exception exception) {
        String message = String.format(
                "Exception while processing this element: %s\nStack trace:\n%s",
                exception.getMessage(),
                ExceptionUtils.getStackTrace(exception)
        );

        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
