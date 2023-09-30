package com.koipsool_new.ui.resume;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityAddResumeSkillBinding;
import com.koipsool_new.kapsoolAdapters.ResumeSkillAdapter;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.ResumeSkillInterface;
import com.koipsool_new.kapsoolModels.SkillsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddResumeSkillActivity extends AppCompatActivity implements ResumeSkillInterface {

    private ActivityAddResumeSkillBinding binding;
    private Session session;
    private AddResumeSkillActivity activity;
    private String skillsNamesList = "";
    private ProgressDialog pd;
    private String skillsIdsList = "";
    private List<SkillsModel.SkillsData> skillsDataList = new ArrayList<>();
    private List<SkillsModel.SkillsData> filteredSkillsDataList = new ArrayList<>();
    private  String type = "0";
    private  String skill_id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddResumeSkillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);


        if(getIntent() != null)
            type = getIntent().getStringExtra("type");

        if(type.equalsIgnoreCase("1"))
            skill_id = getIntent().getStringExtra("skill_id");

        getSkillList();

        binding.skillEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterSkill(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void getSkillList() {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getSkills().enqueue(new Callback<SkillsModel>() {
            @Override
            public void onResponse(@NonNull Call<SkillsModel> call, @NonNull Response<SkillsModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            skillsDataList.addAll(response.body().getData());
                            binding.skillRecycler.setLayoutManager(new LinearLayoutManager(activity));
                            binding.skillRecycler.setAdapter(new ResumeSkillAdapter(activity, skillsDataList, activity));
                        }

            }

            @Override
            public void onFailure(@NonNull Call<SkillsModel> call, @NonNull Throwable t) {
                pd.dismiss();
            }
        });


    }

    @Override
    public void addSkill(String skillName, String skillId) {
        skillsIdsList = skillId;
        skillsNamesList = skillName;

        binding.skillLinar.setVisibility(View.GONE);
        binding.ratingLinear.setVisibility(View.VISIBLE);

        binding.titleSkill.setText("How would you rate your skill in " + skillName);

        binding.skillAdvamced.setOnClickListener(view -> addSkill("advanced"));
        binding.skillBeginner.setOnClickListener(view -> addSkill("beginner"));
        binding.skillIntermediate.setOnClickListener(view -> addSkill("intermediate"));

    }


    private void filterSkill(String key) {

        filteredSkillsDataList.clear();

        if (key.equalsIgnoreCase(""))
            filteredSkillsDataList.addAll(skillsDataList);
        else {

            for (int i = 0; i < skillsDataList.size(); i++) {
                if (skillsDataList.get(i).getSkillName().toLowerCase().contains(key.toLowerCase()))
                    filteredSkillsDataList.add(skillsDataList.get(i));
            }

            binding.skillRecycler.setLayoutManager(new LinearLayoutManager(activity));
            binding.skillRecycler.setAdapter(new ResumeSkillAdapter(activity, filteredSkillsDataList, activity));

        }


    }

    private void addSkill(String rate) {

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        Call<LogoutModel> call ;
        if(type.equalsIgnoreCase("0")){
          call = apiInterfaceKapsool.addMySkill(
                    session.getUserId(),
                    skillsIdsList,
                    skillsNamesList,
                    rate
            );
        } else {
            call = apiInterfaceKapsool.updateMySkill(
                    skill_id,
                    session.getUserId(),
                    skillsIdsList,
                    skillsNamesList,
                    rate
            );
        }

       call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Skill Added..!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    @Override
    public void removeSkill(String skillName, String skillId) {
    }
}