package com.koipsool_new.ui.chemist;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.databinding.FragmentChemistBinding;
import com.koipsool_new.kapsoolAdapters.CompanyUsersAdapter;
import com.koipsool_new.model.UsersListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChemistFragment extends Fragment {

    FragmentChemistBinding binding;
    Activity activity;
    List<UsersListModel.UsersData> companyList = new ArrayList<>();
    List<UsersListModel.UsersData> filterdCompanyList = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChemistBinding.inflate(getLayoutInflater());

        activity = requireActivity();
        binding.swipeRefresh.setOnRefreshListener(this::getMedicalUsersList);

        binding.searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchCompany(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMedicalUsersList();
    }

    private void searchCompany(String key) {
        filterdCompanyList.clear();

        if (key.equalsIgnoreCase("")) {
            filterdCompanyList.addAll(companyList);
        } else {

            for (int i = 0; i < companyList.size(); i++) {
                if (companyList.get(i).getName().toLowerCase().contains(key.toLowerCase()))
                    filterdCompanyList.add(companyList.get(i));
            }

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            binding.recyclerView.setAdapter(new CompanyUsersAdapter(activity, filterdCompanyList));

        }


    }

    private void getMedicalUsersList() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getUsersList("pharma").enqueue(new Callback<UsersListModel>() {
            @Override
            public void onResponse(@NonNull Call<UsersListModel> call, @NonNull Response<UsersListModel> response) {
                binding.progress.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            companyList.addAll(response.body().getData());

                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recyclerView.setAdapter(new CompanyUsersAdapter(activity, companyList));

                        } else {
                            Toast.makeText(activity, "No Medical Users Found..!", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<UsersListModel> call, @NonNull Throwable t) {
                binding.progress.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

}