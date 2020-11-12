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

public class DeleteUserRepository{

    private static final String TAG = DeleteUserRepository.class.getSimpleName();
    private Application application;

    public DeleteUserRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> deleteUser(int userId) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().deleteAccount(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse Delete User: Succeeded");
                Toast.makeText(application, "ACCOUNT Deleted successfully", Toast.LENGTH_SHORT).show();
                ResponseBody responseBody = response.body();
                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure Delete User : " + t.getMessage());
            }
        });

        return mutableLiveData;
    }

}
