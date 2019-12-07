package com.kartoflane.spiresim.template.card;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.CardState;

import java.util.List;

public interface CardTemplate<S extends CardState> {

    // template     -- general, non changing, create state from this, link to controller class to instantiate
    //              -- knows its state class, so can implement card logic
    // state        -- live, in-game, can be upgraded / modified, will need to govern its own state to reset it after combat
    // controller   -- updates state, calls logic from template

    Class<S> getStateType();

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
