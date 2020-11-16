package com.shopping_point.user_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.databinding.ActivityDetailsBinding;
import com.shopping_point.user_shopping_point.databinding.ActivityFullImageBinding;
import com.shopping_point.user_shopping_point.model.Product;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;

public class FullImageActivity extends AppCompatActivity {
    private ActivityFullImageBinding binding;
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        loadLocale(this);



        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_image);


        product = getIntent().getParcelableExtra(PRODUCT);

        String imageUrl = product.getProductImage().replaceAll("\\\\", "/");
        Glide.with(this)
                .load(imageUrl)
                .into(binding.imageOfProduct);

binding.canceImg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});




    }
}