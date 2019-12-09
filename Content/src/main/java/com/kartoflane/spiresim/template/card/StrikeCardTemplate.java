package com.kartoflane.spiresim.template.card;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.card.StrikeCardState;

import java.util.List;

public class StrikeCardTemplate implements CardTemplate<StrikeCardState> {

    private static StrikeCardTemplate INSTANCE;


    public static StrikeCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StrikeCardTemplate();
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

    @Override
    public CardType getCardType() {
        return CardType.ATTACK;
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
        System.out.printf("%s uses %s on %s!%n", caster.getState().getName(), cardState.getName(), target.getState().getName());
        target.applyDamage(encounterController, caster.buildOutgoingAttackValue(encounterController, cardState.getAttackValue()));
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, StrikeCardState cardState) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, StrikeCardState cardState) {

    }
}
