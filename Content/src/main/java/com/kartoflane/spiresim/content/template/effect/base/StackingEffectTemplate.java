package com.kartoflane.spiresim.content.template.effect.base;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.base.StackingEffectState;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.template.effect.EffectTemplate;
import com.kartoflane.spiresim.template.effect.EffectUpdateEvent;

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
    public void onApply(GameController gameController, EntityController target, S effectState, S newInstance) {
        if (newInstance != null) {
            effectState.setStacks(effectState.getStacks() + newInstance.getStacks());
        }
    }

    @Override
    public void onRemove(GameController gameController, EntityController target, S effectState) {
    }

    @Override
    public void onUpdate(GameController gameController, EntityController target, S effectState, EffectUpdateEvent updateEvent) {
    }

    @Override
    public void preprocessCombatValue(
            GameController gameController,
            EntityController target,
            S effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
    }
}
