package com.kartoflane.spiresim.state.effect;

import com.kartoflane.spiresim.template.effect.FrailEffectTemplate;

public class FrailEffectState extends TimedEffectState {

    private double modifier;


    public FrailEffectState(FrailEffectTemplate template) {
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
