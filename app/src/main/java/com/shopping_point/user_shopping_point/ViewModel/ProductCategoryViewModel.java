package com.shopping_point.user_shopping_point.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.user_shopping_point.model.CategoryResponse;
import com.shopping_point.user_shopping_point.repository.CategoryRepository;


public class ProductCategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    public ProductCategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public LiveData<CategoryResponse> getCategory() {
        return categoryRepository.getCategory();
    }
}
