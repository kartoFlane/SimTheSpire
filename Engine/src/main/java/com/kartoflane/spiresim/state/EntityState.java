package com.kartoflane.spiresim.state;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * State class representing a single entity that can participate in combat - a player or an enemy.
 */
public class EntityState {

    private String name;
    private int healthMax;
    private int healthCurrent;
    private int armorCurrent;
    private int energyCurrent;
    private int energyMax;

    private List<EffectState> effectsList = new ArrayList<>();

    private List<CardState> handList = new ArrayList<>();
    private List<CardState> drawPileList = new ArrayList<>();
    private List<CardState> discardPileList = new ArrayList<>();
    private List<CardState> exhaustPileList = new ArrayList<>();


    public EntityState(List<CardState> startingDeck, String name, int healthMax) {
        this.drawPileList.addAll(startingDeck);

        this.setName(name);
        this.setHealthMax(healthMax);
        this.setHealthCurrent(healthMax);
        this.setArmorCurrent(0);
        this.setEnergyMax(3);
        this.setEnergyCurrent(3);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("Name must not be empty.");
        }
        this.name = name;
    }

    public int getHealthMax() {
        return healthMax;
    }

    public void setHealthMax(int healthMax) {
        if (healthMax < 0) {
            throw new IllegalArgumentException("Health must not be a negative number: " + healthMax);
        }
        this.healthMax = healthMax;
        setHealthCurrent(getHealthCurrent());
    }

    public int getHealthCurrent() {
        return healthCurrent;
    }

    public void setHealthCurrent(int healthCurrent) {
        this.healthCurrent = Math.min(healthCurrent, this.healthMax);
    }

    public int getArmorCurrent() {
        return armorCurrent;
    }

    public void setArmorCurrent(int armorCurrent) {
        this.armorCurrent = armorCurrent;
    }

    public int getEnergyCurrent() {
        return energyCurrent;
    }

    public void setEnergyCurrent(int energyCurrent) {
        this.energyCurrent = Math.max(0, energyCurrent);
    }

    public int getEnergyMax() {
        return energyMax;
    }

    public void setEnergyMax(int energyMax) {
        this.energyMax = Math.max(0, energyMax);
    }

    public void applyDamage(int amount) {
        if (amount < 0) {
            return;
        }

        int unblockedDamage = calculateDamageAfterBlock(amount);

        this.setHealthCurrent(this.getHealthCurrent() - unblockedDamage);
    }

    private int calculateDamageAfterBlock(int amount) {
        int unblockedAmount = Math.max(0, amount - this.armorCurrent);
        this.armorCurrent = Math.max(0, this.armorCurrent - amount);
        return unblockedAmount;
    }

    public void applyHeal(int amount) {
        if (amount < 0) {
            return;
        }

        this.setHealthCurrent(this.getHealthCurrent() + amount);
    }

    public void applyArmor(int amount) {
        this.setArmorCurrent(this.getArmorCurrent() + amount);
    }

    public void applyEffect(EncounterState encounterState, EffectState effectState) {
        Optional<EffectState> existingEffect = this.effectsList.stream()
                .filter(effect -> effect.getEffectIdentifier().equals(effectState.getEffectIdentifier()))
                .findFirst();

        if (existingEffect.isPresent()) {
            existingEffect.get().onApply(encounterState, this, effectState);
        } else {
            this.effectsList.add(effectState);
            effectState.onApply(encounterState, this, effectState);
        }
    }

    public void removeEffect(EncounterState encounterState, String effectIdentifier) {
        OptionalInt existingIndex = IntStream.range(0, this.effectsList.size())
                .filter(index -> this.effectsList.get(index).getEffectIdentifier().equals(effectIdentifier))
                .findFirst();

        if (existingIndex.isPresent()) {
            int index = existingIndex.getAsInt();
            EffectState effectState = this.effectsList.remove(index);
            effectState.onRemove(encounterState, this);
        }
    }

    public void updateEffects(EncounterState encounterState, EffectState.UpdateEvent updateEvent) {
        for (EffectState effectState : this.effectsList) {
            effectState.onUpdate(encounterState, this, updateEvent);
        }
    }

    public void iterateEffects(Consumer<EffectState> effectStateConsumer) {
        for (int i = 0; i < effectsList.size(); ++i) {
            effectStateConsumer.accept(effectsList.get(i));
        }
    }

    public List<CardState> getHandList() {
        return handList;
    }

    public List<CardState> getDrawPileList() {
        return drawPileList;
    }

    public List<CardState> getDiscardPileList() {
        return discardPileList;
    }

    public List<CardState> getExhaustPileList() {
        return exhaustPileList;
    }

    public List<CardState> getAllCards() {
        List<CardState> temporaryList = new ArrayList<>(
                this.handList.size() + this.drawPileList.size() + this.discardPileList.size() + this.exhaustPileList.size()
        );

        temporaryList.addAll(this.handList);
        temporaryList.addAll(this.drawPileList);
        temporaryList.addAll(this.discardPileList);
        temporaryList.addAll(this.exhaustPileList);

        return Collections.unmodifiableList(temporaryList);
    }
}
