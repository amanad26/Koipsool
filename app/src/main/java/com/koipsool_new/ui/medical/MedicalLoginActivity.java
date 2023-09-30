package com.koipsool_new.ui.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.koipsool_new.OtpVerificationActivity;
import com.koipsool_new.databinding.ActivityMedicalLoginBinding;

public class MedicalLoginActivity extends AppCompatActivity {

    ActivityMedicalLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicalLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(MedicalLoginActivity.this, OtpVerificationActivity.class);
            startActivity(intent);
        });

    }
}