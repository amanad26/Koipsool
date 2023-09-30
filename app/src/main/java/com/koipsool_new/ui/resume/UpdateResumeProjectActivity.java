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
import com.koipsool_new.databinding.ActivityUpdateResumeProjectBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.MyResumeProjectsModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateResumeProjectActivity extends AppCompatActivity {

    private  UpdateResumeProjectActivity activity ;
    private ActivityUpdateResumeProjectBinding binding ;
    private Session session ;
    private MyResumeProjectsModel.ResumeProjectData data ;
    private String selectedStartDate = "", selectedEndDate = "", currentlyOngoing = "no";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateResumeProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this ;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        data = (MyResumeProjectsModel.ResumeProjectData) getIntent().getSerializableExtra("data");


        binding.projectTitle.setText(data.getTitle());
        binding.projectDescription.setText(data.getDiscription());
        binding.projectLink.setText(data.getProjectLink());
        selectedStartDate = data.getStartDate();
        selectedEndDate = data.getEndDate() ;

        binding.startDateSpinner.setText(data.getStartDate());
        String onGoing = data.getCurrentlyGoing();

        if(onGoing.equalsIgnoreCase("yes")){
            currentlyOngoing = "yes";
            binding.endDateSpinner.setText("present");
            binding.currentOngoingCheck.setChecked(true);
        }else {
            currentlyOngoing = "no";
            binding.startDateSpinner.setText(data.getStartDate());
            binding.currentOngoingCheck.setChecked(false);
        }

        binding.addProject.setOnClickListener(view -> updateProjectData());

        binding.endDateSpinner.setOnClickListener(view -> selectEndDate(binding.endDateSpinner));
        binding.startDateSpinner.setOnClickListener(view -> selectStartDate(binding.startDateSpinner));

    }


    private  void updateProjectData(){
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.updateMyProjects(
                data.getId(),
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
                            Toast.makeText(activity, "Project Updated..!", Toast.LENGTH_SHORT).show();
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