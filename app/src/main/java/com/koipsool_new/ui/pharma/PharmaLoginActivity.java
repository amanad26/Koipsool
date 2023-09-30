package com.koipsool_new.ui.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.OtpVerificationActivity;

import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityPharmaLoginBinding;
import com.koipsool_new.kapsoolModels.LoginModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PharmaLoginActivity extends AppCompatActivity {

    private ActivityPharmaLoginBinding binding;
    private PharmaLoginActivity activity;
    private Session session;
    private String fcmId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPharmaLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        binding.signup.setOnClickListener(view -> {
            Intent intent = new Intent(PharmaLoginActivity.this, PharmaSignupActivity.class);
            startActivity(intent);
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            String fcm_id3 = instanceIdResult.getToken();
            // send it to server
            fcmId = fcm_id3;
            Log.e("refresh_tokentoken", fcm_id3);
        });

        binding.signUpButton.setOnClickListener(view -> {
            if (binding.mobileNumber.getText().toString().equalsIgnoreCase("")) {
                binding.mobileNumber.setError("Enter Mobile Number");
                binding.mobileNumber.requestFocus();
            } else {
                login(binding.mobileNumber.getText().toString());
            }
        });
    }

    private void login(String mobile) {
        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        apiInterfaceKapsool.pharmaLogin(
                mobile,
                fcmId
        ).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                try {
                    if (response.code() == 200)
                        if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                            session.setUser_id(response.body().getData().getId());
                            session.setOtp(String.valueOf(response.body().getData().getOtp()));
                            session.setType(response.body().getData().getType());
                            session.setUserName(response.body().getData().getName());
                            session.setEmail(response.body().getData().getEmail());

                            Toast.makeText(activity, response.body().getData().getOtp(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity, OtpVerificationActivity.class);
                            startActivity(intent);
                            pd.dismiss();
                        } else {
                            pd.dismiss();
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {
                    pd.dismiss();
                    e.getLocalizedMessage();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });
    }
}