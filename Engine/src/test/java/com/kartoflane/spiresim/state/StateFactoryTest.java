package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.test.controller.TestGameController;
import com.kartoflane.spiresim.test.template.card.TestCardTemplate;
import com.kartoflane.spiresim.test.template.effect.TestEffectTemplate;
import com.kartoflane.spiresim.test.template.entity.TestEntityTemplate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StateFactoryTest {
    private GameController gameController;


    @Before
    public void prepareMockEntities() {
        gameController = TestGameController.setupTestEnvironment(TestEntityTemplate.getInstance());
    }

    @Test
    public void shouldCreateCorrectStateTypeFromTemplate() {
        // Prepare
        TestCardTemplate cardTemplate = TestCardTemplate.getInstance();
        TestEffectTemplate effectTemplate = TestEffectTemplate.getInstance();
        TestEntityTemplate entityTemplate = TestEntityTemplate.getInstance();

        // Execute
        TemplatableState cardState = StateFactory.build(gameController, cardTemplate);
        TemplatableState effectState = StateFactory.build(gameController, effectTemplate);
        TemplatableState entityState = StateFactory.build(gameController, entityTemplate);

        // Check
        assertTrue(CardState.class.isAssignableFrom(cardState.getClass()));
        assertTrue(EffectState.class.isAssignableFrom(effectState.getClass()));
        assertTrue(EntityState.class.isAssignableFrom(entityState.getClass()));
    }
}
