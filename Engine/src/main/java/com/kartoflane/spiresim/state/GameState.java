package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.state.entity.EntityState;

import java.util.Random;

public class GameState {

    private Random random;
    private EntityState playerState;


    public GameState(EntityState playerState) {
        this(new Random(), playerState);
    }

    public GameState(Random random, EntityState playerState) {
        this.random = random;
        this.playerState = playerState;
    }

    public Random getRandom() {
        return random;
    }

    public EntityState getPlayerState() {
        return this.playerState;
    }
}
