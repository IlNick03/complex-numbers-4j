package com.nick.math.complex.test;

import com.nick.math.complex.*;
import java.util.*;
import org.junit.jupiter.api.*;


public class ComplexNumbersTest {


    // ---------------------------------------------------------------------- //
    //  Normal conditions
    // ---------------------------------------------------------------------- //

    @Test
    public void testOfRealNumber() {
        Complex complex = ComplexNumbers.of(5.0);
        Assertions.assertEquals(5.0, complex.realValue());
        Assertions.assertEquals(0.0, complex.imaginaryValue());
    }

    @Test
    public void testOfCartesianForm() {
        Complex complex = ComplexNumbers.ofCartesianForm(3.0, 4.0);
        Assertions.assertEquals(3.0, complex.realValue());
        Assertions.assertEquals(4.0, complex.imaginaryValue());
    }

    @Test
    void testOfPolarForm() {
        Complex complex = ComplexNumbers.ofPolarForm(5.0, Math.PI / 6);
        Assertions.assertEquals(5.0, complex.modulusValue());
        Assertions.assertEquals(Math.PI / 6, complex.mainArgumentValue());
        Assertions.assertEquals(5.0 * Math.cos(Math.PI / 6), complex.realValue(), 1e-9);
        Assertions.assertEquals(5.0 * Math.sin(Math.PI / 6), complex.imaginaryValue(), 1e-9);
    }


    @Test
    public void testSumAllVarargs() {
        Complex c1 = ComplexNumbers.ofCartesianForm(1, 1);
        Complex c2 = ComplexNumbers.ofCartesianForm(2, 3);
        Complex c3 = ComplexNumbers.ofCartesianForm(4, -1);
        
        // Test correct sum of multiple Complex numbers
        Complex sum = ComplexNumbers.sumAll(c1, c2, c3);
        Assertions.assertEquals(7, sum.realValue());
        Assertions.assertEquals(3, sum.imaginaryValue());

        // Test with empty array
        Complex sumOfEmpty = ComplexNumbers.sumAll();
        Assertions.assertEquals(0, sumOfEmpty.realValue());
        Assertions.assertEquals(0, sumOfEmpty.imaginaryValue());
    }

    @Test
    public void testSumAllCollection() {
        Collection<Complex> numbers = Arrays.asList(
                ComplexNumbers.ofCartesianForm(1, 1),
                ComplexNumbers.ofCartesianForm(2, 3),
                ComplexNumbers.ofCartesianForm(4, -1)
        );
        
        // Test correct sum of multiple Complex numbers
        Complex sum = ComplexNumbers.sumAll(numbers);
        Assertions.assertEquals(7, sum.realValue());
        Assertions.assertEquals(3, sum.imaginaryValue());
    }

    @Test
    public void testMultiplyAllVarargs() {
        Complex c1 = ComplexNumbers.ofCartesianForm(1, 2);
        Complex c2 = ComplexNumbers.ofCartesianForm(3, 4);
        Complex c3 = ComplexNumbers.ofCartesianForm(0, 5);

        // Test correct multiplication of multiple Complex numbers
        Complex product = ComplexNumbers.multiplyAll(c1, c2, c3);
        Assertions.assertEquals(-50, product.realValue());
        Assertions.assertEquals(-25, product.imaginaryValue());

        // Test correct multiplication of multiple Complex numbers with polar
        Complex c4 = ComplexNumbers.ofPolarForm(2);
        product = ComplexNumbers.multiplyAll(c1, c2, c3, c4);
        Assertions.assertEquals(-100, product.realValue());
        Assertions.assertEquals(-50, product.imaginaryValue());
    }

    @Test
    public void testMultiplyAllCollection() {
        Collection<Complex> numbers = Arrays.asList(
                ComplexNumbers.ofCartesianForm(1, 2),
                ComplexNumbers.ofCartesianForm(3, 4),
                ComplexNumbers.ofCartesianForm(0, 5)
        );

        // Test correct multiplication of multiple Complex numbers
        Complex product = ComplexNumbers.multiplyAll(numbers);
        Assertions.assertEquals(-50, product.realValue());
        Assertions.assertEquals(-25, product.imaginaryValue());
        
        // Test with empty array
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            ComplexNumbers.multiplyAll(new ArrayList<Complex>());
        });
    }

    @Test
    public void testSqrtOf() {
        // Test square root of positive real number
        Complex resultPositive = ComplexNumbers.sqrtOf(9, 0);
        Assertions.assertEquals(3, resultPositive.realValue(), 1e-10);
        Assertions.assertEquals(0, resultPositive.imaginaryValue(), 1e-10);
        
        // Test square root of negative real number (should be imaginary)
        resultPositive = ComplexNumbers.sqrtOf(-9, 1);
        Assertions.assertEquals(0, resultPositive.realValue(), 1e-10);
        Assertions.assertEquals(-3, resultPositive.imaginaryValue(), 1e-10);
    }

    @Test
    public void testAllSqrtsOf() {
        // Test square roots of positive number
        Complex[] roots = ComplexNumbers.allSqrtsOf(16);
        Assertions.assertEquals(4, roots[0].realValue(), 1e-10);
        Assertions.assertEquals(0, roots[0].imaginaryValue(), 1e-10);
        Assertions.assertEquals(-4, roots[1].realValue(), 1e-10);
        Assertions.assertEquals(0, roots[1].imaginaryValue(), 1e-10);
        
        // Test square roots of negative number
        roots = ComplexNumbers.allSqrtsOf(-16);
        Assertions.assertEquals(0, roots[0].realValue(), 1e-10);
        Assertions.assertEquals(4, roots[0].imaginaryValue(), 1e-10);
        Assertions.assertEquals(0, roots[1].realValue(), 1e-10);
        Assertions.assertEquals(-4, roots[1].imaginaryValue(), 1e-10);
    } 

    @Test
    public void testLinearEquationRoot() {
        Complex root = ComplexNumbers.linearEquationRoot(2.0, -4.0);
        Assertions.assertEquals(2.0, root.realValue());
        Assertions.assertEquals(0.0, root.imaginaryValue());
    }


    @Test
    public void testQuadraticEquationRootsReal() {
        double a = 1;
        double b = -3;
        double c = 2;
        Complex[] roots = ComplexNumbers.equationRoots(a, b, c);

        // Check that roots are correct (should be 2 and 1)
        Assertions.assertEquals(2, roots[0].realValue(), 1e-10);
        Assertions.assertEquals(0, roots[0].imaginaryValue(), 1e-10);
        Assertions.assertEquals(1, roots[1].realValue(), 1e-10);
        Assertions.assertEquals(0, roots[1].imaginaryValue(), 1e-10);
    }

    @Test
    public void testQuadraticEquationRootsComplex() {
        double a = 1;
        double b = 0;
        double c = 1; // x^2 + 1 = 0  -> x = +i, -i
        Complex[] roots = ComplexNumbers.equationRoots(a, b, c);
        Assertions.assertEquals(0, roots[0].realValue(), 1e-10);
        Assertions.assertEquals(1, roots[0].imaginaryValue(), 1e-10);
        Assertions.assertEquals(0, roots[1].realValue(), 1e-10);
        Assertions.assertEquals(-1, roots[1].imaginaryValue(), 1e-10);
    }


    // ---------------------------------------------------------------------- //
    //  Peculiar conditions
    // ---------------------------------------------------------------------- //

    @Test
    public void testOfPolarFormWithModulusOnly() {
        Complex complex1 = ComplexNumbers.ofPolarForm(4.3);
        Complex complex2 = ComplexNumbers.ofPolarForm(-4.3);
        
        Assertions.assertEquals(4.3, complex1.modulusValue());
        Assertions.assertEquals(0, complex1.mainArgumentValue());
        Assertions.assertEquals(4.3, complex2.modulusValue());
        Assertions.assertEquals(Math.PI, complex2.mainArgumentValue(), 1e-9);
    }

    @Test
    public void testIsZero() {
        Complex nonZero = ComplexNumbers.ofCartesianForm(1, 1);

        // Test isZero for different complex numbers
        Assertions.assertTrue(ComplexNumbers.isZero(ComplexNumbers.ZERO_COMPLEX_CARTESIAN));
        Assertions.assertTrue(ComplexNumbers.isZero(ComplexNumbers.ZERO_COMPLEX_POLAR));
        Assertions.assertFalse(ComplexNumbers.isZero(nonZero));
        Assertions.assertFalse(ComplexNumbers.isZero(ComplexNumbers.IMAGINARY_UNIT));
    }

    @Test
    public void testIsOne() {
        Complex nonOne = ComplexNumbers.ofCartesianForm(1, 2);

        // Test isOne for different complex numbers
        Assertions.assertTrue(ComplexNumbers.isOne(ComplexNumbers.ONE_COMPLEX_CARTESIAN));
        Assertions.assertTrue(ComplexNumbers.isOne(ComplexNumbers.ONE_COMPLEX_POLAR));
        Assertions.assertFalse(ComplexNumbers.isOne(nonOne));
        Assertions.assertFalse(ComplexNumbers.isOne(ComplexNumbers.IMAGINARY_UNIT));
    }

    @Test
    public void testKahanSum() {
        Collection<Complex> numbers = Arrays.asList(
            ComplexNumbers.ofCartesianForm(1e10, 0),
            ComplexNumbers.ofCartesianForm(1, 1),
            ComplexNumbers.ofCartesianForm(1e-10, 0),
            ComplexNumbers.ofCartesianForm(-1e10, 0)
        );

        Complex result = ComplexNumbers.sumAll(numbers);
        Assertions.assertEquals(1, result.realValue());        // Should be accurate
        Assertions.assertEquals(1, result.imaginaryValue());   // Should be accurate
    }

    @Test
    public void testKahanSumWithNegative() {
        Collection<Complex> numbers = Arrays.asList(
            ComplexNumbers.ofCartesianForm(1e10, 0),
            ComplexNumbers.ofCartesianForm(-1e10, 0),
            ComplexNumbers.ofCartesianForm(1, 1)
        );

        Complex result = ComplexNumbers.sumAll(numbers);
        Assertions.assertEquals(1, result.realValue());        // Should be accurate
        Assertions.assertEquals(1, result.imaginaryValue());   // Should be accurate
    }

    @Test
    public void testSumAllEmptyVarargs() {
        // Test with empty array
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            ComplexNumbers.multiplyAll();
        });
    }

    @Test
    public void testSumAllEmptyCollection() {
        // Test with empty collection
        Complex sumOfEmpty = ComplexNumbers.sumAll(new ArrayList<Complex>());
        Assertions.assertEquals(0, sumOfEmpty.realValue());
        Assertions.assertEquals(0, sumOfEmpty.imaginaryValue());
    }

    @Test
    public void testMultiplyAllByZero() {
        Complex complex1 = ComplexNumbers.of(1.0);
        Complex complex2 = ComplexNumbers.ZERO_COMPLEX_CARTESIAN;  // 0 + 0i
        Complex product = ComplexNumbers.multiplyAll(complex1, complex2);
        Assertions.assertEquals(0.0, product.realValue());
        Assertions.assertEquals(0.0, product.imaginaryValue());
    }
    
    @Test
    public void testMultiplyAllByOne() {
        Complex complex1 = ComplexNumbers.of(1.0);
        Complex complex2 = ComplexNumbers.ONE_COMPLEX_POLAR;  // 1 + 0i
        Complex product = ComplexNumbers.multiplyAll(complex1, complex2);
        Assertions.assertEquals(complex1.realValue(), product.realValue());
        Assertions.assertEquals(complex1.imaginaryValue(), product.imaginaryValue());
    }

    @Test
    public void testSqrtOfZero() {
        Complex resultPositive = ComplexNumbers.sqrtOf(0, 0);
        Assertions.assertEquals(0, resultPositive.realValue(), 1e-10);
        Assertions.assertEquals(0, resultPositive.imaginaryValue(), 1e-10);
    }

    @Test
    public void testAllSqrtsOfZero() {
        // Test square roots of zero
        Complex[] roots = ComplexNumbers.allSqrtsOf(0);
        Assertions.assertEquals(0, roots[0].realValue(), 1e-10);
        Assertions.assertEquals(0, roots[0].imaginaryValue(), 1e-10);
        Assertions.assertEquals(0, roots[1].realValue(), 1e-10);
        Assertions.assertEquals(0, roots[1].imaginaryValue(), 1e-10);
    }

    
    // ---------------------------------------------------------------------- //
    //  Anomalous conditions
    // ---------------------------------------------------------------------- //

    @Test
    public void testOfRealNumberNaN() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.of(Double.NaN);
        });
    }

    @Test
    public void testOfRealNumberInfinity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.of(Float.POSITIVE_INFINITY);
        });
    }

    @Test
    public void testOfCartesianFormNaN() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(Float.NaN, 4.0);
        });
    }

    @Test
    public void testOfCartesianFormInfinity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofCartesianForm(3.0, Double.NEGATIVE_INFINITY);
        });
    }

    @Test
    public void testOfPolarFormNaN() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(Double.NaN, Math.PI);
        });
    }

    @Test
    public void testOfPolarFormInfinity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.ofPolarForm(5.0, Double.POSITIVE_INFINITY);
        });
    }

    @Test
    public void testMultiplyAllEmptyVarargs() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            ComplexNumbers.multiplyAll();
        });
    }

    @Test
    public void testMultiplyAllEmptyCollection() {
        // Test with empty collection
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            ComplexNumbers.multiplyAll(new ArrayList<Complex>());
        });
    }

    @Test
    public void testSumAllNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            ComplexNumbers.sumAll((Complex[]) null);
        });
    }

    @Test
    public void testSumAllCollectionNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            ComplexNumbers.sumAll((List<Complex>) null);
        });
    }

    @Test
    public void testMultiplyAllNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            ComplexNumbers.multiplyAll((Complex[]) null);
        });
    }

    @Test
    public void testMultiplyAllCollectionNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            ComplexNumbers.multiplyAll((List<Complex>) null);
        });
    }

    @Test
    public void testSqrtOfInvalid () {
        // Test invalid k value
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.sqrtOf(9, 2);
        });

        // Test invalid n value
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.sqrtOf(9, -1);
        });
    }

    // Test for linearEquationRoot(double a, double b) with a == 0
    @Test
    void testLinearEquationRootRealAZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.linearEquationRoot(0, 4);
        });
    }

    // Test for equationRoots(double a, double b, double c) with a == 0
    @Test
    void testQuadraticEquationRootsRealAZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ComplexNumbers.linearEquationRoot(0, 4);
        });
    }

}