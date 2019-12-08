package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.template.StateTemplate;

public class StateFactory {

    public static <T extends StateTemplate<S>, S extends TemplatableState> S build(T template) {
        try {
            return template.getStateType()
                    .getDeclaredConstructor(template.getClass())
                    .newInstance(template);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
