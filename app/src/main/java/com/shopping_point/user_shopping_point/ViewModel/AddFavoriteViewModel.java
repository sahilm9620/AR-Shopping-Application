package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import com.shopping_point.user_shopping_point.model.Favorite;
import com.shopping_point.user_shopping_point.repository.AddFavoriteRepository;
import com.shopping_point.user_shopping_point.utils.RequestCallback;

import okhttp3.ResponseBody;

public class AddFavoriteViewModel extends AndroidViewModel {

    private AddFavoriteRepository addFavoriteRepository;

    public AddFavoriteViewModel(@NonNull Application application) {
        super(application);
        addFavoriteRepository = new AddFavoriteRepository(application);
    }

    public LiveData<ResponseBody> addFavorite(Favorite favorite, RequestCallback callback) {
        return addFavoriteRepository.addFavorite(favorite,callback);
    }
}
