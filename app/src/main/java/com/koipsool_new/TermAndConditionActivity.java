package com.koipsool_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.koipsool_new.RetofitSetUp.ApiInterfaceKapsool;
import com.koipsool_new.RetofitSetUp.RetrofitClientKapsool;
import com.koipsool_new.databinding.ActivityTermAndConditionBinding;
import com.koipsool_new.model.TermConditionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermAndConditionActivity extends AppCompatActivity {

    ActivityTermAndConditionBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermAndConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backTermandcon.setOnClickListener(view -> finish());

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(TermAndConditionActivity.this);

        apiInterfaceKapsool.term_conditiondata().enqueue(new Callback<TermConditionModel>() {
            @Override
            public void onResponse(@NonNull Call<TermConditionModel> call, @NonNull Response<TermConditionModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {

                    if(response.code() == 200)
                    if (response.body() != null) {
                        if(response.body().getResult().equals("true")){

                            String about = response.body().getData().getDescription();
                            TextView textView = findViewById(R.id.about_webview);
                            textView.setText(Html.fromHtml(about));
                        }
                        else {
                            Toast.makeText(TermAndConditionActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<TermConditionModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(TermAndConditionActivity.this, "Failed To Load Terms And Conditions.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}