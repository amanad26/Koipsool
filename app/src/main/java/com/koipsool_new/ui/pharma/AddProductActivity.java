package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityAddProductBinding;
import com.koipsool_new.kapsoolModels.CategoriesModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    private ActivityAddProductBinding binding;
    private ArrayList<String> arrId = new ArrayList<>();
    private ArrayList<String> itemlist = new ArrayList<>();
    private ArrayList<String> categoryName = new ArrayList<>();
    private ArrayList<Integer> categoryIds = new ArrayList<>();
    private String selectedProductTax = "", selectedProductCategory = "", selectedExpireDate = "";
    private Session session;
    private AddProductActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        getCategory();

        arrId.add("Tax class");
        arrId.add("5%");
        arrId.add("12%");
        arrId.add("20%");
        arrId.add("28%");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrId);
        binding.taxclassSpinner.setAdapter(spinnerAdapter);
        binding.taxclassSpinner.setOnItemSelectedListener((view, position, id, item) -> selectedProductTax = arrId.get(position));

        binding.addBtn.setOnClickListener(view -> {
            if (isValidate())
                addProduct();
        });


        binding.productExpireDate.setOnClickListener(view -> selectDate(binding.productExpireDate));

    }
    private  void getCategory(){

         ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
         apiInterfaceKapsool.getCategories().enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesModel> call, @NonNull Response<CategoriesModel> response) {

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                categoryName.add(response.body().getData().get(i).getCateName());
                                categoryIds.add(Integer.parseInt(response.body().getData().get(i).getId()));
                            }

                            setCategoryAdapter();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<CategoriesModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void setCategoryAdapter() {
        binding.productItemSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categoryName));
        binding.productItemSpinner.setOnItemSelectedListener((view, position, id, item) -> selectedProductCategory = String.valueOf(categoryIds.get(position)));

    }

    private void addProduct() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        Log.e("TAG", "addProduct() called Tax Class"+selectedProductTax);

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addCompanyProduct(
                session.getUserId(),
                binding.productName.getText().toString(),
                binding.productDescriptiion.getText().toString(),
                binding.productBatchNumber.getText().toString(),
                binding.productExpireDate.getText().toString(),
                binding.productMrp.getText().toString(),
                selectedProductTax,
                binding.productHsnCode.getText().toString(),
                selectedProductCategory
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Product Added..", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(activity, "Failed..", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    public String getMonthName(int month) {

        String monthString;
        switch (month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                monthString = "Inv month";
                break;
        }
        System.out.println(monthString);

        return monthString;
    }

    private void selectDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            String date = day1 + " /" + getMonthName((month1 + 1)) + "/ " +
                    "/" + year1;
            DateBtn.setText(date);
            selectedExpireDate = date;
        }, year, month, day);
        datePickerDialog.show();
    }

    private boolean isValidate() {
        if (binding.productName.getText().toString().equalsIgnoreCase("")) {
            binding.productName.setError("Product Name..!");
            binding.productName.requestFocus();
            return false;
        } else if (binding.productBatchNumber.getText().toString().equalsIgnoreCase("")) {
            binding.productBatchNumber.setError("Product Batch Number..!");
            binding.productBatchNumber.requestFocus();
            return false;
        } else if (binding.productMrp.getText().toString().equalsIgnoreCase("")) {
            binding.productMrp.setError("Product MRP..!");
            binding.productMrp.requestFocus();
            return false;
        } else if (binding.productDescriptiion.getText().toString().equalsIgnoreCase("")) {
            binding.productDescriptiion.setError("Product description..!");
            binding.productDescriptiion.requestFocus();
            return false;
        } else if (selectedExpireDate.equalsIgnoreCase("")) {
            Toast.makeText(this, "Select Expire Date..!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedProductTax.equalsIgnoreCase("")) {
            Toast.makeText(this, "Select Tax Class..!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedProductCategory.equalsIgnoreCase("")) {
            Toast.makeText(this, "Select Product Category..!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }


}