package com.kartoflane.spiresim.state.entity;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.TemplatableState;
import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * State class representing a single entity that can participate in combat - a player or an enemy.
 */
public class EntityState extends TemplatableState {

    private String name;
    private int healthMax;
    private int healthCurrent;
    private int armorCurrent;
    private int energyCurrent;
    private int energyMax;

    private List<EffectState> effectsList = new ArrayList<>();
    private Map<CardPileType, List<CardState>> cardPiles = new HashMap<>();


    public EntityState(EntityTemplate<? extends EntityState> template) {
        super(template);

        for (CardPileType cardPileType : CardPileType.values()) {
            this.cardPiles.put(cardPileType, new ArrayList<>());
        }

        this.setName(template.getName());
        this.setHealthMax(template.getHealth());
        this.setHealthCurrent(template.getHealth());
        this.setArmorCurrent(0);
        this.setEnergyMax(template.getEnergy());
        this.setEnergyCurrent(template.getEnergy());

        this.cardPiles.get(CardPileType.DRAW).addAll(
                template.getStartingDeck().stream()
                        .map(StateFactory::build)
                        .collect(Collectors.toList())
        );
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

    public List<EffectState> getEffectsList() {
        return this.effectsList;
    }

    public List<CardState> getCardPile(CardPileType cardPileType) {
        return this.cardPiles.get(cardPileType);
    }

    public List<CardState> getHandList() {
        return getCardPile(CardPileType.HAND);
    }

    public List<CardState> getDrawPileList() {
        return getCardPile(CardPileType.DRAW);
    }

    public List<CardState> getDiscardPileList() {
        return getCardPile(CardPileType.DISCARD);
    }

    public List<CardState> getExhaustPileList() {
        return getCardPile(CardPileType.EXHAUST);
    }

    public List<CardState> getUsedPowersList() {
        return getCardPile(CardPileType.USED_POWER);
    }

    public List<CardState> getAllCards() {
        final int totalDeckSize = this.cardPiles.values().stream().mapToInt(List::size).sum();

        List<CardState> temporaryList = new ArrayList<>(totalDeckSize);

        for (CardPileType cardPileType : CardPileType.values()) {
            temporaryList.addAll(this.cardPiles.get(cardPileType));
        }

        return Collections.unmodifiableList(temporaryList);
    }
}
