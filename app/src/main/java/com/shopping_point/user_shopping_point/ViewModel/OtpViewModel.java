package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.model.Otp;
import com.shopping_point.user_shopping_point.repository.OtpRepository;

public class OtpViewModel extends AndroidViewModel {

    private OtpRepository otpRepository;

    public OtpViewModel(@NonNull Application application) {
        super(application);
        otpRepository = new OtpRepository(application);
    }

    public LiveData<Otp> getOtpCode(String email) {
        return otpRepository.getOtpCode(email);
    }
}
