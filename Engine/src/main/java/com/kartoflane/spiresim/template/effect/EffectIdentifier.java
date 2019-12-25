package com.kartoflane.spiresim.template.effect;

public interface EffectIdentifier {
    String getIdentifier();

    default boolean isEqual(EffectIdentifier o) {
        if (o == null) return false;
        return this.getIdentifier().equals(o.getIdentifier());
    }
}
