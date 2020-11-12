package com.shopping_point.user_shopping_point.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shopping_point.user_shopping_point.R;

import com.shopping_point.user_shopping_point.databinding.OrderListItemBinding;
import com.shopping_point.user_shopping_point.model.Order;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private Context mContext;
    private List<Order> orderList;
    private Order currentOrder;

    private OrderAdapter.OrderAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface OrderAdapterOnClickHandler {
        void onClick(Order order);
    }

    public OrderAdapter(Context mContext, List<Order> orderList, OrderAdapter.OrderAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.orderList = orderList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        OrderListItemBinding orderListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_item,parent,false);
        return new OrderViewHolder(orderListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        currentOrder = orderList.get(position);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentOrder.getProductPrice());
        holder.binding.orderName.setText(currentOrder.getProductName());
    holder.binding.orderStatus.setText(currentOrder.getOrderDateStatus());
        Glide.with(mContext)
                .load(currentOrder.getProductImage())
                .into(holder.binding.imgProductImage);


        holder.binding.orderPrice.setText(formattedPrice+ " ₹ ");

      holder.binding.Rating.setText(currentOrder.getProductRating() + " ★ ");



        holder.binding.orderDate.setText("Orderd on : " + currentOrder.getOrderDate());
    }

    @Override
    public int getItemCount() {
        if (orderList == null) {
            return 0;
        }
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        private final OrderListItemBinding binding;

        private OrderViewHolder(OrderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Get position of the product
            currentOrder = orderList.get(position);
            // Send product through click
            clickHandler.onClick(currentOrder);
        }
    }
}
