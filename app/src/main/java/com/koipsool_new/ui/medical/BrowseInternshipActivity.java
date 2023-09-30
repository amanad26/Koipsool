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
import com.koipsool_new.databinding.ActivityBrowseInternshipBinding;
import com.koipsool_new.kapsoolAdapters.MedicalFindInternshipAdapter;
import com.koipsool_new.model.MedicalInternshipModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseInternshipActivity extends AppCompatActivity {

    private ActivityBrowseInternshipBinding binding;
    private Session session ;
    private  BrowseInternshipActivity activity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowseInternshipBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllInternships();
    }

    private  void getAllInternships(){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getInternshipMedical(session.getUserId()).enqueue(new Callback<MedicalInternshipModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicalInternshipModel> call, @NonNull Response<MedicalInternshipModel> response) {
                binding.progressBar.setVisibility(View.GONE);

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            binding.recycelrView.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recycelrView.setAdapter(new MedicalFindInternshipAdapter(activity,response.body().getData()));
                        }else {
                            Toast.makeText(activity, "No Internships found..!", Toast.LENGTH_SHORT).show();
                        }


            }

            @Override
            public void onFailure(@NonNull Call<MedicalInternshipModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

}