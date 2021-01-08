package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MarkAttendanceFitFaceActivity extends AppCompatActivity implements View.OnClickListener {
Button retake_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_fit_face);

        retake_btn=findViewById(R.id.retake_btn);


       retake_btn.setOnClickListener(this);






    }

    @Override
    public void onClick(View view) {
        Intent in =new Intent(MarkAttendanceFitFaceActivity.this,MarkAttendanceFlowSubmitSelfieActivity.class);
        startActivity(in);
    }
}
