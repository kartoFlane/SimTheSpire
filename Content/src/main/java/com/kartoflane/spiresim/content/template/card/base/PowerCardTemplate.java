package com.kartoflane.spiresim.content.template.card.base;

import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

public abstract class PowerCardTemplate<S extends CardState> extends CardTemplate<S> {

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.NONE;
    }

    @Override
    public CardType getCardType() {
        return CardType.POWER;
    }

    @Override
    public void onDiscard(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onExhaust(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onRetain(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public final CardPileType onPlay(GameController gameController, EntityController caster, List<EntityController> targets, S state) {
        onPlay(gameController, caster, state);

        return CardPileType.USED_POWER;
    }

    public void onPlay(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onTurnStart(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onTurnEnd(GameController gameController, EntityController caster, S state) {
    }
}
