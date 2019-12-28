package com.kartoflane.spiresim.test.template.encounter;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.template.encounter.EncounterTemplate;
import com.kartoflane.spiresim.template.entity.EntityTemplate;
import com.kartoflane.spiresim.test.template.entity.TestEntityTemplate;

import java.util.Collections;
import java.util.List;

public class TestEncounterTemplate extends EncounterTemplate<EncounterState> {
    private static TestEncounterTemplate INSTANCE;


    public static TestEncounterTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestEncounterTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<? extends EncounterState> getStateType() {
        return EncounterState.class;
    }

    @Override
    public List<EntityTemplate<?>> getStartingEnemies(GameController gameController) {
        return Collections.singletonList(TestEntityTemplate.getInstance());
    }
}
