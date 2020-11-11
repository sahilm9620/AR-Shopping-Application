package com.shopping_point.user_shopping_point.view;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.shopping_point.user_shopping_point.R;

import com.shopping_point.user_shopping_point.databinding.ActivityStatusBinding;
import com.shopping_point.user_shopping_point.model.Order;

import java.text.DecimalFormat;

import static com.shopping_point.user_shopping_point.utils.Constant.ORDER;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCTID;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT_ID;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {
    Order order;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStatusBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_status);

        // Receive the order object
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(ORDER);

        productId = order.getProductId();
        binding.orderDate.setText(order.getOrderDate());
        binding.orderNumber.setText(order.getOrderNumber());
        binding.userName.setText("Name : " + order.getUserName());
        binding.userAddress.setText("Address : " + order.getShippingAddress());
        binding.userPhone.setText("Contact No : "+ order.getShippingPhone());
        binding.txtProductName.setText(order.getProductName());

        Glide.with(this)
                .load(order.getProductImage())
                .into(binding.imgProductImage);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(order.getProductPrice());
        binding.txtProductPrice.setText(formattedPrice + " ₹ ");
        binding.writeReview.setOnClickListener(this);

        String status = getString(R.string.item, order.getOrderDateStatus());
        binding.orderStatus.setText(status);

        binding.reOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reOrder) {
            Intent reOrderIntent = new Intent(this, OrderProductActivity.class);
            reOrderIntent.putExtra(PRODUCTID, productId);
            startActivity(reOrderIntent);
        }if (view.getId() == R.id.writeReview) {
            Intent allReviewIntent = new Intent(StatusActivity.this, WriteReviewActivity.class);
            allReviewIntent.putExtra(PRODUCT_ID,order.getProductId());
            startActivity(allReviewIntent);
        }
    }
}
