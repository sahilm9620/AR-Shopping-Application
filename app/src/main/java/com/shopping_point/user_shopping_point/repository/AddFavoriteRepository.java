package com.shopping_point.user_shopping_point.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.shopping_point.user_shopping_point.model.Favorite;
import com.shopping_point.user_shopping_point.net.RetrofitClient;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.RequestCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFavoriteRepository {

    private static final String TAG = AddFavoriteRepository.class.getSimpleName();
    private Application application;

    public AddFavoriteRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> addFavorite(Favorite favorite, RequestCallback callback) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().addFavorite(favorite).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("onResponse", "" + response.code());
                LoginUtils.getInstance(application).getUserInfo().getId();
                ResponseBody responseBody = response.body();

                if(response.code() == 200){
                    callback.onCallBack();
                }

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", "" + t.getMessage());
            }
        });
        return mutableLiveData;
    }

}
