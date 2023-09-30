package com.koipsool_new.ui.pharma;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityPharmaHomeAllDetailsBinding;
import com.koipsool_new.kapsoolAdapters.SkillsParksAdapter;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.MyJobsModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmaHomeAllDetailsActivity extends AppCompatActivity {

  private MyJobsModel.MyJobInternshipData.PostInternship internshipData ;
  private MyJobsModel.MyJobInternshipData.PostJob jobData ;
  private ActivityPharmaHomeAllDetailsBinding binding ;
  private PharmaHomeAllDetailsActivity activity ;
  private Session session ;
  private String type = "0";
  private  TimeAgo2 ago2 ;
  private  boolean iSaved = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPharmaHomeAllDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity  = this ;
        session = new Session(activity);
        ago2 = new TimeAgo2();

        type = getIntent().getStringExtra("type");
        if(type.equalsIgnoreCase("1") && !type.equalsIgnoreCase("0")){
            jobData = (MyJobsModel.MyJobInternshipData.PostJob) getIntent().getSerializableExtra("data");
            setJobData();
        }else {
            internshipData = (MyJobsModel.MyJobInternshipData.PostInternship) getIntent().getSerializableExtra("data");
           setInternshipData();
        }

        binding.AGHeaders.setOnClickListener(view -> onBackPressed());
    }






    private void setInternshipData() {
        Log.e(TAG, "setInternshipData() called"+internshipData.getCount());
        //Log.e(TAG, "setInternshipData() called saved status "+internshipData.getiSaved());
        binding.duLinaer.setVisibility(View.VISIBLE);
        binding.aboutText.setText(internshipData.getAbout());
        binding.companyNameText.setText(internshipData.getCompanyName());
        binding.locationText.setText(internshipData.getCompanyAddress());
        binding.numberOfOpening.setText(internshipData.getNumberOfOpening());
        binding.priceText.setText(internshipData.getPriceFrom());
        binding.aboutCompany.setText("About "+internshipData.getCompanyName());
        binding.responsibilitesText.setText(internshipData.getInternshipResponsibilities());
        binding.titleText.setText(internshipData.getInternshipTitle());
        binding.internshipStartText.setText(internshipData.getInternshipStartDate());
        binding.internshipDurationText.setText(internshipData.getInternshipDuration());
        binding.websiteText.setText(internshipData.getWebsites());
        binding.applicantsText.setText(internshipData.getCount() + " Applicants");
        binding.typeText.setText("Internship");
        binding.postedDayText.setText(ago2.covertTimeToText(internshipData.getCreatedDate()));

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
        binding.skillsRecycler.setAdapter(new SkillsParksAdapter(activity,skillsList));



        String[] parks = internshipData.getPerks().split(",");
        List<String> parksList = Arrays.asList(parks);
        Log.e("TAG", "setInternshipData() called parksList"+parksList.toString());


        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(activity);
        layoutManager2.setFlexDirection(FlexDirection.ROW);
        layoutManager2.setJustifyContent(JustifyContent.FLEX_START);
        binding.parkesRecycler.setLayoutManager(layoutManager2);
        binding.parkesRecycler.setAdapter(new SkillsParksAdapter(activity,parksList));

        binding.deleteJobInternship.setOnClickListener(view -> deleteInternshipDialog(internshipData.getId()));

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
        Log.e(TAG, "setInternshipData() called"+jobData.getCount());
        //Log.e(TAG, "setJobData() called saved status "+internshipData.getiSaved());


        if(jobData.getCompanyAddress().equalsIgnoreCase("")){
            binding.locationText.setText("Indore");
        }else {
            binding.locationText.setText(jobData.getCompanyAddress());
        }

        binding.aboutCompany.setText("About "+jobData.getCompanyName());
        binding.aboutText.setText(jobData.getAbout());
        binding.companyNameText.setText(jobData.getCompanyName());

        binding.numberOfOpening.setText(jobData.getNumberOfOpening());
        binding.priceText.setText(jobData.getFixedPay() + " "+jobData.getCtcBreakup() );
        binding.responsibilitesText.setText(jobData.getJobResponsibilities());
        binding.titleText.setText(jobData.getName());
        binding.typeText.setText("Job");
        binding.websiteText.setText(jobData.getWebsite());
        binding.postedDayText.setText(ago2.covertTimeToText(jobData.getCreatedDate()));
        binding.internshipDurationText.setVisibility(View.GONE);
        binding.applicantsText.setText(jobData.getCount() + " Applicants");
        List<String> skillsList = null;
        if (jobData.getSkills().size() != 0) {
            skillsList = new ArrayList<>();
            for (int i = 0; i < jobData.getSkills().size(); i++) {
                skillsList.add(jobData.getSkills().get(i).getSkillName());
            }
        }


        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.skillsRecycler.setLayoutManager(layoutManager);
        binding.skillsRecycler.setAdapter(new SkillsParksAdapter(activity,skillsList));


        String[] parks = jobData.getPerks().split(",");
        List<String> parksList = Arrays.asList(parks);
        Log.e("TAG", "setInternshipData() called parksList"+parksList.toString());


        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(activity);
        layoutManager2.setFlexDirection(FlexDirection.ROW);
        layoutManager2.setJustifyContent(JustifyContent.FLEX_START);
        binding.parkesRecycler.setLayoutManager(layoutManager2);
        binding.parkesRecycler.setAdapter(new SkillsParksAdapter(activity,parksList));

        binding.deleteJobInternship.setOnClickListener(view -> deleteJobDialog(jobData.getId()));
        binding.updateJob.setOnClickListener(view -> startActivity(new Intent(activity,UpdateJobdetailsActivity.class)
                .putExtra("data", jobData)));


      binding.saveBtn.setOnClickListener(view -> {

          if(iSaved){
              iSaved = false ;
              binding.saveBtn.setImageResource(R.drawable.ic_save);
          }else {
              iSaved = true ;
              binding.saveBtn.setImageResource(R.drawable.ic_saved);
          }

          saveUnSaveJob("job", jobData.getCompanyId(), jobData.getId());
      });

    }


    private void  deleteJobDialog(String postId){

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(activity);
        builder.setMessage("Do you want to delete this Job Post?") .setTitle("Delete Job");

        builder.setMessage("Do you want to delete this Job Post?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    ProgressDialog pd = new ProgressDialog(activity);
                    pd.show();

                    ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
                    apiInterfaceKapsool.deletePostJob(postId).enqueue(new Callback<LogoutModel>() {
                        @Override
                        public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                         pd.dismiss();
                            if(response.code() == 200 )
                                if(response.body() != null)
                                    if(response.body().getResult().equalsIgnoreCase("true")){
                                        Toast.makeText(activity, "Job Deleted...!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(activity, "Failed...!", Toast.LENGTH_SHORT).show();
                                    }

                        }

                        @Override
                        public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                            pd.dismiss();
                            Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                        }
                    });

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("AlertDialogExample");
        alert.show();


    }

    private void  deleteInternshipDialog(String postId){

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(activity);
        builder.setMessage("Do you want to delete this Internship Post?") .setTitle("Delete Internship");

        builder.setMessage("Do you want to delete this Internship Post? ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    ProgressDialog pd = new ProgressDialog(activity);
                    pd.show();

                    ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
                    apiInterfaceKapsool.deletePostInternship(postId).enqueue(new Callback<LogoutModel>() {
                        @Override
                        public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                         pd.dismiss();
                            if(response.code() == 200 )
                                if(response.body() != null)
                                    if(response.body().getResult().equalsIgnoreCase("true")){
                                        Toast.makeText(activity, "Internship Deleted...!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(activity, "Failed...!", Toast.LENGTH_SHORT).show();
                                    }

                        }

                        @Override
                        public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                            pd.dismiss();
                            Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                        }
                    });

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("AlertDialogExample");
        alert.show();


    }

    public class TimeAgo2 {

        public String covertTimeToText(String dataDate) {

            String convTime = null;

            String prefix = "";
            String suffix = "Ago";

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date pasTime = dateFormat.parse(dataDate);

                Date nowTime = new Date();

                long dateDiff = nowTime.getTime() - pasTime.getTime();

                long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
                long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
                long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
                long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

                if (second < 60) {
                    convTime = second + " Seconds " + suffix;
                } else if (minute < 60) {
                    convTime = minute + " Minutes " + suffix;
                } else if (hour < 24) {
                    convTime = hour + " Hours " + suffix;
                } else if (day >= 7) {
                    if (day > 360) {
                        convTime = (day / 360) + " Years " + suffix;
                    } else if (day > 30) {
                        convTime = (day / 30) + " Months " + suffix;
                    } else {
                        convTime = (day / 7) + " Week " + suffix;
                    }
                } else if (day < 7) {
                    convTime = day + " Days " + suffix;
                }

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("ConvTimeE", e.getMessage());
            }

            return convTime;
        }

    }

}