package com.edusphere.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String course;

    public User(int id, String name, String email, String password, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
    }

    public User(String name, String email, String password, String course) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getCourse() { return course; }
}