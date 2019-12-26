package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectTemplate;
import com.kartoflane.spiresim.template.effect.EffectUpdateEvent;
import com.kartoflane.spiresim.template.effect.StandardEffectUpdateEvents;

public class EffectController<T extends EffectTemplate<S>, S extends EffectState> implements StateController<S> {

    private final T template;
    private final S state;


    @SuppressWarnings("unchecked")
    public EffectController(S state) {
        this.state = state;
        this.template = (T) state.getTemplate();
    }

    @Override
    public S getState() {
        return this.state;
    }

    public void onApply(GameController gameController, EntityController target, S newInstance) {
        template.onApply(gameController, target, state, newInstance);
    }

    public void onRemove(GameController gameController, EntityController target) {
        template.onRemove(gameController, target, state);
    }

    public void onUpdate(GameController gameController, EntityController target, EffectUpdateEvent updateEvent) {
        template.onUpdate(gameController, target, state, updateEvent);
    }

    public void preprocessCombatValue(
            GameController gameController,
            EntityController target,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
        template.preprocessCombatValue(
                gameController,
                target,
                state,
                mutableCombatValue,
                updateEvent
        );
    }

    public void onTurnStart(GameController gameController, EntityController target) {
        template.onUpdate(gameController, target, state, StandardEffectUpdateEvents.TURN_START);
    }

    public void onTurnEnd(GameController gameController, EntityController target) {
        template.onUpdate(gameController, target, state, StandardEffectUpdateEvents.TURN_END);
    }

    public void onCardDraw(GameController gameController, EntityController target, CardController<?, ?> drawnCard) {
        template.onUpdate(gameController, target, state, StandardEffectUpdateEvents.CARD_DRAW);
    }

    public void onCardDiscard(GameController gameController, EntityController target, CardController<?, ?> discardedCard) {
        template.onUpdate(gameController, target, state, StandardEffectUpdateEvents.CARD_DISCARD);
    }

    public void onCardRetain(GameController gameController, EntityController target, CardController<?, ?> drawnCard) {
        template.onUpdate(gameController, target, state, StandardEffectUpdateEvents.CARD_DRAW);
    }

    public void onCardPlay(GameController gameController, EntityController target, CardController<?, ?> playedCard) {
        template.onUpdate(gameController, target, state, StandardEffectUpdateEvents.CARD_PLAY);
    }

    public boolean isEffectType(EffectIdentifier identifier) {
        return this.state.getEffectIdentifier().isEqual(identifier);
    }
}
