package com.koipsool_new.ui.chemist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.koipsool_new.databinding.ActivityRequestBillBinding;

public class RequestBillActivity extends AppCompatActivity {
    private ActivityRequestBillBinding binding;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

    }
}