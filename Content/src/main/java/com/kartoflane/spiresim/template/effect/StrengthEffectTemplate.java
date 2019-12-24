package com.kartoflane.spiresim.template.effect;

import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.StrengthEffectState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.combat.MutableCombatValue;

@DeriveState
public class StrengthEffectTemplate extends StackingEffectTemplate<StrengthEffectState> {
    private static StrengthEffectTemplate INSTANCE;


    public static StrengthEffectTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StrengthEffectTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<StrengthEffectState> getStateType() {
        return StrengthEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return EffectIdentifiers.OUTGOING_DAMAGE_INCREASE_FLAT;
    }

    @Override
    public String getName() {
        return "Strength";
    }

    @Override
    public void onApply(EncounterController encounterController, EntityController target, StrengthEffectState effectState, StrengthEffectState newInstance) {
    }

    @Override
    public void onRemove(EncounterController encounterController, EntityController target, StrengthEffectState effectState) {
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, StrengthEffectState effectState, EffectUpdateEvent updateEvent) {
    }

    @Override
    public void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            StrengthEffectState effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
        if (updateEvent.isEqual(StandardEffectUpdateEvents.ENTITY_OUTGOING_DAMAGE)) {
            mutableCombatValue.setAmount_Add(effectState.getStacks());
        }
    }
}
