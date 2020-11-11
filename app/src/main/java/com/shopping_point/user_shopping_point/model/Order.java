package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {

    @SerializedName("id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_description")
    private String productDesc;
    @SerializedName("order_number")
    private String orderNumber;
    @SerializedName("order_date")
    private String orderDate;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("image")
    private String productImage;
    @SerializedName("status")
    private String orderDateStatus;
    @SerializedName("name")
    private String userName;
    @SerializedName("address")
    private String shippingAddress;
    @SerializedName("city")
    private String shippingcity;
    @SerializedName("country")
    private String shippingcountry;
    @SerializedName("zip")
    private String shippingzip;
    @SerializedName("phone")
    private String shippingPhone;

    public Order(){}

    public String getProductDesc() {
        return productDesc;
    }

    public String getShippingcity() {
        return shippingcity;
    }

    public String getShippingcountry() {
        return shippingcountry;
    }

    public String getShippingzip() {
        return shippingzip;
    }

    public String getProductImage() {
        return productImage;
    }

    public int getProductId() {
        return productId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }


    public String getProductName() {
        return productName;
    }

    public String getOrderDateStatus() {
        return orderDateStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

}
