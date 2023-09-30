package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.koipsool_new.ui.pharma.HomeActivity;

public class InternshipSuccessActivity extends AppCompatActivity {
    private static int SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship_success);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();

        window.setStatusBarColor(getColor(R.color.white));
        Animation hold = AnimationUtils.loadAnimation(this, R.anim.hold);

        // session  = new Session(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(InternshipSuccessActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }

        }, SCREEN_TIME_OUT);
    }
}


