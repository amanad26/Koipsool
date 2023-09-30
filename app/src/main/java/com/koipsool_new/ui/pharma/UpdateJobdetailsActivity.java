package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.koipsool_new.R;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityUpdateJobdetailsBinding;
import com.koipsool_new.kapsoolAdapters.SelectedSkillAdapter;
import com.koipsool_new.kapsoolAdapters.SkillsAdapter;
import com.koipsool_new.kapsoolModels.MyJobsModel;
import com.koipsool_new.kapsoolModels.SkillsInterface;
import com.koipsool_new.kapsoolModels.SkillsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateJobdetailsActivity extends AppCompatActivity implements SkillsInterface {

    private ActivityUpdateJobdetailsBinding binding;
    private UpdateJobdetailsActivity activity;

    private int internshipQuestions = 2;
    private int jobQuestions = 2;
    private Session session;

    List<String> skillsList = null;
    private  String selectedExperience = "";
    private ArrayList<String> arrLanguages = new ArrayList<>();
    private ArrayList<String> arrId = new ArrayList<>();
    private ArrayList<String> selectedJobSkills = new ArrayList<>();
    private ArrayList<String> selectedJobSkillsIds = new ArrayList<>();
    private List<SkillsModel.SkillsData> list = new ArrayList<>();
    private SelectedSkillAdapter adapter;
    private ArrayList<String> selectedJobParks = new ArrayList<>();
    private ArrayList<String> selectedInternshipParks = new ArrayList<>();
    private String selected_job_type = "in-office", selected_ctc_type = "", seleced_pop = "", statPrice = "", endPrice = "";
    private MyJobsModel.MyJobInternshipData.PostJob data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateJobdetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        getSkillsList();


        data = (MyJobsModel.MyJobInternshipData.PostJob) getIntent().getSerializableExtra("data");

        setJobData();

        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(activity);
        layoutManager2.setFlexDirection(FlexDirection.ROW);
        layoutManager2.setJustifyContent(JustifyContent.FLEX_START);

        adapter = new SelectedSkillAdapter(activity, selectedJobSkills);
        binding.selectedSkillsRecycler.setLayoutManager(layoutManager2);
        binding.selectedSkillsRecycler.setAdapter(adapter);


//      binding.postJob.setOnClickListener(view -> checkAndPostJob());


        binding.jobSkillsReq.setOnClickListener(view -> showSkillsDialog());


        binding.jobTypeRadio.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            if (radioButton.getText().toString().equalsIgnoreCase("in Office"))
                selected_job_type = "office";

            else selected_job_type = "remote";
        });

        binding.jobRadioGounpCtc.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

            if (radioButton.getText().toString().equalsIgnoreCase("in %"))
                selected_ctc_type = " %";
            else selected_ctc_type = " LPA";

        });

        binding.jobPopCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.popLinarJob.setVisibility(View.VISIBLE);
                seleced_pop = "yes";
            } else {
                binding.popLinarJob.setVisibility(View.GONE);
                seleced_pop = "no";
            }
        });

        binding.job5dayCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) selectedJobParks.add(binding.job5dayCheck.getText().toString());
            else selectedJobParks.remove(binding.job5dayCheck.getText().toString());
        });

        binding.jobHealthChackbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) selectedJobParks.add(binding.jobHealthChackbox.getText().toString());
            else selectedJobParks.remove(binding.jobHealthChackbox.getText().toString());
        });

        binding.jobLifeinsuranceCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) selectedJobParks.add(binding.jobLifeinsuranceCheck.getText().toString());
            else selectedJobParks.remove(binding.jobLifeinsuranceCheck.getText().toString());
        });


    }

    private void setJobData() {

        binding.postJobTitle.setText(data.getName());
        //  binding.postJobMinExperience.setText(data.get);
        binding.postJobNumberOfOpening.setText(data.getNumberOfOpening());
        binding.postJobResposibility.setText(data.getJobResponsibilities());
        binding.jobFixedPay.setText(data.getFixedPay());
        binding.jobVariablePay.setText(data.getVariablePay());
        binding.jobWebsite.setText(data.getWebsite());
        binding.jobQuesttion1.setText(data.getQuestions_1());
        binding.jobQuesttion2.setText(data.getQuestions_2());
        binding.jobQuesttion3.setText(data.getQuestions_3());
        binding.jobQuesttion4.setText(data.getQuestions_4());
        binding.jobQuesttion5.setText(data.getQuestions_5());
        binding.postJobAbout.setText(data.getAbout());

        binding.postJobSaleryStart.setText(data.getPriceFrom());
        binding.postJobSaleryEnd.setText(data.getPriceTo());
        binding.jobOtherInsentive.setText(data.getOtherIncentives());

        if(data.getJobType().equalsIgnoreCase("no")){
            binding.radioInofficeL2.setChecked(true);
            binding.radioRemoteL2.setChecked(false);
            selected_job_type = "no";
        }else {
            binding.radioInofficeL2.setChecked(false);
            binding.radioRemoteL2.setChecked(true);
            selected_job_type = "yes";
        }

        if(data.getCtcBreakup().equalsIgnoreCase(" LPA")){
            binding.jobRadioLpaCtc.setChecked(true);
            binding.jobRadioPercentCtc.setChecked(false);
            selected_ctc_type = " LPA";
        }else {
            binding.jobRadioLpaCtc.setChecked(true);
            binding.jobRadioPercentCtc.setChecked(false);
            selected_ctc_type = " %";
        }

         getExperienceAndSetData();

         Log.e("TAG", "setJobData() called"+skillsList);



//        String[] parks = data.getPerks().split(",");
//        List<String> parksList = Arrays.asList(parks);
//        Log.e("TAG", "setInternshipData() called parksList"+parksList.toString());
//
//        for (int i = 0; i < parksList.size(); i++) {
//
//            switch (parksList.get(i)){
//
//                case "5 days a week":
//                    binding.
//
//
//            }
//
//
//        }
//


    }

    private void getExperienceAndSetData() {

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

        String exp =  data.getExperience();


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


        for (int i = 0; i < experienceList.size(); i++) {
            if(experienceList.get(i).equalsIgnoreCase(exp)){
                selectedExperience = experienceList.get(i);
                binding.jobExperianceSpinner.setSelection(i);
            }
        }


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
        skill_recyceler.setAdapter(new SkillsAdapter(activity, list, UpdateJobdetailsActivity.this));

        done_btn.setOnClickListener(view -> {
            dialog.dismiss();
           /// binding.jobSkillsReq.setVisibility(View.GONE);
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