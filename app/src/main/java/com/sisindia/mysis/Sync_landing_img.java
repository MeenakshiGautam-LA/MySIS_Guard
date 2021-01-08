package com.sisindia.mysis;

import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;

public class Sync_landing_img extends BaseActivity {

    FrameLayout frame_layout;
AppCompatImageView synServerIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syn_server_layout);
           CSApplicationHelper.application().setActivity(this);
        Toast.makeText(this, "Syncing...", Toast.LENGTH_SHORT).show();
//        DashboadApisMethod db = DashboadApisMethod.getInstance(this);
//        GetDataFromServer.getInstance().getData(db, this, null, false);
        frame_layout = findViewById(R.id.frame_layout);
        frame_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sync_landing_img.this, SyncDataActivity.class));
            }
        });
    }

    @Override
    public int getScreenName() {
        return 0;
    }
}
