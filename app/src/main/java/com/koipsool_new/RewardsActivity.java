package com.koipsool_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.koipsool_new.ui.chemist.ChatchemistActivity;

public class RewardsActivity extends AppCompatActivity {
LinearLayout claim_reward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        claim_reward = findViewById(R.id.claim_reward);
        claim_reward. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RewardsActivity. this, ChatchemistActivity.class);
                startActivity(intent);
            }
        });
    }
}