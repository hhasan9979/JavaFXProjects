package sample;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class MortgageCalculatorController {

    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private BigDecimal year = new BigDecimal(15); // 15% default


    @FXML
    private TextField mp10;

    @FXML
    private TextField mp20;

    @FXML
    private TextField mp30;

    @FXML
    private TextField loanTextField;

  //  @FXML
   // private Button calculateButton;

    @FXML
    private Slider yearSlider;

    @FXML
    private TextField downPTextField;

    @FXML
    private TextField mpTextField;

    @FXML
    private TextField purchasePTextField;

    @FXML
    private TextField interestRTextField;

    @FXML
    private Label yearNum;


    @FXML
    void calculateButtonPressed(ActionEvent event) {

        try
        {
            BigDecimal purchase = new BigDecimal(purchasePTextField.getText());
            BigDecimal down = new BigDecimal(downPTextField.getText());
            BigDecimal loan = purchase.subtract(down);
            loanTextField.setText(loan.toString());

            Integer num = new Integer(yearNum.getText());
            mp10.setText(currency.format(calcPayment(10)));
            mp20.setText(currency.format(calcPayment(20)));
            mp30.setText(currency.format(calcPayment(30)));

            mpTextField.setText(currency.format(calcPayment(num)));
        }

        catch (NumberFormatException ex)
        {
            purchasePTextField.setText("Enter amount");
            purchasePTextField.selectAll();
            purchasePTextField.requestFocus();

            downPTextField.setText("Enter amount");
            downPTextField.selectAll();

            interestRTextField.setText("Enter amount");
            interestRTextField.selectAll();

        }
    }


    private double calcPayment(Integer num) {
        Double payment;

        Double loan = new Double(loanTextField.getText());
        Double rate = new Double(interestRTextField.getText());

        num = num * 12;
        rate = (rate/1200);

        payment = (loan * rate)/(1 - Math.pow(1 + rate, -num));

        return payment;
    }


    public void initialize() {

        currency.setRoundingMode(RoundingMode.HALF_UP);

        yearSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                year = BigDecimal.valueOf(newValue.intValue());
                yearNum.setText(NumberFormat.getIntegerInstance().format(year));
            }
        });
    }
}
