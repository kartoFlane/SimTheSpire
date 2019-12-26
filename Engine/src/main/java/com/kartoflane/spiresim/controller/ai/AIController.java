package com.kartoflane.spiresim.controller.ai;

import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;

import java.util.List;

public interface AIController {
    EntityController getEnemyTargetSingle(GameController gameController);

    List<EntityController> getEnemyTargetsAll(GameController gameController);

    void controlEntity(GameController gameController, EntityController entityController);
}
