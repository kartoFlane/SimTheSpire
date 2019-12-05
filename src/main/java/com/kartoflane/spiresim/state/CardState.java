package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.controller.CardController;

import java.util.HashMap;
import java.util.Map;

public class CardState {

    private final Class<? extends CardController> cardControllerType;
    private final Map<String, Object> propertyMap = new HashMap<>();

    private String name;
    private int cost;
    private boolean retain;


    public CardState(Class<? extends CardController> cardControllerType, String name, int cost) {
        this.cardControllerType = cardControllerType;
        this.setName(name);
        this.setCost(cost);
        this.setRetain(false);
    }

    public Class<? extends CardController> getCardControllerType() {
        return cardControllerType;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must be a non-negative number: " + cost);
        }
        this.cost = cost;
    }

    public boolean isRetain() {
        return retain;
    }

    public void setRetain(boolean retain) {
        this.retain = retain;
    }

    public <T> T getProperty(String key) {
        return (T) propertyMap.get(key);
    }

    public <T> T getPropertyOrDefault(String key, T defaultValue) {
        return (T) propertyMap.getOrDefault(key, defaultValue);
    }

    public <T> void setProperty(String key, T value) {
        propertyMap.put(key, value);
    }
}
