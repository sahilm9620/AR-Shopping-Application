package com.shopping_point.user_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.AddressViewModel;
import com.shopping_point.user_shopping_point.ViewModel.ReviewViewModel;
import com.shopping_point.user_shopping_point.adapter.AddressAdapter;
import com.shopping_point.user_shopping_point.databinding.ActivityAddressBinding;
import com.shopping_point.user_shopping_point.model.AddressList;
import com.shopping_point.user_shopping_point.storage.LoginUtils;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;

public class AddressActivity extends AppCompatActivity {
 private  ActivityAddressBinding binding;
    private  AddressViewModel addressViewModel;
    private AddressAdapter addressAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);

binding.AddAddress.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent newaddress = new Intent(AddressActivity.this, ShippingAddressActivity.class);
        startActivity(newaddress);
    }
});
        setUpRecyclerView();

        getVendors();



    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        binding.included.addressRecyclerView.setLayoutManager(layoutManager);
        binding.included.addressRecyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.included.addressRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void getVendors() {
        addressViewModel.getAddress(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, address -> {
            addressAdapter = new AddressAdapter(getApplicationContext(),address.getAddressList(),this);
            binding.included.addressRecyclerView.setAdapter(addressAdapter);
            addressAdapter.notifyDataSetChanged();
        });
    }


}