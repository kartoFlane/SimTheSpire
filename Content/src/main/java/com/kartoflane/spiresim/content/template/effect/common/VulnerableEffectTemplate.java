package com.kartoflane.spiresim.content.template.effect.common;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.common.VulnerableEffectState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectUpdateEvent;
import com.kartoflane.spiresim.template.effect.TimedEffectTemplate;

@DeriveState
public class VulnerableEffectTemplate extends TimedEffectTemplate<VulnerableEffectState> {

    private static VulnerableEffectTemplate INSTANCE;


    public static VulnerableEffectTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VulnerableEffectTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<VulnerableEffectState> getStateType() {
        return VulnerableEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return CommonEffectIdentifiers.INCOMING_DAMAGE_INCREASE_PERCENT;
    }

    @Override
    public String getName() {
        return "Vulnerable";
    }

    public double getModifier() {
        return 1.5;
    }

    @Override
    public void onApply(EncounterController encounterController, EntityController target, VulnerableEffectState effectState, VulnerableEffectState newInstance) {
        super.onApply(encounterController, target, effectState, newInstance);
    }

    @Override
    public void onRemove(EncounterController encounterController, EntityController target, VulnerableEffectState effectState) {
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, VulnerableEffectState effectState, EffectUpdateEvent updateEvent) {
        super.onUpdate(encounterController, target, effectState, updateEvent);
    }

    @Override
    public void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            VulnerableEffectState effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
        if (updateEvent.isEqual(MutableCombatValueEvents.ENTITY_INCOMING_DAMAGE_PERCENT)) {
            mutableCombatValue.setAmount_Multiply(effectState.getModifier());
        }
    }
}
