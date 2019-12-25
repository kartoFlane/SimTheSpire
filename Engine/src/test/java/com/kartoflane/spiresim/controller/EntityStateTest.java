package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.controller.ai.EnemyAIController;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.TestEntityTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityStateTest {
    private EntityState state;
    private EntityController controller;
    private EncounterController encounterController;


    @Before
    public void prepareMockEntities() {
        state = StateFactory.build(TestEntityTemplate.getInstance());
        controller = new EntityController(state, new EnemyAIController());
        encounterController = new EncounterController(new EncounterState(Collections.emptyList()));
    }

    @Test
    public void damagedEntityWithoutBlockShouldLoseHealth() {
        // Prepare
        final int expectedDamage = 3;

        // Execute
        controller.applyDamage(encounterController, new MutableCombatValue().withAmount(expectedDamage));

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
        controller.applyDamage(encounterController, new MutableCombatValue().withAmount(dealtDamage));

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
        controller.applyDamage(encounterController, new MutableCombatValue().withAmount(dealtDamage));

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
        controller.applyDamage(encounterController, new MutableCombatValue().withAmount(dealtDamage));

        // Check
        assertEquals(state.getHealthMax() - expectedDamage, state.getHealthCurrent());
        assertEquals(Math.max(0, startArmor - dealtDamage), state.getArmorCurrent());
    }

}
