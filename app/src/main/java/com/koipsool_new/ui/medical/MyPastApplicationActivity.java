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
import com.koipsool_new.databinding.ActivityMyPastApplicationBinding;
import com.koipsool_new.kapsoolAdapters.MyAppliedJobAdapter;
import com.koipsool_new.model.MyAppliedJobModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPastApplicationActivity extends AppCompatActivity {

    private ActivityMyPastApplicationBinding binding;
    private MyPastApplicationActivity activity;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyPastApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyAppliedJobs();
    }

    private void getMyAppliedJobs() {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyAppliedJobInternship(
                session.getUserId()
        ).enqueue(new Callback<MyAppliedJobModel>() {
            @Override
            public void onResponse(@NonNull Call<MyAppliedJobModel> call, @NonNull Response<MyAppliedJobModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getData().size() != 0) {
                                binding.recycelrView.setLayoutManager(new LinearLayoutManager(activity));
                                binding.recycelrView.setAdapter(new MyAppliedJobAdapter(activity, response.body().getData()));
                            }
                        }else {
                            Toast.makeText(activity, "No Internship found..", Toast.LENGTH_SHORT).show();
                        }


            }

            @Override
            public void onFailure(@NonNull Call<MyAppliedJobModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

}