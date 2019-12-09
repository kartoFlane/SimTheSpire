package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectTemplate;

/**
 * A state class representing an effect (a buff or debuff) that can be applied to an entity.
 */
public abstract class EffectState extends TemplatableState {

    private String name;
    private int stackCounter;


    public EffectState(EffectTemplate<? extends EffectState> template) {
        super(template);

        this.setName(template.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStackCounter() {
        return stackCounter;
    }

    public void setStackCounter(int stackCounter) {
        this.stackCounter = stackCounter;
    }

    public abstract EffectIdentifier getEffectIdentifier();
}
