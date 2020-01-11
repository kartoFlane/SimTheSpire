package com.kartoflane.spiresim.content.template.encounter.act1;

import com.kartoflane.spiresim.content.template.entity.enemy.GreenLouseEntityTemplate;
import com.kartoflane.spiresim.content.template.entity.enemy.RedLouseEntityTemplate;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.template.encounter.EncounterTemplate;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import java.util.ArrayList;
import java.util.List;

public class Louses2EncounterTemplate extends EncounterTemplate<EncounterState> {

    @Override
    public Class<? extends EncounterState> getStateType() {
        return EncounterState.class;
    }

    @Override
    public List<EntityTemplate<?>> getStartingEnemies(GameController gameController) {
        EntityTemplate<?>[] enemyTypes = {
                gameController.getTemplateInstance(RedLouseEntityTemplate.class),
                gameController.getTemplateInstance(GreenLouseEntityTemplate.class)
        };
        final int startingEnemyCount = 2;

        List<EntityTemplate<?>> startingEnemies = new ArrayList<>(startingEnemyCount);
        while (startingEnemies.size() < startingEnemyCount) {
            startingEnemies.add(gameController.getState().getRandom().randomElement(enemyTypes));
        }

        return startingEnemies;
    }
}
