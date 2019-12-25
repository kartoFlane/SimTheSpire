package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.enemy.DarkStrikeCardState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

@DeriveState
public class DarkStrikeCardTemplate extends CardTemplate<DarkStrikeCardState> {

    private static DarkStrikeCardTemplate INSTANCE;


    public static DarkStrikeCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DarkStrikeCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<DarkStrikeCardState> getStateType() {
        return DarkStrikeCardState.class;
    }

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.SINGLE;
    }

    @Override
    public String getName() {
        return "Dark Strike";
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
    public void onDiscard(EncounterController encounterController, EntityController caster, DarkStrikeCardState state) {

    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster, DarkStrikeCardState state) {

    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster, DarkStrikeCardState state) {

    }

    @Override
    public CardPileType onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, DarkStrikeCardState state) {
        EntityController target = targets.get(0);
        target.applyDamage(encounterController, caster.buildOutgoingAttackValue(encounterController, state.getAttackValue()));

        return CardPileType.DISCARD;
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, DarkStrikeCardState state) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, DarkStrikeCardState state) {

    }
}
