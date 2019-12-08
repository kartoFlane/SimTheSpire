package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.state.EffectState;
import com.kartoflane.spiresim.template.EffectIdentifier;
import com.kartoflane.spiresim.template.EffectTemplate;
import com.kartoflane.spiresim.template.EffectUpdateEvent;

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

    public void onApply(EncounterController encounterController, EntityController target) {
        template.onApply(encounterController, target, state);
    }

    public void onRemove(EncounterController encounterController, EntityController target) {
        template.onRemove(encounterController, target, state);
    }

    public void onUpdate(EncounterController encounterController, EntityController target, EffectUpdateEvent updateEvent) {
        template.onUpdate(encounterController, target, state, updateEvent);
    }

    public void preprocessCombatValue(
            EncounterController encounterController,
            EntityController target,
            MutableCombatValue mutableCombatValue,
            EffectUpdateEvent updateEvent
    ) {
        template.preprocessCombatValue(
                encounterController,
                target,
                state,
                mutableCombatValue,
                updateEvent
        );
    }

    public void onTurnStart(EncounterController encounterController, EntityController target) {
        template.onUpdate(encounterController, target, state, EffectUpdateEvent.StandardEffectUpdateEvents.TURN_START);
    }

    public void onTurnEnd(EncounterController encounterController, EntityController target) {
        template.onUpdate(encounterController, target, state, EffectUpdateEvent.StandardEffectUpdateEvents.TURN_END);

        state.setStackCounter(state.getStackCounter() - 1);
        if (state.getStackCounter() <= 0) {
            target.removeEffect(encounterController, state.getEffectIdentifier());
        }
    }

    public void onCardDraw(EncounterController encounterController, EntityController target, CardController<?, ?> drawnCard) {
        template.onUpdate(encounterController, target, state, EffectUpdateEvent.StandardEffectUpdateEvents.CARD_DRAW);
    }

    public void onCardDiscard(EncounterController encounterController, EntityController target, CardController<?, ?> discardedCard) {
        template.onUpdate(encounterController, target, state, EffectUpdateEvent.StandardEffectUpdateEvents.CARD_DISCARD);
    }

    public void onCardRetain(EncounterController encounterController, EntityController target, CardController<?, ?> drawnCard) {
        template.onUpdate(encounterController, target, state, EffectUpdateEvent.StandardEffectUpdateEvents.CARD_DRAW);
    }

    public void onCardPlay(EncounterController encounterController, EntityController target, CardController<?, ?> playedCard) {
        template.onUpdate(encounterController, target, state, EffectUpdateEvent.StandardEffectUpdateEvents.CARD_PLAY);
    }

    public boolean isEffectType(EffectIdentifier identifier) {
        return this.state.getEffectIdentifier().equals(identifier);
    }
}
