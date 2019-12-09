package com.kartoflane.spiresim.template.entity;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.template.StateTemplate;
import com.kartoflane.spiresim.template.card.CardTemplate;

import java.util.List;

public interface EntityTemplate<S extends EntityState> extends StateTemplate<S> {
    String getName();

    int getHealth();

    int getEnergy();

    List<CardTemplate<? extends CardState>> getStartingDeck();
}
