package com.koipsool_new.ui.pharma;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.databinding.FragmentHomeBinding;
import com.koipsool_new.kapsoolAdapters.MedicalUsersAdapter;
import com.koipsool_new.model.UsersListModel;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Activity activity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding  = FragmentHomeBinding.inflate(getLayoutInflater());

        activity = requireActivity();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMedicalUsersList();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMedicalUsersList();
    }

    private void getMedicalUsersList() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getUsersList("medical").enqueue(new Callback<UsersListModel>() {
            @Override
            public void onResponse(@NonNull Call<UsersListModel> call, @NonNull Response<UsersListModel> response) {
                binding.progress.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){

                            binding.recycelerView.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recycelerView.setAdapter(new MedicalUsersAdapter(activity, response.body().getData()));

                        }else {
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