package com.kartoflane.spiresim.test.template.entity;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.entity.EntityTemplate;
import com.kartoflane.spiresim.test.entity.entity.TestEntityState;

import java.util.Collections;
import java.util.List;

public class TestEntityTemplate extends EntityTemplate<TestEntityState> {
    private static TestEntityTemplate INSTANCE;


    public static TestEntityTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestEntityTemplate();
        }
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Test Dummy";
    }

    @Override
    public int getHealth(GameController gameController) {
        return 20;
    }

    @Override
    public int getEnergy(GameController gameController) {
        return 3;
    }

    @Override
    public List<CardTemplate<? extends CardState>> getStartingDeck(GameController gameController) {
        return Collections.emptyList();
    }

    @Override
    public Class<TestEntityState> getStateType() {
        return TestEntityState.class;
    }
}
