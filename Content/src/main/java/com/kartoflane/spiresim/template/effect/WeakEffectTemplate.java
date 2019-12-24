package com.kartoflane.spiresim.template.effect;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.WeakEffectState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.MutableCombatValue;

@DeriveState
public class WeakEffectTemplate extends TimedEffectTemplate<WeakEffectState> {

    private static WeakEffectTemplate INSTANCE;


    public static WeakEffectTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WeakEffectTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<WeakEffectState> getStateType() {
        return WeakEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return EffectIdentifier.EffectIdentifiers.DAMAGE_DEALT_DECREASE;
    }

    @Override
    public String getName() {
        return "Weak";
    }

    public double getModifier() {
        return 0.5;
    }

    @Override
    public void onApply(EncounterController encounterController, EntityController target, WeakEffectState effectState, WeakEffectState newInstance) {
        super.onApply(encounterController, target, effectState, newInstance);
    }

    @Override
    public void onRemove(EncounterController encounterController, EntityController target, WeakEffectState effectState) {
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, WeakEffectState effectState, EffectUpdateEvent updateEvent) {
        super.onUpdate(encounterController, target, effectState, updateEvent);
    }

    @Override
    public void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            WeakEffectState effectState,
            MutableCombatValue mutableCombatValue,
            EffectUpdateEvent updateEvent
    ) {
        if (updateEvent.equals(EffectUpdateEvent.StandardEffectUpdateEvents.ENTITY_OUTGOING_DAMAGE)) {
            mutableCombatValue.setAmount_Multiply(effectState.getModifier());
        }
    }
}
