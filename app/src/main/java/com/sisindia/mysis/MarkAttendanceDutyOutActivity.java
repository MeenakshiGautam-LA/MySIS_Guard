package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MarkAttendanceDutyOutActivity extends AppCompatActivity implements View.OnClickListener {
TextView duty_out_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_duty_out);

        duty_out_txt=findViewById(R.id.duty_out_txt);
duty_out_txt.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent in = new Intent(MarkAttendanceDutyOutActivity.this,LeaveActivity.class);
        startActivity(in);

    }
}
