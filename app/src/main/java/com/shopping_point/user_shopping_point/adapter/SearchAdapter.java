package com.shopping_point.user_shopping_point.adapter;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.AddFavoriteViewModel;
import com.shopping_point.user_shopping_point.ViewModel.FromCartViewModel;
import com.shopping_point.user_shopping_point.ViewModel.RemoveFavoriteViewModel;
import com.shopping_point.user_shopping_point.ViewModel.ToCartViewModel;
import com.shopping_point.user_shopping_point.ViewModel.ToHistoryViewModel;
import com.shopping_point.user_shopping_point.databinding.SearchListItemBinding;
import com.shopping_point.user_shopping_point.model.Cart;
import com.shopping_point.user_shopping_point.model.Favorite;
import com.shopping_point.user_shopping_point.model.History;
import com.shopping_point.user_shopping_point.model.Product;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.RequestCallback;

import java.text.DecimalFormat;
import java.util.List;

import static com.shopping_point.user_shopping_point.utils.Constant.LOCALHOST;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private Context mContext;
    // Declare an arrayList for products
    private List<Product> productList;

    private Product currentProduct;
    private AddFavoriteViewModel addFavoriteViewModel;
    private RemoveFavoriteViewModel removeFavoriteViewModel;
    private ToCartViewModel toCartViewModel;
    private FromCartViewModel fromCartViewModel;
    private ToHistoryViewModel toHistoryViewModel;

    // Create a final private SearchAdapterOnClickHandler called mClickHandler
    private SearchAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface SearchAdapterOnClickHandler {
        void onClick(Product product);
    }

    public SearchAdapter(Context mContext,List<Product> productList,SearchAdapterOnClickHandler clickHandler, FragmentActivity activity) {
        this.mContext = mContext;
        this.productList = productList;
        this.clickHandler = clickHandler;
        addFavoriteViewModel = ViewModelProviders.of(activity).get(AddFavoriteViewModel.class);
        removeFavoriteViewModel = ViewModelProviders.of(activity).get(RemoveFavoriteViewModel.class);
        toCartViewModel = ViewModelProviders.of(activity).get(ToCartViewModel.class);
        fromCartViewModel = ViewModelProviders.of(activity).get(FromCartViewModel.class);
        toHistoryViewModel = ViewModelProviders.of(activity).get(ToHistoryViewModel.class);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        SearchListItemBinding searchListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.search_list_item,parent,false);
        return new SearchViewHolder(searchListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        currentProduct = productList.get(position);
        holder.binding.txtProductName.setText(currentProduct.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentProduct.getProductPrice()) ;
        holder.binding.txtProductPrice.setText(formattedPrice + " ₹ ");

        holder.binding.DescofProduct.setText(currentProduct.getProductDesc());
        // Load the Product image into ImageView
        String imageUrl =  currentProduct.getProductImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(imageUrl)
                .into(holder.binding.imgProductImage);



        holder.binding.Rating.setText(currentProduct.getProductRating() + " ★ ");

        double rating = Double.parseDouble(currentProduct.getProductRating());
        if(rating<=3 && rating >=2)
        {
            holder.binding.Rating.setBackgroundColor(Color.parseColor("#FFA22C"));
        }else if(rating<2)
        {
            holder.binding.Rating.setBackgroundColor(Color.parseColor("#FE0000"));

        }
        // If product is inserted
        if (currentProduct.isFavourite()==1){
            holder.binding.imgFavourite.setImageResource(R.drawable.ic_favorite_pink);
        }

        // If product is added to cart
        if (currentProduct.isInCart()==1) {
            holder.binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
        }
    }

    @Override
    public int getItemCount() {
        if (productList == null) {
            return 0;
        }
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void clear() {
        int size = productList.size();
        productList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        private final SearchListItemBinding binding;

        private SearchViewHolder(SearchListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
            binding.imgFavourite.setOnClickListener(this);
            binding.imgCart.setOnClickListener(this);
            binding.addToCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Get position of the product
            currentProduct = productList.get(position);

            switch (v.getId()) {
                case R.id.card_view:
                    // Send product through click
                    clickHandler.onClick(currentProduct);
                    insertProductToHistory();
                    break;
                case R.id.imgFavourite:
                    toggleFavourite();
                    break;
                case R.id.imgCart:
                    toggleProductsInCart();
                    break;
                case R.id.addToCart:
                    addToCart();
                    break;
            }
        }


        private void toggleFavourite() {
            // If favorite is not bookmarked
            if (currentProduct.isFavourite() != 1) {
                binding.imgFavourite.setImageResource(R.drawable.ic_favorite_pink);
                insertFavoriteProduct(() -> {
                    currentProduct.setIsFavourite(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Bookmark Added");
            } else {
                binding.imgFavourite.setImageResource(R.drawable.ic_favorite_border);
                deleteFavoriteProduct(() -> {
                    currentProduct.setIsFavourite(false);
                    notifyDataSetChanged();
                });
                showSnackBar("Bookmark Removed");
            }
        }

        private void toggleProductsInCart() {
            // If Product is not added to cart
            if (currentProduct.isInCart() != 1) {
                binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
                insertToCart(() -> {
                    currentProduct.setIsInCart(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Added To Cart");
            } else {
                binding.imgCart.setImageResource(R.drawable.ic_add_shopping_cart);
                deleteFromCart(() -> {
                    currentProduct.setIsInCart(false);
                    notifyDataSetChanged();
                });
                showSnackBar("Removed From Cart");
            }
        }

        private void addToCart() {
            // If Product is not added to cart
            if (currentProduct.isInCart() != 1) {
                binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
                insertToCart(() -> {
                    currentProduct.setIsInCart(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Added To Cart");
            }
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }

        private void insertFavoriteProduct(RequestCallback callback) {
            Favorite favorite = new Favorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
            addFavoriteViewModel.addFavorite(favorite,callback);
        }

        private void deleteFavoriteProduct(RequestCallback callback) {
            removeFavoriteViewModel.removeFavorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId(),callback);
        }

        private void insertToCart(RequestCallback callback) {
            Cart cart = new Cart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
            toCartViewModel.addToCart(cart, callback);
        }

        private void deleteFromCart(RequestCallback callback) {
            fromCartViewModel.removeFromCart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId(),callback);
        }

        private void insertProductToHistory() {
            History history = new History(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
            toHistoryViewModel.addToHistory(history);
        }
    }
}
