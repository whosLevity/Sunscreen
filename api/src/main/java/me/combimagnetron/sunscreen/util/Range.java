package me.combimagnetron.sunscreen.util;

public class Range<T extends Comparable<T>> {
    private final T min;
    private final T max;

    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T min() {
        return min;
    }

    public T max() {
        return max;
    }

    public boolean in(T value) {
        return (min == null || min.compareTo(value) <= 0) && (max == null || max.compareTo(value) >= 0);
    }

}
