package com.kartoflane.spiresim.report;

import java.util.List;
import java.util.stream.Collectors;

public class SampleStatistics {

    protected int count;
    protected double min;
    protected double max;
    protected double mean;
    protected double median;
    protected double variance;
    protected double standardDeviation;


    public static SampleStatistics ofDoubles(List<Double> samplesList) {
        List<Double> sortedSamplesList = samplesList.stream()
                .sorted()
                .collect(Collectors.toList());

        return new SampleStatistics(sortedSamplesList);
    }

    public static SampleStatistics ofIntegers(List<Integer> samplesList) {
        List<Double> sortedSamplesList = samplesList.stream()
                .sorted()
                .map(Double::valueOf)
                .collect(Collectors.toList());

        return new SampleStatistics(sortedSamplesList);
    }

    protected SampleStatistics(List<Double> sortedSamplesList) {
        this.count = sortedSamplesList.size();
        this.min = sortedSamplesList.get(0);
        this.max = sortedSamplesList.get(this.count - 1);
        this.median = computeMedian(sortedSamplesList);
        this.mean = computeMean(sortedSamplesList);
        this.variance = computeVariance(sortedSamplesList, this.mean);
        this.standardDeviation = computeStandardDeviation(this.variance);
    }

    public int getCount() {
        return count;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMedian() {
        return median;
    }

    public final double getAverage() {
        return getMean();
    }

    public double getMean() {
        return mean;
    }

    public double getVariance() {
        return variance;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    protected double computeMedian(List<Double> sortedSamplesList) {
        final int halfIndex = sortedSamplesList.size() / 2;

        if (sortedSamplesList.size() % 2 == 1) {
            return sortedSamplesList.get(halfIndex);
        } else {
            double lower = sortedSamplesList.get(halfIndex - 1);
            double upper = sortedSamplesList.get(halfIndex);
            return (lower + upper) / 2.0;
        }
    }

    protected double computeMean(List<Double> sortedSamplesList) {
        double sum = 0;
        for (Double d : sortedSamplesList) {
            sum += d;
        }

        return sum / sortedSamplesList.size();
    }

    protected double computeVariance(List<Double> sortedSamplesList, double mean) {
        double variance = 0;
        for (Double d : sortedSamplesList) {
            variance += Math.pow(d - mean, 2);
        }

        return variance / sortedSamplesList.size();
    }

    protected double computeStandardDeviation(double variance) {
        return Math.sqrt(variance);
    }
}
