package com.koipsool_new.ui.resume;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityUpdateResumeJobBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.ResumeJobModel;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateResumeJobActivity extends AppCompatActivity {

    private  String type = "";
    private ResumeJobModel.ResumeJobData data ;
    private ActivityUpdateResumeJobBinding binding ;
    private  UpdateResumeJobActivity activity ;
    private Session session ;
    private ProgressDialog pd ;
    private String selectedWorkFromHome = "no";
    private String selectedCurrentlyWorking = "no";
    private String selectStartDate = "";
    private String selectEndDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateResumeJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        data = (ResumeJobModel.ResumeJobData) getIntent().getSerializableExtra("data");
        type = getIntent().getStringExtra("type");

        binding.jobProfile.setText(data.getJobProfile());
        binding.jobDescription.setText(data.getWorkDiscription());
        binding.jobLocation.setText(data.getLocation());
        binding.jobOrganization.setText(data.getOrganization());

        if(data.getCurrentlyWorking().equalsIgnoreCase("no")){
            binding.curenlyWorkingHere.setChecked(false);
            selectedCurrentlyWorking = "no";
        }else {
            binding.curenlyWorkingHere.setChecked(true);
            selectedCurrentlyWorking = "yes";
        }

        if(data.getCurrentlyWorking().equalsIgnoreCase("no")){
         binding.isworkfromhomeCheckbox.setChecked(false);
         selectedWorkFromHome = "no";
        }else {
            binding.isworkfromhomeCheckbox.setChecked(true);
            selectedWorkFromHome = "yes";
        }

        binding.idstartDateSpinner.setText(data.getStartDate());
        binding.endDateSpinner.setText(data.getEndDate());


        binding.saveJobDetails.setOnClickListener(view -> addJobDetails());


        binding.idstartDateSpinner.setOnClickListener(view -> selectDate(binding.idstartDateSpinner));

        binding.endDateSpinner.setOnClickListener(view -> selectEndDate(binding.endDateSpinner));



        binding.curenlyWorkingHere.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                selectedCurrentlyWorking = "yes";
                binding.endDateSpinner.setText("present");
                binding.endDateSpinner.setClickable(false);
                selectEndDate = "present";
            } else {
                selectedCurrentlyWorking = "no";
                binding.endDateSpinner.setText("Select End");
                binding.endDateSpinner.setClickable(true);
                selectEndDate = "";
            }

        });

        binding.isworkfromhomeCheckbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)  selectedWorkFromHome = "yes";
            else   selectedWorkFromHome = "no";
        });




    }


    private void addJobDetails() {
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.updateJobInternship(
                data.getId(),
                binding.jobProfile.getText().toString(),
                binding.jobOrganization.getText().toString(),
                selectedWorkFromHome,
                binding.jobLocation.getText().toString(),
                selectStartDate,
                selectEndDate,
                selectedCurrentlyWorking,
                binding.jobDescription.getText().toString(),
                type
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, type + " Added..!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private boolean isValidate() {

        if (binding.jobProfile.getText().toString().equalsIgnoreCase("")) {
            binding.jobProfile.setError("Enter Job Profile..!");
            binding.jobProfile.requestFocus();
            return false;
        } else if (binding.jobDescription.getText().toString().equalsIgnoreCase("")) {
            binding.jobDescription.setError("Enter Job Description..!");
            binding.jobDescription.requestFocus();
            return false;
        } else if (binding.jobLocation.getText().toString().equalsIgnoreCase("")) {
            binding.jobLocation.setError("Enter Job Location..!");
            binding.jobLocation.requestFocus();
            return false;
        } else if (selectEndDate.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select end date..!", Toast.LENGTH_SHORT).show();
            return false;

        } else if (selectStartDate.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select start date..!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void selectDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            String date = day1 + "-" + (month1 + 1) + "-" + year1;
            DateBtn.setText(date);
            selectStartDate = date;
        }, year, month, day);
        datePickerDialog.show();
    }

    private void selectEndDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            String date = day1 + "-" + (month1 + 1) + "-" + year1;
            DateBtn.setText(date);
            selectEndDate = date;
        }, year, month, day);
        datePickerDialog.show();
    }

}