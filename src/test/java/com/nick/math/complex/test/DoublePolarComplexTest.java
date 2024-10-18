package com.nick.math.complex.test;

import com.nick.math.complex.*;
import org.junit.jupiter.api.*;

public class DoublePolarComplexTest {

    // ---------------------------------------------------------------------- //
    //  Normal conditions
    // ---------------------------------------------------------------------- //

    @Test
    public void testConstructorAndGettersNormal() {
        Complex complex = ComplexNumbers.ofPolarForm(5.0, Math.PI / 4);
        Assertions.assertEquals(5.0, complex.modulusValue(), 1e-9);
        Assertions.assertEquals(Math.PI / 4, complex.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testPlusNormal() {
        Complex c1 = ComplexNumbers.ofPolarForm(1.0, Math.PI / 4);
        Complex c2 = ComplexNumbers.ofPolarForm(2.0, Math.PI / 3);
        Complex result = c1.plus(c2);
        
        Assertions.assertEquals((Math.sqrt(2) + 2) / 2, result.realValue(), 1e-9);
        Assertions.assertEquals((Math.sqrt(2) + 2 * Math.sqrt(3)) / 2, result.imaginaryValue(), 1e-9);
    }

    @Test
    public void testPlusReal() {
        Complex complex = ComplexNumbers.ofPolarForm(1.0, Math.PI / 4);
        Complex result = complex.plusReal(2.0);
        Assertions.assertEquals(2.70710678118, result.realValue(), 1e-9);
        Assertions.assertEquals(0.70710678118, result.imaginaryValue(), 1e-9);
    }

    @Test
    public void testPlusImaginary() {
        Complex complex = ComplexNumbers.ofPolarForm(1.0, Math.PI / 4);
        Complex result = complex.plusImaginary(2.0);
        Assertions.assertEquals(0.70710678118, result.realValue(), 1e-9);
        Assertions.assertEquals(2.70710678118, result.imaginaryValue(), 1e-9);
    }

    @Test
    public void testMinusReal() {
        Complex complex = ComplexNumbers.ofPolarForm(5.0, Math.PI / 4);
        Complex result = complex.minusReal(3.0);
        Assertions.assertEquals((5 * Math.sqrt(2) / 2) - 3, result.realValue(), 1e-9);
        Assertions.assertEquals(5 * Math.sqrt(2) / 2, result.imaginaryValue(), 1e-9);
    }

    @Test
    public void testMinusImaginary() {
        Complex complex = ComplexNumbers.ofPolarForm(5.0, Math.PI / 4);
        Complex result = complex.minusImaginary(2.0);
        Assertions.assertEquals(5 * Math.sqrt(2) / 2, result.realValue(), 1e-9);
        Assertions.assertEquals((5 * Math.sqrt(2) / 2) - 2, result.imaginaryValue(), 1e-9);
    }

    @Test
    public void testMultiplyByNormal() {
        Complex c1 = ComplexNumbers.ofPolarForm(1.0, Math.PI / 4);
        Complex c2 = ComplexNumbers.ofPolarForm(2.0, Math.PI / 3);
        Complex result = c1.multiplyBy(c2);
        Assertions.assertEquals(1.0 * 2.0, result.modulusValue(), 1e-9);
        Assertions.assertEquals(Math.PI / 4 + Math.PI / 3, result.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testMultiplyByReal() {
        Complex complex = ComplexNumbers.ofPolarForm(1.0, Math.PI / 4);
        Complex result = complex.multiplyByReal(3.0);
        Assertions.assertEquals(3.0, result.modulusValue(), 1e-9);
        Assertions.assertEquals(Math.PI / 4, result.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testMultiplyByImaginary() {
        Complex complex = ComplexNumbers.ofPolarForm(1.0, Math.PI / 4);
        Complex result = complex.multiplyByImaginary(2.0);
        Assertions.assertEquals(2.0, result.modulusValue(), 1e-9);
        Assertions.assertEquals(Math.PI / 4 + Math.PI / 2, result.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testDivideByReal() {
        Complex complex = ComplexNumbers.ofPolarForm(5.0, Math.PI / 4);
        Complex result = complex.divideByReal(2.0);
        Assertions.assertEquals(2.5, result.modulusValue(), 1e-9);
        Assertions.assertEquals(Math.PI / 4, result.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testDivideByImaginary() {
        Complex complex = ComplexNumbers.ofPolarForm(5.0, Math.PI / 4);
        Complex result = complex.divideByImaginary(2.0);
        Assertions.assertEquals(2.5, result.modulusValue(), 1e-9);
        Assertions.assertEquals(5 * Math.sqrt(2) / 4, result.realValue(), 1e-9);
        Assertions.assertEquals(-5 * Math.sqrt(2) / 4, result.imaginaryValue(), 1e-9);
    }

    @Test
    public void testReciprocalNormal() {
        Complex complex = ComplexNumbers.ofPolarForm(5.0, Math.PI / 3); 
        Complex result = complex.reciprocal();
        Assertions.assertEquals(1 / 5.0, result.modulusValue(), 1e-9);
        Assertions.assertEquals(-Math.PI / 3, result.mainArgumentValue(), 1e-9);
//        Assertions.assertEquals((5 * Math.PI) / 3, result.mainArgumentValue(), 1e-9);
    }


    // ---------------------------------------------------------------------- //
    //  Peculiar conditions
    // ---------------------------------------------------------------------- //

    @Test
    public void testPlusWithZero() {
        Complex c1 = ComplexNumbers.ofPolarForm(0.0, 0.0);
        Complex c2 = ComplexNumbers.ofPolarForm(2.0, Math.PI / 3);
        Complex result = c1.plus(c2);
        Assertions.assertEquals(2.0, result.modulusValue(), 1e-9);
        Assertions.assertEquals(Math.PI / 3, result.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testMultiplyByWithZero() {
        Complex c1 = ComplexNumbers.ofPolarForm(0.0, 0.0);
        Complex c2 = ComplexNumbers.ofPolarForm(2.0, Math.PI / 3);
        Complex result = c1.multiplyBy(c2);
        Assertions.assertEquals(0.0, result.modulusValue(), 1e-9);
        Assertions.assertEquals(0.0, result.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testArgumentOfPureImaginaryNumber() {
        Complex complex = ComplexNumbers.ofPolarForm(3.5, - Math.PI / 2);
        Assertions.assertEquals(- (Math.PI / 2), complex.mainArgumentValue(), 1e-9);
        Assertions.assertEquals(0, complex.realValue(), 1e-9);
        Assertions.assertEquals(-3.5, complex.imaginaryValue());
    }

    @Test
    public void testArgumentOfNegativeReal() {
        Complex complex = ComplexNumbers.ofPolarForm(4.2, Math.PI);
        Assertions.assertEquals(Math.PI, complex.mainArgumentValue(), 1e-9);
        Assertions.assertEquals(-4.2, complex.realValue());
        Assertions.assertEquals(0, complex.imaginaryValue(), 1e-9);
    }


    // ---------------------------------------------------------------------- //
    //  Anomalous conditions
    // ---------------------------------------------------------------------- //

    @Test
    public void testConstructorWithNegativeModulus() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(-5.0, Math.PI / 4);
        });
    }

    @Test
    public void testConstructorWithNaN() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(Double.NaN, Math.PI / 4);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(5.0, Double.NaN);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(Double.NaN, Double.NaN);
        });
    }

    @Test
    public void testConstructorWithInfinity() {
        // Testa la creazione di numeri complessi con valori infiniti
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(Double.POSITIVE_INFINITY, Math.PI / 4);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(5.0, Double.POSITIVE_INFINITY);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(Double.NEGATIVE_INFINITY, Math.PI / 4);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(5.0, Double.NEGATIVE_INFINITY);
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        });
    }

    @Test
    public void testReciprocalOfZero() {
        // Testa il calcolo del reciproco di zero
        Assertions.assertThrows(ArithmeticException.class, () -> {
            ComplexNumbers.ZERO_COMPLEX_POLAR.reciprocal();
        });
    }

    @Test
    public void testInvalidRootIndex() {
        // Testa i casi di indice non valido per la radice n-esima
        Complex complex = ComplexNumbers.ONE_COMPLEX_POLAR;
        Assertions.assertThrows(ArithmeticException.class, () -> {
            complex.root(2, -1);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            complex.root(0, 2);
        });
    }
}