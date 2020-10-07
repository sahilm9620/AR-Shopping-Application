package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.model.NewsFeedResponse;
import com.shopping_point.user_shopping_point.repository.NewsFeedRepository;

public class NewsFeedViewModel extends AndroidViewModel {

    private NewsFeedRepository newsFeedRepository;

    public NewsFeedViewModel(@NonNull Application application) {
        super(application);
        newsFeedRepository = new NewsFeedRepository(application);
    }

    public LiveData<NewsFeedResponse> getPosters() {
        return newsFeedRepository.getPosters();
    }
}
