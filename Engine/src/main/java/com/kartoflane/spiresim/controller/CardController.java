package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.targeting.TargetingController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;

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

    public void onDiscard(GameController gameController, EntityController caster) {
        template.onDiscard(gameController, caster, state);
    }

    public void onExhaust(GameController gameController, EntityController caster) {
        template.onExhaust(gameController, caster, state);
    }

    public void onRetain(GameController gameController, EntityController caster) {
        template.onRetain(gameController, caster, state);
    }

    public CardPileType onPlay(GameController gameController, EntityController caster, List<EntityController> targets) {
        if (targets.size() == 1) {
            System.out.printf("  %s plays %s on %s!%n", caster.getState().getName(), state.getName(), targets.get(0).getState().getName());
        } else {
            System.out.printf("  %s plays %s!%n", caster.getState().getName(), state.getName());
        }

        return template.onPlay(gameController, caster, targets, state);
    }

    public void onTurnStart(GameController gameController, EntityController caster) {
        template.onTurnStart(gameController, caster, state);
    }

    public void onTurnEnd(GameController gameController, EntityController caster) {
        template.onTurnEnd(gameController, caster, state);
    }
}
