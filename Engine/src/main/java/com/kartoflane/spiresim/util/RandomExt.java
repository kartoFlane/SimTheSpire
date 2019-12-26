package com.kartoflane.spiresim.util;

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
}
