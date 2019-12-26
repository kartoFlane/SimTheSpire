package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.test.controller.TestGameController;
import com.kartoflane.spiresim.test.template.TestEntityTemplate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityDamageTest {
    private GameController gameController;
    private EntityController controller;
    private EntityState state;


    @Before
    public void prepareMockEntities() {
        gameController = TestGameController.setupTestEnvironment(TestEntityTemplate.getInstance());

        controller = gameController.getPlayerController();
        state = gameController.getPlayerController().getState();
    }

    @Test
    public void damagedEntityWithoutBlockShouldLoseHealth() {
        // Prepare
        final int expectedDamage = 3;

        // Execute
        controller.applyDamage(gameController, new MutableCombatValue().withAmount(expectedDamage));

        // Check
        assertEquals(state.getHealthMax() - expectedDamage, state.getHealthCurrent());
    }

    @Test
    public void damagedEntityWithSufficientBlockShouldNotLoseHealth() {
        // Prepare
        final int startArmor = 5;
        final int dealtDamage = 3;
        state.setArmorCurrent(startArmor);

        assertTrue(startArmor > dealtDamage);

        // Execute
        controller.applyDamage(gameController, new MutableCombatValue().withAmount(dealtDamage));

        // Check
        assertEquals(state.getHealthMax(), state.getHealthCurrent());
        assertEquals(startArmor - dealtDamage, state.getArmorCurrent());
    }

    @Test
    public void damagedEntityWithPerfectBlockShouldNotLoseHealth() {
        // Prepare
        final int startArmor = 5;
        final int dealtDamage = 5;
        state.setArmorCurrent(startArmor);

        assertEquals(startArmor, dealtDamage);

        // Execute
        controller.applyDamage(gameController, new MutableCombatValue().withAmount(dealtDamage));

        // Check
        assertEquals(state.getHealthMax(), state.getHealthCurrent());
        assertEquals(startArmor - dealtDamage, state.getArmorCurrent());
    }

    @Test
    public void damagedEntityWithInsufficientBlockShouldLoseHealth() {
        // Prepare
        final int startArmor = 2;
        final int dealtDamage = 5;
        final int expectedDamage = dealtDamage - startArmor;
        state.setArmorCurrent(startArmor);

        assertTrue(startArmor < dealtDamage);

        // Execute
        controller.applyDamage(gameController, new MutableCombatValue().withAmount(dealtDamage));

        // Check
        assertEquals(state.getHealthMax() - expectedDamage, state.getHealthCurrent());
        assertEquals(Math.max(0, startArmor - dealtDamage), state.getArmorCurrent());
    }

    @Test
    public void healedEntityWithMissingHealthShouldGainHealth() {
        // Prepare
        final int missingHealth = 10;
        final int healing = 5;
        final int expectedHealth = state.getHealthMax() - missingHealth + healing;
        state.setHealthCurrent(state.getHealthMax() - missingHealth);

        assertTrue(healing < missingHealth);

        // Execute
        controller.applyHeal(gameController, new MutableCombatValue().withAmount(healing));

        // Check
        assertEquals(expectedHealth, state.getHealthCurrent());
    }

    @Test
    public void healedEntityWithFullHealthShouldNotGainHealth() {
        // Prepare
        final int healing = 5;
        final int expectedHealth = state.getHealthMax();

        // Execute
        controller.applyHeal(gameController, new MutableCombatValue().withAmount(healing));

        // Check
        assertEquals(expectedHealth, state.getHealthCurrent());
    }
}
