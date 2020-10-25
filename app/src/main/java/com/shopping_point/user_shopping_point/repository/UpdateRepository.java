package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shopping_point.user_shopping_point.model.Update;
import com.shopping_point.user_shopping_point.model.UpdateApiResponse;
import com.shopping_point.user_shopping_point.net.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;


public class UpdateRepository {

    private static final String TAG = com.shopping_point.user_shopping_point.repository.UpdateRepository.class.getSimpleName();
    private Application application;

    public UpdateRepository(Application application) {
        this.application = application;
    }

    public LiveData<UpdateApiResponse> getUpdateResponseData(Update update) {
        final MutableLiveData<UpdateApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().updateProfile(update).enqueue(new Callback<UpdateApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UpdateApiResponse> call, Response<UpdateApiResponse> response) {

                // Toast.makeText(application, response.message(), Toast.LENGTH_SHORT).show();

               // Log.d(TAG, "onResponse: Succeeded" + response.body().getMessage());

                UpdateApiResponse updateApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(updateApiResponse);
                    Log.d(TAG, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UpdateApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
