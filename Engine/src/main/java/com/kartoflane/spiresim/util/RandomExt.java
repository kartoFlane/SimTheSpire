package com.kartoflane.spiresim.util;

import java.util.Arrays;
import java.util.List;

public class RandomExt extends java.util.Random {
    public RandomExt() {
    }

    public RandomExt(long seed) {
        super(seed);
    }

    /**
     * @return returns an integer value between min (inclusive) and max (exclusive)
     */
    public int nextInt(int min, int max) {
        return min + nextInt(max - min);
    }

    /**
     * @return returns a double value between min (inclusive) and max (inclusive)
     */
    public double nextDouble(double min, double max) {
        return min + nextDouble() * (max - min);
    }

    public int randomIndex(Object[] objects) {
        return nextInt(objects.length);
    }

    public int randomIndex(List<?> collection) {
        return nextInt(collection.size());
    }

    @SafeVarargs
    public final <T> T randomElement(T... objects) {
        return objects[randomIndex(objects)];
    }

    public <T> T randomElement(List<T> objects) {
        return objects.get(randomIndex(objects));
    }

    public int rollSameDice(int diceCount, int sideCount) {
        int[] input = new int[diceCount];
        Arrays.fill(input, sideCount);
        return rollDice(input);
    }

    public int rollDice(int... sideCounts) {
        int total = 0;
        for (int sideCount : sideCounts) {
            total += 1 + this.nextInt(0, sideCount);
        }

        return total;
    }

    public int rollSameExplodingDice(int diceCount, int sideCount) {
        int[] input = new int[diceCount];
        Arrays.fill(input, sideCount);
        return rollExplodingDice(input);
    }

    /**
     * Exploding Dice: roll again if you achieve the highest possible roll, and add to the total.
     */
    public int rollExplodingDice(int... sideCounts) {
        int total = 0;
        for (int sideCount : sideCounts) {
            int rollResult = 0;
            do {
                rollResult = 1 + this.nextInt(0, sideCount);
                total += rollResult;
            } while (rollResult == sideCount);
        }

        return total;
    }
}
