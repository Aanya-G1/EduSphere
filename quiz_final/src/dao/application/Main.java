package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DatabaseInitializer;

public class Main extends Application {
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/quiz.fxml"));
			Scene scene = new Scene(loader.load(), 1000, 800);
			stage.setScene(scene);
			stage.setTitle("Quiz");
			stage.show();
		}
		catch (Exception e) {
			System.out.println("Quiz UI not working!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//DatabaseInitializer.initialize();
		launch(args);
	}
}
