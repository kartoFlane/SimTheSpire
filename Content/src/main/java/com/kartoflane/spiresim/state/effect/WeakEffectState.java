package com.kartoflane.spiresim.state.effect;

import com.kartoflane.spiresim.template.effect.WeakEffectTemplate;

public class WeakEffectState extends TimedEffectState {

    private double modifier;


    public WeakEffectState(WeakEffectTemplate template) {
        super(template);

        this.setModifier(template.getModifier());
    }

    public double getModifier() {
        return modifier;
    }

    public void setModifier(double modifier) {
        this.modifier = modifier;
    }
}
