package com.shopping_point.user_shopping_point.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.PasswordViewModel;
import com.shopping_point.user_shopping_point.databinding.ActivityPasswordBinding;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.Validation;

import java.io.IOException;

import static com.shopping_point.user_shopping_point.view.PasswordAssistantActivity.emailEntered;
import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.user_shopping_point.view.AuthenticationActivity.isActivityRunning;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "PasswordActivity";
    private ActivityPasswordBinding binding;
    private PasswordViewModel passwordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.change_password));

        passwordViewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);

        binding.saveChanges.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);


        if(isActivityRunning){

            binding.currentPassword.setVisibility(View.GONE);
            binding.txtCurrentPassword.setVisibility(View.GONE);
            binding.txtCurrentPassInputLayout.setVisibility(View.GONE);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveChanges:
                updatePassword();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    private void updatePassword() {
        String newPassword = binding.newPassword.getText().toString();
        String retypePassword =binding.retypePassword.getText().toString();


if(!isActivityRunning) {

    String oldPassword = LoginUtils.getInstance(this).getUserInfo().getUser_password().trim();
    String currentPassword = binding.currentPassword.getText().toString().trim();
    Toast.makeText(this, oldPassword, Toast.LENGTH_SHORT).show();
    if (!oldPassword.equals(currentPassword)) {

        binding.currentPassword.setError(getString(R.string.enter_current_password));
        binding.currentPassword.requestFocus();
        return;
    }

}
        if (!Validation.isValidPassword(newPassword)) {
            binding.newPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.newPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(newPassword) || !(retypePassword.equals(newPassword))) {
            binding.retypePassword.setError(getString(R.string.password_not_match));
            binding.retypePassword.requestFocus();
            return;
        }

        passwordViewModel.updatePassword(newPassword,emailEntered).observe(this, responseBody -> {

            try {
                Log.d(TAG, "onSucceed: " +responseBody.string());
                startActivity(new Intent(this,LoginActivity.class));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "ERRORRRR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
