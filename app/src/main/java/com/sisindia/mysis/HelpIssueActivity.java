package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelpIssueActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ArrayList<AttendanceEntity> attendancearrayList;
    AttendanceEntity attendanceEntity;

     Button raise_grievance_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_issue);
        recyclerView = findViewById(R.id.recyclerview);

        raise_grievance_btn = findViewById(R.id.btn_raise_grievance);

        attendancearrayList = new ArrayList<>();

        getData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        ContactsAdapter customAdapter = new ContactsAdapter(HelpIssueActivity.this, attendancearrayList);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
  raise_grievance_btn.setOnClickListener(this);

    }
    private void getData() {

        attendanceEntity=new AttendanceEntity("This is the dummy text for the question related with my attendance");
        attendancearrayList.add(attendanceEntity);

        attendanceEntity=new AttendanceEntity("This is the dummy text for the question related with my attendance");
        attendancearrayList.add(attendanceEntity);

        attendanceEntity=new AttendanceEntity("This is the dummy text for the question related with my attendance");
        attendancearrayList.add(attendanceEntity);


        attendanceEntity=new AttendanceEntity("This is the dummy text for the question related with my attendance");
        attendancearrayList.add(attendanceEntity);
    }

    @Override
    public void onClick(View view) {
      //  Intent in = new Intent(HelpIssueActivity.this,HelpIssueChatActivity.class);

        Intent in = new Intent(HelpIssueActivity.this,HelpIssueDetailsActivity.class);

        startActivity(in);
    }
}