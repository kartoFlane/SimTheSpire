package com.kartoflane.spiresim.state.card;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.card.DefendTemplate;

public class DefendCardState extends CardState {

    private int defenseValue;


    public DefendCardState(DefendTemplate template) {
        super(template);
    }

    @Override
    public DefendTemplate getTemplate() {
        return (DefendTemplate) super.getTemplate();
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public int getDefenseValue() {
        return this.defenseValue;
    }
}
