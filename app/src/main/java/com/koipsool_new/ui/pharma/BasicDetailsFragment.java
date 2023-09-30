package com.koipsool_new.ui.pharma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.FragmentBasicDetailBinding;
import com.koipsool_new.kapsoolModels.CompanyProfileModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BasicDetailsFragment extends Fragment {

    private ProgressDialog pd;
    FragmentBasicDetailBinding binding;
    private Activity activity;
    private Session session ;


    public BasicDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBasicDetailBinding.inflate(getLayoutInflater());

        activity = requireActivity();
        pd = new ProgressDialog(activity);
        session = new Session(activity);

        getCompanyProfile();

        binding.addBtn.setOnClickListener(view -> {
             startActivity(new Intent(activity, UpdateCompanyProfileActivity.class));
        });

        return binding.getRoot();
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
                            binding.gstNo.setText(data.getGstNo());
                            binding.partyPinCode.setText(data.getPinCode());
                            binding.companyCitySpinner.setText(data.getCityName());
                            binding.companyStateSpinner.setText(data.getStateName());
                            binding.partyContactNumber.setText(data.getAddress());
                            binding.partyDrugLicence.setText(data.getDrugsLicenceImage());
                            binding.companyName.setText(data.getCompanyName());

                            session.setCity(data.getCityId());
                            session.setState(data.getStateId());

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


}