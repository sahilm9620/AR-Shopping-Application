package com.shopping_point.user_shopping_point.model;

public class LoginApiResponse {

    private int id;
    private String name;
    private String email;
    private boolean error;
    private String message;
    private String password;
    private String phone_no;
    private String token;
    private boolean isActive;


    public LoginApiResponse(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public LoginApiResponse(String message) {
        this.message = message;
        this.error = true;
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

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }
public String getPhone_no(){return phone_no; }
    public String getToken() {
        return token;
    }

    public boolean isActive() {
        return isActive;
    }

}
