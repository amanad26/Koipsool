package com.koipsool_new.ui.pharma;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityUpdateCompanyProfileBinding;
import com.koipsool_new.kapsoolModels.CityModel;
import com.koipsool_new.kapsoolModels.CompanyProfileModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.StateModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCompanyProfileActivity extends AppCompatActivity {

    private ActivityUpdateCompanyProfileBinding binding;
    private UpdateCompanyProfileActivity activity;
    private Session session;
    private ProgressDialog pd;
    private final ArrayList<String> stateNamesList = new ArrayList<>();
    private final ArrayList<Integer> cityIdList = new ArrayList<>();
    private final ArrayList<Integer> stateIdList = new ArrayList<>();
    private final ArrayList<String> cityNamesList = new ArrayList<>();
    private int selected_state_id = -1;
    private int selected_city_id = -1;
    private String GST = "";
    private String GSTNumber = "";
    private String Selling = "";
    private String SellingOther = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCompanyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        pd = new ProgressDialog(activity);
        session = new Session(activity);
        setGstAdapter();
        setSellingAdapter();
        getStates();

        getCompanyProfile();

//        if (!session.getState().equalsIgnoreCase("")) {
//            selected_state_id = Integer.parseInt(session.getState());
//        }
//
//        if (!session.getCity().equalsIgnoreCase("")) {
//            selected_city_id = Integer.parseInt(session.getCity());
//            getCites(selected_state_id);
//        }

        binding.addBtn.setOnClickListener(view -> {
            if (isValidate()) {

                if (Selling.equalsIgnoreCase("Other State")) {
                    if (binding.sellingOther.getText().toString().equalsIgnoreCase("")) {
                        binding.sellingOther.setError("");
                        binding.sellingOther.requestFocus();
                    } else {
                        addCompanyProfile();
                    }
                } else {
                    binding.sellingOther.setText("0");
                    addCompanyProfile();
                }
            }
        });

    }

    private void addCompanyProfile() {

    }

    private  void getCompanyProfile(){
        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getCompanyProfile(session.getUserId()).enqueue(new Callback<CompanyProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<CompanyProfileModel> call, @NonNull Response<CompanyProfileModel> response) {
                pd.dismiss();
                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            CompanyProfileModel.CompanyProfileData data = response.body().getData().get(0);
                            binding.partyName.setText(data.getName());
                            binding.partyPinCode.setText(data.getPinCode());
                            binding.partyContactNumber.setText(data.getAddress());
                            binding.partyDrugLicence.setText(data.getDrugsLicenceImage());
                            binding.companyName.setText(data.getCompanyName());

                            if(data.getGstNo().equalsIgnoreCase("")){
                                GST = "not applicable";
                            }else {
                                GST = "applicable";
                                binding.gstNo.setVisibility(View.VISIBLE);
                            }

                            session.setCity(data.getCityId());
                            session.setState(data.getStateId());

                            for (int  i =0 ; i<stateIdList.size() ; i++){
                                if(Integer.parseInt(data.getStateId()) == stateIdList.get(i)){
                                    binding.companyStateSpinner.setSelection(i);
                                    selected_state_id = Integer.parseInt(data.getStateId());
                                    getCites(selected_state_id);
                                }
                            }

                            Log.e("TAG", "onResponse() called with: Data  = [" + data.toString() + "]");

                        }
            }

            @Override
            public void onFailure(@NonNull Call<CompanyProfileModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });



    }

    private void setGstAdapter() {

        List<String> arrId = new ArrayList<>();
        arrId.add("GST*");
        arrId.add("Applicable");
        arrId.add("Not Applicable");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, arrId);
        binding.gstSpinner.setAdapter(spinnerAdapter);
        binding.gstSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (item.toString().equalsIgnoreCase("Applicable")) {
                    binding.gstNo.setVisibility(View.VISIBLE);
                    GST = "Applicable";
                } else {
                    GST = "Not Applicable";
                    binding.gstNo.setVisibility(View.GONE);
                }
            }
        });


    }

    private void setSellingAdapter() {

        List<String> arryId = new ArrayList<>();
        arryId.add("Selling In");
        arryId.add("Same State");
        arryId.add("Other State");


        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, arryId);
        binding.sellingIn.setAdapter(spinneradapter);
        binding.sellingIn.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Selling = arryId.get(position);
                if (item.toString().equalsIgnoreCase("Other State")) {
                    binding.sellingOther.setVisibility(View.VISIBLE);
                } else {
                    binding.sellingOther.setVisibility(View.GONE);
                }
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

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            stateNamesList.add(response.body().getData().get(i).getName());
                            stateIdList.add(Integer.parseInt(response.body().getData().get(i).getId()));
                        }

                        ArrayAdapter adb = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, stateNamesList);
                        adb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        pd.dismiss();
                        binding.companyStateSpinner.setAdapter(adb);

                        int statePos = 0 ;
                        for (int i = 0; i < stateIdList.size(); i++) {
                            if(stateIdList.get(i) == selected_state_id)
                               statePos  =  i ;
                        }

                        binding.companyStateSpinner.setAdapter(adb);
                        binding.companyStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    selected_state_id = stateIdList.get(position);
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
                                Log.e("TAG", "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], selected_city_id = [" + selected_city_id + "]");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        for (int i = 0; i < cityIdList.size(); i++) {
                            if(cityIdList.get(i) == selected_city_id){
                                selected_city_id = Integer.parseInt(session.getCity());
                                binding.companyCitySpinner.setSelection(i);
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

    private boolean isValidate() {
        if (binding.partyAddress.getText().toString().equalsIgnoreCase("")) {
            binding.partyAddress.setError("Enter Party Address");
            binding.partyAddress.requestFocus();
            return false;
        } else if (binding.partyContactNumber.getText().toString().equalsIgnoreCase("")) {
            binding.partyContactNumber.setError("Enter Party Contact Number");
            binding.partyContactNumber.requestFocus();
            return false;
        } else if (binding.partyName.getText().toString().equalsIgnoreCase("")) {
            binding.partyName.setError("Enter Party Name Number");
            binding.partyName.requestFocus();
            return false;
        } else if (binding.partyPinCode.getText().toString().equalsIgnoreCase("")) {
            binding.partyPinCode.setError("Enter Party Pin Code Number");
            binding.partyPinCode.requestFocus();
            return false;
        } else if (GST.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select GST..!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Selling.equalsIgnoreCase("")) {
            Toast.makeText(activity, "Select Selling In..!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selected_state_id == -1) {
            Toast.makeText(activity, "Select State..!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selected_city_id == -1) {
            Toast.makeText(activity, "Select City..!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }
}