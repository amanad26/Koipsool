package com.koipsool_new.ui.pharma;

import static android.content.ContentValues.TAG;

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
import com.koipsool_new.databinding.ActivityCouponsBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsActivity extends AppCompatActivity {
    private ActivityCouponsBinding binding;
    private final ArrayList<String> arrId = new ArrayList<>();
    private Session session;
    private String selectedAvailableFor = "";
    private CouponsActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCouponsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        arrId.add("Available for*");
        arrId.add("All");
        arrId.add("New");
        arrId.add("Old");

        binding.couponValidity.setOnClickListener(view -> selectDate(binding.couponValidity));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arrId);
        binding.availableIn.setAdapter(spinnerAdapter);
        binding.availableIn.setOnItemSelectedListener((view, position, id, item) -> selectedAvailableFor = arrId.get(position));

        binding.add.setOnClickListener(view -> {if(isValidate()) addCoupon();});

        binding.icBack.setOnClickListener(view -> onBackPressed());

    }

    private boolean isValidate() {
        if (binding.productMrp.getText().toString().equalsIgnoreCase("")) {
            binding.productMrp.setError("Enter Product MRP");
            binding.productMrp.requestFocus();
            return false;
        } else if (binding.productCompositions.getText().toString().equalsIgnoreCase("")) {
            binding.productCompositions.setError("Enter Product Compositions");
            binding.productCompositions.requestFocus();
            return false;
        } else if (binding.productDiscountPercentage.getText().toString().equalsIgnoreCase("")) {
            binding.productDiscountPercentage.setError("Enter Discount Percentage");
            binding.productDiscountPercentage.requestFocus();
            return false;
        } else if (binding.couponValidity.getText().toString().equalsIgnoreCase("Coupon validity")) {
            binding.couponValidity.setError("Select Expire Date");
            binding.couponValidity.requestFocus();
            return false;
        } else if (binding.productName.getText().toString().equalsIgnoreCase("")) {
            binding.productName.setError("Enter Product Name");
            binding.productName.requestFocus();
            return false;
        } else if (binding.productOffer.getText().toString().equalsIgnoreCase("")) {
            binding.productOffer.setError("Enter Product Offer");
            binding.productOffer.requestFocus();
            return false;
        } else {
            return true;
        }


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
        }, year, month, day);
        datePickerDialog.show();
    }

    private void addCoupon() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.createCoupons(
                session.getUserId(),
                binding.productName.getText().toString(),
                binding.productCompositions.getText().toString(),
                binding.productOffer.getText().toString(),
                binding.productDiscountPercentage.getText().toString(),
                binding.productDiscountPercentage.getText().toString(),
                binding.productDiscountPrice.getText().toString(),
                binding.couponValidity.getText().toString(),
                binding.productMrp.getText().toString(),
                selectedAvailableFor
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            finish();
                            Toast.makeText(activity, "Coupon Added...", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(activity, "Failed...!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }


}