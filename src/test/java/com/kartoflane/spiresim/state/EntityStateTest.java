package com.kartoflane.spiresim.state;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class EntityStateTest {

    @Test
    public void damagedEntityWithoutBlockShouldLoseHealth() {
        // Prepare
        EntityState entityState = new EntityState(Collections.emptyList(), "TestEntity", 10);

        // Execute
        entityState.applyDamage(3);

        // Check
        assertEquals(7, entityState.getHealthCurrent());
    }

    @Test
    public void damagedEntityWithSufficientBlockShouldNotLoseHealth() {
        // Prepare
        EntityState entityState = new EntityState(Collections.emptyList(), "TestEntity", 10);
        entityState.setArmorCurrent(5);

        // Execute
        entityState.applyDamage(3);

        // Check
        assertEquals(10, entityState.getHealthCurrent());
        assertEquals(2, entityState.getArmorCurrent());
    }

    @Test
    public void damagedEntityWithPerfectBlockShouldNotLoseHealth() {
        // Prepare
        EntityState entityState = new EntityState(Collections.emptyList(), "TestEntity", 10);
        entityState.setArmorCurrent(5);

        // Execute
        entityState.applyDamage(5);

        // Check
        assertEquals(10, entityState.getHealthCurrent());
        assertEquals(0, entityState.getArmorCurrent());
    }

    @Test
    public void damagedEntityWithInsufficientBlockShouldLoseHealth() {
        // Prepare
        EntityState entityState = new EntityState(Collections.emptyList(), "TestEntity", 10);
        entityState.setArmorCurrent(2);

        // Execute
        entityState.applyDamage(5);

        // Check
        assertEquals(7, entityState.getHealthCurrent());
        assertEquals(0, entityState.getArmorCurrent());
    }
}
