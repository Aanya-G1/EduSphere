package com.edusphere.view;

import com.edusphere.controller.UserController;
import com.edusphere.model.User;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("User Registration");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        TextField courseField = new TextField();
        courseField.setPromptText("Enter Course");

        Button registerBtn = new Button("Register");

        registerBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String course = courseField.getText();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || course.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please fill all fields!");
                    alert.show();
                    return;
                }

                User user = new User(name, email, password, course);

                UserController controller = new UserController();
                boolean success = controller.registerUser(user);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                if (success) {
                    alert.setContentText("Registration Successful!");
                    nameField.clear();
                    emailField.clear();
                    passwordField.clear();
                    courseField.clear();
                } else {
                    alert.setContentText("Registration Failed!");
                }

                alert.show();

            } catch (Exception ex) {
                ex.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error: " + ex.getMessage());
                alert.show();
            }
        });

        VBox root = new VBox(12);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, nameField, emailField, passwordField, courseField, registerBtn);

        Scene scene = new Scene(root, 400, 350);

        stage.setTitle("EduSphere Register");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}