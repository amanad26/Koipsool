package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.koipsool_new.databinding.ActivityViewinternshipDetailBinding;

public class ViewinternshipDetailActivity extends AppCompatActivity {
    ActivityViewinternshipDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewinternshipDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewinternshipDetailActivity.this, SucessActivity.class);
                startActivity(intent);
                ;
            }
        });

    }

   /* private void showAlertBox() {
        new AlertDialog.Builder(ViewinternshipDetailActivity.this)
                .setTitle("your internship has been saved successfully")
                .setIcon(R.drawable.successfylly)

                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     *//*   Intent intent = new Intent(ViewinternshipDetailActivity.this,InternshipUserActivity.class);
                        startActivity(intent);*//*

                    }

                }).show();
    }*/
}
