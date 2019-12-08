package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.AIController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EffectState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.template.CardTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class EntityController implements StateController<EntityState> {

    private final EntityState state;
    private final AIController aiController;
    private final Map<CardState, CardController<?, ?>> cardStateToControllerMap = new HashMap<>();


    public EntityController(EntityState state, AIController aiController) {
        this.state = state;
        this.aiController = aiController;

        for (CardState cardState : this.state.getAllCards()) {
            cardStateToControllerMap.put(cardState, new CardController<>(cardState));
        }
    }

    public EntityState getState() {
        return state;
    }

    public void onTurnStart(EncounterController encounterController) {
        this.state.updateEffects(encounterController.getState(), EffectState.UpdateEvent.TURN_START);
        this.state.setEnergyCurrent(this.state.getEnergyMax());
    }

    public void onTurnEnd(EncounterController encounterController) {
        discardHand(encounterController);

        this.state.updateEffects(encounterController.getState(), EffectState.UpdateEvent.TURN_END);

        this.state.setArmorCurrent(0); // TODO armor retention
    }

    public void simulateTurn(GameController gameController, EncounterController encounterController) {
        aiController.controlEntity(gameController, encounterController, this);
    }

    public void drawHand(GameState gameState, int cardsToDraw) {
        for (int drawnCards = 0; drawnCards < cardsToDraw; ++drawnCards) {
            CardState cardState = drawCard(gameState);
            this.state.getHandList().add(cardState);
        }
    }

    public CardState drawCard(GameState gameState) {
        if (this.state.getDrawPileList().isEmpty()) {
            shuffleDiscardPileIntoDrawPile(gameState);
        }

        return this.state.getDrawPileList().remove(0);
    }

    public void shuffleDiscardPileIntoDrawPile(GameState gameState) {
        this.state.getDrawPileList().addAll(this.state.getDiscardPileList());
        this.state.getDiscardPileList().clear();
        Collections.shuffle(this.state.getDrawPileList(), gameState.getRandom());
    }

    public List<CardState> getPlayableCards() {
        return this.state.getHandList().stream()
                .filter(this::isCardPlayable)
                .collect(Collectors.toList());
    }

    public boolean isCardPlayable(CardState cardState) {
        return this.state.getEnergyCurrent() >= cardState.getCost();
    }

    @SuppressWarnings("unchecked")
    public <T extends CardTemplate<S>, S extends CardState> CardController<T, S> getCardController(S cardState) {
        return (CardController<T, S>) this.cardStateToControllerMap.get(cardState);
    }

    public <T extends CardTemplate<S>, S extends CardState> void playCard(
            EncounterController encounterController,
            CardController<T, S> cardController,
            List<EntityController> targets
    ) {
        CardState cardState = cardController.getState();

        this.state.setEnergyCurrent(this.state.getEnergyCurrent() - cardState.getCost());
        this.state.getHandList().remove(cardState);
        this.state.getDiscardPileList().add(cardState);

        cardController.onPlay(encounterController, this, targets);
    }

    public void discardHand(EncounterController encounterController) {
        List<CardState> cardsToDiscard = new ArrayList<>();
        for (CardState card : this.state.getHandList()) {
            cardsToDiscard.add(card);
        }

        for (CardState card : cardsToDiscard) {
            this.state.getHandList().remove(card);
            this.state.getDiscardPileList().add(card);

            getCardController(card).onDiscard(encounterController, this);
        }

        for (CardState card : this.state.getHandList()) {
            getCardController(card).onRetain(encounterController, this);
        }
    }

    public boolean isAlive() {
        return this.state.getHealthCurrent() > 0;
    }

    public void resetAndShuffleDecks(GameState gameState) {
        List<CardState> drawPile = this.state.getDrawPileList();
        drawPile.addAll(this.state.getHandList());
        drawPile.addAll(this.state.getDiscardPileList());
        drawPile.addAll(this.state.getExhaustPileList());
        this.state.getHandList().clear();
        this.state.getDiscardPileList().clear();
        this.state.getExhaustPileList().clear();

        Collections.shuffle(drawPile, gameState.getRandom());
    }
}
