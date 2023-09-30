package com.koipsool_new.ui.medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.koipsool_new.R;
import com.koipsool_new.ResumeFragment;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityResumeBinding;
import com.koipsool_new.kapsoolAdapters.EducationAdapter;
import com.koipsool_new.kapsoolAdapters.MyResumeProjectAdapter;
import com.koipsool_new.kapsoolAdapters.MySkillsAdapter;
import com.koipsool_new.kapsoolAdapters.ResumeJobAdapter;
import com.koipsool_new.kapsoolModels.EducationModel;
import com.koipsool_new.kapsoolModels.MyResumeProjectsModel;
import com.koipsool_new.kapsoolModels.MySkillsModel;
import com.koipsool_new.kapsoolModels.ProtfolioModel;
import com.koipsool_new.kapsoolModels.ResumeJobModel;
import com.koipsool_new.ui.resume.AddEducationActivity;
import com.koipsool_new.ui.resume.AddJobResumeActivity;
import com.koipsool_new.ui.resume.AddProtfolioActivity;
import com.koipsool_new.ui.resume.AddResumeProjectActivity;
import com.koipsool_new.ui.resume.AddResumeSkillActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeActivity extends AppCompatActivity {

    private ActivityResumeBinding binding;
    private ResumeActivity activity;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResumeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        binding.addEducationTv.setOnClickListener(view -> startActivity(new Intent(activity, AddEducationActivity.class)));
        binding.addProtfolioTv.setOnClickListener(view -> startActivity(new Intent(activity, AddProtfolioActivity.class)));
        binding.addProjectDetails.setOnClickListener(view -> startActivity(new Intent(activity, AddResumeProjectActivity.class)));
        binding.addJobDetails.setOnClickListener(view -> startActivity(new Intent(activity, AddJobResumeActivity.class)));
        binding.addJobDetails.setOnClickListener(view -> startActivity(new Intent(activity, AddJobResumeActivity.class).putExtra("type", "1")));
        binding.addInternshipDetails.setOnClickListener(view -> startActivity(new Intent(activity, AddJobResumeActivity.class).putExtra("type", "2")));
        binding.addSkillTv.setOnClickListener(view -> startActivity(new Intent(activity, AddResumeSkillActivity.class).putExtra("type", "0")));
        binding.icBack.setOnClickListener(view -> onBackPressed());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new ResumeFragment()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getMyEducation();
        getMyJobsInternship();
        getMySkills();
        getMyProtfolio();
        getMyResumeProjects();
    }

    private void getMySkills() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        apiInterfaceKapsool.getMySkill(session.getUserId()).enqueue(new Callback<MySkillsModel>() {
            @Override
            public void onResponse(@NonNull Call<MySkillsModel> call, @NonNull Response<MySkillsModel> response) {
                binding.skillProgressBar.setVisibility(View.GONE);

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.skillRecycler.setLayoutManager(new LinearLayoutManager(activity));
                            binding.skillRecycler.setAdapter(new MySkillsAdapter(activity, response.body().getData(),0));
                        }

            }

            @Override
            public void onFailure(@NonNull Call<MySkillsModel> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                binding.skillProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getMyEducation() {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyEducation(session.getUserId()).enqueue(new Callback<EducationModel>() {
            @Override
            public void onResponse(@NonNull Call<EducationModel> call, @NonNull Response<EducationModel> response) {
                binding.educationProgress.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.educationRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
                            binding.educationRecyclerview.setAdapter(new EducationAdapter(activity, response.body().getData(),0));

                        }

            }

            @Override
            public void onFailure(@NonNull Call<EducationModel> call, @NonNull Throwable t) {
                binding.educationProgress.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void getMyJobsInternship() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getResumeJobInternship(session.getUserId()).enqueue(new Callback<ResumeJobModel>() {
            @Override
            public void onResponse(@NonNull Call<ResumeJobModel> call, @NonNull Response<ResumeJobModel> response) {
                binding.jobProgressBar.setVisibility(View.GONE);
                binding.internshipProgressBar.setVisibility(View.GONE);

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            List<ResumeJobModel.ResumeJobData> jobList = new ArrayList<>();
                            List<ResumeJobModel.ResumeJobData> internshipList = new ArrayList<>();

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if (response.body().getData().get(i).getType().equalsIgnoreCase("job")) {
                                    jobList.add(response.body().getData().get(i));
                                } else {
                                    internshipList.add(response.body().getData().get(i));
                                }

                                binding.jobRecycler.setLayoutManager(new LinearLayoutManager(activity));
                                binding.jobRecycler.setAdapter(new ResumeJobAdapter(activity, jobList, 0 ));

                                binding.internshipRecycler.setLayoutManager(new LinearLayoutManager(activity));
                                binding.internshipRecycler.setAdapter(new ResumeJobAdapter(activity, internshipList, 0 ));

                            }


                        }

            }

            @Override
            public void onFailure(@NonNull Call<ResumeJobModel> call, @NonNull Throwable t) {
                binding.jobProgressBar.setVisibility(View.GONE);
                binding.internshipProgressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void getMyProtfolio() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyProtfolio(session.getUserId()).enqueue(new Callback<ProtfolioModel>() {
            @Override
            public void onResponse(@NonNull Call<ProtfolioModel> call, @NonNull Response<ProtfolioModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            ProtfolioModel.ProtfolioData data = response.body().getData().get(0);

                            if (!data.getBlogLink().equalsIgnoreCase("")) {
                                binding.blogLinear.setVisibility(View.VISIBLE);
                                binding.myBlogTv.setText(data.getBlogLink());
                            }

                            if (!data.getGithubProfile().equalsIgnoreCase("")) {
                                binding.githubLinear.setVisibility(View.VISIBLE);
                                binding.myGithubTv.setText(data.getGithubProfile());
                            }

                            if (!data.getPlayStoreLink().equalsIgnoreCase("")) {
                                binding.playStoreLinear.setVisibility(View.VISIBLE);
                                binding.myPlayStoreTv.setText(data.getGithubProfile());
                            }

                            if (!data.getPortfolioLink().equalsIgnoreCase("")) {
                                binding.behanceLinear.setVisibility(View.VISIBLE);
                                binding.myBehanceTv.setText(data.getPortfolioLink());
                            }

                            if (!data.getOtherWork().equalsIgnoreCase("")) {
                                binding.otherLinear.setVisibility(View.VISIBLE);
                                binding.myOtherTv.setText(data.getOtherWork());
                            }


                           binding.addProtfolioTv.setText(R.string.edit_protfolio_details);

                        }


            }

            @Override
            public void onFailure(@NonNull Call<ProtfolioModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private  void getMyResumeProjects(){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyResumeProject(session.getUserId()).enqueue(new Callback<MyResumeProjectsModel>() {
            @Override
            public void onResponse(@NonNull Call<MyResumeProjectsModel> call, @NonNull Response<MyResumeProjectsModel> response) {
                binding.projectProgressBar.setVisibility(View.GONE);
                if(response.code() ==200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                         binding.projectRecycler.setLayoutManager(new LinearLayoutManager(activity));
                         binding.projectRecycler.setAdapter(new MyResumeProjectAdapter(activity, response.body().getData(), 0));
                        }

            }

            @Override
            public void onFailure(@NonNull Call<MyResumeProjectsModel> call, @NonNull Throwable t) {
                binding.projectProgressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

}