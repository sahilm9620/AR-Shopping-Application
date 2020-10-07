package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteApiResponse {

    @SerializedName("favorites")
    private List<Product> favorites;

    public List<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Product> favorites) {
        this.favorites = favorites;
    }
}
