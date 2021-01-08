//package com.sisindia.guardattendance;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//
//
//import com.sisindia.guardattendance.Adapter.NotificationAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Notification extends AppCompatActivity {
//
//    RecyclerView notification_RV;
//    NotificationAdapter notificationAdapter;
//    List<String> notificationDateList = new ArrayList<>();
//    List<String> notificationContentList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification);
//
//        notificationDateList.add("Today");
//        notificationDateList.add("12, March");
//        notificationDateList.add("11, March");
//
//        notificationContentList.add("Leave Request Rejected");
//        notificationContentList.add("Leave Request Accesspted");
//        notificationContentList.add("Leave Request Accesspted");
//
//        notification_RV = findViewById(R.id.notification_recycle_view);
//
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        notification_RV.setLayoutManager(mLayoutManager);
//
//        notificationAdapter = new NotificationAdapter(this, notificationDateList,notificationContentList);
//        notification_RV.setAdapter(notificationAdapter);
//        // notification_RV.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
//        notificationAdapter.notifyDataSetChanged();
//
//
//    }
//}
