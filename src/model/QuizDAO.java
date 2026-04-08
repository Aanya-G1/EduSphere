package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.List;

public class QuizDAO {

    public void addQuiz(Quiz quiz) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO quizzes (title, created_by, created_at) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, quiz.getTitle());
            stmt.setInt(2, quiz.getCreatedBy());
            stmt.setTimestamp(3, Timestamp.valueOf(quiz.getCreatedAt()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int quizId = rs.getInt(1);

                String qSql = "INSERT INTO questions (quiz_id, question, option1, option2, option3, option4, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement qStmt = conn.prepareStatement(qSql);

                for (Question q : quiz.getQuestions()) {
                    qStmt.setInt(1, quizId);
                    qStmt.setString(2, q.getQuestion());
                    qStmt.setString(3, q.getOption1());
                    qStmt.setString(4, q.getOption2());
                    qStmt.setString(5, q.getOption3());
                    qStmt.setString(6, q.getOption4());
                    qStmt.setInt(7, q.getCorrectAnswer());
                    qStmt.addBatch();
                }

                qStmt.executeBatch();
            }

            System.out.println("Quiz saved");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
