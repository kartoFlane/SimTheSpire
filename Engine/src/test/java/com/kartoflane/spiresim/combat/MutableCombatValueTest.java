package com.kartoflane.spiresim.combat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MutableCombatValueTest {

    @Test
    public void defaultCombatValueShouldBeZero() {
        // Execute
        MutableCombatValue combatValue = new MutableCombatValue();

        // Check
        assertEquals(0, combatValue.getAmount());
    }

    @Test
    public void combatValueShouldBeMutable() {
        // Prepare
        final int expectedAmount = 5;
        MutableCombatValue combatValue = new MutableCombatValue();

        // Execute
        combatValue.setAmount(expectedAmount);

        // Check
        assertEquals(expectedAmount, combatValue.getAmount());
    }

    @Test
    public void combatValueAdd() {
        // Prepare
        final int initialAmount = 2;
        final int modifierAmount = 5;
        final int expectedAmount = initialAmount + modifierAmount;
        MutableCombatValue combatValue = new MutableCombatValue()
                .withAmount(initialAmount);

        // Execute
        combatValue.setAmount_Add(modifierAmount);

        // Check
        assertEquals(expectedAmount, combatValue.getAmount());
    }

    @Test
    public void combatValueSubtract() {
        // Prepare
        final int initialAmount = 2;
        final int modifierAmount = 5;
        final int expectedAmount = initialAmount - modifierAmount;
        MutableCombatValue combatValue = new MutableCombatValue()
                .withAmount(initialAmount);

        // Execute
        combatValue.setAmount_Subtract(modifierAmount);

        // Check
        assertEquals(expectedAmount, combatValue.getAmount());
    }

    @Test
    public void combatValueMultiply() {
        // Prepare
        final int initialAmount = 2;
        final int modifierAmount = 5;
        final int expectedAmount = initialAmount * modifierAmount;
        MutableCombatValue combatValue = new MutableCombatValue()
                .withAmount(initialAmount);

        // Execute
        combatValue.setAmount_Multiply(modifierAmount);

        // Check
        assertEquals(expectedAmount, combatValue.getAmount());
    }

    @Test
    public void combatValueDivide() {
        // Prepare
        final int initialAmount = 6;
        final int modifierAmount = 3;
        final int expectedAmount = initialAmount / modifierAmount;
        MutableCombatValue combatValue = new MutableCombatValue()
                .withAmount(initialAmount);

        // Execute
        combatValue.setAmount_Divide(modifierAmount);

        // Check
        assertEquals(expectedAmount, combatValue.getAmount());
    }
}
