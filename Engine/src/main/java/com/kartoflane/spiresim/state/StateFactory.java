package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.template.StateTemplate;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;

public class StateFactory {

    private StateFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends StateTemplate<S>, S extends TemplatableState> S build(T template) {
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

            return constructor$.get().newInstance(template);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <T extends StateTemplate<S>, S extends TemplatableState> boolean isExactConstructor(Constructor<S> constructor, Class<T> templateClass) {
        return constructor.getParameters().length == 1 &&
                constructor.getParameters()[0].getType().equals(templateClass);
    }

    private static <T extends StateTemplate<S>, S extends TemplatableState> boolean isSuperclassConstructor(Constructor<S> constructor, Class<T> templateClass) {
        return constructor.getParameters().length == 1 &&
                constructor.getParameters()[0].getType().isAssignableFrom(templateClass);
    }
}
