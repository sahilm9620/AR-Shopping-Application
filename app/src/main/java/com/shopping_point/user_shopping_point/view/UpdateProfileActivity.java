package com.shopping_point.user_shopping_point.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.ViewModel.UpdateProfileViewModel;
import com.shopping_point.user_shopping_point.databinding.ActivityUpdateProfileBinding;
import com.shopping_point.user_shopping_point.model.Update;
import com.shopping_point.user_shopping_point.storage.LoginUtils;
import com.shopping_point.user_shopping_point.utils.Validation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UpdateProfileActivity";
    private UpdateProfileViewModel updateProfileViewModel;
    private ActivityUpdateProfileBinding binding;
    String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile);


        binding.name.setText(LoginUtils.getInstance(this).getName());
        binding.email.setText(LoginUtils.getInstance(this).getUserInfo().getUser_email());
        binding.contact.setText(LoginUtils.getInstance(this).getUserInfo().getUser_contact_number());
       binding.dob.setText(LoginUtils.getInstance(this).getDob());
       if(LoginUtils.getInstance(this).getGender().equals("Male"))
       {
           binding.male.toggle();
       }else
       {
           binding.female.toggle();
       }
        binding.proceed.setOnClickListener(this);
        binding.cancleUpdate.setOnClickListener(this);
        binding.dob.setOnClickListener(this);

        updateProfileViewModel = ViewModelProviders.of(this).get(UpdateProfileViewModel.class);
        // setBoldStyle();
    }

    public void onRadioButtonClicked(View view) {
        boolean status_checked=((RadioButton)view).isChecked();
        switch (view.getId())
        {
            case R.id.male:
                if(status_checked)
                {
                    gender = binding.male.getText().toString().trim();
                }

                break;
            case R.id.female:
                if(status_checked) {
                    gender = binding.female.getText().toString().trim() + "\n";
                }
                break;
        }

    }

    private void updateAdmin() {

        String email = LoginUtils.getInstance(this).getUserInfo().getUser_email();
        String name =  binding.name.getText().toString().trim();
        String phone_no = binding.contact.getText().toString().trim();
        String dob = binding.dob.getText().toString();

        Toast.makeText(this, email + name + phone_no + gender + dob , Toast.LENGTH_SHORT).show();
        if (name.isEmpty()) {
            binding.name.setError(getString(R.string.Name_Required));
            binding.name.requestFocus();
            return;
        }

        if (!Validation.isValidName(name)) {
            binding.name.setError(getString(R.string.enter_at_least_3_characters));
            binding.name.requestFocus();
            return;
        }

        if (phone_no.isEmpty()) {
            binding.contact.setError(getString(R.string.Phone_Required));
            binding.contact.requestFocus();
            return;
        }


        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage("Profile Updating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        updateProfileViewModel.getUpdateResponseLiveData(new Update(email, name, phone_no,gender,dob)).observe(this, updateApiResponse -> {
            if (!updateApiResponse.isError()) {
               // Toast.makeText(this, updateApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(this, updateApiResponse.getPhone_no(), Toast.LENGTH_SHORT).show();
                 LoginUtils.getInstance(this).saveUserInfo(updateApiResponse.getName(),updateApiResponse.getEmail(),updateApiResponse.getPhone_no(),updateApiResponse.getGender(),updateApiResponse.getDob());
                progressDialog.dismiss();
                 goToAccountActivity();
            } else {
              //  Toast.makeText(this, updateApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }

    private void goToAccountActivity() {
        startActivity(new Intent(UpdateProfileActivity.this,AccountActivity.class));
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.proceed:
                updateAdmin();
                break;
            case R.id.cancle_update:
                finish();
                break;
            case R.id.dob:
                date();
                break;


        }
    }

    private void date() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        DatePickerDialog datePicker  = new DatePickerDialog(UpdateProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePicker.show();

    }

}