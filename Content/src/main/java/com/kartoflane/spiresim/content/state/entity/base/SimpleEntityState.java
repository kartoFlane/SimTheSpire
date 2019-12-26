package com.kartoflane.spiresim.content.state.entity.base;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

public class SimpleEntityState extends EntityState {
    public SimpleEntityState(GameController gameController, EntityTemplate<SimpleEntityState> template) {
        super(gameController, template);
    }
}
