package com.shopping_point.user_shopping_point.net;

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
import com.shopping_point.user_shopping_point.model.Shipping;
import com.shopping_point.user_shopping_point.model.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("users/register.php")
    Call<RegisterApiResponse> createUser(@Body User user);

    @GET("users/login.php")
    Call<LoginApiResponse> logInUser(@Query("email") String email, @Query("password") String password);

    @GET("users/deleteuser.php")
    Call<ResponseBody> deleteAccount(@Query("userId") int userId);

    @Multipart
    @PUT("users/upload")
    Call<ResponseBody> uploadPhoto(@Part MultipartBody.Part userPhoto, @Part("id") RequestBody userId);

    @GET("users/info.php")
    Call<ResponseBody> updatePassword(@Query("password") String newPassword, @Query("email") String email);

    @Multipart
    @POST("products/insert")
    Call<ResponseBody> insertProduct(@PartMap Map<String, RequestBody> productInfo, @Part MultipartBody.Part image);

    @GET("users/getImage.php")
    Call<Image> getUserImage(@Query("id") int userId);

    @GET("users/otp.php")
    Call<Otp> getOtp(@Query("email") String email);

    @GET("products/getproducts.php")
    Call<ProductApiResponse> getProducts(@Query("page") int page);

    @GET("products/getProductsByCategory.php")
    Call<ProductApiResponse> getProductsByCategory(@Query("category") String category, @Query("userId") int userId,@Query("page") int page);

    @GET("products/search")
    Call<ProductApiResponse> searchForProduct(@Query("q") String keyword, @Query("userId") int userId);

    @POST("favorites/add")
    Call<ResponseBody> addFavorite(@Body Favorite favorite);

    @DELETE("favorites/remove")
    Call<ResponseBody> removeFavorite(@Query("userId") int userId, @Query("productId") int productId);

    @GET("favorites")
    Call<FavoriteApiResponse> getFavorites(@Query("userId") int userId);

    @POST("carts/add")
    Call<ResponseBody> addToCart(@Body Cart cart);

    @DELETE("carts/remove")
    Call<ResponseBody> removeFromCart(@Query("userId") int userId, @Query("productId") int productId);

    @GET("carts")
    Call<CartApiResponse> getProductsInCart(@Query("userId") int userId);

    @POST("history/add.php")
    Call<ResponseBody> addToHistory(@Body History history);

    @DELETE("history/remove.php")
    Call<ResponseBody> removeAllFromHistory();

    @GET("history/getProductsInHistory.php")
    Call<HistoryApiResponse> getProductsInHistory(@Query("userId") int userId, @Query("page") int page);

    @POST("review/add")
    Call<ResponseBody> addReview(@Body Review review);

    @GET("review")
    Call<ReviewApiResponse> getAllReviews(@Query("productId") int productId);

    @GET("posters")
    Call<NewsFeedResponse> getPosters();

    @GET("orders/get")
    Call<OrderApiResponse> getOrders(@Query("userId") int userId);

    @POST("address/add")
    Call<ResponseBody> addShippingAddress(@Body Shipping shipping);

    @POST("orders/add")
    Call<ResponseBody> orderProduct(@Body Ordering ordering);
}
