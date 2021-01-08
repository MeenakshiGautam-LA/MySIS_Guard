package com.sisindia.mysis;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MarkAttendanceScanCode extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout relativeLayout;
    ImageView icon_circular_guard;
    LinearLayout scanOrCodeLinear;
    ImageView cancelImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_scan_qr_code);
        relativeLayout = findViewById(R.id.relative);
        icon_circular_guard = findViewById(R.id.icon_circular_guard);
        cancelImg = findViewById(R.id.cancelImg);
        relativeLayout.setOnClickListener(this);
        icon_circular_guard.setOnClickListener(this);
        cancelImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelImg:
//                startActivity(new Intent(MarkAttendanceScanCode.this, MarkAttendanceDutyInActivity.class));
                break;
            case R.id.icon_circular_guard:
//                startActivity(new Intent(MarkAttendanceScanCode.this, SelfieActivity.class));
                break;
        }
       /* if(v.getId() == relativeLayout.getId()){
            Intent in = new Intent(MarkAttendanceScanCode.this,MarkAttendanceConfirmDutyActivity.class);
            startActivity(in);
        }else if (v.getId() == icon_circular_guard.getId()){
            startActivity(new Intent(MarkAttendanceScanCode.this, SelfieActivity.class));
        }*/



    }
}
