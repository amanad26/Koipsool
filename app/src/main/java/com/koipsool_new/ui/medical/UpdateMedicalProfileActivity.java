package com.koipsool_new.ui.medical;

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
import com.koipsool_new.databinding.ActivityUpdateMedicalProfileBinding;
import com.koipsool_new.kapsoolModels.CityModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.StateModel;
import com.koipsool_new.model.MedicalProfileModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMedicalProfileActivity extends AppCompatActivity {

    private UpdateMedicalProfileActivity activity;
    private Session session;
    private ActivityUpdateMedicalProfileBinding binding;
    private ProgressDialog pd;

    private List<String> stateNamesList = new ArrayList<>();
    private List<String> cityNamesList = new ArrayList<>();
    private List<Integer> stateIdList = new ArrayList<>();
    private List<Integer> cityIdList = new ArrayList<>();
    private Integer selected_state_id = -1;
    private Integer selected_city_id = -1;
    private Integer selected_city_id_new = -1;
    private String selectedCityName = "";
    private String selectedStateName = "";
    private  MedicalProfileModel.MedicalProfileData data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateMedicalProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);



        data  = (MedicalProfileModel.MedicalProfileData) getIntent().getSerializableExtra("data");

        getStates();
        binding.userName.setText(data.getName());
        binding.userAddress.setText(data.getAddress());
        binding.userEmail.setText(data.getEmail());

        selectedCityName = data.getCity();
        selectedStateName = data.getState();


        getProfile();

        binding.updateProfile.setOnClickListener(view -> updateProfile());

    }

    private void updateProfile() {
        pd.show();

        Log.e("TAG", "updateProfile() called state id "+selected_state_id);
        Log.e("TAG", "updateProfile() called city  id "+selected_city_id);
        Log.e("TAG", "updateProfile() called state name "+selectedStateName);
        Log.e("TAG", "updateProfile() called city  name "+selectedCityName);


        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.updateMedicalProfile(
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
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }


    private void getProfile() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMedicalProfile(session.getUserId()).enqueue(new Callback<MedicalProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<MedicalProfileModel> call, @NonNull Response<MedicalProfileModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setImage(response.body().getPath() + response.body().getData().get(0).getImage());
                            session.setUserName(response.body().getData().get(0).getName());
                        }

            }

            @Override
            public void onFailure(@NonNull Call<MedicalProfileModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
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


                        if (!data.getStateId().equalsIgnoreCase("")) {
                            selected_state_id = Integer.parseInt(data.getStateId());

                            for (int i = 0; i < stateIdList.size(); i++) {
                                if (stateIdList.get(i).equals(selected_state_id)) {
                                    binding.companyStateSpinner.setSelection(i);
                                    selected_city_id_new = Integer.parseInt(data.getCityId());
                                    break;
                                }
                            }
                            getCites(Integer.parseInt(data.getStateId()));
                        }


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


                        Log.e("TAG", "onResponse() called with: Already selected city id  = [" + selected_city_id_new + "]");

                        for (int i = 0; i < cityIdList.size(); i++) {
                            if (cityIdList.get(i).equals(selected_city_id_new)) {
                                binding.companyCitySpinner.setSelection(i);
                                selected_city_id = selected_city_id_new  ;
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


}