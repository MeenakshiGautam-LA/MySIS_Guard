package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class PunctualActivity extends AppCompatActivity {

    ImageView floatingImg,backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punctual_badge);

        floatingImg = findViewById(R.id.floatingImg);
        backBtn = findViewById(R.id.backBtn);
       /* floatingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PunctualActivity.this, BadgeActivity.class);
                startActivity(intent);
            }
        });*/
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PunctualActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
