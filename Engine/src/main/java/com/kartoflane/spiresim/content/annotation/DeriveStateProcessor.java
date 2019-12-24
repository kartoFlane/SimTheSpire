package com.kartoflane.spiresim.content.annotation;

import com.kartoflane.spiresim.content.StateTypeFactory;
import com.kartoflane.spiresim.util.ExceptionUtils;
import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("com.kartoflane.spiresim.content.annotation.DeriveState")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DeriveStateProcessor extends AbstractProcessor {

    private StateTypeFactory stateTypeFactory;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        stateTypeFactory = new StateTypeFactory(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(DeriveState.class);

        for (Element annotatedElement : annotatedElements) {
            processAnnotatedElement((TypeElement) annotatedElement);
        }

        return false;
    }

    private void processAnnotatedElement(TypeElement annotatedElement) {
        try {
            processCorrectElement(annotatedElement);
        } catch (Exception e) {
            processException(annotatedElement, e);
        }
    }

    private void processCorrectElement(TypeElement annotatedElement) throws IOException {
        JavaFile javaFile = stateTypeFactory.deriveState(annotatedElement);
        javaFile.writeTo(processingEnv.getFiler());
    }

    private void processException(Element element, Exception exception) {
        String message = String.format(
                "Exception while processing this element.\nMessage: %s\nStack trace:\n%s",
                exception.getMessage(),
                ExceptionUtils.getStackTrace(exception)
        );

        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
