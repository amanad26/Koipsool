package com.koipsool_new.ui.resume;

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
import com.koipsool_new.databinding.ActivityAddResumeProjectBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddResumeProjectActivity extends AppCompatActivity {

    private AddResumeProjectActivity activity;
    private ActivityAddResumeProjectBinding binding;
    private Session session;
    private String selectedStartDate = "", selectedEndDate = "", currentlyOngoing = "no";
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddResumeProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);


        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.addProject.setOnClickListener(view -> {
            if (isValidate()) addProject();
        });


        binding.currentOngoingCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                currentlyOngoing = "yes";
                selectedEndDate ="present";
                binding.endDateSpinner.setText("present");
                binding.endDateSpinner.setClickable(false);
            } else {
                currentlyOngoing = "no";
                selectedEndDate ="";
                binding.endDateSpinner.setText("");
                binding.endDateSpinner.setClickable(true);
            }
        });

        binding.endDateSpinner.setOnClickListener(view -> selectEndDate(binding.endDateSpinner));
        binding.startDateSpinner.setOnClickListener(view -> selectStartDate(binding.startDateSpinner));


    }

    private void addProject() {

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addMyProjects(
                session.getUserId(),
                binding.projectTitle.getText().toString(),
                selectedStartDate,
                selectedEndDate,
                currentlyOngoing,
                binding.projectDescription.getText().toString(),
                binding.projectLink.getText().toString()
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
              pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Project Saved..!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(activity, "Failed...!", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private boolean isValidate() {
        if (binding.projectTitle.getText().toString().equalsIgnoreCase("")) {
            binding.projectTitle.setError("Enter Project Name..!");
            binding.projectTitle.requestFocus();
            return false;
        } else if (binding.projectDescription.getText().toString().equalsIgnoreCase("")) {
            binding.projectDescription.setError("Enter Project Description..!");
            binding.projectDescription.requestFocus();
            return false;
        } else if (selectedStartDate.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select Start Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedEndDate.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select End Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (currentlyOngoing.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select Currently ongoing or not ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }

    private void selectStartDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            String date = day1 + "-" + (month1 + 1) + "-" + year1;
            DateBtn.setText(date);
            selectedStartDate = date;
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
            selectedEndDate = date;
        }, year, month, day);
        datePickerDialog.show();
    }


}