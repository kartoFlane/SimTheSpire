package com.kartoflane.spiresim.state.card;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.card.BashCardTemplate;

public class AmplifyCardState extends CardState {

    private int attackValue;
    private int startingStacks;


    public AmplifyCardState(BashCardTemplate template) {
        super(template);

        setAttackValue(template.getAttackValue());
        setStartingStacks(template.getStartingStacks());
    }

    @Override
    public BashCardTemplate getTemplate() {
        return (BashCardTemplate) super.getTemplate();
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getAttackValue() {
        return this.attackValue;
    }

    public int getStartingStacks() {
        return startingStacks;
    }

    public void setStartingStacks(int startingStacks) {
        this.startingStacks = startingStacks;
    }
}
