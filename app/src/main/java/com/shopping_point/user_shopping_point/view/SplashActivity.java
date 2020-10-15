package com.shopping_point.user_shopping_point.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.shopping_point.user_shopping_point.R;
import com.shopping_point.user_shopping_point.storage.LoginUtils;

import static com.shopping_point.user_shopping_point.storage.LanguageUtils.loadLocale;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, ProductActivity.class);
                startActivity(i);

                // Close this activity
                finish();
                // If user does not log in before, go to LoginActivity
                if(!LoginUtils.getInstance(SplashActivity.this).isLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
