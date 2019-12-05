package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.EnemyAIController;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.EntityState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncounterController implements StateController<EncounterState> {

    private final EncounterState state;
    private final EntityController playerController;
    private final Map<EntityState, EntityController> enemyControllers = new HashMap<>();


    public EncounterController(EncounterState state, EntityController playerController) {
        this.state = state;
        this.playerController = playerController;

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

    public EntityController getPlayerController() {
        return this.playerController;
    }

    public List<EntityController> listEnemyControllers() {
        return new ArrayList<>(this.enemyControllers.values());
    }

    public void onEncounterStart(GameController gameController) {
    }

    public void simulateEncounter(GameController gameController) {
        while (isEncounterInProgress()) {
            executeEntityTurn(gameController, playerController);

            processEnemyControllers(gameController);
        }
    }

    public boolean isEncounterInProgress() {
        return getPlayerController().isAlive() && !enemyControllers.isEmpty();
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
        entityController.onTurnStart(this);
        entityController.simulateTurn(gameController, this);
        entityController.onTurnEnd(this);
    }

    public void onEncounterEnd(GameController gameController) {
        playerController.resetAndShuffleDecks(gameController.getState());
        if (playerController.isAlive()) {
            System.out.printf(
                    "Player won! HP: %s/%s%n",
                    playerController.getState().getHealthCurrent(),
                    playerController.getState().getHealthMax()
            );
        }
    }
}
