package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class CalendarLeaveActivity extends AppCompatActivity implements View.OnClickListener {
RelativeLayout relativeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_leave);


        relativeLayout=findViewById(R.id.relative);

        relativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(CalendarLeaveActivity.this,CalendarDayWiseDetailActivity.class);
        startActivity(in);

    }
}
