package dao;

import model.Question;
import model.Quiz;
import model.Result;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

	public void addQuiz(Quiz quiz) {
		try {
			Connection conn = DBConnection.getConnection();
			String sql = "INSERT INTO quizzes (title, subject, created_by, created_at) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, quiz.getTitle());
			stmt.setString(2, quiz.getSubject());
			stmt.setInt(3, quiz.getCreatedBy());
			stmt.setTimestamp(4, Timestamp.valueOf(quiz.getCreatedAt()));
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
			System.out.println("Quiz saved successfully!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Quiz> getAllQuizzes() {
		List<Quiz> quizzes = new ArrayList<>();
		try {
			Connection conn = DBConnection.getConnection();
			String sql = "SELECT * FROM quizzes";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Quiz quiz = new Quiz();
				quiz.setQuizId(rs.getInt("quiz_id"));
				quiz.setTitle(rs.getString("title"));
				quiz.setSubject(rs.getString("subject"));
				quiz.setCreatedBy(rs.getInt("created_by"));
				quiz.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				quizzes.add(quiz);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return quizzes;
	}

	public List<Quiz> getQuizzesBySubject(String subject) {
		List<Quiz> quizzes = new ArrayList<>();
		try {
			Connection conn = DBConnection.getConnection();
			String sql = "SELECT * FROM quizzes WHERE subject = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, subject);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Quiz quiz = new Quiz();
				quiz.setQuizId(rs.getInt("quiz_id"));
				quiz.setTitle(rs.getString("title"));
				quiz.setSubject(rs.getString("subject"));
				quiz.setCreatedBy(rs.getInt("created_by"));
				quiz.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				quizzes.add(quiz);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return quizzes;
	}

	public List<Quiz> getQuizzesByUser(int userId) {
		List<Quiz> quizzes = new ArrayList<>();
		try {
			Connection conn = DBConnection.getConnection();
			String sql = "SELECT * FROM quizzes WHERE created_by = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Quiz quiz = new Quiz();
				quiz.setQuizId(rs.getInt("quiz_id"));
				quiz.setTitle(rs.getString("title"));
				quiz.setSubject(rs.getString("subject"));
				quiz.setCreatedBy(rs.getInt("created_by"));
				quiz.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				quizzes.add(quiz);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return quizzes;
	}

	public List<Question> getQuestionsByQuizId(int quizId) {
		List<Question> questions = new ArrayList<>();
		try {
			Connection conn = DBConnection.getConnection();
			String sql = "SELECT * FROM questions WHERE quiz_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, quizId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Question q = new Question();
				q.setQuestionId(rs.getInt("question_id"));
				q.setQuizId(rs.getInt("quiz_id"));
				q.setQuestion(rs.getString("question"));
				q.setOption1(rs.getString("option1"));
				q.setOption2(rs.getString("option2"));
				q.setOption3(rs.getString("option3"));
				q.setOption4(rs.getString("option4"));
				q.setCorrectAnswer(rs.getInt("correct_answer"));
				questions.add(q);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return questions;
	}

	public void saveResult(Result result) {
		try {
			Connection conn = DBConnection.getConnection();
			String sql = "INSERT INTO quiz_results (user_id, quiz_id, score, total) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, result.getUserId());
			stmt.setInt(2, result.getQuizId());
			stmt.setInt(3, result.getScore());
			stmt.setInt(4, result.getTotal());
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Result saved successfully!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Result> getResultsByQuizId(int quizId) {
		List<Result> results = new ArrayList<>();
		try {
			Connection conn = DBConnection.getConnection();
			String sql = "SELECT * FROM quiz_results WHERE quiz_id = ? ORDER BY score DESC";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, quizId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Result result = new Result();
				result.setResultId(rs.getInt("result_id"));
				result.setUserId(rs.getInt("user_id"));
				result.setQuizId(rs.getInt("quiz_id"));
				result.setScore(rs.getInt("score"));
				result.setTotal(rs.getInt("total"));
				results.add(result);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<Result> getResultsByUserId(int userId) {
		List<Result> results = new ArrayList<>();
		try {
			Connection conn = DBConnection.getConnection();
			String sql = "SELECT * FROM quiz_results WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Result result = new Result();
				result.setResultId(rs.getInt("result_id"));
				result.setUserId(rs.getInt("user_id"));
				result.setQuizId(rs.getInt("quiz_id"));
				result.setScore(rs.getInt("score"));
				result.setTotal(rs.getInt("total"));
				results.add(result);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
}
