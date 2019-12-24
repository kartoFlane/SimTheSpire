package com.kartoflane.spiresim.template.effect;

public interface EffectUpdateEvent {

    String getIdentifier();

    default boolean isEqual(Object o) {
        if (o == null) return false;
        if (!(o instanceof EffectUpdateEvent)) return false;
        EffectUpdateEvent other = (EffectUpdateEvent) o;
        return this.getIdentifier().equals(other.getIdentifier());
    }
}
