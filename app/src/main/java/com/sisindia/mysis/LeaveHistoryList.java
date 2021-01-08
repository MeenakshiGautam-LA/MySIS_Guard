//package com.example.digitalguard;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.example.digitalguard.Adapter.LeaveHistoryAdapter;
//import com.example.digitalguard.Model.LeaveLandingModel;
//
//import java.util.ArrayList;
//
//
//
//public class LeaveHistoryList extends AppCompatActivity
//   {
//
//    RecyclerView recyclerView;
//    LeaveHistoryAdapter leaveLandingAdapter;
//    Context context;
//    ArrayList<LeaveLandingModel> leaveLandingModelArrayList;
//    LeaveLandingModel leaveLandingModel;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_leave_history);
//        context=this;
//        leaveLandingModelArrayList = new ArrayList<LeaveLandingModel>();
//        recyclerView = findViewById(R.id.recyclerview);
//
//
//        prepareData();
//
//        leaveLandingAdapter = new LeaveHistoryAdapter(context, leaveLandingModelArrayList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(leaveLandingAdapter);
//
//    }
//
//
//    private void prepareData() {
//
//
//        leaveLandingModel= new LeaveLandingModel("Family Function", "12", "22 Feb-03 Mar", "Approved");
//
//        leaveLandingModelArrayList.add(leaveLandingModel);
//
//        leaveLandingModel = new LeaveLandingModel("Marriage", "19", "06 Apr-25 Apr", "Approved");
//        leaveLandingModelArrayList.add(leaveLandingModel);
//
//        leaveLandingModel = new LeaveLandingModel("Family Function", "1", "25 Apr", "Approved");
//        leaveLandingModelArrayList.add(leaveLandingModel);
//
//        leaveLandingModel = new LeaveLandingModel("Family Function", "0", "06 Apr-25 Apr", "Approved");
//        leaveLandingModelArrayList.add(leaveLandingModel);
//
//
//    }
//}
//
