package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.databinding.ActivityViewApplicatnsBinding;
import com.koipsool_new.kapsoolAdapters.ApplicationPageAdapter;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.model.MyAppliedJobModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewApplicatnsActivity extends AppCompatActivity {

    private ViewApplicatnsActivity activity;
    private ActivityViewApplicatnsBinding binding;
    private String companyId = "";
    private String jobInternshipId = "";
    private String items[] = {"All" , "Accepted","Rejected"};
    private ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewApplicatnsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        pd = new ProgressDialog(activity);
        companyId = getIntent().getStringExtra("c_id");
        jobInternshipId = getIntent().getStringExtra("j_id");

        getAllApplicants();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllApplicants();
    }

    private void getAllApplicants() {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getAllApplicants(
                companyId,
                jobInternshipId).enqueue(new Callback<MyAppliedJobModel>() {
            @Override
            public void onResponse(@NonNull Call<MyAppliedJobModel> call, @NonNull Response<MyAppliedJobModel> response) {
              //  binding.progress.setVisibility(View.GONE);
              pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            ApplicationPageAdapter applicationPageAdapter = new ApplicationPageAdapter(activity,response.body().getData());
                            binding.viewPager.setAdapter(applicationPageAdapter);

                            new TabLayoutMediator(binding.tabLay, binding.viewPager, (tab, position) -> tab.setText(items[position])).attach();

                        } else {
                            Toast.makeText(activity, "No Applicants Found..!", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<MyAppliedJobModel> call, @NonNull Throwable t) {
                // binding.progress.setVisibility(View.GONE);
                pd.dismiss();
                 Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }
}