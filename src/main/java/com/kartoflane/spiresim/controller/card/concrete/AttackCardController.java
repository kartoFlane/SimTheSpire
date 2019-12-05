package com.kartoflane.spiresim.controller.card.concrete;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.card.TargetSingleCardController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.card.concrete.AttackCardState;

import java.util.List;

public class AttackCardController extends TargetSingleCardController {

    public AttackCardController(CardState state) {
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
        EntityController target = targets.get(0);
        EntityState targetState = target.getState();

        targetState.applyDamage(getAttackValue());
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster) {
        // Nothing.
    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster) {
        // Nothing.
    }

    public int getAttackValue() {
        return state.getProperty(AttackCardState.ATTACK_VALUE);
    }
}
