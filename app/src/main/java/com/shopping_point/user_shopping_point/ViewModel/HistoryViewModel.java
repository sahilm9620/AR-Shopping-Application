package com.shopping_point.user_shopping_point.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.shopping_point.user_shopping_point.model.Product;
import com.shopping_point.user_shopping_point.net.HistoryDataSource;
import com.shopping_point.user_shopping_point.net.HistoryDataSourceFactory;

import static com.shopping_point.user_shopping_point.net.HistoryDataSourceFactory.historyDataSource;

public class HistoryViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> historyPagedList;
    private LiveData<PageKeyedDataSource<Integer, Product>> liveDataSource;

    public void loadHistory(int userId) {
        HistoryDataSourceFactory historyDataSourceFactory = new HistoryDataSourceFactory(userId);

        liveDataSource = historyDataSourceFactory.getProductsInHistory();

        // Get PagedList configuration
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(HistoryDataSource.PAGE_SIZE).build();

        // Build the paged list
        historyPagedList = (new LivePagedListBuilder(historyDataSourceFactory, pagedListConfig)).build();
    }

    public void invalidate(){
        if(historyDataSource != null) historyDataSource.invalidate();
    }


}
