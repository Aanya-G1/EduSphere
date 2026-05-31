package controller;

import dao.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Quiz;
import model.Result;

import java.util.List;

public class LeaderboardController {

	@FXML private Label quizTitle;
	@FXML private VBox leaderboardContainer;
	@FXML
    private Button backBtn;

	public void loadLeaderboard(Quiz quiz) {
		quizTitle.setText("Leaderboard: " + quiz.getTitle());
		leaderboardContainer.getChildren().clear();
		QuizDAO dao = new QuizDAO();
		List<Result> results = dao.getResultsByQuizId(quiz.getQuizId());
		if (results.isEmpty()) {
			Label noData = new Label("No attempts yet for this quiz.");
			noData.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
			leaderboardContainer.getChildren().add(noData);
			return;
		}
		int rank = 1;
		for (Result result : results) {
			HBox row = new HBox(20);
			row.setStyle("-fx-background-color: white; -fx-border-color: #CAF0F8; -fx-border-width: 1; -fx-padding: 10;");
			Label rankLabel = new Label("#" + rank);
			rankLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-min-width: 40;");
			Label userLabel = new Label("User: " + result.getUserId());
			userLabel.setStyle("-fx-font-size: 14px; -fx-min-width: 150;");
			Label scoreLabel = new Label("Score: " + result.getScore() + " / " + result.getTotal());
			scoreLabel.setStyle("-fx-font-size: 14px;");
			row.getChildren().addAll(rankLabel, userLabel, scoreLabel);
			leaderboardContainer.getChildren().add(row);
			rank++;
		}
	}
	@FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/quiz.fxml"));
            
            Stage stage = (Stage) backBtn.getScene().getWindow();
            
            stage.setScene(new Scene(loader.load(), 1000, 800)); 
            stage.setTitle("EduSphere"); 
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to navigate back.");
        }
    }
}
