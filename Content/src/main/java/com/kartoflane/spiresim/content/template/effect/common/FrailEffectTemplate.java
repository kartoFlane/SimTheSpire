package com.kartoflane.spiresim.content.template.effect.common;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.common.FrailEffectState;
import com.kartoflane.spiresim.content.template.effect.base.TimedEffectTemplate;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;


@DeriveState
public class FrailEffectTemplate extends TimedEffectTemplate<FrailEffectState> {

    @Override
    public Class<FrailEffectState> getStateType() {
        return FrailEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return CommonEffectIdentifiers.INCOMING_ARMOR_DECREASE_PERCENT;
    }

    @Override
    public String getName() {
        return "Frail";
    }

    public double getModifier() {
        return 0.5;
    }

    @Override
    public void preprocessCombatValue(
            GameController gameController,
            EntityController target,
            FrailEffectState effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
        if (updateEvent.isEqual(MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_PERCENT)) {
            mutableCombatValue.setAmount_Multiply(effectState.getModifier());
        }
    }
}
