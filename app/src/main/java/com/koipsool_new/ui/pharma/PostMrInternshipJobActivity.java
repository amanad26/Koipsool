package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.R;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityPostMrInternshipJobBinding;
import com.koipsool_new.kapsoolAdapters.SelectedSkillAdapter;
import com.koipsool_new.kapsoolAdapters.SkillsAdapter;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.SkillsInterface;
import com.koipsool_new.kapsoolModels.SkillsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostMrInternshipJobActivity extends AppCompatActivity implements SkillsInterface {

    private ActivityPostMrInternshipJobBinding binding;
    private Activity activity;
    private Session session;
    private int internshipQuestions = 2 ;
    private int jobQuestions = 2 ;

    private ArrayList<String> arrLanguages = new ArrayList<>();
    private ArrayList<String> arrId = new ArrayList<>();
    private ArrayList<String> selectedJobSkills = new ArrayList<>();
    private ArrayList<String> selectedJobSkillsIds = new ArrayList<>();
    private List<SkillsModel.SkillsData> list = new ArrayList<>();
    private SelectedSkillAdapter adapter;
    private  String selectedExperience = "Fresher";
    private ArrayList<String> selectedJobParks = new ArrayList<>();
    private ArrayList<String> selectedInternshipParks = new ArrayList<>();
    private String selected_job_type = "in-office", selected_ctc_type = "", seleced_pop = "", statPrice = "", endPrice = "";

    private String selectedInternshipType = "in-office", partTimeAllowed = "no", internshipStartDate = "", selectedDuration = "", allowWomen = "no", selectedStipend = "fixed", selectedInternshipPop = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostMrInternshipJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = PostMrInternshipJobActivity.this;

        session = new Session(activity);
        getSkillsList();

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.layoutInternship.setVisibility(View.VISIBLE);
        binding.layoutJob.setVisibility(View.GONE);

        binding.radioInternship.setChecked(true);

        binding.radioInternship.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                selectedJobSkillsIds.clear();
                binding.radioInternship.setChecked(true);
                binding.radioJob.setChecked(false);
                binding.layoutInternship.setVisibility(View.VISIBLE);
                binding.layoutJob.setVisibility(View.GONE);
            }
        });


        binding.postInternship.setOnClickListener(view -> {
//            Intent intent = new Intent(PostMrInternshipJobActivity.this, ViewinternshipDetailActivity.class);
//            startActivity(intent);
            checkAndPostInternship();
        });


        binding.radioJob.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.radioJob.setChecked(true);
                selectedJobSkillsIds.clear();
                binding.radioInternship.setChecked(false);
                binding.layoutJob.setVisibility(View.VISIBLE);
                binding.layoutInternship.setVisibility(View.GONE);
            }
        });


        binding.radioFixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectedStipend = "fixed";
                    binding.radioNegotiable.setChecked(false);
                    binding.radioUnpaid.setChecked(false);
                    binding.radioPerfomanceBased.setChecked(false);
                    binding.linearFixed.setVisibility(View.VISIBLE);
                    binding.linaerNegotiable.setVisibility(View.GONE);
                    binding.linearUnpaid.setVisibility(View.GONE);
                    binding.linaerPerformanceBased.setVisibility(View.GONE);
                }
            }
        });


        binding.radioNegotiable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectedStipend = "negotiable";
                    binding.radioFixed.setChecked(false);
                    binding.radioUnpaid.setChecked(false);
                    binding.radioPerfomanceBased.setChecked(false);
                    binding.linearFixed.setVisibility(View.GONE);
                    binding.linaerNegotiable.setVisibility(View.VISIBLE);
                    binding.linearUnpaid.setVisibility(View.GONE);
                    binding.linaerPerformanceBased.setVisibility(View.GONE);
                }
            }
        });


        binding.radioUnpaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectedStipend = "unpaid";
                    binding.radioNegotiable.setChecked(false);
                    binding.radioFixed.setChecked(false);
                    binding.radioPerfomanceBased.setChecked(false);
                    binding.linearFixed.setVisibility(View.GONE);
                    binding.linaerNegotiable.setVisibility(View.GONE);
                    binding.linearUnpaid.setVisibility(View.VISIBLE);
                    binding.linaerPerformanceBased.setVisibility(View.GONE);
                }
            }
        });

        binding.radioPerfomanceBased.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectedStipend = "performance_based";
                    binding.radioNegotiable.setChecked(false);
                    binding.radioFixed.setChecked(false);
                    binding.radioUnpaid.setChecked(false);
                    binding.linearFixed.setVisibility(View.GONE);
                    binding.linaerNegotiable.setVisibility(View.GONE);
                    binding.linearUnpaid.setVisibility(View.GONE);
                    binding.linaerPerformanceBased.setVisibility(View.VISIBLE);
                }
            }
        });


        //////////////////////////////////////////////////////////////////////////////////
        ///////////////////////  Post  Job Section


        setExperiecneFirJob();


        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(activity);
        layoutManager2.setFlexDirection(FlexDirection.ROW);
        layoutManager2.setJustifyContent(JustifyContent.FLEX_START);

        adapter = new SelectedSkillAdapter(activity, selectedJobSkills);
        binding.selectedSkillsRecycler.setLayoutManager(layoutManager2);
        binding.selectedSkillsRecycler.setAdapter(adapter);


        binding.postJob.setOnClickListener(view -> checkAndPostJob());


        binding.jobSkillsReq.setOnClickListener(view -> showSkillsDialog());


        binding.jobTypeRadio.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            if (radioButton.getText().toString().equalsIgnoreCase("in Office"))
                selected_job_type = "office";

            else selected_job_type = "remote";
        });

        binding.jobRadioGounpCtc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                if (radioButton.getText().toString().equalsIgnoreCase("in %"))
                    selected_ctc_type = " %";
                else selected_ctc_type = " LPA";

            }
        });

        binding.jobPopCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.popLinarJob.setVisibility(View.VISIBLE);
                    seleced_pop = "yes";
                } else {
                    binding.popLinarJob.setVisibility(View.GONE);
                    seleced_pop = "no";
                }
            }
        });

        binding.job5dayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) selectedJobParks.add(binding.job5dayCheck.getText().toString());
                else selectedJobParks.remove(binding.job5dayCheck.getText().toString());
            }
        });

        binding.jobHealthChackbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) selectedJobParks.add(binding.jobHealthChackbox.getText().toString());
                else selectedJobParks.remove(binding.jobHealthChackbox.getText().toString());
            }
        });

        binding.jobLifeinsuranceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) selectedJobParks.add(binding.jobLifeinsuranceCheck.getText().toString());
                else selectedJobParks.remove(binding.jobLifeinsuranceCheck.getText().toString());
            }
        });

        binding.addJobQuestions.setOnClickListener(view -> {
            if(jobQuestions == 0) {
                binding.questionsJobLinear.setVisibility(View.VISIBLE);
                binding.jobQuesttion1.setVisibility(View.VISIBLE);
                jobQuestions = jobQuestions+1  ;
            }else if(jobQuestions == 1){
                binding.jobQuesttion2.setVisibility(View.VISIBLE);
                jobQuestions = jobQuestions+1  ;
            }else if(jobQuestions == 2){
                binding.jobQuesttion3.setVisibility(View.VISIBLE);
                jobQuestions = jobQuestions+1  ;
            }else if(jobQuestions == 3){
                binding.jobQuesttion4.setVisibility(View.VISIBLE);
                jobQuestions = jobQuestions+1  ;
            }else if(jobQuestions == 4){
                binding.jobQuesttion5.setVisibility(View.VISIBLE);
                jobQuestions = jobQuestions+1  ;
            }else{
                binding.addJobQuestions.setText("You can add maximum 5 questions");
            }
        });


        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////     Post Internship Data


        FlexboxLayoutManager layoutManager22 = new FlexboxLayoutManager(activity);
        layoutManager22.setFlexDirection(FlexDirection.ROW);
        layoutManager22.setJustifyContent(JustifyContent.FLEX_START);

        adapter = new SelectedSkillAdapter(activity, selectedJobSkills);
        binding.selectedSkillsRecyclerIntern.setLayoutManager(layoutManager22);
        binding.selectedSkillsRecyclerIntern.setAdapter(adapter);

        binding.internSkillsReq.setOnClickListener(view -> showSkillsDialogForInterns());


        binding.internTypeRadio.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

            if (radioButton.getText().toString().equalsIgnoreCase("Remote")) {
                selectedInternshipType = "remote";
            } else {
                selectedInternshipType = "in-office";
            }

        });


        binding.internPartTimeSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) partTimeAllowed = "yes";
            else partTimeAllowed = "no";
        });

        binding.internStatDateRadioG.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

            if (radioButton.getText().toString().equalsIgnoreCase("Later")) {
                internshipStartDate = "latter";
            } else {
                internshipStartDate = "immediately";
            }
        });

        arrId.add("1 Months");
        arrId.add("2 Months");
        arrId.add("3 Months");
        arrId.add("4 Months");
        arrId.add("5 Months");
        arrId.add("6 Months");
        arrId.add("7 Months");
        arrId.add("8 Months");
        arrId.add("9 Months");
        arrId.add("10 Months");
        arrId.add("11 Months");
        arrId.add("12 Months");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrId);
        binding.monthsSpinner.setAdapter(spinnerAdapter);

        binding.monthsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDuration = arrId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.internFixedCertificate.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) selectedInternshipParks.add(binding.internFixedCertificate.getText().toString());
            else
                selectedInternshipParks.remove(binding.internFixedCertificate.getText().toString());

        });


        binding.internFixedWorkHours.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) selectedInternshipParks.add(binding.internFixedWorkHours.getText().toString());
            else selectedInternshipParks.remove(binding.internFixedWorkHours.getText().toString());

        });

        binding.internFixedLetterOfRecomndeation.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                selectedInternshipParks.add(binding.internFixedLetterOfRecomndeation.getText().toString());
            else
                selectedInternshipParks.remove(binding.internFixedLetterOfRecomndeation.getText().toString());

        });

        binding.internFixed5dayWork.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) selectedInternshipParks.add(binding.internFixed5dayWork.getText().toString());
            else selectedInternshipParks.remove(binding.internFixed5dayWork.getText().toString());

        });


        binding.internFixedInformalDresscode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                selectedInternshipParks.add(binding.internFixedInformalDresscode.getText().toString());
            else
                selectedInternshipParks.remove(binding.internFixedInformalDresscode.getText().toString());

        });

        binding.internFixedFreeSnacks.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) selectedInternshipParks.add(binding.internFixedFreeSnacks.getText().toString());
            else selectedInternshipParks.remove(binding.internFixedFreeSnacks.getText().toString());

        });

        binding.internFixedPop.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.internshipPopmonths.setVisibility(View.VISIBLE);
                selectedInternshipPop = "yes";
            } else {
                binding.internshipPopmonths.setVisibility(View.GONE);
                selectedInternshipPop = "no";
            }
        });


        binding.internAllowWomen.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) allowWomen = "yes";
            else allowWomen = "no";
        });



        binding.addQuestionInternship.setOnClickListener(view -> {
            if(internshipQuestions == 0) {
                binding.questionsInternshipLinear.setVisibility(View.VISIBLE);
                binding.internshipQuesttion1.setVisibility(View.VISIBLE);
                internshipQuestions = internshipQuestions+1  ;
            }else if(internshipQuestions == 1){
                binding.internshipQuesttion2.setVisibility(View.VISIBLE);
                internshipQuestions = internshipQuestions+1  ;
            }else if(internshipQuestions == 2){
                binding.internshipQuesttion3.setVisibility(View.VISIBLE);
                internshipQuestions = internshipQuestions+1  ;
            }else if(internshipQuestions == 3){
                binding.internshipQuesttion4.setVisibility(View.VISIBLE);
                internshipQuestions = internshipQuestions+1  ;
            }else if(internshipQuestions == 4){
                binding.internshipQuesttion5.setVisibility(View.VISIBLE);
                internshipQuestions = internshipQuestions+1  ;
            }else {
                binding.addQuestionInternship.setText("You can add maximum 5 questions");
            }
        });


    }

    private void setExperiecneFirJob() {

        ArrayList<String> experienceList = new ArrayList<>();
        experienceList.add("Fresher");
        experienceList.add("3 Months");
        experienceList.add("6 Months");
        experienceList.add("9 Months");
        experienceList.add("1 Year");
        experienceList.add("1.5 Year");
        experienceList.add("2 Year");
        experienceList.add("2.5 Year");
        experienceList.add("3 Year");
        experienceList.add("3.5 Year");
        experienceList.add("4 Year");
        experienceList.add("4.5 Year");
        experienceList.add("5 Year");
        experienceList.add("5 Year+");


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,experienceList);
        binding.jobExperianceSpinner.setAdapter(spinnerAdapter);

        binding.jobExperianceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedExperience = experienceList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    private void checkAndPostJob() {

        if (selectedJobSkillsIds.size() == 0) {
            Toast.makeText(activity, "Minimum one skill required..!", Toast.LENGTH_SHORT).show();
        } else if (binding.postJobTitle.getText().toString().equalsIgnoreCase("")) {
            binding.postJobTitle.setError("Enter Job Title..!");
            binding.postJobTitle.requestFocus();
        } else if (selected_job_type.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select Job Type", Toast.LENGTH_SHORT).show();
        } else if (selected_ctc_type.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select CTC Type", Toast.LENGTH_SHORT).show();
        } else if (binding.postJobNumberOfOpening.getText().toString().equalsIgnoreCase("")) {
            binding.postJobNumberOfOpening.setError("No Of opening ..!");
            binding.postJobNumberOfOpening.requestFocus();
        } else if (binding.postJobResposibility.getText().toString().equalsIgnoreCase("")) {
            binding.postJobResposibility.setError("Job responsibilities ..!");
            binding.postJobResposibility.requestFocus();
        } else if (binding.jobFixedPay.getText().toString().equalsIgnoreCase("")) {
            binding.jobFixedPay.setError("Fixed Pay Amount ..!");
            binding.jobFixedPay.requestFocus();
        } else {

            if (seleced_pop.equalsIgnoreCase("yes")) {
                if (binding.jobPopMonths.getText().toString().equalsIgnoreCase("")) {
                    binding.jobPopMonths.setError("Enter POP Months..!");
                    binding.jobPopMonths.requestFocus();
                } else {
                    addJobPost();
                }
            } else {
                addJobPost();
            }

        }

    }

    private void checkAndPostInternship() {

        if (selectedJobSkillsIds.size() == 0) {
            Toast.makeText(activity, "Minimum one skill required..!", Toast.LENGTH_SHORT).show();
        } else if (binding.internTitle.getText().toString().equalsIgnoreCase("")) {
            binding.internTitle.setError("Enter Internship Title..!");
            binding.internTitle.requestFocus();
        } else if (binding.internNumberOfOpening.getText().toString().equalsIgnoreCase("")) {
            binding.internNumberOfOpening.setError("Enter Number Of ");
        } else if (binding.internCity.getText().toString().equalsIgnoreCase("")) {
            binding.internCity.setError("Enter City");
            binding.internCity.requestFocus();
        } else if (binding.internResposibilityDescription.getText().toString().equalsIgnoreCase("")) {
            binding.internResposibilityDescription.setError("Enter day to day resposbilities");
            binding.internResposibilityDescription.requestFocus();
        } else if (internshipStartDate.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select Internship start date", Toast.LENGTH_SHORT).show();
        } else if (selectedDuration.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select Internship Duration", Toast.LENGTH_SHORT).show();
        } else if (selectedStipend.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select Stipend..!", Toast.LENGTH_SHORT).show();
        } else addInternship();


    }

    private void addInternship() {

        Log.e("TAG", "addInternship() called User id " + session.getUserId());
        Log.e("TAG", "addInternship() called Title " + binding.internTitle.getText().toString());
        Log.e("TAG", "addInternship() called Job skills " + selectedJobSkillsIds.toString().replaceAll("\\[", "").replace("]", ""));
        Log.e("TAG", "addInternship() called selectedInternshipType " + selectedInternshipType);
        Log.e("TAG", "addInternship() called partTimeAllowed " + partTimeAllowed);
        Log.e("TAG", "addInternship() called City " + binding.internCity.getText().toString());
        Log.e("TAG", "addInternship() called Number of opening " + binding.internNumberOfOpening.getText().toString());
        Log.e("TAG", "addInternship() called internshipStartDate " + internshipStartDate);
        Log.e("TAG", "addInternship() called selectedDuration " + selectedDuration);
        Log.e("TAG", "addInternship() called responsibility " + binding.internResposibilityDescription.getText().toString());
        Log.e("TAG", "addInternship() called selectedInternshipPop " + selectedInternshipPop);
        Log.e("TAG", "addInternship() called Min Assure " + binding.internPerfomanceMinAssure.getText().toString());
        Log.e("TAG", "addInternship() called Max Assure " + binding.internPerfomanceMaxAssure.getText().toString());
        Log.e("TAG", "addInternship() called selectedStipend " + selectedStipend);
        Log.e("TAG", "addInternship() called Selected Parks " + selectedInternshipParks.toString().replaceAll("\\[", "").replace("]", ""));
        Log.e("TAG", "addInternship() called Start Price " + binding.internPriceStart1.getText().toString());
        Log.e("TAG", "addInternship() called End Price " + binding.internNegotiableEndPrice.getText().toString());
        Log.e("TAG", "addInternship() called Allow Women " + allowWomen);
        Log.e("TAG", "addInternship() called About " + binding.internAbout.getText().toString());
        Log.e("TAG", "addInternship() called Pop " + binding.internshipPopmonths.getText().toString());


        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addInternshipPost(
                session.getUserId(),
                binding.internTitle.getText().toString(),
                selectedJobSkillsIds.toString().replaceAll("\\[", "").replace("]", ""),
                selectedInternshipType,
                partTimeAllowed,
                binding.internCity.getText().toString(),
                binding.internNumberOfOpening.getText().toString(),
                internshipStartDate,
                selectedDuration,
                binding.internResposibilityDescription.getText().toString(),
                selectedInternshipPop,
                binding.internWebsite.getText().toString(),
                binding.internPerfomanceMinAssure.getText().toString(),
                binding.internPerfomanceMaxAssure.getText().toString(),
                "0",
                selectedStipend,
                selectedInternshipParks.toString().replaceAll("\\[", "").replace("]", ""),
                binding.internAbout.getText().toString(),
                "0",
                binding.internPriceStart1.getText().toString(),
                binding.internNegotiableEndPrice.getText().toString(),
                allowWomen,
                binding.internshipPopmonths.getText().toString(),
                binding.internshipQuesttion1.getText().toString(),
                binding.internshipQuesttion2.getText().toString(),
                binding.internshipQuesttion3.getText().toString(),
                binding.internshipQuesttion4.getText().toString(),
                binding.internshipQuesttion5.getText().toString(),
                "0"
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                progressDialog.dismiss();

                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }

    private void addJobPost() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        statPrice = binding.postJobSaleryStart.getText().toString();
        endPrice = binding.postJobSaleryEnd.getText().toString();

        String variablePay = "", otherIncentive = "";
        variablePay = binding.jobVariablePay.getText().toString();
        otherIncentive = binding.jobOtherInsentive.getText().toString();


        Log.e("TAG", "addJobPost() called Userid " + session.getUserId());
        Log.e("TAG", "addJobPost() called selected_job_type " + selected_job_type);
        Log.e("TAG", "addJobPost() called postJobNumberOfOpening " + binding.postJobNumberOfOpening.getText().toString());
        Log.e("TAG", "addJobPost() called postJobResposibility " + binding.postJobResposibility.getText().toString());
        Log.e("TAG", "addJobPost() called selectedJobSkillsIds " + selectedJobSkillsIds.toString().replaceAll("\\[", "").replace("]", ""));
        Log.e("TAG", "addJobPost() called selected_ctc_type " + selected_ctc_type);
        Log.e("TAG", "addJobPost() called jobFixedPay " + binding.jobFixedPay.getText().toString());
        Log.e("TAG", "addJobPost() called variablePay " + variablePay);
        Log.e("TAG", "addJobPost() called otherIncentive " + otherIncentive);
        Log.e("TAG", "addJobPost() called seleced_pop " + seleced_pop);
        Log.e("TAG", "addJobPost() called jobPopMonths " + binding.jobPopMonths.getText().toString());
        Log.e("TAG", "addJobPost() called postJobTitle " + binding.postJobTitle.getText().toString());
        Log.e("TAG", "addJobPost() called selectedJobParks " + selectedJobParks.toString().replaceAll("\\[", "").replace("]", ""));
        Log.e("TAG", "addJobPost() called postJobAbout " + binding.postJobAbout.getText().toString());

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addPharmacyPostJob(
                session.getUserId(),
                selected_job_type,
                binding.postJobNumberOfOpening.getText().toString(),
                binding.postJobResposibility.getText().toString(),
                selectedJobSkillsIds.toString().replaceAll("\\[", "").replace("]", ""),
                selected_ctc_type,
                binding.jobFixedPay.getText().toString(),
                variablePay,
                otherIncentive,
                seleced_pop,
                binding.jobPopMonths.getText().toString(),
                binding.postJobTitle.getText().toString(),
                selectedJobParks.toString().replaceAll("\\[", "").replace("]", ""),
                binding.postJobAbout.getText().toString(),
                "0",
                "Negotiable",
                binding.postJobSaleryStart.getText().toString(),
                binding.postJobSaleryEnd.getText().toString(),
                binding.jobWebsite.getText().toString(),
                binding.jobQuesttion1.getText().toString(),
                binding.jobQuesttion2.getText().toString(),
                binding.jobQuesttion3.getText().toString(),
                binding.jobQuesttion4.getText().toString(),
                binding.jobQuesttion5.getText().toString(),
                selectedExperience,
                "0"
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

    private void showSkillsDialog() {

        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.skils_recycler_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


        EditText skill_search_edit = dialog.findViewById(R.id.skill_search_edit);
        RecyclerView skill_recyceler = dialog.findViewById(R.id.skill_recyceler);
        ImageView done_btn = dialog.findViewById(R.id.done_btn);


        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        skill_recyceler.setLayoutManager(layoutManager);
        skill_recyceler.setAdapter(new SkillsAdapter(activity, list, PostMrInternshipJobActivity.this));

        done_btn.setOnClickListener(view -> {
            dialog.dismiss();
            binding.jobSkillsReq.setVisibility(View.GONE);
        });

        dialog.show();


    }

    private void showSkillsDialogForInterns() {

        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.skils_recycler_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


        EditText skill_search_edit = dialog.findViewById(R.id.skill_search_edit);
        RecyclerView skill_recyceler = dialog.findViewById(R.id.skill_recyceler);
        ImageView done_btn = dialog.findViewById(R.id.done_btn);


        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        skill_recyceler.setLayoutManager(layoutManager);
        skill_recyceler.setAdapter(new SkillsAdapter(activity, list, PostMrInternshipJobActivity.this));

        done_btn.setOnClickListener(view -> {
            dialog.dismiss();
            binding.internSkillsReq.setVisibility(View.GONE);
        });

        dialog.show();


    }

    @Override
    public void onSelected(String skillName, String id) {
        selectedJobSkills.add(skillName);
        selectedJobSkillsIds.add(id);
        adapter.notifyDataSetChanged();
        Log.e("TAG", "onSelected() called with: skillName = [" + skillName + "] and list = " + selectedJobSkills.toString());



    }

    @Override
    public void onRemove(String skillName, String id) {
        adapter.notifyDataSetChanged();
        selectedJobSkillsIds.remove(id);
        selectedJobSkills.remove(skillName);
        Log.e("TAG", "onRemove() called with: skillName = [" + skillName + "]and list " + selectedJobSkills.toString());


    }

    private void getSkillsList() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        apiInterfaceKapsool.getSkills().enqueue(new Callback<SkillsModel>() {
            @Override
            public void onResponse(@NonNull Call<SkillsModel> call, @NonNull Response<SkillsModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            list = response.body().getData();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<SkillsModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }
}