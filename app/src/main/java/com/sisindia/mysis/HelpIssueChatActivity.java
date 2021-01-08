package com.sisindia.mysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.sisindia.mysis.Adapter.HelpissueChatadapter;

import java.util.ArrayList;

public class HelpIssueChatActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerview;
    TextView type_here_edittxt;
    ImageView icon_send_message;
    String message_txt;
   HelpissueChatadapter helpIssueChatAdapter;
   ArrayList<String> helpIssueArrayList;
   Context context;
RelativeLayout relative;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_issue_chat);

        context=this;

        helpIssueArrayList=new ArrayList<>();

        recyclerview = findViewById(R.id.recyclerview);
        type_here_edittxt = findViewById(R.id.type_here_edittxt);
        icon_send_message = findViewById(R.id.icon_send_message);

        relative = findViewById(R.id.relative);






relative.setOnClickListener(this);
        icon_send_message.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == icon_send_message.getId()) {
            message_txt = type_here_edittxt.getText().toString();


            helpIssueArrayList.add(message_txt);
            helpIssueChatAdapter = new HelpissueChatadapter(context, helpIssueArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);


            // LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            // ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
            //recyclerView.setLayoutManager(layoutManager);


            recyclerview.setLayoutManager(mLayoutManager);
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            recyclerview.setAdapter(helpIssueChatAdapter);


        }

        else if(v.getId()==relative.getId())
        {
            Intent in = new Intent(HelpIssueChatActivity.this,HelpRaiseIssueActivity.class);
            startActivity(in);

        }

    }



}
