package com.shopping_point.user_shopping_point.view;

import android.app.ProgressDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.RegisterViewModel;
import com.shopping_point.user_shopping_point.databinding.ActivitySignupBinding;
import com.shopping_point.user_shopping_point.model.User;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.Validation;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";
    private ActivitySignupBinding binding;
    private RegisterViewModel registerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        binding.buttonSignUp.setOnClickListener(this);
        binding.textViewLogin.setOnClickListener(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

//        setBoldStyle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (LoginUtils.getInstance(this).isLoggedIn()) {
            goToProductActivity();
        }
    }

    private void signUpUser() {
        String name = binding.userName.getText().toString();
        String email = binding.userEmail.getText().toString();
        String phone_no = binding.ccp.getSelectedCountryCodeWithPlus() + binding.userContact.getText().toString();

        String con_password = binding.userConfirmPassword.getText().toString();
        String password = binding.userPassword.getText().toString();

        if (name.isEmpty()) {
            binding.userName.setError(getString(R.string.name_required));
            binding.userName.requestFocus();
            return;
        }

        if (!Validation.isValidName(name)) {
            binding.userName.setError(getString(R.string.enter_at_least_3_characters));
            binding.userName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            binding.userEmail.setError(getString(R.string.email_required));
            binding.userEmail.requestFocus();
        }

        if (Validation.isValidEmail(email)) {
            binding.userEmail.setError(getString(R.string.enter_a_valid_email_address));
            binding.userEmail.requestFocus();
            return;
        }

        if(phone_no.isEmpty())
        {
            binding.userContact.setError(getString(R.string.phone_required));
            binding.userContact.requestFocus();
            return;
        }
        Toast.makeText(this, phone_no, Toast.LENGTH_SHORT).show();
        if (password.isEmpty()) {
            binding.userPassword.setError(getString(R.string.password_required));
            binding.userPassword.requestFocus();
            return;
        }

        if (con_password.isEmpty()) {
            binding.userConfirmPassword.setError(getString(R.string.password_required));
            binding.userConfirmPassword.requestFocus();
            return;
        }

        if(!password.equals(con_password))
        {
            binding.userPassword.setError(getString(R.string.password_missmatch));
            binding.userPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(password)) {
            binding.userPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.userPassword.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage(getString(R.string.create_account));
        progressDialog.setCancelable(false);
        progressDialog.show();

        registerViewModel.getRegisterResponseLiveData(new User(name, email, phone_no,password)).observe(this, registerApiResponse -> {
            if (!registerApiResponse.isError()) {
                Toast.makeText(this, registerApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                //LoginUtils.getInstance(this).saveUserInfo(registerApiResponse.getUser());
                progressDialog.dismiss();
                goToLoginActivity();
            }else
                {
                progressDialog.cancel();
                Toast.makeText(this, registerApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                signUpUser();
                break;
            case R.id.textViewLogin:
                goToLoginActivity();
                break;
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToProductActivity() {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

//    private void setBoldStyle() {
//        String boldText = getString(R.string.boldText);
//        String normalText = getString(R.string.normalText);
//        SpannableString str = new SpannableString(  normalText + boldText);
//        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        binding.textViewLogin.setText(str);
//    }
}
