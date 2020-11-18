package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.user_shopping_point.model.Upload;
import com.shopping_point.user_shopping_point.model.UploadProfileApiResponse;
import com.shopping_point.user_shopping_point.net.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;


public class UploadProfileRepository {

    private static final String TAG = com.shopping_point.user_shopping_point.repository.UploadProfileRepository.class.getSimpleName();
    private Application application;

    public UploadProfileRepository(Application application) {
        this.application = application;
    }


    public LiveData<UploadProfileApiResponse> getUploadProfileResponseData(Upload upload) {
        final MutableLiveData<UploadProfileApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().upload_profile_image(upload).enqueue(new Callback<UploadProfileApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UploadProfileApiResponse> call, Response<UploadProfileApiResponse> response) {

                //Toast.makeText(application, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                UploadProfileApiResponse addBannerApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(addBannerApiResponse);
                    Log.d(TAG, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UploadProfileApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
