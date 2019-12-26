package com.kartoflane.spiresim.template.card;

import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.StateTemplate;

import java.util.List;

public abstract class CardTemplate<S extends CardState> implements StateTemplate<S> {

    public abstract TargetingType getTargetingType();

    public abstract CardType getCardType();

    public abstract String getName();

    public abstract int getCost(GameController gameController);

    /**
     * Actions to execute when the card is discarded from hand before end of turn.
     */
    public abstract void onDiscard(GameController gameController, EntityController caster, S state);

    /**
     * Actions to execute when the card is exhausted.
     */
    public abstract void onExhaust(GameController gameController, EntityController caster, S state);

    /**
     * Actions to execute when the card is retained in hand at the end of the turn.
     */
    public abstract void onRetain(GameController gameController, EntityController caster, S state);

    /**
     * Actions to execute when the card is played
     *
     * @return the pile in which the played card should be placed
     */
    public abstract CardPileType onPlay(GameController gameController, EntityController caster, List<EntityController> targets, S state);

    /**
     * Actions to execute at the start of turn.
     */
    public abstract void onTurnStart(GameController gameController, EntityController caster, S state);

    /**
     * Actions to execute at the end of turn.
     */
    public abstract void onTurnEnd(GameController gameController, EntityController caster, S state);
}
