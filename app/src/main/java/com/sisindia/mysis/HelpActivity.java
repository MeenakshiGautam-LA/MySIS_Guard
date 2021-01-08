package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout contactHelpLinear,faqHelpLinear,tourHelpLinear,grievanceHelpLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        contactHelpLinear = findViewById(R.id.contactHelpLinear);
        faqHelpLinear = findViewById(R.id.faqHelpLinear);
        tourHelpLinear = findViewById(R.id.tourHelpLinear);
        grievanceHelpLinear = findViewById(R.id.grievanceHelpLinear);

        contactHelpLinear.setOnClickListener(this);
        faqHelpLinear.setOnClickListener(this);
        tourHelpLinear.setOnClickListener(this);
        grievanceHelpLinear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contactHelpLinear:
                startActivity(new Intent(HelpActivity.this, HelpContactUsActivity.class));
                break;
            case R.id.faqHelpLinear:
                startActivity(new Intent(HelpActivity.this, FAQLandingActivity.class));
                break;
            case R.id.tourHelpLinear:
                startActivity(new Intent(HelpActivity.this, HelpTakeTourActivity.class));
                break;
            case R.id.grievanceHelpLinear:
                startActivity(new Intent(HelpActivity.this, HelpIssueActivity.class));
                break;
        }
    }
}
