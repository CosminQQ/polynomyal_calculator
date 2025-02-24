package ro.tuc.polynomial_calculator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ro.tuc.polynomial_calculator.model.Polynomial;
import ro.tuc.polynomial_calculator.model.PolynomialsOperations;

public class PolynomialsOperationsTest {

    @Test
    public void testAddPolynomials() {
        Polynomial a = new Polynomial();
        a.parsePolynomial("4x^2+5x");

        Polynomial b = new Polynomial();
        b.parsePolynomial("x^3+5");

        PolynomialsOperations operations = new PolynomialsOperations();
        String result = operations.addPolynomials(a, b);
        assertEquals("x^3+4x^2+5x+5", result);
    }
    @Test
    public void testSubPolynomials() {
        Polynomial a = new Polynomial();
        a.parsePolynomial("4x^2 + 5x");

        Polynomial b = new Polynomial();
        b.parsePolynomial("x^3 + 5");

        PolynomialsOperations operations = new PolynomialsOperations();
        String result = operations.subPolynomials(a, b);
        assertEquals("-x^3+4x^2+5x-5", result);
    }

    @Test
    public void testMultiplyPolynomials() {
        Polynomial a = new Polynomial();
        a.parsePolynomial("2x^2 + 3x");

        Polynomial b = new Polynomial();
        b.parsePolynomial("x^3 + 4x");

        PolynomialsOperations operations = new PolynomialsOperations();
        String result = operations.multiplyPolynomials(a, b);
        assertEquals("2x^5 +8x^3+ 3x^4 +12x^2", result);
    }

    @Test
    public void testDividePolynomials() {
        Polynomial a = new Polynomial();
        a.parsePolynomial("2x^4 +3x^3 +4x^2 +5x +6");

        Polynomial b = new Polynomial();
        b.parsePolynomial("x^2 + 1");

        PolynomialsOperations operations = new PolynomialsOperations();
        String[] result = operations.dividePolynomials(a, b);
        assertEquals("2x^2 + 3x + 2", result[0]);
        assertEquals("x +4", result[1]);
    }

    @Test
    public void testDerivatePolynomial() {
        Polynomial a = new Polynomial();
        a.parsePolynomial("3x^3 +2x^2 +5x +1");

        PolynomialsOperations operations = new PolynomialsOperations();
        String result = operations.derivatePolynomial(a);
        assertEquals("9x^2 +4x +5", result);
    }

    @Test
    public void testIntegratePolynomial() {
        Polynomial a = new Polynomial();
        a.parsePolynomial("4x^2 + 3x + 2");

        PolynomialsOperations operations = new PolynomialsOperations();
        String result = operations.integratePolynomial(a);
        assertEquals((4/3) + "x^3 +(3/2)x^2 +2x", result);
    }

}
