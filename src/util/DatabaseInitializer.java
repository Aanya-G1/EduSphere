package util;
import java.sql.*;
public class DatabaseInitializer {
	public static void initialize() {
		try {
			Connection conn=DBConnection.getConnection();
			Statement stmt=conn.createStatement();
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS chatbot_rules("+
			"rule_id INT AUTO_INCREMENT PRIMARY KEY,"+
			"keyword VARCHAR(100),"+
			"response TEXT)");
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
