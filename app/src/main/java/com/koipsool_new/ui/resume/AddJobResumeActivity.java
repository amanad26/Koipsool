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
import com.koipsool_new.databinding.ActivityAddJobResumeBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddJobResumeActivity extends AppCompatActivity {

    private ActivityAddJobResumeBinding binding;
    private AddJobResumeActivity activity;
    private Session session;
    private ProgressDialog pd;
    private String selectedWorkFromHome = "no";
    private String selectedCurrentlyWorking = "no";
    private String selectStartDate = "";
    private String selectEndDate = "";
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddJobResumeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        type = getIntent().getStringExtra("type");
        if (type.equalsIgnoreCase("1")) type = "job";
        else type = "internship";

        if (type.equalsIgnoreCase("job")) {
            binding.title.setText("Add Job Details");
        } else {
            binding.title.setText("Add Internship Details");
        }

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


        binding.saveJobDetails.setOnClickListener(view -> {
            if (isValidate())
                addJobDetails();
        });

    }

    private void addJobDetails() {
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addJobInternship(
                session.getUserId(),
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