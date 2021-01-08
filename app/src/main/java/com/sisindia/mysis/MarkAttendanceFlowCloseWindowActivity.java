package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MarkAttendanceFlowCloseWindowActivity extends AppCompatActivity implements View.OnClickListener {
TextView close_txt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_close_window);

        close_txt=findViewById(R.id.close_txt);

        close_txt.setOnClickListener(this);









    }

    @Override
    public void onClick(View view) {
        Intent in =new Intent(MarkAttendanceFlowCloseWindowActivity.this,SyncDataActivity.class);
        startActivity(in);
    }
}
