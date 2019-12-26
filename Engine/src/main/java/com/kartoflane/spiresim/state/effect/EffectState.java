package com.kartoflane.spiresim.state.effect;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.TemplatableState;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectTemplate;

/**
 * A state class representing a permanent effect (a buff or debuff) that can be applied to an entity.
 */
public abstract class EffectState extends TemplatableState {

    private String name;


    protected EffectState(GameController gameController, EffectTemplate<? extends EffectState> template) {
        super(template);

        this.setName(template.getName());
    }

    @Override
    public EffectTemplate<? extends TemplatableState> getTemplate() {
        return (EffectTemplate<?>) super.getTemplate();
    }

    public EffectIdentifier getEffectIdentifier() {
        return getTemplate().getEffectIdentifier();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
