package controller;

import dao.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Quiz;
import util.TestUser;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

	@FXML private VBox quizContainer;
	@FXML private ChoiceBox<String> subjectFilter;
	@FXML private MenuItem createQuizMenu;
	@FXML private MenuItem myQuizzesMenu;

	private String[] subjects = {"All", "Data Structures", "Microprocessors", "Machine Learning"};

	public void initialize(URL location, ResourceBundle resources) {
		subjectFilter.getItems().addAll(subjects);
		subjectFilter.setValue("All");
		subjectFilter.setOnAction(this::filterBySubject);
		createQuizMenu.setOnAction(this::openCreateQuiz);
		myQuizzesMenu.setOnAction(this::openMyQuizzes);
		loadQuizzes();
	}

	public void loadQuizzes() {
		quizContainer.getChildren().clear();
		QuizDAO dao = new QuizDAO();
		List<Quiz> quizzes = dao.getAllQuizzes();
		for (Quiz quiz : quizzes) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/quizCard.fxml"));
				VBox card = loader.load();
				QuizCardController controller = loader.getController();
				controller.loadData(quiz, this);
				quizContainer.getChildren().add(card);
			}
			catch (Exception e) {
				System.out.println("Couldn't load quiz card! " + e.getMessage());
			}
		}
	}

	@FXML
	private void filterBySubject(ActionEvent event) {
		String selected = subjectFilter.getValue();
		quizContainer.getChildren().clear();
		QuizDAO dao = new QuizDAO();
		List<Quiz> quizzes;
		if (selected.equals("All")) {
			quizzes = dao.getAllQuizzes();
		} else {
			quizzes = dao.getQuizzesBySubject(selected);
		}
		for (Quiz quiz : quizzes) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/quizCard.fxml"));
				VBox card = loader.load();
				QuizCardController controller = loader.getController();
				controller.loadData(quiz, this);
				quizContainer.getChildren().add(card);
			}
			catch (Exception e) {
				System.out.println("Couldn't load quiz card! " + e.getMessage());
			}
		}
	}

	@FXML
	private void openCreateQuiz(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/createQuiz.fxml"));
			Scene scene = new Scene(loader.load(), 1000, 800);
			CreateQuizController controller = loader.getController();
			controller.setOnSaveCallback(() -> loadQuizzes());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Create Quiz");
			stage.show();
		}
		catch (Exception e) {
			System.out.println("Create Quiz UI not working!");
			e.printStackTrace();
		}
	}

	@FXML
	private void openMyQuizzes(ActionEvent event) {
		quizContainer.getChildren().clear();
		QuizDAO dao = new QuizDAO();
		List<Quiz> quizzes = dao.getQuizzesByUser(TestUser.getInstance().getUserId());
		for (Quiz quiz : quizzes) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/quizCard.fxml"));
				VBox card = loader.load();
				QuizCardController controller = loader.getController();
				controller.loadData(quiz, this);
				quizContainer.getChildren().add(card);
			}
			catch (Exception e) {
				System.out.println("Couldn't load quiz card! " + e.getMessage());
			}
		}
	}

	public void openAttemptQuiz(Quiz quiz) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/quizAttempt.fxml"));
			Scene scene = new Scene(loader.load(), 1000, 800);
			QuizAttemptController controller = loader.getController();
			controller.loadQuiz(quiz);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Attempt Quiz");
			stage.show();
		}
		catch (Exception e) {
			System.out.println("Quiz Attempt UI not working!");
			e.printStackTrace();
		}
	}
}
