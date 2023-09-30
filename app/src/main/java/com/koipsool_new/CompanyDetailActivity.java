package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.koipsool_new.databinding.ActivityCompanyDetailBinding;
import com.koipsool_new.ui.chemist.ChemistFragment;

public class CompanyDetailActivity extends AppCompatActivity {
private ActivityCompanyDetailBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompanyDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.icBack.setOnClickListener(view -> onBackPressed());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new ChemistFragment()).commit();


//        binding.myBidsCompany.setOnClickListener(view -> {
//            Intent intent = new Intent(CompanyDetailActivity.this,ViewProductsActivity.class);
//            startActivity(intent);
//        });
//



    }
}