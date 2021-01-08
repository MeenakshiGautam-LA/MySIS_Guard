package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SalarySlipLandingActivity extends AppCompatActivity implements View.OnClickListener {

RelativeLayout relative1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_slip_landing);

        relative1=findViewById(R.id.relative1);

        relative1.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(SalarySlipLandingActivity.this,SalaryNoSlipActivity.class);
        startActivity(in);

    }


}
