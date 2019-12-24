package com.kartoflane.spiresim.template.entity;

import com.kartoflane.spiresim.state.EntityState;

public abstract class EnemyEntityTemplate<S extends EntityState> extends EntityTemplate<S> {

    @Override
    public int getEnergy() {
        return 1;
    }
}
