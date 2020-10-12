package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.shopping_point.user_shopping_point.net.RetrofitClient;
import com.shopping_point.user_shopping_point.view.AuthenticationActivity;
import com.shopping_point.user_shopping_point.view.LoginActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.ContextCompat.startActivity;

public class PasswordRepository {

    private static final String TAG = PasswordRepository.class.getSimpleName();
    private Application application;

    public PasswordRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> updatePassword(String newPassword, String email) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().updatePassword(newPassword, email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
             ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                    Toast.makeText(application, " Password Changed Successfully!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", " Failed" + t.getMessage());
            }
        });

        return mutableLiveData;
    }

}
