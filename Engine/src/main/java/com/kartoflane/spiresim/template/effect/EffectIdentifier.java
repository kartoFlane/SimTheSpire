package com.kartoflane.spiresim.template.effect;

public interface EffectIdentifier {
    String getIdentifier();

    default boolean isEqual(Object o) {
        if (o == null) return false;
        if (!(o instanceof EffectIdentifier)) return false;
        EffectIdentifier other = (EffectIdentifier) o;
        return this.getIdentifier().equals(other.getIdentifier());
    }
}
