package com.edusphere.view;

import com.edusphere.session.Session;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DashboardUI extends Application {

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        // ===== SIDEBAR =====
        VBox sidebar = new VBox(18);
        sidebar.setPadding(new Insets(25));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #0B1020, #111827);");

        ImageView logoImg = new ImageView(new Image(getClass().getResourceAsStream("/assets/logo.png")));
        logoImg.setFitWidth(42);
        logoImg.setFitHeight(42);

        Label logoText = new Label("EduSphere");
        logoText.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        HBox logoBox = new HBox(10, logoImg, logoText);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        Button dashboardBtn = createSidebarButton("Dashboard");
        Button feedBtn = createSidebarButton("Academic Feed");
        Button chatroomBtn = createSidebarButton("Chatroom");
        Button assistantBtn = createSidebarButton("AI Assistant");
        Button quizBtn = createSidebarButton("Quiz");
        Button logoutBtn = createSidebarButton("Logout");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(
                logoBox,
                dashboardBtn,
                feedBtn,
                chatroomBtn,
                assistantBtn,
                quizBtn,
                spacer,
                logoutBtn
        );

        // ===== HEADER =====
        HBox header = new HBox();
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: white; -fx-border-color: #E5E7EB; -fx-border-width: 0 0 1 0;");

        String userName = (Session.currentUser != null) ? Session.currentUser.getName() : "Student";
        String course = (Session.currentUser != null) ? Session.currentUser.getCourse() : "Course";

        VBox welcomeBox = new VBox(4);
        Label welcome = new Label("Welcome back, " + userName + "!");
        welcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Label courseLabel = new Label(course);
        courseLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #6B7280;");

        welcomeBox.getChildren().addAll(welcome, courseLabel);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/assets/avatar.png")));
        avatar.setFitWidth(45);
        avatar.setFitHeight(45);
        avatar.setClip(new Circle(22.5, 22.5, 22.5));

        Label profileName = new Label(userName);
        profileName.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        HBox profileBox = new HBox(10, avatar, profileName);
        profileBox.setAlignment(Pos.CENTER);

        header.getChildren().addAll(welcomeBox, headerSpacer, profileBox);

        // ===== CENTER =====
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(25));
        centerBox.setStyle("-fx-background-color: #F8FAFC;");

        Label dashTitle = new Label("Dashboard");
        dashTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Label dashSub = new Label("Access your academic tools and connect with your peers.");
        dashSub.setStyle("-fx-font-size: 14px; -fx-text-fill: #6B7280;");

        VBox titleBox = new VBox(6, dashTitle, dashSub);

        HBox cardsRow = new HBox(18);
        cardsRow.setAlignment(Pos.CENTER_LEFT);

        VBox feedCard = createCard("/assets/feed.png", "Academic Feed",
                "Stay updated with announcements, notes, and academic updates.",
                "#4F46E5", "Open Feed");

        VBox chatCard = createCard("/assets/chatroom.png", "Chatroom",
                "Connect with peers, join discussions, and share knowledge.",
                "#10B981", "Open Chatroom");

        VBox botCard = createCard("/assets/bot.png", "AI Assistant",
                "Ask doubts, get explanations, and learn better with AI help.",
                "#2563EB", "Chat Now");

        VBox quizCard = createCard("/assets/quiz.png", "Quiz",
                "Test your knowledge with quizzes and assessments.",
                "#F97316", "Go to Quiz");

        cardsRow.getChildren().addAll(feedCard, chatCard, botCard, quizCard);

        centerBox.getChildren().addAll(titleBox, cardsRow);

        root.setLeft(sidebar);
        root.setTop(header);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("EduSphere Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(170);
        btn.setPrefHeight(42);
        btn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-alignment: CENTER-LEFT;" +
                "-fx-padding: 0 0 0 18;"
        );

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #312E81;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-alignment: CENTER-LEFT;" +
                "-fx-padding: 0 0 0 18;"
        ));

        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-alignment: CENTER-LEFT;" +
                "-fx-padding: 0 0 0 18;"
        ));

        return btn;
    }

    private VBox createCard(String imagePath, String title, String desc, String color, String btnText) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setPrefSize(220, 320);
        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;"
        );

        DropShadow shadow = new DropShadow();
        shadow.setRadius(12);
        shadow.setOffsetY(3);
        shadow.setColor(Color.rgb(0, 0, 0, 0.08));
        card.setEffect(shadow);

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        image.setFitWidth(90);
        image.setFitHeight(90);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Label descLabel = new Label(desc);
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #6B7280;");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button actionBtn = new Button(btnText);
        actionBtn.setPrefWidth(170);
        actionBtn.setPrefHeight(40);
        actionBtn.setStyle(
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;"
        );

        card.getChildren().addAll(image, titleLabel, descLabel, spacer, actionBtn);
        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
