package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.SimulationState;
import com.kartoflane.spiresim.state.card.concrete.AttackCardState;
import com.kartoflane.spiresim.state.card.concrete.DefendCardState;

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

        gameController.simulateGame();
    }

    private GameState buildNewGame(Random random) {
        List<CardState> playerStartingDeck = Arrays.asList(
                new AttackCardState(),
                new AttackCardState(),
                new AttackCardState(),
                new DefendCardState(),
                new DefendCardState(),
                new DefendCardState()
        );
        EntityState playerEntity = new EntityState(playerStartingDeck, "Player", 70);

        return new GameState(random, playerEntity);
    }
}
