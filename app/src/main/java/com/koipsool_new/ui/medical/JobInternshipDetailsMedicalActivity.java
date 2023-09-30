package com.koipsool_new.ui.medical;

import static android.content.ContentValues.TAG;

import static com.koipsool_new.RetofitSetUp.BaseUrls.USER_IMAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityJobInternshipDetailsMedicalBinding;
import com.koipsool_new.kapsoolAdapters.SkillsParksAdapter;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.model.MedicalInternshipModel;
import com.koipsool_new.model.MedicalJobModel;
import com.koipsool_new.model.TimeAgo2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JobInternshipDetailsMedicalActivity extends AppCompatActivity {

    private MedicalJobModel.JobData data;
    private MedicalInternshipModel.MedicalInternshipData internshipData;
    private String type = "";
    private ActivityJobInternshipDetailsMedicalBinding binding;
    private JobInternshipDetailsMedicalActivity activity;
    private TimeAgo2 ago2;
    private Session session;
    private ProgressDialog pd;
    private boolean iSaved = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobInternshipDetailsMedicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        ago2 = new TimeAgo2();
        session = new Session(activity);
        pd = new ProgressDialog(activity);


        type = getIntent().getStringExtra("type");

        if (type.equalsIgnoreCase("1")) {
            data = (MedicalJobModel.JobData) getIntent().getSerializableExtra("data");
            setJobData();
        } else {
            internshipData = (MedicalInternshipModel.MedicalInternshipData) getIntent().getSerializableExtra("data");
            setInternshipData();
        }


    }

    private void setInternshipData() {

        Log.e("TAG", "setInternshipData() called Save Status "+internshipData.getiSaved());


        if(internshipData.getiApplied().equalsIgnoreCase("1")){
            binding.applyBtnNo.setVisibility(View.VISIBLE);
            Log.e("TAG", "setInternshipData() called"+internshipData.getiApplied());
        }else {
            binding.applyBtn.setVisibility(View.VISIBLE);
        }


        if(!internshipData.getCompany_image().equalsIgnoreCase("")){
            Picasso.get().load(USER_IMAGE+internshipData.getCompany_image()).into(binding.comapnyLogo);
        }

        binding.aboutText.setText(internshipData.getAbout());
        binding.companyNameText.setText(internshipData.getCompanyName());
        binding.locationText.setText(internshipData.getCompanyAddress());
        binding.numberOfOpening.setText(internshipData.getNumberOfOpening());
        binding.priceText.setText(internshipData.getPriceFrom());
        binding.aboutCompany.setText("About " + internshipData.getCompanyName());
        binding.responsibilitesText.setText(internshipData.getInternshipResponsibilities());
        binding.titleText.setText(internshipData.getInternshipTitle());
        binding.internshipStartText.setText(internshipData.getInternshipStartDate());
        binding.internshipDurationText.setText(internshipData.getInternshipDuration());
        binding.websiteText.setText(internshipData.getWebsites());
        binding.typeText.setText("Internship");
        binding.postedDayText.setText(ago2.covertTimeToText(internshipData.getCreatedDate()));
        binding.applicantsText.setText(internshipData.getCount() + " Applicants");

        List<String> skillsList = null;
        if (internshipData.getSkillsRequired().size() != 0) {
            skillsList = new ArrayList<>();
            for (int i = 0; i < internshipData.getSkillsRequired().size(); i++) {
                skillsList.add(internshipData.getSkillsRequired().get(i).getSkillName());
            }
        }

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.skillsRecycler.setLayoutManager(layoutManager);
        binding.skillsRecycler.setAdapter(new SkillsParksAdapter(activity, skillsList));


        String[] parks = internshipData.getPerks().split(",");
        List<String> parksList = Arrays.asList(parks);
        Log.e("TAG", "setInternshipData() called parksList" + parksList.toString());


        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(activity);
        layoutManager2.setFlexDirection(FlexDirection.ROW);
        layoutManager2.setJustifyContent(JustifyContent.FLEX_START);
        binding.parkesRecycler.setLayoutManager(layoutManager2);
        binding.parkesRecycler.setAdapter(new SkillsParksAdapter(activity, parksList));

        //// binding.deleteJobInternship.setOnClickListener(view -> deleteInternshipDialog(internshipData.getId()));
        binding.applyBtn.setOnClickListener(view -> startActivity(new Intent(activity, ContinueApplicationActivity.class)
                .putExtra("type", "internship")
                .putExtra("data", internshipData)

        ));


        if(internshipData.getiSaved().equalsIgnoreCase("1")){
            iSaved = true ;
            binding.saveBtn.setImageResource(R.drawable.ic_saved);
        }else {
            iSaved = false ;
            binding.saveBtn.setImageResource(R.drawable.ic_save);
        }

        binding.saveBtn.setOnClickListener(view -> {

            if(iSaved){
                iSaved = false ;
                binding.saveBtn.setImageResource(R.drawable.ic_save);
            }else {
                iSaved = true ;
                binding.saveBtn.setImageResource(R.drawable.ic_saved);
            }

            saveUnSaveJob("internship", internshipData.getCompanyId(), internshipData.getId());
        });


    }


    private void saveUnSaveJob(String type, String companyId, String id) {

        String save = "";
        if(iSaved) save = "1";
        else save = "0";


        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.saveUnSaveJob(
                session.getUserId(),
                id,
                companyId,
                save,
                type
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){

                            if(response.body().getMsg().equalsIgnoreCase("job internship not saved")){
                                iSaved = false ;
                            }else {
                                iSaved = true ;
                            }

                        }


            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }


    private void setJobData() {
        Log.e("TAG", "setJobData() called Save Status "+data.getiSaved());

        if(data.getiApplied().equalsIgnoreCase("1")){
            binding.applyBtnNo.setVisibility(View.VISIBLE);
            Log.e("TAG", "setInternshipData() called"+data.getiApplied());

        }else {
            binding.applyBtn.setVisibility(View.VISIBLE);
        }


        if(!data.getCompany_image().equalsIgnoreCase("")){
            Picasso.get().load(USER_IMAGE+data.getCompany_image()).into(binding.comapnyLogo);
        }


        if(data.getCompanyAddress().equalsIgnoreCase("")){
            binding.locationText.setText("Indore");
        }else {
            binding.locationText.setText(data.getCompanyAddress());
        }

        binding.aboutCompany.setText("About " + data.getCompanyName());
        binding.aboutText.setText(data.getAbout());
        binding.companyNameText.setText(data.getCompanyName());

        binding.numberOfOpening.setText(data.getNumberOfOpening());
        binding.priceText.setText(data.getFixedPay() + " " + data.getCtcBreakup());
        binding.responsibilitesText.setText(data.getJobResponsibilities());
        binding.titleText.setText(data.getName());
        binding.typeText.setText("Job");
        binding.websiteText.setText(data.getWebsite());
        binding.postedDayText.setText(ago2.covertTimeToText(data.getCreatedDate()));
        binding.internshipDurationText.setVisibility(View.GONE);
        binding.applicantsText.setText(data.getCount() + " Applicants");
        List<String> skillsList = null;
        if (data.getSkills().size() != 0) {
            skillsList = new ArrayList<>();
            for (int i = 0; i < data.getSkills().size(); i++) {
                skillsList.add(data.getSkills().get(i).getSkillName());
            }
        }

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.skillsRecycler.setLayoutManager(layoutManager);
        binding.skillsRecycler.setAdapter(new SkillsParksAdapter(activity, skillsList));


        String[] parks = data.getPerks().split(",");
        List<String> parksList = Arrays.asList(parks);
        Log.e("TAG", "setInternshipData() called parksList" + parksList.toString());


        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(activity);
        layoutManager2.setFlexDirection(FlexDirection.ROW);
        layoutManager2.setJustifyContent(JustifyContent.FLEX_START);
        binding.parkesRecycler.setLayoutManager(layoutManager2);
        binding.parkesRecycler.setAdapter(new SkillsParksAdapter(activity, parksList));

        binding.applyBtn.setOnClickListener(view -> startActivity(new Intent(activity, ContinueApplicationActivity.class)
                .putExtra("type", "job")
                .putExtra("data", data)

        ));



        if(data.getiSaved().equalsIgnoreCase("1")){
            iSaved = true ;
            binding.saveBtn.setImageResource(R.drawable.ic_saved);
        }else {
            iSaved = false ;
            binding.saveBtn.setImageResource(R.drawable.ic_save);
        }

        binding.saveBtn.setOnClickListener(view -> {

            if(iSaved){
                iSaved = false ;
                binding.saveBtn.setImageResource(R.drawable.ic_save);
            }else {
                iSaved = true ;
                binding.saveBtn.setImageResource(R.drawable.ic_saved);
            }

            saveUnSaveJob("job", data.getCompanyId(), data.getId());
        });


    }


}