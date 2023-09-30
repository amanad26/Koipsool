package com.koipsool_new.ui.pharma;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.FragmentBillingDetailBinding;
import com.koipsool_new.kapsoolModels.CityModel;
import com.koipsool_new.kapsoolModels.GetBillingAddressModel;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.StateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillingDetailFragment extends Fragment {

    private FragmentBillingDetailBinding binding;
    private Activity activity;
    private List<String> stateNamesList = new ArrayList<>();
    private List<String> cityNamesList = new ArrayList<>();
    private List<Integer> stateIdList = new ArrayList<>();
    private List<Integer> cityIdList = new ArrayList<>();
    private int selected_state_id = -1;
    private int selected_city_id = -1;
    private ProgressDialog pd;
    private Session session;
    private  String billId = "";


    public BillingDetailFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBillingDetailBinding.inflate(getLayoutInflater());

        activity = requireActivity();
        pd = new ProgressDialog(activity);
        session = new Session(activity);

        getPharmaBillingAddress();

        binding.addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.companyAddress.getText().toString().equalsIgnoreCase("")) {
                    binding.companyAddress.setError("Enter Company Address");
                    binding.companyAddress.requestFocus();
                }else if (binding.companyPhone.getText().toString().equalsIgnoreCase("")) {
                    binding.companyPhone.setError("Enter Company Mobile Number");
                    binding.companyPhone.requestFocus();
                } else if (binding.companyEmail.getText().toString().equalsIgnoreCase("")) {
                    binding.companyEmail.setError("Enter Company Email..!");
                    binding.companyEmail.requestFocus();
                }else if (selected_state_id == -1) {
                    Toast.makeText(activity, "Select State..!", Toast.LENGTH_SHORT).show();
                }else if (selected_city_id == -1) {
                    Toast.makeText(activity, "Select City..!", Toast.LENGTH_SHORT).show();
                }else if (!emailValidator(binding.companyEmail.getText().toString())) {
                    binding.companyEmail.setError("Invalid  Email..!");
                    binding.companyEmail.requestFocus();
                }else addCompanyAddress();

            }
        });


        binding.updateAddress.setOnClickListener(view -> updateCompanyBillingAddress());

        return binding.getRoot();
    }

    private void addCompanyAddress() {

        if (binding.companyDrugLicenceNumber.getText().toString().equalsIgnoreCase("")) {
            binding.companyDrugLicenceNumber.setText("0");
        }

        String gstNo = "0";
        if(!binding.companyGstnNumber.getText().toString().equalsIgnoreCase(""))
            gstNo = binding.companyGstnNumber.getText().toString();

        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addPharmacyBillingAddress(
                session.getUserId(),
                binding.companyPhone.getText().toString(),
                binding.companyEmail.getText().toString(),
                binding.companyAddress.getText().toString(),
                String.valueOf(selected_state_id),
                String.valueOf(selected_city_id),
                gstNo,
                binding.companyDrugLicenceNumber.getText().toString()
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Billing Address Added ", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }else {
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.toString() + "]");
            }
        });


    }


    private  void updateCompanyBillingAddress(){
        Log.e("TAG", "updateCompanyBillingAddress() called State "+selected_state_id);
        Log.e("TAG", "updateCompanyBillingAddress() called City "+selected_city_id);
        Log.e("TAG", "updateCompanyBillingAddress() called Email "+binding.companyEmailEdit.getText().toString());
        Log.e("TAG", "updateCompanyBillingAddress() called Address "+binding.companyAddressEdit.getText().toString());
        Log.e("TAG", "updateCompanyBillingAddress() called GST No "+binding.companyGstnNumberEdit.getText().toString());
        Log.e("TAG", "updateCompanyBillingAddress() called Licence No "+binding.companyDrugLicenceNumberEdit.getText().toString());
        Log.e("TAG", "updateCompanyBillingAddress() called Phone No "+binding.companyPhoneEdit.getText().toString());

        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.updatePharmaBillingAddress(
                billId,
                binding.companyPhoneEdit.getText().toString(),
                binding.companyAddressEdit.getText().toString(),
                binding.companyGstnNumberEdit.getText().toString(),
                String.valueOf(selected_state_id),
                String.valueOf(selected_city_id),
                binding.companyDrugLicenceNumberEdit.getText().toString()
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {

                progressDialog.dismiss();
                if (response.code() == 200 && response.body() != null)
                    if(response.body().getResult().equalsIgnoreCase("true")) {
                        Toast.makeText(activity, "Address Updated Successfully....!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(activity, "Failed.!", Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });



    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void getPharmaBillingAddress() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getPharmacyBillingAddress(session.getUserId()).enqueue(new Callback<GetBillingAddressModel>() {
            @Override
            public void onResponse(@NonNull Call<GetBillingAddressModel> call, @NonNull Response<GetBillingAddressModel> response) {
                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        GetBillingAddressModel.BillingAddressData data = response.body().getData().get(0);

                        getStates();
                        binding.linear2.setVisibility(View.VISIBLE);
                        binding.linear1.setVisibility(View.GONE);
                        binding.addAddress.setVisibility(View.GONE);
                        binding.updateAddress.setVisibility(View.VISIBLE);

                        billId = data.getId();

                        binding.companyEmailEdit.setText(data.getEmail());
                        binding.companyAddressEdit.setText(data.getAddress());
                        binding.companyPhoneEdit.setText(data.getMobile());
                        binding.companyGstnNumberEdit.setText(data.getGstNo());
                        binding.companyDrugLicenceNumberEdit.setText(data.getDrugLicenseNo());

                        String stateId = data.getStateId();
                        String cityId = data.getCityId();
                        selected_state_id = Integer.parseInt(stateId);
                        selected_city_id = Integer.parseInt(cityId);

                        Log.e("TAG", "onResponse() called with: stateId = [" + stateId + "], cityId = [" + cityId + "]");

                        if (!stateId.equalsIgnoreCase("")) {
                            int pos = 0;
                            for (int i = 0; i < stateIdList.size(); i++) {
                                if (stateId.equalsIgnoreCase(stateIdList.get(i).toString())) {
                                    pos = i;
                                    break;
                                }
                            }

                            getCites2(stateId, cityId);
                            binding.companyStateEdit.setSelection(pos);

                        }

                    } else {
                         getStates();
                        binding.linear2.setVisibility(View.GONE);
                        binding.linear1.setVisibility(View.VISIBLE);
                        binding.addAddress.setVisibility(View.VISIBLE);
                        binding.updateAddress.setVisibility(View.GONE);
                    }

            }

            @Override
            public void onFailure(@NonNull Call<GetBillingAddressModel> call, @NonNull Throwable t) {
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


                        binding.companyStateEdit.setAdapter(adb);
                        binding.companyStateEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                        binding.companyStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_state_id = stateIdList.get(position);
                                getCites(stateIdList.get(position));
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
                        binding.companyCitySpinnerEdit.setAdapter(adb);

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

    private void getCites2(String ci, String cityId) {


        List<Integer> cityIds = new ArrayList<>();
        List<String> cityNames2 = new ArrayList<>();
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getCities(ci).enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(@NonNull Call<CityModel> call, @NonNull Response<CityModel> response) {

                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        String cityName = "";
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            cityNames2.add(response.body().getData().get(i).getName());
                            cityIds.add(Integer.parseInt(response.body().getData().get(i).getId()));
                        }
                        ArrayAdapter adb = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, cityNames2);
                        adb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        binding.companyCitySpinnerEdit.setAdapter(adb);

                        binding.companyCitySpinnerEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_city_id = cityIds.get(position);
                                Log.e("TAG", "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], selected_city_id = [" + selected_city_id + "]");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        Log.e("TAG", "onResponse() called with: City  = [" + cityId + "]");
                        int pos = 0;
                        for (int i = 0; i < cityIds.size(); i++) {
                            if (cityId.equalsIgnoreCase(cityIds.get(i).toString())) {
                                pos = i;
                                Log.e("TAG", "onResponse() called with: City Name = [" + cityNames2.get(i) + "]");
                                break;
                            }
                        }
                        Log.e("TAG", "City Position = [" + pos + "], response = [" + cityIds.get(pos) + "]");

                        binding.companyCitySpinnerEdit.setSelection(pos);
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