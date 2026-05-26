package util;
import java.sql.*;
public class DatabaseInitializer {
	public static void initialize() {
		try {
			Connection conn=DBConnection.getConnection();
			Statement stmt=conn.createStatement();
			
			stmt.executeUpdate(
    		"CREATE TABLE IF NOT EXISTS chatbot_rules (" +
        	"rule_id INT AUTO_INCREMENT PRIMARY KEY," +
        	"course VARCHAR(100)," +
        	"subject VARCHAR(100)," +
        	"category VARCHAR(100)," +
        	"keywords TEXT," +
        	"response TEXT" +
    		")"
			);

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS chat_rooms("+
			"room_id INT AUTO_INCREMENT PRIMARY KEY,"+
			"room_name VARCHAR(100))");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS chat_messages("+
			"message_id INT AUTO_INCREMENT PRIMARY KEY,"+
			"room_id INT,"+
			"user_id INT,"+
			"message TEXT,"+
			"sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
