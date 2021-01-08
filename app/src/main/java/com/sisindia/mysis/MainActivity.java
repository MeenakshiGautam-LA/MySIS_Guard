package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    AppCompatButton help_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        help_btn = findViewById(R.id.help);
        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SulabhLoanActivity.class));
                //startActivity(new Intent(MainActivity.this, SyncDataActivity.class));
                //startActivity(new Intent(MainActivity.this, DutyLeaveCalendarActivity.class));
                //startActivity(new Intent(MainActivity.this, SelfieActivity.class));
            }
        });
    }
}
