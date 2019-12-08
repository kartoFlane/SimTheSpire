package com.kartoflane.spiresim.util;

@FunctionalInterface
public interface ExceptionalSupplier<T> {
    T get() throws Exception;
}
