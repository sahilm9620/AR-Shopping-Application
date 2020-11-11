package com.shopping_point.user_shopping_point.view;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.ShippingViewModel;
import com.shopping_point.user_shopping_point.databinding.ActivityShippingAddressBinding;
import com.shopping_point.user_shopping_point.model.Product;
import com.shopping_point.user_shopping_point.model.Shipping;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.Validation;

import java.io.IOException;

import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCTID;
import static com.shopping_point.user_shopping_point.utils.Constant.SHIPPING;

public class ShippingAddressActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ShippingAddressActivity";
    private ActivityShippingAddressBinding binding;
    private Product product;
    private ShippingViewModel shippingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_address);

        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);

        binding.proceed.setOnClickListener(this);

        binding.txtName.setText(LoginUtils.getInstance(this).getUserInfo().getUser_name());
        product= getIntent().getParcelableExtra(PRODUCT);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            addShippingAddress();
        }
    }

    private void addShippingAddress() {

        String address = binding.address.getText().toString().trim();
        String city = binding.city.getText().toString().trim();
        String country = binding.country.getText().toString().trim();
        String zip = binding.zip.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();
        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Intent intent = getIntent();
        int productId = intent.getIntExtra(PRODUCTID, 0);

        if (address.isEmpty()) {
            binding.address.setError(getString(R.string.name_required));
            binding.address.requestFocus();
            return;
        }

        if (city.isEmpty()) {
            binding.city.setError(getString(R.string.city_required));
            binding.city.requestFocus();
            return;
        }
        if (country.isEmpty()) {
            binding.country.setError(getString(R.string.country_required));
            binding.country.requestFocus();
            return;
        }
        if (zip.isEmpty()) {
            binding.zip.setError(getString(R.string.zip_required));
            binding.zip.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            binding.phone.setError(getString(R.string.phone_required));
            binding.phone.requestFocus();
            return;
        }


        Shipping shipping = new Shipping(address, city, country, zip, phone,userId, productId);

        shippingViewModel.addShippingAddress(shipping).observe(this, responseBody -> {
            try {
                Toast.makeText(ShippingAddressActivity.this, responseBody.string()+"", Toast.LENGTH_SHORT).show();
               // Toast.makeText(this, "SUCESSSSSSSSSSSSSSSSSSSSSSSS", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                //Toast.makeText(this, "ERRORRRRRRRRRRRRRRRRRRRRRRRR", Toast.LENGTH_SHORT).show();
            }
            Intent orderProductIntent = new Intent(ShippingAddressActivity.this, OrderProductActivity.class);
            orderProductIntent.putExtra(PRODUCTID,productId);
            orderProductIntent.putExtra(PRODUCT, (product));

            startActivity(orderProductIntent);
        });
    }
}
