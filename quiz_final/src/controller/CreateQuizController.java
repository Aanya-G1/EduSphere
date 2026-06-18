package controller;

import dao.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Question;
import model.Quiz;
import util.TestUser;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateQuizController implements Initializable {

	@FXML private TextField titleField;
	@FXML private ChoiceBox<String> subjectChoice;
	@FXML private TextField qField;
	@FXML private TextField o1, o2, o3, o4;
	@FXML private TextField correctField;
	@FXML private Button addQuestionBtn;
	@FXML private Button saveQuizBtn;
	@FXML private ListView<String> questionsList;
	@FXML private Label questionCount;

	private String[] subjects = {"Data Structures", "Microprocessors", "Machine Learning"};
	private List<Question> questions = new ArrayList<>();
	private Runnable onSaveCallback;

	public void setOnSaveCallback(Runnable callback) {
		this.onSaveCallback = callback;
	}

	public void initialize(URL location, ResourceBundle resources) {
		subjectChoice.getItems().addAll(subjects);
		subjectChoice.setValue("Select Subject");
		addQuestionBtn.setOnAction(this::addQuestion);
		saveQuizBtn.setOnAction(this::saveQuiz);
	}

	@FXML
	private void addQuestion(ActionEvent event) {
		if (qField.getText().isEmpty() || o1.getText().isEmpty() || o2.getText().isEmpty()
				|| o3.getText().isEmpty() || o4.getText().isEmpty() || correctField.getText().isEmpty()) {
			System.out.println("Please fill all question fields!");
			return;
		}
		try {
			Question q = new Question();
			q.setQuestion(qField.getText());
			q.setOption1(o1.getText());
			q.setOption2(o2.getText());
			q.setOption3(o3.getText());
			q.setOption4(o4.getText());
			q.setCorrectAnswer(Integer.parseInt(correctField.getText()));
			questions.add(q);
			questionsList.getItems().add((questions.size()) + ". " + qField.getText());
			questionCount.setText("Questions Added: " + questions.size());
			qField.clear();
			o1.clear();
			o2.clear();
			o3.clear();
			o4.clear();
			correctField.clear();
		}
		catch (NumberFormatException e) {
			System.out.println("Correct answer must be a number between 1 and 4!");
		}
	}

	@FXML
	private void saveQuiz(ActionEvent event) {
		if (titleField.getText().isEmpty() || subjectChoice.getValue().equals("Select Subject") || questions.isEmpty()) {
			System.out.println("Please fill quiz title, subject and add at least one question!");
			return;
		}
		Quiz quiz = new Quiz();
		quiz.setTitle(titleField.getText());
		quiz.setSubject(subjectChoice.getValue());
		quiz.setCreatedBy(TestUser.getInstance().getUserId());
		quiz.setCreatedAt(LocalDateTime.now());
		quiz.setQuestions(questions);
		new QuizDAO().addQuiz(quiz);
		if (onSaveCallback != null) {
			onSaveCallback.run();
		}
		Stage stage = (Stage) saveQuizBtn.getScene().getWindow();
		stage.close();
	}
}
