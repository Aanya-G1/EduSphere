package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class chatTest extends Application{
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/chatroom.fxml"));
			Scene scene=new Scene(loader.load(),1000, 800);
			stage.setScene(scene);
			stage.setTitle("ChatRoom");
			stage.show();
		}
		catch(Exception e) {
			System.out.println("chatroom UI not working!");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
