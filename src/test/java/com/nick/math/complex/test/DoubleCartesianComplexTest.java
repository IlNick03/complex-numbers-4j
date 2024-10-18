package com.nick.math.complex.test;

import com.nick.math.complex.*;
import org.junit.jupiter.api.*;

public class DoubleCartesianComplexTest {

    // ---------------------------------------------------------------------- //
    //  Normal conditions
    // ---------------------------------------------------------------------- //
    
    @Test
    public void testConstructorAndGettersNormal() {
        Complex complex = ComplexNumbers.ofCartesianForm(3.0, 4.0); 
        Assertions.assertEquals(3.0, complex.realValue());
        Assertions.assertEquals(4.0, complex.imaginaryValue());
    }

    @Test
    public void testPlusNormal() {
        Complex c1 = ComplexNumbers.ofCartesianForm(1.0, 2.0); 
        Complex c2 = ComplexNumbers.ofCartesianForm(2.0, 3.0); 
        Complex result = c1.plus(c2);
        Assertions.assertEquals(3.0, result.realValue());
        Assertions.assertEquals(5.0, result.imaginaryValue());
    }

    @Test
    public void testPlusReal() {
        Complex complex = ComplexNumbers.ofCartesianForm(1.0, 2.0);
        Complex result = complex.plusReal(5.0);
        Assertions.assertEquals(6.0, result.realValue());
        Assertions.assertEquals(2.0, result.imaginaryValue());
    }

    @Test
    public void testPlusImaginary() {
        Complex complex = ComplexNumbers.ofCartesianForm(1.0, 2.0);
        Complex result = complex.plusImaginary(3.0);
        Assertions.assertEquals(1.0, result.realValue());
        Assertions.assertEquals(5.0, result.imaginaryValue());
    }

    @Test
    public void testMinusReal() {
        Complex complex = ComplexNumbers.ofCartesianForm(5.0, 3.0);
        Complex result = complex.minusReal(2.0);
        Assertions.assertEquals(3.0, result.realValue());
        Assertions.assertEquals(3.0, result.imaginaryValue());
    }

    @Test
    public void testMinusImaginary() {
        Complex complex = ComplexNumbers.ofCartesianForm(5.0, 3.0);
        Complex result = complex.minusImaginary(2.0);
        Assertions.assertEquals(5.0, result.realValue());
        Assertions.assertEquals(1.0, result.imaginaryValue());
    }

    @Test
    public void testMultiplyByNormal() {
        Complex c1 = ComplexNumbers.ofCartesianForm(1.0, 2.0); 
        Complex c2 = ComplexNumbers.ofCartesianForm(2.0, 3.0); 
        Complex result = c1.multiplyBy(c2);
        Assertions.assertEquals(-4.0, result.realValue());
        Assertions.assertEquals(7.0, result.imaginaryValue());
    }

    @Test
    public void testMultiplyByReal() {
        Complex complex = ComplexNumbers.ofCartesianForm(2.0, 3.0);
        Complex result = complex.multiplyByReal(4.0);
        Assertions.assertEquals(8.0, result.realValue());
        Assertions.assertEquals(12.0, result.imaginaryValue());
    }

    @Test
    public void testMultiplyByImaginary() {
        Complex complex = ComplexNumbers.ofCartesianForm(2.0, 3.0);
        Complex result = complex.multiplyByImaginary(5.0);
        Assertions.assertEquals(-15.0, result.realValue());
        Assertions.assertEquals(10.0, result.imaginaryValue());
    }

    @Test
    public void testDivideByReal() {
        Complex complex = ComplexNumbers.ofCartesianForm(4.0, 2.0);
        Complex result = complex.divideByReal(2.0);
        Assertions.assertEquals(2.0, result.realValue());
        Assertions.assertEquals(1.0, result.imaginaryValue());
    }

    @Test
    public void testDivideByImaginary() {
        Complex complex = ComplexNumbers.ofCartesianForm(3.0, 6.0);
        Complex result = complex.divideByImaginary(3.0);
        Assertions.assertEquals(2.0, result.realValue());
        Assertions.assertEquals(-1.0, result.imaginaryValue());
    }

    @Test
    public void testReciprocalNormal() {
        Complex complex = ComplexNumbers.ofCartesianForm(4.0, 3.0); 
        Complex result = complex.reciprocal();
        Assertions.assertEquals(0.16, result.realValue(), 1e-9);
        Assertions.assertEquals(-0.12, result.imaginaryValue(), 1e-9);
    }


    // ---------------------------------------------------------------------- //
    //  Peculiar conditions
    // ---------------------------------------------------------------------- //

    @Test
    public void testPlusWithZero() {
        Complex c1 = ComplexNumbers.ofCartesianForm(0.0, 0.0); 
        Complex c2 = ComplexNumbers.ofCartesianForm(2.0, 3.0); 
        Complex result = c1.plus(c2);
        Assertions.assertEquals(2.0, result.realValue());
        Assertions.assertEquals(3.0, result.imaginaryValue());
    }

    @Test
    public void testMultiplyByWithZero() {
        Complex c1 = ComplexNumbers.ofCartesianForm(0.0, 0.0); 
        Complex c2 = ComplexNumbers.ofCartesianForm(2.0, 3.0); 
        Complex result = c1.multiplyBy(c2);
        Assertions.assertEquals(0.0, result.realValue());
        Assertions.assertEquals(0.0, result.imaginaryValue());
    }

    @Test
    public void testArgumentOfPureImaginaryNumber() {
        Complex complex = ComplexNumbers.ofCartesianForm(0.0, 1.0); 
        Assertions.assertEquals(Math.PI / 2, complex.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testArgumentOfNegativeReal() {
        Complex complex = ComplexNumbers.ofCartesianForm(-1.0, 0.0); 
        Assertions.assertEquals(Math.PI, complex.mainArgumentValue(), 1e-9);
    }


    // ---------------------------------------------------------------------- //
    //  Anomalous conditions
    // ---------------------------------------------------------------------- //

    @Test
    public void testConstructorWithNaN() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(Double.NaN, 2.0);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(-3.5, Float.NaN);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(Double.NaN, Float.NaN);
        });
    }
    
    @Test
    public void testConstructorWithInfinity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(Double.POSITIVE_INFINITY, 2.0);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(-3.5, Double.POSITIVE_INFINITY);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(Double.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(Double.NEGATIVE_INFINITY, 2.0);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(-3.5, Double.NEGATIVE_INFINITY);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(Float.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        });
    }

    @Test
    public void testReciprocalOfZero() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            ComplexNumbers.ZERO_COMPLEX_CARTESIAN.reciprocal();
        });
    }

    @Test
    public void testInvalidRootIndex() {
        Complex complex = ComplexNumbers.ONE_COMPLEX_CARTESIAN;
        Assertions.assertThrows(ArithmeticException.class, () -> {
            complex.nThRoot(2, -1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            complex.nThRoot(0, 2);
        });
    }
}