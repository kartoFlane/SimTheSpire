package com.kartoflane.spiresim.template;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import java.util.Collections;
import java.util.List;

public class TestEntityTemplate extends EntityTemplate<EntityState> {
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
    public int getHealth() {
        return 20;
    }

    @Override
    public int getEnergy() {
        return 0;
    }

    @Override
    public List<CardTemplate<? extends CardState>> getStartingDeck() {
        return Collections.emptyList();
    }

    @Override
    public Class<EntityState> getStateType() {
        return EntityState.class;
    }
}
