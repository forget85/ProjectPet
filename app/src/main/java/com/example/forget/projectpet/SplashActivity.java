package com.example.forget.projectpet;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        runnable = null;
        handler = null;
    }
}
