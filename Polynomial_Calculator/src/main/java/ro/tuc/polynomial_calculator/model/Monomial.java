package ro.tuc.polynomial_calculator.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monomial {
    private float coefficient;
    private int exponent;

    Monomial(String monomialString) {
        String my_regex = "[-+]?[0-9]*[Xx]?[^^][0-9]*"; //extract coefficient and exponent as follows : coefficient * X, exponent
        Pattern pattern = Pattern.compile(my_regex);
        Matcher matcher = pattern.matcher(monomialString);

        if (matcher.find() && !matcher.group().isEmpty()) {
            if (matcher.group().contains(String.valueOf('x'))) {
                this.coefficient = Float.parseFloat(matcher.group().replace(String.valueOf('x'), "1"));
                if (!(this.coefficient == 1 || this.coefficient == -1)) this.coefficient = (this.coefficient - 1) / 10;

                if (matcher.find() && !matcher.group().isEmpty()) {
                    this.exponent = Integer.parseInt(matcher.group());
                } else this.exponent = 1;

            } else {
                this.coefficient = Float.parseFloat(matcher.group());
                this.exponent = 0;
            }
        } else {
            this.exponent = -1;
            this.coefficient = 0;
        }
    }

    public float getCoefficient() {
        return this.coefficient;
    }

    public int getExponent() {
        return this.exponent;
    }

}
