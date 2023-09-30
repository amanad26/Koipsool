package com.koipsool_new;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.FragmentResumeBinding;
import com.koipsool_new.kapsoolAdapters.EducationAdapter;
import com.koipsool_new.kapsoolAdapters.MyResumeProjectAdapter;
import com.koipsool_new.kapsoolAdapters.MySkillsAdapter;
import com.koipsool_new.kapsoolAdapters.ResumeJobAdapter;
import com.koipsool_new.kapsoolModels.EducationModel;
import com.koipsool_new.kapsoolModels.MyResumeProjectsModel;
import com.koipsool_new.kapsoolModels.MySkillsModel;
import com.koipsool_new.kapsoolModels.ProtfolioModel;
import com.koipsool_new.kapsoolModels.ResumeJobModel;
import com.koipsool_new.model.MedicalProfileModel;
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


public class ResumeFragment extends Fragment {
    private FragmentResumeBinding binding;
    private Activity activity;
    private Session session ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResumeBinding.inflate(inflater, container, false);
        activity = requireActivity() ;
        session = new Session(activity);

        binding.addEducationTv.setOnClickListener(view -> startActivity(new Intent(activity, AddEducationActivity.class)));
        binding.addProtfolioTv.setOnClickListener(view -> startActivity(new Intent(activity, AddProtfolioActivity.class)));
        binding.addProjectDetails.setOnClickListener(view -> startActivity(new Intent(activity, AddResumeProjectActivity.class)));
        binding.addJobDetails.setOnClickListener(view -> startActivity(new Intent(activity, AddJobResumeActivity.class)));
        binding.addJobDetails.setOnClickListener(view -> startActivity(new Intent(activity, AddJobResumeActivity.class).putExtra("type", "1")));
        binding.addInternshipDetails.setOnClickListener(view -> startActivity(new Intent(activity, AddJobResumeActivity.class).putExtra("type", "2")));
        binding.addSkillTv.setOnClickListener(view -> startActivity(new Intent(activity, AddResumeSkillActivity.class).putExtra("type", "0")));

        setPersonalInformation();

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        getMyEducation();
        getMyJobsInternship();
        getMySkills();
        getMyProtfolio();
        getMyResumeProjects();
        getMyProfile();
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
                                binding.internshipRecycler.setAdapter(new ResumeJobAdapter(activity, internshipList ,0 ));

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

    private  void getMyProfile(){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMedicalProfile(session.getUserId()).enqueue(new Callback<MedicalProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicalProfileModel> call, @NonNull Response<MedicalProfileModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setImage(response.body().getPath() + response.body().getData().get(0).getImage());
                            session.setUserName(response.body().getData().get(0).getName());
                            session.setCity(response.body().getData().get(0).getAddress());
                            session.setEmail(response.body().getData().get(0).getEmail());
                            session.setMobile(response.body().getData().get(0).getMobile());
                            setPersonalInformation();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<MedicalProfileModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });



    }

    private void setPersonalInformation() {

        binding.myName.setText(session.getUserName());
        binding.myEmail.setText(session.getEmail());
        binding.myAddress.setText(session.getCity());
        binding.myPhone.setText(session.getMobile());
    }

}