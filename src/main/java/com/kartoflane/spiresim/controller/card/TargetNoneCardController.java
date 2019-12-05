package com.kartoflane.spiresim.controller.card;

import com.kartoflane.spiresim.controller.CardController;
import com.kartoflane.spiresim.controller.targeting.NoneTargetingController;
import com.kartoflane.spiresim.state.CardState;

/**
 * Implements no targeting logic, returning no targets, but being a valid targeting result.
 * Suitable for cards that apply to the caster, such as self-buffs, enhancements, curses, etc.
 */
public abstract class TargetNoneCardController extends CardController {

    public TargetNoneCardController(CardState state) {
        super(state, NoneTargetingController.getInstance());
    }
}
