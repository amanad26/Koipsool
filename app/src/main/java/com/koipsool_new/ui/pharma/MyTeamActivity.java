package com.koipsool_new.ui.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityMyTeamBinding;
import com.koipsool_new.kapsoolAdapters.MembersAdapter;
import com.koipsool_new.kapsoolModels.TeamMemberModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTeamActivity extends AppCompatActivity {

    private MyTeamActivity activity;
    private ActivityMyTeamBinding binding;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        getMyTeamMember();
        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.swipeRefresh.setOnRefreshListener(() -> getMyTeamMember());


    }

    private void getMyTeamMember() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        apiInterfaceKapsool.getMyTeamMember(session.getUserId()).enqueue(new Callback<TeamMemberModel>() {
            @Override
            public void onResponse(@NonNull Call<TeamMemberModel> call, @NonNull Response<TeamMemberModel> response) {
                binding.memberProgress.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                binding.memberRecycler.setLayoutManager(new LinearLayoutManager(activity));
                                binding.memberRecycler.setAdapter(new MembersAdapter(activity, response.body().getData()));
                                binding.memberProgress.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(activity, "No Members Found..!", Toast.LENGTH_SHORT).show();
                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TeamMemberModel> call, @NonNull Throwable t) {
                binding.memberProgress.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

}