package org.zetool.math.averaging;

import java.util.function.DoubleFunction;

/**
 * Implementation of some methods providing non-linear averaging.
 * 
 * @author Jan-Philipp Kappmeier
 */
public class NonLinearAverages {
    
    public static double logisticAverage(double weightParameter, double val1, double val2) {
        return generalAverage(NonLinearAverages::logisticWeightFactor, weightParameter, val1, val2);
    }

    /**
     * Default parameterized average based on a logistic function with parameter values within [0,1]. The default
     * average uses a {@literal steepness} of {@literal 12} which leads to a weight at the boundaries of circa 
     * {@literal 0.0025} and {@literal 0.9975}, respectively.
     * 
     * @param x the weight parameter
     * @return the weighting logisticWeightFactor for the weight parameter
     */
    public static double logisticWeightFactor(double x) {
        return logisticWeightFactor(12, x);
    }

    /**
     * Default parameterized average based on a logistic function with parameter values within [0,1].
     * 
     * @param steepness
     * @param x
     * @return 
     */
    public static double logisticWeightFactor(double steepness, double x) {
        double nom = 1 + Math.exp(-steepness * (x - 0.5));

        return 1/nom;
    }
    
    /**
     * Computes the weighted combination of two values. The weight is specified by a function which is applied to
     * an input parameter.
     * 
     * @param weightFunction the weight function
     * @param x the input parameter
     * @param val1 one of the two inputs for weighted combination
     * @param val2 the other of the two inputs for weighted combination
     * @return 
     */
    public static double generalAverage(DoubleFunction<Double> weightFunction, double x, double val1, double val2) {
        double weight = weightFunction.apply(x);
        return (1-weight) * val1 + (weight) * val2;
    }
}
