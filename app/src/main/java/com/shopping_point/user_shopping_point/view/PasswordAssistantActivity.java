package com.shopping_point.user_shopping_point.view;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;


import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.OtpViewModel;
import com.shopping_point.user_shopping_point.databinding.ActivityPasswordAssistantBinding;

import static com.shopping_point.user_shopping_point.utils.Constant.EMAIL;
import static com.shopping_point.user_shopping_point.utils.Constant.OTP;

public class PasswordAssistantActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PasswordAssistantActivity";
    private ActivityPasswordAssistantBinding binding;
    private OtpViewModel otpViewModel;
    private String userEmail;
    private String otpCode;
    public static  String emailEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_assistant);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.password_assistance));
        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);
        binding.proceed.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            checkUserEmail();
        }
    }

    private void checkUserEmail() {
        emailEntered = binding.emailAddress.getText().toString();

        otpViewModel.getOtpCode(emailEntered).observe(this, responseBody -> {
            if (!responseBody.isError()) {
                userEmail = responseBody.getEmail();
                otpCode = responseBody.getOtp();
                goToAuthenticationActivity();
            } else {
                binding.emailAddress.setError(responseBody.getMessage());
            }
        });
    }

    private void goToAuthenticationActivity() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra(EMAIL, userEmail);
        intent.putExtra(OTP, otpCode);
        startActivity(intent);
    }
}
