package com.kartoflane.spiresim.content.template.effect.common;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.common.WeakEffectState;
import com.kartoflane.spiresim.content.template.effect.base.TimedEffectTemplate;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;

@DeriveState
public class WeakEffectTemplate extends TimedEffectTemplate<WeakEffectState> {

    @Override
    public Class<WeakEffectState> getStateType() {
        return WeakEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return CommonEffectIdentifiers.OUTGOING_DAMAGE_DECREASE_PERCENT;
    }

    @Override
    public String getName() {
        return "Weak";
    }

    public double getModifier() {
        return 0.5;
    }

    @Override
    public void preprocessCombatValue(
            GameController gameController,
            EntityController target,
            WeakEffectState effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
        if (updateEvent.isEqual(MutableCombatValueEvents.ENTITY_OUTGOING_DAMAGE_ATTACK_PERCENT)) {
            mutableCombatValue.setAmount_Multiply(effectState.getModifier());
        }
    }
}
