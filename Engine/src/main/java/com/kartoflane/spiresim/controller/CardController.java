package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.targeting.TargetingController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.CardTemplate;

import java.util.List;

public class CardController<T extends CardTemplate<S>, S extends CardState> implements StateController<S> {

    private final TargetingController targetingController;
    private final T template;
    private final S state;


    @SuppressWarnings("unchecked")
    public CardController(S state) {
        this.state = state;
        this.template = (T) state.getTemplate();
        this.targetingController = this.template.getTargetingType().getController();
    }

    public S getState() {
        return this.state;
    }

    public TargetingController getTargetingController() {
        return this.targetingController;
    }

    public void onDiscard(EncounterController encounterController, EntityController caster) {
        template.onDiscard(encounterController, caster, state);
    }

    public void onExhaust(EncounterController encounterController, EntityController caster) {
        template.onExhaust(encounterController, caster, state);
    }

    public void onRetain(EncounterController encounterController, EntityController caster) {
        template.onRetain(encounterController, caster, state);
    }

    public void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets) {
        template.onPlay(encounterController, caster, targets, state);
    }

    public void onTurnStart(EncounterController encounterController, EntityController caster) {
        template.onTurnStart(encounterController, caster, state);
    }

    public void onTurnEnd(EncounterController encounterController, EntityController caster) {
        template.onTurnEnd(encounterController, caster, state);
    }
}