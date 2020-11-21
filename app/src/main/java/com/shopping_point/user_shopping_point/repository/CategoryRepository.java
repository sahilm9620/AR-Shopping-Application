package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shopping_point.user_shopping_point.model.CategoryResponse;
import com.shopping_point.user_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {

    private static final String TAG = com.shopping_point.user_shopping_point.repository.CategoryRepository.class.getSimpleName();
    private Application application;

    public CategoryRepository(Application application) {
        this.application = application;
    }

    public LiveData<CategoryResponse> getCategory() {
        final MutableLiveData<CategoryResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                Log.d("onResponse", "" + response.code());

                CategoryResponse responseBody = response.body();
              //  Toast.makeText(application, response.body() + "Category Repo ", Toast.LENGTH_SHORT).show();
                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
             //   Toast.makeText(application, "Category : FAILURE", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;
    }
}
