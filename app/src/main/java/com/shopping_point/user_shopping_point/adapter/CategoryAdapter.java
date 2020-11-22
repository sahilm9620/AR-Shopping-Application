package com.shopping_point.user_shopping_point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.databinding.VerticalRecyclerviewBinding;
import com.shopping_point.user_shopping_point.model.Category;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<Category> categorylist;


    public CategoryAdapter(Context mContext, List<Category> categorylist) {
        this.mContext = mContext;
        this.categorylist = categorylist;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        VerticalRecyclerviewBinding activityAddCategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.vertical_recyclerview,parent,false);
        return new CategoryViewHolder(activityAddCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categorylist.get(position);

if(category!=null){
    String categoryName = category

            .getCategory_name();
    //Toast.makeText(mContext, ""+categoryName, Toast.LENGTH_SHORT).show();
    holder.binding.textViewMobiles.setText((CharSequence) categoryName);

}


    }

    @Override
    public int getItemCount() {
        if (categorylist == null) {
            return 0;
        }
        return categorylist.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final VerticalRecyclerviewBinding binding;

        private CategoryViewHolder(VerticalRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
