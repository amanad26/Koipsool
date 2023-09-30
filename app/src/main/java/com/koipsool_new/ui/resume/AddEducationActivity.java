package com.koipsool_new.ui.resume;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityAddEducationBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEducationActivity extends AppCompatActivity {

   private ActivityAddEducationBinding binding;
   private Session session;
   private AddEducationActivity activity;
   private String selectedClass = "", selectedPerformanceScale = "", selectedStartDate = "", selectedEndDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);


        setClassesAdapter();
        setPerformanceAdapter();

        binding.idstartDateSpinner.setOnClickListener(view -> selectDate(binding.idstartDateSpinner));
        binding.selectCompletionYear.setOnClickListener(view -> selectDate(binding.selectCompletionYear));
        binding.endDateSpinner.setOnClickListener(view -> selectEndDate(binding.endDateSpinner));


        ////// Education for College is not fully developed
        binding.saveEductionCollge.setOnClickListener(view -> {

            if (selectedClass.equalsIgnoreCase("")) {
                Toast.makeText(activity, "Select Education Level", Toast.LENGTH_SHORT).show();
            } else if (selectedPerformanceScale.equalsIgnoreCase("")) {
                Toast.makeText(activity, "Select Performance Scale", Toast.LENGTH_SHORT).show();
            } else if (selectedStartDate.equalsIgnoreCase("")) {
                Toast.makeText(activity, "Select Start year", Toast.LENGTH_SHORT).show();
            } else if (selectedEndDate.equalsIgnoreCase("")) {
                Toast.makeText(activity, "Select End year", Toast.LENGTH_SHORT).show();
            } else if (binding.saveEductionCollge.getText().toString().equalsIgnoreCase("")) {
                binding.saveEductionCollge.setError("Enter School Name..!");
                binding.saveEductionCollge.requestFocus();
            } else if (binding.educationDegree.getText().toString().equalsIgnoreCase("")) {
                binding.educationDegree.setError("Enter Your Degree..!");
                binding.educationDegree.requestFocus();
            } else if (binding.educationStream.getText().toString().equalsIgnoreCase("")) {
                binding.educationStream.setError("Enter Your Stream..!");
                binding.educationStream.requestFocus();
            } else if (binding.educationPerformance.getText().toString().equalsIgnoreCase("")) {
                binding.educationPerformance.setError("Enter Your Marks..!");
                binding.educationPerformance.requestFocus();
            } else {
                addEducationCollege();
            }

        });

        binding.addEducationSchool.setOnClickListener(view -> {
            if (selectedClass.equalsIgnoreCase("")) {
                Toast.makeText(activity, "Select Education Level", Toast.LENGTH_SHORT).show();
            } else if (selectedPerformanceScale.equalsIgnoreCase("")) {
                Toast.makeText(activity, "Select Performance Scale", Toast.LENGTH_SHORT).show();
            } else if (selectedStartDate.equalsIgnoreCase("")) {
                Toast.makeText(activity, "Select Completion year", Toast.LENGTH_SHORT).show();
            }
            else if (binding.educationBoard.getText().toString().equalsIgnoreCase("")) {
                binding.educationBoard.setError("Enter Board Name..!");
                binding.educationBoard.requestFocus();
            }else if (binding.educationSchoolName.getText().toString().equalsIgnoreCase("")) {
                binding.educationSchoolName.setError("Enter School Name..!");
                binding.educationSchoolName.requestFocus();
            } else if (binding.educationPerformanceSchool.getText().toString().equalsIgnoreCase("")) {
                binding.educationPerformanceSchool.setError("Enter Your Marks..!");
                binding.educationPerformanceSchool.requestFocus();
            } else {
                addSchoolEducation();
            }
        });


    }

    private void addEducationCollege() {
        Log.e("TAG", "addEducationCollege() called  College ");

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addEducation(
                session.getUserId(),
                selectedClass,
                binding.educationSchoolCollege.getText().toString(),
                selectedStartDate,
                selectedEndDate,
                binding.educationDegree.getText().toString(),
                binding.educationStream.getText().toString(),
                selectedPerformanceScale,
                binding.educationPerformance.getText().toString(),
                ""
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Saved.!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(activity, "Failed..", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void addSchoolEducation() {
        Log.e("TAG", "addSchoolEducation() called  School ");


        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addEducation(
                session.getUserId(),
                selectedClass,
                binding.educationSchoolName.getText().toString(),
                selectedStartDate,
                "",
                "",
                "",
                selectedPerformanceScale,
                binding.educationPerformanceSchool.getText().toString(),
                binding.educationBoard.getText().toString()
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Saved.!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(activity, "Failed..", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void selectDate(TextView DateBtn) {
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

    private void setPerformanceAdapter() {

        List<String> performanceList = new ArrayList<>();
        performanceList.add("Percentage");
        performanceList.add("CGPA(/10)");
        performanceList.add("CGPA(/7)");
        performanceList.add("CGPA(/5)");
        performanceList.add("CGPA(/4)");

        ArrayAdapter adb = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, performanceList);
        adb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.performanceScale.setAdapter(adb);
        binding.performanceScale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPerformanceScale = performanceList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.performanceScaleSchool.setAdapter(adb);
        binding.performanceScaleSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPerformanceScale = performanceList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setClassesAdapter() {

        List<String> classesList = new ArrayList<>();
        classesList.add("10th");
        classesList.add("12th");
        classesList.add("Graduation");
        classesList.add("Post Graduation");
        classesList.add("Diploma Details");

        ArrayAdapter adb = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, classesList);
        adb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.classSpinner.setAdapter(adb);
        binding.classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedClass = classesList.get(i);
                if (i == 0 || i == 1) {
                    binding.educationCollegeLinear.setVisibility(View.GONE);
                    binding.educationSchoolLinear.setVisibility(View.VISIBLE);
                } else {
                    binding.educationCollegeLinear.setVisibility(View.VISIBLE);
                    binding.educationSchoolLinear.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}