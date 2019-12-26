package com.kartoflane.spiresim.template.entity;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.StateTemplate;
import com.kartoflane.spiresim.template.card.CardTemplate;

import java.util.List;

public abstract class EntityTemplate<S extends EntityState> implements StateTemplate<S> {

    public abstract String getName();

    public abstract int getHealth(GameController gameController);

    public abstract int getEnergy(GameController gameController);

    public abstract List<CardTemplate<? extends CardState>> getStartingDeck();
}
