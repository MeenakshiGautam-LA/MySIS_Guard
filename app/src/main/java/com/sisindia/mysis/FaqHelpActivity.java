package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.sisindia.mysis.Adapter.FaqHelpAdapter;
import com.sisindia.mysis.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class FaqHelpActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

    List<String> faqHelpHeadingList = new ArrayList<>();
    List<String> faqHelpContentList = new ArrayList<>();
    RecyclerView faq_recycle_view;
    FaqHelpAdapter faqHelpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_help);
/*
        faqHelpHeadingList.add("My Attendance");
        faqHelpHeadingList.add("My Salary");
        faqHelpHeadingList.add("My Salary");
        faqHelpHeadingList.add("My Salary");
        faqHelpHeadingList.add("My Salary");

        faqHelpContentList.add("this is dumy text for help question");
        faqHelpContentList.add("this is dumy text for help question");
        faqHelpContentList.add("this is dumy text for help question");
        faqHelpContentList.add("this is dumy text for help question");
        faqHelpContentList.add("this is dumy text for help question");
        faqHelpContentList.add("this is dumy text for help question");

        faq_recycle_view = findViewById(R.id.faq_recycle_view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        faq_recycle_view.setLayoutManager(mLayoutManager);

        faqHelpAdapter = new FaqHelpAdapter(this, faqHelpHeadingList, faqHelpContentList);
        faq_recycle_view.setAdapter(faqHelpAdapter);
      //  faq_recycle_view.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        faqHelpAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.e("log>>> ", "main faq recycler position... " + position);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
