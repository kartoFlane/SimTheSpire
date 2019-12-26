package com.kartoflane.spiresim.test.controller;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.GameState;

public class TestGameController extends GameController {
    public TestGameController(GameState state) {
        super(state);
    }

    public void setCurrentEncounter(EncounterController encounterController) {
        super.setCurrentEncounter(encounterController);
    }
}
