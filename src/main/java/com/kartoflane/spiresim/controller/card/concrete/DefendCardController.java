package com.kartoflane.spiresim.controller.card.concrete;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.card.TargetNoneCardController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.card.concrete.DefendCardState;

import java.util.List;

public class DefendCardController extends TargetNoneCardController {

    public DefendCardController(CardState state) {
        super(state);
    }

    @Override
    public void onDiscard(EncounterController encounterController, EntityController caster) {
        // Nothing.
    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster) {
        // Nothing.
    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster) {
        // Nothing.
    }

    @Override
    public void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets) {
        EntityState casterState = caster.getState();

        casterState.applyArmor(getDefenseValue());
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster) {
        // Nothing.
    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster) {
        // Nothing.
    }

    public int getDefenseValue() {
        return state.getProperty(DefendCardState.DEFENSE_VALUE);
    }
}
