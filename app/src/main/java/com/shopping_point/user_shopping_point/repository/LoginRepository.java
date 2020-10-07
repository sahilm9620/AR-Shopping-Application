package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.shopping_point.user_shopping_point.model.LoginApiResponse;
import com.shopping_point.user_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();
    private Application application;

    public LoginRepository(Application application) {
        this.application = application;
    }

    public LiveData<LoginApiResponse> getLoginResponseData(String email, String password) {
        final MutableLiveData<LoginApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().logInUser(email, password).enqueue(new Callback<LoginApiResponse>() {
            @Override
            public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
                Log.d(TAG, "onResponse: Succeeded");

                LoginApiResponse loginResponse;
                if(response.code() == 200){
                    loginResponse = response.body();
                }else if (response.code() == 214){
                    // Add Custom message
                    loginResponse = new LoginApiResponse("Account does not exist");
                }else {
                    loginResponse = new LoginApiResponse("Incorrect Password");
                }
                mutableLiveData.setValue(loginResponse);
            }

            @Override
            public void onFailure(Call<LoginApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
