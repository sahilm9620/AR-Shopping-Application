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
import com.shopping_point.user_shopping_point.model.Address;
import com.shopping_point.user_shopping_point.model.Product;

import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.Validation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

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


        product= getIntent().getParcelableExtra(PRODUCT);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            addShippingAddress();
        }
    }

    private void addShippingAddress() {
        String name = binding.txtName.getText().toString().trim();
        String address = binding.address.getText().toString().trim();
        String city = binding.city.getText().toString().trim();
        String country = binding.country.getText().toString().trim();
        String zip = binding.zip.getText().toString().trim();
        String phone = binding.ccp.getSelectedCountryCodeWithPlus() + binding.phone.getText().toString().trim();
        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Intent intent = getIntent();
        int productId = intent.getIntExtra(PRODUCTID, 0);

        if (name.isEmpty()) {
            binding.txtName.setError("Name Required");
            binding.txtName.requestFocus();
            return;
        }

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

        String id =  generateAddress_id();
       // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        Address shipping = new Address(id,name,address, city, country, zip,userId,phone);

        shippingViewModel.addShippingAddress(shipping).observe(this, responseBody -> {

                Toast.makeText(ShippingAddressActivity.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();


            Intent orderProductIntent = new Intent(ShippingAddressActivity.this, AddressActivity.class);
           orderProductIntent.putExtra(PRODUCTID,productId);
            orderProductIntent.putExtra(PRODUCT, (product));

            startActivity(orderProductIntent);
        });
    }


    private String generateAddress_id() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
       String Address_id = "ADR" +  date+String.valueOf(randomNum);
        return Address_id;
    }
}
