package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityMedcastBinding;
import com.koipsool_new.ui.chemist.ChemistSignUpActivity;
import com.koipsool_new.ui.pharma.PharmaSignupActivity;
import com.koipsool_new.ui.medical.MedicalSignupActivity;

public class MedcastActivity extends AppCompatActivity {
    ActivityMedcastBinding binding;
    String selected_tab = "";
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedcastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        session = new Session(MedcastActivity.this);


        binding.nextBtn.setOnClickListener(view -> {
            if (selected_tab.equalsIgnoreCase("pharma")) {
                session.setType("pharma");
                Intent intent = new Intent(MedcastActivity.this, PharmaSignupActivity.class);
                startActivity(intent);

            } else if (selected_tab.equalsIgnoreCase("medical")) {
                session.setType("medical");
                Intent intent = new Intent(MedcastActivity.this, MedicalSignupActivity.class);
                startActivity(intent);
            } else if (selected_tab.equalsIgnoreCase("chemist")) {
                session.setType("chemist");
                Intent intent = new Intent(MedcastActivity.this, ChemistSignUpActivity.class);
                startActivity(intent);
            }

        });

        binding.pharmaCom.setOnClickListener(view -> {
            binding.pharmaCom.setBackgroundResource(R.drawable.selected_bg);
            binding.medicalReg.setBackgroundResource(R.drawable.custom_bg_btn);
            binding.chemist.setBackgroundResource(R.drawable.custom_bg_btn);
            selected_tab = "pharma";
        });

        binding.medicalReg.setOnClickListener(view -> {
            binding.pharmaCom.setBackgroundResource(R.drawable.custom_bg_btn);
            binding.medicalReg.setBackgroundResource(R.drawable.selected_bg);
            binding.chemist.setBackgroundResource(R.drawable.custom_bg_btn);
            selected_tab = "medical";
        });

        binding.chemist.setOnClickListener(view -> {
            binding.pharmaCom.setBackgroundResource(R.drawable.custom_bg_btn);
            binding.medicalReg.setBackgroundResource(R.drawable.custom_bg_btn);
            binding.chemist.setBackgroundResource(R.drawable.selected_bg);
            selected_tab = "chemist";
        });


    }
}


