package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AnnualKitReplacmentActivity extends AppCompatActivity {

    TextView howToSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_kit_replacment);

        howToSubmit = findViewById(R.id.howToSubmit);
        howToSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnnualKitReplacmentActivity.this, Sync_landing_img.class));
            }
        });
    }
}
