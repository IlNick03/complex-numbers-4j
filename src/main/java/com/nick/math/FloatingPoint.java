package com.nick.math;

/**
 * A class which is useful to verify equality of two double numbers considering
 * possible numeric cancelation due to finite precision limits in computations.
 *
 * @author Nicolas Scalese
 */
public class FloatingPoint {
    
    /**
     * This constant holds the value: {@code 2^(-53) = 1,11 * 10^(-16)} ,
     * that is the precision of a 64 bit floating-point number.
     * <p>
     * The value {@code 4 * } {@link #DOUBLE_EPS} {@code = 0,444 * 10^(-15)}  
     * is useful to compare a double-precision result with its expected value,
     * even considering a {@code LITTLE} numeric canceling (2-bits error, or
     * 1 "lost" decimal digit).
     * 
     * @see Double#PRECISION
     * @see #FLOAT_EPS
     */
    public static final double DOUBLE_EPS = Math.pow(2, - (Double.PRECISION));

    /**
     * This constant holds the value: {@code 2^(-39) = 0,909 * 10^(-12)}  .
     * <p>
     * It is useful to compare a decimal result with its expected value,
     * considering a {@code GREATER} numeric canceling (13-bits error, or 
     * 4 "lost" decimal digits).
     * 
     * @see #DOUBLE_EPS
     * @see #FLOAT_EPS
     */
    public static final double MANY_CALCULATIONS_EPS = Math.pow(2, -39);
    
    /**
     * This constant holds the value: {@code 2^(-24) = 0,596 * 10^(-7)} ,
     * that is the precision of a 32 bit floating-point number.
     * <p>
     * It is useful to compare a result of multiple calculations with its expected value:
     * <ul>
     *   <li> Single-precision : </li> 
     *      even considering a {@code LITTLE} numeric canceling (1-bit error).
     *   <li> Double-precision : </li>
     *      even considering a {@code DISASTROUS} numeric canceling (29-bits error, 
     *      or 5 "lost" decimal digits).
     * </ul>
     * 
     * @see Float#PRECISION
     * @see #DOUBLE_EPS
     */
    public static final double FLOAT_EPS = Math.pow(2, - (Float.PRECISION));
    
    public static IllegalArgumentException NAN_OR_INFINITY_ARGUMENT = 
            new IllegalArgumentException("NaN or infinity numbers not allowed.");
    
    
    private FloatingPoint() {}
    
    // -------------------------------------------------------------------------
    
    /**
     * It is equivalent to the code: 
     * <ul> {@code approxEqual(actual, expected, 4 *} {@link #DOUBLE_EPS} {@code)} </ul>
     * 
     * @param actual
     * @param expected
     * @return Approximate equality based on: {@code |expected - actual| <} {@link #DOUBLE_EPS}
     * @see #approxEqual(double, double, double) 
     * @see #DOUBLE_EPS
     */
    public static boolean approxEqual(double actual, double expected) {
//        return (actual > expected - (4 * DOUBLE_EPS)) && (actual < expected + (4 * DOUBLE_EPS));
        return FloatingPoint.approxEqual(actual, expected, 4 * DOUBLE_EPS);
    }
    
    /**
     * Returns if the actual number is approximately equal to the expected number.
     * More formally, verifies if the actual number belongs to the range:
     * {@code (expected - epsilon, expected + epsilon)} (etremes excluded).
     * So the absolute difference {@code |expected - actual|} has to be inferior to epsilon.
     * 
     * @param actual
     * @param expected
     * @return Approximate equality based on: {@code |expected - actual| < epsilon}
     */
    public static boolean approxEqual(double actual, double expected, double epsilon) {
//        return (actual > expected - epsilon) && (actual < expected + epsilon);
        double difference = Math.abs(expected - actual);
        return (difference < epsilon);
    }
    
    /**
     * Returns if the actual number is approximately equal to the expected number.
     * More formally, verifies if the actual number belongs to the range:
     * {@code (- epsilon, + epsilon)} (etremes excluded).
     * So the absolute value {@code |actual|} has to be inferior to epsilon.
     * <p>
     * It is equivalent to the code: 
     * <ul> {@code approxEqual(actual, 0, } {@link #DOUBLE_EPS} {@code)} </ul>
     * 
     * @param actual
     * @return Approximate equality based on: {@code |actual| < epsilon}
     */
    public static boolean approxZero(double actual) {
        return FloatingPoint.approxEqual(actual, 0, 4 * DOUBLE_EPS);
    }
    
    public static boolean approxZero(double actual, double epsilon) {
        return FloatingPoint.approxEqual(actual, 0, epsilon);
    }
    
}
