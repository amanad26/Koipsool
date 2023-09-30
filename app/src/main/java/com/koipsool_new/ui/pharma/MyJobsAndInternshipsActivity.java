package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityMyJobsAndInternshipsBinding;
import com.koipsool_new.kapsoolAdapters.MyJobsInternshipAdapter;
import com.koipsool_new.kapsoolModels.MyJobsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyJobsAndInternshipsActivity extends AppCompatActivity {

    private ActivityMyJobsAndInternshipsBinding binding;
    private Session session;
    private MyJobsAndInternshipsActivity activity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyJobsAndInternshipsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this ;
        session = new Session(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        getMyJobsInternship();

//        details = findViewById(R.id.details);
//        ic_back = findViewById(R.id.ic_back);
//        ic_back.setOnClickListener(view -> onBackPressed());
//        application_received = findViewById(R.id.application_received);
//        application_received.setOnClickListener(view -> {
//            Intent intent = new Intent(MyJobsAndInternshipsActivity.this, ViewApplicationActivity.class);
//            startActivity(intent);
//        });

//        details.setOnClickListener(view -> {
//            Intent intent = new Intent(MyJobsAndInternshipsActivity.this, PharmaHomeAllDetailsActivity.class);
//            startActivity(intent);
//        });


    }


    private  void getMyJobsInternship(){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyJobPostInternships(session.getUserId()).enqueue(new Callback<MyJobsModel>() {
            @Override
            public void onResponse(@NonNull Call<MyJobsModel> call, @NonNull Response<MyJobsModel> response) {

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            List<MyJobsModel.MyJobInternshipData.PostJob> jobs = response.body().getData().getPostJob();
                            List<MyJobsModel.MyJobInternshipData.PostInternship> internships = response.body().getData().getPostInternship();

                            if(response.body().getData().getPostInternship() == null)
                                internships = new ArrayList<>();

                            if(response.body().getData().getPostJob() == null)
                                jobs = new ArrayList<>();


                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recyclerView.setAdapter(new MyJobsInternshipAdapter(activity,jobs,internships));

                            binding.progressBar.setVisibility(View.GONE);

                        }else {
                            binding.noDataTv.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);

                        }


            }

            @Override
            public void onFailure(@NonNull Call<MyJobsModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.noDataTv.setVisibility(View.VISIBLE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

}