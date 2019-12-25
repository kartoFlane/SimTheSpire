package com.kartoflane.spiresim.template.entity;

import com.kartoflane.spiresim.state.entity.EntityState;

public abstract class EnemyEntityTemplate<S extends EntityState> extends EntityTemplate<S> {

    @Override
    public int getEnergy() {
        return 1;
    }
}
