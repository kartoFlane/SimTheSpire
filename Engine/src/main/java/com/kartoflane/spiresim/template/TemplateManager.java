package com.kartoflane.spiresim.template;

import com.kartoflane.spiresim.state.TemplatableState;

import java.util.HashMap;
import java.util.Map;

public class TemplateManager {
    @SuppressWarnings("rawtypes")
    private Map<Class<? extends StateTemplate>, StateTemplate> templatesMap = new HashMap<>();


    @SuppressWarnings("unchecked")
    public <T extends StateTemplate<S>, S extends TemplatableState> T getInstance(Class<T> templateClass) {
        if (templateClass == null) {
            throw new IllegalArgumentException("TemplateClass must not be null.");
        }

        T template = (T) templatesMap.get(templateClass);
        if (template == null) {
            template = newInstance(templateClass);
            templatesMap.put(templateClass, template);
        }

        return template;
    }

    private <T extends StateTemplate<S>, S extends TemplatableState> T newInstance(Class<T> templateClass) {
        try {
            return templateClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
