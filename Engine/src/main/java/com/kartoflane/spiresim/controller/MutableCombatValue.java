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

    public void setAmount_Add(double amount) {
        this.amount += amount;
    }

    public MutableCombatValue withAmount_Add(double amount) {
        setAmount_Add(amount);

        return this;
    }

    public void setAmount_Subtract(double amount) {
        this.amount -= amount;
    }

    public MutableCombatValue withAmount_Subtract(double amount) {
        setAmount_Subtract(amount);

        return this;
    }

    public void setAmount_Multiply(double amount) {
        this.amount *= amount;
    }

    public MutableCombatValue withAmount_Multiply(double amount) {
        setAmount_Multiply(amount);

        return this;
    }

    public void setAmount_Divide(double amount) {
        this.amount /= amount;
    }

    public MutableCombatValue withAmount_Divide(double amount) {
        setAmount_Divide(amount);

        return this;
    }
}
