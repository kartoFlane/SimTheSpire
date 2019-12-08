package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.MutableCombatValue;
import com.kartoflane.spiresim.controller.ai.EnemyAIController;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.EntityState;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class EntityStateTest {

    private EntityState state;
    private EntityController controller;
    private EncounterController encounterController;


    @Before
    public void prepareMockEntities() {
        state = new EntityState(Collections.emptyList(), "TestEntity", 10);
        controller = new EntityController(state, new EnemyAIController());
        encounterController = new EncounterController(new EncounterState(Collections.emptyList()));
    }

    @Test
    public void damagedEntityWithoutBlockShouldLoseHealth() {
        // Execute
        controller.applyDamage(encounterController, new MutableCombatValue().withAmount(3));

        // Check
        assertEquals(7, state.getHealthCurrent());
    }

    @Test
    public void damagedEntityWithSufficientBlockShouldNotLoseHealth() {
        // Prepare
        state.setArmorCurrent(5);

        // Execute
        controller.applyDamage(encounterController, new MutableCombatValue().withAmount(3));

        // Check
        assertEquals(10, state.getHealthCurrent());
        assertEquals(2, state.getArmorCurrent());
    }

    @Test
    public void damagedEntityWithPerfectBlockShouldNotLoseHealth() {
        // Prepare
        state.setArmorCurrent(5);

        // Execute
        controller.applyDamage(encounterController, new MutableCombatValue().withAmount(5));

        // Check
        assertEquals(10, state.getHealthCurrent());
        assertEquals(0, state.getArmorCurrent());
    }

    @Test
    public void damagedEntityWithInsufficientBlockShouldLoseHealth() {
        // Prepare
        state.setArmorCurrent(2);

        // Execute
        controller.applyDamage(encounterController, new MutableCombatValue().withAmount(5));

        // Check
        assertEquals(7, state.getHealthCurrent());
        assertEquals(0, state.getArmorCurrent());
    }
}
