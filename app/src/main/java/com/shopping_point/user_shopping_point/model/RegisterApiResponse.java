package com.shopping_point.user_shopping_point.model;

public class RegisterApiResponse {

    private int id;
    private boolean error;
    private String message;
    private User user;

    public RegisterApiResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

}
