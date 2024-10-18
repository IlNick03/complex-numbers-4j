# complex-numbers-4J

**complex-numbers-4J** is a Java library that provides a comprehensive set of operations for complex numbers. It offers an intuitive and efficient way to perform arithmetic, power operations, root extractions, and even solve linear and quadratic equations involving complex numbers.

## Features

- **Complex Arithmetic**: 
  - Add, subtract, multiply, and divide complex numbers with ease.
  
- **Power and Roots**: 
  - Raise complex numbers to real powers and compute roots.
  
- **Equation Solvers**:
  - Solve quadratic equations using complex numbers. Both real and complex coefficients are supported.  
  - Solve linear equations with complex coefficients.

- **Fluent API**: 
  - Enables chained operations, allowing for concise expressions like:
    ```java
    Complex result = c1.plus(c2).minusReal(Math.PI).divideBy(c3.multiplyByImaginary(3)).sqrt(0);
    // Equivalent to: sqrt( (c1 + c2 - PI)/(c3*(0 + 3i) )
    ```

- **Dual Implementations**:
  - Switches between Cartesian and Polar forms for complex numbers. Polar form minimizes the loss of significant digits in calculations involving multiplication, division, power elevation, and roots.

- **Future Enhancements**: 
  - More tests will assess the correctness of linear and quadratic equation methods in special cases \(a*x = 0, ax^2 = 0, ax^2 + c = 0, ax^2 + bx = 0\)

## Installation

To use the `complex-numbers-4J` library in your project, follow these steps:

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.nick.math</groupId>
    <artifactId>complex-numbers-4J</artifactId>
    <version>1.0</version>
</dependency>
```

### Gradle

Add the following to your `build.gradle` dependencies:

```gradle
implementation 'com.nick.math:complex-numbers-4J:1.0'
```

### Dependencies

- **JUnit 5**: Used only for unit testing in the library. It is not required for runtime use.

## Usage

Here are a few examples to help you get started with the `complex-numbers-4J` library.

### 1. Basic Complex Arithmetic

```java
import com.nick.math.Complex;

public class ComplexExample {
    public static void main(String[] args) {
        Complex z1 = ComplexNumbers.ofCartesianForm(2, 3);  // 2 + 3i
        Complex z2 = ComplexNumbers.ofCartesianForm(1, -1); // 1 - i

        Complex sum = z1.add(z2);
        Complex difference = z1.subtract(z2);
        Complex product = z1.multiply(z2);
        Complex quotient = z1.divide(z2);

        System.out.println("Sum: " + sum);           // Output: 3 + 2i
        System.out.println("Difference: " + difference);  // Output: 1 + 4i
        System.out.println("Product: " + product);   // Output: 5 + i
        System.out.println("Quotient: " + quotient); // Output: 0.5 + 2.5i
    }
}
```

### 2. Power and Root Operations

```java
import com.nick.math.Complex;

public class ComplexOperations {
    public static void main(String[] args) {
        Complex z = ComplexNumbers.ofCartesianForm(1, 1); // 1 + i

        Complex power = z.pow(3);  // Raise to the power of 3
        Complex[] roots = z.roots(2);  // Compute square roots of 1 + i

        System.out.println("z^3: " + power);         // Output: -2 + 2i
        for (Complex root : roots) {
            System.out.println("Root: " + root);    // Outputs two complex roots
        }
    }
}
```

### 3. Solving Equations with Complex Coefficients

#### Solving Linear Equations
For a linear equation of the form \(a*x + b = 0\):

```java
import com.nick.math.Complex;
import com.nick.math.ComplexEquation;

public class LinearEquationExample {
    public static void main(String[] args) {
        Complex a = ComplexNumbers.ofCartesianForm(2, 1);  // 2 + i
        Complex b = ComplexNumbers.ofCartesianForm(-1, -3); // -1 - 3i

        Complex solution = ComplexNumbers.solveLinearEquation(a, b);

        System.out.println("Solution: " + solution); // Output: Solution to the equation
    }
}
```

#### Solving Quadratic Equations
For a quadratic equation of the form \(a*x^2 + b*x + c = 0\):

```java
import com.nick.math.Complex;
import com.nick.math.ComplexEquation;

public class QuadraticEquationExample {
    public static void main(String[] args) {
        Complex a = ComplexNumbers.ofCartesianForm(1, 0);  // 1
        Complex b = ComplexNumbers.ofCartesianForm(0, 1);  // i
        Complex c = ComplexNumbers.ofCartesianForm(-1, 0); // -1

        Complex[] solutionsForComplexCoeffs = ComplexEquation.solveQuadraticEquation(a, b, c);

        for (Complex solution : solutionsForComplexCoeffs) {
            System.out.println("Solution: " + solution); // Outputs both solutions
        }

        Complex[] solutionsForRealCoeffs = ComplexEquation.solveQuadraticEquation(1, 0, 1);

        for (Complex solution : solutionsForRealCoeffs) {
            System.out.println("Solution: " + solution); // Outputs both solutions
        }
    }
}
```

## API Overview

### Complex Interface

The `Complex` interface represents a complex number in the form `(a + b*i)`, where:
- `a` is the real part
- `b` is the imaginary part

Or complex numbers in the form: `r*( cos(theta) + i*sin(theta) )`, where:
- `r` is the modulus
- `theta` is the main argument, between -PI (excluded) and PI (included)


#### Key Methods:

- `add(Complex other)`: Returns the sum of this and another complex number.
- `subtract(Complex other)`: Returns the difference.
- `multiply(Complex other)`: Returns the product.
- `divide(Complex other)`: Returns the quotient.
- `pow(double exponent)`: Raises the complex number to a real power.
- `allRoots(int n)`: Computes all `n`-th roots of the complex number, and return them as an array of complex numbers. You can access each `k`-th root using the `k` index of the array: `result[k]`

### ComplexNumbers Class

The `ComplexNumbers` class allows you to create instances of `Complex`.

#### Static factory methods:
- `of(double real)` : Creates a complex number from a real number, represented as: \(r + 0i\)
- `ofCartesianForm(double real, double imaginary)` : Creates a complex number from its Cartesian form, represented as: \(a + b*i\)
- `ofPolarForm(double modulus, double argument)` : Creates a complex number from its polar form, defined by a modulus and an angle: \(r*( cos(theta) + i*sin(theta) )\)
- `ofPolarForm(double real)` : Creates a complex number from a real number, but represented in Polar form.

And also provides static methods to solve equations involving complex coefficients.

#### Key Methods:

- `solveLinearEquation(Complex a, Complex b)`: Solves a linear equation \(a*x + b = 0\) and returns the solution as a `Complex` number.
- `solveQuadraticEquation(double a, double b, double c)`: Solves a quadratic equation \(a*x^2 + b*x + c = 0\) and returns the two solutions as an array of `Complex` numbers.
- `solveQuadraticEquation(Complex a, Complex b, Complex c)`: Solves a quadratic equation \(a*x^2 + b*x + c = 0\) and returns the two solutions as an array of `Complex` numbers.

## License

This project is licensed under the GNU Lesser GPL v3.0

See the [LICENSE](LICENSE) file for details.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
