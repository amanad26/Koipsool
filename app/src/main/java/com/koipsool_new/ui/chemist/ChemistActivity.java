package com.koipsool_new.ui.chemist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.koipsool_new.CompanyDetailActivity;
import com.koipsool_new.HelpAndSuuportActivity;
import com.koipsool_new.MoreActivity;
import com.koipsool_new.R;
import com.koipsool_new.RewardsActivity;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityChemistBinding;
import com.koipsool_new.ui.pharma.MyBillingActivity;
import com.squareup.picasso.Picasso;

public class ChemistActivity extends AppCompatActivity {

    private ActivityChemistBinding binding;
    private ChemistActivity activity;
    private Session session;

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.close();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChemistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fullscreen_container, new ChemistFragment()).commit();

        binding.icMenu.setOnClickListener(view -> binding.drawerLayout.open());

        binding.companies.setOnClickListener(view -> {
            Intent intent = new Intent(activity, CompanyDetailActivity.class);
            startActivity(intent);
        });

        binding.profileLinear.setOnClickListener(view -> startActivity(new Intent(activity, ChemistProfileActivity.class)));

        binding.reward.setOnClickListener(view -> {
            Intent intent = new Intent(activity, RewardsActivity.class);
            startActivity(intent);
        });

        binding.requestBill.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ClientsBillActivity.class);
            startActivity(intent);
        });

        binding.myBills.setOnClickListener(view -> {
            Intent intent = new Intent(activity, MyBillingActivity.class);
            startActivity(intent);
        });


        binding.helpAndSupport.setOnClickListener(view -> {
            Intent intent = new Intent(activity, HelpAndSuuportActivity.class);
            startActivity(intent);
        });


        binding.more.setOnClickListener(view -> {
            Intent intent = new Intent(activity, MoreActivity.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!session.getImage().equalsIgnoreCase(""))
            Picasso.get().load(session.getImage()).placeholder(R.drawable.user_profile).into(binding.changePhoto);

          binding.userName.setText(session.getUserName());
          binding.userCity.setText(session.getEmail());


    }


}