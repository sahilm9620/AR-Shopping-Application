package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderApiResponse {

    @SerializedName("orders")
    private List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
