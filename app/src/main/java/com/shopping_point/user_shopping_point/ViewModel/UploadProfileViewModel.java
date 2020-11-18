package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.user_shopping_point.model.Upload;
import com.shopping_point.user_shopping_point.model.UploadProfileApiResponse;
import com.shopping_point.user_shopping_point.repository.UploadProfileRepository;


public class UploadProfileViewModel extends AndroidViewModel {

    private UploadProfileRepository uploadProfileRepository;

    public UploadProfileViewModel(@NonNull Application application) {
        super(application);
        uploadProfileRepository = new UploadProfileRepository(application);
    }


    public LiveData<UploadProfileApiResponse> getAddBannerResponseLiveData(Upload upload) {
        return uploadProfileRepository.getUploadProfileResponseData(upload);
    }
}
