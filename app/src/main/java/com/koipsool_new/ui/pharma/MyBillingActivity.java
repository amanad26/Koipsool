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
import com.koipsool_new.databinding.ActivityMyBillingBinding;
import com.koipsool_new.kapsoolAdapters.MyOrderAdapter;
import com.koipsool_new.model.MyOrderModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBillingActivity extends AppCompatActivity {

    private ActivityMyBillingBinding binding;
    private MyBillingActivity activity;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBillingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyOrders(session.getUserId());
    }

    private void getMyOrders(String id) {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyOrder(
                id,
                ""
        ).enqueue(new Callback<MyOrderModel>() {
            @Override
            public void onResponse(@NonNull Call<MyOrderModel> call, @NonNull Response<MyOrderModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.recycelerView.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recycelerView.setAdapter(new MyOrderAdapter(activity, response.body().getData()));
                        } else {
                            Toast.makeText(activity, "No Orders Found..!", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<MyOrderModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

}