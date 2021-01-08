package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class YourBadgeDetails extends AppCompatActivity {

    CardView alert_badge,unearn_badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_badge_details);

        alert_badge = findViewById(R.id.alert_badge);
        unearn_badge = findViewById(R.id.unearn_badge);
        alert_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(YourBadgeDetails.this,LeaveActivity.class));
            }
        });

        unearn_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YourBadgeDetails.this,MainActivity.class));
            }
        });
    }
}
