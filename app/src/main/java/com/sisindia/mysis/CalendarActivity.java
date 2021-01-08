package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {
     TextView txt_calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

       txt_calendar=findViewById(R.id.txt_calendar);

       txt_calendar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent in = new Intent(CalendarActivity.this,CalendarLeaveActivity.class);
        startActivity(in);





    }
}
