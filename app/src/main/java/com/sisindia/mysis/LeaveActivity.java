package com.sisindia.mysis;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class LeaveActivity extends AppCompatActivity implements View.OnClickListener {
TextView txt_leave;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

       txt_leave=findViewById(R.id.leave_txt);

       txt_leave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        Intent in = new Intent(LeaveActivity.this,LeaveLandingActivity.class);
//        startActivity(in);

    }
}
