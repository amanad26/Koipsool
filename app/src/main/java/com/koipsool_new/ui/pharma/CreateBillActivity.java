package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityCreateBillBinding;
import com.koipsool_new.kapsoolModels.CityModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.StateModel;
import com.koipsool_new.model.AddOrderModel;
import com.koipsool_new.model.UsersListModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Updated By Aman (March 10 , 2023)
 **/

public class CreateBillActivity extends AppCompatActivity {
    private ActivityCreateBillBinding binding;
    private ArrayList<String> arrId = new ArrayList<>();
    private CreateBillActivity activity;
    private ArrayList<String> arryId = new ArrayList<>();
    private ProgressDialog pd;
    private Session session;
    private String selectedClientId = "";

    ///// State And city List
    private ArrayList<String> stateNamesList = new ArrayList<>();
    private ArrayList<String> cityNamesList = new ArrayList<>();
    private ArrayList<Integer> stateIdList = new ArrayList<>();
    private ArrayList<Integer> cityIdList = new ArrayList<>();

    ///// Client List
    private ArrayList<Integer> clientsIdList = new ArrayList<>();
    private ArrayList<String> clientsList = new ArrayList<>();
    private Integer selected_state_id = -1;
    private Integer selected_city_id = -1;
    private String selectedStateName = "";
    private String selectedCityName = "";
    private String selectedClientName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);


        binding.icBack.setOnClickListener(view -> onBackPressed());


        binding.AGNext.setOnClickListener(view -> {
            if (!selectedClientId.equalsIgnoreCase("")) addOrder();
            else Toast.makeText(activity, "Select Client ", Toast.LENGTH_SHORT).show();
        });


        arrId.add("GST*");
        arrId.add("Applicable");
        arrId.add("Not Applicable");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, arrId);
        binding.gstSpinner.setAdapter(spinnerAdapter);
        binding.gstSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            if (item.toString().equalsIgnoreCase("Applicable")) {
                binding.gstNo.setVisibility(View.VISIBLE);
            } else {
                binding.gstNo.setVisibility(View.GONE);
            }
        });

        arryId.add("Selling In");
        arryId.add("Same State");
        arryId.add("Other State");

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, arryId);
        binding.sellingIn.setAdapter(spinneradapter);
        binding.sellingIn.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (item.toString().equalsIgnoreCase("Other State")) {
                    binding.igst.setVisibility(View.VISIBLE);
                } else {
                    binding.igst.setVisibility(View.GONE);
                }
                /*if (item.toString().equalsIgnoreCase("Same State")) {

                    binding.SellingOption.setVisibility(View.VISIBLE);
                } else {
                    binding.SellingOption.setVisibility(View.GONE);
                }*/
            }
        });


    }

    private void getStates() {

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        apiInterfaceKapsool.getStates().enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(@NonNull Call<StateModel> call, @NonNull Response<StateModel> response) {

                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {

                        stateIdList.clear();
                        stateNamesList.clear();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            stateNamesList.add(response.body().getData().get(i).getName());
                            stateIdList.add(Integer.parseInt(response.body().getData().get(i).getId()));
                        }

                        ArrayAdapter adb = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, stateNamesList);
                        adb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        pd.dismiss();
                        binding.companyStateSpinner.setAdapter(adb);

                        int statePos = 0;
                        for (int i = 0; i < stateIdList.size(); i++) {
                            if (stateIdList.get(i) == selected_state_id)
                                statePos = i;
                        }

                        binding.companyStateSpinner.setAdapter(adb);
                        binding.companyStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    selected_state_id = stateIdList.get(position);
                                    selectedStateName = stateNamesList.get(position);
                                    getCites(stateIdList.get(position));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        binding.companyStateSpinner.setSelection(statePos);

                    } else {
                        pd.dismiss();
                    }

            }

            @Override
            public void onFailure(@NonNull Call<StateModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void getCites(Integer integer) {
        cityIdList.clear();
        cityNamesList.clear();
        Log.e("TAG", "getCites() called with: integer = [" + integer + "]");

        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getCities(String.valueOf(integer)).enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(@NonNull Call<CityModel> call, @NonNull Response<CityModel> response) {

                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                       cityIdList.clear();
                       cityNamesList.clear();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            cityNamesList.add(response.body().getData().get(i).getName());
                            cityIdList.add(Integer.parseInt(response.body().getData().get(i).getId()));
                        }

                        ArrayAdapter adb = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, cityNamesList);
                        adb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        pd.dismiss();
                        binding.companyCitySpinner.setAdapter(adb);
                        /// binding.companyCitySpinnerEdit.setAdapter(adb);


                        binding.companyCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_city_id = cityIdList.get(position);
                                selectedCityName = cityNamesList.get(position);
                                Log.e("TAG", "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], selected_city_id = [" + selected_city_id + "]");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    } else {
                        pd.dismiss();
                    }

            }

            @Override
            public void onFailure(@NonNull Call<CityModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void getClientsList() {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getUsersList("chemist").enqueue(new Callback<UsersListModel>() {
            @Override
            public void onResponse(@NonNull Call<UsersListModel> call, @NonNull Response<UsersListModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            clientsList.clear();
                            clientsIdList.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                clientsList.add(response.body().getData().get(i).getName());
                                clientsIdList.add(Integer.parseInt(response.body().getData().get(i).getId()));
                            }

                            Log.e("TAG", "onResponse() called with: clientsList = [" + clientsList + "]");
                            setClientAdapter();

                        }

            }

            @Override
            public void onFailure(@NonNull Call<UsersListModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void setClientAdapter() {
        Log.e("TAG", "setClientAdapter() called Clients Name " + clientsList.toString());
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, clientsList);
        binding.clientSpinner.setAdapter(spinnerAdapter);
        binding.clientSpinner.setOnItemSelectedListener((view, position, id, item) -> {
            selectedClientId = String.valueOf(clientsIdList.get(position));
            selectedClientName = clientsList.get(position);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getClientsList();
        getStates();
    }

    private void addOrder() {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addOrder(
                session.getUserId(),
                selectedClientId,
                selectedClientName,
                selectedStateName,
                selectedCityName,
                binding.contactNumber.getText().toString(),
                "0",
                binding.gstNumber.getText().toString(),
                "",
                "",
                binding.hsnNumber.getText().toString(),
                binding.drugLicance.getText().toString()
        ).enqueue(new Callback<AddOrderModel>() {
            @Override
            public void onResponse(@NonNull Call<AddOrderModel> call, @NonNull Response<AddOrderModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Intent intent = new Intent(activity, BillingProductDetailActivity.class);
                            intent.putExtra("orderId", String.valueOf(response.body().getData()));
                            startActivity(intent);
                            finish();
                            Toast.makeText(activity, "Order Created..!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "failed..!", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<AddOrderModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

}