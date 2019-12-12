package com.kartoflane.spiresim.state.effect;

import com.kartoflane.spiresim.template.effect.TimedEffectTemplate;

/**
 * A state class representing a temporary effect (a buff or debuff) that can be applied to an entity,
 * that decays each turn by one stack.
 */
public abstract class TimedEffectState extends EffectState {

    private int stacks = 0;


    public TimedEffectState(TimedEffectTemplate<? extends EffectState> template) {
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
