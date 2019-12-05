package com.kartoflane.spiresim.controller.targeting;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.ai.AIController;

import java.util.Collections;

/**
 * Implements no targeting logic, returning no targets, but being a valid targeting result.
 * Suitable for cards that apply to the caster, such as self-buffs, enhancements, curses, etc.
 */
public class NoneTargetingController implements TargetingController {

    private static NoneTargetingController INSTANCE;


    private NoneTargetingController() {
    }

    @Override
    public TargetingResult selectTargets(GameController gameController, EncounterController encounterController, AIController aiController) {
        return new TargetingResult(TargetingResultType.TARGET_NONE, Collections.emptyList());
    }

    public static NoneTargetingController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoneTargetingController();
        }

        return INSTANCE;
    }
}
