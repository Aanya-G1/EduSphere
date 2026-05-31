package controller;

import dao.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Question;
import model.Quiz;
import model.Result;
import util.QuizTimer;
import util.UserSession;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class QuizAttemptController {

	@FXML private Label quizTitle;
	@FXML private Label questionLabel;
	@FXML private Label questionNumber;
	@FXML private Label timerLabel;
	@FXML private RadioButton op1, op2, op3, op4;
	@FXML private Button nextBtn;
	@FXML
    private Button backBtn;

	private ToggleGroup optionGroup = new ToggleGroup();
	private List<Question> questions = new ArrayList<>();
	private int currentIndex = 0;
	private int score = 0;
	private Quiz currentQuiz;
	private QuizTimer timer;
	private List<Integer> userAnswers = new ArrayList<>();

	public void loadQuiz(Quiz quiz) {
		this.currentQuiz = quiz;
		QuizDAO dao = new QuizDAO();
		questions = dao.getQuestionsByQuizId(quiz.getQuizId());
		quizTitle.setText(quiz.getTitle());
		op1.setToggleGroup(optionGroup);
		op2.setToggleGroup(optionGroup);
		op3.setToggleGroup(optionGroup);
		op4.setToggleGroup(optionGroup);
		nextBtn.setOnAction(this::nextQuestion);
		loadQuestion();
		timer = new QuizTimer(questions.size() * 30, timerLabel, this::submitQuiz);
		timer.setDaemon(true);
		timer.start();
	}

	private void loadQuestion() {
		if (currentIndex >= questions.size()) {
			submitQuiz();
			return;
		}
		Question q = questions.get(currentIndex);
		questionNumber.setText("Question " + (currentIndex + 1) + " of " + questions.size());
		questionLabel.setText(q.getQuestion());
		op1.setText(q.getOption1());
		op2.setText(q.getOption2());
		op3.setText(q.getOption3());
		op4.setText(q.getOption4());
		optionGroup.selectToggle(null);
	}

	@FXML
	private void nextQuestion(ActionEvent event) {
		int selected = getSelectedOption();
		userAnswers.add(selected);
		if (selected == questions.get(currentIndex).getCorrectAnswer()) {
			score++;
		}
		currentIndex++;
		if (currentIndex < questions.size()) {
			loadQuestion();
		} else {
			submitQuiz();
		}
	}

	private int getSelectedOption() {
		if (op1.isSelected()) return 1;
		if (op2.isSelected()) return 2;
		if (op3.isSelected()) return 3;
		if (op4.isSelected()) return 4;
		return 0;
	}

	private void submitQuiz() {
		if (timer != null) {
			timer.stopTimer();
		}
		Result result = new Result();
		User actualUser = UserSession.getInstance().getUser();
	    if (actualUser == null) {
	        System.out.println("Cannot save score: No user logged in!");
	        return;
	    }
	    result.setUserId(actualUser.getId());
		result.setQuizId(currentQuiz.getQuizId());
		result.setScore(score);
		result.setTotal(questions.size());
		new QuizDAO().saveResult(result);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/quizResult.fxml"));
			Scene scene = new Scene(loader.load(), 1000, 800);
			QuizResultController controller = loader.getController();
			controller.loadResult(result, questions, userAnswers);
			Stage stage = (Stage) nextBtn.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Quiz Result");
		}
		catch (Exception e) {
			System.out.println("Result UI not working!");
			e.printStackTrace();
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
