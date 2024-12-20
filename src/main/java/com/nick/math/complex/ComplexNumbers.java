package com.nick.math.complex;

import com.nick.math.FloatingPoint;
import static com.nick.math.FloatingPoint.NAN_OR_INFINITY_ARGUMENT;
import java.util.*;
import java.math.BigDecimal;

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
    public static final Complex ZERO_COMPLEX_CARTESIAN = new CartesianComplexDouble(0, 0);
    
    /**
     * The Complex value: {@code z = 0 * (cos(0) + i*sin(0))}, which is the 
     * additive identity in polar form.
     */
    public static final Complex ZERO_COMPLEX_POLAR = new PolarComplexDouble(0, 0);
    
    /**
     * The Complex value: {@code z = 1 + 0i} , which is the multiplicative identity 
     * in the complex number system.
     */
    public static final Complex ONE_COMPLEX_CARTESIAN = new CartesianComplexDouble(1, 0);
    
    /**
     * The Complex value: {@code z = 1 * (cos(0) + i*sen(0))}, which is the 
     * multiplicative identity in polar form.
     */
    public static final Complex ONE_COMPLEX_POLAR = new PolarComplexDouble(1, 0);

    /**
     * The Complex value: {@code z = +i} , which is the complex unit.
     */
    public static final Complex IMAGINARY_UNIT = new CartesianComplexDouble(0, 1);

    /**
     * The Complex value: {@code z = -i} , which is the complex unit, negated.
     */
    public static final Complex IMAGINARY_UNIT_NEGATIVE = new CartesianComplexDouble(0, -1);
    
    public static final int POSITIVE_COMPLEX_SQRT = 0;
    public static final int NEGATIVE_COMPLEX_SQRT = 1;
    
    
    private ComplexNumbers() {}
    

    // ---------------------------------------------------------------------- //
    //  Static factory methods
    // ---------------------------------------------------------------------- //
    
    /**
     * Returns a copy of the given {@code Complex} number. 
     * <p>
     * More specifically: creates another instance of {@link Complex} using the same 
     * implementing class (Cartesian or Polar form), and assigns it the same value
     *
     * @param complex A complex number to copy.
     * @return Returns a copy of the given {@code Complex} number.
     * @throws UnsupportedOperationException if the provided complex number implementation is not supported (yet).
     */
    public static Complex of(Complex complex) {
        if (complex instanceof CartesianComplexDouble) {
            return new CartesianComplexDouble(complex);
        }
        if (complex instanceof PolarComplexDouble) {
            return new PolarComplexDouble(complex);
        }
        // should be updated in the future
        throw new UnsupportedOperationException("This instance/implementation of \'Complex\' is not supported (yet).");
    }
    
    /**
     * Creates a complex number from a real {@code double} number, represented as {@code r + 0i}.
     *
     * @param realValue A realValue number to convert to a complex number.
     * @return A complex number equivalent to {@code r + 0i}.
     * @throws IllegalArgumentException if the provided real number is NaN or infinite.
     */
    public static Complex of(double realValue) {
        validateFinite(realValue);
        return new CartesianComplexDouble(realValue);
    }
    
    /**
     * Creates a complex number from a real {@code BigDecimal} number, represented as {@code r + 0i}.
     * 
     * @apiNote 
     * A future release of this library will add {@link Complex} implementations handling
     * {@code BigDecimal} values for real part, imaginary part, modulus, main argument. 
     * For now, complex numbers are created using the {@code double} value of the given BigDecimal(s).
     *
     * @param realValue A realValue number to convert to a complex number.
     * @return A complex number equivalent to {@code r + 0i}.
     * @see BigDecimal#doubleValue()
     * @throws IllegalArgumentException if the provided real number is NaN or infinite.
     */
    public static Complex of(BigDecimal realValue) {
        validateFinite(realValue);
        return new CartesianComplexDouble(realValue.doubleValue());  // should be updated in the future
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
        return new CartesianComplexDouble(real, imaginary);
    }
    
    /**
     * Creates a complex number from its Cartesian form, represented as {@code a + i*b}.
     * 
     * @apiNote 
     * A future release of this library will add {@link Complex} implementations handling
     * {@code BigDecimal} values for real part, imaginary part, modulus, main argument. 
     * For now, complex numbers are created using the {@code double} value of the given BigDecimal(s).
     *
     * @param real Real part of the complex number.
     * @param imaginary Imaginary part of the complex number.
     * @return A complex number represented as {@code a + i*b}.
     * @see BigDecimal#doubleValue()
     * @throws IllegalArgumentException if either the real or imaginary part is NaN or infinite.
     */
    public static Complex ofCartesianForm(BigDecimal real, BigDecimal imaginary) {
        validateFinite(real, imaginary);
        return new CartesianComplexDouble(real.doubleValue(), imaginary.doubleValue());  // should be updated in the future
    }
    
    /**
     * Creates a complex number from its {@code double} Polar form, defined by a modulus and an angle: 
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
     * @param angle Argument (or angle) in radians.
     * @return A complex number represented in polar form as {@code r * (cos(theta) + i*sin(theta))}.
     * @throws IllegalArgumentException if either the modulus or angle is NaN or infinite.
     */
    public static Complex ofPolarForm(double modulus, double angle) {
        validateFinite(modulus, angle);
        return new PolarComplexDouble(modulus, angle);
    }
    
    /**
     * Creates a complex number from its {@code double} Polar form, defined by a modulus and an angle: 
     * {@code z = r * (cos(theta) + i*sen(theta))}
     * <ul>
     *   <li> Modulus ({@code r}): </li> 
     *        Distance between the origin {@code (0,0)} and the point {@code (a,b)}, 
     *        where {@code (a,b)} are the real and imaginary part of {@code z} .
     *   <li> Angle ({@code theta}): </li>
     *        Angle between the {@code (0,0)-(a,b)} segment and the {@code X} axis.
     * </ul>
     * 
     * @apiNote 
     * A future release of this library will add {@link Complex} implementations handling
     * {@code BigDecimal} values for real part, imaginary part, modulus, main argument. 
     * For now, complex numbers are created using the {@code double} value of the given BigDecimal(s).
     * 
     * @param modulus Modulus (or magnitude) of the complex number.
     * @param angle Argument (or angle) in radians.
     * @return A complex number represented in polar form as {@code r * (cos(theta) + i*sin(theta))}.
     * @see BigDecimal#doubleValue()
     * @throws IllegalArgumentException if either the modulus or angle is NaN or infinite.
     */
    public static Complex ofPolarForm(BigDecimal modulus, BigDecimal angle) {
        validateFinite(modulus, angle);
        return new PolarComplexDouble(modulus.doubleValue(), angle.doubleValue());  // should be updated in the future
    }
    
    /**
     * Creates a complex number from a {@code double} real number, represented in polar form.
     * More specifically, the given number is interpreted as a modulus.
     * If the modulus is non-negative, the argument is set to 0; otherwise, 
     * it is set to {@code Math.PI}.
     *
     * @param realValue The real part of the complex number.
     * @return A complex number represented in polar form.
     * @throws IllegalArgumentException if the provided realValue number is NaN or infinite.
     */
    public static Complex ofPolarForm(double realValue) {
        validateFinite(realValue);
        return new PolarComplexDouble(realValue);
    }
    
    /**
     * Creates a complex number from a {@code double} real number, represented in polar form.
     * More specifically, the given number is interpreted as a modulus.
     * If the modulus is non-negative, the argument is set to 0; otherwise, 
     * it is set to {@code Math.PI}.
     * 
     * @apiNote 
     * A future release of this library will add {@link Complex} implementations handling
     * {@code BigDecimal} values for real part, imaginary part, modulus, main argument. 
     * For now, complex numbers are created using the {@code double} value of the given BigDecimal(s).
     *
     * @param realValue The real part of the complex number.
     * @return A complex number represented in polar form.
     * @see BigDecimal#doubleValue()
     * @throws IllegalArgumentException if the provided realValue number is NaN or infinite.
     */
    public static Complex ofPolarForm(BigDecimal realValue) {
        validateFinite(realValue);
        return new PolarComplexDouble(realValue.doubleValue());  // should be updated in the future
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
    
    private static void validateFinite(BigDecimal value) {
        validateFinite(value.doubleValue());
    }
    
    private static void validateFinite(BigDecimal value1, BigDecimal value2) {
        validateFinite(value1.doubleValue(), value2.doubleValue());
    }
    

    // ---------------------------------------------------------------------- //
    //  Other static methods
    // ---------------------------------------------------------------------- //
    
    public static boolean isZero(Complex complex) {
        return complex.isZero();
    }
    
    public static boolean isZero(Complex complex, double eps) {
        return complex.isZero(eps);
    }
    
    public static boolean isOne(Complex complex) {
        return complex.isOne();
    }
    
    public static boolean equals(Complex c1, Complex c2) {
        return (c1 == c2) || (c1 != null && c1.equals(c2));
    }
    
    public static boolean deepEquals(Complex c1, Complex c2) {
        return (c1 == c2) || (c1 != null && c1.deepEquals(c2));
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
    
    public static Complex complexSqrtOf(double value, int k) {
        if ((k < 0) || (k >= 2)) {
            throw new IllegalArgumentException("k value must be in range: 0 (included) - 2 (excluded)");
        } 
        
        double sqrt = Math.sqrt(Math.abs(value));
        if (k == NEGATIVE_COMPLEX_SQRT) {
            sqrt = - sqrt;
        }
        
        if (FloatingPoint.approxZero(value)) {
            return ZERO_COMPLEX_CARTESIAN;
        }
        if (value < 0) {
            // sqrt(-9) = +3i (-3i)
            return ComplexNumbers.ofCartesianForm(0, sqrt);
        }
        // sqrt(9) = +3 (-3)
        return ComplexNumbers.ofCartesianForm(sqrt, 0);
    }
    
    public static Complex[] allComplexSqrtsOf(double value) {
        Complex[] roots = new Complex[2]; 
        double sqrtAbs = Math.sqrt(Math.abs(value));
        
        if (FloatingPoint.approxZero(value)) {
            roots[0] = ZERO_COMPLEX_CARTESIAN;
            roots[1] = roots[0];
        } else if (value < 0) {
            // sqrt(-9) = +3i , -3i
            roots[0] = ComplexNumbers.ofCartesianForm(0, sqrtAbs);
            roots[1] = ComplexNumbers.ofCartesianForm(0, - sqrtAbs);
        } else {
            // sqrt(9) = +3, -3
            roots[0] = ComplexNumbers.ofCartesianForm(sqrtAbs, 0);
            roots[1] = ComplexNumbers.ofCartesianForm(- sqrtAbs, 0);
        }
        
        return roots;
    }
    
    public static Complex complexCbrt(double value, int k) {
        return ComplexNumbers.complexRoot(value, 3, k);
    }
    
    public static Complex[] allComplexCbrts(double value, int k) {
        return ComplexNumbers.allComplexRoots(value, 3);
    }
    
    public static Complex complexRoot(double value, int rootIndex, int k) {
        return ComplexNumbers.ofPolarForm(value).root(rootIndex, k);
    }
    
    public static Complex[] allComplexRoots(double value, int rootIndex) {
        return ComplexNumbers.ofPolarForm(value).allRoots(rootIndex);
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Solves a linear equation of the form {@code a*x + b = 0},
     * where the coefficients are {@link Complex} numbers, and returns the complexRoot.
     *
     * @param a The complex coefficient of {@code x^1} (must not be zero).
     * @param b The complex coefficient of {@code x^0} (known term).
     * @return The complexRoot of the linear equation.
     * @throws IllegalArgumentException if {@code a} is zero.
     */
    public static Complex solveLinearEquation(Complex a, Complex b) {
        if (a.isZero()) {
            throw new IllegalArgumentException("Coeff of x^1 cannot be zero.");
        }
        if (b.isZero()) {
            // a*x = 0  -> x = 0
            return ZERO_COMPLEX_CARTESIAN;
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
     *         If the roots are real, they are returned as {@link CartesianComplexDouble} instances.
     *         If the roots are complex, they are returned as {@link PolarComplexDouble} instances.
     * @throws IllegalArgumentException if <code>a == 0</code>, as this would make the equation linear rather than quadratic.
     */
    public static Complex[] solveQuadraticEquation(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("Coeff of x^2 cannot be zero.");
        }
        
        if ((b == 0) && (c == 0)) {
            // a(x^2) = 0
            return ComplexNumbers.findRootsWithOnlyA();
        }
        
        Complex complexA = ComplexNumbers.of(a);
        Complex complexB = ComplexNumbers.of(b);
        Complex complexC = ComplexNumbers.of(c);
        
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
        if (a.isZero()) {
            throw new IllegalArgumentException("Coeff of x^2 cannot be zero.");
        }
        
        if (b.isZero() && c.isZero()) {
            // a(x^2) = 0
            return ComplexNumbers.findRootsWithOnlyA();
        }
        if (b.isZero()) {
            // a(x^2) + c = 0;
            return ComplexNumbers.findRootsWithOnlyAC(a, c);
        }
        if (c.isZero()) {
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
        return ComplexNumbers.complexSqrtOf(delta, POSITIVE_COMPLEX_SQRT);
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
        // Complex x2 = c.negative().divideFor(a).sqrt(NEGATIVE_COMPLEX_SQRT);
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
        
        Complex sgnB = ComplexNumbers.ofCartesianForm(Math.signum(b.realValue()), Math.signum(b.imaginaryValue())); 
        Complex expression = b.negative().minus(sqrtOfDelta.multiplyBy(sgnB));    // -b - sgn(b)*sqrt(delta)
        // x1 = (-b - sgn(b)*sqrt(delta)) /(2*a)  -> Alternative formula: avoid catastrophic cancellation
        x1 = expression.divideBy(a.multiplyByReal(2));
        
        if (expression.isZero()) {
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