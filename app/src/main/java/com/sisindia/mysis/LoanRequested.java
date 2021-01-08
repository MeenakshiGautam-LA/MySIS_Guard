package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoanRequested extends AppCompatActivity {

    Button contactUsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_requested);

        contactUsBtn = findViewById(R.id.contactUsBtn);
        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoanRequested.this, AnnualKitReplacmentActivity.class));
            }
        });
    }
}
