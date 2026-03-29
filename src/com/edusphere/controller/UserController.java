package com.edusphere.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.edusphere.database.DBConnection;
import com.edusphere.model.User;

public class UserController {
    public boolean registerUser(User user) {

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed!");
                return false;
            }

            String sql = "INSERT INTO users (name, email, password, course) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getCourse());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("User registered successfully!");
                return true;
            } else {
                System.out.println("Registration failed!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean loginUser(String email, String password) {

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed!");
                return false;
            }

            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid email or password!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}