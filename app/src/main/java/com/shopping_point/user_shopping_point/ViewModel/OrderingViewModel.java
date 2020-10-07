package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.model.Ordering;
import com.shopping_point.user_shopping_point.repository.OrderingRepository;

import okhttp3.ResponseBody;

public class OrderingViewModel extends AndroidViewModel {

    private OrderingRepository orderingRepository;

    public OrderingViewModel(@NonNull Application application) {
        super(application);
        orderingRepository = new OrderingRepository(application);
    }

    public LiveData<ResponseBody> orderProduct(Ordering ordering) {
        return orderingRepository.orderProduct(ordering);
    }

}
