package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class botTest extends Application{
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Chatbot.fxml"));
			Scene scene=new Scene(loader.load(),350, 550);
			stage.setScene(scene);
			stage.setTitle("Chatbot");
			stage.show();
		}
		catch(Exception e) {
			System.out.println("chatbot UI not working!");
			e.printStackTrace();
		}
	}
	public static void main(String []args) {
		launch(args);
	}
}
