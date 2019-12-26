package com.kartoflane.spiresim.combat;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MutableCombatValueEventTest {

    @Test
    public void sameEventsShouldBeEqual() {
        // Prepare
        MutableCombatValueEvent a = MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_FLAT;
        MutableCombatValueEvent b = MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_FLAT;

        // Execute
        boolean result = a.isEqual(b);

        // Check
        assertTrue(result);
    }

    @Test
    public void differentEventsShouldNotBeEqual() {
        // Prepare
        MutableCombatValueEvent a = MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_FLAT;
        MutableCombatValueEvent b = MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_PERCENT;

        // Execute
        boolean result = a.isEqual(b);

        // Check
        assertFalse(result);
    }

    @Test
    public void identifierShouldBeTiedToEnum() {
        // Prepare
        MutableCombatValueEvent a = MutableCombatValueEvents.ENTITY_INCOMING_ARMOR_FLAT;

        // Execute
        MutableCombatValueEvent b = MutableCombatValueEvents.identifierFor(a.getIdentifier());

        // Check
        assertTrue(a.isEqual(b));
    }
}
