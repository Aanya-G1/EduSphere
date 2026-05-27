package util;

import java.sql.Connection;
import java.sql.Statement;

import com.edusphere.database.DBConnection;

public class DatabaseInitializer {

    public static void initialize() {

        try {

            Connection conn = DBConnection.getConnection();

            Statement stmt = conn.createStatement();

            //USERS 
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100)," +
                "email VARCHAR(100)," +
                "password VARCHAR(100)," +
                "course VARCHAR(100))"
            );

            // QUIZZES TABLE
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS quizzes (" +
                "quiz_id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255)," +
                "subject VARCHAR(100)," +
                "created_by INT," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
            );

            //QUESTIONS 
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS questions (" +
                "question_id INT AUTO_INCREMENT PRIMARY KEY," +
                "quiz_id INT," +
                "question TEXT," +
                "option1 VARCHAR(255)," +
                "option2 VARCHAR(255)," +
                "option3 VARCHAR(255)," +
                "option4 VARCHAR(255)," +
                "correct_answer INT)"
            );

            //QUIZ RESULTS
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS quiz_results (" +
                "result_id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT," +
                "quiz_id INT," +
                "score INT," +
                "total INT)"
            );

            System.out.println("All tables created successfully!");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}