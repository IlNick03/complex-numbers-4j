package com.nick.math.complex;

import com.nick.math.FloatingPoint;
import static com.nick.math.complex.ComplexNumbers.ZERO_COMPLEX_POLAR;
import static java.lang.Math.PI;

/**
 * A complex number in its polar form: {@code z = r * (cos(theta) + i*sen(theta))} , where:
 * <ul>
 *   <li> {@code r} = modulus
 *   <li> {@code theta} = main argument, between -PI (excluded) and PI (included)
 </ul>
 * 
 * @see Complex
 * @see AbstractDoubleComplex
 * @author Nicolas Scalese
 */
class DoublePolarComplex extends AbstractDoubleComplex {
    
    private final double modulus;
    private final double argument;
    

    public DoublePolarComplex(double modulus, double angle) {
        if (modulus < 0) {
            throw new IllegalArgumentException("Modulus must be positive or equal to 0.");
        }
        if (modulus == 0) {
            this.modulus = 0;
            this.argument = 0;
            return;
        }
        
        this.modulus = modulus;
        
        angle = angle % (2 * PI);
        // Move the angle in range: (-PI, PI] , if necessary
        if (angle == PI || angle == -PI) {
            this.argument = PI;
        } else if (angle > PI) {
            this.argument = angle - (2 * PI);
        } else if (angle < -PI) {
            this.argument = angle + (2 * PI); 
        } else {
            this.argument = angle;
        }
    }
    
    public DoublePolarComplex(double real) {
        if (real >= 0) {
            this.modulus = real;
            this.argument = 0;
        } else {
            this.modulus = -real;
            this.argument = PI;
        }
    }
    
    // -------------------------------------------------------------------------

    @Override
    public double realValue() {
        return (this.modulus * Math.cos(this.argument));
    }

    @Override
    public double imaginaryValue() {
        return (this.modulus * Math.sin(this.argument));
    }

    @Override
    public double modulusValue() {
        return this.modulus;
    }

    @Override
    public double mainArgumentValue() {
        return this.argument;
    }

    @Override
    public Complex conjugate() {
        // real = modulus * cos(argument) = modulus * cos(-argument)
        // - imaginary = modulus * (- sin(argument)) = modulus * sin(-argument)
        double argumentNegated = (this.argument == 0) ? 0 : -this.argument;
        return new DoublePolarComplex(this.modulus, argumentNegated);
    }

    @Override
    public Complex negative() {
        // - real = modulus * (- cos(argument)) = modulus * cos(argument + pi)
        // - imaginary = modulus * (- sin(argument)) = modulus * sin(argument + pi)
        return new DoublePolarComplex(this.modulus, this.argument + PI);
    }

    // -------------------------------------------------------------------------

    @Override
    public boolean isZero() {
        return (this.modulus == 0);
    }
    
    public boolean isZero(double eps) {
        return (FloatingPoint.approxZero(this.modulus, eps));
    }
    
    @Override
    public boolean isOne() {
//        return (this.modulus == 1) && (this.argument == 0);
        return (this.modulus == 1) && (FloatingPoint.approxZero(this.argument));
    }
    
    @Override
    public boolean hasRealOnly() {
        return (this.argument == 0) || (this.argument == PI);
    }
    
    @Override
    public boolean hasRealOnly(double eps) {
        return (FloatingPoint.approxEqual(this.argument, 0, eps))
                || (FloatingPoint.approxEqual(this.argument, PI, eps));
    }
    
    @Override
    public boolean hasImaginaryOnly() {
        return (this.argument == 0.5 * PI) || (this.argument == 1.5 * PI);
    }
    
    @Override
    public boolean hasImaginaryOnly(double eps) {
        return (FloatingPoint.approxEqual(this.argument, 0.5 * PI, eps))
                || (FloatingPoint.approxEqual(this.argument, 1.5 * PI, eps));
    }

    @Override
    public boolean hasNullArgument() {
        return (this.argument == 0);
    }
    
    @Override
    public boolean hasNullArgument(double eps) {
        return (FloatingPoint.approxZero(this.argument, eps));
    }
    
    // -------------------------------------------------------------------------
    
    private Complex multiplyBy(double otherModulus, double otherArgument) {
        double modulus = this.modulus * otherModulus;
        double argument = this.argument + otherArgument;
        return new DoublePolarComplex(modulus, argument);
    }
    
    @Override
    public Complex multiplyBy(Complex complex) {
        if (this.isZero() || complex.isZero()) {
            return ZERO_COMPLEX_POLAR; 
        }
        if (this.isOne()) {
            return complex;
        }
        if (complex.isOne()) {
            return this;
        }
        
        return this.multiplyBy(complex.modulusValue(), complex.mainArgumentValue());
    }

    @Override
    public Complex multiplyByReal(double amount) {
        if (this.isZero() || (amount == 0)) {
            // multiply for: 0 + 0i
            return ZERO_COMPLEX_POLAR;
        }
        if (amount == 1) {
            return this;
        }
        
        if (amount > 0) {
            return this.multiplyBy(amount, 0);
        } else {
            return this.multiplyBy(amount, PI);
        }
    }

    @Override
    public Complex multiplyByImaginary(double amount) {
        if (this.isZero() || (amount == 0)) {
            // multiply for: 0 + 0i
            return ZERO_COMPLEX_POLAR;
        }
        
        if (amount > 0) {
            return this.multiplyBy(amount, PI/ 2.0);
        } else {
            return this.multiplyBy(amount, 1.5 * PI);
        }
    }
    
    // -------------------------------------------------------------------------
    
    private Complex divideFor(double otherModulus, double otherArgument) {
        if ((otherModulus == 0) && (otherArgument == 0)) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        
        double modulus = this.modulus / otherModulus;
        double argument = this.argument - otherArgument;
        return new DoublePolarComplex(modulus, argument);
    }

    @Override
    public Complex divideBy(Complex complex) {
        if (complex.isZero()) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        if (this.isZero()) {
            return ZERO_COMPLEX_POLAR;
        }
        if (complex.isOne()) {
            return this;
        }
        
        return this.divideFor(complex.modulusValue(), complex.mainArgumentValue());
    }

    @Override
    public Complex divideByReal(double amount) {
        if (amount == 0) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        if (this.isZero()) {
            return ZERO_COMPLEX_POLAR;
        }
        if (amount == 1) {
            return this;
        }
        
        if (amount > 0) {
            return this.divideFor(amount, 0);
        } else {
            // argument = theta1 - theta2 = theta1 - PI = theta1 + PI
            return this.divideFor(amount, - PI);
        }
    }

    @Override
    public Complex divideByImaginary(double amount) {
        if (amount == 0) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        if (this.isZero()) {
            return ZERO_COMPLEX_POLAR;
        }
        
        if (amount > 0) {
            // argument = theta1 - theta2 = theta1 - PI/2
            return this.divideFor(amount, PI/ 2.0);
        } else {
            // argument = theta1 - theta2 = theta1 - (3/2)*PI = theta1 + PI/2
            return this.divideFor(amount, -  PI/ 2.0);
        }
    }

    
    @Override
    public Complex reciprocal() {
        if (this.isZero()) {
            throw new ArithmeticException("Unable to divide by:  0 + 0i");
        }
        
        double modulus = 1.0 / this.modulus;
        double argument = - this.argument;
        return new DoublePolarComplex(modulus, argument);
    }
    
    // -------------------------------------------------------------------------
    
    @Override
    public boolean equals(Complex complex, double epsilon) {
        double modulusDifference = Math.abs(this.modulus - complex.modulusValue());
        double argumentDifference = Math.abs(this.argument - complex.mainArgumentValue());
        return ((modulusDifference < epsilon) && (argumentDifference < epsilon));
    }
    
    @Override
    public String toString() {
        return super.polarForm();
    }

    @Override
    public Object clone() {
        return new DoublePolarComplex(this.modulus, this.argument);
    }
    
}