package com.kartoflane.spiresim.controller.card;

import com.kartoflane.spiresim.controller.CardController;
import com.kartoflane.spiresim.controller.targeting.AllTargetingController;
import com.kartoflane.spiresim.state.CardState;

public abstract class TargetAllCardController extends CardController {

    public TargetAllCardController(CardState state) {
        super(state, AllTargetingController.getInstance());
    }
}
