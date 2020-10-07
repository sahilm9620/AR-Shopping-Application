package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.repository.RemoveFavoriteRepository;
import com.shopping_point.user_shopping_point.utils.RequestCallback;

import okhttp3.ResponseBody;

public class RemoveFavoriteViewModel extends AndroidViewModel {

    private RemoveFavoriteRepository removeFavoriteRepository;

    public RemoveFavoriteViewModel(@NonNull Application application) {
        super(application);
        removeFavoriteRepository = new RemoveFavoriteRepository(application);
    }

    public LiveData<ResponseBody> removeFavorite(int userId, int productId, RequestCallback callback) {
        return removeFavoriteRepository.removeFavorite(userId, productId, callback);
    }
}
