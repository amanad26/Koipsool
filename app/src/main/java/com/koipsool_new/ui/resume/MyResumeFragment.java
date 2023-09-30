package com.koipsool_new.ui.resume;

import android.app.Activity;
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
import com.koipsool_new.databinding.FragmentMyResumeBinding;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyResumeFragment extends Fragment {

    private  FragmentMyResumeBinding binding;
    private Activity activity ;
    private Session session ;
    private  String userId = "" ; 

    public MyResumeFragment(String uid) {
        this.userId = uid ;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyResumeBinding.inflate(getLayoutInflater());

        activity = requireActivity() ;
        session = new Session(activity);


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

        apiInterfaceKapsool.getMySkill(userId).enqueue(new Callback<MySkillsModel>() {
            @Override
            public void onResponse(@NonNull Call<MySkillsModel> call, @NonNull Response<MySkillsModel> response) {
                binding.skillProgressBar.setVisibility(View.GONE);

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.skillRecycler.setLayoutManager(new LinearLayoutManager(activity));
                            binding.skillRecycler.setAdapter(new MySkillsAdapter(activity, response.body().getData() ,1));
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
        apiInterfaceKapsool.getMyEducation(userId).enqueue(new Callback<EducationModel>() {
            @Override
            public void onResponse(@NonNull Call<EducationModel> call, @NonNull Response<EducationModel> response) {
                binding.educationProgress.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.educationRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
                            binding.educationRecyclerview.setAdapter(new EducationAdapter(activity, response.body().getData(),1));

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
        apiInterfaceKapsool.getResumeJobInternship(userId).enqueue(new Callback<ResumeJobModel>() {
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
                                binding.jobRecycler.setAdapter(new ResumeJobAdapter(activity, jobList, 1));

                                binding.internshipRecycler.setLayoutManager(new LinearLayoutManager(activity));
                                binding.internshipRecycler.setAdapter(new ResumeJobAdapter(activity, internshipList,1));

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
        apiInterfaceKapsool.getMyProtfolio(userId).enqueue(new Callback<ProtfolioModel>() {
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


                           ///// binding.addProtfolioTv.setText(R.string.edit_protfolio_details);

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
        apiInterfaceKapsool.getMyResumeProject(userId).enqueue(new Callback<MyResumeProjectsModel>() {
            @Override
            public void onResponse(@NonNull Call<MyResumeProjectsModel> call, @NonNull Response<MyResumeProjectsModel> response) {
                binding.projectProgressBar.setVisibility(View.GONE);
                if(response.code() ==200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            binding.projectRecycler.setLayoutManager(new LinearLayoutManager(activity));
                            binding.projectRecycler.setAdapter(new MyResumeProjectAdapter(activity, response.body().getData(), 1));
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
        apiInterfaceKapsool.getMedicalProfile(userId).enqueue(new Callback<MedicalProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicalProfileModel> call, @NonNull Response<MedicalProfileModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
//                            session.setImage(response.body().getPath() + response.body().getData().get(0).getImage());
//                            session.setUserName(response.body().getData().get(0).getName());
//                            session.setCity(response.body().getData().get(0).getAddress());
//                            session.setEmail(response.body().getData().get(0).getEmail());
//                            session.setMobile(response.body().getData().get(0).getMobile());


                            binding.myName.setText(response.body().getData().get(0).getName());
                            binding.myEmail.setText(response.body().getData().get(0).getAddress());
                            binding.myAddress.setText(response.body().getData().get(0).getEmail());
                            binding.myPhone.setText(response.body().getData().get(0).getMobile());

                        }

            }

            @Override
            public void onFailure(@NonNull Call<MedicalProfileModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });



    }

}