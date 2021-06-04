package com.shopping_point.user_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.shopping_point.user_shopping_point.R;

public class AR_Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r__test);
        Toast.makeText(this, " Welcome AR ", Toast.LENGTH_SHORT).show();
    }
}