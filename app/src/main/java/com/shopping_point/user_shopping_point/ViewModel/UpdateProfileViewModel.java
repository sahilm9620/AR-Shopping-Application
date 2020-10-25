package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.user_shopping_point.model.Update;
import com.shopping_point.user_shopping_point.model.UpdateApiResponse;
import com.shopping_point.user_shopping_point.repository.UpdateRepository;


public class UpdateProfileViewModel extends AndroidViewModel {

    private UpdateRepository updateRepository;

    public UpdateProfileViewModel(@NonNull Application application) {
        super(application);
        updateRepository = new UpdateRepository(application);
    }


    public LiveData<UpdateApiResponse> getUpdateResponseLiveData(Update update) {
        return updateRepository.getUpdateResponseData(update);
    }
}
