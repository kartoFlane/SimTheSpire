package com.kartoflane.spiresim.state.card;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.card.StrikeCardTemplate;

public class StrikeCardState extends CardState {

    private int attackValue;


    public StrikeCardState(StrikeCardTemplate template) {
        super(template);

        this.attackValue = template.getAttackValue();
    }

    @Override
    public StrikeCardTemplate getTemplate() {
        return (StrikeCardTemplate) super.getTemplate();
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getAttackValue() {
        return this.attackValue;
    }
}
