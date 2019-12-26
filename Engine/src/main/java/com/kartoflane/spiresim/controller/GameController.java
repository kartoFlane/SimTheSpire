package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.PlayerAIController;
import com.kartoflane.spiresim.report.EncounterSummary;
import com.kartoflane.spiresim.report.PlaythroughSummary;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.entity.EntityState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GameController implements StateController<GameState> {

    private final GameState state;
    private EntityController playerController;
    private EncounterController currentEncounter;


    public GameController(GameState state) {
        this.state = state;
    }

    public void initialize(EntityState playerEntity) {
        this.state.initialize(playerEntity);
        this.playerController = new EntityController(playerEntity, new PlayerAIController());
    }

    public boolean isInitialized() {
        return this.playerController != null;
    }

    private void checkInitialized() {
        if (!isInitialized()) {
            throw new IllegalStateException("GameController has not been initialized yet!");
        }
    }

    @Override
    public GameState getState() {
        return this.state;
    }

    public EntityController getPlayerController() {
        return this.playerController;
    }

    public EncounterController getCurrentEncounter() {
        return currentEncounter;
    }

    public PlaythroughSummary simulateGame(Function<GameController, EncounterState> encounterSupplier) {
        checkInitialized();

        List<EncounterSummary> encounterSummaryList = new ArrayList<>();
        while (isGameInProgress()) {
            state.setCurrentEncounter(encounterSupplier.apply(this));
            currentEncounter = new EncounterController(state.getCurrentEncounter());

            currentEncounter.onEncounterStart(this);
            EncounterSummary encounterSummary = currentEncounter.simulateEncounter(this);
            currentEncounter.onEncounterEnd(this);

            state.setCurrentEncounter(null);
            currentEncounter = null;

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
