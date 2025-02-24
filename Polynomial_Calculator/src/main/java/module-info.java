module ro.tuc.polynomial_calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens ro.tuc.polynomial_calculator to javafx.fxml;
    exports ro.tuc.polynomial_calculator;
    exports ro.tuc.polynomial_calculator.controller;
    opens ro.tuc.polynomial_calculator.controller to javafx.fxml;
}