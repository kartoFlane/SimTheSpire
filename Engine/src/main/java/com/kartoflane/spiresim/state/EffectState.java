package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.template.EffectIdentifier;
import com.kartoflane.spiresim.template.EffectTemplate;

/**
 * A state class representing an effect (a buff or debuff) that can be applied to an entity.
 */
public abstract class EffectState extends TemplatableState {

    private String name;
    private int stackCounter;


    public EffectState(EffectTemplate<? extends EffectState> template) {
        super(template);
    }

    public int getStackCounter() {
        return stackCounter;
    }

    public void setStackCounter(int stackCounter) {
        this.stackCounter = stackCounter;
    }

    public abstract EffectIdentifier getEffectIdentifier();
}
