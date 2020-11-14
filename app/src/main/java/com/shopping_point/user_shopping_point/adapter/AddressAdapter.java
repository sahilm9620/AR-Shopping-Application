package com.shopping_point.user_shopping_point.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.AddressViewModel;
import com.shopping_point.user_shopping_point.databinding.AddressListBinding;
import com.shopping_point.user_shopping_point.model.Address;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.view.AddressActivity;

import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{

    private Context mContext;
    private List<Address> addressList;
private AddressActivity addressActivity;
private AddressViewModel addressViewModel;


    public AddressAdapter(Context mContext, List<Address> addressList, AddressActivity addressActivity) {
        this.mContext = mContext;
        this.addressList = addressList;
        this.addressActivity = addressActivity;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        addressViewModel = ViewModelProviders.of(addressActivity).get(AddressViewModel.class);

        AddressListBinding addressListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.address_list,parent,false);
        return new AddressViewHolder(addressListBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {


        Address address = addressList.get(position);
if(address!=null){

    holder.binding.txtName.setText(LoginUtils.getInstance(mContext).getUserInfo().getUser_name());
    holder.binding.phoneNo.setText(address.getPhone());
    holder.binding.txvAddress.setText(address.getAddress() + address.getCity());
    holder.binding.txvAddress2.setText(address.getCountry() + " Pin - " + address.getZip());
}





    }



    @Override
    public int getItemCount() {
        if (addressList == null) {
            return 0;
        }
        return addressList.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {

        private final AddressListBinding binding;

        private AddressViewHolder(AddressListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
