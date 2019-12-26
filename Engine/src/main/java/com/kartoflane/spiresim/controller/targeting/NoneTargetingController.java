package com.kartoflane.spiresim.controller.targeting;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.ai.AIController;

import java.util.Collections;

/**
 * Implements no targeting logic, returning no targets, but being a valid targeting result.
 * Suitable for cards that apply to the caster, such as self-buffs, enhancements, curses, etc.
 */
class NoneTargetingController implements TargetingController {

    NoneTargetingController() {
    }

    @Override
    public TargetingResult selectTargets(GameController gameController, AIController aiController) {
        return new TargetingResult(TargetingResultType.TARGET_NONE, Collections.emptyList());
    }
}
