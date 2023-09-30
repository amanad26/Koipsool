package com.koipsool_new.ui.pharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.koipsool_new.ViewMrReportActivity;
import com.koipsool_new.databinding.ActivityMadicalReportBinding;

public class MadicalReportActivity extends AppCompatActivity {
private ActivityMadicalReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMadicalReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.viewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MadicalReportActivity.this, ViewMrReportActivity.class);
                startActivity(intent);
            }
        });
    }
}