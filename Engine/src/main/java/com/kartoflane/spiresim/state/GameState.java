package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.TemplateManager;
import com.kartoflane.spiresim.util.RandomExt;

public class GameState {

    private RandomExt random;
    private EntityState playerState;
    private EncounterState currentEncounter;
    private TemplateManager templateManager;


    public GameState() {
        this(new TemplateManager(), new RandomExt());
    }

    public GameState(TemplateManager templateManager) {
        this(templateManager, new RandomExt());
    }

    public GameState(RandomExt random) {
        this(new TemplateManager(), random);
    }

    public GameState(TemplateManager templateManager, RandomExt random) {
        this.templateManager = templateManager;
        this.random = random;
    }

    public void initialize(EntityState playerState) {
        if (this.playerState != null) {
            throw new IllegalStateException("Game state has already been initialized.");
        }
        this.playerState = playerState;
    }

    public TemplateManager getTemplateManager() {
        return templateManager;
    }

    public RandomExt getRandom() {
        return random;
    }

    public EntityState getPlayerState() {
        return playerState;
    }

    public EncounterState getCurrentEncounter() {
        return currentEncounter;
    }

    public void setCurrentEncounter(EncounterState currentEncounter) {
        this.currentEncounter = currentEncounter;
    }
}
