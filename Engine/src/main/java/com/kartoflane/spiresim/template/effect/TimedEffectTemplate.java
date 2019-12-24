package com.kartoflane.spiresim.template.effect;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.state.effect.StackingEffectState;

/**
 * A template class representing a stackable effect (a buff or debuff) that can be applied to an entity.
 * Multiple applications of the same effect wil increase the number of stacks, amplifying the effect.
 * The effect decays each turn, and is eventually removed once the stack counter reaches zero.
 */
public abstract class TimedEffectTemplate<S extends StackingEffectState> extends EffectTemplate<S> {

    @Override
    public void onApply(EncounterController encounterController, EntityController target, S effectState, S newInstance) {
        if (newInstance != null) {
            effectState.setStacks(effectState.getStacks() + newInstance.getStacks());
        }
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, S effectState, EffectUpdateEvent updateEvent) {
        if (updateEvent.isEqual(StandardEffectUpdateEvents.TURN_END)) {
            effectState.setStacks(effectState.getStacks() - 1);

            if (effectState.getStacks() == 0) {
                target.removeEffect(encounterController, effectState);
            }
        }
    }
}
