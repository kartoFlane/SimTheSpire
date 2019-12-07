package com.kartoflane.spiresim.template.card.concrete;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.card.concrete.StrikeCardState;
import com.kartoflane.spiresim.template.card.CardTemplate;

import java.util.List;

public class StrikeTemplate implements CardTemplate<StrikeCardState> {

    private static StrikeTemplate INSTANCE;


    public static StrikeTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StrikeTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<StrikeCardState> getStateType() {
        return StrikeCardState.class;
    }

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.SINGLE;
    }

    @Override
    public String getName() {
        return "Strike";
    }

    @Override
    public int getCost() {
        return 1;
    }

    public int getAttackValue() {
        return 6;
    }

    @Override
    public void onDiscard(EncounterController encounterController, EntityController caster, StrikeCardState cardState) {

    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster, StrikeCardState cardState) {

    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster, StrikeCardState cardState) {

    }

    @Override
    public void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, StrikeCardState cardState) {
        EntityController target = targets.get(0);
        EntityState targetState = target.getState();

        targetState.applyDamage(cardState.getAttackValue());
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, StrikeCardState cardState) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, StrikeCardState cardState) {

    }
}
