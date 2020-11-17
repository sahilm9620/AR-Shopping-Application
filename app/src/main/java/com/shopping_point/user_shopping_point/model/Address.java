package com.shopping_point.user_shopping_point.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.google.gson.annotations.SerializedName;
import com.shopping_point.user_shopping_point.R;

public class Address implements Parcelable {

    @SerializedName("address_id")
    private String address_id;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("zip")
    private String zip;
    @SerializedName("phone")
    private String phone;

    private int userId;

    private Address mInfo;

    public Address( String name, String address, String city, String country, String zip, int userId,String phone) {

        this.name=name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.userId = userId;

    }

    public String getPhone() {
        return phone;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }


    public int getUserId() {
        return userId;
    }




    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(address_id);
        out.writeString(name);
        out.writeString(address);
        out.writeString(city);
        out.writeString(country);
        out.writeString(zip);
        out.writeString(phone);

        out.writeInt(userId);

        out.writeParcelable(mInfo, flags);
    }

    // Retrieve the values written into the `Parcel`.
    private Address(Parcel in) {

        address_id = in.readString();
        name=in.readString();

        address = in.readString();
        city = in.readString();
        country = in.readString();
        zip = in.readString();
        phone = in.readString();
        userId = in.readInt();

        mInfo = in.readParcelable(Address.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Create the Parcelable.Creator<Product> CREATOR` constant for our class;
    public static final Parcelable.Creator<Address> CREATOR
            = new Parcelable.Creator<Address>() {

        // This simply calls our new constructor and
        // passes along `Parcel`, and then returns the new object!
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };





}
