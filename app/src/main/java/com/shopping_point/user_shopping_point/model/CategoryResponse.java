package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @SerializedName("category")
    private List<com.shopping_point.user_shopping_point.model.Category> category;

    public List<com.shopping_point.user_shopping_point.model.Category> getCategory() {
        return category;
    }

    public void setCategory(List<com.shopping_point.user_shopping_point.model.Category> category) {
        this.category = category;
    }
}
