package com.lgardias.controller;

import com.lgardias.model.FISModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class MainPaneController implements Initializable {

    private FISModel fisModel;
    private String outSring = new String("");

    private Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
    private Boolean trueDistance = false;
    private Boolean trueSpeed = false;

    @FXML
    private TextField distanceTextField;

    @FXML
    private TextField speedTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button evaluateButton;

    @FXML
    private Label infoLabel;

    @FXML
    private Button chartButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextArea outputTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fisModel = new FISModel();

        try {
            configureInfoButton();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        configureTextFields();
        configureAddButton();
        configureEvaluateButton();
        configureChartButton();
        configureExitButton();
    }

    private void configureTextFields() {
        TextFormatter<Double> textFormatter = new TextFormatter<>(converter,0.0, filter);
        TextFormatter<Double> textFormatter2 = new TextFormatter<>(converter,0.0, filter);
        distanceTextField.setTextFormatter(textFormatter);
        speedTextField.setTextFormatter(textFormatter2);
    }

    private void configureEvaluateButton() {
        infoLabel.setText("");
        evaluateButton.setOnAction(event -> {
            if(trueSpeed && trueDistance){
                fisModel.getFis().evaluate();
                outputTextArea.setText(outSring + fisModel.distanceMembership() + fisModel.speedMembership() +
                        fisModel.balastMembership() + fisModel.getResult());
            }else {
                infoLabel.setText("Wprowadż poprawne dane");
            }
        });
    }

    private void configureAddButton() {

        addButton.setOnAction(event -> {
            infoLabel.setText("");
            trueDistance = false;
            trueSpeed = false;
            String value = distanceTextField.getText();
            String value2 = speedTextField.getText();
            outputTextArea.setText(distanceTextField.getText());
            if (Double.valueOf(value) >= 0.0 && Double.valueOf(value) <= 300.0) {
                fisModel.setDistance(Double.valueOf(value));
                outSring += "Zadana wartość :: Głębokość zanurzenia = " + value + "\n";
                outputTextArea.setText(outSring);
                trueDistance = true;
            } else {
                outSring += "GŁĘBOKOŚĆ ZANURZENIA musi zawierać się w przedziale 0.0 - 300.0" + "\n";
                outputTextArea.setText(outSring);
                distanceTextField.setText("0.0");
                infoLabel.setText("Głebokośc: 0.0 - 300.0 ");
            }

            if (Double.valueOf(value2) >= 0.0 && Double.valueOf(value2) <= 10.0) {
                fisModel.setSpeed(Double.valueOf(value2));
                outSring += "Zadana wartość :: Prędkość zanurzania = " + value2 + "\n";
                outputTextArea.setText(outSring);
                trueSpeed = true;
            } else {
                outSring += "PRĘDKOŚĆ ZANURZENIA musi zawierać się w przedziale 0.0 - 10.0" + "\n";
                outputTextArea.setText(outSring);
                speedTextField.setText("0.0");
                infoLabel.setText("Prędkość: 0.0 - 10.0 ");
            }

        });

    }

    private void configureChartButton() {
        chartButton.setOnAction(event -> {
            infoLabel.setText("");
            fisModel.createDialogFis();
        });
    }

    private void configureExitButton() {
        exitButton.setOnAction(event -> {
           System.exit(0);

        });
    }

    private void configureInfoButton() throws FileNotFoundException {
        InputStream inputStream = MainPaneController.class.getClassLoader().getResourceAsStream("info/info.txt");
        Scanner in = new Scanner(inputStream);

        infoButton.setOnAction(event -> {
            infoLabel.setText("");
            String info = new String("");
            while (in.hasNext()) {
                info = info + in.nextLine() + "\n";
            }
            outputTextArea.setText(info);
            in.close();
        });
    }


    UnaryOperator<TextFormatter.Change> filter = c -> {
        String text = c.getControlNewText();
        if (validEditingState.matcher(text).matches()) {
            return c;
        } else {
            return null;
        }
    };

    StringConverter<Double> converter = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return object.toString();
        }

        @Override
        public Double fromString(String string) {
            if (string.isEmpty() || "-".equals(string) || ".".equals(string) || "-.".equals(string)){
                return 0.0;
            }else {
                return Double.valueOf(string);
            }
        }
    };
}
