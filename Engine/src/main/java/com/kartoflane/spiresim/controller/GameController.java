package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.PlayerAIController;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.GameState;

import java.util.function.Supplier;

public class GameController implements StateController<GameState> {

    private final GameState state;
    private final EntityController playerController;


    public GameController(GameState state) {
        this.state = state;
        this.playerController = new EntityController(
                this.state.getPlayerState(),
                new PlayerAIController()
        );
    }

    @Override
    public GameState getState() {
        return this.state;
    }

    public EntityController getPlayerController() {
        return this.playerController;
    }

    public void simulateGame(Supplier<EncounterState> encounterSupplier) {
        while (isGameInProgress()) {
            EncounterState encounterState = encounterSupplier.get();
            EncounterController encounterController = new EncounterController(encounterState);

            encounterController.onEncounterStart(this);
            encounterController.simulateEncounter(this);
            encounterController.onEncounterEnd(this);
        }

        System.out.println("Game over!");
    }

    public boolean isGameInProgress() {
        return this.playerController.isAlive();
    }
}
