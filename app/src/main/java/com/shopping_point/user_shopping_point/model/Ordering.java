package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.SerializedName;

public class Ordering {

    @SerializedName("orderId")
    private String orderId;
    @SerializedName("paymentId")
    private String paymentId;
    private int userId;
    private int productId;

    public Ordering(String orderId, String paymentId, int userId, int productId) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.userId = userId;
        this.productId = productId;
    }
}
