package com.kartoflane.spiresim.content.template.effect.base;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.effect.base.StackingEffectState;
import com.kartoflane.spiresim.template.effect.EffectTemplate;

/**
 * A template class representing a stackable effect (a buff or debuff) that can be applied to an entity.
 * Multiple applications of the same effect wil increase the number of stacks, amplifying the effect.
 */
@DeriveState
public abstract class StackingEffectTemplate<S extends StackingEffectState> extends EffectTemplate<S> {
    // Marker class.

    public final int getStacks() {
        return 0;
    }
}
