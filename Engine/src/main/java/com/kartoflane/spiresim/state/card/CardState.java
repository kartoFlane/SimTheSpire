package com.kartoflane.spiresim.state.card;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.TemplatableState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

public abstract class CardState extends TemplatableState {

    private String name;
    private int cost;


    protected CardState(GameController gameController, CardTemplate<? extends CardState> template) {
        super(template);
        this.setName(template.getName());
        this.setCost(template.getCost(gameController));
    }

    @Override
    public CardTemplate<? extends CardState> getTemplate() {
        return (CardTemplate<? extends CardState>) super.getTemplate();
    }

    public TargetingType getTargetingType() {
        return getTemplate().getTargetingType();
    }

    public CardType getCardType() {
        return getTemplate().getCardType();
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
