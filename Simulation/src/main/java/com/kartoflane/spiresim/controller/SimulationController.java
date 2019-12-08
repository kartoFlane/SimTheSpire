package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.state.*;
import com.kartoflane.spiresim.template.card.DefendTemplate;
import com.kartoflane.spiresim.template.card.StrikeTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimulationController implements StateController<SimulationState> {

    private final SimulationState state;


    public SimulationController(SimulationState state) {
        this.state = state;
    }

    public SimulationState getState() {
        return this.state;
    }

    public void start() throws Exception {
        GameState gameState = buildNewGame(new Random());
        GameController gameController = new GameController(gameState);

        gameController.simulateGame(this::buildNewEncounter);
    }

    private GameState buildNewGame(Random random) throws Exception {
        List<CardState> playerStartingDeck = Arrays.asList(
                CardStateFactory.build(StrikeTemplate.getInstance()),
                CardStateFactory.build(StrikeTemplate.getInstance()),
                CardStateFactory.build(StrikeTemplate.getInstance()),
                CardStateFactory.build(DefendTemplate.getInstance()),
                CardStateFactory.build(DefendTemplate.getInstance()),
                CardStateFactory.build(DefendTemplate.getInstance())
        );
        EntityState playerEntity = new EntityState(playerStartingDeck, "Player", 70);

        return new GameState(random, playerEntity);
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
