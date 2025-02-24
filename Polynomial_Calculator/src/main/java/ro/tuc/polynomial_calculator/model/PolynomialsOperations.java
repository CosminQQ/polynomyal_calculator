package ro.tuc.polynomial_calculator.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PolynomialsOperations {
    public String addPolynomials(Polynomial a, Polynomial b) {
        int greatestExponent = Math.max(a.gratestExponent, b.gratestExponent);
        float[] addArray = operationAddSub(a.getMyPolynomialHashMap(), b.getMyPolynomialHashMap(), greatestExponent, false);

        return printPolynomial(addArray, greatestExponent, false);
    }

    public String subPolynomials(Polynomial a, Polynomial b) {
        int greatestExponent = Math.max(a.gratestExponent, b.gratestExponent);
        float[] addArray = operationAddSub(a.getMyPolynomialHashMap(), b.getMyPolynomialHashMap(), greatestExponent, true);

        return printPolynomial(addArray, greatestExponent, false);
    }

    public float[] operationAddSub(HashMap<Integer, Monomial> polynomialOne, HashMap<Integer, Monomial> polynomialTwo, int greatestExponent, boolean operation) {
        float[] resultedPolynomial = new float[greatestExponent + 1];
        for (int i = 0; i < greatestExponent; i++)
            resultedPolynomial[i] = Float.MAX_VALUE;
        Monomial monomial;
        for (Map.Entry<Integer, Monomial> entry : polynomialOne.entrySet()) {
            monomial = entry.getValue();
            resultedPolynomial[monomial.getExponent()] = monomial.getCoefficient();
        }
        for (Map.Entry<Integer, Monomial> entry : polynomialTwo.entrySet()) {
            monomial = entry.getValue();
            if (resultedPolynomial[monomial.getExponent()] == Float.MAX_VALUE)
                if (!operation) {
                    resultedPolynomial[monomial.getExponent()] = monomial.getCoefficient();
                } else {
                    resultedPolynomial[monomial.getExponent()] = -monomial.getCoefficient();
                }
            else {
                if (!operation) {
                    resultedPolynomial[monomial.getExponent()] += monomial.getCoefficient();
                } else {
                    resultedPolynomial[monomial.getExponent()] -= monomial.getCoefficient();
                }
            }
        }

//        for (int i = 0; i <= greatestExponent; i++)
//            System.out.printf(" " + resultedPolynomial[i]);

        return resultedPolynomial;
    }

    public String printPolynomial(float[] myPolynom, int exponent, boolean precision) {
        StringBuilder builder = new StringBuilder();
        for (int i = exponent; i >= 0; i--) {
           // System.out.println("Here!" + myPolynom[i]);                           //iterate through the coefficients array in decreasing order
            if (myPolynom[i] != Float.MAX_VALUE) {                  //ignore nonexistent monomials
                if (myPolynom[i] != 0) {                            //if the coefficient is 0 we do not print monomial pattern
                    if (i != exponent && myPolynom[i] >= 0)         //make sure to not print + at the beginning of the polynomial but to print + sign if it is an interior monomial
                        builder.append("+");
                    if (myPolynom[i] < 0 && !precision)                              //print negative sign
                        builder.append("-");
                    if (myPolynom[i] == 1) {                        //do not print coefficient when it is equal 1
                        if (i != 0) {                               //make sure to not print full monomial pattern when exponent is 0 or 1
                            if (i == 1) builder.append("X");
                            else {
                                builder.append("X^")
                                        .append(i);
                            }
                        } else builder.append("1");
                    } else {
                        if (!precision)
                            builder.append(Math.round(Math.abs(myPolynom[i])));
                        else builder.append(String.format("%.2f",myPolynom[i]));                      //print coefficient
                        if (i != 0) {
                            if (i == 1)
                                builder.append("X");            //making sure to not print monomial pattern if it is not needed
                            else {
                                builder.append("X^")
                                        .append(i);
                            }
                        }
                    }
                    builder.append(" ");        //separate monomials via a whitespace
                }
            }
        }

        if (builder.toString().isEmpty())       //returns 0 if we have no monomial
            builder.append("0");

        return builder.toString();
    }

    public String multiplyPolynomials(Polynomial a, Polynomial b) {
        int greatestExponent = a.gratestExponent + b.gratestExponent;
        float[] resultedArray = operationMultiply(a.getMyPolynomialHashMap(), b.getMyPolynomialHashMap(), greatestExponent);
        String result = printPolynomial(resultedArray, greatestExponent, false);

        return result;
    }

    private float[] operationMultiply(HashMap<Integer, Monomial> polynomialOne, HashMap<Integer, Monomial> polynomialTwo, int exponent) {
        float[] multiplyArray = new float[exponent + 5]; //i don't know why but it gives me index out of bounds error when i use fixed lengths for arrays :(

        for (int i = 0; i <= exponent; i++) {
            multiplyArray[exponent] = Float.MAX_VALUE;
        }

        Monomial monomialPolyOne, monomialPolyTwo;
        float newCoefficient;
        int newExponent;

        for (Map.Entry<Integer, Monomial> entry : polynomialOne.entrySet()) {
            monomialPolyOne = entry.getValue();
            for (Map.Entry<Integer, Monomial> entry2 : polynomialTwo.entrySet()) {
                monomialPolyTwo = entry2.getValue();
                newCoefficient = monomialPolyOne.getCoefficient() * monomialPolyTwo.getCoefficient();
                newExponent = monomialPolyOne.getExponent() + monomialPolyTwo.getExponent();
                if (multiplyArray[newExponent] == Float.MAX_VALUE)
                    multiplyArray[newExponent] = newCoefficient;
                else
                    multiplyArray[newExponent] += newCoefficient;
            }
        }

        return multiplyArray;
    }


    public String[] dividePolynomials(Polynomial a, Polynomial b) {
        if (b.getValue(a.gratestExponent) == 0 && b.gratestExponent == 0)
            return new String[] {"Divide by 0!"," "};

        int dividendDegree = a.gratestExponent;
        int divisorDegree = b.gratestExponent;

        if (dividendDegree < divisorDegree)
            return new String[] {"0"," "};

        float[] quotientCoefficients = new float[dividendDegree - divisorDegree + 1];
        float[] dividendCoefficients = new float[dividendDegree + 1];
        float[] divisorCoefficients = new float[divisorDegree + 1];
        float[] reminderCoefficient = new float[dividendDegree + 1];

        for (Map.Entry<Integer, Monomial> entry : a.getMyPolynomialHashMap().entrySet()) {
            dividendCoefficients[entry.getKey()] = entry.getValue().getCoefficient();
        }

        for (Map.Entry<Integer, Monomial> entry : b.getMyPolynomialHashMap().entrySet()) {
            divisorCoefficients[entry.getKey()] = entry.getValue().getCoefficient();
        }

        float factor;
        for (int i = dividendDegree; i >= divisorDegree; i--) {
            if (dividendCoefficients[i] != 0) {
                factor = dividendCoefficients[i] / divisorCoefficients[divisorDegree];
                quotientCoefficients[i - divisorDegree] = factor;

                for (int j = divisorDegree; j >= 0; j--) {
                    dividendCoefficients[i - divisorDegree + j] -= factor * divisorCoefficients[j];
                }
            }
        }

        for(int i=0; i<=dividendDegree; i++)
            reminderCoefficient[i] = dividendCoefficients[i];

        return new String[] {printPolynomial(quotientCoefficients, dividendDegree - divisorDegree, false),
                                printPolynomial(reminderCoefficient, dividendDegree, false)};
    }

    public String derivatePolynomial(Polynomial a) {
        int greatestExponent = a.gratestExponent - 1;
        return printPolynomial(operationDerive(a.getMyPolynomialHashMap(), greatestExponent), greatestExponent, false);
    }

    private float[] operationDerive(HashMap<Integer, Monomial> polynomial, int power) {
        float[] derrivedPolynomial = new float[power + 1];

        Arrays.fill(derrivedPolynomial, Float.MAX_VALUE);

        for (Map.Entry<Integer, Monomial> entry : polynomial.entrySet()) {
            if (entry.getKey() != 0) {
                derrivedPolynomial[entry.getValue().getExponent() - 1] = entry.getValue().getExponent() * entry.getValue().getCoefficient();
                System.out.println(entry.getValue().getCoefficient());
            }
        }
        return derrivedPolynomial;
    }

    public String integratePolynomial(Polynomial a) {
        int greatestExponent = a.gratestExponent;
        return printPolynomial(operationIntegrate(a.getMyPolynomialHashMap(), greatestExponent), greatestExponent + 1, true);
    }

    private float[] operationIntegrate(HashMap<Integer, Monomial> polynomial, int power) {
        float[] derrivedPolynomial = new float[power + 2];

        Arrays.fill(derrivedPolynomial, Float.MAX_VALUE);

        for (Map.Entry<Integer, Monomial> entry : polynomial.entrySet()) {
                derrivedPolynomial[entry.getValue().getExponent() + 1] = (float) (entry.getValue().getCoefficient() / (entry.getValue().getExponent() + 1));
//                System.out.println("in method "+ entry.getValue().getCoefficient());

        }
        return derrivedPolynomial;
    }

}
