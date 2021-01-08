//package com.example.digitalguard;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.digitalguard.Adapter.LeaveLandingAdapter;
//import com.example.digitalguard.Model.LeaveLandingModel;
//
//import java.util.ArrayList;
//
//
//
//public class LeaveLandingActivity extends AppCompatActivity implements View.OnClickListener
//   {
//    RecyclerView recyclerView;
//    LeaveLandingAdapter leaveLandingAdapter;
//    Context context;
//    ArrayList<LeaveLandingModel> leaveLandingModelArrayList;
//    LeaveLandingModel leaveLandingModel;
//    TextView leave_history_txt;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState)
//       {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_leave_landing);
//        context=this;
//        leaveLandingModelArrayList = new ArrayList<LeaveLandingModel>();
//        recyclerView = findViewById(R.id.recyclerview);
//        leave_history_txt=findViewById(R.id.leave_history_txt);
//
//        prepareData();
//
//        leaveLandingAdapter = new LeaveLandingAdapter(context, leaveLandingModelArrayList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(leaveLandingAdapter);
//
//        leave_history_txt.setOnClickListener(this);
//
//    }
//
//
//    private void prepareData() {
//
//       leaveLandingModel= new LeaveLandingModel("Family Function", "12", "22 Feb-03 Mar", "Approved");
//
//       leaveLandingModelArrayList.add(leaveLandingModel);
//
//       leaveLandingModel = new LeaveLandingModel("Marriage", "19", "06 Apr-25 Apr", "Approved");
//        leaveLandingModelArrayList.add(leaveLandingModel);
//
//        leaveLandingModel = new LeaveLandingModel("Family Function", "1", "25 Apr", "Approved");
//        leaveLandingModelArrayList.add(leaveLandingModel);
//
//        leaveLandingModel = new LeaveLandingModel("Family Function", "0", "06 Apr-25 Apr", "Approved");
//        leaveLandingModelArrayList.add(leaveLandingModel);
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//        Intent in = new Intent(LeaveLandingActivity.this,LeaveHistoryList.class);
//        startActivity(in);
//
//    }
//}
//
