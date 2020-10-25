package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Update {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_no")
    @Expose
    private String phone_no;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;



    public Update(String email, String name, String phone_no, String gender, String dob) {
        this.name = name;
        this.email = email;
        this.phone_no=phone_no;
        this.gender = gender;
        this.dob=dob;


    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_no(){return phone_no;}

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }


}
