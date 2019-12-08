package com.kartoflane.spiresim.state.card;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.card.BashCardTemplate;

public class AmplifyCardState extends CardState {

    private int attackValue;


    public AmplifyCardState(BashCardTemplate template) {
        super(template);

        this.attackValue = template.getAttackValue();
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
}
