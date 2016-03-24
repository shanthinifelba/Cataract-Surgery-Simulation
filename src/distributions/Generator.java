package unbc.ca.distributed.distributions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.JavaRNG;

public abstract class Generator {

    protected abstract NumberGenerator<?> createValueGenerator(Random paramRandom);
    private Map<Double, Double> generatedValues = new LinkedHashMap<>();
    private int count = 0;
    private ArrayList<Double> numbers = new ArrayList<>();

    private double min = 1.7976931348623157E+308D;
    private double max = 4.9E-324D;

    private NumberGenerator generator;

    public void setGenerator(Random rng) {
        generator = createValueGenerator(new JavaRNG());
    }

    public Map<Double, Double> getGeneratedValues() {
        if (isDiscrete()) {
            double sum = 0.0D;
            Map<Double, Double> values = new LinkedHashMap<>();
            values.putAll(generatedValues);
            for (Double key : values.keySet()) {
                Double value = (Double) values.get(key);
                values.put(key, Double.valueOf(value.doubleValue() / count));
                sum += value.doubleValue();
            }
            assert (Math.round(sum) == count) : ("Wrong total: " + sum);
            return values;
        } else {
            Map values = doQuantization(max, min, numbers);
            double sum = 0.0D;
            for (Object key : values.keySet()) {
                Double value = (Double) values.get(key);
                values.put(key, Double.valueOf(value.doubleValue() / count));
                sum += value.doubleValue();
            }
            assert (Math.round(sum) == count) : ("Wrong total: " + sum);
            return values;             
        }
    }

    public int generate() {
        return isDiscrete() ? generateDiscreteValues() : generateContinuousValues();
    }

    private int generateDiscreteValues() {

        double value = generator.nextValue().doubleValue();
        Double aggregate = (Double) generatedValues.get(Double.valueOf(value));
        aggregate = Double.valueOf(aggregate == null ? 0.0D : aggregate.doubleValue());
        generatedValues.put(Double.valueOf(value), aggregate = Double.valueOf(aggregate.doubleValue() + 1.0D));

        count++;
        return (int)Math.round(value);
    }

    private int generateContinuousValues() {
        double value = generator.nextValue().doubleValue();
        while (value < 0) {
            value = generator.nextValue().doubleValue();
        }
        min = Math.min(value, min);
        max = Math.max(value, max);
        
        numbers.add(value);
        count++;
        
        return (int)Math.round(value);
    }

    protected static Map<Double, Double> doQuantization(double max, double min, ArrayList<Double> values) {
        double range = max - min;
        int noIntervals = 20;
        double intervalSize = range / noIntervals;
        int[] intervals = new int[noIntervals];        
        for (double value : values) {
            int interval = Math.min(noIntervals - 1, (int) Math.floor((value - min) / intervalSize));

            assert ((interval >= 0) && (interval < noIntervals)) : ("Invalid interval: " + interval);
            intervals[interval] += 1;
        }
        Map discretisedValues = new HashMap();
        for (int i = 0; i < intervals.length; i++) {
            double value = 1.0D / intervalSize * intervals[i];
            discretisedValues.put(Double.valueOf(min + (i + 0.5D) * intervalSize), Double.valueOf(value));
        }
        return discretisedValues;
    }

    public abstract Map<Double, Double> getExpectedValues();

    public abstract double getExpectedMean();

    public abstract double getExpectedStandardDeviation();

    public abstract String getDescription();

    public abstract boolean isDiscrete();
}
