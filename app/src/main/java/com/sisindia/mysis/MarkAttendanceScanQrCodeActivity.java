package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MarkAttendanceScanQrCodeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView duty_in_txt;
    ImageView icon_circular_guard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_scan_qr_code);

        duty_in_txt = findViewById(R.id.duty_in_txt);
        icon_circular_guard = findViewById(R.id.icon_circular_guard);
        duty_in_txt.setOnClickListener(this);
        icon_circular_guard.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == duty_in_txt.getId()){
            Intent in = new Intent(MarkAttendanceScanQrCodeActivity.this,MarkAttendanceConfirmDutyActivity.class);
            startActivity(in);
        }else if (view.getId() == icon_circular_guard.getId()){
//            startActivity(new Intent(MarkAttendanceScanQrCodeActivity.this, SelfieActivity.class));
        }

        Intent in = new Intent(MarkAttendanceScanQrCodeActivity.this, MarkAttendanceFitFaceActivity.class);
        startActivity(in);
    }
}
