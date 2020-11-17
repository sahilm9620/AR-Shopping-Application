package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.shopping_point.user_shopping_point.model.AddressList;
import com.shopping_point.user_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {

    private static final String TAG = AddressRepository.class.getSimpleName();
    private Application application;

    public AddressRepository(Application application) {
        this.application = application;
    }

    public LiveData<AddressList> getAddress(int userId) {
        final MutableLiveData<AddressList> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getAddress(userId).enqueue(new Callback<AddressList>() {
            @Override
            public void onResponse(Call<AddressList> call, Response<AddressList> response) {
                Log.d("onResponse", "" + response.code());

                AddressList responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<AddressList> call, Throwable t) {

            }


        });


        return mutableLiveData;
    }


}