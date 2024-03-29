package com.shopping_point.user_shopping_point.view;

import android.Manifest;
import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.FromHistoryViewModel;
import com.shopping_point.user_shopping_point.ViewModel.HistoryViewModel;
import com.shopping_point.user_shopping_point.ViewModel.NewsFeedViewModel;
import com.shopping_point.user_shopping_point.ViewModel.ProductViewModel;
import com.shopping_point.user_shopping_point.ViewModel.UploadProfileViewModel;
import com.shopping_point.user_shopping_point.ViewModel.UserImageViewModel;
import com.shopping_point.user_shopping_point.adapter.ProductAdapter;
import com.shopping_point.user_shopping_point.databinding.ActivityProductBinding;
import com.shopping_point.user_shopping_point.model.NewsFeed;
import com.shopping_point.user_shopping_point.model.Product;
import com.shopping_point.user_shopping_point.model.Upload;
import com.shopping_point.user_shopping_point.receiver.NetworkChangeReceiver;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.OnNetworkListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.user_shopping_point.utils.Constant.CAMERA_PERMISSION_CODE;
import static com.shopping_point.user_shopping_point.utils.Constant.CAMERA_REQUEST;
import static com.shopping_point.user_shopping_point.utils.Constant.CATEGORY;
import static com.shopping_point.user_shopping_point.utils.Constant.GALLERY_REQUEST;
import static com.shopping_point.user_shopping_point.utils.Constant.PRODUCT;
import static com.shopping_point.user_shopping_point.utils.Constant.READ_EXTERNAL_STORAGE_CODE;
import static com.shopping_point.user_shopping_point.utils.ImageUtils.getImageUri;
import static com.shopping_point.user_shopping_point.utils.ImageUtils.getRealPathFromURI;
import static com.shopping_point.user_shopping_point.utils.InternetUtils.isNetworkConnected;
import static com.shopping_point.user_shopping_point.view.AccountActivity.historyIsDeleted;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkListener, ProductAdapter.ProductAdapterOnClickHandler,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ProductActivity";
   private ActivityProductBinding binding;
    private ProductAdapter mobileAdapter;
    private ProductAdapter laptopAdapter;
    private ProductAdapter historyAdapter;
    private ProductViewModel productViewModel;
    private FromHistoryViewModel fromHistoryViewModel;
    private HistoryViewModel historyViewModel;
    private UploadProfileViewModel uploadProfileViewModel;
    private UserImageViewModel userImageViewModel;
    private Snackbar snack;
    private CircleImageView circleImageView,circleImageView1;
    private Uri selectedImage;
    private Bitmap bitmap;
    String encode_image;
    private NewsFeedViewModel newsFeedViewModel;
    private NetworkChangeReceiver mNetworkReceiver;
    public  ArrayList<String> poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
            poster=new ArrayList<>();
        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        newsFeedViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);
        fromHistoryViewModel = ViewModelProviders.of(this).get(FromHistoryViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadMobiles("mobile", userID);
        productViewModel.loadLaptops("laptop",userID);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.loadHistory(userID);
        uploadProfileViewModel = ViewModelProviders.of(this).get(UploadProfileViewModel.class);
        userImageViewModel = ViewModelProviders.of(this).get(UserImageViewModel.class);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        binding.included.content.txtSeeAllMobiles.setOnClickListener(this);
        binding.included.content.txtSeeAllLaptops.setOnClickListener(this);
        binding.included.content.txtCash.setOnClickListener(this);
        binding.included.content.txtReturn.setOnClickListener(this);
        binding.included.txtSearch.setOnClickListener(this);










        setUpViews();

        getMobiles();
        getLaptops();
        getHistory();
        getUserImage();
        getPosters();
        flipImages(poster);

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);
    }


    private void getPosters() {
        newsFeedViewModel.getPosters().observe(this, NewsFeedResponse -> {

            List<NewsFeed> newsFeeds = NewsFeedResponse.getPosters();
            for(int i=0;i<newsFeeds.size();i++)
            {
                NewsFeed currentNewsFeed = newsFeeds.get(i);

                String posterUrl =  currentNewsFeed.getImage().replaceAll("\\\\", "/");
            //   poster.add(posterUrl);


                ImageView imageView = new ImageView(this);

                Glide.with(this)
                        .load(posterUrl)
                        .into(imageView);


                binding.included.content.imageSlider.addView(imageView);
            }




        });
    }

    private void setUpViews() {
        Toolbar toolbar = binding.included.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        View headerContainer = binding.navView.getHeaderView(0);
        circleImageView = headerContainer.findViewById(R.id.profile_image);
        circleImageView1 = headerContainer.findViewById(R.id.plus);
        circleImageView1.setOnClickListener(this);
        TextView userName = headerContainer.findViewById(R.id.nameOfUser);
        userName.setText(LoginUtils.getInstance(this).getName());
        TextView userEmail = headerContainer.findViewById(R.id.emailOfUser);
        userEmail.setText(LoginUtils.getInstance(this).getUserInfo().getUser_email());

        binding.included.content.listOfMobiles.setHasFixedSize(true);
        binding.included.content.listOfMobiles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfMobiles.setItemAnimator(null);

        binding.included.content.listOfLaptops.setHasFixedSize(true);
        binding.included.content.listOfLaptops.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfLaptops.setItemAnimator(null);

        binding.included.content.historyList.setHasFixedSize(true);
        binding.included.content.historyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.historyList.setItemAnimator(null);

        mobileAdapter = new ProductAdapter(this, this);
        laptopAdapter = new ProductAdapter(this, this);
        historyAdapter = new ProductAdapter(this, this);

        if (historyIsDeleted) {
            binding.included.content.textViewHistory.setVisibility(View.GONE);
        }
    }

    private void getMobiles() {
        if (isNetworkConnected(this)) {
            productViewModel.productPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    mobileAdapter.submitList(products);
                }
            });

            binding.included.content.listOfMobiles.setAdapter(mobileAdapter);
            mobileAdapter.notifyDataSetChanged();
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getLaptops() {
        if (isNetworkConnected(this)) {
            productViewModel.laptopPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    laptopAdapter.submitList(products);
                }
            });

            binding.included.content.listOfLaptops.setAdapter(laptopAdapter);
            laptopAdapter.notifyDataSetChanged();
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getHistory() {
        if (isNetworkConnected(this)) {
            historyViewModel.historyPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    binding.included.content.historyList.setAdapter(historyAdapter);
                    historyAdapter.submitList(products);
                    historyAdapter.notifyDataSetChanged();
                    products.addWeakCallback(null, productCallback);
                }
            });
        } else {
            showOrHideViews(View.INVISIBLE);
            binding.included.content.textViewHistory.setVisibility(View.GONE);
            showSnackBar();
        }

    }

    private void flipImages(ArrayList<String> images) {
        for (String image : images) {
            ImageView imageView = new ImageView(this);

            Glide.with(this)
                    .load(image)
                    .into(imageView);


            binding.included.content.imageSlider.addView(imageView);
        }

        binding.included.content.imageSlider.setFlipInterval(2000);
        binding.included.content.imageSlider.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.included.content.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving th screen
        binding.included.content.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSeeAllMobiles:
                Intent mobileIntent = new Intent(this, AllMobilesActivity.class);
                startActivity(mobileIntent);
                break;
            case R.id.txtSeeAllLaptops:
                Intent laptopIntent = new Intent(this, AllLaptopsActivity.class);
                startActivity(laptopIntent);
                break;
            case R.id.plus:
                showCustomAlertDialog();
                break;
            case R.id.txtCash:
                showNormalAlertDialog(getString(R.string.cash));
                break;
            case R.id.txtReturn:
                showNormalAlertDialog(getString(R.string.returnProduct));
                break;
            case R.id.txtSearch:
                Intent searchIntent = new Intent(ProductActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
    }

    private void showNormalAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null).show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.blue));
    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_image_dialog);

        Button takePicture = dialog.findViewById(R.id.takePicture);
        Button useGallery = dialog.findViewById(R.id.useGallery);

        takePicture.setEnabled(true);
        useGallery.setEnabled(true);

        takePicture.setOnClickListener(v -> {
            launchCamera();
            dialog.cancel();
        });

        useGallery.setOnClickListener(v -> {
            getImageFromGallery();
            dialog.cancel();
        });

        dialog.show();
    }

    private void getImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ProductActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_CODE);
            } else {
                try {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                    startActivityForResult(chooserIntent, GALLERY_REQUEST);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
            }
        }
    }


    private void launchCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            selectedImage = data.getData();
            circleImageView.setImageURI(selectedImage);
            encode_image=imageToString(bitmap);

            uploadPhoto(encode_image);

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(photo);


            encode_image=imageToString(photo);
            uploadPhoto(encode_image);


        }
    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
    private void uploadPhoto(String encode_image) {
        Toast.makeText(this, "IN UPLOADPHOTO FUNC", Toast.LENGTH_SHORT).show();
        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        int adminId= LoginUtils.getInstance(this).getUserInfo().getId();
        uploadProfileViewModel.getAddBannerResponseLiveData(new Upload(encode_image,adminId)).observe(this, uploadProfileApiResponse -> {
            if (!uploadProfileApiResponse.isError()) {
                Toast.makeText(this, uploadProfileApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                //LoginUtils.getInstance(this).saveUserInfo(addBannerApiResponse.getUser());
                progressDialog.dismiss();
            }else
            {
                progressDialog.cancel();
                Toast.makeText(this, uploadProfileApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getUserImage() {
        userImageViewModel.getUserImage(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, response -> {
            if (response != null) {
                String imageUrl =  response.getImage().replaceAll("\\\\", "/");

                Log.d(TAG, "GET USER IMAGE : " + imageUrl);
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.profile_picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform();

                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .apply(options)
                        .into(circleImageView);
            }
        });
    }

    public void showSnackBar() {
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }

    public void hideSnackBar() {
        snack.dismiss();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public void onNetworkConnected() {
        hideSnackBar();
        showOrHideViews(View.VISIBLE);
        getMobiles();
        getLaptops();
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem addMenu = menu.findItem(R.id.action_addProduct);
        if (LoginUtils.getInstance(this).getUserInfo().isActive()) {
            addMenu.setVisible(true);
        } else {
            addMenu.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                return true;
            case R.id.action_addProduct:
//                Intent addProductIntent = new Intent(this, AddProductActivity.class);
//                startActivity(addProductIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOrHideViews(int view) {
        binding.included.content.textViewMobiles.setVisibility(view);
        binding.included.content.txtSeeAllMobiles.setVisibility(view);
        binding.included.content.textViewLaptops.setVisibility(view);
        binding.included.content.txtSeeAllLaptops.setVisibility(view);
        binding.included.content.txtCash.setVisibility(view);
        binding.included.content.txtReturn.setVisibility(view);

    }
    private void signOut() {
        LoginUtils.getInstance(this).clearAll();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteAllProductsInHistory() {
        fromHistoryViewModel.removeAllFromHistory().observe(this, responseBody -> {
            Log.d(TAG,getString(R.string.all_removed));
        });
        historyIsDeleted = true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_mobiles) {
            goToCategoryActivity(getString(R.string.Furniture));
        } else if (id == R.id.nav_laptops) {
            goToCategoryActivity(getString(R.string.Appliances));
        } else if (id == R.id.nav_babies) {
            goToCategoryActivity(getString(R.string.Sports));
        } else if (id == R.id.nav_toys) {
            goToCategoryActivity("Toy");
        } else if (id == R.id.nav_trackOrder) {
            Intent orderIntent = new Intent(this, OrdersActivity.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_myAccount) {
            Intent accountIntent = new Intent(this, AccountActivity.class);
            startActivity(accountIntent);
        } else if (id == R.id.nav_newsFeed) {
            Intent newsFeedIntent = new Intent(this, NewsFeedActivity.class);
            startActivity(newsFeedIntent);
        } else if (id == R.id.nav_wishList) {
            Intent wishListIntent = new Intent(this, WishListActivity.class);
            startActivity(wishListIntent);
        }else if (id == R.id.nav_settings) {
            Intent SettingsIntent = new Intent(this, NavSettings.class);
            startActivity(SettingsIntent);
        }
        else  if (id == R.id.menu_logout) {
            signOut();
            deleteAllProductsInHistory();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToCategoryActivity(String category) {
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        categoryIntent.putExtra(CATEGORY, category);
        startActivity(categoryIntent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            closeApplication();
        }
    }

    private void closeApplication() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.want_to_exit)
                .setPositiveButton(R.string.ok, (dialog, which) -> finish())
                .setNegativeButton(R.string.cancel, null)
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.darkGreen));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.darkGreen));
    }


    @Override
    protected void onResume() {
        super.onResume();
        productViewModel.invalidate();
        getMobiles();
        getLaptops();
        historyViewModel.invalidate();
        getHistory();
    }

    private PagedList.Callback productCallback = new PagedList.Callback() {
        @Override
        public void onChanged(int position, int count) {
            Log.d(TAG, "onChanged: "+ count);
        }

        @Override
        public void onInserted(int position, int count) {
            Log.d(TAG, "onInserted: "+ count);
            if (count != 0) {
                binding.included.content.textViewHistory.setVisibility(View.VISIBLE);
                historyAdapter.notifyOnInsertedItem(position);
                getHistory();
            }
        }

        @Override
        public void onRemoved(int position, int count) {
            Log.d(TAG, "onRemoved: "+ count);
        }
    };

}
