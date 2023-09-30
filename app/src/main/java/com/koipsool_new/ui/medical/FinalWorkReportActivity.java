package com.koipsool_new.ui.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.koipsool_new.databinding.ActivityFinalWorkReportBinding;

public class FinalWorkReportActivity extends AppCompatActivity {
ActivityFinalWorkReportBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding = ActivityFinalWorkReportBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());


    binding.nextBtn.setOnClickListener(view -> {
        Intent intent = new Intent(FinalWorkReportActivity.this, NewPrescriberActivity.class);
        startActivity(intent);
    });
    }
}