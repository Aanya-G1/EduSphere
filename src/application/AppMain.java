package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginUI; 

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            new LoginUI().start(primaryStage);
        } catch(Exception e) {
            System.out.println("Failed to load Login UI!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}
