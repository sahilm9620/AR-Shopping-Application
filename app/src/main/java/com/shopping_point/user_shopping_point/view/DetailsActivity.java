package com.shopping_point.user_shopping_point.view;


import androidx.annotation.Nullable;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;



import com.bumptech.glide.Glide;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.ProductViewModel;
import com.shopping_point.user_shopping_point.ViewModel.ReviewViewModel;
import com.shopping_point.user_shopping_point.ViewModel.ToCartViewModel;
import com.shopping_point.user_shopping_point.adapter.ProductAdapter;
import com.shopping_point.user_shopping_point.adapter.ReviewAdapter;


import com.shopping_point.user_shopping_point.databinding.ActivityDetailsBinding;
import com.shopping_point.user_shopping_point.model.Cart;
import com.shopping_point.user_shopping_point.model.Product;
import com.shopping_point.user_shopping_point.model.Review;

import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.RequestCallback;

import java.text.DecimalFormat;
import java.util.List;



import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;

import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCTID;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT_ID;
import static com.shopping_point.user_shopping_point.utils.InternetUtils.isNetworkConnected;


public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.ProductAdapterOnClickHandler {

    private static final String TAG = "DetailsActivity";

    private ActivityDetailsBinding binding;
    private ReviewViewModel reviewViewModel;
    private ToCartViewModel toCartViewModel;
    private ProductViewModel productViewModel;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;

    private Product product;
    private ProductAdapter mobileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);



        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        toCartViewModel = ViewModelProviders.of(this).get(ToCartViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        product = getIntent().getParcelableExtra(PRODUCT);

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();
        productViewModel.loadMobiles(product.getProductCategory(), userID);


        setUpViews();


        binding.txtSeeAllReviews.setOnClickListener(this);

        binding.addToCart.setOnClickListener(this);
        binding.buy.setOnClickListener(this);

        getProductDetails();
        getMobiles();
        setUpRecycleView();

        getReviewsOfProduct();

        binding.imageOfProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, FullImageActivity.class);
                intent.putExtra(PRODUCT, (product));
                startActivity(intent);

            }
        });


    }


    private void setUpViews() {


        binding.listOfMobiles.setHasFixedSize(true);
        binding.listOfMobiles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.listOfMobiles.setItemAnimator(null);


        mobileAdapter = new ProductAdapter(this, this);


    }


    private void getMobiles() {
        if (isNetworkConnected(this)) {
            productViewModel.productPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    mobileAdapter.submitList(products);
                }
            });

            binding.listOfMobiles.setAdapter(mobileAdapter);
            mobileAdapter.notifyDataSetChanged();
        } else {


        }
    }

    private void setUpRecycleView() {
        binding.listOfReviews.setHasFixedSize(true);
        binding.listOfReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.listOfReviews.setItemAnimator(null);
    }

    private void getProductDetails() {
        // Receive the product object
        product = getIntent().getParcelableExtra(PRODUCT);

        Log.d(TAG, "isFavourite " + product.isFavourite() + " isInCart " + product.isInCart());

        // Set Custom ActionBar Layout
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_title_layout);
        ((TextView) findViewById(R.id.action_bar_title)).setText(product.getProductName());

        binding.ProductHighlights.setText("Highlights");
        binding.nameOfProduct.setText(product.getProductName());
        binding.DescofProduct.setText(product.getProductDesc());
        binding.ARbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailsActivity.this,ARActivity.class);
                startActivity(intent);
            }
        });


        binding.Rating.setText(product.getProductRating() + " ★ ");

        double rating = Double.parseDouble(product.getProductRating());
        if (rating <= 3 && rating >= 2) {
            binding.Rating.setBackgroundColor(Color.parseColor("#FFA22C"));
        } else if (rating < 2) {
            binding.Rating.setBackgroundColor(Color.parseColor("#FE0000"));

        }

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(product.getProductPrice());
        binding.priceOfProduct.setText(formattedPrice + " ₹ ");

        String imageUrl = product.getProductImage().replaceAll("\\\\", "/");
        Glide.with(this)
                .load(imageUrl)
                .into(binding.imageOfProduct);

    }




    private void getReviewsOfProduct() {
        reviewViewModel.getReviews(product.getProductId()).observe(this, reviewApiResponse -> {
            if (reviewApiResponse != null) {
                reviewList = reviewApiResponse.getReviewList();
                reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewList);
                binding.listOfReviews.setAdapter(reviewAdapter);
                //reviewAdapter.notifyOnInsertedItem();
                reviewAdapter.notifyDataSetChanged();
            }

            if(reviewList.size() == 0){
                binding.listOfReviews.setVisibility(View.GONE);
                binding.txtFirst.setVisibility(View.VISIBLE);
            }else {
                binding.listOfReviews.setVisibility(View.VISIBLE);
                binding.txtFirst.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtSeeAllReviews) {
            Intent allReviewIntent = new Intent(DetailsActivity.this, AllReviewsActivity.class);
            allReviewIntent.putExtra(PRODUCT_ID,product.getProductId());
            startActivity(allReviewIntent);
        } else  if(view.getId() == R.id.addToCart){
            insertToCart(() -> {
                product.setIsInCart(true);
            });
            Intent cartIntent = new Intent(DetailsActivity.this, CartActivity.class);
            startActivity(cartIntent);
        }else if(view.getId() == R.id.buy){
            Intent shippingIntent = new Intent(DetailsActivity.this, AddressActivity.class);
            shippingIntent.putExtra(PRODUCTID, product.getProductId());
            shippingIntent.putExtra(PRODUCT, (product));
            startActivity(shippingIntent);
        }
    }

    private void insertToCart(RequestCallback callback) {
        Cart cart = new Cart(LoginUtils.getInstance(this).getUserInfo().getId(), product.getProductId());
        toCartViewModel.addToCart(cart, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReviewsOfProduct();
    }


    @Override
    public void onClick(Product product) {


        Intent intent = getIntent();
        intent.putExtra(PRODUCT, (product));
        finish();
        startActivity(intent);
       // Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        // Pass an object of product class

        startActivity(intent);
    }
}
