package com.kartoflane.spiresim.template.entity;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.template.StateTemplate;
import com.kartoflane.spiresim.template.card.CardTemplate;

import java.util.List;

public abstract class EntityTemplate<S extends EntityState> implements StateTemplate<S> {

    public abstract String getName();

    public abstract int getHealth();

    public abstract int getEnergy();

    public abstract List<CardTemplate<? extends CardState>> getStartingDeck();
}
