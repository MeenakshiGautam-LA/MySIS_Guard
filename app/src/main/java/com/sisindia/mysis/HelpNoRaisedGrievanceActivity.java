package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HelpNoRaisedGrievanceActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_no_raised_grievance);

        btn=findViewById(R.id.btn_raise_grievance);

        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent in =new Intent(HelpNoRaisedGrievanceActivity.this,HelpContactUsActivity.class);
        startActivity(in);


    }


}
