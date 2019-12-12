package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private List<CardState> handList = new ArrayList<>();
    private List<CardState> drawPileList = new ArrayList<>();
    private List<CardState> discardPileList = new ArrayList<>();
    private List<CardState> exhaustPileList = new ArrayList<>();


    public EntityState(EntityTemplate<? extends EntityState> template) {
        super(template);

        this.setName(template.getName());
        this.setHealthMax(template.getHealth());
        this.setHealthCurrent(template.getHealth());
        this.setArmorCurrent(0);
        this.setEnergyMax(template.getEnergy());
        this.setEnergyCurrent(template.getEnergy());

        this.drawPileList.addAll(
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
