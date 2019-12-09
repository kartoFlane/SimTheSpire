package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.state.*;
import com.kartoflane.spiresim.template.entity.enemy.SlaverEntityTemplate;
import com.kartoflane.spiresim.template.entity.player.WarriorEntityTemplate;

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
        EntityState playerEntity = StateFactory.build(WarriorEntityTemplate.getInstance());

        return new GameState(random, playerEntity);
    }

    private EncounterState buildNewEncounter() {
        List<EntityState> enemyEntities = Arrays.asList(
                StateFactory.build(SlaverEntityTemplate.getInstance())
        );

        return new EncounterState(enemyEntities);
    }
}
