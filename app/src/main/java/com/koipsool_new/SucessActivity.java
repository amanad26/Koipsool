package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.koipsool_new.ui.pharma.HomeActivity;

public class SucessActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();

        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SucessActivity.this, HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }

        }, SPLASH_SCREEN_TIME_OUT);
    }
}