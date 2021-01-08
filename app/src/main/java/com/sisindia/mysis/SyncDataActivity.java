package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sisindia.mysis.Adapter.PendingSyncToServerAdapter;


import java.util.ArrayList;
import java.util.List;

public class SyncDataActivity extends AppCompatActivity {

    RecyclerView sync_RV;
    PendingSyncToServerAdapter syncDataAdapter;
    List<String> list = new ArrayList<>();
    List<String> syncDataHeadinglist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);

        list.add("Pawan kumar");
        list.add("Ankit kumar");
        list.add("Prabhat kumar");
        list.add("Manoj kumar");
        list.add("Saroj kumar");

        syncDataHeadinglist.add("Attendance");
        syncDataHeadinglist.add("Attendance");
        syncDataHeadinglist.add("Leave Request");


//        sync_RV = findViewById(R.id.sync_RV);
//
//        syncDataAdapter = new PendingSyncToServerAdapter(SyncDataActivity.this,list,syncDataHeadinglist);
//        sync_RV.setAdapter(syncDataAdapter);


    }
}
