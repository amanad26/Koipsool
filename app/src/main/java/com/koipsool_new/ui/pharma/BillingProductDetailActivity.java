package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityBillingProductDetailBinding;
import com.koipsool_new.kapsoolAdapters.CreateBillAdapter;
import com.koipsool_new.kapsoolModels.CreateBillProductModel;
import com.koipsool_new.kapsoolModels.LoginModel;
import com.koipsool_new.kapsoolModels.ProductModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.model.AddOrderModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingProductDetailActivity extends AppCompatActivity {

    private int itemId = 1;
    private ArrayList<String> arrId = new ArrayList<>();
    private ArrayList<String> freeitem = new ArrayList<>();
    private ArrayList<String> itemlist = new ArrayList<>();
    private ActivityBillingProductDetailBinding binding;
    private String selectedProductId = "", freeItemString = "no", selectedFreeItem = "", selectedFreeItemId = "";
    private List<CreateBillProductModel.OrderItemData> billModel = new ArrayList<>();
    private CreateBillAdapter adapter;
    private BillingProductDetailActivity activity;

    ///// Product List
    private Session session;
    private String orderId = "";
    private ArrayList<String> productNames = new ArrayList<>();
    private ArrayList<String> productIds = new ArrayList<>();
    private String selectedProductName = "";
    private ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillingProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        orderId = getIntent().getStringExtra("orderId");
        Log.e("TAG", "onCreate() called with: Order Id  = [" + orderId + "]");

//
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(activity));
        binding.productsRecycler.setAdapter(adapter);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.AGNext.setOnClickListener(view -> {
            if (billModel.size() != 0) {
                Intent intent = new Intent(BillingProductDetailActivity.this, GstDetailsActivity.class);
                intent.putExtra("model", (Serializable) billModel);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(activity, "Add at least one product", Toast.LENGTH_SHORT).show();
            }
        });

        freeitem.add("Free Item");
        freeitem.add("Yes");
        freeitem.add("No");

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, freeitem);
        binding.freeItemSpinner.setAdapter(spinneradapter);
        binding.freeItemSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (item.toString().equalsIgnoreCase("Yes")) {
                    binding.itemListSpinnerLay.setVisibility(View.VISIBLE);
                    freeItemString = "yes";
                } else {
                    freeItemString = "no";
                    binding.itemListSpinnerLay.setVisibility(View.GONE);
                }
            }
        });

        itemlist.add("item List");
        itemlist.add("a");
        itemlist.add("b");
        itemlist.add("d");
        itemlist.add("e");
        itemlist.add("f");
        binding.itemListSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemlist));
        binding.itemListSpinner.setOnItemSelectedListener((view, position, id, item) -> selectedFreeItem = itemlist.get(position));

        binding.saveAddProduct.setOnClickListener(view -> {
            if (selectedProductId.equalsIgnoreCase(""))
                Toast.makeText(BillingProductDetailActivity.this, "Select Product", Toast.LENGTH_SHORT).show();

            else if (binding.edtTotalQuantity.getText().toString().equalsIgnoreCase(""))
                Toast.makeText(BillingProductDetailActivity.this, "Enter Product Quantity", Toast.LENGTH_SHORT).show();

            else if (binding.edtPriceMrp.getText().toString().equalsIgnoreCase(""))
                Toast.makeText(BillingProductDetailActivity.this, "Enter Product MRP", Toast.LENGTH_SHORT).show();

            else if (binding.edtSellingPrice.getText().toString().equalsIgnoreCase(""))
                Toast.makeText(BillingProductDetailActivity.this, "Enter Product Selling Price", Toast.LENGTH_SHORT).show();
            else if (freeItemString.equalsIgnoreCase("yes") && selectedFreeItem.equalsIgnoreCase(""))
                Toast.makeText(BillingProductDetailActivity.this, "Select Free Item", Toast.LENGTH_SHORT).show();
            else addProductInDB();
        });

    }

    private void addProductInDB() {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addOrderItem(
                orderId,
                session.getUserId(),
                "",
                selectedProductName,
                binding.edtPriceMrp.getText().toString(),
                binding.edtSellingPrice.getText().toString(),
                binding.edtTotalQuantity.getText().toString(),
                freeItemString,
                selectedFreeItem,
                selectedFreeItemId
        ).enqueue(new Callback<AddOrderModel>() {
            @Override
            public void onResponse(@NonNull Call<AddOrderModel> call, @NonNull Response<AddOrderModel> response) {
                pd.dismiss();

                if(response.code() ==200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            Toast.makeText(activity, "Product Added..", Toast.LENGTH_SHORT).show();
                            binding.edtPriceMrp.setText("");
                            binding.edtTotalQuantity.setText("");
                            binding.edtSellingPrice.setText("");
                            selectedProductId = "";
                            selectedFreeItem = "";
                            freeItemString = "no";
                            getAddedProduct();
                        }
             }

            @Override
            public void onFailure(@NonNull Call<AddOrderModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void getAddedProduct() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getOrderItems(orderId).enqueue(new Callback<CreateBillProductModel>() {
            @Override
            public void onResponse(@NonNull Call<CreateBillProductModel> call, @NonNull Response<CreateBillProductModel> response) {

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            billModel.clear();
                            billModel.addAll(response.body().getData());
                            adapter = new CreateBillAdapter(activity, billModel, 0);
                            binding.productsRecycler.setAdapter(adapter);
                        }

            }

            @Override
            public void onFailure(@NonNull Call<CreateBillProductModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private boolean checkFreeOrNot(String str) {
        return !str.equalsIgnoreCase("no");
    }

    private void getMyProduct() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyProduct(session.getUserId()).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductModel> call, @NonNull Response<ProductModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                productIds.add(response.body().getData().get(i).getId());
                                productNames.add(response.body().getData().get(i).getName());
                            }

                            setProductAdapter();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<ProductModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void setProductAdapter() {

        binding.productItemSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, productNames));
        binding.productItemSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            selectedProductId = productIds.get(position);
            selectedProductName = productNames.get(position);
        });


        binding.itemListSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, productNames));
        binding.itemListSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            selectedFreeItemId = productIds.get(position);
            selectedFreeItem = productNames.get(position);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyProduct();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(billModel.size() == 0 ){
           deleteOrder(orderId);
        }
    }

    private  void deleteOrder(String orderId){

         ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
         apiInterfaceKapsool.deleteMyOrder(orderId).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                        }

            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

}