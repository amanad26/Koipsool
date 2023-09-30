package com.koipsool_new.ui.medical;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.koipsool_new.HelpAndSuuportActivity;
import com.koipsool_new.MoreActivity;
import com.koipsool_new.R;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityHomeMedicalResBinding;
import com.squareup.picasso.Picasso;

public class HomeMedicalResActivity extends AppCompatActivity {

    private ActivityHomeMedicalResBinding binding;
    private Activity activity;
    private Session session ;


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
        binding = ActivityHomeMedicalResBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        session = new Session(activity);



        getSupportFragmentManager().beginTransaction().add(R.id.fullscreen_container, new MedicalHomeFragment()).commit();

        binding.txtBrowseJob.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, BrowseJobActivity.class)));

        binding.navMyApplication.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, MyPastApplicationActivity.class)));

        binding.changePhoto.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, ChangePhotoActivity.class)));

        binding.changeProfile.setOnClickListener((View view) -> startActivity(new Intent(HomeMedicalResActivity.this, ChangePhotoActivity.class)));

        binding.icMenu.setOnClickListener(view -> binding.drawerLayout.open());

        binding.browseInternship.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, BrowseInternshipActivity.class)));

        binding.resumeTxt.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, ResumeActivity.class)));

        binding.moreLinear.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, MoreActivity.class)));

        binding.workReport.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, WorkReportActivity.class)));


        binding.savedList.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, SavedJobListActivity.class)));


        binding.navTowService.setOnClickListener(view -> startActivity(new Intent(HomeMedicalResActivity.this, HelpAndSuuportActivity.class)));




    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!session.getImage().equalsIgnoreCase(""))
            Picasso.get().load(session.getImage()).placeholder(R.drawable.user_profile).into(binding.changePhoto);

        if(!session.getUserName().equalsIgnoreCase(""))
            binding.userName.setText(session.getUserName());



    }
}