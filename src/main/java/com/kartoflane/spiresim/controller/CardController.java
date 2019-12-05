package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.targeting.TargetingController;
import com.kartoflane.spiresim.state.CardState;

import java.util.List;

public abstract class CardController implements StateController<CardState> {

    private final TargetingController targetingController;
    protected final CardState state;


    public CardController(CardState state, TargetingController targetingController) {
        this.state = state;
        this.targetingController = targetingController;
    }

    public CardState getState() {
        return this.state;
    }

    public TargetingController getTargetingController() {
        return this.targetingController;
    }

    /**
     * Actions to execute when the card is discarded from hand before end of turn.
     */
    public abstract void onDiscard(EncounterController encounterController, EntityController caster);

    /**
     * Actions to execute when the card is exhausted.
     */
    public abstract void onExhaust(EncounterController encounterController, EntityController caster);

    /**
     * Actions to execute when the card is retained in hand at the end of the turn.
     */
    public abstract void onRetain(EncounterController encounterController, EntityController caster);

    /**
     * Actions to execute when the card is played
     */
    public abstract void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets);

    /**
     * Actions to execute at the start of turn.
     */
    public abstract void onTurnStart(EncounterController encounterController, EntityController caster);

    /**
     * Actions to execute at the end of turn.
     */
    public abstract void onTurnEnd(EncounterController encounterController, EntityController caster);
}
