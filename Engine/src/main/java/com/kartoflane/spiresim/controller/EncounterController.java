package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.EnemyAIController;
import com.kartoflane.spiresim.report.EncounterSummary;
import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.state.entity.EntityState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncounterController implements StateController<EncounterState> {

    private final EncounterState state;
    private final Map<EntityState, EntityController> enemyControllers = new HashMap<>();


    public EncounterController(EncounterState state) {
        this.state = state;

        for (EntityState entityState : state.getEnemyEntities()) {
            enemyControllers.put(
                    entityState,
                    new EntityController(entityState, new EnemyAIController())
            );
        }
    }

    public EncounterState getState() {
        return this.state;
    }

    public List<EntityController> listEnemyControllers() {
        return new ArrayList<>(this.enemyControllers.values());
    }

    public void onEncounterStart(GameController gameController) {
    }

    public EncounterSummary simulateEncounter(GameController gameController) {
        while (isEncounterInProgress(gameController)) {
            state.setTurnCount(state.getTurnCount() + 1);
            executeEntityTurn(gameController, gameController.getPlayerController());

            processEnemyControllers(gameController);
        }

        return new EncounterSummary()
                .withTurns(state.getTurnCount());
    }

    public boolean isEncounterInProgress(GameController gameController) {
        return gameController.getPlayerController().isAlive() && !enemyControllers.isEmpty();
    }

    private void processEnemyControllers(GameController gameController) {
        List<EntityState> entitiesToRemove = new ArrayList<>();

        for (EntityController enemyController : enemyControllers.values()) {
            processEnemyController(gameController, enemyController, entitiesToRemove);
        }

        for (EntityState entityState : entitiesToRemove) {
            this.enemyControllers.remove(entityState);
        }
    }

    private void processEnemyController(GameController gameController, EntityController entityController, List<EntityState> entitiesToRemove) {
        if (entityController.isAlive()) {
            executeEntityTurn(gameController, entityController);
        } else {
            entitiesToRemove.add(entityController.getState());
        }
    }

    private void executeEntityTurn(GameController gameController, EntityController entityController) {
        entityController.onTurnStart(gameController);
        entityController.simulateTurn(gameController);
        entityController.onTurnEnd(gameController);
    }

    public void onEncounterEnd(GameController gameController) {
        EntityController playerController = gameController.getPlayerController();

        playerController.resetAndShuffleDecks(gameController);
        if (playerController.isAlive()) {
            System.out.printf(
                    "Player won! HP: %s/%s%n",
                    playerController.getState().getHealthCurrent(),
                    playerController.getState().getHealthMax()
            );
        }
    }
}
