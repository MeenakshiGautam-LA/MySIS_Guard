package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HelpIssueDetailsActivity extends AppCompatActivity implements View.OnClickListener {
Button detail_correct_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_issue_details);


detail_correct_btn=findViewById(R.id.detail_correct_btn);
detail_correct_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(HelpIssueDetailsActivity.this,HelpIssueChatActivity.class);

        startActivity(in);
    }
}
