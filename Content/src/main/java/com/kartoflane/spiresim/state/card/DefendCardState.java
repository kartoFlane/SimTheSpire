package com.kartoflane.spiresim.state.card;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.card.DefendCardTemplate;

public class DefendCardState extends CardState {

    private int defenseValue;


    public DefendCardState(DefendCardTemplate template) {
        super(template);

        this.defenseValue = template.getDefenseValue();
    }

    @Override
    public DefendCardTemplate getTemplate() {
        return (DefendCardTemplate) super.getTemplate();
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public int getDefenseValue() {
        return this.defenseValue;
    }
}
