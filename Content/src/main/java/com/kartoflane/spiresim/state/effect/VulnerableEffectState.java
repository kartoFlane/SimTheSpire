package com.kartoflane.spiresim.state.effect;

import com.kartoflane.spiresim.template.effect.VulnerableEffectTemplate;

public class VulnerableEffectState extends TimedEffectState {

    private double modifier;


    public VulnerableEffectState(VulnerableEffectTemplate template) {
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