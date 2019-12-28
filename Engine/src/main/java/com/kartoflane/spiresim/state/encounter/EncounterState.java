package com.kartoflane.spiresim.state.encounter;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.TemplatableState;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.encounter.EncounterTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A state class representing a single encounter.
 */
public class EncounterState extends TemplatableState {

    private List<EntityState> enemyEntities = new ArrayList<>();
    private int turnCount = 0;


    public EncounterState(GameController gameController, EncounterTemplate<? extends EncounterState> template) {
        super(template);

        enemyEntities.addAll(
                template.getStartingEnemies(gameController).stream()
                        .map(entityTemplate -> StateFactory.build(gameController, entityTemplate))
                        .collect(Collectors.toList())
        );
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
