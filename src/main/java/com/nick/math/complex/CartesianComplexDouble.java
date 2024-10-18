package com.nick.math.complex;

import com.nick.math.FloatingPoint;
import static com.nick.math.complex.ComplexNumbers.ZERO_COMPLEX_CARTESIAN;
import static java.lang.Math.PI;

/**
 * A complex number in its cartesian form: {@code z = a + i*b}, where:
 * <ul>
 *   <li> {@code a} = real part
   <li> {@code a} = imaginary part
 </ul>
 * 
 * @see Complex
 * @see AbstractComplexDouble
 * @author Nicolas Scalese
 */
class CartesianComplexDouble extends AbstractComplexDouble {
    
    private final double real;
    private final double imaginary;

    public CartesianComplexDouble(double real) {
        this.real = real;
        this.imaginary = 0;
    }
    
    public CartesianComplexDouble(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }
    
    // -------------------------------------------------------------------------

    @Override
    public double realValue() {
        return this.real;
    }

    @Override
    public double imaginaryValue() {
        return this.imaginary;
    }

    @Override
    public double modulusValue() {
        double real2plusImg2 = this.re2PlusIm2(this.real, this.imaginary);
        return Math.sqrt(real2plusImg2);
    }
    
    private double re2PlusIm2(double real, double imaginary) {
        return (real * real) + (imaginary * imaginary);
    }

    @Override
    public double mainArgumentValue() {
        double angle = Math.atan2(this.imaginary, this.real);
        if (angle == -PI) {
            return PI;
        }
        return angle; 
    }
    
    @Override
    public Complex conjugate() {
        double imaginaryNegated = (this.imaginary == 0) ? 0 : -this.imaginary;
        return new CartesianComplexDouble(this.real, imaginaryNegated);
    }
    
    @Override
    public Complex negative() {
        double realNegated = (this.real == 0) ? 0 : -this.real;
        double imaginaryNegated = (this.imaginary == 0) ? 0 : -this.imaginary;
        return new CartesianComplexDouble(realNegated, imaginaryNegated);
    }
    
    // -------------------------------------------------------------------------

    @Override
    public boolean isZero() {
        return (this.real == 0) && (this.imaginary == 0);
    }
    
    public boolean isZero(double eps) {
        return (FloatingPoint.approxZero(this.real, eps)) 
                && (FloatingPoint.approxZero(this.imaginary, eps));
    }
    
    @Override
    public boolean isOne() {
//        return (this.real == 1) && (this.imaginary == 0);
        return (this.real == 1) && (FloatingPoint.approxZero(this.imaginary));
    }

    @Override
    public boolean hasRealOnly() {
        return (this.imaginary == 0);
    }
    
    @Override
    public boolean hasRealOnly(double eps) {
        return FloatingPoint.approxZero(this.imaginary, eps);
    }
    
    @Override
    public boolean hasImaginaryOnly() {
        return (this.real == 0);
    }
    
    @Override
    public boolean hasImaginaryOnly(double eps) {
        return FloatingPoint.approxZero(this.real, eps);
    }

    @Override
    public boolean hasNullArgument() {
        return (this.real >= 0) && (this.imaginary == 0);
    }
    
    @Override
    public boolean hasNullArgument(double eps) {
        return (this.real >= -eps) && 
                (FloatingPoint.approxZero(this.imaginary, eps));
    }
    
    // -------------------------------------------------------------------------
    
    @Override
    public Complex multiplyBy(Complex complex) {
        if (this.isZero() || complex.isZero()) {
            return ZERO_COMPLEX_CARTESIAN;
        }
        if (this.isOne()) {
            return complex;
        }
        if (complex.isOne()) {
            return this;
        }
        
        if (complex.hasRealOnly()) {
            return this.multiplyByReal(complex.realValue());
        }
        if (complex.hasImaginaryOnly()) {
            return this.multiplyByImaginary(complex.imaginaryValue());
        }
        
        
        double a1 = this.real;
        double b1 = this.imaginary;
        double a2 = complex.realValue();
        double b2 = complex.imaginaryValue();
        
        double real = (a1 * a2) - (b1 * b2);
        double imaginary = (a1 * b2) + (a2 * b1);
        return new CartesianComplexDouble(real, imaginary);
    }
    
    @Override
    public Complex multiplyByReal(double amount) {
        if (this.isZero() || (amount == 0)) {
            // multiply for: 0 + 0i
            return ZERO_COMPLEX_CARTESIAN;
        }
        if (amount == 1) {
            return this;
        }
        
        // (a + bi)*(c + 0i) = ac + (bc)*i
        double real = this.real * amount;
        double imaginary = this.imaginary * amount;
        return new CartesianComplexDouble(real, imaginary);
    }

    @Override
    public Complex multiplyByImaginary(double amount) {
        if (this.isZero() || (amount == 0)) {
            // multiply for: 0 + 0i
            return ZERO_COMPLEX_CARTESIAN;
        }
        
        // (a + bi)*(0 + di) = -bd + (ad)*i
        double real = - this.imaginary * amount;
        double imaginary = this.real * amount;
        return new CartesianComplexDouble(real, imaginary);
    }
    
    // -------------------------------------------------------------------------
    
    private Complex divideBy(double otherReal, double otherImaginary) {
        if ((otherReal == 0) && (otherImaginary == 0)) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        
        double a1 = this.realValue();
        double b1 = this.imaginaryValue();
        double a2 = otherReal;
        double b2 = otherImaginary;
        double real2plusImg2 = this.re2PlusIm2(otherReal, otherImaginary);
        
        double real = ((a1 * a2) + (b1 * b2)) / real2plusImg2;
        double imaginary = ((b1 * a2) - (a1 * b2)) / real2plusImg2;
        return new CartesianComplexDouble(real, imaginary);
    }
    
    @Override
    public Complex divideBy(Complex complex) {
        if (complex.isZero()) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        if (this.isZero()) {
            return ZERO_COMPLEX_CARTESIAN;
        }
        if (complex.isOne()) {
            return this;
        }
        
        return this.divideBy(complex.realValue(), complex.imaginaryValue());
    }
    
    @Override
    public Complex divideByReal(double amount) {
        if (amount == 0) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        if (this.isZero()) {
            return ZERO_COMPLEX_CARTESIAN;
        }
        if (amount == 1) {
            return this;
        }

        // (a + bi)/(c + 0i) = a/c + (b/c)*i
        double real = this.real / amount;
        double imaginary = this.imaginary / amount;
        return new CartesianComplexDouble(real, imaginary);
    }

    @Override
    public Complex divideByImaginary(double amount) {
        if (amount == 0) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        if (this.isZero()) {
            return ZERO_COMPLEX_CARTESIAN;
        }
        
        // (a + bi)/(0 + di) = b/d - (a/d)*i
        double real = this.imaginary / amount;
        double imaginary = - this.real / amount;
        return new CartesianComplexDouble(real, imaginary);
    }
    
    
    @Override
    public Complex reciprocal() {
        if (this.isZero()) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        
        double real2plusImg2 = this.re2PlusIm2(this.real, this.imaginary);
        double real = this.real / real2plusImg2;
        double imaginary = - this.imaginary / real2plusImg2;
        return new CartesianComplexDouble(real, imaginary);
    }

    // -------------------------------------------------------------------------
    
    @Override
    public boolean equals(Complex complex, double epsilon) {
        double realDifference = Math.abs(this.real - complex.realValue());
        double imaginaryDifference = Math.abs(this.imaginary - complex.imaginaryValue());
        return ((realDifference < epsilon) && (imaginaryDifference < epsilon));
    }
    
    @Override
    public String toString() {
        return super.cartesianForm();
    }
    
    @Override
    public Object clone() {
        return new CartesianComplexDouble(this.real, this.imaginary);
    }
    
}