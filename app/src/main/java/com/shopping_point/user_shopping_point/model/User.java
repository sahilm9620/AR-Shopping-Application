package com.shopping_point.user_shopping_point.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String phone_no;
    private boolean isActive;

    public User(String name, String email, String phone_no, String password) {
        this.name = name;
        this.email = email;
        this.phone_no=phone_no;
        this.password = password;

    }

    public User(int id, String name, String email, String password, String phone_no, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone_no=phone_no;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone_no(){return phone_no;}

    public boolean isActive() {
        return isActive;
    }

}
