package com.kartoflane.spiresim.state.card.concrete;

import com.kartoflane.spiresim.controller.card.concrete.AttackCardController;
import com.kartoflane.spiresim.state.CardState;

public class AttackCardState extends CardState {
    public static final String ATTACK_VALUE = "ATTACK_VALUE";


    public AttackCardState() {
        super(AttackCardController.class, "Strike", 1);

        this.setProperty(ATTACK_VALUE, 6);
    }
}
