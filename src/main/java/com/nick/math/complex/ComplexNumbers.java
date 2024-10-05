package com.nick.math.complex;

import com.nick.math.FloatingPoint;
import java.util.*;
import static com.nick.math.FloatingPoint.NAN_OR_INFINITY_ARGUMENT;

/**
 * This static class allows you to create instances for {@link Complex} interface,
 * choosing a proper implementation, and implements arithmetic operations 
 * between multiple complex numbers.
 * 
 * <p> The operations supported include creating complex numbers from 
 * real numbers, performing sums and products of complex numbers, 
 * and solving linear and quadratic equations involving real and complex coefficients.</p>
 * 
 * @see Complex
 * @author Nicolas Scalese
 */
public class ComplexNumbers {
    
    /**
     * The Complex value: {@code z = 0 + 0i}, which is the additive identity 
     * in the complex number system.
     */
    public static final Complex ZERO_COMPLEX_CARTESIAN = new DoubleComplex(0, 0);
    
    /**
     * The Complex value: {@code z = 0 * (cos(0) + i*sin(0))}, which is the 
     * additive identity in polar form.
     */
    public static final Complex ZERO_COMPLEX_POLAR = new DoublePolarComplex(0, 0);
    
    /**
     * The Complex value: {@code z = 1 + 0i} ,, which is the multiplicative identity 
     * in the complex number system.
     */
    public static final Complex ONE_COMPLEX_CARTESIAN = new DoubleComplex(1, 0);
    
    /**
     * The Complex value: {@code z = 1 * (cos(0) + i*sen(0))}, which is the 
     * multiplicative identity in polar form.
     */
    public static final Complex ONE_COMPLEX_POLAR = new DoublePolarComplex(1, 0);

    /**
     * The Complex value: {@code z = +i} , which is the complex unit.
     */
    public static final Complex IMAGINARY_UNIT = new DoubleComplex(0, 1);

    /**
     * The Complex value: {@code z = -i} , which is the complex unit, negated.
     */
    public static final Complex IMAGINARY_UNIT_NEGATIVE = new DoubleComplex(0, -1);
    
    public static final int POSITIVE_COMPLEX_SQRT = 0;
    public static final int NEGATIVE_COMPLEX_SQRT = 1;
    
    
    private ComplexNumbers() {}
    

    // ---------------------------------------------------------------------- //
    //  Static factory methods
    // ---------------------------------------------------------------------- //
    
    /**
     * Creates a complex number from a real number, represented as {@code r + 0i}.
     *
     * @param real A real number to convert to a complex number.
     * @return A complex number equivalent to {@code r + 0i}.
     * @throws IllegalArgumentException if the provided real number is NaN or infinite.
     */
    public static Complex of(double real) {
        validateFinite(real);
        return new DoubleComplex(real);
    }
    
    /**
     * Creates a complex number from its Cartesian form, represented as {@code a + i*b}.
     *
     * @param real Real part of the complex number.
     * @param imaginary Imaginary part of the complex number.
     * @return A complex number represented as {@code a + i*b}.
     * @throws IllegalArgumentException if either the real or imaginary part is NaN or infinite.
     */
    public static Complex ofCartesianForm(double real, double imaginary) {
        validateFinite(real, imaginary);
        return new DoubleComplex(real, imaginary);
    }
    
    /**
     * Creates a complex number from its polar form, defined by a modulus and an angle: 
     * {@code z = r * (cos(theta) + i*sen(theta))}
     * <ul>
     *   <li> Modulus ({@code r}): </li> 
     *        Distance between the origin {@code (0,0)} and the point {@code (a,b)}, 
     *        where {@code (a,b)} are the real and imaginary part of {@code z} .
     *   <li> Angle ({@code theta}): </li>
     *        Angle between the {@code (0,0)-(a,b)} segment and the {@code X} axis.
     * </ul>
     * 
     * @param modulus Modulus (or magnitude) of the complex number.
     * @param argument Argument (or angle) in radians.
     * @return A complex number represented in polar form as {@code r * (cos(theta) + i*sin(theta))}.
     * @throws IllegalArgumentException if either the modulus or argument is NaN or infinite.
     */
    public static Complex ofPolarForm(double modulus, double argument) {
        validateFinite(modulus, argument);
        return new DoublePolarComplex(modulus, argument);
    }
    
     /**
     * Creates a complex number from a real number, represented in polar form.
     * More specifically, the given number is interpreted as a modulus.
     * If the modulus is non-negative, the argument is set to 0; otherwise, 
     * it is set to {@code Math.PI}.
     *
     * @param real The real part of the complex number.
     * @return A complex number represented in polar form.
     * @throws IllegalArgumentException if the provided real number is NaN or infinite.
     */
    public static Complex ofPolarForm(double real) {
        validateFinite(real);
        return new DoublePolarComplex(real);
    }
    
    
    private static void validateFinite(double value) {
        if (!Double.isFinite(value)) {
            throw NAN_OR_INFINITY_ARGUMENT;
        }
    }
    
    private static void validateFinite(double value1, double value2) {
        if ((!Double.isFinite(value1)) || (!Double.isFinite(value2))) {
            throw NAN_OR_INFINITY_ARGUMENT;
        }
    }
    

    // ---------------------------------------------------------------------- //
    //  Other static methods
    // ---------------------------------------------------------------------- //
    
    public static boolean isZero(Complex complex) {
        return Objects.equals(complex, ZERO_COMPLEX_CARTESIAN) || 
                Objects.equals(complex, ZERO_COMPLEX_POLAR);
    }
    
    public static boolean isZero(Complex complex, double eps) {
        return complex.isZero(eps);
    }
    
    public static boolean isOne(Complex complex) {
        return Objects.equals(complex, ONE_COMPLEX_CARTESIAN) || 
                Objects.equals(complex, ONE_COMPLEX_POLAR);
    }
    
    // -------------------------------------------------------------------------
    
     /**
     * Computes the sum of all the provided {@link Complex} numbers.
     *
     * <p>This method performs the summation in Cartesian form. The order of 
     * the inputs does not affect the result.</p>
     *
     * @param complex Varargs of complex numbers to sum.
     * @return The resulting complex sum.
     * @throws IllegalArgumentException if no complex numbers are provided.
     * @throws NullPointerException
     */
    public static Complex sumAll(Complex... complex) {
        if (complex == null) {
            throw new NullPointerException();
        }
        if (complex.length == 0) {
            return ZERO_COMPLEX_CARTESIAN;
        }
        
        // Kahan sum to compensate numeric canceling
        Complex sum = complex[0];
        Complex compensation = ComplexNumbers.ZERO_COMPLEX_CARTESIAN;  // For less-significant digits lost.
        for (int i = 1; i < complex.length; i++) {
            // sum = sum.plus(complex[i]);
            Complex y = complex[i].minus(compensation);    // Equals y at the first iteration. Otherwise, adds the lost part of previous summation.
            Complex t = sum.plus(y);    // If sum is big and y is smaller, there could be digits lost
            compensation = t.minus(sum).minus(y);    // Recovers the less significant part of y, negated.
            sum = t;
        }
        return sum;
    }
    
    /**
     * Computes the sum of all the provided {@link Complex} numbers.
     *
     * <p>This method performs the summation in Cartesian form. The order of 
     * the inputs does not affect the result.</p>
     * 
     * @param complexNumbers A collection of complex numbers
     * @return The resulting complex sum.
     * @throws NullPointerException
     */
    public static Complex sumAll(Collection<Complex> complexNumbers) {
        if (complexNumbers == null) {
            throw new NullPointerException();
        }
        if (complexNumbers.isEmpty()) {
            return ZERO_COMPLEX_CARTESIAN;
        }
        
        // Kahan sum to compensate numeric canceling
        Complex sum = null;
        Complex compensation = ComplexNumbers.ZERO_COMPLEX_CARTESIAN;  // For less-significant digits lost.
        for (Complex c : complexNumbers) {
            if (sum != null) {
                // sum = sum.plus(c);
                Complex y = c.minus(compensation);    // Equals y at the 1st iteration. Adds the lost part of previous summation.
                Complex t = sum.plus(y);    // If sum is big and y is smaller, there could be digits lost
                compensation = t.minus(sum).minus(y);    // Recovers the less significant part of y, negated.
                sum = t;
            } else {
                sum = c;
            }
        }
        return sum;
    }
    
    /**
     * Computes the product of all the provided {@link Complex} numbers.
     *
     * <p>This method performs the multiplications in both Cartesian and Polar form. 
     * So the order of the inputs may affect the result, since multiplication 
     * is implemented differently for each form.</p>
     *
     * @param complex Varargs of complex numbers to multiply.
     * @return The resulting complex product.
     * @throws IllegalArgumentException if no complex numbers are provided.
     */
    public static Complex multiplyAll(Complex... complex) {
        if (complex == null) {
            throw new NullPointerException();
        }
        if (complex.length == 0) {
            throw new UnsupportedOperationException("Product for empty collection of complex numbers is not supported.");
        }
        
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
     * @param complexNumbers A collection of complex numbers
     * @return 
     * @see Complex#multiplyBy(com.complexmath.Complex) 
     * @see Complex#equals(com.complexmath.Complex, double) 
     * @see ComplexNumbers#multiplyAll(com.complexmath.Complex...)  
     */
    public static Complex multiplyAll(Collection<Complex> complexNumbers) {
        if (complexNumbers == null) {
            throw new NullPointerException();
        }
        if (complexNumbers.isEmpty()) {
            throw new UnsupportedOperationException("Product for empty collection of complex numbers is not supported.");
        }
        
        Complex product = null;
        for (Complex c : complexNumbers) {
            if (product != null) {
                product = product.multiplyBy(c);
            } else {
                product = c;
            }
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
    
    public static Complex[] allSqrtsOf(double real) {
        Complex[] roots = new Complex[2]; 
        double sqrtAbs = Math.sqrt(Math.abs(real));
        
        if (FloatingPoint.approxZero(real)) {
            roots[0] = ZERO_COMPLEX_CARTESIAN;
            roots[1] = roots[0];
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
     * Solves a linear equation of the form {@code a*x + b = 0},
     * where the coefficients are {@link Complex} numbers, and returns the root.
     *
     * @param a The complex coefficient of {@code x^1} (must not be zero).
     * @param b The complex coefficient of {@code x^0} (known term).
     * @return The root of the linear equation.
     * @throws IllegalArgumentException if {@code a} is zero.
     */
    public static Complex solveLinearEquation(Complex a, Complex b) {
        if (ComplexNumbers.isZero(a)) {
            throw new IllegalArgumentException("Coeff of x^1 is zero.");
        }
        // a*x + b = 0  -> x = (-b)/a
        return b.negative().divideBy(a);
    }
    
    
    /**
     * Solves a quadratic equation of the form <code>ax^2 + bx + c = 0</code>, where the coefficients are real numbers.
     * This method computes the roots of the equation by handling different cases:
     * <ul>
     *   <li>If <code>b == 0</code>, the equation is treated as <code>ax^2 + c = 0</code>.</li>
     *   <li>Otherwise, the quadratic formula is applied, calculating the discriminant (delta) and using it to find the roots.</li>
     * </ul>
     * The method returns the roots as an array of {@link Complex} objects, to accommodate real and complex roots.
     *
     * @param a Coefficient of {@code x^2}. Must be non-zero.
     * @param b Coefficient of {@code x^1}.
     * @param c Coefficient of {@code x^1} (known term).
     * @return an array of {@link Complex} objects representing the roots of the quadratic equation.
     *         If the roots are real, they are returned as {@link DoubleComplex} instances.
     *         If the roots are complex, they are returned as {@link DoublePolarComplex} instances.
     * @throws IllegalArgumentException if <code>a == 0</code>, as this would make the equation linear rather than quadratic.
     */
    public static Complex[] solveQuadraticEquation(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("Coeff of x^2 is zero.");
        }
        if ((b == 0) && (c == 0)) {
            // a(x^2) = 0
            return ComplexNumbers.findRootsWithOnlyA();
        }
        
        Complex complexA = new DoubleComplex(a);
        Complex complexB = new DoubleComplex(b);
        Complex complexC = new DoubleComplex(c);
        
        if (b == 0) {
            // a(x^2) + c = 0;
            return ComplexNumbers.findRootsWithOnlyAC(complexA, complexC);
        }
        if (c == 0) {
            // a(x^2) + bx = 0
            return ComplexNumbers.findRootsWithOnlyAB(complexA, complexB);
        }
        
        // a(x^2) + bx + c = 0
        Complex sqrtOfDelta = ComplexNumbers.sqrtOfDelta(a, b, c);    // delta = b^2 - 4ac
        return ComplexNumbers.findRoots(complexA, complexB, complexC, sqrtOfDelta);
    }
    
    /**
     * Solves a quadratic equation of the form <code>ax^2 + bx + c = 0</code>, where the coefficients are complex numbers.
     * This method computes the roots of the equation by handling different cases:
     * <ul>
     *   <li>If <code>b</code> is zero, the equation is treated as <code>ax^2 + c = 0</code>.</li>
     *   <li>Otherwise, the quadratic formula is applied, calculating the discriminant (delta) and using it to find the roots.</li>
     * </ul>
     * The method returns the roots as an array of {@link Complex} objects, which may represent real or complex solutions.
     *
     * @param a The complex coefficient of <code>x^2</code>. Must be non-zero.
     * @param b The complex coefficient of <code>x^1</code>.
     * @param c The complex coefficient of {@code x^1} (known term).
     * @return an array of {@link Complex} objects representing the roots of the quadratic equation.
     *         The roots are returned in complex form, even if they are real.
     * @throws UnsupportedOperationException if <code>a</code> is zero, as this would make the equation linear rather than quadratic.
     */
    public static Complex[] solveQuadraticEquation(Complex a, Complex b, Complex c) {
        if (ComplexNumbers.isZero(a)) {
            throw new IllegalArgumentException("Coeff of x^2 is zero.");
        }
        if (ComplexNumbers.isZero(b) && ComplexNumbers.isZero(c)) {
            // a(x^2) = 0
            return ComplexNumbers.findRootsWithOnlyA();
        }
        
        if (ComplexNumbers.isZero(b)) {
            // a(x^2) + c = 0;
            return ComplexNumbers.findRootsWithOnlyAC(a, c);
        }
        if (ComplexNumbers.isZero(c)) {
            // a(x^2) + bx = 0
            return ComplexNumbers.findRootsWithOnlyAB(a, b);
        }

        // a(x^2) + bx + c = 0
        Complex sqrtOfDelta = ComplexNumbers.sqrtOfDelta(a, b, c);    // delta = b^2 - 4ac
        return ComplexNumbers.findRoots(a, b, c, sqrtOfDelta);
    }
    
    
    private static Complex sqrtOfDelta(Complex a, Complex b, Complex c) {
        Complex bPow2 = b.pow(2);
        Complex fourAC = a.multiplyBy(c).multiplyByReal(4);
        Complex delta = bPow2.minus(fourAC);
        // Complex[] sqrt(delta) = {+z , -z}
        return delta.sqrt(POSITIVE_COMPLEX_SQRT);
    }
    
    private static Complex sqrtOfDelta(double a, double b, double c) {
        double delta = (b * b) - 4 * a * c;
        // Complex[] sqrt(delta) = {+z , -z}
        return ComplexNumbers.sqrtOf(delta, POSITIVE_COMPLEX_SQRT);
    }

    private static Complex[] findRootsWithOnlyA() {
        // a(x^2) = 0  -> x1 = x2 = 0
        Complex x1 = ZERO_COMPLEX_CARTESIAN;
        Complex x2 = x1;
        return new Complex[] {x1, x2};
    }
    
    private static Complex[] findRootsWithOnlyAC(Complex a, Complex c) {
        // a(x^2) + c = 0  -> x1 = x2 = +- sqrt(-c/a)
        Complex x1 = c.negative().divideBy(a).sqrt(POSITIVE_COMPLEX_SQRT);
//        Complex x2 = c.negative().divideFor(a).sqrt(NEGATIVE_COMPLEX_SQRT);
        Complex x2 = x1.negative();
        return new Complex[] {x1, x2};
    }

    private static Complex[] findRootsWithOnlyAB(Complex a, Complex b) {
        // a(x^2) + bx = 0  -> ax + b = 0, x = 0
        Complex x1 = ZERO_COMPLEX_CARTESIAN;    // x = 0
        Complex x2 = ComplexNumbers.solveLinearEquation(a, b);    // ax + b = 0 
        return new Complex[] {x1, x2};
    }
    
    private static Complex[] findRoots(Complex a, Complex b, Complex c, Complex sqrtOfDelta) {
        Complex x1, x2;    // The equation roots
        
        if (sqrtOfDelta.isZero()) {
            // If delta is zero, we have 2 equal roots.
            // x1 = x2 = -b /(2*a)
            x1 = b.negative().divideBy(a.multiplyByReal(2));
            x2 = x1;
            return new Complex[] {x1, x2};
        }
        
        Complex sgnB = new DoubleComplex(Math.signum(b.realValue()), Math.signum(b.imaginaryValue())); 
        Complex expression = b.negative().minus(sqrtOfDelta.multiplyBy(sgnB));    // -b - sgn(b)*sqrt(delta)
        // x1 = (-b - sgn(b)*sqrt(delta)) /(2*a)  -> Alternative formula: avoid catastrophic cancellation
        x1 = expression.divideBy(a.multiplyByReal(2));
        
        if (ComplexNumbers.isZero(expression)) {
            // If x1 is zero, we cannot use the alternative formula: dividing by zero is not allowed.
            // x2 = (-b - sqrt(delta)) /(2*a)  -> Traditional formula
            x2 = b.negative().minus(sqrtOfDelta).divideBy(a.multiplyByReal(2));
        } else {
            // x2 = c / (a * x1)  -> Alternative formula: avoid catastrophic cancellation
            x2 = c.divideBy(x1.multiplyBy(a));
        }
        return new Complex[] {x1, x2};
    }
    
}