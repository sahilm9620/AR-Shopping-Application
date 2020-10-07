package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.repository.UploadPhotoRepository;

import okhttp3.ResponseBody;

public class UploadPhotoViewModel extends AndroidViewModel {

    private UploadPhotoRepository uploadPhotoRepository;

    public UploadPhotoViewModel(@NonNull Application application) {
        super(application);
        uploadPhotoRepository = new UploadPhotoRepository(application);
    }

    public LiveData<ResponseBody> uploadPhoto(String pathname) {
        return uploadPhotoRepository.uploadPhoto(pathname);
    }
}
