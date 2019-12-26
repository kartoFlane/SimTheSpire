package com.kartoflane.spiresim.controller.targeting;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.ai.AIController;

import java.util.Collections;

class SingleTargetingController implements TargetingController {

    SingleTargetingController() {
    }

    @Override
    public TargetingResult selectTargets(GameController gameController, AIController aiController) {
        return new TargetingResult(
                TargetingResultType.TARGET_SINGLE,
                Collections.singletonList(aiController.getEnemyTargetSingle(gameController))
        );
    }
}
