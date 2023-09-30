package com.koipsool_new.ui.medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityBrowseJobBinding;
import com.koipsool_new.kapsoolAdapters.MedicalFindJobAdapter;
import com.koipsool_new.model.MedicalJobModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseJobActivity extends AppCompatActivity {
    private ActivityBrowseJobBinding binding;
    private Session session ;
    private  BrowseJobActivity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowseJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this ;
        session = new Session(activity);


        binding.icBack.setOnClickListener(view -> onBackPressed());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getJobs();
    }

    private void getJobs() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getJobMedical(session.getUserId()).enqueue(new Callback<MedicalJobModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicalJobModel> call, @NonNull Response<MedicalJobModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recyclerView.setAdapter(new MedicalFindJobAdapter(activity, response.body().getData()));
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