package com.koipsool_new.ui.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.koipsool_new.databinding.ActivityNewPrescribeSubmitBinding;


import java.util.ArrayList;

public class NewPrescribeSubmitActivity extends AppCompatActivity {
    ActivityNewPrescribeSubmitBinding binding;
    ArrayList<String> clientlst = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPrescribeSubmitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nxtBtn.setOnClickListener(view -> {
            Intent intent = new Intent(NewPrescribeSubmitActivity.this, WorkReportNewRetailerActivity.class);
            startActivity(intent);

        });
        clientlst.add("New Chemist");
        clientlst.add("Client Chemist");
        clientlst.add("New Prescriber");
        clientlst.add("Client Prescriber");
        clientlst.add("Other Chemist");
        clientlst.add("Other Prescriber");

        binding.clientSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, clientlst));

    }
}