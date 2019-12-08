package com.kartoflane.spiresim.controller;

public class MutableCombatValue {
    private int amount;

    public MutableCombatValue() {
        amount = 0;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MutableCombatValue withAmount(int amount) {
        setAmount(amount);

        return this;
    }
}
