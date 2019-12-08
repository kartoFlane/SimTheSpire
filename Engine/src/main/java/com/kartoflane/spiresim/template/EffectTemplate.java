package com.kartoflane.spiresim.template;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.MutableCombatValue;
import com.kartoflane.spiresim.state.EffectState;

public interface EffectTemplate<S extends EffectState> extends StateTemplate<S> {

    EffectIdentifier getEffectIdentifier();

    /**
     * Actions to perform when the effect is first applied to an entity.
     */
    void onApply(EncounterController encounterController, EntityController target, S effectState);

    /**
     * Actions to perform when the effect is removed from the entity.
     */
    void onRemove(EncounterController encounterController, EntityController target, S effectState);

    /**
     * Actions to perform during update steps.
     */
    void onUpdate(EncounterController encounterController, EntityController target, S effectState, EffectUpdateEvent updateEvent);

    void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            S effectState,
            MutableCombatValue mutableCombatValue,
            EffectUpdateEvent updateEvent
    );
}
