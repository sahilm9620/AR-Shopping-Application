package com.shopping_point.user_shopping_point.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.databinding.ActivityNavSettingsBinding;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;

public class NavSettings extends AppCompatActivity implements View.OnClickListener {
    private ActivityNavSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nav_settings);
        binding.feedbackDescLink.setOnClickListener(this);
        //  Toast.makeText(this, "SETTIIINNNGGGSSSSSS OOPPEENN", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_desc_link:
                feedback();
                break;

        }
    }

    private void feedback() {
        String s_email= "info@shoppingpoint.com";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{s_email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Issue/Feedback");
        startActivity(intent);
    }
}