package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.template.card.CardTemplate;

public abstract class CardState {

    private final CardTemplate<? extends CardState> template;

    private String name;
    private int cost;


    protected CardState(CardTemplate<? extends CardState> template) {
        this.template = template;
        this.setName(template.getName());
        this.setCost(template.getCost());
    }

    public CardTemplate<? extends CardState> getTemplate() {
        return template;
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
}
