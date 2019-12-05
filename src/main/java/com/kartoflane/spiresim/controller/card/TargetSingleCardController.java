package com.kartoflane.spiresim.controller.card;

import com.kartoflane.spiresim.controller.CardController;
import com.kartoflane.spiresim.controller.targeting.SingleTargetingController;
import com.kartoflane.spiresim.state.CardState;

public abstract class TargetSingleCardController extends CardController {

    public TargetSingleCardController(CardState state) {
        super(state, SingleTargetingController.getInstance());
    }
}
