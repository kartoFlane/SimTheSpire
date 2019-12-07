package com.kartoflane.spiresim.controller.targeting;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.ai.AIController;

class AllTargetingController implements TargetingController {

    AllTargetingController() {
    }

    @Override
    public TargetingResult selectTargets(GameController gameController, EncounterController encounterController, AIController aiController) {
        return new TargetingResult(
                TargetingResultType.TARGET_ALL,
                aiController.getEnemyTargetsAll(gameController, encounterController)
        );
    }
}
