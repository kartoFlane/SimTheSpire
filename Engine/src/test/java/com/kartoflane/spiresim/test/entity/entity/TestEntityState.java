package com.kartoflane.spiresim.test.entity.entity;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.test.template.entity.TestEntityTemplate;

public class TestEntityState extends EntityState {
    public TestEntityState(GameController gameController, TestEntityTemplate template) {
        super(gameController, template);
    }
}
