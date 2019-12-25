package com.kartoflane.spiresim.combat;

public interface MutableCombatValueEvent {

    String getIdentifier();

    default boolean isEqual(MutableCombatValueEvent o) {
        if (o == null) return false;
        return this.getIdentifier().equals(o.getIdentifier());
    }
}
