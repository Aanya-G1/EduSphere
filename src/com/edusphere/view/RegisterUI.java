package com.edusphere.view;

import com.edusphere.controller.UserController;
import com.edusphere.model.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("Create Account");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        TextField courseField = new TextField();
        courseField.setPromptText("Enter Course");

        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(250);

        Hyperlink loginLink = new Hyperlink("Already have an account? Login");

        registerBtn.setOnAction(e -> {
            try {
                User user = new User(
                        nameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        courseField.getText()
                );

                UserController controller = new UserController();
                boolean success = controller.registerUser(user);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                if (success) {
                    alert.setContentText("Registration Successful!");
                    alert.showAndWait();

                    stage.close();
                    new LoginUI().start(new Stage());

                } else {
                    alert.setContentText("Registration Failed!");
                    alert.show();
                }

            } catch (Exception ex) {
                ex.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error: " + ex.getMessage());
                alert.show();
            }
        });

        loginLink.setOnAction(e -> {
            try {
                stage.close();
                new LoginUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.getChildren().addAll(
                title,
                nameField,
                emailField,
                passwordField,
                courseField,
                registerBtn,
                loginLink
        );

        Scene scene = new Scene(root, 400, 450);

        stage.setTitle("EduSphere Register");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}