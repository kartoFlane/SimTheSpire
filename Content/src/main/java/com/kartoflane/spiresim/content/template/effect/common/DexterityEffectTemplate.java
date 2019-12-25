package com.kartoflane.spiresim.content.template.effect.common;

import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.common.DexterityEffectState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectUpdateEvent;
import com.kartoflane.spiresim.template.effect.StackingEffectTemplate;

@DeriveState
public class DexterityEffectTemplate extends StackingEffectTemplate<DexterityEffectState> {
    private static DexterityEffectTemplate INSTANCE;


    public static DexterityEffectTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DexterityEffectTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<DexterityEffectState> getStateType() {
        return DexterityEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return CommonEffectIdentifiers.INCOMING_ARMOR_INCREASE_FLAT;
    }

    @Override
    public String getName() {
        return "Dexterity";
    }

    @Override
    public void onApply(EncounterController encounterController, EntityController target, DexterityEffectState effectState, DexterityEffectState newInstance) {
    }

    @Override
    public void onRemove(EncounterController encounterController, EntityController target, DexterityEffectState effectState) {
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, DexterityEffectState effectState, EffectUpdateEvent updateEvent) {
    }

    @Override
    public void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            DexterityEffectState effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
        if (updateEvent.isEqual(MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_FLAT)) {
            mutableCombatValue.setAmount_Add(effectState.getStacks());
        }
    }
}
