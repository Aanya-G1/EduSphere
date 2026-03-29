package com.edusphere.test;

import com.edusphere.controller.UserController;
import com.edusphere.model.User;

public class TestConnection {

    public static void main(String[] args) {

        UserController uc = new UserController();

        // REGISTER TEST
        User user = new User("Drishti", "drishti@gmail.com", "1234", "B.Tech");

        boolean reg = uc.registerUser(user);
        System.out.println("Registration: " + reg);

        // LOGIN TEST
        boolean login = uc.loginUser("drishti@gmail.com", "1234");
        System.out.println("Login: " + login);
    }
}