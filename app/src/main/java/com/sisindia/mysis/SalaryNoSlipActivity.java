package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SalaryNoSlipActivity extends AppCompatActivity implements View.OnClickListener {
TextView txt_salary_slip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_salary_slip);

        txt_salary_slip=findViewById(R.id.txt_salary_slip);

        txt_salary_slip.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(SalaryNoSlipActivity.this,FAQLandingActivity.class);
        startActivity(in);

    }



}
