package com.kartoflane.spiresim.content.template.card.base;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.base.SimpleAttackCardState;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardType;

@DeriveState
public abstract class SimpleAttackCardTemplate<S extends SimpleAttackCardState> extends TargetSingleCardTemplate<S> {

    @Override
    public CardType getCardType() {
        return CardType.ATTACK;
    }

    @Override
    public CardPileType onPlay(GameController gameController, EntityController caster, EntityController target, S state) {
        target.applyDamage(gameController, caster.buildOutgoingAttackDamageValue(gameController, state.getAttackValue()));

        return CardPileType.DISCARD;
    }

    public abstract int getAttackValue(GameController gameController);
}
