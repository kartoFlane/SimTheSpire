package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.template.StateTemplate;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;

public class StateFactory {

    private StateFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends StateTemplate<S>, S extends TemplatableState> S build(GameController gameController, T template) {
        try {
            Constructor<S>[] constructors = (Constructor<S>[]) template.getStateType().getDeclaredConstructors();

            Optional<Constructor<S>> constructor$ = Arrays.stream(constructors)
                    .filter(constructor -> isExactConstructor(constructor, template.getClass()))
                    .findFirst();

            if (!constructor$.isPresent()) {
                constructor$ = Arrays.stream(constructors)
                        .filter(constructor -> isSuperclassConstructor(constructor, template.getClass()))
                        .findFirst();
            }

            if (!constructor$.isPresent()) {
                throw new NoSuchMethodException(template.getStateType().getName() + " has no constructor for parameters: " + template.getClass().getName());
            }

            return constructor$.get().newInstance(gameController, template);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends StateTemplate<S>, S extends TemplatableState> boolean isExactConstructor(Constructor<S> constructor, Class<T> templateClass) {
        return constructor.getParameters().length == 2 &&
                constructor.getParameters()[0].getType().equals(GameController.class) &&
                constructor.getParameters()[1].getType().equals(templateClass);
    }

    private static <T extends StateTemplate<S>, S extends TemplatableState> boolean isSuperclassConstructor(Constructor<S> constructor, Class<T> templateClass) {
        return constructor.getParameters().length == 2 &&
                constructor.getParameters()[0].getType().equals(GameController.class) &&
                constructor.getParameters()[1].getType().isAssignableFrom(templateClass);
    }
}
