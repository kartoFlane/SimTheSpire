package com.kartoflane.spiresim.template.encounter;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.template.StateTemplate;
import com.kartoflane.spiresim.template.entity.EntityTemplate;

import java.util.List;

public abstract class EncounterTemplate<S extends EncounterState> implements StateTemplate<S> {

    public abstract List<EntityTemplate<?>> getStartingEnemies(GameController gameController);
}
