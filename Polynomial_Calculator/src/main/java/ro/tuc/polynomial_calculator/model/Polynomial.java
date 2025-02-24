package ro.tuc.polynomial_calculator.model;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    public int gratestExponent = 0;
    private HashMap<Integer, Monomial> myPolynomialHashMap = new HashMap<>();

    public HashMap<Integer, Monomial> getMyPolynomialHashMap() {
        return this.myPolynomialHashMap;
    }

    public void parsePolynomial(String polynomial) {
        String my_regex = "[-]?[1-9]?[0-9]*[xX^]*[0-9]*[^ +-]"; //separate the monomials as a * X ^ b

        Pattern pattern = Pattern.compile(my_regex);      //create the pattern object allowed pattern: +- a * X ^ b -> only
        // exceptions a = 1 -> X ^ b
        //            b = 1 -> a * X
        //            b = 0 -> a
        Matcher matcher = pattern.matcher(polynomial);      //extract monomials from string;

        while (matcher.find() && !matcher.group().isEmpty()) {
           // System.out.println("Monomial: " + matcher.group());
            Monomial currentMonomial = new Monomial(matcher.group());
            if (currentMonomial.getExponent() == -1) {
//                System.out.println("Unvalid monomial");
                break;
            } else {
                if (gratestExponent < currentMonomial.getExponent())
                    gratestExponent = currentMonomial.getExponent();

                myPolynomialHashMap.put(currentMonomial.getExponent(), currentMonomial);
//                System.out.println("Coefficient: " + currentMonomial.getCoefficient() + "Eponent: " + currentMonomial.getExponent());
//                System.out.println("HashMap value: " + myPolynomialHashMap.get(currentMonomial.getExponent()).getCoefficient());
            }

        }
    }

    public float getValue(int key) {
        if (myPolynomialHashMap.containsKey(key))
            return myPolynomialHashMap.get(key).getCoefficient();
        else return 0;
    }
}
/*
 public String printPolynomial() {
        if (myPolynomialHashMap.isEmpty()) {
            System.out.println("Empty hash map");
            return "";
        }

        StringBuilder builder = new StringBuilder();
        int exponent;
        float coefficient;

        for (HashMap.Entry<Integer, Monomial> entry : myPolynomialHashMap.entrySet()) {
            coefficient = entry.getValue().getCoefficient();
            exponent = entry.getValue().getExponent();
            if (coefficient > 0)
                builder.append("+");
            builder.append(Integer.toString(Math.round(coefficient)))
                    .append("X^")
                    .append(Integer.toString(exponent))
                    .append(" ");
        }

        return builder.toString();
    }
    /*
    input string -> allowed characters: '+', '-', '^', '.', '*', 'x'
                 -> allowed pattern a * X ^ b -> only exceptions a = 1 -> X ^ b
                                                                 b = 1 -> a * X
                                                                 b = 0 -> a
                 -> in hash map every field that has null is treated as 0
                 -> at the end if we have a string that does not respect the allowed pattern -> error
     */
