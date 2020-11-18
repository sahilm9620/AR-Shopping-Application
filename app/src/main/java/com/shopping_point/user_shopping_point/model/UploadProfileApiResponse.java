package com.shopping_point.user_shopping_point.model;

public class UploadProfileApiResponse {

    private boolean error;
    private String message;


    public UploadProfileApiResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }


    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }


}
