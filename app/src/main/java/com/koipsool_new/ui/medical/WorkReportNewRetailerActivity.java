package com.koipsool_new.ui.medical;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.koipsool_new.databinding.ActivityWorkReportNewRetailerBinding;

public class WorkReportNewRetailerActivity extends AppCompatActivity {
    private ActivityWorkReportNewRetailerBinding binding;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkReportNewRetailerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        binding.nextBtn.setOnClickListener(view -> startActivity(new Intent(WorkReportNewRetailerActivity.this, FinalWorkReportActivity.class)));
    }
}