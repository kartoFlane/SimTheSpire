package com.kartoflane.spiresim.template.effect;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.state.effect.TimedEffectState;

public abstract class TimedEffectTemplate<S extends TimedEffectState> extends EffectTemplate<S> {

    @Override
    public void onApply(EncounterController encounterController, EntityController target, S effectState, S newInstance) {
        if (newInstance != null) {
            effectState.setStacks(effectState.getStacks() + newInstance.getStacks());
        }
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, S effectState, EffectUpdateEvent updateEvent) {
        if (updateEvent == EffectUpdateEvent.StandardEffectUpdateEvents.TURN_END) {
            effectState.setStacks(effectState.getStacks() - 1);

            if (effectState.getStacks() == 0) {
                target.removeEffect(encounterController, effectState);
            }
        }
    }
}
