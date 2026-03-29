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
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
