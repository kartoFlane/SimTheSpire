package com.kartoflane.spiresim.state;

import java.util.ArrayList;
import java.util.List;

/**
 * A state class representing a single encounter.
 */
public class EncounterState {

    private List<EntityState> enemyEntities = new ArrayList<>();
    private EntityState playerEntity;


    public EncounterState(EntityState playerEntity, List<EntityState> enemyEntities) {
        this.playerEntity = playerEntity;
        this.enemyEntities.addAll(enemyEntities);
    }

    public List<EntityState> getEnemyEntities() {
        return enemyEntities;
    }

    public EntityState getPlayerEntity() {
        return playerEntity;
    }
}
