package com.nick.math.complex;

import com.nick.math.FloatingPoint;
import static com.nick.math.FloatingPoint.NAN_ARGUMENT;
import java.util.*;

/**
 * This static class allow you create instances for {@link Complex} interface,
 * choosing a proper implementation.
 * And implements aritmetic operations between multiple complex numbers
 * 
 * @author Nicolas Scalese
 * @see Complex
 */
public class ComplexNumbers {
    
    /**
     * The Complex value: {@code z = 0 + 0i} , which is neuter for sum.
     */
    public static Complex ZERO_COMPLEX_CARTESIAN = new DoubleComplex(0, 0);
    
    /**
     * The Complex value: {@code z = 0 * (cos(0) + i*sen(0))} , which is neuter for sum.
     */
    public static Complex ZERO_COMPLEX_POLAR = new DoublePolarComplex(0, 0);
    
    /**
     * The Complex value: {@code z = 1 + 0i} , which is neuter for product.
     */
    public static Complex ONE_COMPLEX_CARTESIAN = new DoubleComplex(1, 0);
    
    /**
     * The Complex value: {@code z = 1 * (cos(0) + i*sen(0))} , which is neuter for product.
     */
    public static Complex ONE_COMPLEX_POLAR = new DoublePolarComplex(1, 0);
    
    public static int POSITIVE_COMPLEX_SQRT = 0;
    public static int NEGATIVE_COMPLEX_SQRT = 1;
    
    
    private ComplexNumbers() {}
    
    // -------------------------------------------------------------------------
    
    /**
     * If {@code r} is a real number, its complex equivalent is: {@code r + 0i}.
     * So this method creates and returns a {@link Complex} with a proper implementation.
     * 
     * @param real A real number
     * @return A complex number: {@code r + 0i}
     */
    public static Complex of(double real) {
        if ((real == Double.NaN) || (real == Float.NaN)) {
            throw NAN_ARGUMENT;
        }
        return new DoubleComplex(real);
    }
    
    /**
     * A complex number {@code z = a + i*b} is a cartesian product of 2 real numbers {@code a, b}.
     * So this method creates and returns a {@link Complex} with a proper implementation.
     * 
     * @param real Real part: {@code a}
     * @param imaginary Imaginary part: {@code b}
     * @return A complex number: {@code a + i*b}
     */
    public static Complex ofCartesianForm(double real, double imaginary) {
        if ((real == Double.NaN) || (real == Float.NaN)
                || (imaginary == Double.NaN) || (imaginary == Float.NaN)) {
            throw NAN_ARGUMENT;
        }
        return new DoubleComplex(real, imaginary);
    }
    
    /**
     * A complex number {@code z = r * (cos(theta) + i*sen(theta))} in its polar form
     * is defined by its:
     * <ul>
     *   <li> Modulus ({@code r}): </li> 
     *        Distance between the origin {@code (0,0)} and the point {@code (a,b)}, 
     *        where {@code (a,b)} are the real and imaginary part of {@code z} .
     *   <li> Angulus ({@code theta}): </li>
     *        Angulus between the {@code (0,0)-(a,b)} segment and the {@code X} axis.
     * </ul>
     * So this method creates and returns a {@link Complex} with a proper implementation.
     * 
     * @param modulus Modulus: {@code r}
     * @param argument Angulus: {@code theta}
     * @return A complex number: {@code z = r * (cos(theta) + i*sen(theta))}
     */
    public static Complex ofPolarForm(double modulus, double argument) {
        if ((modulus == Double.NaN) || (modulus == Float.NaN)
                || (argument == Double.NaN) || (argument == Float.NaN)) {
            throw NAN_ARGUMENT;
        }
        return new DoublePolarComplex(modulus, argument);
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method makes the sum of all the {@link Complex} number in input.
     * <p>
     * <p> API NOTE: </p>
     * The sum of any {@link Complex} instances {@code z1, z2, ... , zN} 
     * is always implemented in cartesian form: ({@code w = a + i*b}).
     * So, permuting their order, should give the same result.
     * 
     * @param complex Complex numbers
     * @return 
     * @see Complex#plus(com.complexmath.Complex) 
     * @see ComplexNumbers#sumAll(java.util.List) 
     */
    public static Complex sumAll(Complex... complex) {
        Complex sum = complex[0];
        for (int i = 1; i < complex.length; i++) {
            sum = sum.plus(complex[i]);
        }
        return sum;
    }
    
    /**
     * This method makes the sum of all the {@link Complex} number in input.
     * <p>
     * <p> API NOTE: </p>
     * The sum of any {@link Complex} instances {@code z1, z2, ... , zN} 
     * is always implemented in cartesian form: ({@code w = a + i*b}).
     * So, permuting their order, should give the same result.
     * 
     * @param complex A list of complex numbers
     * @return
     * @see Complex#plus(com.complexmath.Complex) 
     * @see ComplexNumbers#sumAll(com.complexmath.Complex...) 
     */
    public static Complex sumAll(Collection<Complex> complex) {
        Complex sum = new DoubleComplex(0, 0);
            
        for (Complex c : complex) {
            sum = sum.plus(c);
        }
        return sum;
    }
    
    /**
     * This method makes the product of all the {@link Complex} number in input.
     * <p>
     * <p> API NOTE: </p>
     * If {@code z1, z2, ... , zN} are {@link Complex} instances with different 
     * implementations, their {@link Complex} product {@code z1 * z2 * ... * zN} 
     * has the same implementation of the first one. 
     * So, permuting their order, shoud give a slightly different result,
     * due to finite precision limits in similar arithmetic operations.
     * <p>
     * It is recommended to verify product results with {@link Complex#equals(com.complexmath.Complex, double)}
     * method instead of {@link Complex#equals(java.lang.Object)} .
     * 
     * @param complex Complex numbers
     * @return 
     * @see Complex#multiplyBy(com.complexmath.Complex) 
     * @see Complex#equals(com.complexmath.Complex, double) 
     * @see ComplexNumbers#multiplyAll(java.util.List) 
     */
    public static Complex multiplyAll(Complex... complex) {
        Complex product = complex[0];
        for (int i = 1; i < complex.length; i++) {
            product = product.multiplyBy(complex[i]);
        }
        return product;
    }
    
    /**
     * This method makes the product of all the {@link Complex} number in input.
     * <p>
     * <p> API NOTE: </p>
     * If {@code z1, z2, ... , zN} are {@link Complex} instances with different 
     * implementations, their {@link Complex} product {@code z1 * z2 * ... * zN} 
     * has the same implementation of the first one. 
     * So, permuting their order, shoud give a slightly different result,
     * due to finite precision limits in similar arithmetic operations.
     * <p>
     * It is recommended to verify product results with {@link Complex#equals(com.complexmath.Complex, double)}
     * method instead of {@link Complex#equals(java.lang.Object)} .
     * 
     * @param complex A list of complex numbers
     * @return 
     * @see Complex#multiplyBy(com.complexmath.Complex) 
     * @see Complex#equals(com.complexmath.Complex, double) 
     * @see ComplexNumbers#multiplyAll(com.complexmath.Complex...)  
     */
    public static Complex multiplyAll(List<Complex> complex) {
        Complex product;
        if (complex.get(0) instanceof DoubleComplex) {
            product = new DoubleComplex(1, 0);
        } else if (complex.get(0) instanceof DoublePolarComplex) {
            product = new DoublePolarComplex(1, 0);
        } else {
            throw new UnsupportedOperationException("Not supported operation.");
        }
            
        for (Complex c : complex) {
            product = product.multiplyBy(c);
        }
        return product;
    }
    
    // -------------------------------------------------------------------------
    
    public static Complex nThRootOf(double real, int rootIndex, int k) {
        return new DoublePolarComplex(real, 0).nThRoot(rootIndex, k);
    }
    
    public static Complex[] allNThRootsOf(double real, int rootIndex) {
        return new DoublePolarComplex(real, 0).allNThRoots(rootIndex);
    }
    
    public static Complex sqrtOf(double real, int k) {
        if ((k < 0) || (k >= 2)) {
            throw new IllegalArgumentException("k value must be in range: 0 (included) - 2 (excluded)");
        } 
        
        Complex[] roots = new Complex[2]; 
        double sqrt = Math.sqrt(Math.abs(real));
        if (k == NEGATIVE_COMPLEX_SQRT) {
            sqrt = - sqrt;
        }
        
        if (FloatingPoint.approxZero(real)) {
            return ZERO_COMPLEX_CARTESIAN;
        }
        if (real < 0) {
            // sqrt(-9) = +3i (-3i)
            return new DoubleComplex(0, sqrt);
        }
        // sqrt(9) = +3 (-3)
        return new DoubleComplex(sqrt, 0);
    }
    
    public static Complex[] sqrtsOf(double real) {
        Complex[] roots = new Complex[2]; 
        double sqrtAbs = Math.sqrt(Math.abs(real));
        
        if (FloatingPoint.approxZero(real)) {
            roots[0] = ZERO_COMPLEX_CARTESIAN;
            roots[1] = ZERO_COMPLEX_POLAR;
        } else if (real < 0) {
            // sqrt(-9) = +3i , -3i
            roots[0] = new DoubleComplex(0, sqrtAbs);
            roots[1] = new DoubleComplex(0, - sqrtAbs);
        } else {
            // sqrt(9) = +3, -3
            roots[0] = new DoubleComplex(sqrtAbs, 0);
            roots[1] = new DoubleComplex(- sqrtAbs, 0);
        }
        
        return roots;
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * 
     * @param a Coeff of x^2
     * @param b Coeff of x^1
     * @param c Coeff of x^0
     * @return 
     */
    public Complex[] equationRoots(double a, double b, double c) {
        double delta = (b * b) - 4 * a * c;
        // complex sqrt(delta) = +z , -z
        Complex deltaRoot = ComplexNumbers.sqrtsOf(delta)[0];
        
        Complex[] equationRoots = new Complex[2];
        // x1 = (-b - sgn(b)*sqrt(delta)) / (2 * a)
        equationRoots[1] = deltaRoot.multiplyByReal(Math.signum(b))
                .plusReal(b)
                .negative()
                .divideForReal(2 * a);
        // x2 = c / (a * x1)  ->  Limits the numeric canceling
        equationRoots[2] = equationRoots[1].multiplyByReal(a)
                .reciprocal()
                .multiplyByReal(c);
        
        return equationRoots;
    }
    
}
