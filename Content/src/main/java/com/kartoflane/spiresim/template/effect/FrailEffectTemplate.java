package com.kartoflane.spiresim.template.effect;

import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.FrailEffectState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.combat.MutableCombatValue;


@DeriveState
public class FrailEffectTemplate extends TimedEffectTemplate<FrailEffectState> {

    private static FrailEffectTemplate INSTANCE;


    public static FrailEffectTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FrailEffectTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<FrailEffectState> getStateType() {
        return FrailEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return EffectIdentifiers.INCOMING_ARMOR_DECREASE_PERCENT;
    }

    @Override
    public String getName() {
        return "Frail";
    }

    public double getModifier() {
        return 0.5;
    }

    @Override
    public void onApply(EncounterController encounterController, EntityController target, FrailEffectState effectState, FrailEffectState newInstance) {
        super.onApply(encounterController, target, effectState, newInstance);
    }

    @Override
    public void onRemove(EncounterController encounterController, EntityController target, FrailEffectState effectState) {
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, FrailEffectState effectState, EffectUpdateEvent updateEvent) {
        super.onUpdate(encounterController, target, effectState, updateEvent);
    }

    @Override
    public void preprocessCombatValue(
            EncounterController encounterController,
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
