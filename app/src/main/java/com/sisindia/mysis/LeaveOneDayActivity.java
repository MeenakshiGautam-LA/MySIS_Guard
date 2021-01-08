package com.sisindia.mysis;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Adapter.MyDayCalendarAdapter;
import com.sisindia.mysis.Model.MyDayModel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class LeaveOneDayActivity extends AppCompatActivity implements MyDayCalendarAdapter.TaskCalendarClickListener, View.OnClickListener {


    List<MyDayModel> taskModelList = new ArrayList<>();
    ImageView activity_leave_calendar_prev_icon, activity_leave_calendar_next_icon;
    int spMonth = 10, spYear = 2019, startDateNo = 0, endDateNo = 0;
    SimpleDateFormat activity_scedule_calendar_date_format;
    Context context;
    RecyclerView activity_leave_calendar_recycler_view;
    MyDayCalendarAdapter myDayCalendarAdapter;
    ArrayList<String> months = new ArrayList<>();
    boolean _dateRange = true, _startDate = false;
    TextView activity_leave_calendar_month_year_txt;
     Button raise_request_btn;
    LinearLayout linearLayout;


     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_one_day);
        context = this;

        linearLayout=findViewById(R.id.linear);
         /*setTaskModelList(spMonth, spYear);*/
        spMonth = Calendar.getInstance().get(Calendar.MONTH);
        spYear = Calendar.getInstance().get(Calendar.YEAR);
        activity_scedule_calendar_date_format = new SimpleDateFormat("yyyy-MM-dd");


        activity_leave_calendar_prev_icon = findViewById(R.id.activity_leave_calendar_prev_icon);
        activity_leave_calendar_next_icon = findViewById(R.id.activity_leave_calendar_next_icon);

        activity_leave_calendar_month_year_txt = findViewById(R.id.activity_leave_calendar_month_year_txt);
         raise_request_btn = findViewById(R.id.raise_request_btn);


        activity_leave_calendar_recycler_view = findViewById(R.id.activity_leave_calendar_recycler_view);
        activity_leave_calendar_recycler_view.setLayoutManager(new GridLayoutManager(context, 7));

        setTaskModelList(spMonth, spYear);

        myDayCalendarAdapter = new MyDayCalendarAdapter(context, taskModelList, (MyDayCalendarAdapter.TaskCalendarClickListener) this, _dateRange);
        activity_leave_calendar_recycler_view.setAdapter(myDayCalendarAdapter);
        activity_leave_calendar_recycler_view.getRecycledViewPool().setMaxRecycledViews(0, 0);

        Collections.addAll(months, getResources().getStringArray(R.array.month));

        raise_request_btn.setOnClickListener(this);

        activity_leave_calendar_prev_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spMonth += (11);
                spYear--;
                updateMonthYear();
            }
        });
        activity_leave_calendar_next_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spMonth++;
                updateMonthYear();
            }
        });

        updateMonthYear();
    }



    public void setTaskModelList(int month, int year) {
        int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int firstDayOfMonth = getFirstDayOfMonth(month, year);
        int totalDayInMonth = getTotalNoOfDayInMonth(month, year);
        taskModelList.clear();
        switch (firstDayOfMonth) {
            case (1):
                Log.d("Day", "" + firstDayOfMonth);
                for (int i = 0; i < totalDayInMonth + 6; i++) {
                    MyDayModel myDayModel = new MyDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 5) {
                        myDayModel.setToday(true);
                    } else {
                        myDayModel.setToday(false);
                    }
                    myDayModel.setDay(firstDayOfMonth);

                    myDayModel = setTaskModelListModel(myDayModel, i, 5);


                    taskModelList.add(i, myDayModel);
                }
                break;
            case (2):
                Log.d("Day", "" + firstDayOfMonth);
                for (int i = 0; i < totalDayInMonth; i++) {
                    MyDayModel myDayModel = new MyDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i + 1) {
                        myDayModel.setToday(true);
                    } else {
                        myDayModel.setToday(false);
                    }
                    myDayModel.setDay(firstDayOfMonth);

                    myDayModel = setTaskModelListModel(myDayModel, i, -1);

                    taskModelList.add(i, myDayModel);
                }
                break;
            case 3:
                Log.d("Day", "" + firstDayOfMonth);
                for (int i = 0; i < totalDayInMonth + 1; i++) {
                    MyDayModel myDayModel = new MyDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i) {
                        myDayModel.setToday(true);
                    } else {
                        myDayModel.setToday(false);
                    }
                    myDayModel.setDay(firstDayOfMonth);
                    myDayModel = setTaskModelListModel(myDayModel, i, 0);
                    taskModelList.add(i, myDayModel);
                }
                break;
            case 4:
                Log.d("Day", "" + firstDayOfMonth);
                for (int i = 0; i < totalDayInMonth + 2; i++) {
                    MyDayModel myDayModel = new MyDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 1) {
                        myDayModel.setToday(true);
                    } else {
                        myDayModel.setToday(false);
                    }
                    myDayModel.setDay(firstDayOfMonth);
                    myDayModel = setTaskModelListModel(myDayModel, i, 1);
                    taskModelList.add(i, myDayModel);
                }
                break;
            case 5:
                Log.d("Day", "" + firstDayOfMonth);
                for (int i = 0; i < totalDayInMonth + 3; i++) {
                    MyDayModel myDayModel = new MyDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 2) {
                        myDayModel.setToday(true);
                    } else {
                        myDayModel.setToday(false);
                    }
                    myDayModel.setDay(firstDayOfMonth);
                    myDayModel = setTaskModelListModel(myDayModel, i, 2);
                    taskModelList.add(i, myDayModel);
                }
                break;
            case 6:
                Log.d("Day", "" + firstDayOfMonth);
                for (int i = 0; i < totalDayInMonth + 4; i++) {

                    MyDayModel myDayModel = new MyDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 3) {
                        myDayModel.setToday(true);
                    } else {
                        myDayModel.setToday(false);
                    }
                    myDayModel.setDay(firstDayOfMonth);
                    myDayModel = setTaskModelListModel(myDayModel, i, 3);
                    taskModelList.add(i, myDayModel);
                }
                break;
            case 7:
                Log.d("Day", "" + firstDayOfMonth);
                for (int i = 0; i < totalDayInMonth + 5; i++) {
                    MyDayModel myDayModel = new MyDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 4) {
                        myDayModel.setToday(true);
                    } else {
                        myDayModel.setToday(false);
                    }
                    myDayModel.setDay(firstDayOfMonth);
                    myDayModel = setTaskModelListModel(myDayModel, i, 4);
                    taskModelList.add(i, myDayModel);
                }
                break;
        }

    }


    private int getFirstDayOfMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private int getTotalNoOfDayInMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private MyDayModel setTaskModelListModel(MyDayModel myDayModel, int i, int j) {
        myDayModel.setMonth(spMonth);
        myDayModel.setYear(spYear);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, spYear);
        cal.set(Calendar.MONTH, spMonth);
        cal.set(Calendar.DAY_OF_MONTH, (i - j));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        myDayModel.setDate("" + activity_scedule_calendar_date_format.format(cal.getTime()));
        myDayModel.setDateSingleDigit("" + (i - j));
        return myDayModel;
    }


    private void updateMonthYear() {
        spYear += spMonth / 12;
        spMonth %= 12;
        setTaskModelList(spMonth, spYear);
        activity_leave_calendar_month_year_txt.setText(months.get(spMonth) + " " + spYear);
        myDayCalendarAdapter = new MyDayCalendarAdapter(context, taskModelList, LeaveOneDayActivity.this, _dateRange);
        activity_leave_calendar_recycler_view.setAdapter(myDayCalendarAdapter);
        activity_leave_calendar_recycler_view.getRecycledViewPool().setMaxRecycledViews(0, 0);
    }

    @Override
    public void taskClicked(int selectedGUID, String type) {
        for (int i = 0; i < taskModelList.size(); i++) {
            taskModelList.get(i).setSelected(false);
        }
        if (_dateRange) {
            if (_startDate) {

                for (int i = 0; i < taskModelList.size(); i++) {
                    taskModelList.get(i).setDayNumber(0);
                }

                taskModelList.get(selectedGUID).setDayNumber(1);

                _startDate = false;
                startDateNo = Integer.parseInt(taskModelList.get(selectedGUID).getDateSingleDigit());
            } else {
                endDateNo = Integer.parseInt(taskModelList.get(selectedGUID).getDateSingleDigit());

                if (startDateNo < endDateNo) {
                    taskModelList.get(selectedGUID).setDayNumber(3);
                    //   range_end_date_tv.setText(taskModelList.get(selectedGUID).getDate());
                } else {
                    // reset
                    for (int i = 0; i < taskModelList.size(); i++) {
                        taskModelList.get(i).setDayNumber(0);
                    }
                }
                _startDate = true;
                setMiddleDates();
            }
        } else {
            /*Single Date*/
            taskModelList.get(selectedGUID).setSelected(true);
        }


        //        Toast.makeText(context, "" + type, Toast.LENGTH_SHORT).show();
//        myDayCalendarAdapter.notifyDataSetChanged();
        myDayCalendarAdapter = new MyDayCalendarAdapter(context, taskModelList, LeaveOneDayActivity.this, _dateRange);
        activity_leave_calendar_recycler_view.setAdapter(myDayCalendarAdapter);
        activity_leave_calendar_recycler_view.getRecycledViewPool().setMaxRecycledViews(0, 0);
    }

    private void setMiddleDates() {
        for (int i = 0; i < taskModelList.size(); i++) {
            MyDayModel myDayModel = taskModelList.get(i);
            if (Integer.parseInt(myDayModel.getDateSingleDigit()) > startDateNo && Integer.parseInt(myDayModel.getDateSingleDigit()) < endDateNo)
                myDayModel.setDayNumber(2);
        }
        startDateNo = 0;
        endDateNo = 0;
    }


    @Override
    public void onClick(View v) {

         linearLayout.setBackgroundColor(getResources().getColor(R.color.bg_transparent_color));
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.leave_confirm_dialog, null);

        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(this);

        alert.setView(alertLayout);

        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.show();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 70%
        int dialogWindowHeight = (int) (displayHeight * 0.8f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;


       // layoutParams.width = WindowManager.LayoutParams.;

        // Apply the newly created layout parameters to the alert dialog window
        dialog.getWindow().setAttributes(layoutParams);






    }
}

