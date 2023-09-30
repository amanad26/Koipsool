package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.koipsool_new.ui.chemist.ChatchemistActivity;

public class ViewBillActivity extends AppCompatActivity {
LinearLayout view_bill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        view_bill = findViewById( R.id.view_bill);
        view_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewBillActivity.this, ChatchemistActivity.class);
                startActivity(intent);
            }
        });
    }
}