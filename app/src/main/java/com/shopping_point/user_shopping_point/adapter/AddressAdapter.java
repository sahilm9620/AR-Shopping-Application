package com.shopping_point.user_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.AddressViewModel;
import com.shopping_point.user_shopping_point.databinding.AddressListBinding;
import com.shopping_point.user_shopping_point.model.Address;
import com.shopping_point.user_shopping_point.view.AddressActivity;

import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{

    private Context mContext;
    private List<Address> addressList;
private AddressActivity addressActivity;
private AddressViewModel addressViewModel;
    public static Address address;

    private AddressAdapter.AddressAdapterOnClickHandler clickHandler;

    public interface AddressAdapterOnClickHandler {
        void onClick(Address address);
    }


    public AddressAdapter(Context mContext, List<Address> addressList, AddressActivity addressActivity, AddressAdapter.AddressAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
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

    holder.binding.txtName.setText(address.getName());
    holder.binding.phoneNo.setText(address.getPhone());
    holder.binding.txvAddress.setText(address.getAddress() + ", " + address.getCity() + ", ");
    holder.binding.txvAddress2.setText(address.getCountry() + " Pin - " + address.getZip());
}

holder.binding.radioButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, address.getAddress_id(), Toast.LENGTH_SHORT).show();
        holder.binding.button.setVisibility(View.VISIBLE);


        clickHandler.onClick(address);


    }
});




    }



    @Override
    public int getItemCount() {
        if (addressList == null) {
            return 0;
        }
        return addressList.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final AddressListBinding binding;

        private AddressViewHolder(AddressListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this::onClick);

            binding.addressList.setOnClickListener(this::onClick);
            binding.radioButton.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            // Get position of the product
            switch (v.getId()) {
                case R.id.address_list:
                    // Send product through click
                   // clickHandler.onClick(address);

                    break;
                case R.id.radioButton:

                    break;

            }

        }
    }



}
