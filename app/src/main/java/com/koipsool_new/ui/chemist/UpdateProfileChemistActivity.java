package com.koipsool_new.ui.chemist;

import static com.koipsool_new.RetofitSetUp.BaseUrls.USER_IMAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityUpdateProfileChemistBinding;
import com.koipsool_new.kapsoolModels.ChemistProfileModel;
import com.koipsool_new.kapsoolModels.CityModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.StateModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileChemistActivity extends AppCompatActivity {

    private UpdateProfileChemistActivity activity;
    private ActivityUpdateProfileChemistBinding binding;
    private Session session;
    private ArrayList<String> stateNamesList = new ArrayList<>();
    private ArrayList<String> cityNamesList = new ArrayList<>();
    private ArrayList<Integer> stateIdList = new ArrayList<>();
    private ArrayList<Integer> cityIdList = new ArrayList<>();
    private Integer selected_state_id = 0;
    private Integer selected_city_id = 0;
    private String selectedStateName = "";
    private String selectedCityName = "";
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileChemistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.updateProfile.setOnClickListener(view -> updateChemistProfile());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getChemistProfile();
    }

    private void getChemistProfile() {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getChemistProfile(session.getUserId()).enqueue(new Callback<ChemistProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ChemistProfileModel> call, @NonNull Response<ChemistProfileModel> response) {
                binding.updateProfile.setVisibility(View.VISIBLE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setImage(USER_IMAGE + response.body().getData().get(0).getImage());
                            session.setUserName(response.body().getData().get(0).getName());
                            session.setEmail(response.body().getData().get(0).getEmail());
                            session.setMobile(response.body().getData().get(0).getMobile());

                            binding.userName.setText(response.body().getData().get(0).getName());
                            binding.userEmail.setText(response.body().getData().get(0).getEmail());
                            binding.userState.setText(response.body().getData().get(0).getState());
                            binding.userCity.setText(response.body().getData().get(0).getCity());
                            binding.userAddress.setText(response.body().getData().get(0).getAddress());
                            selectedStateName = response.body().getData().get(0).getState();
                            selectedCityName = response.body().getData().get(0).getCity();

                            if (!response.body().getData().get(0).getStateId().equalsIgnoreCase("")) {
                                selected_state_id = Integer.parseInt(response.body().getData().get(0).getStateId());
                            }
                            if (!response.body().getData().get(0).getCityId().equalsIgnoreCase("")) {
                                selected_city_id = Integer.parseInt(response.body().getData().get(0).getCityId());
                            }

                            getStates();


                        }
            }

            @Override
            public void onFailure(@NonNull Call<ChemistProfileModel> call, @NonNull Throwable t) {

            }
        });

    }

    private void getStates() {

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        apiInterfaceKapsool.getStates().enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(@NonNull Call<StateModel> call, @NonNull Response<StateModel> response) {
                pd.dismiss();
                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            stateNamesList.add(response.body().getData().get(i).getName());
                            stateIdList.add(Integer.parseInt(response.body().getData().get(i).getId()));
                        }

                        ArrayAdapter adb = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, stateNamesList);
                        adb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

                        for (int i = 0; i < stateIdList.size(); i++) {
                            if (stateIdList.get(i).equals(selected_state_id)) {
                                binding.companyStateSpinner.setSelection(i);
                                break;
                            }
                        }

                        getCites(selected_city_id);

                    }

            }

            @Override
            public void onFailure(@NonNull Call<StateModel> call, @NonNull Throwable t) {
                pd.dismiss();
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
                pd.dismiss();
                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            cityNamesList.add(response.body().getData().get(i).getName());
                            cityIdList.add(Integer.parseInt(response.body().getData().get(i).getId()));
                        }

                        ArrayAdapter adb = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, cityNamesList);
                        adb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                        binding.companyCitySpinner.setAdapter(adb);

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


                        Log.e("TAG", "onResponse() called with: Already selected city id  = [" + selected_city_id + "]");

                        for (int i = 0; i < cityIdList.size(); i++) {
                            if (cityIdList.get(i).equals(selected_city_id)) {
                                binding.companyCitySpinner.setSelection(i);
                                break;
                            }
                        }


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

    private void updateChemistProfile() {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.updateChemistProfile(
                session.getUserId(),
                binding.userName.getText().toString(),
                selectedStateName,
                String.valueOf(selected_state_id),
                selectedCityName,
                String.valueOf(selected_city_id),
                binding.userEmail.getText().toString(),
                binding.userAddress.getText().toString()
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Updated....", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(activity, "Failed...", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Failed...", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });


    }

}