package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.combat.MutableCombatValueEvents;
import com.kartoflane.spiresim.controller.ai.AIController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectTemplate;
import com.kartoflane.spiresim.template.effect.StandardEffectUpdateEvents;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
        iterateCards(cardController -> notifyEvent(encounterController, cardController::onTurnStart));
        this.state.setEnergyCurrent(this.state.getEnergyMax());
        this.state.setArmorCurrent(0); // TODO armor retention
    }

    public void onTurnEnd(EncounterController encounterController) {
        discardHand(encounterController);
        iterateEffects(effectController -> notifyEvent(encounterController, effectController::onTurnEnd));
        iterateCards(cardController -> notifyEvent(encounterController, cardController::onTurnEnd));
    }

    public void simulateTurn(GameController gameController, EncounterController encounterController) {
        aiController.controlEntity(gameController, encounterController, this);
    }

    public boolean isAlive() {
        return this.state.getHealthCurrent() > 0;
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

        CardPileType destinationPile = cardController.onPlay(encounterController, this, targets);

        this.state.getCardPile(destinationPile).add(cardState);

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

    public void applyEffect(EncounterController encounterController, EffectState effectState) {
        final EffectIdentifier effectIdentifier = effectState.getEffectIdentifier();

        Optional<EffectState> existingEffect = this.state.getEffectsList().stream()
                .filter(effect -> effect.getEffectIdentifier().isEqual(effectIdentifier))
                .findFirst();

        if (existingEffect.isPresent()) {
            EffectController<EffectTemplate<EffectState>, EffectState> existingEffectController = getEffectController(existingEffect.get());
            existingEffectController.onApply(encounterController, this, effectState);
        } else {
            this.state.getEffectsList().add(effectState);
            updateEffectControllersMap();
            getEffectController(effectState).onApply(encounterController, this, null);
        }
    }

    public void removeEffect(EncounterController encounterController, EffectIdentifier effectIdentifier) {
        Optional<EffectState> existingEffect = this.state.getEffectsList().stream()
                .filter(effect -> effect.getEffectIdentifier().isEqual(effectIdentifier))
                .findFirst();

        existingEffect.ifPresent(effectState -> removeEffect(encounterController, effectState));
    }

    public void removeEffect(EncounterController encounterController, EffectState effectState) {
        this.state.getEffectsList().remove(effectState);
        getEffectController(effectState).onRemove(encounterController, this);

        updateEffectControllersMap();
    }

    private void iterateEffects(Consumer<EffectController<?, ?>> consumer) {
        List<EffectController<?, ?>> modifiableView = new ArrayList<>(this.effectStateToControllerMap.values());
        modifiableView.forEach(consumer);
        modifiableView.clear();
    }

    private void iterateCards(Consumer<CardController<?, ?>> consumer) {
        List<CardController<?, ?>> modifiableView = new ArrayList<>(this.cardStateToControllerMap.values());
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
        notifyEffectsForCombatValue(
                encounterController,
                this,
                mutableCombatValue,
                MutableCombatValueEvents.ENTITY_INCOMING_DAMAGE_FLAT,
                MutableCombatValueEvents.ENTITY_INCOMING_DAMAGE_PERCENT
        );

        calculateDamageAfterBlock(mutableCombatValue);
        this.state.setHealthCurrent(this.state.getHealthCurrent() - mutableCombatValue.getAmount());

        iterateEffectsNonModifying(effectController -> effectController.onUpdate(
                encounterController,
                this,
                StandardEffectUpdateEvents.ENTITY_INCOMING_DAMAGE
        ));
    }

    private void calculateDamageAfterBlock(MutableCombatValue mutableCombatValue) {
        int unblockedAmount = Math.max(0, mutableCombatValue.getAmount() - this.state.getArmorCurrent());
        this.state.setArmorCurrent(Math.max(0, this.state.getArmorCurrent() - mutableCombatValue.getAmount()));
        mutableCombatValue.setAmount(unblockedAmount);
    }

    public void applyHeal(EncounterController encounterController, MutableCombatValue mutableCombatValue) {
        notifyEffectsForCombatValue(
                encounterController,
                this,
                mutableCombatValue,
                MutableCombatValueEvents.ENTITY_INCOMING_HEAL_FLAT,
                MutableCombatValueEvents.ENTITY_INCOMING_HEAL_PERCENT
        );

        this.state.setHealthCurrent(this.state.getHealthCurrent() + mutableCombatValue.getAmount());

        iterateEffectsNonModifying(effectController -> effectController.onUpdate(
                encounterController,
                this,
                StandardEffectUpdateEvents.ENTITY_INCOMING_HEAL
        ));
    }

    public void applyArmor(EncounterController encounterController, MutableCombatValue mutableCombatValue) {
        notifyEffectsForCombatValue(
                encounterController,
                this,
                mutableCombatValue,
                MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_FLAT,
                MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_PERCENT
        );

        this.state.setArmorCurrent(this.state.getArmorCurrent() + mutableCombatValue.getAmount());

        iterateEffectsNonModifying(effectController -> effectController.onUpdate(
                encounterController,
                this,
                StandardEffectUpdateEvents.ENTITY_INCOMING_ARMOR
        ));
    }

    public MutableCombatValue buildOutgoingAttackValue(EncounterController encounterController, int amount) {
        return buildMutableCombatValue(
                encounterController,
                amount,
                MutableCombatValueEvents.ENTITY_OUTGOING_DAMAGE_FLAT,
                MutableCombatValueEvents.ENTITY_OUTGOING_DAMAGE_PERCENT
        );
    }

    public MutableCombatValue buildOutgoingHealingValue(EncounterController encounterController, int amount) {
        return buildMutableCombatValue(
                encounterController,
                amount,
                MutableCombatValueEvents.ENTITY_OUTGOING_HEAL_FLAT,
                MutableCombatValueEvents.ENTITY_OUTGOING_HEAL_PERCENT
        );
    }

    public MutableCombatValue buildOutgoingArmorValue(EncounterController encounterController, int amount) {
        return buildMutableCombatValue(
                encounterController,
                amount,
                MutableCombatValueEvents.ENTITY_OUTGOING_ARMOR_FLAT,
                MutableCombatValueEvents.ENTITY_OUTGOING_ARMOR_PERCENT
        );
    }

    public MutableCombatValue buildMutableCombatValue(EncounterController encounterController, int amount, MutableCombatValueEvent... updateEvents) {
        MutableCombatValue mutableCombatValue = new MutableCombatValue();
        mutableCombatValue.setAmount(amount);

        notifyEffectsForCombatValue(encounterController, this, mutableCombatValue, updateEvents);

        return mutableCombatValue;
    }

    private void notifyEffectsForCombatValue(
            EncounterController encounterController,
            EntityController target,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent... updateEvents
    ) {
        for (MutableCombatValueEvent updateEvent : updateEvents) {
            iterateEffectsNonModifying(effectController -> effectController.preprocessCombatValue(
                    encounterController,
                    target,
                    mutableCombatValue,
                    updateEvent
            ));
        }
    }

    private void iterateEffectsNonModifying(Consumer<EffectController<?, ?>> consumer) {
        this.effectStateToControllerMap.values().forEach(consumer);
    }
}
