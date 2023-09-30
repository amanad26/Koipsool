package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.koipsool_new.databinding.ActivityViewProductAllDetailsBinding;

public class ViewProductAllDetailsActivity extends AppCompatActivity {

    private ActivityViewProductAllDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewProductAllDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.icBack.setOnClickListener(view -> onBackPressed());
    }
}