package com.example.opengenus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class splash extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(splash.this, MainActivity.class));
            progressBar.setVisibility(View.VISIBLE);
            finish();
        }, 2000);
        progressBar.setVisibility(View.GONE);
    }
}