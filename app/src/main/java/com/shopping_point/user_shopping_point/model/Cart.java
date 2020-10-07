package com.shopping_point.user_shopping_point.model;

public class Cart {

    private int userId;
    private int productId;

    public Cart(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
