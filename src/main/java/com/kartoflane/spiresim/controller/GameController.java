package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.PlayerAIController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.card.concrete.AttackCardState;
import com.kartoflane.spiresim.state.card.concrete.DefendCardState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void simulateGame() {
        while (isGameInProgress()) {
            EncounterState encounterState = buildNewEncounter();
            EncounterController encounterController = new EncounterController(encounterState, this.playerController);

            encounterController.onEncounterStart(this);
            encounterController.simulateEncounter(this);
            encounterController.onEncounterEnd(this);
        }

        System.out.println("Game over!");
    }

    public boolean isGameInProgress() {
        return this.playerController.isAlive();
    }

    private EncounterState buildNewEncounter() {
        List<EntityState> enemyEntities = new ArrayList<>();
        List<CardState> enemyStartingDeck = Arrays.asList(
                new AttackCardState(),
                new AttackCardState(),
                new DefendCardState()
        );
        EntityState enemyEntity = new EntityState(enemyStartingDeck, "Enemy", 25);
        enemyEntities.add(enemyEntity);

        return new EncounterState(playerController.getState(), enemyEntities);
    }
}
