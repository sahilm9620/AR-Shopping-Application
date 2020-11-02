package com.shopping_point.user_shopping_point.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.shopping_point.user_shopping_point.model.LoginApiResponse;
import com.shopping_point.user_shopping_point.model.User;

public class LoginUtils {

    private static final String SHARED_PREF_NAME = "shared_preference";

    private static LoginUtils mInstance;
    private Context mCtx;

    private LoginUtils(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized LoginUtils getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new LoginUtils(mCtx);
        }
        return mInstance;
    }

    public void saveUserInfo(LoginApiResponse response) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", response.getUser_id());
        editor.putString("name", response.getUser_name());
        editor.putString("email", response.getUser_email());
        editor.putString("password", response.getUser_password());
        editor.putString("user_contact_number",response.getUser_contact_number());
        editor.putString("gender",response.getUser_gender());
        editor.putString("dob",response.getUser_dob());
        editor.putString("token", response.getToken());
        editor.putBoolean("isActive", response.isActive());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public void saveUserInfo(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("name", user.getUser_name());
        editor.putString("email", user.getUser_email());
        editor.putString("password", user.getUser_password());
        editor.putString("user_contact_number",user.getUser_contact_number());
        editor.putString("gender",getGender());
        editor.putString("dob", getDob());
        editor.putBoolean("isActive", user.isActive());
        editor.apply();
    }




    public void saveUserInfo(String name,String email,String contact_no,String gender,String dob) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("user_name", name);
        editor.putString("user_email", email);
        editor.putString("user_contact_number", contact_no);
        editor.putString("gender",gender);
        editor.putString("dob",dob);
        editor.apply();
    }

    public String getName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name= sharedPreferences.getString("user_name",getUserInfo().getUser_name());
        return name;
    }

public String getDob()
{
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    String dob = sharedPreferences.getString("dob","00/00/0000");
    return dob;
}

    public String getGender()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String gender = sharedPreferences.getString("gender","undefined");
        return gender;
    }



    public User getUserInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", "USER NAME"),
                sharedPreferences.getString("email", "user_email@gmail.com"),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("user_contact_number",null),
                sharedPreferences.getBoolean("isActive", false)
        );
    }

    public void clearAll() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        editor.apply();
    }

}
