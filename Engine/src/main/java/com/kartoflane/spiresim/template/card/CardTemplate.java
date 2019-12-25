package com.kartoflane.spiresim.template.card;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.StateTemplate;

import java.util.List;

public abstract class CardTemplate<S extends CardState> implements StateTemplate<S> {

    public abstract TargetingType getTargetingType();

    public abstract CardType getCardType();

    public abstract String getName();

    public abstract int getCost();

    /**
     * Actions to execute when the card is discarded from hand before end of turn.
     */
    public abstract void onDiscard(EncounterController encounterController, EntityController caster, S state);

    /**
     * Actions to execute when the card is exhausted.
     */
    public abstract void onExhaust(EncounterController encounterController, EntityController caster, S state);

    /**
     * Actions to execute when the card is retained in hand at the end of the turn.
     */
    public abstract void onRetain(EncounterController encounterController, EntityController caster, S state);

    /**
     * Actions to execute when the card is played
     *
     * @return the pile in which the played card should be placed
     */
    public abstract CardPileType onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, S state);

    /**
     * Actions to execute at the start of turn.
     */
    public abstract void onTurnStart(EncounterController encounterController, EntityController caster, S state);

    /**
     * Actions to execute at the end of turn.
     */
    public abstract void onTurnEnd(EncounterController encounterController, EntityController caster, S state);
}
