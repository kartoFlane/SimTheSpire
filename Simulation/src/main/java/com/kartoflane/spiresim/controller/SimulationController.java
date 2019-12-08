package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.state.*;
import com.kartoflane.spiresim.template.card.DefendTemplate;
import com.kartoflane.spiresim.template.card.StrikeTemplate;

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

        gameController.simulateGame();
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
}
