package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MarkAttendanceFlowSubmitSelfieActivity extends AppCompatActivity implements View.OnClickListener {
TextView submit_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_submit_selfie);

      submit_btn=findViewById(R.id.retake_btn);

      submit_btn.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        Intent in =new Intent(MarkAttendanceFlowSubmitSelfieActivity.this,MarkAttendanceFlowCloseWindowActivity.class);
        startActivity(in);
    }
}
