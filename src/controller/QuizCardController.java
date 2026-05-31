package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Quiz;

public class QuizCardController {

	@FXML private Label quizTitle;
	@FXML private Label quizSubject;
	@FXML private Label quizCreator;
	@FXML private Button attemptBtn;
	@FXML private Button leaderboardBtn;

	private Quiz currentQuiz;
	private QuizController quizController;

	public void loadData(Quiz quiz, QuizController quizController) {
		this.currentQuiz = quiz;
		this.quizController = quizController;
		quizTitle.setText(quiz.getTitle());
		quizSubject.setText("Subject: " + quiz.getSubject());
		quizCreator.setText("Created by User: " + quiz.getCreatedBy());
		attemptBtn.setOnAction(this::attemptQuiz);
		leaderboardBtn.setOnAction(this::openLeaderboard);
	}

	@FXML
	private void attemptQuiz(ActionEvent event) {
		quizController.openAttemptQuiz(currentQuiz);
	}

	@FXML
	private void openLeaderboard(ActionEvent event) {
		try {
			javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/leaderboard.fxml"));
			javafx.scene.Scene scene = new javafx.scene.Scene(loader.load(), 1000, 800);
			LeaderboardController controller = loader.getController();
			controller.loadLeaderboard(currentQuiz);
			javafx.stage.Stage stage = new javafx.stage.Stage();
			stage.setScene(scene);
			stage.setTitle("Leaderboard - " + currentQuiz.getTitle());
			stage.show();
		}
		catch (Exception e) {
			System.out.println("Leaderboard UI not working!");
			e.printStackTrace();
		}
	}
}
