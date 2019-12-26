package com.kartoflane.spiresim.test.template.card;

import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;
import com.kartoflane.spiresim.test.entity.card.TestCardState;

import java.util.List;

public class TestCardTemplate extends CardTemplate<TestCardState> {
    private static TestCardTemplate INSTANCE;


    public static TestCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestCardTemplate();
        }
        return INSTANCE;
    }

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.NONE;
    }

    @Override
    public CardType getCardType() {
        return CardType.SKILL;
    }

    @Override
    public String getName() {
        return "Dummy Card";
    }

    @Override
    public int getCost(GameController gameController) {
        return 1;
    }

    @Override
    public void onDiscard(GameController gameController, EntityController caster, TestCardState state) {
    }

    @Override
    public void onExhaust(GameController gameController, EntityController caster, TestCardState state) {
    }

    @Override
    public void onRetain(GameController gameController, EntityController caster, TestCardState state) {
    }

    @Override
    public CardPileType onPlay(GameController gameController, EntityController caster, List<EntityController> targets, TestCardState state) {
        return CardPileType.DISCARD;
    }

    @Override
    public void onTurnStart(GameController gameController, EntityController caster, TestCardState state) {
    }

    @Override
    public void onTurnEnd(GameController gameController, EntityController caster, TestCardState state) {
    }

    @Override
    public Class<TestCardState> getStateType() {
        return TestCardState.class;
    }
}
