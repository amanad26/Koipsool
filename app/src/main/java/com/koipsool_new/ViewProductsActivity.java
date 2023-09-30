package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.koipsool_new.databinding.ActivityViewProductsBinding;

public class ViewProductsActivity extends AppCompatActivity {
private ActivityViewProductsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProductsActivity.this,ViewProductAllDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}