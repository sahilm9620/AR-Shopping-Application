package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.repository.FromCartRepository;
import com.shopping_point.user_shopping_point.utils.RequestCallback;

import okhttp3.ResponseBody;

public class FromCartViewModel extends AndroidViewModel {

    private FromCartRepository fromCartRepository;

    public FromCartViewModel(@NonNull Application application) {
        super(application);
        fromCartRepository = new FromCartRepository(application);
    }

    public LiveData<ResponseBody> removeFromCart(int userId, int productId, RequestCallback callback) {
        return fromCartRepository.removeFromCart(userId, productId, callback);
    }
}
