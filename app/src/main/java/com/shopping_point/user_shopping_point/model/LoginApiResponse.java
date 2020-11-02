package com.shopping_point.user_shopping_point.model;

public class LoginApiResponse {

    private int user_id;
    private String user_name;
    private String user_email;
    private boolean error;
    private String message;
    private String user_password;
    private String user_gender;
    private String user_dob;
    private String user_contact_number;
    private String token;
    private boolean isActive;


    public LoginApiResponse(int user_id, String token) {
        this.user_id = user_id;
        this.token = token;
    }

    public LoginApiResponse(String message) {
        this.message = message;
        this.error = true;
    }


    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }
    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getUser_password() {
        return user_password;
    }
    public String getUser_contact_number(){return user_contact_number; }
    public String getToken() {
        return token;
    }

    public boolean isActive() {
        return isActive;
    }

}
