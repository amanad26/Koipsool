package com.koipsool_new.ui.medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.koipsool_new.databinding.ActivityWorkReportBinding;

import java.util.ArrayList;

public class WorkReportActivity extends AppCompatActivity {
    private ActivityWorkReportBinding binding;

    ArrayList<String> cltlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWorkReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.nxtBtn.setOnClickListener(view -> {
            Intent intent = new Intent(WorkReportActivity.this, NewPrescriberActivity.class);
            startActivity(intent);
        });

        binding.icBack.setOnClickListener(view -> onBackPressed());

        cltlist.add("New Chemist*");
        cltlist.add("Client Chemist");
        cltlist.add("New Prescriber");
        cltlist.add("Client Prescriber");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cltlist);
        binding.clientSpinner.setAdapter(spinnerAdapter);
    }
}
