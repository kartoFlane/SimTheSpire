package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.PlayerAIController;
import com.kartoflane.spiresim.report.EncounterSummary;
import com.kartoflane.spiresim.report.PlaythroughSummary;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.GameState;

import java.util.ArrayList;
import java.util.List;
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

    public PlaythroughSummary simulateGame(Supplier<EncounterState> encounterSupplier) {
        List<EncounterSummary> encounterSummaryList = new ArrayList<>();
        while (isGameInProgress()) {
            EncounterState encounterState = encounterSupplier.get();
            EncounterController encounterController = new EncounterController(encounterState);

            encounterController.onEncounterStart(this);
            EncounterSummary encounterSummary = encounterController.simulateEncounter(this);
            encounterController.onEncounterEnd(this);

            encounterSummaryList.add(encounterSummary);
        }

        System.out.println("Game over! Played encounters: " + encounterSummaryList.size());

        return new PlaythroughSummary()
                .withEncounterSummaries(encounterSummaryList);
    }

    public boolean isGameInProgress() {
        return this.playerController.isAlive();
    }
}
