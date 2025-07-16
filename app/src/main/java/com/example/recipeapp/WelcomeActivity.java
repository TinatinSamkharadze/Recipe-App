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

        // Initialize views
        btnStartCooking = findViewById(R.id.btnStartCooking);

        // Set click listener for start cooking button
        btnStartCooking.setOnClickListener(v -> {
            // Navigate to MainActivity (your recipe list page)
            Intent intent = new Intent(WelcomeActivity.this, com.example.recipeapp.ui.main.MainActivity.class);
            startActivity(intent);
            finish(); // Optional: finish this activity so user can't go back
        });
    }
}