package com.kartoflane.spiresim.combat;

public interface MutableCombatValueEvent {

    String getIdentifier();

    default boolean isEqual(Object o) {
        if (o == null) return false;
        if (!(o instanceof MutableCombatValueEvent)) return false;
        MutableCombatValueEvent other = (MutableCombatValueEvent) o;
        return this.getIdentifier().equals(other.getIdentifier());
    }
}
