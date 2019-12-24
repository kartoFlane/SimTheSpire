package com.kartoflane.spiresim.template;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.card.CardTemplate;

public class TemplateFactory {
    private TemplateFactory() {
    }


    public static CardTemplate<? extends CardState> buildCardTemplate() {
        // TODO ?
        throw new UnsupportedOperationException("NYI");
    }
}
