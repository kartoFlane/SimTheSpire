package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.enemy.IncantationCardState;
import com.kartoflane.spiresim.content.state.effect.enemy.RitualEffectState;
import com.kartoflane.spiresim.content.template.card.base.PowerCardTemplate;
import com.kartoflane.spiresim.content.template.effect.enemy.RitualEffectTemplate;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.state.StateFactory;

@DeriveState
public class IncantationCardTemplate extends PowerCardTemplate<IncantationCardState> {

    private static IncantationCardTemplate INSTANCE;


    public static IncantationCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IncantationCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<IncantationCardState> getStateType() {
        return IncantationCardState.class;
    }

    @Override
    public String getName() {
        return "Incantation";
    }

    @Override
    public int getCost() {
        return 1;
    }

    public int getStartingStacks() {
        return 3;
    }

    @Override
    public void onPlay(EncounterController encounterController, EntityController caster, IncantationCardState cardState) {
        RitualEffectState effectState = StateFactory.build(RitualEffectTemplate.getInstance());
        effectState.setStacks(cardState.getStartingStacks());
        caster.applyEffect(encounterController, effectState);
    }
}
