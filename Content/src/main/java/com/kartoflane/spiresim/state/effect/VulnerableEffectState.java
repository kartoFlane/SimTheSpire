package com.kartoflane.spiresim.state.effect;

import com.kartoflane.spiresim.state.EffectState;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.VulnerableEffectTemplate;

public class VulnerableEffectState extends EffectState {

    public VulnerableEffectState(VulnerableEffectTemplate template) {
        super(template);
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return EffectIdentifier.EffectIdentifiers.DAMAGE_RECEIVED_INCREASE;
    }
}
