package com.koipsool_new.ui.resume;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityUpdateEducationBinding;
import com.koipsool_new.kapsoolModels.EducationModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateEducationActivity extends AppCompatActivity {

    ActivityUpdateEducationBinding binding ;
    UpdateEducationActivity activity ;
    Session session ;
    private String selectedStartDate = "";
    private String selectedEndDate = "";
    private String selectedPerformanceScale = "";
    private String selectedClass = "";
    private EducationModel.EducationData data = null;
    private boolean isSchool = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this ;
        session = new Session(activity);



         data = (EducationModel.EducationData) getIntent().getSerializableExtra("data");


         setClassesAdapter();

        Log.e(TAG, "onCreate() called with: type = [" + data.getEducation() + "]");
        if(data.getEducation().equalsIgnoreCase("10th") || data.getEducation().equalsIgnoreCase("12th")){
             binding.educationSchoolLinear.setVisibility(View.VISIBLE);
            setSchoolData();
            selectedClass = data.getEducation();
            Log.e(TAG, "onCreate() called with: If  = [" + data.getEducation().equalsIgnoreCase("10th") + "]");
            isSchool = true;
        }else {
            binding.educationCollegeLinear.setVisibility(View.VISIBLE);
            setCollegeData();
            Log.e(TAG, "onCreate() called with: Else  = [" + data.getEducation().equalsIgnoreCase("10th") + "]");

        }
        setPerformanceAdapter();


        binding.idstartDateSpinner.setOnClickListener(view -> selectDate(binding.idstartDateSpinner));
        binding.selectCompletionYear.setOnClickListener(view -> selectDate(binding.selectCompletionYear));
        binding.endDateSpinner.setOnClickListener(view -> selectEndDate(binding.endDateSpinner));


        binding.addEducationSchool.setOnClickListener(view -> addSchoolEducation());


        binding.saveEductionCollge.setOnClickListener(view -> {
            addEducationCollege();
        });


    }


    private void addEducationCollege() {
        Log.e("TAG", "addEducationCollege() called  College ");

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.UpdateEducation(
                data.getId(),
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
        apiInterfaceKapsool.UpdateEducation(
                data.getId(),
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


    private void setCollegeData() {

        Log.e("TAG", "setCollegeData() called");
        binding.educationSchoolCollege.setText(data.getSchoolCollage());
        binding.educationPerformance.setText(data.getPerformance());
        binding.idstartDateSpinner.setText(data.getStartDate());
        binding.endDateSpinner.setText(data.getEndDate());
        binding.educationStream.setText(data.getStream());
        binding.educationDegree.setText(data.getDegree());

        selectedPerformanceScale = data.getPerformanceScale();
        selectedClass = data.getEducation() ;
    }

    private void setSchoolData() {

        binding.educationSchoolName.setText(data.getSchoolCollage());
        binding.educationBoard.setText(data.getBoard());
        binding.selectCompletionYear.setText(data.getStartDate());
        binding.educationPerformanceSchool.setText(data.getPerformance());
        selectedPerformanceScale = data.getPerformanceScale();
        selectedClass = data.getEducation() ;

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

        selectedPerformanceScale = data.getPerformanceScale();
        for (int i = 0; i < performanceList.size(); i++) {
            if(performanceList.get(i).equalsIgnoreCase(selectedPerformanceScale)) {

              if(isSchool)
                binding.performanceScaleSchool.setSelection(i);
              else
                binding.performanceScale.setSelection(i);
            }
        }

    }
//
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        selectedClass = data.getEducation();
        for (int i = 0; i < classesList.size(); i++) {
            if(selectedClass.equalsIgnoreCase(classesList.get(i)))
                binding.classSpinner.setSelection(i);
        }



    }
}