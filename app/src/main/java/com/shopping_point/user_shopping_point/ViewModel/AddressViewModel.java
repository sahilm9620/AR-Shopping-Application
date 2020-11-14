package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.user_shopping_point.model.Address;
import com.shopping_point.user_shopping_point.model.AddressList;
import com.shopping_point.user_shopping_point.repository.AddressRepository;


public class AddressViewModel extends AndroidViewModel {

    private AddressRepository addressRepository;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        addressRepository = new AddressRepository(application);
    }

    public LiveData<AddressList> getAddress(int userId) {
        return addressRepository.getAddress(userId);
    }

}
