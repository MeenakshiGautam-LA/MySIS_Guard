//package com.example.digitalguard;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//
//import com.example.digitalguard.Adapter.EmployeeCalendarAdapter;
//import com.example.digitalguard.Model.CalendarDayModel;
//import com.example.digitalguard.fragment.MarkAttendanceDutyInActivity;
//import com.example.digitalguard.utils.DialogClass;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//public class DutyLeaveCalendarActivity extends AppCompatActivity implements EmployeeCalendarAdapter.TaskCalendarClickListener {
//
//    RecyclerView calenderRv;
//    List<CalendarDayModel> taskModelList = new ArrayList<>();
//    ArrayList<String> months = new ArrayList<>();
//    int spMonth = Calendar.getInstance().get(Calendar.MONTH);
//    int spYear = Calendar.getInstance().get(Calendar.YEAR);
//    TextView month_name_tv;
//    ImageView next_month_iv,previous_month_iv;
//    EmployeeCalendarAdapter employeeCalendarAdapter;
//    Boolean _dateRange = false, _rangeStartDate = false;
//    LinearLayout totalLeaveLinear;
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//    LinearLayout menuLinear,dutyBottomNavigationLY;
//    DialogClass dialogClass;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_duty_leave_calendar);
//
//        calenderRv = findViewById(R.id.calenderRv);
//        month_name_tv = findViewById(R.id.month_name_tv);
//        previous_month_iv = findViewById(R.id.previous_month_iv);
//        next_month_iv = findViewById(R.id.next_month_iv);
//        totalLeaveLinear = findViewById(R.id.totalLeaveLinear);
//        menuLinear = findViewById(R.id.menuLinear);
//        dutyBottomNavigationLY = findViewById(R.id.dutyBottomNavigationLY);
//
//        calenderRv.setLayoutManager(new GridLayoutManager(DutyLeaveCalendarActivity.this, 7));
//        setTaskModelList(spMonth, spYear);
//        for (int i = 0; i < taskModelList.size(); i++) {
//            taskModelList.get(i).setSelected(false);
//        }
//
//
//        Collections.addAll(months, getResources().getStringArray(R.array.month));
//        month_name_tv.setText(months.get(spMonth) + ", " + spYear);
////        setLeaveDateOnCalendar();
//        // setLeaveDateOnCalendar();
//        employeeCalendarAdapter = new EmployeeCalendarAdapter(DutyLeaveCalendarActivity.this, taskModelList, this, _dateRange);
//        calenderRv.setAdapter(employeeCalendarAdapter);
//        calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
//
//        dutyBottomNavigationLY.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DutyLeaveCalendarActivity.this, MarkAttendanceDutyInActivity.class));
//            }
//        });
//
//        menuLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogClass = new DialogClass(DutyLeaveCalendarActivity.this, null);
//                dialogClass.openBottomMenuBar(DutyLeaveCalendarActivity.this);
//            }
//        });
//
//        previous_month_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < taskModelList.size(); i++) {
//                    taskModelList.get(i).setSelected(false);
//                }
//                spMonth += (11);
//                spYear--;
//                updateMonthYear();
//            }
//        });
//        next_month_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < taskModelList.size(); i++) {
//                    taskModelList.get(i).setSelected(false);
//                }
//                spMonth++;
//                updateMonthYear();
//            }
//        });
//
//        totalLeaveLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DutyLeaveCalendarActivity.this, CalendarLeaveActivity.class));
//            }
//        });
//
//
//    }
//
//    public void setTaskModelList(int month, int year) {
//        int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        int firstDayOfMonth = getFirstDayOfMonth(month, year);
//        int totalDayInMonth = getTotalNoOfDayInMonth(month, year);
//        Log.d("Day>>>", "firstDayOfMonth... " + firstDayOfMonth);
//        Log.d("month>>>", "totalDayInMonth... " + totalDayInMonth);
//        taskModelList.clear();
//        switch (firstDayOfMonth) {
//            case (1):
//                for (int i = 0; i < totalDayInMonth + 6; i++) {
//                    CalendarDayModel calendarDayModel = new CalendarDayModel();
//                    if (currentMonth == month && currentYear == year && currentDate == i - 5) {
//                        calendarDayModel.setToday(true);
//                    } else {
//                        calendarDayModel.setToday(false);
//                    }
//                    calendarDayModel.setDay(firstDayOfMonth);
//                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 5);
//                    taskModelList.add(i, calendarDayModel);
//                }
//                break;
//            case (2):
//                for (int i = 0; i < totalDayInMonth; i++) {
//                    CalendarDayModel calendarDayModel = new CalendarDayModel();
//                    if (currentMonth == month && currentYear == year && currentDate == i + 1) {
//                        calendarDayModel.setToday(true);
//                    } else {
//                        calendarDayModel.setToday(false);
//                    }
//                    calendarDayModel.setDay(firstDayOfMonth);
//                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, -1);
//                    taskModelList.add(i, calendarDayModel);
//                }
//                break;
//            case 3:
//                for (int i = 0; i < totalDayInMonth + 1; i++) {
//                    CalendarDayModel calendarDayModel = new CalendarDayModel();
//                    if (currentMonth == month && currentYear == year && currentDate == i) {
//                        calendarDayModel.setToday(true);
//                    } else {
//                        calendarDayModel.setToday(false);
//                    }
//                    calendarDayModel.setDay(firstDayOfMonth);
//                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 0);
//                    taskModelList.add(i, calendarDayModel);
//                }
//                break;
//            case 4:
//                for (int i = 0; i < totalDayInMonth + 2; i++) {
//                    CalendarDayModel calendarDayModel = new CalendarDayModel();
//                    if (currentMonth == month && currentYear == year && currentDate == i - 1) {
//                        calendarDayModel.setToday(true);
//                    } else {
//                        calendarDayModel.setToday(false);
//                    }
//                    calendarDayModel.setDay(firstDayOfMonth);
//                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 1);
//                    taskModelList.add(i, calendarDayModel);
//                }
//                break;
//            case 5:
//                for (int i = 0; i < totalDayInMonth + 3; i++) {
//                    CalendarDayModel calendarDayModel = new CalendarDayModel();
//                    if (currentMonth == month && currentYear == year && currentDate == i - 2) {
//                        calendarDayModel.setToday(true);
//                    } else {
//                        calendarDayModel.setToday(false);
//                    }
//                    calendarDayModel.setDay(firstDayOfMonth);
//                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 2);
//                    taskModelList.add(i, calendarDayModel);
//                }
//                break;
//            case 6:
//                for (int i = 0; i < totalDayInMonth + 4; i++) {
//                    CalendarDayModel calendarDayModel = new CalendarDayModel();
//                    if (currentMonth == month && currentYear == year && currentDate == i - 3) {
//                        calendarDayModel.setToday(true);
//                    } else {
//                        calendarDayModel.setToday(false);
//                    }
//                    calendarDayModel.setDay(firstDayOfMonth);
//                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 3);
//                    taskModelList.add(i, calendarDayModel);
//                }
//                break;
//            case 7:
//                for (int i = 0; i < totalDayInMonth + 5; i++) {
//                    CalendarDayModel calendarDayModel = new CalendarDayModel();
//                    if (currentMonth == month && currentYear == year && currentDate == i - 4) {
//                        calendarDayModel.setToday(true);
//                    } else {
//                        calendarDayModel.setToday(false);
//                    }
//                    calendarDayModel.setDay(firstDayOfMonth);
//                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 4);
//                    taskModelList.add(i, calendarDayModel);
//                }
//                break;
//        }
//
//    }
//
//
//    private int getFirstDayOfMonth(int month, int year) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DATE, 1);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.YEAR, year);
//        return calendar.get(Calendar.DAY_OF_WEEK);
//    }
//
//    private int getTotalNoOfDayInMonth(int month, int year) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        calendar.setTime(date);
//        calendar.set(Calendar.DATE, 1);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.YEAR, year);
//        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//    }
//
//
//    private CalendarDayModel setTaskModelListModel(CalendarDayModel myDayModel, int i, int j) {
//        myDayModel.setMonth(spMonth);
//        myDayModel.setYear(spYear);
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, spYear);
//        cal.set(Calendar.MONTH, spMonth);
//        cal.set(Calendar.DAY_OF_MONTH, (i - j));
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        myDayModel.setDate("" + formatter.format(cal.getTime()));
//        myDayModel.setDateSingleDigit("" + (i - j));
//        return myDayModel;
//    }
//
//    private void updateMonthYear() {
//        setTaskModelList(spMonth, spYear);
//        for (int i = 0; i < taskModelList.size(); i++) {
//            taskModelList.get(i).setSelected(false);
//        }
//        spYear += spMonth / 12;
//        spMonth %= 12;
//        month_name_tv.setText(months.get(spMonth) + ", " + spYear);
//        employeeCalendarAdapter = new EmployeeCalendarAdapter(DutyLeaveCalendarActivity.this, taskModelList, this, _dateRange);
//        calenderRv.setAdapter(employeeCalendarAdapter);
//        calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
//    }
//
//
//
//    @Override
//    public void taskClicked(int date, View view, String leaveReason, List<String> dateRange, Boolean dateRangeStatus, Boolean selectionStatus) {
//        Log.e("", "");
//    }
//}
