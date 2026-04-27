package com.edusphere.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.edusphere.database.DBConnection;
import com.edusphere.model.User;

public class UserController {

    public boolean registerUser(User user) {

        System.out.println("➡ registerUser() called");

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("❌ DB Connection NULL");
                return false;
            }

            String sql = "INSERT INTO users (name, email, password, course) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getCourse());

            int result = ps.executeUpdate();

            System.out.println("✔ Rows inserted = " + result);

            return result > 0;

        } catch (Exception e) {
            System.out.println("❌ Error in registerUser");
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String email, String password) {

        System.out.println("➡ loginUser() called");

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("❌ DB Connection NULL");
                return null;
            }

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("course")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error in loginUser");
            e.printStackTrace();
        }

        return null;
    }
}