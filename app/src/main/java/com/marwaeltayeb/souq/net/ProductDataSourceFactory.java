package com.marwaeltayeb.souq.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;

public class ProductDataSourceFactory extends DataSource.Factory{

    // Creating the mutable live database
    private MutableLiveData<PageKeyedDataSource<Integer, Product>> productLiveDataSource = new MutableLiveData<>();

    public static ProductDataSource productDataSource;

    private String category;
    private int userId;

    public ProductDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        productDataSource = new ProductDataSource(category, userId);

        // Posting the Data source to get the values
        productLiveDataSource.postValue(productDataSource);

        // Returning the Data source
        return productDataSource;
    }


    // Getter for Product live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Product>> getProductLiveDataSource() {
        return productLiveDataSource;
    }
}