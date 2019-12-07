package com.kartoflane.spiresim.state;

import java.util.ArrayList;
import java.util.List;

/**
 * A state class representing a single encounter.
 */
public class EncounterState {

    private List<EntityState> enemyEntities = new ArrayList<>();


    public EncounterState(List<EntityState> enemyEntities) {
        this.enemyEntities.addAll(enemyEntities);
    }

    public List<EntityState> getEnemyEntities() {
        return enemyEntities;
    }
}
