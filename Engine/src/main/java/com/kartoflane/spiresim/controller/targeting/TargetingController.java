package com.kartoflane.spiresim.controller.targeting;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.ai.AIController;

public interface TargetingController {
    TargetingResult selectTargets(GameController gameController, AIController aiController);
}
