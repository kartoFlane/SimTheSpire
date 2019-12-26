package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.controller.ai.AIController;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectTemplate;
import com.kartoflane.spiresim.template.effect.StandardEffectUpdateEvents;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EntityController implements StateController<EntityState> {

    private final EntityState state;
    private final AIController aiController;
    private final Map<CardState, CardController<?, ?>> cardStateToControllerMap = new ConcurrentHashMap<>();
    private final Map<EffectState, EffectController<?, ?>> effectStateToControllerMap = new ConcurrentHashMap<>();


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

    public void onTurnStart(GameController gameController) {
        iterateEffects(effectController -> notifyEvent(gameController, effectController::onTurnStart));
        iterateCards(cardController -> notifyEvent(gameController, cardController::onTurnStart));
        this.state.setEnergyCurrent(this.state.getEnergyMax());
        this.state.setArmorCurrent(0); // TODO armor retention
    }

    public void onTurnEnd(GameController gameController) {
        discardHand(gameController);
        iterateEffects(effectController -> notifyEvent(gameController, effectController::onTurnEnd));
        iterateCards(cardController -> notifyEvent(gameController, cardController::onTurnEnd));
    }

    public void simulateTurn(GameController gameController) {
        aiController.controlEntity(gameController, this);
    }

    public boolean isAlive() {
        return this.state.getHealthCurrent() > 0;
    }

    public void drawHand(GameController gameController, int cardsToDraw) {
        for (int drawnCards = 0; drawnCards < cardsToDraw; ++drawnCards) {
            CardState cardState = drawCard(gameController);
            if (cardState != null) {
                this.state.getHandList().add(cardState);
            }
        }
    }

    public CardState drawCard(GameController gameController) {
        if (this.state.getDrawPileList().isEmpty()) {
            if (this.state.getDiscardPileList().isEmpty()) {
                return null;
            } else {
                shuffleDiscardPileIntoDrawPile(gameController);
            }
        }

        CardState drawnCard = this.state.getDrawPileList().remove(0);
        CardController<?, ?> cardController = getCardController(drawnCard);

        iterateEffects(effectController -> effectController.onCardDraw(gameController, this, cardController));

        return drawnCard;
    }

    public void shuffleDiscardPileIntoDrawPile(GameController gameController) {
        Collections.shuffle(this.state.getDiscardPileList(), gameController.getState().getRandom());
        this.state.getDrawPileList().addAll(this.state.getDiscardPileList());
        this.state.getDiscardPileList().clear();
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
            GameController gameController,
            CardController<T, S> cardController,
            List<EntityController> targets
    ) {
        CardState cardState = cardController.getState();

        this.state.setEnergyCurrent(this.state.getEnergyCurrent() - cardState.getCost());
        this.state.getHandList().remove(cardState);

        CardPileType destinationPile = cardController.onPlay(gameController, this, targets);

        this.state.getCardPile(destinationPile).add(cardState);

        iterateEffects(effectController -> effectController.onCardPlay(gameController, this, cardController));
    }

    public void discardHand(GameController gameController) {
        List<CardState> cardsToDiscard = new ArrayList<>();
        for (CardState card : this.state.getHandList()) {
            // TODO Card retention
            cardsToDiscard.add(card);
        }

        for (CardState card : cardsToDiscard) {
            this.state.getHandList().remove(card);
            this.state.getDiscardPileList().add(card);

            CardController<?, ?> cardController = getCardController(card);
            cardController.onDiscard(gameController, this);
            iterateEffects(effectController -> effectController.onCardDiscard(gameController, this, cardController));
        }

        for (CardState card : this.state.getHandList()) {
            CardController<?, ?> cardController = getCardController(card);
            cardController.onRetain(gameController, this);
            iterateEffects(effectController -> effectController.onCardRetain(gameController, this, cardController));
        }
    }

    public void resetAndShuffleDecks(GameState gameState) {
        List<CardState> drawPile = this.state.getDrawPileList();
        drawPile.addAll(this.state.getHandList());
        drawPile.addAll(this.state.getDiscardPileList());
        drawPile.addAll(this.state.getExhaustPileList());
        drawPile.addAll(this.state.getUsedPowersList());
        this.state.getHandList().clear();
        this.state.getDiscardPileList().clear();
        this.state.getExhaustPileList().clear();
        this.state.getUsedPowersList().clear();

        Collections.shuffle(drawPile, gameState.getRandom());
    }

    public void addCardToDeck(CardState cardState) {
        addCardToPileBottom(cardState, CardPileType.DRAW);
    }

    public void addCardToPileTop(CardState cardState, CardPileType pileType) {
        checkValidCardAddition(cardState);

        this.state.getCardPile(pileType).add(0, cardState);
        cardStateToControllerMap.put(cardState, new CardController<>(cardState));
    }

    public void addCardToPileShuffle(GameController gameController, CardState cardState, CardPileType pileType) {
        checkValidCardAddition(cardState);

        List<CardState> pile = this.state.getCardPile(pileType);
        int index = gameController.getState().getRandom().randomIndex(pile);
        pile.add(index, cardState);
        cardStateToControllerMap.put(cardState, new CardController<>(cardState));
    }

    public void addCardToPileBottom(CardState cardState, CardPileType pileType) {
        checkValidCardAddition(cardState);

        this.state.getCardPile(pileType).add(cardState);
        cardStateToControllerMap.put(cardState, new CardController<>(cardState));
    }

    private void checkValidCardAddition(CardState cardState) {
        if (state.getAllCards().contains(cardState)) {
            throw new IllegalArgumentException("Deck already contains the specified CardState!");
        }
    }

    public void applyEffect(GameController gameController, EffectState effectState) {
        final EffectIdentifier effectIdentifier = effectState.getEffectIdentifier();

        Optional<EffectState> existingEffect$ = this.state.getEffectsList().stream()
                .filter(effect -> effect.getEffectIdentifier().isEqual(effectIdentifier))
                .findFirst();

        if (existingEffect$.isPresent()) {
            EffectController<EffectTemplate<EffectState>, EffectState> existingEffectController = getEffectController(existingEffect$.get());
            existingEffectController.onApply(gameController, this, effectState);
        } else {
            this.state.getEffectsList().add(effectState);
            updateEffectControllersMap();
            getEffectController(effectState).onApply(gameController, this, null);
        }
    }

    public void removeEffect(GameController gameController, EffectIdentifier effectIdentifier) {
        Optional<EffectState> existingEffect$ = this.state.getEffectsList().stream()
                .filter(effect -> effect.getEffectIdentifier().isEqual(effectIdentifier))
                .findFirst();

        existingEffect$.ifPresent(effectState -> removeEffect(gameController, effectState));
    }

    public void removeEffect(GameController gameController, EffectState effectState) {
        this.state.getEffectsList().remove(effectState);
        getEffectController(effectState).onRemove(gameController, this);

        updateEffectControllersMap();
    }

    private void iterateEffects(Consumer<EffectController<?, ?>> consumer) {
        this.effectStateToControllerMap.values().forEach(consumer);
    }

    private void iterateCards(Consumer<CardController<?, ?>> consumer) {
        this.cardStateToControllerMap.values().forEach(consumer);
    }

    private void notifyEvent(GameController gameController, BiConsumer<GameController, EntityController> consumer) {
        consumer.accept(gameController, this);
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

    public void applyDamage(GameController gameController, MutableCombatValue mutableCombatValue) {
        notifyEffectsForCombatValue(
                gameController,
                this,
                mutableCombatValue,
                MutableCombatValueEvents.ENTITY_INCOMING_DAMAGE_ATTACK_FLAT,
                MutableCombatValueEvents.ENTITY_INCOMING_DAMAGE_ATTACK_PERCENT
        );

        calculateDamageAfterBlock(mutableCombatValue);
        this.state.setHealthCurrent(this.state.getHealthCurrent() - mutableCombatValue.getAmount());

        System.out.printf("    %s takes %s damage!%n", this.state.getName(), mutableCombatValue.getAmount());

        iterateEffects(effectController -> effectController.onUpdate(
                gameController,
                this,
                StandardEffectUpdateEvents.ENTITY_INCOMING_DAMAGE
        ));
    }

    private void calculateDamageAfterBlock(MutableCombatValue mutableCombatValue) {
        int unblockedAmount = Math.max(0, mutableCombatValue.getAmount() - this.state.getArmorCurrent());
        this.state.setArmorCurrent(Math.max(0, this.state.getArmorCurrent() - mutableCombatValue.getAmount()));
        mutableCombatValue.setAmount(unblockedAmount);
    }

    public void applyHeal(GameController gameController, MutableCombatValue mutableCombatValue) {
        notifyEffectsForCombatValue(
                gameController,
                this,
                mutableCombatValue,
                MutableCombatValueEvents.ENTITY_INCOMING_HEAL_FLAT,
                MutableCombatValueEvents.ENTITY_INCOMING_HEAL_PERCENT
        );

        this.state.setHealthCurrent(this.state.getHealthCurrent() + mutableCombatValue.getAmount());

        iterateEffects(effectController -> effectController.onUpdate(
                gameController,
                this,
                StandardEffectUpdateEvents.ENTITY_INCOMING_HEAL
        ));
    }

    public void applyArmor(GameController gameController, MutableCombatValue mutableCombatValue) {
        notifyEffectsForCombatValue(
                gameController,
                this,
                mutableCombatValue,
                MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_FLAT,
                MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_PERCENT
        );

        this.state.setArmorCurrent(this.state.getArmorCurrent() + mutableCombatValue.getAmount());

        iterateEffects(effectController -> effectController.onUpdate(
                gameController,
                this,
                StandardEffectUpdateEvents.ENTITY_INCOMING_ARMOR
        ));
    }

    public MutableCombatValue buildOutgoingAttackDamageValue(GameController gameController, int amount) {
        return buildMutableCombatValue(
                gameController,
                amount,
                MutableCombatValueEvents.ENTITY_OUTGOING_DAMAGE_ATTACK_FLAT,
                MutableCombatValueEvents.ENTITY_OUTGOING_DAMAGE_ATTACK_PERCENT
        );
    }

    public MutableCombatValue buildOutgoingSkillDamageValue(GameController gameController, int amount) {
        return buildMutableCombatValue(
                gameController,
                amount,
                MutableCombatValueEvents.ENTITY_OUTGOING_DAMAGE_SKILL_FLAT,
                MutableCombatValueEvents.ENTITY_OUTGOING_DAMAGE_SKILL_PERCENT
        );
    }

    public MutableCombatValue buildOutgoingHealingValue(GameController gameController, int amount) {
        return buildMutableCombatValue(
                gameController,
                amount,
                MutableCombatValueEvents.ENTITY_OUTGOING_HEAL_FLAT,
                MutableCombatValueEvents.ENTITY_OUTGOING_HEAL_PERCENT
        );
    }

    public MutableCombatValue buildOutgoingArmorValue(GameController gameController, int amount) {
        return buildMutableCombatValue(
                gameController,
                amount,
                MutableCombatValueEvents.ENTITY_OUTGOING_ARMOR_FLAT,
                MutableCombatValueEvents.ENTITY_OUTGOING_ARMOR_PERCENT
        );
    }

    public MutableCombatValue buildMutableCombatValue(GameController gameController, int amount, MutableCombatValueEvent... updateEvents) {
        MutableCombatValue mutableCombatValue = new MutableCombatValue();
        mutableCombatValue.setAmount(amount);

        notifyEffectsForCombatValue(gameController, this, mutableCombatValue, updateEvents);

        return mutableCombatValue;
    }

    private void notifyEffectsForCombatValue(
            GameController gameController,
            EntityController target,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent... updateEvents
    ) {
        for (MutableCombatValueEvent updateEvent : updateEvents) {
            iterateEffects(effectController -> effectController.preprocessCombatValue(
                    gameController,
                    target,
                    mutableCombatValue,
                    updateEvent
            ));
        }
    }
}
