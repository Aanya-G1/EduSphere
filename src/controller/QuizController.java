package controller;

import dao.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.*;
import util.TestUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuizController {

    @FXML private TextField titleField, qField, o1, o2, o3, o4, correct;

    private List<Question> questions = new ArrayList<>();

    @FXML
    public void addQuestion() {
        Question q = new Question();
        q.setQuestion(qField.getText());
        q.setOption1(o1.getText());
        q.setOption2(o2.getText());
        q.setOption3(o3.getText());
        q.setOption4(o4.getText());
        q.setCorrectAnswer(Integer.parseInt(correct.getText()));

        questions.add(q);
    }

    @FXML
    public void saveQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle(titleField.getText());
        quiz.setCreatedBy(TestUser.getInstance().getUserId());
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setQuestions(questions);

        new QuizDAO().addQuiz(quiz);
    }
}
