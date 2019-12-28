package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.util.RandomExt;

public class GameState {

    private RandomExt random;
    private EntityState playerState;
    private EncounterState currentEncounter;


    public GameState() {
        this(new RandomExt());
    }

    public GameState(RandomExt random) {
        this.random = random;
    }

    public void initialize(EntityState playerState) {
        if (this.playerState != null) {
            throw new IllegalStateException("Game state has already been initialized.");
        }
        this.playerState = playerState;
    }

    public RandomExt getRandom() {
        return random;
    }

    public EntityState getPlayerState() {
        return this.playerState;
    }

    public EncounterState getCurrentEncounter() {
        return currentEncounter;
    }

    public void setCurrentEncounter(EncounterState currentEncounter) {
        this.currentEncounter = currentEncounter;
    }
}
