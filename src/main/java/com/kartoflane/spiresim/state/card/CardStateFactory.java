package com.kartoflane.spiresim.state.card;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.template.card.CardTemplate;

public class CardStateFactory {

    public static <T extends CardTemplate<S>, S extends CardState> S build(T template) throws Exception {
        return template.getStateType()
                .getDeclaredConstructor(template.getClass())
                .newInstance(template);
    }
}
