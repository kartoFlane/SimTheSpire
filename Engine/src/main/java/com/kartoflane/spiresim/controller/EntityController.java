package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.AIController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EffectState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.template.CardTemplate;
import com.kartoflane.spiresim.template.EffectIdentifier;
import com.kartoflane.spiresim.template.EffectTemplate;
import com.kartoflane.spiresim.template.EffectUpdateEvent;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntityController implements StateController<EntityState> {

    private final EntityState state;
    private final AIController aiController;
    private final Map<CardState, CardController<?, ?>> cardStateToControllerMap = new HashMap<>();
    private final Map<EffectState, EffectController<?, ?>> effectStateToControllerMap = new HashMap<>();


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
        iterateEffects(effectController -> notifyEvent(encounterController, effectController::onTurnStart));
        this.state.setEnergyCurrent(this.state.getEnergyMax());
        this.state.setArmorCurrent(0); // TODO armor retention
    }

    public void onTurnEnd(EncounterController encounterController) {
        discardHand(encounterController);
        iterateEffects(effectController -> notifyEvent(encounterController, effectController::onTurnEnd));
    }

    public void simulateTurn(GameController gameController, EncounterController encounterController) {
        aiController.controlEntity(gameController, encounterController, this);
    }

    public void drawHand(GameState gameState, EncounterController encounterController, int cardsToDraw) {
        for (int drawnCards = 0; drawnCards < cardsToDraw; ++drawnCards) {
            CardState cardState = drawCard(gameState, encounterController);
            this.state.getHandList().add(cardState);
        }
    }

    public CardState drawCard(GameState gameState, EncounterController encounterController) {
        if (this.state.getDrawPileList().isEmpty()) {
            shuffleDiscardPileIntoDrawPile(gameState);
        }

        CardState drawnCard = this.state.getDrawPileList().remove(0);
        CardController<?, ?> cardController = getCardController(drawnCard);

        iterateEffects(effectController -> effectController.onCardDraw(encounterController, this, cardController));

        return drawnCard;
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

    @SuppressWarnings("unchecked")
    public <T extends EffectTemplate<S>, S extends EffectState> EffectController<T, S> getEffectController(S effectState) {
        return (EffectController<T, S>) this.effectStateToControllerMap.get(effectState);
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

        iterateEffects(effectController -> effectController.onCardPlay(encounterController, this, cardController));
    }

    public void discardHand(EncounterController encounterController) {
        List<CardState> cardsToDiscard = new ArrayList<>();
        for (CardState card : this.state.getHandList()) {
            // TODO Card retention
            cardsToDiscard.add(card);
        }

        for (CardState card : cardsToDiscard) {
            this.state.getHandList().remove(card);
            this.state.getDiscardPileList().add(card);

            CardController<?, ?> cardController = getCardController(card);
            cardController.onDiscard(encounterController, this);
            iterateEffects(effectController -> effectController.onCardDiscard(encounterController, this, cardController));
        }

        for (CardState card : this.state.getHandList()) {
            CardController<?, ?> cardController = getCardController(card);
            cardController.onRetain(encounterController, this);
            iterateEffects(effectController -> effectController.onCardRetain(encounterController, this, cardController));
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

    public void applyEffect(EncounterController encounterController, EffectState effectState) {
        final EffectIdentifier effectIdentifier = effectState.getEffectIdentifier();

        Optional<EffectState> existingEffect = this.state.getEffectsList().stream()
                .filter(effect -> effect.getEffectIdentifier().equals(effectIdentifier))
                .findFirst();

        EffectController<?, ?> effectController = null;
        if (existingEffect.isPresent()) {
            effectController = getEffectController(existingEffect.get());
        } else {
            this.state.getEffectsList().add(effectState);
            updateEffectControllersMap();
            effectController = getEffectController(effectState);
        }

        effectController.onApply(encounterController, this);
    }

    public void removeEffect(EncounterController encounterController, EffectIdentifier effectIdentifier) {
        OptionalInt existingIndex = IntStream.range(0, this.state.getEffectsList().size())
                .filter(index -> this.state.getEffectsList().get(index).getEffectIdentifier().equals(effectIdentifier))
                .findFirst();

        if (existingIndex.isPresent()) {
            int index = existingIndex.getAsInt();
            EffectController<?, ?> effectController = getEffectController(this.state.getEffectsList().remove(index));
            effectController.onRemove(encounterController, this);

            updateEffectControllersMap();
        }
    }

    private void iterateEffects(Consumer<EffectController<?, ?>> consumer) {
        List<EffectController<?, ?>> modifiableView = new ArrayList<>(this.effectStateToControllerMap.values());
        modifiableView.forEach(consumer);
        modifiableView.clear();
    }

    private void notifyEvent(EncounterController encounterController, BiConsumer<EncounterController, EntityController> consumer) {
        consumer.accept(encounterController, this);
    }

    private void updateEffectControllersMap() {
        for (EffectState effectState : this.effectStateToControllerMap.keySet()) {
            if (!this.state.getEffectsList().contains(effectState)) {
                effectStateToControllerMap.remove(effectState);
            }
        }

        for (EffectState effectState : this.state.getEffectsList()) {
            this.effectStateToControllerMap.putIfAbsent(effectState, new EffectController<>(effectState));
        }
    }

    public void applyDamage(EncounterController encounterController, MutableCombatValue mutableCombatValue) {
        if (mutableCombatValue.getAmount() < 0) {
            return;
        }

        this.effectStateToControllerMap.values().stream()
                .filter(effectController -> effectController.isEffectType(EffectIdentifier.EffectIdentifiers.DAMAGE_RECEIVED_INCREASE))
                .forEach(effectController -> effectController.preprocessCombatValue(
                        encounterController,
                        this,
                        mutableCombatValue,
                        EffectUpdateEvent.StandardEffectUpdateEvents.ENTITY_INCOMING_DAMAGE
                ));

        calculateDamageAfterBlock(mutableCombatValue);

        this.state.setHealthCurrent(this.state.getHealthCurrent() - mutableCombatValue.getAmount());

        System.out.printf("%s takes %s damage!%n", state.getName(), mutableCombatValue.getAmount());
    }

    private void calculateDamageAfterBlock(MutableCombatValue mutableCombatValue) {
        int unblockedAmount = Math.max(0, mutableCombatValue.getAmount() - this.state.getArmorCurrent());
        this.state.setArmorCurrent(Math.max(0, this.state.getArmorCurrent() - mutableCombatValue.getAmount()));
        mutableCombatValue.setAmount(unblockedAmount);
    }

    public void applyHeal(MutableCombatValue mutableCombatValue) {
        if (mutableCombatValue.getAmount() < 0) {
            return;
        }

        this.state.setHealthCurrent(this.state.getHealthCurrent() + mutableCombatValue.getAmount());
    }

    public void applyArmor(EncounterController encounterController, MutableCombatValue mutableCombatValue) {
        this.effectStateToControllerMap.values().stream()
                .filter(effectController -> effectController.isEffectType(EffectIdentifier.EffectIdentifiers.ARMOR_RECEIVED_REDUCE))
                .forEach(effectController -> effectController.preprocessCombatValue(
                        encounterController,
                        this,
                        mutableCombatValue,
                        EffectUpdateEvent.StandardEffectUpdateEvents.ENTITY_ARMOR
                ));

        this.state.setArmorCurrent(this.state.getArmorCurrent() + mutableCombatValue.getAmount());

        this.effectStateToControllerMap.values().stream()
                .filter(effectController -> effectController.isEffectType(EffectIdentifier.EffectIdentifiers.ARMOR_RECEIVED_REDUCE))
                .forEach(effectController -> effectController.onUpdate(
                        encounterController,
                        this,
                        EffectUpdateEvent.StandardEffectUpdateEvents.ENTITY_ARMOR
                ));
    }

    public MutableCombatValue buildOutgoingAttackValue(EncounterController encounterController, int amount) {
        return buildMutableCombatValue(
                encounterController,
                EffectIdentifier.EffectIdentifiers.DAMAGE_DEALT_INCREASE,
                EffectUpdateEvent.StandardEffectUpdateEvents.ENTITY_OUTGOING_DAMAGE,
                amount
        );
    }

    public MutableCombatValue buildArmorValue(EncounterController encounterController, int amount) {
        return buildMutableCombatValue(
                encounterController,
                EffectIdentifier.EffectIdentifiers.ARMOR_GRANTED_REDUCE,
                EffectUpdateEvent.StandardEffectUpdateEvents.ENTITY_ARMOR,
                amount
        );
    }

    public MutableCombatValue buildMutableCombatValue(EncounterController encounterController, EffectIdentifier effectIdentifier, EffectUpdateEvent updateEvent, int amount) {
        MutableCombatValue mutableCombatValue = new MutableCombatValue();
        mutableCombatValue.setAmount(amount);

        this.effectStateToControllerMap.values().stream()
                .filter(effectController -> effectController.isEffectType(effectIdentifier))
                .forEach(effectController -> effectController.preprocessCombatValue(
                        encounterController,
                        this,
                        mutableCombatValue,
                        updateEvent
                ));

        return mutableCombatValue;
    }
}
