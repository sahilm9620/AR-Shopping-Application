package com.shopping_point.user_shopping_point.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import android.content.Intent;
import android.content.res.Configuration;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.ProductViewModel;
import com.shopping_point.user_shopping_point.adapter.ProductAdapter;
import com.shopping_point.user_shopping_point.databinding.ActivityAllLaptopsBinding;
import com.shopping_point.user_shopping_point.model.Product;
import com.shopping_point.user_shopping_point.storage.LoginUtils;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;

public class AllLaptopsActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler {

    private ActivityAllLaptopsBinding binding;
    private ProductAdapter laptopAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_laptops);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.all_laptops));

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadLaptops("laptop",userID);

        setupRecyclerViews();

        getAllLaptops();
    }

    private void setupRecyclerViews() {
        // Laptops
        binding.allLaptopsRecyclerView.setHasFixedSize(true);
        binding.allLaptopsRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        laptopAdapter = new ProductAdapter(this, this);
    }

    public void getAllLaptops() {

        // Observe the productPagedList from ViewModel
        productViewModel.laptopPagedList.observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(@Nullable PagedList<Product> products) {
                laptopAdapter.submitList(products);
            }
        });

        binding.allLaptopsRecyclerView.setAdapter(laptopAdapter);
        laptopAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllLaptopsActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
