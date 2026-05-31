package util;
import java.sql.*;
public class DatabaseInitializer {
	public static void initialize() {
		try {
			Connection conn=DBConnection.getConnection();
			Statement stmt=conn.createStatement();
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS posts ("+
			"post_id INT AUTO_INCREMENT PRIMARY KEY,"+
			"user_id INT,"+
			"content TEXT,"+
			"created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS post_attachments (" +
            "    attachment_id SERIAL PRIMARY KEY, " +
            "    post_id INT REFERENCES posts(post_id) ON DELETE CASCADE, " +
            "    file_name VARCHAR(255)," +
            "    file_path TEXT NOT NULL)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS comments("+
			"comment_id INT AUTO_INCREMENT PRIMARY KEY,"+
			"post_id INT,"+
			"user_id INT,"+
			"content TEXT,"+
			"created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS chat_rooms("+
			"room_id INT AUTO_INCREMENT PRIMARY KEY,"+
			"room_name VARCHAR(100))");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS chat_messages("+
			"message_id INT AUTO_INCREMENT PRIMARY KEY,"+
			"room_id INT,"+
			"user_id INT,"+
			"message TEXT,"+
			"sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS chatbot_rules("+
			"rule_id INT AUTO_INCREMENT PRIMARY KEY,"+
			"keyword VARCHAR(100),"+
			"response TEXT)");
			
			stmt.executeUpdate(
	                "CREATE TABLE IF NOT EXISTS users (" +
	                "id INT AUTO_INCREMENT PRIMARY KEY," +
	                "name VARCHAR(100)," +
	                "email VARCHAR(100)," +
	                "password VARCHAR(100)," +
	                "course VARCHAR(100))"
	        );
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
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
