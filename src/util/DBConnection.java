package util;
import java.sql.*;
public class DBConnection {
	private static final String URL="jdbc:mysql://edusphere-my-sql-edusphere.f.aivencloud.com:23446/edusphere?ssl-mode=REQUIRED";
	private static final String Username="avnadmin";
	private static final String Password="AVNS_ZoCi3XEDv8tQnyyexKi";
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(URL,Username,Password);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
