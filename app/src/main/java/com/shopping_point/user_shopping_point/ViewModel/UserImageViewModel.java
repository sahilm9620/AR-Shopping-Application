package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.model.Image;
import com.shopping_point.user_shopping_point.repository.UserImageRepository;

public class UserImageViewModel extends AndroidViewModel {

    private UserImageRepository userImageRepository;

    public UserImageViewModel(@NonNull Application application) {
        super(application);
        userImageRepository = new UserImageRepository(application);
    }

    public LiveData<Image> getUserImage(int userId) {
        return userImageRepository.getUserImage(userId);
    }
}
