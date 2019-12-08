package com.kartoflane.spiresim.template;

import com.kartoflane.spiresim.state.TemplatableState;

public interface StateTemplate<S extends TemplatableState> {
    Class<S> getStateType();
}
