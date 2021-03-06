package com.kartoflane.spiresim.content.template.effect.common;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.common.StrengthEffectState;
import com.kartoflane.spiresim.content.template.effect.base.StackingEffectTemplate;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;

@DeriveState
public class StrengthEffectTemplate extends StackingEffectTemplate<StrengthEffectState> {

    @Override
    public Class<StrengthEffectState> getStateType() {
        return StrengthEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return CommonEffectIdentifiers.OUTGOING_DAMAGE_INCREASE_FLAT;
    }

    @Override
    public String getName() {
        return "Strength";
    }

    @Override
    public void preprocessCombatValue(
            GameController gameController,
            EntityController target,
            StrengthEffectState effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
        if (updateEvent.isEqual(MutableCombatValueEvents.ENTITY_OUTGOING_DAMAGE_ATTACK_FLAT)) {
            mutableCombatValue.setAmount_Add(effectState.getStacks());
        }
    }
}
