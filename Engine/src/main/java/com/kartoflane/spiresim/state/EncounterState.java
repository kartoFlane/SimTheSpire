package com.kartoflane.spiresim.state;

import java.util.ArrayList;
import java.util.List;

/**
 * A state class representing a single encounter.
 */
public class EncounterState {

    private List<EntityState> enemyEntities = new ArrayList<>();
    private int turnCount = 0;

    public EncounterState(List<EntityState> enemyEntities) {
        this.enemyEntities.addAll(enemyEntities);
    }

    public List<EntityState> getEnemyEntities() {
        return enemyEntities;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        if (turnCount < 0) {
            throw new IllegalArgumentException("Turn count must be greater than 0: " + turnCount);
        }
        this.turnCount = turnCount;
    }
}
