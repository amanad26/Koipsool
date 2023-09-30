package com.koipsool_new.ui.pharma;

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
import com.koipsool_new.databinding.ActivityMyCouponsBinding;
import com.koipsool_new.kapsoolAdapters.MyCouponsAdapter;
import com.koipsool_new.kapsoolModels.CouponsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCouponsActivity extends AppCompatActivity {

    private ActivityMyCouponsBinding binding ;
    private Session session ;
    private  MyCouponsActivity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCouponsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this ;
        session = new Session(activity);

        getMyCoupons();

        binding.icBack.setOnClickListener(view -> onBackPressed());


    }
    private  void getMyCoupons(){

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyCoupons(session.getUserId()).enqueue(new Callback<CouponsModel>() {
            @Override
            public void onResponse(@NonNull Call<CouponsModel> call, @NonNull Response<CouponsModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            binding.couponRecycler.setLayoutManager(new LinearLayoutManager(activity));
                            binding.couponRecycler.setAdapter(new MyCouponsAdapter(activity,response.body().getData()));
                        }else {

                            Toast.makeText(activity, "No Coupons Found...!", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<CouponsModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }
}