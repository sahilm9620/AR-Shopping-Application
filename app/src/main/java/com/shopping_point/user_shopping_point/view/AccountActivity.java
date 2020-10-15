package com.shopping_point.user_shopping_point.view;

import android.app.Dialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.DeleteUserViewModel;
import com.shopping_point.user_shopping_point.ViewModel.FromHistoryViewModel;
import com.shopping_point.user_shopping_point.databinding.ActivityAccountBinding;
import com.shopping_point.user_shopping_point.storage.LoginUtils;

import java.io.IOException;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.getEnglishState;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.getHindiState;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.setEnglishState;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.setHindiState;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.setLocale;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.setMarathiState;
import static com.shopping_point.user_shopping_point.utils.CommunicateUtils.rateAppOnGooglePlay;
import static com.shopping_point.user_shopping_point.utils.CommunicateUtils.shareApp;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AccountActivity";
    private DeleteUserViewModel deleteUserViewModel;
    private FromHistoryViewModel fromHistoryViewModel;
    public static boolean historyIsDeleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        ActivityAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.my_account));

        deleteUserViewModel = ViewModelProviders.of(this).get(DeleteUserViewModel.class);
        fromHistoryViewModel = ViewModelProviders.of(this).get(FromHistoryViewModel.class);

        binding.nameOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        binding.emailOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());
        binding.emailOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());
       // binding.profileImage

        binding.myOrders.setOnClickListener(this);
        binding.myWishList.setOnClickListener(this);
        binding.languages.setOnClickListener(this);
        binding.helpCenter.setOnClickListener(this);
        binding.shareWithFriends.setOnClickListener(this);
        binding.rateUs.setOnClickListener(this);
        binding.changePassword.setOnClickListener(this);
        binding.deleteAccount.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signOut) {
            signOut();
            deleteAllProductsInHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myOrders:
                Intent ordersIntent = new Intent(this, OrdersActivity.class);
                startActivity(ordersIntent);
                break;
            case R.id.myWishList:
                Intent wishListIntent = new Intent(this, WishListActivity.class);
                startActivity(wishListIntent);
                break;
            case R.id.languages:
                showCustomAlertDialog();
                break;
            case R.id.helpCenter:
                Intent helpCenterIntent = new Intent(this, HelpActivity.class);
                startActivity(helpCenterIntent);
                break;
            case R.id.shareWithFriends:
                shareApp(this);
                break;
            case R.id.rateUs:
                rateAppOnGooglePlay(this);
                break;
            case R.id.changePassword:
                Intent passwordIntent = new Intent(this, PasswordActivity.class);
                startActivity(passwordIntent);
                break;
            case R.id.deleteAccount:
                deleteAccount();
                break;
        }
    }

    private void deleteAccount() {
        deleteUserViewModel.deleteUser(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, responseBody -> {
            if(responseBody!= null){
                LoginUtils.getInstance(getApplicationContext()).clearAll();
                try {
                    Toast.makeText(AccountActivity.this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: delete account" + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                goToLoginActivity();
            }
        });
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_language_dialog);

        Button english = dialog.findViewById(R.id.txtEnglish);
        Button hindi = dialog.findViewById(R.id.txtHindi);
        Button marathi = dialog.findViewById(R.id.txtMarathi);


        if(getEnglishState(this)){
            english.setEnabled(false);
            english.setAlpha(.5f);
            marathi.setEnabled(true);
            hindi.setEnabled(true);
        }else if(getHindiState(this)){
            hindi.setEnabled(false);
            hindi.setAlpha(.5f);
            marathi.setEnabled(true);
            english.setEnabled(true);
        }else {
            hindi.setEnabled(true);
            english.setEnabled(true);
            marathi.setEnabled(false);
            marathi.setAlpha(.5f);

        }

        english.setOnClickListener(v -> {
            english.setEnabled(true);
            chooseEnglish();
            dialog.cancel();
        });

        hindi.setOnClickListener(v -> {
            hindi.setEnabled(true);
            chooseHindi();
            dialog.cancel();
        });

        marathi.setOnClickListener(v -> {
            marathi.setEnabled(true);
            chooseMarathi();
            dialog.cancel();
        });

        dialog.show();
    }

    private void chooseEnglish() {
        setLocale(this,"en");
        recreate();
        Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();
        setEnglishState(this, true);
    }

    private void chooseHindi() {
        setLocale(this,"hi");
        recreate();
        Toast.makeText(this, "Hindi", Toast.LENGTH_SHORT).show();
        setHindiState(this, false);
        setEnglishState(this, true);

    }
    private void chooseMarathi() {
        setLocale(this,"mr");
        recreate();
        Toast.makeText(this, "Marathi", Toast.LENGTH_SHORT).show();
        setMarathiState(this, false);

    }
}