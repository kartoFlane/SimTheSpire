package com.kartoflane.spiresim.content.template.card.base;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.base.SimpleAttackCardState;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

@DeriveState
public abstract class SimpleAttackCardTemplate<S extends SimpleAttackCardState> extends CardTemplate<S> {

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.SINGLE;
    }

    @Override
    public CardType getCardType() {
        return CardType.ATTACK;
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
        EntityController target = targets.get(0);
        target.applyDamage(gameController, caster.buildOutgoingAttackDamageValue(gameController, state.getAttackValue()));

        onPlay(gameController, caster, target, state);

        return CardPileType.DISCARD;
    }

    public void onPlay(GameController gameController, EntityController caster, EntityController target, S state) {
    }

    @Override
    public void onTurnStart(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onTurnEnd(GameController gameController, EntityController caster, S state) {
    }

    public abstract int getAttackValue(GameController gameController);
}
