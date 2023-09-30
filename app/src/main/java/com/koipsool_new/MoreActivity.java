package com.koipsool_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.Session.Session;
import com.koipsool_new.databinding.ActivityMoreBinding;
import com.koipsool_new.kapsoolModels.LogoutModel;
import com.koipsool_new.kapsoolModels.ProgressDialog;
import com.koipsool_new.ui.AboutActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreActivity extends AppCompatActivity {

    private ActivityMoreBinding binding;
    private MoreActivity activity;
    private Session session;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);
        binding.aboutLinear.setOnClickListener(view -> startActivity(new Intent(activity, AboutActivity.class)));
        binding.privacyLinear.setOnClickListener(view -> startActivity(new Intent(activity, PrivacyPolicyActivity.class)));
        binding.termsAndConditionTv.setOnClickListener(view -> startActivity(new Intent(activity, TermAndConditionActivity.class)));

        binding.logoutTv.setOnClickListener(view -> showDialog("1"));


    }

    private void showDialog(String type) {

        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.logout_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


        TextView dialogButtonYes = dialog.findViewById(R.id.btn_yes);
        TextView dialogButtonNo = dialog.findViewById(R.id.btn_no);
        TextView title_text = dialog.findViewById(R.id.title_text);

        if(type.equalsIgnoreCase("2"))  title_text.setText(R.string.delete_account);

        dialogButtonNo.setOnClickListener(v -> dialog.dismiss());
        dialogButtonYes.setOnClickListener(v -> {
           if(type.equalsIgnoreCase("1")) logout();
           else deleteAccount();
            dialog.dismiss();
        });

        dialog.show();

    }

    private  void logout(){
        pd.show();
        ApiInterfaceKapsool apiInterface = RetrofitClientKapsool.getClient(activity);
        apiInterface.pharmaLogout(session.getUserId()).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {

                pd.dismiss();
                try {

                    if (response.code() == 200)
                        if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                            session.logOut();
                            Intent intent = new Intent(activity, MedcastActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Toast.makeText(activity, "Logged Out Success", Toast.LENGTH_SHORT).show();
                        }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }


    private void deleteAccount() {
        pd.show();
        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(activity);

        apiInterfaceKapsool.deletePharmaAccount(session.getUserId()).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                try {

                    if (response.code() == 200)
                        if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                            session.logOut();
                            Intent intent = new Intent(activity, MedcastActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Toast.makeText(activity, "Account Deleted", Toast.LENGTH_SHORT).show();
                        }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }

}