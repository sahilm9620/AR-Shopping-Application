package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.model.Cart;
import com.shopping_point.user_shopping_point.repository.ToCartRepository;
import com.shopping_point.user_shopping_point.utils.RequestCallback;

import okhttp3.ResponseBody;

public class ToCartViewModel extends AndroidViewModel {

    private ToCartRepository toCartRepository;

    public ToCartViewModel(@NonNull Application application) {
        super(application);
        toCartRepository = new ToCartRepository(application);
    }

    public LiveData<ResponseBody> addToCart(Cart cart, RequestCallback callback) {
        return toCartRepository.addToCart(cart, callback);
    }
}
