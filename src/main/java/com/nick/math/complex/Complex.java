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
 * @apiNote
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
 *       <li> {@link #equals()}</li>
 *     </ul>
 * </ul>
 * 
 * @author Nicolas Scalese
 */
public interface Complex {
    
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
     * Values of {@code theta} angle: 
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
     */
    double mainArgumentValue();
    
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
    
    boolean hasRealOnly(double eps);
    
    /**
     * Checks if this complex number has only an imaginary part (real part is zero).
     *
     * @return {@code true} if this complex number has only an imaginary part, {@code false} otherwise
     */
    boolean hasImaginaryOnly();
    
    boolean hasImaginaryOnly(double eps);
    
    /**
     * Checks if this complex number has a null argument (main argument is zero).
     *
     * @return {@code true} if this complex number has a null argument, {@code false} otherwise
     */
    boolean hasNullArgument();
    
    boolean hasNullArgument(double eps);
    
    // -------------------------------------------------------------------------
    
    /**
     * Adds this complex number to another complex number.
     *
     * @param complex the complex number to be added to this complex number
     * @return the sum of this complex number and the specified complex number
     */
    Complex plus(Complex complex);
  
    Complex plusReal(double amount);
    
    Complex plusImaginary(double amount);
    
    /**
     * Subtracts another complex number from this complex number.
     *
     * @param complex the complex number to be subtracted from this complex number
     * @return the difference between this complex number and the specified complex number
     */
    Complex minus(Complex complex);
    
    Complex minusReal(double amount);
    
    Complex minusImaginary(double amount);
    
    /**
     * Multiplies this complex number by another complex number.
     *
     * @param complex the complex number to multiply this complex number by
     * @return the product of this complex number and the specified complex number
     */
    Complex multiplyBy(Complex complex);
    
    Complex multiplyByReal(double amount);
    
    Complex multiplyByImaginary(double amount);
    
    /**
     * Divides this complex number by another complex number.
     *
     * @param complex the complex number to divide this complex number by
     * @return the quotient of this complex number and the specified complex number
     * @throws ArithmeticException if the divisor is {@code Complex} zero ({@code 0 + 0i})
     * @see #isZero()
     */
    Complex divideFor(Complex complex);
    
    Complex divideForReal(double amount);
    
    Complex divideForImaginary(double amount);
    
    /**
     * Returns the reciprocal of this complex number.
     *
     * @return the reciprocal of this complex number
     */
    Complex reciprocal();
    
    // -------------------------------------------------------------------------
    
    /**
     * Raises this complex number to the power of the specified exponent.
     *
     * @param exponent the exponent to raise this complex number to
     * @return this complex number raised to the specified power
     */
    Complex pow(double exponent);
    
    /**
     * Computes the specified n-th root of this complex number.
     *
     * @param n the degree of the root
     * @param k the specific root to be computed (0 <= k < n)
     * @return the k-th n-th root of this complex number
     */
    Complex nThRoot(int n, int k);
    
    /**
     * Computes all n-th roots of this complex number.
     *
     * @param n the degree of the roots to be computed
     * @return an array containing all n-th roots of this complex number
     */
    Complex[] allNThRoots(int n);
    
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
    
    String cartesianForm(DecimalFormat df);
    
    String cartesianCoordinates();
    
    String cartesianCoordinates(DecimalFormat df);
    
    /**
     * Returns a string representation of this complex number in polar form.
     *
     * @return a string representation of this complex number in polar form
     */
    String polarForm();
    
    String polarForm(DecimalFormat df);
    
    String polarCoordinates();
    
    String polarCoordinates(DecimalFormat df);
    
    /**
     * Returns a string representation of this complex number in exponential (Eulerian) form.
     *
     * @return a string representation of this complex number in exponential form
     */
    String eulerianForm();
    
    String eulerianForm(DecimalFormat df);
    
    // -------------------------------------------------------------------------
    
    /**
     * Checks if this complex number is equal to another object, 
     * within the tolerance of:  {@code 4 *} {@link com.nick.math.FloatingPoint#DOUBLE_EPS}
     *
     * @param complex the object to compare with this complex number
     * @return {@code true} if the specified object is a complex number equal to this complex number, {@code false} otherwise
     * @see com.nick.math.FloatingPoint#approxEqual(double, double) 
     */
    @Override
    boolean equals(Object complex);
    
    /**
     * Checks if this complex number is equal to another complex number within a given tolerance.
     *
     * @param complex the complex number to compare with this complex number
     * @param epsilon the tolerance for comparing the two complex numbers
     * @return {@code true} if the specified complex number is equal to this complex number within the given tolerance, {@code false} otherwise
     * @see com.nick.math.FloatingPoint#approxEqual(double, double, double) 
     */
    boolean equals(Complex complex, double epsilon);
    
    @Override
    int hashCode();
    
    public Object clone();
    
}
