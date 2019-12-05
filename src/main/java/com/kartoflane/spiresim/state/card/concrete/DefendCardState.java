package com.kartoflane.spiresim.state.card.concrete;

import com.kartoflane.spiresim.controller.card.concrete.DefendCardController;
import com.kartoflane.spiresim.state.CardState;

public class DefendCardState extends CardState {
    public static final String DEFENSE_VALUE = "DEFENSE_VALUE";


    public DefendCardState() {
        super(DefendCardController.class, "Defend", 1);

        this.setProperty(DEFENSE_VALUE, 6);
    }
}
