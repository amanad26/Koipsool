package com.koipsool_new.ui.medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivitySavedJobListBinding;
import com.koipsool_new.kapsoolAdapters.SavedJobAdapter;
import com.koipsool_new.kapsoolModels.SavedJobModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedJobListActivity extends AppCompatActivity {

    private ActivitySavedJobListBinding binding;
    private SavedJobListActivity activity;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedJobListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);


        binding.swipeRefresh.setOnRefreshListener(() -> getMySavedJob());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMySavedJob();
    }

    private void getMySavedJob() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMySavedJob(session.getUserId()).enqueue(new Callback<SavedJobModel>() {
            @Override
            public void onResponse(@NonNull Call<SavedJobModel> call, @NonNull Response<SavedJobModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recyclerView.setAdapter(new SavedJobAdapter(activity, response.body().getData(), response.body().getPath()));
                        } else {
                            Toast.makeText(activity, "No Saved Job/Internship Found", Toast.LENGTH_SHORT).show();
                        }


            }

            @Override
            public void onFailure(@NonNull Call<SavedJobModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

}