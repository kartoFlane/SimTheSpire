package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.template.CardTemplate;

public class CardStateFactory {

    public static <T extends CardTemplate<S>, S extends CardState> S build(T template) throws Exception {
        return template.getStateType()
                .getDeclaredConstructor(template.getClass())
                .newInstance(template);
    }
}
