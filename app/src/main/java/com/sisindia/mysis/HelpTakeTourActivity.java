package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HelpTakeTourActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView icon_magnifying_glass;
    LinearLayout how_to_make_LY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_take_tour);

        icon_magnifying_glass = findViewById(R.id.icon_magnifying_glass);
        how_to_make_LY = findViewById(R.id.how_to_make_LY);

        how_to_make_LY.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(HelpTakeTourActivity.this, HelpMarkAttendanceFlowClickDutyInActivity.class);
        startActivity(in);
    }
}
