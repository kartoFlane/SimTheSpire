package com.kartoflane.spiresim.content;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Map;

class TypeMirrorHelper {
    private final Types types;
    private final Elements elements;

    private final Map<Object, TypeMirror> typeMirrorMap;


    TypeMirrorHelper(ProcessingEnvironment processingEnvironment) {
        this.types = processingEnvironment.getTypeUtils();
        this.elements = processingEnvironment.getElementUtils();
        this.typeMirrorMap = new HashMap<>();
    }

    public Element asElement(TypeMirror typeMirror) {
        return this.types.asElement(typeMirror);
    }

    public TypeMirror getErasure(Class<?> clazz) {
        TypeMirror typeMirror = getTypeMirror(clazz);
        return types.erasure(typeMirror);
    }

    public TypeMirror getTypeMirror(Class<?> clazz) {
        TypeMirror typeMirror = typeMirrorMap.get(clazz);
        if (typeMirror == null) {
            typeMirror = elements.getTypeElement(clazz.getCanonicalName()).asType();
            typeMirrorMap.put(clazz, typeMirror);
        }

        return typeMirror;
    }

    public TypeMirror getTypeMirror(TypeKind typeKind) {
        TypeMirror typeMirror = typeMirrorMap.get(typeKind);
        if (typeMirror == null) {
            switch (typeKind) {
                case NULL:
                    typeMirror = types.getNullType();
                    break;
                case NONE:
                case VOID:
                    typeMirror = types.getNoType(typeKind);
                    break;
                case INT:
                case BYTE:
                case CHAR:
                case LONG:
                case FLOAT:
                case DOUBLE:
                case SHORT:
                case BOOLEAN:
                    typeMirror = types.getPrimitiveType(typeKind);
                    break;
                default:
                    throw new IllegalArgumentException("No case defined for TypeKind " + typeKind);
            }

            typeMirrorMap.put(typeKind, typeMirror);
        }

        return typeMirror;
    }

    public boolean isAssignable(TypeMirror from, TypeMirror to) {
        return types.isAssignable(from, to);
    }
}
