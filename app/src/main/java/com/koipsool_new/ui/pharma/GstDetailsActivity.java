package com.koipsool_new.ui.pharma;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.databinding.ActivityGstDetailsBinding;
import com.koipsool_new.kapsoolAdapters.CreateBillAdapter;
import com.koipsool_new.kapsoolModels.CreateBillProductModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GstDetailsActivity extends AppCompatActivity {

    private List<CreateBillProductModel.OrderItemData> billModel;
    private String totalPrice = "";
    private ActivityGstDetailsBinding binding;
    private GstDetailsActivity activity;
    private String orderId = "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGstDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        pd = new ProgressDialog(activity);


        billModel = (List<CreateBillProductModel.OrderItemData>) getIntent().getSerializableExtra("model");
        orderId = getIntent().getStringExtra("orderId");

        int p2 = 0;
        for (int i = 0; i < billModel.size(); i++) {
            int p = Integer.parseInt(billModel.get(i).getProductSellingPrice());
            p2 = p2 + p;
        }

        totalPrice = String.valueOf(p2);
        binding.netAmountTv.setText(totalPrice);
        // Log.e("TAG", "onCreate() called with: totalPrice= [" + totalPrice + "]");
        // Log.e("TAG", "onCreate() called with: bill Model = [" + billModel.toString() + "]");

        binding.productRecycelr.setLayoutManager(new LinearLayoutManager(activity));
        binding.productRecycelr.setAdapter(new CreateBillAdapter(activity, billModel, 1));

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.AGNext.setOnClickListener(view -> {
            updateOrderPrice(totalPrice);

        });

    }

    private void updateOrderPrice(String price) {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.updateOrderDetails(
                orderId,
                "",
                "",
                "",
                "",
                price,
                "",
                "",
                "",
                "",
                ""
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Bill Created..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, ViewOrderDetailsActivity.class)
                                    .putExtra("order_id", orderId)
                            );
                            finish();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

}