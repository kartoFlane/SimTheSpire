package com.kartoflane.spiresim.content.template.effect.base;

import com.kartoflane.spiresim.content.state.effect.base.StackingEffectState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.template.effect.EffectUpdateEvent;
import com.kartoflane.spiresim.template.effect.StandardEffectUpdateEvents;

/**
 * A template class representing a stackable effect (a buff or debuff) that can be applied to an entity.
 * Multiple applications of the same effect wil increase the number of stacks, amplifying the effect.
 * The effect decays each turn, and is eventually removed once the stack counter reaches zero.
 */
public abstract class TimedEffectTemplate<S extends StackingEffectState> extends StackingEffectTemplate<S> {

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
