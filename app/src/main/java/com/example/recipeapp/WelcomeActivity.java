package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnStartCooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnStartCooking = findViewById(R.id.btnStartCooking);

        btnStartCooking.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, com.example.recipeapp.ui.main.MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}