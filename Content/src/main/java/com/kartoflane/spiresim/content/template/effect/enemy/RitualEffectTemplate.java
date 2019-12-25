package com.kartoflane.spiresim.content.template.effect.enemy;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.common.StrengthEffectState;
import com.kartoflane.spiresim.content.state.effect.enemy.RitualEffectState;
import com.kartoflane.spiresim.content.template.effect.base.StackingEffectTemplate;
import com.kartoflane.spiresim.content.template.effect.common.StrengthEffectTemplate;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectUpdateEvent;
import com.kartoflane.spiresim.template.effect.StandardEffectUpdateEvents;

@DeriveState
public class RitualEffectTemplate extends StackingEffectTemplate<RitualEffectState> {
    private static RitualEffectTemplate INSTANCE;


    public static RitualEffectTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RitualEffectTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<RitualEffectState> getStateType() {
        return RitualEffectState.class;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return EnemyEffectIdentifiers.POWER_RITUAL;
    }

    @Override
    public String getName() {
        return "Ritual";
    }

    @Override
    public void onUpdate(EncounterController encounterController, EntityController target, RitualEffectState effectState, EffectUpdateEvent updateEvent) {
        if (updateEvent.isEqual(StandardEffectUpdateEvents.TURN_START)) {
            StrengthEffectState state = StateFactory.build(StrengthEffectTemplate.getInstance());
            state.setStacks(effectState.getStacks());
            target.applyEffect(encounterController, state);
        }
    }
}
