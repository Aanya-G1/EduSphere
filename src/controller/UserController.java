package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DBConnection;
import model.User;

public class UserController {

    // REGISTER USER
    public boolean registerUser(User user) {

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("DB Connection NULL");
                return false;
            }

            String sql = "INSERT INTO users (name, email, password, course) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getCourse());

            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   //Login user
    public User loginUser(String email, String password) {

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("DB Connection NULL");
                return null;
            }

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(1, email.trim());
            ps.setString(2, password.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                		rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("course")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}