package com.shopping_point.user_shopping_point.model;

public class Upload {


    private String image;
    private  int userId;

    public Upload(String encode_image, int userId) {

        this.image = encode_image;
        this.userId = userId;



    }

    public int getUserId() {
        return userId;
    }

    public String getImage() {
        return image;
    }


}