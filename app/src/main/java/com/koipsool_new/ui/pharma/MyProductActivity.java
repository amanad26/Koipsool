package com.koipsool_new.ui.pharma;

import static android.content.ContentValues.TAG;

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
import com.koipsool_new.databinding.ActivityMyProductBinding;
import com.koipsool_new.kapsoolAdapters.ProductAdapter;
import com.koipsool_new.kapsoolModels.ProductModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProductActivity extends AppCompatActivity {
    ActivityMyProductBinding binding;
    Session session;
    MyProductActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.swipeRefresh.setOnRefreshListener(() -> getMyProduct());

    }

    private void getMyProduct() {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyProduct(session.getUserId()).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductModel> call, @NonNull Response<ProductModel> response) {
              binding.swipeRefresh.setRefreshing(false);
              binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            binding.recycelrview.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recycelrview.setAdapter(new ProductAdapter(activity,response.body().getData()));
                        } else {
                            Toast.makeText(activity, "No Product Found.!", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<ProductModel> call, @NonNull Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyProduct();
    }
}