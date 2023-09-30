package com.koipsool_new.ui.pharma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityMedicalRepresentativesBinding;

public class MedicalRepresentativesActivity extends AppCompatActivity {

    ActivityMedicalRepresentativesBinding binding ;
    private Session session;
    MedicalRepresentativesActivity activity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMedicalRepresentativesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this ;
        session = new Session(activity);

//        binding.viewProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MedicalRepresentativesActivity.this,ViewProfileActivity.class);
//                startActivity(intent);
//            }
//        });
//        binding.filters.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MedicalRepresentativesActivity.this,FiltersActivity.class);
//                startActivity(intent);
//            }
//        });

        binding.icBack.setOnClickListener(view -> onBackPressed());

    }


}