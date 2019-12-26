package com.kartoflane.spiresim.test.controller;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        List<EntityState> enemyStates = Arrays.stream(enemyTemplates)
                .map(enemyTemplate -> StateFactory.build(gameController, enemyTemplate))
                .collect(Collectors.toList());

        gameController.setCurrentEncounter(new EncounterController(new EncounterState(enemyStates)));

        return gameController;
    }
}
