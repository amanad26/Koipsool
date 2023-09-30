package com.koipsool_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityHelpAndSuuportBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpAndSuuportActivity extends AppCompatActivity {


    private Session session;
    private ActivityHelpAndSuuportBinding binding;
    private ProgressDialog pd;
    private HelpAndSuuportActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHelpAndSuuportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);


        binding.submitHelp.setOnClickListener(view -> {
            if (binding.heading.getText().toString().equalsIgnoreCase("")) {
                binding.heading.setError("Enter Heading");
                binding.heading.requestFocus();
            } else if (binding.queryEdit.getText().toString().equalsIgnoreCase("")) {
                binding.queryEdit.setError("Enter Query");
                binding.queryEdit.requestFocus();
            } else {
                pd.show();
                submitQuery();
            }
        });


    }

    private void submitQuery() {
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);
        apiInterfaceKapsool.helpAndSupport(
                session.getUserId(),
                binding.heading.getText().toString(),
                binding.queryEdit.getText().toString(),
                session.getType()
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Thank you for giving your valuable time..!", Toast.LENGTH_SHORT).show();
                            binding.queryEdit.setText("");
                            binding.heading.setText("");
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