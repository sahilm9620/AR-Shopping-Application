package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressList {



        @SerializedName("address")
        private List<Address> addressList;

    public List<Address> getAddressList() {
        return addressList;
    }
}
