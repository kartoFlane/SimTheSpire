package com.kartoflane.spiresim.template.effect;

public interface EffectUpdateEvent {

    String getIdentifier();

    default boolean isEqual(EffectUpdateEvent o) {
        if (o == null) return false;
        return this.getIdentifier().equals(o.getIdentifier());
    }
}
