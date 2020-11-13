package com.shopping_point.user_shopping_point.view;

import android.app.ProgressDialog;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.LoginViewModel;
import com.shopping_point.user_shopping_point.databinding.ActivityLoginBinding;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.Validation;
import static com.shopping_point.user_shopping_point.view.PasswordAssistantActivity.emailEntered;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private Snackbar snack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        binding.buttonLogin.setOnClickListener(this);
        binding.textViewSignUp.setOnClickListener(this);
        binding.forgetPassword.setOnClickListener(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // If user logged in, go directly to ProductActivity
        if (LoginUtils.getInstance(this).isLoggedIn()) {
            goToProductActivity();
        }
    }

    private void logInUser() {
        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();

        if (email.isEmpty()) {
            binding.inputEmail.setError(getString(R.string.email_required));
            binding.inputEmail.requestFocus();
        }

        if (Validation.isValidEmail(email)) {
            binding.inputEmail.setError(getString(R.string.enter_a_valid_email_address));
            binding.inputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.inputPassword.setError(getString(R.string.password_required));
            binding.inputPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(password)) {
            binding.inputPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.inputPassword.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        loginViewModel.getLoginResponseLiveData(email,password).observe(this, loginApiResponse -> {
            if (!loginApiResponse.isError()) {
                LoginUtils.getInstance(this).saveUserInfo(loginApiResponse);
                emailEntered=loginApiResponse.getUser_email();
                Toast.makeText(this, loginApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                goToProductActivity();
            }else {
                progressDialog.cancel();
                showMsg("ERROR",loginApiResponse.getMessage());
                Toast.makeText(this, loginApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMsg(String titel, String message) {
        snack = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);

        snack.setAction("Re-Try", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snack.dismiss();
            }
        });
        snack.setTextColor(Color.RED);
        snack.setBackgroundTint(Color.LTGRAY);
        snack.setActionTextColor(Color.WHITE);
        snack.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                logInUser();
                break;
            case R.id.textViewSignUp:
                goToSignUpActivity();
                break;
            case R.id.forgetPassword:
                goToPasswordAssistantActivity();
                break;
        }
    }

    private void goToSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void goToProductActivity() {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goToPasswordAssistantActivity() {
        Intent intent = new Intent(this, PasswordAssistantActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
