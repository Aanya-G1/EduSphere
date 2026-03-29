package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class QuizAttemptController {

    @FXML private Label questionLabel;
    @FXML private RadioButton op1, op2, op3, op4;

    @FXML
    public void next() {
        System.out.println("Next question...");
    }
}
