package com.koipsool_new.ui.pharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.koipsool_new.ViewBillActivity;
import com.koipsool_new.databinding.ActivityClientsBinding;

public class ClientsActivity extends AppCompatActivity {
private ActivityClientsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.linear1.setVisibility(View.VISIBLE);
        binding.linear2.setVisibility(View.GONE);

        binding.radioInternshipL2.setChecked(true);

        binding.radioInternshipL2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    binding.linear1.setVisibility(View.VISIBLE);
                    binding.linear2.setVisibility(View.GONE);
                }

            }
        });

        binding.radioJobL2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    binding.linear1.setVisibility(View.GONE);
                    binding.linear2.setVisibility(View.VISIBLE);
                }

            }
        });


        binding.txtBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ClientsActivity.this, ViewBillActivity.class);
                startActivity(intent);
            }
        });
    }
}