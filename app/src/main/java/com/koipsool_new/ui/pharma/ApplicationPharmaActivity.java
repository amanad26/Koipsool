package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityApplicationPharmaBinding;
import com.koipsool_new.kapsoolAdapters.ApplicationPageAdapter;
import com.koipsool_new.model.MyAppliedJobModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationPharmaActivity extends AppCompatActivity {

    private ApplicationPharmaActivity activity;
    private ActivityApplicationPharmaBinding binding;
    private Session session;
    private String items[] = {"All" , "Accepted","Rejected"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplicationPharmaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this ;
        session = new Session(activity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyAppliedJobs();
    }

    private void getMyAppliedJobs() {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyApplicationsCompany(
                session.getUserId()
        ).enqueue(new Callback<MyAppliedJobModel>() {
            @Override
            public void onResponse(@NonNull Call<MyAppliedJobModel> call, @NonNull Response<MyAppliedJobModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getData().size() != 0) {
                                ApplicationPageAdapter applicationPageAdapter = new ApplicationPageAdapter(activity,response.body().getData());
                                binding.viewPager.setAdapter(applicationPageAdapter);

                                new TabLayoutMediator(binding.tabLay, binding.viewPager, (tab, position) -> tab.setText(items[position])).attach();

                            }
                        } else {
                            Toast.makeText(activity, "No Internship found..", Toast.LENGTH_SHORT).show();
                        }


            }

            @Override
            public void onFailure(@NonNull Call<MyAppliedJobModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

}