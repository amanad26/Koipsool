package com.koipsool_new.ui.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.koipsool_new.databinding.ActivityNewPrescriberBinding;

public class NewPrescriberActivity extends AppCompatActivity {
    ActivityNewPrescriberBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPrescriberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nxtBtn.setOnClickListener(view -> {
            Intent intent = new Intent(NewPrescriberActivity.this, NewPrescribeSubmitActivity.class);
            startActivity(intent);
        });
    }
}