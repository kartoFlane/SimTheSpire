package com.kartoflane.spiresim.util;

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
}
