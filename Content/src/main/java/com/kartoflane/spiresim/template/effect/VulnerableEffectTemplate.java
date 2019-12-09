package com.kartoflane.spiresim.template.effect;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.MutableCombatValue;
import com.kartoflane.spiresim.state.effect.VulnerableEffectState;

public class VulnerableEffectTemplate implements EffectTemplate<VulnerableEffectState> {

    private static VulnerableEffectTemplate INSTANCE;


    public static VulnerableEffectTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VulnerableEffectTemplate();
        }

        return INSTANCE;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return EffectIdentifier.EffectIdentifiers.DAMAGE_RECEIVED_INCREASE;
    }

    @Override
    public String getName() {
        return "Vulnerable";
    }

    @Override
    public Class<VulnerableEffectState> getStateType() {
        return VulnerableEffectState.class;
    }

    @Override
    public void onApply(EncounterController encounterController, EntityController target, VulnerableEffectState effectState) {
    }

    @Override
    public void onRemove(EncounterController encounterController, EntityController target, VulnerableEffectState effectState) {
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, VulnerableEffectState effectState, EffectUpdateEvent updateEvent) {
    }

    @Override
    public void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            VulnerableEffectState effectState,
            MutableCombatValue mutableCombatValue,
            EffectUpdateEvent updateEvent
    ) {
        if (updateEvent.equals(EffectUpdateEvent.StandardEffectUpdateEvents.ENTITY_INCOMING_DAMAGE)) {
            mutableCombatValue.setAmount((int) (mutableCombatValue.getAmount() * 1.5));
        }
    }
}
