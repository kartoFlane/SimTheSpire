package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.template.StateTemplate;

public abstract class TemplatableState {

    private final StateTemplate<? extends TemplatableState> template;

    TemplatableState(StateTemplate<? extends TemplatableState> template) {
        this.template = template;
    }

    public StateTemplate<? extends TemplatableState> getTemplate() {
        return template;
    }
}
