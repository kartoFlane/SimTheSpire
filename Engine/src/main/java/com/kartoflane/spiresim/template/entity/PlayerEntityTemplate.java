package com.kartoflane.spiresim.template.entity;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.entity.EntityState;

public abstract class PlayerEntityTemplate<S extends EntityState> extends EntityTemplate<S> {

    @Override
    public int getEnergy(GameController gameController) {
        return 3;
    }
}
