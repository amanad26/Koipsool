package com.koipsool_new.ui.medical;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;


import com.koipsool_new.R;
import com.koipsool_new.ResumeFragment;

import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityContinueApplicationBinding;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.model.MedicalInternshipModel;
import com.koipsool_new.model.MedicalJobModel;


public class ContinueApplicationActivity extends AppCompatActivity {

    private ContinueApplicationActivity activity;
    private Session session;
    private ActivityContinueApplicationBinding binding;
    private ProgressDialog pd;
    private String id = "", type = "", c_id = "", name = "", cName;
    private MedicalJobModel.JobData jobData;
    private MedicalInternshipModel.MedicalInternshipData internshipData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContinueApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        type = getIntent().getStringExtra("type");

        if (type.equalsIgnoreCase("job")) {
            jobData = (MedicalJobModel.JobData) getIntent().getSerializableExtra("data");
            setJobData();
        } else {
            internshipData = (MedicalInternshipModel.MedicalInternshipData) getIntent().getSerializableExtra("data");
           setInternshipData();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.resume_container, new ResumeFragment()).commit();

        binding.titleTv.setText("Applying " + name + "work from home/office at " + cName);

    }

    private void setInternshipData() {
        id = internshipData.getId();
        c_id = internshipData.getCompanyId();


        binding.applyBtn.setOnClickListener(view -> startActivity(new Intent(activity,AskQuestionsActivity.class)
                .putExtra("type" , "internship")
                .putExtra("data",internshipData)));

    }

    private void setJobData() {
        id = jobData.getId();
        c_id = jobData.getCompanyId();

        binding.applyBtn.setOnClickListener(view -> startActivity(new Intent(activity,AskQuestionsActivity.class)
                .putExtra("type" , "job")
                .putExtra("data",jobData)));

    }

}