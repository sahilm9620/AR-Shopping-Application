package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.model.Address;

import com.shopping_point.user_shopping_point.repository.ShippingRepository;

import okhttp3.ResponseBody;

public class ShippingViewModel  extends AndroidViewModel {

    private ShippingRepository shippingRepository;

    public ShippingViewModel(@NonNull Application application) {
        super(application);
        shippingRepository = new ShippingRepository(application);
    }

    public LiveData<ResponseBody> addShippingAddress(Address address) {
        return shippingRepository.addShippingAddress(address);
    }
}
