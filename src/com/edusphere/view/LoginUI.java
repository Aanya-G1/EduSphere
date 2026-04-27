package com.edusphere.view;

import com.edusphere.controller.UserController;
import com.edusphere.model.User;
import com.edusphere.session.Session;

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

public class LoginUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("User Login");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button loginBtn = new Button("Login");

        loginBtn.setOnAction(e -> {
            try {
                String email = emailField.getText();
                String password = passwordField.getText();

                if (email.isEmpty() || password.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please fill all fields!");
                    alert.show();
                    return;
                }

                UserController controller = new UserController();
                User user = controller.loginUser(email, password);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                if (user != null) {
                    Session.currentUser = user;
                    System.out.println("Logged in user: " + Session.currentUser.getName());
                    alert.setContentText("Welcome " + user.getName());
                } else {
                    alert.setContentText("Invalid Email or Password");
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
        root.getChildren().addAll(title, emailField, passwordField, loginBtn);

        Scene scene = new Scene(root, 350, 250);

        stage.setTitle("EduSphere Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}