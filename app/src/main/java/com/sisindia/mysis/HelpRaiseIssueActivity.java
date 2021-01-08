package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HelpRaiseIssueActivity extends AppCompatActivity implements View.OnClickListener {
Button btn_grievance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_raise_issue);

        btn_grievance=findViewById(R.id.btn_raise_grievance);

      btn_grievance.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(HelpRaiseIssueActivity.this,HelpRaiseIssueDescriptionActivity.class);
        startActivity(in);
    }
}
