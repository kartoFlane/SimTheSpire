package com.kartoflane.spiresim.state;

/**
 * A state class representing an effect (a buff or debuff) that can be applied to an entity.
 */
public abstract class EffectState {

    private int stackCounter;


    public int getStackCounter() {
        return stackCounter;
    }

    public void setStackCounter(int stackCounter) {
        this.stackCounter = stackCounter;
    }

    public abstract String getEffectIdentifier();

    /**
     * Actions to perform when the effect is first applied to an entity.
     */
    public abstract void onApply(EncounterState encounterState, EntityState entityState, EffectState effectState);

    /**
     * Actions to perform when the effect is removed from the entity.
     */
    public abstract void onRemove(EncounterState encounterState, EntityState entityState);

    /**
     * Actions to perform during update steps.
     */
    public abstract void onUpdate(EncounterState encounterState, EntityState entityState, UpdateEvent updateEvent);

    public enum UpdateEvent {
        TURN_START,
        TURN_END;
    }
}
