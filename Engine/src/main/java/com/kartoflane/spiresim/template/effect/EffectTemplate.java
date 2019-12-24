package com.kartoflane.spiresim.template.effect;

import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.template.StateTemplate;

public abstract class EffectTemplate<S extends EffectState> implements StateTemplate<S> {

    public abstract EffectIdentifier getEffectIdentifier();

    public abstract String getName();

    /**
     * Actions to perform when the effect is first applied to an entity.
     */
    public abstract void onApply(EncounterController encounterController, EntityController target, S effectState, S newInstance);

    /**
     * Actions to perform when the effect is removed from the entity.
     */
    public abstract void onRemove(EncounterController encounterController, EntityController target, S effectState);

    /**
     * Actions to perform during update steps.
     */
    public abstract void onUpdate(EncounterController encounterController, EntityController target, S effectState, EffectUpdateEvent updateEvent);

    public abstract void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            S effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    );
}
