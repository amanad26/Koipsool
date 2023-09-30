package com.koipsool_new.ui.pharma;

import static android.content.ContentValues.TAG;

import static com.koipsool_new.RetofitSetUp.BaseUrls.USER_IMAGE;

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
import com.koipsool_new.databinding.ActivityViewOrderDetailsBinding;
import com.koipsool_new.kapsoolAdapters.OrderDetailAdapter;
import com.koipsool_new.kapsoolModels.ChemistProfileModel;
import com.koipsool_new.model.OrderDetailsModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderDetailsActivity extends AppCompatActivity {

    private String order_id = "";
    private ActivityViewOrderDetailsBinding binding;
    private ViewOrderDetailsActivity activity;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        order_id = getIntent().getStringExtra("order_id");

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetails(order_id);
    }

    private void getOrderDetails(String id) {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getOrderDetails(id).enqueue(new Callback<OrderDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderDetailsModel> call, @NonNull Response<OrderDetailsModel> response) {
                binding.progressbar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            OrderDetailsModel.Order data = response.body().getOrder();
                            binding.clientName.setText(data.getCustomerName());
                            binding.orderPrice.setText(data.getTotalAmount());
                            binding.orderAddress.setText(data.getCustomerAddress());

                            getUserImage(data.getClientId());

                            binding.itemsRecycler.setLayoutManager(new LinearLayoutManager(activity));
                            binding.itemsRecycler.setAdapter(new OrderDetailAdapter(activity, response.body().getItemData()));
//
                        } else {
                            Toast.makeText(activity, "Details Not found...", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailsModel> call, @NonNull Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void getUserImage(String clientId) {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getChemistProfile(clientId).enqueue(new Callback<ChemistProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ChemistProfileModel> call, @NonNull Response<ChemistProfileModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            String image = response.body().getData().get(0).getImage();
                            if (!image.equalsIgnoreCase(""))
                                Picasso.get().load(USER_IMAGE + image).into(binding.userImage);
                        }
            }

            @Override
            public void onFailure(@NonNull Call<ChemistProfileModel> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }
}