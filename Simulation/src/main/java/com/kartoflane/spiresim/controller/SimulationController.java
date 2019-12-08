package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.state.*;
import com.kartoflane.spiresim.template.card.BashCardTemplate;
import com.kartoflane.spiresim.template.card.DefendCardTemplate;
import com.kartoflane.spiresim.template.card.StrikeCardTemplate;

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

    public void start() {
        GameState gameState = buildNewGame(new Random());
        GameController gameController = new GameController(gameState);

        gameController.simulateGame(this::buildNewEncounter);
    }

    private GameState buildNewGame(Random random) {
        List<CardState> playerStartingDeck = Arrays.asList(
                StateFactory.build(StrikeCardTemplate.getInstance()),
                StateFactory.build(StrikeCardTemplate.getInstance()),
                StateFactory.build(StrikeCardTemplate.getInstance()),
                StateFactory.build(DefendCardTemplate.getInstance()),
                StateFactory.build(DefendCardTemplate.getInstance()),
                StateFactory.build(DefendCardTemplate.getInstance()),
                StateFactory.build(BashCardTemplate.getInstance())
        );
        EntityState playerEntity = new EntityState(playerStartingDeck, "Player", 70);

        return new GameState(random, playerEntity);
    }

    private EncounterState buildNewEncounter() {
        List<EntityState> enemyEntities = new ArrayList<>();
        List<CardState> enemyStartingDeck = Arrays.asList(
                StateFactory.build(StrikeCardTemplate.getInstance()),
                StateFactory.build(StrikeCardTemplate.getInstance()),
                StateFactory.build(DefendCardTemplate.getInstance())
        );
        EntityState enemyEntity = new EntityState(enemyStartingDeck, "Enemy", 25);
        enemyEntities.add(enemyEntity);

        return new EncounterState(enemyEntities);
    }
}
