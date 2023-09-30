package com.koipsool_new.ui;

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
import com.koipsool_new.R;
import com.koipsool_new.model.AboutModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {


    ImageView back_about ;
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        back_about = findViewById(R.id.back_about);
        progressBar = findViewById(R.id.progress_bar);
        getAbout();
        back_about.setOnClickListener(view -> finish());
    }
    private void getAbout() {

        ApiInterfaceKapsool apiInterfaceKapsool = RetrofitClientKapsool.getClient(AboutActivity.this);

        apiInterfaceKapsool.getAbout().enqueue(new Callback<AboutModel>() {
            @Override

            public void onResponse(@NonNull Call<AboutModel> call, @NonNull Response<AboutModel> response) {
                progressBar.setVisibility(View.GONE);
                try {

                    if(response.code() == 200)
                        if (response.body() != null) {
                            if(response.body().getResult().equals("true")){
                                AboutModel.AboutData data =  response.body().getData();
                                String about = data.getDescription();
                                TextView textView = findViewById(R.id.about_webview);
                                textView.setText(Html.fromHtml(about));
                            }
                            else {
                                Toast.makeText(AboutActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AboutModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AboutActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}