package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Question;
import model.Result;

import java.util.List;

public class QuizResultController {

	@FXML private Label scoreLabel;
	@FXML private Label summaryLabel;
	@FXML private VBox reviewContainer;

	public void loadResult(Result result, List<Question> questions, List<Integer> userAnswers) {
		scoreLabel.setText("Your Score: " + result.getScore() + " / " + result.getTotal());
		int percentage = (result.getTotal() > 0) ? (result.getScore() * 100 / result.getTotal()) : 0;
		summaryLabel.setText("You scored " + percentage + "%. " + getPerformanceMessage(percentage));
		reviewContainer.getChildren().clear();
		for (int i = 0; i < questions.size(); i++) {
			Question q = questions.get(i);
			int userAns = (i < userAnswers.size()) ? userAnswers.get(i) : 0;
			boolean correct = (userAns == q.getCorrectAnswer());
			HBox row = new HBox(10);
			Label qLabel = new Label((i + 1) + ". " + q.getQuestion());
			qLabel.setWrapText(true);
			qLabel.setMaxWidth(600);
			Label resultLabel = new Label(correct ? "✔ Correct" : "✘ Wrong (Correct: Option " + q.getCorrectAnswer() + ")");
			resultLabel.setStyle(correct ? "-fx-text-fill: green; -fx-font-weight: bold;" : "-fx-text-fill: red; -fx-font-weight: bold;");
			row.getChildren().addAll(qLabel, resultLabel);
			reviewContainer.getChildren().add(row);
		}
	}

	private String getPerformanceMessage(int percentage) {
		if (percentage >= 80) return "Excellent work!";
		if (percentage >= 50) return "Good effort!";
		return "Keep practicing!";
	}
}
