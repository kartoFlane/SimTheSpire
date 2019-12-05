package com.kartoflane.spiresim.controller.ai;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;

import java.util.List;

public interface AIController {
    EntityController getEnemyTargetSingle(GameController gameController, EncounterController encounterController);

    List<EntityController> getEnemyTargetsAll(GameController gameController, EncounterController encounterController);

    void controlEntity(GameController gameController, EncounterController encounterController, EntityController entityController);
}
