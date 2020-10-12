package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.shopping_point.user_shopping_point.net.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                Log.d("onResponse", "password repo" + response.code() + response.message());
                Toast.makeText(application, "SUCCESS " + response, Toast.LENGTH_SHORT).show();
                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                    Toast.makeText(application, " SUCCESS PASSWORD", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", " FAILURE PASSWORD " + t.getMessage());
            }
        });

        return mutableLiveData;
    }

}
