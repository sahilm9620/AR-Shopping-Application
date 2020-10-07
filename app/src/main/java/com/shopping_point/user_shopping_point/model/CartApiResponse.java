package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartApiResponse {

    @SerializedName("carts")
    private List<Product> carts;

    public List<Product> getProductsInCart() {
        return carts;
    }

    public void setProductsInCart(List<Product> carts) {
        this.carts = carts;
    }
}
