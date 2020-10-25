package com.shopping_point.user_shopping_point.model;

public class UpdateApiResponse {

    private String name;
    private String email;
    private String phone_no;
    private boolean error;
    private String message;
    private String gender;
    private String dob;

    public UpdateApiResponse(String email,String name, String phone_no, String gender, String dob, boolean error, String message) {
        this.name = name;
        this.email = email;
        this.phone_no = phone_no;
        this.gender = gender;
        this.dob=dob;
        this.error = error;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }
}
