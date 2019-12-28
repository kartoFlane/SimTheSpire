package com.kartoflane.spiresim.test.controller;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.template.entity.EntityTemplate;
import com.kartoflane.spiresim.test.template.encounter.TestEncounterTemplate;

public class TestGameController extends GameController {
    public TestGameController(GameState state) {
        super(state);
    }

    public void setCurrentEncounter(EncounterController encounterController) {
        super.setCurrentEncounter(encounterController);
    }

    public static TestGameController setupTestEnvironment(EntityTemplate<?> entityTemplate, EntityTemplate<?>... enemyTemplates) {
        TestGameController gameController = new TestGameController(new GameState());
        gameController.initialize(StateFactory.build(gameController, entityTemplate));

        EncounterState encounterState = StateFactory.build(gameController, TestEncounterTemplate.getInstance());
        gameController.setCurrentEncounter(new EncounterController(encounterState));

        return gameController;
    }
}
