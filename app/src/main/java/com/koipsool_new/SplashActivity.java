package com.koipsool_new;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.koipsool_new.Session.Session;
import com.koipsool_new.ui.chemist.ChemistActivity;
import com.koipsool_new.ui.medical.HomeMedicalResActivity;
import com.koipsool_new.ui.pharma.HomeActivity;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    private Session session;
    private SplashActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();

        window.setStatusBarColor(getColor(R.color.white));
        activity = this;
        session = new Session(activity);

//        session.setType(medical);
//        session.setUser_id("1");

        new Handler().postDelayed(() -> {
            if (session.sharedPreferences.contains("user_id")) {
                Log.e("TAG", "run() called user id "+session.getUserId());
                if (session.getType().equalsIgnoreCase("pharma")) {
                    Intent intent = new Intent(activity, HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                } else if (session.getType().equalsIgnoreCase("medical")) {
                    Intent intent = new Intent(activity, HomeMedicalResActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                } else if (session.getType().equalsIgnoreCase("chemist")) {
                    Intent intent = new Intent(activity, ChemistActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }

            } else {
                Intent intent = new Intent(activity, MedcastActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }


        }, SPLASH_SCREEN_TIME_OUT);
    }
}