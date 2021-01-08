package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SalarySlipActivity extends AppCompatActivity implements View.OnClickListener {
TextView txt_salary;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salaryslip);

        txt_salary=findViewById(R.id.txt_salary);

        txt_salary.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(SalarySlipActivity.this,SalarySlipLandingActivity.class);
        startActivity(in);

    }
}
