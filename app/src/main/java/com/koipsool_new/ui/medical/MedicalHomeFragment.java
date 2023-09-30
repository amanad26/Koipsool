package com.koipsool_new.ui.medical;

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
import com.koipsool_new.databinding.FragmentMedicalHomeBinding;
import com.koipsool_new.kapsoolAdapters.MedicalHomeAdapter;
import com.koipsool_new.model.MedicalJobModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHomeFragment extends Fragment {

   private  FragmentMedicalHomeBinding binding ;
   private Session session ;
   private Activity activity ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMedicalHomeBinding.inflate(getLayoutInflater());

        activity = requireActivity();
        session = new Session(activity);

        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        getJobs();
    }

    private  void getJobs(){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getJobMedical(session.getUserId()).enqueue(new Callback<MedicalJobModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicalJobModel> call, @NonNull Response<MedicalJobModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            binding.jobRecycelr.setLayoutManager(new LinearLayoutManager(activity));
                            binding.jobRecycelr.setAdapter(new MedicalHomeAdapter(activity, response.body().getData()));
                        }
            }

            @Override
            public void onFailure(@NonNull Call<MedicalJobModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

}