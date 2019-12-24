package com.kartoflane.spiresim.template.effect;

import com.kartoflane.spiresim.state.effect.StackingEffectState;

/**
 * A template class representing a stackable effect (a buff or debuff) that can be applied to an entity.
 * Multiple applications of the same effect wil increase the number of stacks, amplifying the effect.
 */
public abstract class StackingEffectTemplate<S extends StackingEffectState> extends EffectTemplate<S> {
    // Marker class.
}
