package com.koipsool_new.ui.resume;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityAddProtforioBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.kapsoolModels.ProtfolioModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProtfolioActivity extends AppCompatActivity {

    private AddProtfolioActivity activity;
    private ActivityAddProtforioBinding binding;
    private Session session;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProtforioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        pd = new ProgressDialog(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.saveEductionCollge.setOnClickListener(view -> addProtfolio());

        getMyProtfolio();

    }

    private void getMyProtfolio() {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.getMyProtfolio(session.getUserId()).enqueue(new Callback<ProtfolioModel>() {
            @Override
            public void onResponse(@NonNull Call<ProtfolioModel> call, @NonNull Response<ProtfolioModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            ProtfolioModel.ProtfolioData data = response.body().getData().get(0);

                            if (!data.getBlogLink().equalsIgnoreCase("")) {
                                binding.blogLink.setText(data.getBlogLink());
                            }

                            if (!data.getGithubProfile().equalsIgnoreCase("")) {
                                binding.githubLink.setText(data.getGithubProfile());
                            }

                            if (!data.getPlayStoreLink().equalsIgnoreCase("")) {
                                binding.playStore.setText(data.getGithubProfile());
                            }

                            if (!data.getPortfolioLink().equalsIgnoreCase("")) {
                                binding.behanceLink.setText(data.getPortfolioLink());
                            }

                            if (!data.getOtherWork().equalsIgnoreCase("")) {
                                binding.otherLinks.setText(data.getOtherWork());
                            }

                        }


            }

            @Override
            public void onFailure(@NonNull Call<ProtfolioModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void addProtfolio() {
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.addProtfolio(
                session.getUserId(),
                binding.blogLink.getText().toString(),
                binding.githubLink.getText().toString(),
                binding.playStore.getText().toString(),
                binding.behanceLink.getText().toString(),
                binding.otherLinks.getText().toString()
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Protfolio Saved..!", Toast.LENGTH_SHORT).show();
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
}