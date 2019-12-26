package com.kartoflane.spiresim.content.template.card.player.warrior;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.player.warrior.DefendCardState;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

@DeriveState
public class DefendCardTemplate extends CardTemplate<DefendCardState> {

    private static DefendCardTemplate INSTANCE;


    public static DefendCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DefendCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<DefendCardState> getStateType() {
        return DefendCardState.class;
    }

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.NONE;
    }

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public int getCost(GameController gameController) {
        return 1;
    }

    @Override
    public CardType getCardType() {
        return CardType.SKILL;
    }

    public int getDefenseValue(GameController gameController) {
        return 6;
    }

    @Override
    public void onDiscard(GameController gameController, EntityController caster, DefendCardState cardState) {
    }

    @Override
    public void onExhaust(GameController gameController, EntityController caster, DefendCardState cardState) {
    }

    @Override
    public void onRetain(GameController gameController, EntityController caster, DefendCardState cardState) {
    }

    @Override
    public CardPileType onPlay(GameController gameController, EntityController caster, List<EntityController> targets, DefendCardState cardState) {
        caster.applyArmor(gameController, caster.buildOutgoingArmorValue(gameController, cardState.getDefenseValue()));

        return CardPileType.DISCARD;
    }

    @Override
    public void onTurnStart(GameController gameController, EntityController caster, DefendCardState cardState) {
    }

    @Override
    public void onTurnEnd(GameController gameController, EntityController caster, DefendCardState cardState) {
    }
}
