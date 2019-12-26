package com.kartoflane.spiresim.content.template.entity.base;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

public abstract class EnemyEntityTemplate<S extends EntityState> extends EntityTemplate<S> {

    @Override
    public int getEnergy(GameController gameController) {
        return 1;
    }
}
