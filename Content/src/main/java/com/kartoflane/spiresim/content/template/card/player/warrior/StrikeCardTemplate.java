package com.kartoflane.spiresim.content.template.card.player.warrior;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.content.state.card.player.warrior.StrikeCardState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

@DeriveState
public class StrikeCardTemplate extends CardTemplate<StrikeCardState> {

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
    public void onDiscard(EncounterController encounterController, EntityController caster, StrikeCardState state) {

    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster, StrikeCardState state) {

    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster, StrikeCardState state) {

    }

    @Override
    public void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, StrikeCardState state) {
        EntityController target = targets.get(0);
        target.applyDamage(encounterController, caster.buildOutgoingAttackValue(encounterController, state.getAttackValue()));
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, StrikeCardState state) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, StrikeCardState state) {

    }
}
