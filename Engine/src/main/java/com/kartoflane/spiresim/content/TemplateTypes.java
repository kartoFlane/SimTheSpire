package com.kartoflane.spiresim.content;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.TemplatableState;
import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.state.effect.StackingEffectState;
import com.kartoflane.spiresim.template.StateTemplate;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.effect.EffectTemplate;
import com.kartoflane.spiresim.template.effect.StackingEffectTemplate;
import com.kartoflane.spiresim.template.effect.TimedEffectTemplate;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

enum TemplateTypes {
    CARD(CardState.class, CardTemplate.class),
    EFFECT_TIMED(StackingEffectState.class, TimedEffectTemplate.class),
    EFFECT_STACKING(StackingEffectState.class, StackingEffectTemplate.class),
    EFFECT(EffectState.class, EffectTemplate.class),
    ENTITY(EntityState.class, EntityTemplate.class);

    private Class<? extends TemplatableState> stateClass;
    private Class<? extends StateTemplate> templateClass;


    TemplateTypes(Class<? extends TemplatableState> stateClass, Class<? extends StateTemplate> templateClass) {
        this.stateClass = stateClass;
        this.templateClass = templateClass;
    }

    public Class<? extends TemplatableState> getStateClass() {
        return this.stateClass;
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
