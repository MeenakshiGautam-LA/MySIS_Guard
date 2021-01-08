package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class YourBadge extends AppCompatActivity {

    ImageView discpline_logo,team_work_logo,punctual_logo,alert_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_badge);

        discpline_logo = findViewById(R.id.discpline_logo);
        team_work_logo = findViewById(R.id.team_work_logo);
        punctual_logo = findViewById(R.id.punctual_logo);
        alert_logo = findViewById(R.id.alert_logo);

        discpline_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YourBadge.this, YourBadgeDetails.class));
            }
        });

        team_work_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YourBadge.this, MarkDutyRejectedActivity.class));
            }
        });

        punctual_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(YourBadge.this, Notification.class));
            }
        });

        alert_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YourBadge.this, FaqHelpActivity.class));
            }
        });
    }
}
