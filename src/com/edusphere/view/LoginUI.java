package com.edusphere.view;

import com.edusphere.controller.UserController;
import com.edusphere.model.User;
import com.edusphere.session.Session;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("Login to EduSphere");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(200);

        Hyperlink registerLink = new Hyperlink("New user? Register here");

        loginBtn.setOnAction(e -> {

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

            if (user != null) {

                Session.currentUser = user;

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Welcome " + user.getName());
                alert.showAndWait();

                stage.close();

                try {
                    new DashboardUI().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Email or Password");
                alert.show();
            }
        });

        registerLink.setOnAction(e -> {
            stage.close();
            try {
                new RegisterUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.getChildren().addAll(title, emailField, passwordField, loginBtn, registerLink);

        Scene scene = new Scene(root, 400, 350);

        stage.setTitle("EduSphere Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}