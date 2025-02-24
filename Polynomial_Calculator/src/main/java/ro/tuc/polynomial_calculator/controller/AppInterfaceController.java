package ro.tuc.polynomial_calculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ro.tuc.polynomial_calculator.model.Polynomial;
import ro.tuc.polynomial_calculator.model.PolynomialsOperations;

public class AppInterfaceController {
    private Polynomial polynomialOne, polynomialTwo;
    @FXML
    protected Button AddButton;
    @FXML
    protected TextField PolynomOneInsertBox;
    @FXML
    protected TextField PolynomTwoInsertBox;
    @FXML
    protected Label AnswerErrorLabel;
    @FXML
    protected Button MultiplyButton, DivideButton, DerivateButton, IntegrateButton;
    private String result;

    private void setPolynomials(String pOne, String pTwo) {
        polynomialOne = new Polynomial();
        polynomialOne.parsePolynomial(pOne);

        polynomialTwo = new Polynomial();
        polynomialTwo.parsePolynomial(pTwo);
    }

    private boolean checkInput(String input) {
        //check for other letters than x X transform all X to x
        //other characters than allowed characters: '+', '-', '^', 'X', 'x' and real numbers
        //after x can be spaces and 1. ^ and number
        //                          2. +/- and number
        if (!input.matches("[\\s0-9+x\\-^]+"))
            return true;

        return false;
    }

    public boolean addPolynomials(boolean onlyP1) {
        String polyOneString = PolynomOneInsertBox.getText();
        String polyTwoString = PolynomTwoInsertBox.getText();
        polyOneString = polyOneString.replaceAll("X", "x");
        polyTwoString = polyTwoString.replaceAll("X", "x");
        System.out.println(polyOneString);

        if (checkInput(polyOneString)) {
            AnswerErrorLabel.setText("The Polynomial_1 is not valid");
            return false;
        }
        if (!onlyP1){
            if (checkInput(polyTwoString)) {
                AnswerErrorLabel.setText("The Polynomial_2 is not valid");
                return false;
            }
        }else polyTwoString = "0";

        setPolynomials(polyOneString, polyTwoString);

        return true;
    }

    public void subButtonOnAction(ActionEvent e) {
        if (!addPolynomials(false))
            return;
        //add
        //AnswerErrorLabel.setText(polynomialOne.printPolynomial());
        PolynomialsOperations p = new PolynomialsOperations();
        result = p.subPolynomials(polynomialOne, polynomialTwo);
        AnswerErrorLabel.setText(result);
    }

    public void addButtonOnAction(ActionEvent e) {
        if (!addPolynomials(false))
            return;

        PolynomialsOperations p = new PolynomialsOperations();
        result = p.addPolynomials(polynomialOne, polynomialTwo);
        AnswerErrorLabel.setText(result);
    }

    public void multiplyButtonOnAction() {
        if (!addPolynomials(false))
            return;
        PolynomialsOperations p = new PolynomialsOperations();
        result = p.multiplyPolynomials(polynomialOne, polynomialTwo);
        AnswerErrorLabel.setText(result);
    }

    public void divideButtonOnAction() {
        if (!addPolynomials(false))
            return;
        PolynomialsOperations p = new PolynomialsOperations();
        String[] result = p.dividePolynomials(polynomialOne,polynomialTwo);
        AnswerErrorLabel.setText("Q: "+ result[0] + "\nR: " + result[1]);

    }

    public void deriveButtonOnAction() {
        if (!addPolynomials(true))
            return;
        PolynomialsOperations p = new PolynomialsOperations();
        result = p.derivatePolynomial(polynomialOne);
        AnswerErrorLabel.setText(result);
    }

    public void integrateButtonOnAction() {
        if (!addPolynomials(true))
            return;
        PolynomialsOperations p = new PolynomialsOperations();
        result = p.integratePolynomial(polynomialOne);
        AnswerErrorLabel.setText(result);
    }
}
