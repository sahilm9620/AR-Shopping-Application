package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.model.LoginApiResponse;
import com.marwaeltayeb.souq.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository(application);
    }

    public LiveData<LoginApiResponse> getLoginResponseLiveData(String email, String password) {
        return loginRepository.getLoginResponseData(email,password);
    }
}
