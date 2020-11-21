package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category_name")
    @Expose
    private String catehory_name;

    public String getCategory_name() {
        return catehory_name;
    }
}