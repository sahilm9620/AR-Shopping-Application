package com.shopping_point.user_shopping_point.model;

public class User {

    private int id;
    private String user_name;
    private String user_email;
    private String user_password;
    private String user_contact_number;

    private boolean isActive;

    public User(String user_name, String user_email, String user_contact_number, String user_password) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_contact_number = user_contact_number;
        this.user_password = user_password;

    }

    public User(int id, String user_name, String user_email, String user_password, String user_contact_number, boolean isActive) {
        this.id = id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_contact_number = user_contact_number;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_contact_number(){return user_contact_number;}

    public boolean isActive() {
        return isActive;
    }



}
