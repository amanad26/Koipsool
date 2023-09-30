package com.koipsool_new;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.koipsool_new.databinding.ActivityViewJobDetailBinding;

public class ViewJobDetailActivity extends AppCompatActivity {
    private ActivityViewJobDetailBinding binding;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewJobDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        binding.icBack.setOnClickListener(view -> onBackPressed());
        binding.update.setOnClickListener(view -> startActivity(new Intent(activity, InternshipSuccessActivity.class)));
    }
}