package com.koipsool_new.ui.chemist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.koipsool_new.OtpVerificationActivity;
import com.koipsool_new.databinding.ActivityChemistLoginBinding;

public class ChemistLoginActivity extends AppCompatActivity {
    private ActivityChemistLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChemistLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(ChemistLoginActivity.this, OtpVerificationActivity.class);
            startActivity(intent);
        });
    }
}