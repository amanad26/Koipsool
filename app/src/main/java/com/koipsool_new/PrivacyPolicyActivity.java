package com.koipsool_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.model.PrivacyModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyActivity extends AppCompatActivity {

    TextView set_privacy_text;
    ImageView back_btn_id;
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);


        back_btn_id = findViewById(R.id.back_btn_id);
        progressBar = findViewById(R.id.progress_bar);

        get_privacy_policy();

        back_btn_id.setOnClickListener(view -> finish());


    }

    public  void get_privacy_policy(){
        ApiInterfaceKapsool apiInterface = RetrofitClientKapsool.getClient(PrivacyPolicyActivity.this);
        apiInterface.get_privacy_policy().enqueue(new Callback<PrivacyModel>() {
            @Override
            public void onResponse(@NonNull Call<PrivacyModel> call, @NonNull Response<PrivacyModel> response) {
                progressBar.setVisibility(View.GONE);
                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equals("true")) {

                            String about = response.body().getData().getDescription();
                            TextView textView = findViewById(R.id.privacy_webview);
                            textView.setText(Html.fromHtml(about));

                        } else {
                            Toast.makeText(PrivacyPolicyActivity.this, "Privacy Policy Not Available..", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PrivacyModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PrivacyPolicyActivity.this, "Failed To Load Data ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}