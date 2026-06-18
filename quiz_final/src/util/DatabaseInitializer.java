package util;

import java.sql.*;

public class DatabaseInitializer {
	public static void initialize() {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS quizzes (" +
				"quiz_id INT AUTO_INCREMENT PRIMARY KEY," +
				"title VARCHAR(255)," +
				"subject VARCHAR(100)," +
				"created_by INT," +
				"created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS questions (" +
				"question_id INT AUTO_INCREMENT PRIMARY KEY," +
				"quiz_id INT," +
				"question TEXT," +
				"option1 VARCHAR(255)," +
				"option2 VARCHAR(255)," +
				"option3 VARCHAR(255)," +
				"option4 VARCHAR(255)," +
				"correct_answer INT)");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS quiz_results (" +
				"result_id INT AUTO_INCREMENT PRIMARY KEY," +
				"user_id INT," +
				"quiz_id INT," +
				"score INT," +
				"total INT)");

			System.out.println("Quiz tables initialized successfully!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
