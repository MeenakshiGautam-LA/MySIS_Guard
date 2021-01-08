package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelpContactUsActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ArrayList<HelpContactEntity> helpContactArrayList;
    HelpContactEntity helpContactEntity;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_contact);

        recyclerView = findViewById(R.id.recyclerview);

        relativeLayout=findViewById(R.id.relative);

        helpContactArrayList = new ArrayList<>();

        getData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        HelpContactAdapter customAdapter = new HelpContactAdapter(HelpContactUsActivity.this, helpContactArrayList);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        relativeLayout.setOnClickListener(this);


    }

    private void getData() {

        helpContactEntity = new HelpContactEntity("Area Manager","Pawan Kumar Singh","9876543210");
        helpContactArrayList.add(helpContactEntity);

       helpContactEntity = new HelpContactEntity("Supervisor","Pawan Kumar Singh","9876543210");
        helpContactArrayList.add(helpContactEntity);

      /*  attendanceEntity = new AttendanceEntity("This is the dummy text for the question related with my attendance");
        attendancearrayList.add(attendanceEntity);


        attendanceEntity = new AttendanceEntity("This is the dummy text for the question related with my attendance");
        attendancearrayList.add(attendanceEntity);*/
    }

    @Override
    public void onClick(View view) {
        Intent in =new Intent(HelpContactUsActivity.this,HelpTakeTourActivity.class);
        startActivity(in);

    }
}
