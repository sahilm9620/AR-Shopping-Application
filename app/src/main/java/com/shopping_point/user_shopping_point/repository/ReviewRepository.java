package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.shopping_point.user_shopping_point.model.ReviewApiResponse;
import com.shopping_point.user_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository {

    private static final String TAG = ReviewRepository.class.getSimpleName();
    private Application application;

    public ReviewRepository(Application application) {
        this.application = application;
    }

    public LiveData<ReviewApiResponse> getReviews(int productId) {
        final MutableLiveData<ReviewApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getAllReviews(productId).enqueue(new Callback<ReviewApiResponse>() {
            @Override
            public void onResponse(Call<ReviewApiResponse> call, Response<ReviewApiResponse> response) {
                Log.d("onResponse",response.code() + "");

                ReviewApiResponse responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ReviewApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}

