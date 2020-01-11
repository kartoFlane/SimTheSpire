package com.kartoflane.spiresim.content.template.encounter.act1;

import com.kartoflane.spiresim.content.template.entity.enemy.JawWormEntityTemplate;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.template.encounter.EncounterTemplate;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import java.util.Collections;
import java.util.List;

public class JawWormEncounterTemplate extends EncounterTemplate<EncounterState> {

    @Override
    public Class<? extends EncounterState> getStateType() {
        return EncounterState.class;
    }

    @Override
    public List<EntityTemplate<?>> getStartingEnemies(GameController gameController) {
        return Collections.singletonList(gameController.getTemplateInstance(JawWormEntityTemplate.class));
    }
}
