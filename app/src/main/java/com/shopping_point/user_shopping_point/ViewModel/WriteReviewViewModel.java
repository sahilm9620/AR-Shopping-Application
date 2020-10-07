package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.shopping_point.user_shopping_point.model.Review;
import com.shopping_point.user_shopping_point.repository.WriteReviewRepository;

import okhttp3.ResponseBody;

public class WriteReviewViewModel extends AndroidViewModel {

    private WriteReviewRepository writeReviewRepository;

    public WriteReviewViewModel(@NonNull Application application) {
        super(application);
        writeReviewRepository = new WriteReviewRepository(application);
    }

    public LiveData<ResponseBody> writeReview(Review review) {
        return writeReviewRepository.writeReview(review);
    }
}
