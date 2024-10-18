package com.nick.math.complex;

import java.text.DecimalFormat;

/**
 * The {@code Complex} interface represents a complex number, which can be defined as:
 * <ul>
 *   <li>Cartesian form: {@code z = a + i*b}
 *     <ul>
 *       <li> Real part ({@code a})</li>
 *       <li> Imaginary part ({@code b}) where the imaginary unit {@code i} satisfies the equation: {@code i^2 = -1}</li>
 *     </ul>
 *   </li>
 *   <li>Polar form: {@code z = r * (cos(theta) + i*sin(theta))}
 *     <ul>
 *       <li> Modulus ({@code r}): the distance between {@code (0,0)} and {@code (a,b)}, or the length of the vector in the Gaussian plane.</li>
 *       <li> Main argument ({@code theta}): the angle formed between the {@code X} axis and the {@code (a,b)} vector.</li>
 *     </ul>
 *   </li>
 *   <li>Exponential form: {@code z = r * e^(i*theta)}
 *     <ul>
 *       <li> This is the most compact version of the polar form.</li>
 *       <li> Based on the equation: {@code e^(i*theta) = cos(theta) + i*sin(theta)}</li>
 *     </ul>
 *   </li>
 * </ul>
 * <p>
 * @apiNote
 * This API is fluent, allowing chained operations:
 * <p>
 * {@code Complex result = c1.plus(c2).minusReal(Math.PI).divideBy(c3.multiplyByImaginary(3)).sqrt(0);}
 * {@code // sqrt( (c1 + c2 - PI)/(c3*(0+3i)) )}
 * <p>
 * <p>
 * Some notes about the implementation:
 * <ul>
 *   <li> There are 2 different implementations of {@code Complex}, defined in Cartesian and polar/exponential form.</li>
 *     <ul>
 *       <li> The static class {@link ComplexNumbers} has public methods that return
 *            objects implementing the {@code Complex} interface. They "automatically"
 *            choose the proper implementation without the developer needing to worry about it.</li>
 *       <li> Knowing the implementation is not necessary. Also, some operations may turn
 *            a Cartesian {@code Complex} into a polar {@code Complex}, or vice-versa.</li>
 *     </ul>
 *   <li> Math operations are implemented as follows:</li>
 *     <ul>
 *       <li> Addition {@link #plus(Complex)} and subtraction {@link #minus(Complex)} are
 *            always computed using Cartesian form, calling methods {@link #realValue()} 
 *            and {@link #imaginaryValue()} in every case.</li>
 *       <li> Multiplication {@link #multiplyBy(Complex)} and division {@link #divideBy(Complex)} 
 *            are implemented differently. In case we multiply/divide different {@code Complex} 
 *            number types, the first of the two operands "decides" how the operation is made.</li>
 *       <li> Exponentiation {@link #pow(double)} and roots {@link #nThRoot(int, int)}, {@link #allNThRoots(int)}
 *            have a common implementation: they are always computed using polar form, 
 *            calling methods {@link #modulusValue()} and {@link #mainArgumentValue()} 
 *            in every case.</li>
 *     </ul> 
 *   <li> String representation:</li>
 *     <ul>
 *       <li> Regardless of the final result of operations, it is possible for both 
 *           implementations to get a {@link String} representation in Cartesian {@link #cartesianForm()},
 *            polar {@link #polarForm()}, and exponential {@link #eulerianForm()} forms 
 *            of the same number.</li>
 *       <li> The {@link #toString()} method returns a polar representation only 
 *            if this {@code Complex} is defined in polar form. Otherwise, it returns 
 *            a Cartesian representation.</li>
 *     </ul>
 *   <li> Other methods implemented differently:</li>
 *     <ul>
 *       <li> {@link #conjugate()}, {@link #negative()}, {@link #reciprocal()}</li>
 *       <li> {@link #hasRealOnly()}, {@link #hasImaginaryOnly()}, {@link #hasNullArgument()}</li>
 *       <li> {@link #isZero()}, {@link #isOne()}</li>
 *       <li> {@link #equals(java.lang.Object)}</li>
 *     </ul>
 * </ul>
 * <p>
 * 
 * @see ComplexNumbers
 * @author Nicolas Scalese
 */
public interface Complex extends Cloneable {
    
    /**
     * Returns the real part of this complex number.
     *
     * @return the real part of this complex number
     */
    double realValue();
    
    /**
     * Returns the imaginary part of this complex number.
     *
     * @return the imaginary part of this complex number
     */
    double imaginaryValue();
    
    /**
     * Returns the modulus of this complex number, the modulus of 
     * {@code (a,b)} vector in the Gaussian plan.
     *
     * @return the modulus of this complex number
     */
    double modulusValue();
    
    /**
     * Returns the main argument (angle) of this complex number, 
     * the angulus formed between {@code (a,b)} vector and the {@code X} axis.
     * <p>
     * @apiNote
     * Values of {@code theta} angle, between -PI (excluded) and PI (included): 
     * <ul>
     *   <li> {@code z = a + 0i}: </li>
     *        {@code theta = 0}
     *   <li> I or IV quadrant: </li> 
     *        {@code theta = arctg(b/a)}
     *   <li> II quadrant: </li>
     *        {@code theta = arctg(b/a) + pi}
     *   <li> III: </li>
     *        {@code theta = arctg(b/a) - pi}
     *   <li> 90° angulus: </li>
     *        {@code theta = pi/2}
     *   <li> 270° angulus: </li>
     *        {@code theta = (3/2)*pi}
     * </ul>
     * @return the main argument of this complex number
     * @see Math#atan2(double, double) 
     */
    double mainArgumentValue();
    
    /**
     * Returns the main argument (angle) of this complex number, 
     * a value between 0 (included) and 2*PI (excluded).
     * @return 
     */
    double mainArgumentValue2();
    
    /**
     * Returns the conjugate of this complex number.
     *
     * @return the conjugate of this complex number
     */
    Complex conjugate();
    
    /**
     * Returns the negative of this complex number: {@code -z = - a - i*b}
     *
     * @return the negative of this complex number
     */
    Complex negative();
    
    // -------------------------------------------------------------------------
    
    /**
     * Checks if this complex number is zero.
     *
     * @return {@code true} if this complex number is zero, {@code false} otherwise
     * @see com.nick.math.FloatingPoint#approxZero(double) 
     * @see #multiplyBy(com.nick.math.complex.Complex) 
     */
    boolean isZero();
    
    /**
     * Checks if this complex number is zero or approximates zero, within
     * the tolerance of:  {@code 4 *} {@link com.nick.math.FloatingPoint#DOUBLE_EPS}
     *
     * @return {@code true} if this complex number is zero, {@code false} otherwise
     * @see com.nick.math.FloatingPoint#approxZero(double) 
     * @see #multiplyBy(com.nick.math.complex.Complex) 
     */
    boolean isZero(double eps);
    
    /**
     * Checks if this complex number is one.
     *
     * @return {@code true} if this complex number is one, {@code false} otherwise
     * @see #multiplyBy(com.nick.math.complex.Complex) 
     */
    boolean isOne();
    
    /**
     * Checks if this complex number has only a real part (imaginary part is zero).
     * 
     * @return {@code true} if this complex number has only a real part, {@code false} otherwise
     */
    boolean hasRealOnly();
    
    /**
     * Checks if this complex number has only a real part (imaginary part is zero), 
     * within the tolerance of the specified epsilon value.
     * 
     * @param eps the tolerance for checking if the imaginary part is effectively zero
     * @return {@code true} if this complex number has only a real part, {@code false} otherwise
     */
    boolean hasRealOnly(double eps);
    
    /**
     * Checks if this complex number has only an imaginary part (real part is zero).
     * 
     * @return {@code true} if this complex number has only an imaginary part, {@code false} otherwise
     */
    boolean hasImaginaryOnly();
    
    /**
     * Checks if this complex number has only an imaginary part (real part is zero),
     * within the tolerance of the specified epsilon value.
     * 
     * @param eps the tolerance for checking if the real part is effectively zero
     * @return {@code true} if this complex number has only an imaginary part, {@code false} otherwise
     */
    boolean hasImaginaryOnly(double eps);
    
    /**
     * Checks if this complex number has a null argument (main argument is zero).
     *
     * @return {@code true} if this complex number has a null argument, {@code false} otherwise
     */
    boolean hasNullArgument();
    
    /**
     * Checks if this complex number has a null argument (main argument is zero),
     * within the tolerance of the specified epsilon value.
     * 
     * @param eps the tolerance for checking if the argument is effectively zero
     * @return {@code true} if the argument of this complex number is zero, {@code false} otherwise
     */
    boolean hasNullArgument(double eps);
    
    // -------------------------------------------------------------------------
    
    /**
     * Adds this complex number to another complex number.
     * This operation is performed in Cartesian form.
     * 
     * @param complex the complex number to be added
     * @return the sum of this complex number and the specified complex number
     */
    Complex plus(Complex complex);
  
    /**
     * Adds an amount to the real part of this complex number.
     * 
     * @param amount the amount to add to the real part
     * @return a new {@code Complex} representing the sum of this complex number and the real part
     */
    Complex plusReal(double amount);
    
    /**
     * Adds an amount to the imaginary part of this complex number.
     * 
     * @param amount the amount to add to the imaginary part
     * @return a new {@code Complex} representing the sum of this complex number and the imaginary part
     */
    Complex plusImaginary(double amount);
    
    /**
     * Subtracts another complex number from this complex number.
     * This operation is performed in Cartesian form.
     *
     * @param complex the complex number to be subtracted from this complex number
     * @return the difference between this complex number and the specified complex number
     */
    Complex minus(Complex complex);
    
    /**
     * Subtracts an amount from the real part of this complex number.
     * 
     * @param amount the amount to subtract from the real part
     * @return a new {@code Complex} representing the difference between this complex number and the real part
     */
    Complex minusReal(double amount);
    
    /**
     * Subtracts an amount from the imaginary part of this complex number.
     * 
     * @param amount the amount to subtract from the imaginary part
     * @return a new {@code Complex} representing the difference between this complex number and the imaginary part
     */
    Complex minusImaginary(double amount);
    
    /**
     * Multiplies this complex number by another complex number.
     *
     * @param complex the complex number to multiply this complex number by
     * @return the product of this complex number and the specified complex number
     */
    Complex multiplyBy(Complex complex);
    
    /**
     * Multiplies the real part of this complex number by an amount.
     * 
     * @param amount the amount to multiply the real part by
     * @return a new {@code Complex} representing the result of the multiplication
     */
    Complex multiplyByReal(double amount);
    
    /**
     * Multiplies the imaginary part of this complex number by an amount.
     * 
     * @param amount the amount to multiply the imaginary part by
     * @return a new {@code Complex} representing the result of the multiplication
     */
    Complex multiplyByImaginary(double amount);
    
    /**
     * Divides this complex number by another complex number.
     *
     * @param complex the complex number to divide this complex number by
     * @return the quotient of this complex number and the specified complex number
     * @throws ArithmeticException if the divisor is {@code Complex} zero ({@code 0 + 0i})
     * @see #isZero()
     */
    Complex divideBy(Complex complex);
    
    /**
     * Divides the real part of this complex number by an amount.
     * 
     * @param amount the amount to divide the real part by
     * @return a new {@code Complex} representing the result of the division
     * @throws ArithmeticException if {@code amount} is zero
     */
    Complex divideByReal(double amount);
    
    /**
     * Divides the imaginary part of this complex number by an amount.
     * 
     * @param amount the amount to divide the imaginary part by
     * @return a new {@code Complex} representing the result of the division
     * @throws ArithmeticException if {@code amount} is zero
     */
    Complex divideByImaginary(double amount);
    
    /**
     * Returns the reciprocal (multiplicative inverse) of this complex number.
     * 
     * @return the reciprocal of this complex number
     * @throws ArithmeticException if this complex number is {@code Complex} zero ({@code 0 + 0i})
     */
    Complex reciprocal();
    
    // -------------------------------------------------------------------------
    
    /**
     * Raises this complex number to the power of the specified exponent.
     * This operation is performed in Polar form.
     * 
     * @param exponent the exponent to raise this complex number to
     * @return this complex number raised to the specified power
     */
    Complex pow(double exponent);
    
    /**
     * Computes the square root of this complex number, selecting a specific root 
     * based on the value of {@code k}.
     * This operation is performed in Polar form.
     *
     * @param k the specific root to compute (0 <= k < 2)
     * @return the k-th square root of this complex number as a new {@code Complex}
     * @throws ArithmeticException if {@code k} is out of bounds (not in [0, 2))
     * @see #root(int, int)
     */
    default Complex sqrt(int k) {
        return this.root(2, k);
    }
    
    /**
     * Computes all square roots of this complex number.
     * This operation is performed in Polar form.
     *
     * @return an array of {@code Complex} numbers representing all square roots of this complex number
     * @see #allRoots(int)
     */
    default Complex[] allSqrts() {
        return this.allRoots(2);
    }
    
    /**
     * Computes the cube root of this complex number, selecting a specific root 
     * based on the value of {@code k}.
     * This operation is performed in polar form.
     *
     * @param k the specific root to compute (0 <= k < 3)
     * @return the k-th cube root of this complex number as a new {@code Complex}
     * @throws ArithmeticException if {@code k} is out of bounds (not in [0, 3))
     * @see #root(int, int)
     */
    default Complex cbrt(int k) {
        return this.root(3, k);
    }
    
    /**
     * Computes all cube roots of this complex number.
     * This operation is performed in polar form.
     *
     * @return an array of {@code Complex} numbers representing all cube roots of this complex number
     * @see #allRoots(int)
     */
    default Complex[] allCbrts() {
        return this.allRoots(3);
    }
    
    /**
     * Computes the specified n-th root of this complex number.
     * This operation is performed in Polar form.
     * 
     * @param n the degree of the root (must be greater than 0)
     * @param k the specific root to compute (0 <= k < n)
     * @return the k-th n-th root of this complex number as a new {@code Complex}
     * @throws IllegalArgumentException if {@code n} is less than or equal to 0
     * @throws ArithmeticException if {@code k} is out of bounds (not in [0, n))
     */
    Complex root(int n, int k);
    
    /**
     * Computes all n-th roots of this complex number.
     * This operation is performed in Polar form.
     * 
     * @param n the degree of the root (must be greater than 0)
     * @return an array of {@code Complex} numbers representing all n-th roots of this complex number
     * @throws IllegalArgumentException if {@code n} is less than or equal to 0
     */
    Complex[] allRoots(int n);
    
    // -------------------------------------------------------------------------
    
    /**
     * Returns a string representation of this complex number.
     * If this complex number is defined in polar form, the polar representation is returned.
     * Otherwise, the Cartesian representation is returned.
     *
     * @return a string representation of this complex number
     */
    @Override
    String toString();
    
    /**
     * Returns a string representation of this complex number in Cartesian form.
     *
     * @return a string representation of this complex number in Cartesian form
     */
    String cartesianForm();
    
    /**
     * Returns a string representation of this complex number in Cartesian form
     * formatted with the specified {@code DecimalFormat}.
     *
     * @param df the {@code DecimalFormat} object to format the real and imaginary parts
     * @return a formatted string representation of this complex number in Cartesian form
     */
    String cartesianForm(DecimalFormat df);
    
    /**
     * Returns the Cartesian coordinates of this complex number as a string
     * in the form: {@code (a, b)} where {@code a} is the real part and {@code b} is the imaginary part.
     *
     * @return a string representation of the Cartesian coordinates of this complex number
     */
    String cartesianCoordinates();
    
    /**
     * Returns the Cartesian coordinates of this complex number as a string
     * in the form: {@code (a, b)} where {@code a} is the real part and {@code b} is the imaginary part,
     * formatted with the specified {@code DecimalFormat}.
     *
     * @param df the {@code DecimalFormat} object to format the real and imaginary parts
     * @return a formatted string representation of the Cartesian coordinates of this complex number
     */
    String cartesianCoordinates(DecimalFormat df);
    
    /**
     * Returns a string representation of this complex number in polar form.
     *
     * @return a string representation of this complex number in polar form
     */
    String polarForm();
    
    /**
     * Returns a string representation of this complex number in polar form
     * formatted with the specified {@code DecimalFormat}.
     *
     * @param df the {@code DecimalFormat} object to format the modulus and argument
     * @return a formatted string representation of this complex number in polar form
     */
    String polarForm(DecimalFormat df);
    
    /**
     * Returns the polar coordinates of this complex number as a string
     * in the form: {@code (r, θ)} where {@code r} is the modulus and {@code θ} is the argument.
     *
     * @return a string representation of the polar coordinates of this complex number
     */
    String polarCoordinates();
    
    /**
     * Returns the polar coordinates of this complex number as a string
     * in the form: {@code (r, θ)} where {@code r} is the modulus and {@code θ} is the argument,
     * formatted with the specified {@code DecimalFormat}.
     *
     * @param df the {@code DecimalFormat} object to format the modulus and argument
     * @return a formatted string representation of the polar coordinates of this complex number
     */
    String polarCoordinates(DecimalFormat df);
    
    /**
     * Returns a string representation of this complex number in exponential (Eulerian) form.
     *
     * @return a string representation of this complex number in exponential form
     */
    String eulerianForm();
    
    /**
     * Returns a string representation of this complex number in exponential (Eulerian) form
     * formatted with the specified {@code DecimalFormat}.
     *
     * @param df the {@code DecimalFormat} object to format the modulus and argument
     * @return a formatted string representation of this complex number in exponential form
     */
    String eulerianForm(DecimalFormat df);
    
    // -------------------------------------------------------------------------
    
    /**
     * Compares this complex number to the specified object.
     * Returns {@code true} if the specified object is a complex number with 
     * the same real and imaginary parts (or same modulus and main argument) 
     * as this complex number, within a tolerance of {@code 4 *} {@link com.nick.math.FloatingPoint#DOUBLE_EPS}.
     *
     * @param complex the object to compare with this complex number
     * @return {@code true} if the specified object is a complex number equal to this one, {@code false} otherwise
     * @see com.nick.math.FloatingPoint#approxEqual(double, double)
     */
    @Override
    boolean equals(Object complex);
    
    /**
     * Compares this complex number to another complex number within the specified tolerance.
     * Returns {@code true} if the real and imaginary parts (or modulus and main argument) 
     * of the two complex numbers are approximately equal, within the given tolerance {@code epsilon}.
     *
     * @param complex the complex number to compare with this complex number
     * @param epsilon the tolerance for comparing the two complex numbers
     * @return {@code true} if the specified complex number is approximately equal to this one, {@code false} otherwise
     * @see com.nick.math.FloatingPoint#approxEqual(double, double, double)
     */
    boolean equals(Complex complex, double epsilon);
    
    /**
     * Returns the hash code value for this complex number.
     * The hash code is computed based on the real and imaginary parts of this number.
     *
     * @return the hash code value for this complex number
     */
    @Override
    int hashCode();
    
    /**
     * Creates and returns a copy (clone) of this complex number.
     *
     * @return a clone of this complex number
     */
    Object clone();
}