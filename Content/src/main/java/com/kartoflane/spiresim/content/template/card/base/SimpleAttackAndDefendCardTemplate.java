package com.kartoflane.spiresim.content.template.card.base;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.base.SimpleAttackAndDefendCardState;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardType;

@DeriveState
public abstract class SimpleAttackAndDefendCardTemplate extends TargetSingleCardTemplate<SimpleAttackAndDefendCardState> {

    @Override
    public CardType getCardType() {
        return CardType.ATTACK;
    }

    @Override
    public Class<SimpleAttackAndDefendCardState> getStateType() {
        return SimpleAttackAndDefendCardState.class;
    }

    @Override
    public CardPileType onPlay(GameController gameController, EntityController caster, EntityController target, SimpleAttackAndDefendCardState state) {
        target.applyDamage(gameController, caster.buildOutgoingAttackDamageValue(gameController, state.getAttackValue()));
        caster.applyArmor(gameController, caster.buildOutgoingArmorValue(gameController, state.getDefenseValue()));

        return CardPileType.DISCARD;
    }

    public abstract int getAttackValue(GameController gameController);

    public abstract int getDefenseValue(GameController gameController);
}
