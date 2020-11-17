package com.shopping_point.user_shopping_point.net;

import com.shopping_point.user_shopping_point.model.Address;
import com.shopping_point.user_shopping_point.model.AddressList;
import com.shopping_point.user_shopping_point.model.Cart;
import com.shopping_point.user_shopping_point.model.CartApiResponse;
import com.shopping_point.user_shopping_point.model.Favorite;
import com.shopping_point.user_shopping_point.model.FavoriteApiResponse;
import com.shopping_point.user_shopping_point.model.History;
import com.shopping_point.user_shopping_point.model.HistoryApiResponse;
import com.shopping_point.user_shopping_point.model.Image;
import com.shopping_point.user_shopping_point.model.LoginApiResponse;
import com.shopping_point.user_shopping_point.model.NewsFeedResponse;
import com.shopping_point.user_shopping_point.model.OrderApiResponse;
import com.shopping_point.user_shopping_point.model.Ordering;
import com.shopping_point.user_shopping_point.model.Otp;
import com.shopping_point.user_shopping_point.model.ProductApiResponse;
import com.shopping_point.user_shopping_point.model.RegisterApiResponse;
import com.shopping_point.user_shopping_point.model.Review;
import com.shopping_point.user_shopping_point.model.ReviewApiResponse;

import com.shopping_point.user_shopping_point.model.Update;
import com.shopping_point.user_shopping_point.model.UpdateApiResponse;
import com.shopping_point.user_shopping_point.model.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @POST("users/register.php")
    Call<RegisterApiResponse> createUser(@Body User user);

    @GET("users/login.php")
    Call<LoginApiResponse> logInUser(@Query("email") String email, @Query("password") String password);

    @GET("users/deleteuser.php")
    Call<ResponseBody> deleteAccount(@Query("userId") int userId);


    @GET("users/info.php")
    Call<ResponseBody> updatePassword(@Query("user_password") String newPassword, @Query("user_email") String email);

    @GET("users/getImage.php")
    Call<Image> getUserImage(@Query("id") int userId);

    @GET("users/otp.php")
    Call<Otp> getOtp(@Query("email") String email);

    @GET("products/getproducts.php")
    Call<ProductApiResponse> getProducts(@Query("page") int page);

    @GET("products/getProductsByCategory.php")
    Call<ProductApiResponse> getProductsByCategory(@Query("category") String category, @Query("userId") int userId,@Query("page") int page);

    @GET("products/search.php")
    Call<ProductApiResponse> searchForProduct(@Query("keyword") String keyword, @Query("userId") int userId);

    @POST("favorites/add.php")
    Call<ResponseBody> addFavorite(@Body Favorite favorite);

    @GET("favorites/remove.php")
    Call<ResponseBody> removeFavorite(@Query("userId") int userId, @Query("productId") int productId);

    @GET("favorites/getFavorites.php")
    Call<FavoriteApiResponse> getFavorites(@Query("userId") int userId);

    @POST("carts/add.php")
    Call<ResponseBody> addToCart(@Body Cart cart);

    @GET("carts/remove.php")
    Call<ResponseBody> removeFromCart(@Query("userId") int userId, @Query("productId") int productId);

    @GET("carts/getProductsInCart.php")
    Call<CartApiResponse> getProductsInCart(@Query("userId") int userId);

    @POST("history/add.php")
    Call<ResponseBody> addToHistory(@Body History history);

    @DELETE("history/remove.php")
    Call<ResponseBody> removeAllFromHistory();

    @GET("history/getProductsInHistory.php")
    Call<HistoryApiResponse> getProductsInHistory(@Query("userId") int userId, @Query("page") int page);

    @POST("review/add.php")
    Call<ResponseBody> addReview(@Body Review review);

    @GET("review/getAllReviews.php")
    Call<ReviewApiResponse> getAllReviews(@Query("productId") int productId);

    @GET("posters/getPosters.php")
    Call<NewsFeedResponse> getPosters();

    @GET("orders/get.php")
    Call<OrderApiResponse> getOrders(@Query("userId") int userId);

    @POST("address/add.php")
    Call<ResponseBody> addShippingAddress(@Body Address address);

    @POST("orders/add.php")
    Call<ResponseBody> orderProduct(@Body Ordering ordering);

    @Multipart
    @POST("users/upload.php")
    Call<ResponseBody> uploadPhoto(@Part MultipartBody.Part userPhoto, @Part("id") RequestBody userId);

    @POST("users/update_profile.php")
    Call<UpdateApiResponse> updateProfile(@Body Update update);


    @GET("address/getAddress.php")
    Call<AddressList> getAddress(@Query("userId") int userId);

}
