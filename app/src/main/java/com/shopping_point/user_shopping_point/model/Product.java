package com.shopping_point.user_shopping_point.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_desc")
    private String productDesc;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("quantity")
    private int productQuantity;
    @SerializedName("supplier")
    private String productSupplier;
    @SerializedName("category")
    private String productCategory;
    @SerializedName("image")
    private String productImage;
    @SerializedName("rating")
    private String productRating;
    @SerializedName("isFavourite")
    private int isFavourite;
    @SerializedName("isInCart")
    private int isInCart;
    @SerializedName("modelName")
    private String modelName;
    // Include child Parcelable objects
    private Product mInfo;




    public Product(String productName, double productPrice, int productQuantity, String productSupplier, String productCategory,String productRating , String modelName) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
        this.productCategory = productCategory;
        this.productRating = productRating;
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public Product() {

    }

    public String getProductRating() {
        return productRating;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductImage() {
        return productImage;
    }


    public int isFavourite() {
        return isFavourite;
    }

    public int isInCart() {
        return isInCart;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite ? 1 : 0;
    }

    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart ? 1 : 0;
    }

    // Write the values to be saved to the `Parcel`.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(productId);
        out.writeString(productName);
        out.writeString(productDesc);
        out.writeDouble(productPrice);
        out.writeInt(productQuantity);
        out.writeString(productSupplier);
        out.writeString(productCategory);
        out.writeString(productImage);
        out.writeString(productRating);
        out.writeInt(isFavourite);
        out.writeInt(isInCart);
        out.writeString(modelName);
        out.writeParcelable(mInfo, flags);
    }

    // Retrieve the values written into the `Parcel`.
    private Product(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        productDesc=in.readString();
        productPrice = in.readDouble();
        productQuantity = in.readInt();
        productSupplier = in.readString();
        productCategory = in.readString();
        productImage = in.readString();
        productRating = in.readString();
        isFavourite = in.readInt();
        isInCart = in.readInt();
        modelName = in.readString();
        mInfo = in.readParcelable(Product.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Create the Parcelable.Creator<Product> CREATOR` constant for our class;
    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {

        // This simply calls our new constructor and
        // passes along `Parcel`, and then returns the new object!
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
