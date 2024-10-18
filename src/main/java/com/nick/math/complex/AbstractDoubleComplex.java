package com.nick.math.complex;

import static java.lang.Math.PI;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Common (partial) implementation for cartesian form and polar form 
 * of a {@link Complex} number.
 * 
 * @see Complex
 * @see DoubleCartesianComplex
 * @see DoublePolarComplex
 * @author Nicolas Scalese
 */
abstract class AbstractDoubleComplex implements Complex {

    @Override
    public final double mainArgumentValue2() {
        double angle = this.mainArgumentValue();
        if (angle < 0) {
            angle += 2 * PI;
        }
        return angle;
    }
    
    // -------------------------------------------------------------------------
    
    private Complex plus(double otherReal, double otherImaginary) {
        double real = this.realValue() + otherReal;
        double imaginary = this.imaginaryValue() + otherImaginary;
        return new DoubleCartesianComplex(real, imaginary);
    }
    
    @Override
    public final Complex plus(Complex complex) {
        return this.plus(complex.realValue(), complex.imaginaryValue());
    }

    @Override
    public final Complex plusReal(double amount) {
        return this.plus(amount, 0);
    }

    @Override
    public final Complex plusImaginary(double amount) {
        return this.plus(0, amount);
    }

    @Override
    public final Complex minus(Complex complex) {
        return this.plus(- complex.realValue(), - complex.imaginaryValue());
    }

    @Override
    public final Complex minusReal(double amount) {
        return this.plus(- amount, 0);
    }

    @Override
    public final Complex minusImaginary(double amount) {
        return this.plus(0, - amount);
    }
    
    // -------------------------------------------------------------------------

    @Override
    public final Complex pow(double exponent) {
        double modulus = Math.pow(this.modulusValue(), exponent);
        double angulus = exponent * this.mainArgumentValue();
        return new DoublePolarComplex(modulus, angulus);
    }
    
    
    @Override
    public final Complex nThRoot(int rootIndex, int k) {
        if (rootIndex <= 0) {
            throw new IllegalArgumentException();
        }
        if ((k < 0) || (k >= rootIndex)) {
            throw new ArithmeticException();
        }
        
        double modulus = Math.pow(this.modulusValue(), 1. / rootIndex);
        double angulus = (this.mainArgumentValue() + (2 * k * Math.PI)) / rootIndex;
        return new DoublePolarComplex(modulus, angulus);
    }
    
    @Override
    public final Complex[] allNThRoots(int rootIndex) {
        Complex[] allRoots = new Complex[rootIndex];
        
        for (int k = 0; k < rootIndex; k++) {
            allRoots[k] = this.nThRoot(rootIndex, k);
        }
        return allRoots;
    } 
    
    // -------------------------------------------------------------------------
    
    @Override
    public final String cartesianForm() {
        //  -3 + 5i
        //  +3 - 5i
        StringBuilder sb = new StringBuilder();
        return sb.append(signumOf(this.realValue()))
                .append(Math.abs(this.realValue()))
                .append(' ')
                .append(signumOf(this.imaginaryValue())).append(' ')
                .append(Math.abs(this.imaginaryValue())).append('i')
                .toString();
    }
    
    @Override
    public final String cartesianForm(DecimalFormat df) {
        String realAbsString = df.format(Math.abs(this.realValue()));
        String imaginaryAbsString = df.format(Math.abs(this.imaginaryValue()));
        
        StringBuilder sb = new StringBuilder();
        return sb.append(signumOf(this.realValue()))
                .append(realAbsString)
                .append(' ')
                .append(signumOf(this.imaginaryValue())).append(' ')
                .append(imaginaryAbsString).append('i')
                .toString();
    }
    
    private char signumOf(double d) {
        if (d >= 0) {
            return '+';
        }
        return '-';
    }
    
    @Override
    public final String cartesianCoordinates() {
        //  (-3, 5)
        //  (3, -5)
        StringBuilder sb = new StringBuilder();
        return sb.append('(').append(this.realValue())
                .append(", ")
                .append(this.imaginaryValue()).append(')')
                .toString();
    }
    
    @Override
    public final String cartesianCoordinates(DecimalFormat df) {
        String realString = df.format(this.realValue());
        String imaginaryString = df.format(this.imaginaryValue());
        
        StringBuilder sb = new StringBuilder();
        return sb.append('(').append(realString)
                .append(", ")
                .append(imaginaryString).append(')')
                .toString();
    }

    @Override
    public final String polarForm() {
        //  1 * (cos(2.094) + i*sen(2.094))   // argument = (2/3) * PI
        //  1 * (cos(4.188) + i*sen(4.188))   // argument = (4/3) * PI
        StringBuilder sb = new StringBuilder();
        return sb.append(this.modulusValue())
                .append(" * (cos(").append(this.mainArgumentValue()).append(')')
                .append(" + i*sin(").append(this.mainArgumentValue()).append("))")
                .toString();
    }
    
    @Override
    public final String polarForm(DecimalFormat df) {
        String modulusString = df.format(this.modulusValue());
        String mainArgumentString = df.format(this.mainArgumentValue());
        
        StringBuilder sb = new StringBuilder();
        return sb.append(modulusString)
                .append(" * (cos(").append(mainArgumentString).append(')')
                .append(" + i*sin(").append(mainArgumentString).append("))")
                .toString();
    }
    
    @Override
    public final String polarCoordinates() {
        //  (r= 1, theta= 2.094)   // (2/3) * PI
        //  (r= 1, theta= 4.188)   // (4/3) * PI
        StringBuilder sb = new StringBuilder();
        return sb.append("(r= ").append(this.modulusValue())
                .append(", theta= ").append(this.mainArgumentValue()).append(')')
                .toString();
    }
    
    @Override
    public final String polarCoordinates(DecimalFormat df) {
        String modulusString = df.format(this.modulusValue());
        String mainArgumentString = df.format(this.mainArgumentValue());
        
        StringBuilder sb = new StringBuilder();
        return sb.append("(r= ").append(modulusString)
                .append(", theta= ").append(mainArgumentString).append(')')
                .toString();
    }
    
    @Override
    public final String eulerianForm() {
        //  1 * e^(2.094i)   // argument = (2/3) * PI
        //  1 * e^(4.188i)   // argument = (4/3) * PI
        StringBuilder sb = new StringBuilder();
        return sb.append(this.modulusValue())
                .append(" * e^ (")
                .append(this.mainArgumentValue()).append("i)")
                .toString();
    }
    
    @Override
    public final String eulerianForm(DecimalFormat df) {
        String modulusString = df.format(this.modulusValue());
        String mainArgumentString = df.format(this.mainArgumentValue());
        
        StringBuilder sb = new StringBuilder();
        return sb.append(modulusString)
                .append(" * e^ (")
                .append(mainArgumentString).append("i)")
                .toString();
    }
    
    // -------------------------------------------------------------------------
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Complex)) {
            return false;
        }
        
        Complex complex = (Complex) o;
        return ((this.realValue() == complex.realValue()) && 
                (this.imaginaryValue() == complex.imaginaryValue())) 
                    || 
                ((this.modulusValue() == complex.modulusValue()) && 
                (this.mainArgumentValue() == complex.mainArgumentValue()));
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hash(this.realValue());
        hash = 97 * hash + Objects.hash(this.imaginaryValue());
        return hash;
    }
    
    // -------------------------------------------------------------------------
    
    @Override
    public abstract Object clone();
    
}