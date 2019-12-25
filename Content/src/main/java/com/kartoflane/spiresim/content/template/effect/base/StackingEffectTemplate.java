package com.kartoflane.spiresim.content.template.effect.base;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.base.StackingEffectState;
import com.kartoflane.spiresim.content.state.effect.common.DexterityEffectState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.effect.EffectTemplate;
import com.kartoflane.spiresim.template.effect.EffectUpdateEvent;

import java.util.List;

/**
 * A template class representing a stackable effect (a buff or debuff) that can be applied to an entity.
 * Multiple applications of the same effect wil increase the number of stacks, amplifying the effect.
 */
@DeriveState
public abstract class StackingEffectTemplate<S extends StackingEffectState> extends EffectTemplate<S> {
    public final int getStacks() {
        return 0;
    }

    @Override
    public void onApply(EncounterController encounterController, EntityController target, S effectState, S newInstance) {
        if (newInstance != null) {
            effectState.setStacks(effectState.getStacks() + newInstance.getStacks());
        }
    }

    @Override
    public void onRemove(EncounterController encounterController, EntityController target, S effectState) {
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, S effectState, EffectUpdateEvent updateEvent) {
    }

    @Override
    public void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            S effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
    }
}
