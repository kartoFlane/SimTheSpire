package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.ai.PlayerAIController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.card.CardStateFactory;
import com.kartoflane.spiresim.template.card.concrete.DefendTemplate;
import com.kartoflane.spiresim.template.card.concrete.StrikeTemplate;

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

    public EntityController getPlayerController() {
        return this.playerController;
    }

    public void simulateGame() throws Exception {
        while (isGameInProgress()) {
            EncounterState encounterState = buildNewEncounter();
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

    private EncounterState buildNewEncounter() throws Exception {
        List<EntityState> enemyEntities = new ArrayList<>();
        List<CardState> enemyStartingDeck = Arrays.asList(
                CardStateFactory.build(StrikeTemplate.getInstance()),
                CardStateFactory.build(StrikeTemplate.getInstance()),
                CardStateFactory.build(DefendTemplate.getInstance())
        );
        EntityState enemyEntity = new EntityState(enemyStartingDeck, "Enemy", 25);
        enemyEntities.add(enemyEntity);

        return new EncounterState(enemyEntities);
    }
}
