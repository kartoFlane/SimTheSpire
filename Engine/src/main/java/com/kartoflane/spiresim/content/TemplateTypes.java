package com.kartoflane.spiresim.content;

import com.kartoflane.spiresim.template.StateTemplate;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.effect.EffectTemplate;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

enum TemplateTypes {
    CARD(CardTemplate.class, "card"),
    EFFECT(EffectTemplate.class, "effect"),
    ENTITY(EntityTemplate.class, "entity");


    private Class<? extends StateTemplate> templateClass;
    private String packageName;


    TemplateTypes(Class<? extends StateTemplate> templateClass, String packageName) {
        this.templateClass = templateClass;
        this.packageName = packageName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public boolean isAssignableFrom(TypeMirrorHelper helper, TypeElement templateElement) {
        TypeMirror erasure = helper.getErasure(templateClass);
        return helper.isAssignable(templateElement.asType(), erasure);
    }

    public static boolean isTemplateType(TypeMirrorHelper helper, TypeElement templateElement) {
        TypeMirror erasure = helper.getErasure(StateTemplate.class);
        return helper.isAssignable(templateElement.asType(), erasure);
    }

    public static TemplateTypes valueOf(TypeMirrorHelper helper, TypeElement templateElement) {
        for (TemplateTypes value : values()) {
            if (value.isAssignableFrom(helper, templateElement)) {
                return value;
            }
        }

        throw new IllegalArgumentException("TypeElement did not match any known TemplateTypes: " + templateElement);
    }
}
