package com.kartoflane.spiresim.state.card.concrete;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.card.concrete.StrikeTemplate;

public class StrikeCardState extends CardState {

    private int attackValue;


    public StrikeCardState(StrikeTemplate template) {
        super(template);

        this.attackValue = template.getAttackValue();
    }

    @Override
    public StrikeTemplate getTemplate() {
        return (StrikeTemplate) super.getTemplate();
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getAttackValue() {
        return this.attackValue;
    }
}
