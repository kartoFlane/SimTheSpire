package com.kartoflane.spiresim.content.template.card.base;

import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardType;

public abstract class PowerCardTemplate<S extends CardState> extends TargetNoneCardTemplate<S> {

    @Override
    public CardType getCardType() {
        return CardType.POWER;
    }

    @Override
    public final CardPileType onPlay(GameController gameController, EntityController caster, S state) {
        onPlayPower(gameController, caster, state);

        return CardPileType.USED_POWER;
    }

    public abstract void onPlayPower(GameController gameController, EntityController caster, S state);
}
