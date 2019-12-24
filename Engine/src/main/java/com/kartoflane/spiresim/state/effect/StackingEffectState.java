package com.kartoflane.spiresim.state.effect;

import com.kartoflane.spiresim.template.effect.EffectTemplate;

/**
 * A state class representing a stackable effect (a buff or debuff) that can be applied to an entity.
 * Multiple applications of the same effect wil increase the number of stacks, amplifying the effect.
 */
public abstract class StackingEffectState extends EffectState {

    private int stacks = 0;


    public StackingEffectState(EffectTemplate<? extends EffectState> template) {
        super(template);

        this.setName(template.getName());
    }

    public int getStacks() {
        return stacks;
    }

    public void setStacks(int stacks) {
        this.stacks = stacks;
    }
}
