package com.kartoflane.spiresim.template;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.CardState;

import java.util.List;

public interface CardTemplate<S extends CardState> extends StateTemplate<S> {

    TargetingType getTargetingType();

    String getName();

    int getCost();

    /**
     * Actions to execute when the card is discarded from hand before end of turn.
     */
    void onDiscard(EncounterController encounterController, EntityController caster, S cardState);

    /**
     * Actions to execute when the card is exhausted.
     */
    void onExhaust(EncounterController encounterController, EntityController caster, S cardState);

    /**
     * Actions to execute when the card is retained in hand at the end of the turn.
     */
    void onRetain(EncounterController encounterController, EntityController caster, S cardState);

    /**
     * Actions to execute when the card is played
     */
    void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, S cardState);

    /**
     * Actions to execute at the start of turn.
     */
    void onTurnStart(EncounterController encounterController, EntityController caster, S cardState);

    /**
     * Actions to execute at the end of turn.
     */
    void onTurnEnd(EncounterController encounterController, EntityController caster, S cardState);
}
