package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HelpMarkAttendanceFlowClickDutyInActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markattendance_flow_click_dutyin);

        txt_next = findViewById(R.id.next_txt);


        txt_next.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(HelpMarkAttendanceFlowClickDutyInActivity.this, MarkAttendanceScanQrCodeActivity.class);
        startActivity(in);
    }
}
