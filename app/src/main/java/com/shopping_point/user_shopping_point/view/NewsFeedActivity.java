package com.shopping_point.user_shopping_point.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.NewsFeedViewModel;
import com.shopping_point.user_shopping_point.adapter.NewsFeedAdapter;
import com.shopping_point.user_shopping_point.databinding.ActivityNewsFeedBinding;

public class NewsFeedActivity extends AppCompatActivity {

    private static final String TAG = "NewsFeedActivity";
    private ActivityNewsFeedBinding binding;
    private NewsFeedViewModel newsFeedViewModel;
    private NewsFeedAdapter newsFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_feed);

        newsFeedViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);

        setUpRecyclerView();

        getPosters();
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.newsFeedList.setLayoutManager(layoutManager);
        binding.newsFeedList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.newsFeedList.addItemDecoration(dividerItemDecoration);
    }

    private void getPosters() {
        newsFeedViewModel.getPosters().observe(this, NewsFeedResponse -> {
            newsFeedAdapter = new NewsFeedAdapter(getApplicationContext(), NewsFeedResponse.getPosters());
            binding.newsFeedList.setAdapter(newsFeedAdapter);
            newsFeedAdapter.notifyDataSetChanged();
        });
    }

}
