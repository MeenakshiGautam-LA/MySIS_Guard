package com.sisindia.mysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FAQLandingActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener, View.OnClickListener {
    RecyclerView recyclerView;
    ArrayList<AttendanceEntity> attendancearrayList;
    AttendanceEntity attendanceEntity;
    private ExpandableListView expandableListView,expandableListviewsecond;
    private ExpandableListAdapter adaptersecond,adapter;

     RelativeLayout relativeLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_landing);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        relativeLayout=findViewById(R.id.relative);
        expandableListviewsecond= (ExpandableListView) findViewById(R.id.expandableListviewsecond);

        List<String> groupData = new ArrayList<>();
        groupData.add("This is the dummy text for the question related with my attendance?");
        groupData.add("This is the dummy text for the question related with my attendance?");
        groupData.add("This is the dummy text for the question related with my attendance?");

        List<String> mobileData = new ArrayList<>();
        mobileData.add("This is the dummy text for the question related with my attendance?");
        mobileData.add("This is the dummy text for the question related with my attendance?");
        mobileData.add("This is the dummy text for the question related with my attendance?");
        mobileData.add("This is the dummy text for the question related with my attendance?");
        mobileData.add("This is the dummy text for the question related with my attendance?");

        List<String> laptopData = new ArrayList<>();
        laptopData.add("This is the dummy text for the question related with my attendance?");
        laptopData.add("This is the dummy text for the question related with my attendance?");
        laptopData.add("This is the dummy text for the question related with my attendance?");
        laptopData.add("This is the dummy text for the question related with my attendance?");
        laptopData.add("This is the dummy text for the question related with my attendance?");

        List<String> tvData = new ArrayList<>();
        tvData.add("This is the dummy text for the question related with my attendance?");
        tvData.add("This is the dummy text for the question related with my attendance?");
        tvData.add("This is the dummy text for the question related with my attendance?");
        tvData.add("This is the dummy text for the question related with my attendance?");

        HashMap<String, List<String>> childData = new HashMap<>();
        childData.put(groupData.get(0), mobileData);
        childData.put(groupData.get(1), laptopData);
        childData.put(groupData.get(2), tvData);




        List<String> groupDataSecond = new ArrayList<>();
        groupDataSecond.add("This is the dummy text for the question related with my attendance?");
        groupDataSecond.add("This is the dummy text for the question related with my attendance?");
        groupDataSecond.add("This is the dummy text for the question related with my attendance?");

        List<String> mobileDataSecond = new ArrayList<>();
        mobileDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance");
        mobileDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");
        mobileDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");
        mobileDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");
        mobileDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");

        List<String> laptopDataSecond = new ArrayList<>();
        laptopDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");
        laptopDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");
        laptopDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");
        laptopDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");
        laptopDataSecond.add("This is the dummy text for the answer for the questions related with my attendance. This is the dummy text for the answer for the questions related with my attendance.");


        HashMap<String, List<String>> childDataSecond = new HashMap<>();
        childDataSecond.put(groupData.get(0), mobileData);
        childDataSecond.put(groupData.get(1), laptopData);
        childDataSecond.put(groupData.get(2), tvData);


        // Setting up the Adapter
        adapter = new ExpandableListAdapter(this, groupData, childData);

        expandableListView.setAdapter(adapter);
        // Implementing callback to get notified when a Child item is clicked
        expandableListView.setOnChildClickListener(this);


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });



        adaptersecond = new ExpandableListAdapter(this, groupDataSecond, childDataSecond);

        expandableListviewsecond.setAdapter(adaptersecond);
        // Implementing callback to get notified when a Child item is clicked
        expandableListviewsecond.setOnChildClickListener(this);


        expandableListviewsecond.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });



relativeLayout.setOnClickListener(this);


    }


    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }



    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        TextView childItem = v.findViewById(R.id.tv_child_text);
        String item = childItem.getText().toString();
        Toast.makeText(this, item + " clicked!", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(FAQLandingActivity.this,HelpIssueActivity.class);
        startActivity(in);

    }
}