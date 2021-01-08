package com.sisindia.mysis.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Adapter.CalenderViewAdapter;
import com.sisindia.mysis.Adapter.PresentAttendanceInCalenderAdapter;
import com.sisindia.mysis.Adapter.Roster_DetailView_InCalenderAdapter;
import com.sisindia.mysis.Adapter.Viewlegend_Adapter;
import com.sisindia.mysis.ApiCalling_Class;
import com.sisindia.mysis.Model.Attendance_for_calender_model;
import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.Model.Leave_Detail_Model;
import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.MainActivity_Guard;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.VolleyAsyncClassPost;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.LeaveDetailTransaction;
import com.sisindia.mysis.entity.LeaveMaster;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSDrawable;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.sisindia.mysis.utils.Constants.DAILY_ATTENDANCEDETAIL;
import static com.sisindia.mysis.utils.Constants.POST_DAILY_ATTENDANCE;
import static com.sisindia.mysis.utils.Constants.POST_LEAVE_DETAIL;
import static com.sisindia.mysis.utils.Constants.POST_LEAVE_MASTER;

public class CalendarFragment extends CSHeaderFragmentFragment implements CalenderViewAdapter.
        TaskCalendarClickListener, VolleyAsyncClassPost.VolleyPostResponse {
    //DAY_OF_WEEK  mean which day
    @BindView(R.id.calenderRv)
    RecyclerView calenderRv;
    @BindView(R.id.month_name_tv)
    TextView month_name_tv;
    @BindView(R.id.next_month_iv)
    ImageView next_month_iv;
    @BindView(R.id.previous_month_iv)
    ImageView previous_month_iv;
    @BindView(R.id.totalLeaveLinear)
    LinearLayout totalLeaveLinear;
    @BindView(R.id.total_leave_txt)
    TextView total_leave_txt;
    @BindView(R.id.presentCountTV)
    TextView presentCountTV;
    @BindView(R.id.extraDuty_TV)
    TextView extraDuty_TV;
    @BindView(R.id.viewLegendTV)
    TextView viewLegendTV;
    @BindView(R.id.sync_home_iv)
    AppCompatImageView sync_home_iv;
    @BindView(R.id.sync_BTN)
    Button sync_BTN;
    @BindView(R.id.calender_main_RL)
    RelativeLayout calender_main_RL;
    List<CalendarDayModel> taskModelList = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();
    int spMonth = 1;
    int spYear = Calendar.getInstance().get(Calendar.YEAR);
    CalenderViewAdapter employeeCalendarAdapter;
    Boolean _dateRange = false, _rangeStartDate = false;
    CalendarDayModel leaveEmployeeDetailModel;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    int totalDayInMonth, totalLeaveCount = 0, totalPresentDayCount = 0, extraDuty = 0;
    int taskmodalListPostion;

    @Override
    public int layoutResource() {
        return R.layout.activity_duty_leave_calendar;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        sync_home_iv.setVisibility(View.VISIBLE);
        if(getArguments()!=null){
             spMonth = getArguments().getInt("MONTH");
             spYear = getArguments().getInt("YEAR");
        }else {
             spMonth = Calendar.getInstance().get(Calendar.MONTH);
             spYear = Calendar.getInstance().get(Calendar.YEAR);
        }
        totalPresentDayCount = 0;
        extraDuty = 0;
        totalLeaveCount = 0;
        setCalendar();
        Drawable mIcon = ContextCompat.getDrawable(getActivity(), R.drawable.white_arrow);
        ((MainActivity_Guard)getActivity()).changeFooter_Icon_Color_from_Fragment(1);
        if (CSShearedPrefence.isNightModeEnabled()) {
            calender_main_RL.setBackground(null);
            previous_month_iv.setImageDrawable(mIcon);
            previous_month_iv.setRotation(180.0f);
            next_month_iv.setImageDrawable(mIcon);
//            calender_main_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        sync_BTN.setOnClickListener(view -> {
            if (!CSAppUtil.isNetworkNotAvailable(csActivity, false)) {
                ApiCalling_Class calling_class = new ApiCalling_Class(csActivity);
                calling_class.push_All_Apis();
                /*List<DailyAttendanceDetail> postAttendance = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getListToSync("1");
                if (postAttendance.size() > 0) {
                    try {
                        JSONArray array = new JSONArray();

                        Log.e("getListToSync", "PostAttendanceWork" + postAttendance.size());
                        for (int i = 0; i < postAttendance.size(); i++) {
                            array.put(new JSONObject(postAttendance.get(i).getJSON()));
                        }
                        new VolleyAsyncClassPost(this, array, POST_DAILY_ATTENDANCE, DAILY_ATTENDANCEDETAIL);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                            CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "UnitDailyAttendanceDetail"), getTagURL(),
                            "GetUnitDailyAttendanceDetail");
                    hold_for_the_setUP();
                }
                List<AttendanceGuardPicModel> postAttendance_Picture = CSApplicationHelper.application().getDatabaseInstance().attendancePicDao().getListToSync("1");

                if (postAttendance_Picture.size() > 0) {
                    try {
                        for (int i = 0; i < postAttendance_Picture.size(); i++) {

                            File file = convertbyteToFile(postAttendance_Picture.get(i).getPicture());


                            ProfilePictureMultipartsRequest imageUploadReq = new ProfilePictureMultipartsRequest(
                                    Constants.BASE_URL + Constants.UPLOAD_IMAGE_TAG,
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("EmailBGService", "Service $$$$$ Multiple Attatachmet failed area");
                                        }
                                    }, file, "testing",

                                    new ProfilePictureMultipartsRequest.ProfilePicUpdateListener() {
                                        @Override
                                        public void onProfilePicUpdateSuccessfully(String response) {
                                            Log.e("EmailBGService", "Service Server Returned Successs ID :" + response);

                                            try {
                                                Log.e("DAILY_ATTENDANCEDETAIL>", "DAILY_ATTENDANCEDETAIL.....   " + response.toString());
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.optString("status").equals("true")) {
                                                    // JSONObject jsonObject = response.getJSONObject("data");
                                                    JSONArray array = jsonObject.getJSONArray("data");
                                                    //
                                                    for (int i = 0; i < array.length(); i++) {
                                                        JSONObject idObject = array.getJSONObject(i);
                                                        String ID = idObject.optString("ID");
                                                        CSApplicationHelper.application().getDatabaseInstance().attendancePicDao().deleteRecord(ID);
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, getPostDataParam(postAttendance_Picture.get(i)));
                            CSApplicationHelper.application().addToRequestQueue(imageUploadReq, "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
*/

                CSApplicationHelper.application().getInstance_ProgresssBar().show();
                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.Q)
                    @Override
                    public void run() {
                        ApiCalling_Class calling_class = new ApiCalling_Class(csActivity);
                        calling_class.push_All_Apis();
                        CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();

                  /*  DashboadApisMethod db = DashboadApisMethod.getInstance(csActivity);
                    GetDataFromServer.getInstance().getData(db, csActivity, null, false);*/
                    }
                }, 1000 * 1 * 60);
            }else {
                CSDialogUtil.showInfoDialog(csActivity,CSStringUtil.getString(R.string.no_internet));
            }
        });
        CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();
        viewLegendTV.setOnClickListener(view -> {
            showLegendDialog(csActivity, viewLegendTV);
        });
        sync_home_iv.setOnClickListener(v -> {
            if (!CSAppUtil.isNetworkNotAvailable(csActivity, false)) {

                JSONArray array = new JSONArray();
                for (int i = 1; i <= totalDayInMonth; i++) {
                    String startDateOfSelectedmonth = spYear + "-" + String.format("%02d", spMonth + 1) + "-" + String.format("%02d", i);

                    String jsonString = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                            getSelectedMonth_Json(CSShearedPrefence.getUser(), startDateOfSelectedmonth);
                    try {

                        if (!CSStringUtil.isEmptyStr(jsonString)) {

                            array.put(new JSONObject(jsonString));
                            show_Log_Data("JSON_ARRAY", jsonString);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (array.length() > 0) {
                    CSApplicationHelper.application().getInstance_ProgresssBar().show();
                    new Handler().postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.Q)
                        @Override
                        public void run() {
                            new VolleyAsyncClassPost(CalendarFragment.this, array, POST_DAILY_ATTENDANCE, DAILY_ATTENDANCEDETAIL);
                            CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();

                  /*  DashboadApisMethod db = DashboadApisMethod.getInstance(csActivity);
                    GetDataFromServer.getInstance().getData(db, csActivity, null, false);*/
                        }
                    }, 3000);


                }
            }else {
                CSDialogUtil.showInfoDialog(csActivity,CSStringUtil.getString(R.string.no_internet));
            }


//            if (getListToSync.size() > 0) {
//                try {
//                    JSONArray array = new JSONArray();
//
//                    Log.e("getListToSync", "PostAttendanceWork" + getListToSync.size());
//                    for (int i = 0; i < getListToSync.size(); i++) {
//                        array.put(new JSONObject(getListToSync.get(i)));
//                        show_Log_Data("JSON_ARRAY",getListToSync.get(i));
//                    }
////                    new VolleyAsyncClassPost(this, array, POST_DAILY_ATTENDANCE, DAILY_ATTENDANCEDETAIL);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        });
    }

    public void setCalendar() {
        calenderRv.setLayoutManager(new GridLayoutManager(getContext(), 7));

        setTaskModelList(spMonth, spYear);
        for (int i = 0; i < taskModelList.size(); i++) {
            taskModelList.get(i).setSelected(false);
            taskModelList.get(i).setLeaveSelected(false);
        }

        Collections.addAll(months, getResources().getStringArray(R.array.month));
        month_name_tv.setText(months.get(spMonth) + ", " + spYear);
        setDetailOnCalender();
        total_leave_txt.setText(String.valueOf(totalLeaveCount));
        presentCountTV.setText(String.valueOf(totalPresentDayCount));
        extraDuty_TV.setText(String.valueOf(extraDuty));
        employeeCalendarAdapter = new CalenderViewAdapter(getContext(), taskModelList, this, _dateRange);
        calenderRv.setAdapter(employeeCalendarAdapter);
        calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
        previous_month_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalPresentDayCount = 0;
                totalLeaveCount = 0;
                extraDuty = 0;
                presentCountTV.setText(String.valueOf(0));
                extraDuty_TV.setText(String.valueOf(0));
                total_leave_txt.setText(String.valueOf(totalLeaveCount));
                for (int i = 0; i < taskModelList.size(); i++) {
                    taskModelList.get(i).setSelected(false);
                    taskModelList.get(i).setLeaveSelected(false);
                }
                spMonth += (11);
                spYear--;
                updateMonthYear();
            }
        });
        next_month_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spMonth==11){
                    next_month_iv.setBackgroundTintList(ContextCompat.getColorStateList(csActivity, R.color.black));
                }else {
                    next_month_iv.setBackgroundTintList(ContextCompat.getColorStateList(csActivity, R.color.app_default_red_color));
                }
                totalPresentDayCount = 0;
                totalLeaveCount = 0;
                extraDuty = 0;
                presentCountTV.setText(String.valueOf(0));
                extraDuty_TV.setText(String.valueOf(0));
                total_leave_txt.setText(String.valueOf(totalLeaveCount));
//                workingHourTV.setText(String.valueOf(0));


                for (int i = 0; i < taskModelList.size(); i++) {
                    taskModelList.get(i).setSelected(false);
                    taskModelList.get(i).setLeaveSelected(false);
                }
                spMonth++;
                updateMonthYear();
            }
        });

        totalLeaveLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Bundle cbundle = new Bundle();
                cbundle.putString("REGNO", "GAR000198");
                cbundle.putString("totalLeaveDay", String.valueOf(totalLeaveCount));
                cbundle.putString("totalPresentDay", String.valueOf(totalPresentDayCount));
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_total_leave_count_fragment, cbundle);
                // startActivity(new Intent(getContext(), CalendarLeaveActivity.class));*/
            }
        });


    }


    public void setTaskModelList(int month, int year) {
        int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int firstDayOfMonth = getFirstDayOfMonth(month, year);
        totalDayInMonth = getTotalNoOfDayInMonth(month, year);
        Log.d("Day>>>", "firstDayOfMonth... " + firstDayOfMonth);
        Log.d("month>>>", "totalDayInMonth... " + totalDayInMonth);
        taskModelList.clear();
        switch (firstDayOfMonth) {
            case (1):
                for (int i = 0; i < totalDayInMonth + 6; i++) {
                    CalendarDayModel calendarDayModel = new CalendarDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 5) {
                        calendarDayModel.setToday(true);
                    } else {
                        calendarDayModel.setToday(false);
                    }
                    calendarDayModel.setDay(firstDayOfMonth);
                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 5);
                    taskModelList.add(i, calendarDayModel);
                }
                break;
            case (2):
                for (int i = 0; i < totalDayInMonth; i++) {
                    CalendarDayModel calendarDayModel = new CalendarDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i + 1) {
                        calendarDayModel.setToday(true);
                    } else {
                        calendarDayModel.setToday(false);
                    }
                    calendarDayModel.setDay(firstDayOfMonth);
                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, -1);
                    taskModelList.add(i, calendarDayModel);
                }
                break;
            case 3:
                for (int i = 0; i < totalDayInMonth + 1; i++) {
                    CalendarDayModel calendarDayModel = new CalendarDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i) {
                        calendarDayModel.setToday(true);
                    } else {
                        calendarDayModel.setToday(false);
                    }
                    calendarDayModel.setDay(firstDayOfMonth);
                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 0);
                    taskModelList.add(i, calendarDayModel);
                }
                break;
            case 4:
                for (int i = 0; i < totalDayInMonth + 2; i++) {
                    CalendarDayModel calendarDayModel = new CalendarDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 1) {
                        calendarDayModel.setToday(true);
                    } else {
                        calendarDayModel.setToday(false);
                    }
                    calendarDayModel.setDay(firstDayOfMonth);
                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 1);
                    taskModelList.add(i, calendarDayModel);
                }
                break;
            case 5:
                for (int i = 0; i < totalDayInMonth + 3; i++) {
                    CalendarDayModel calendarDayModel = new CalendarDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 2) {
                        calendarDayModel.setToday(true);
                    } else {
                        calendarDayModel.setToday(false);
                    }
                    calendarDayModel.setDay(firstDayOfMonth);
                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 2);
                    taskModelList.add(i, calendarDayModel);
                }
                break;
            case 6:
                for (int i = 0; i < totalDayInMonth + 4; i++) {
                    CalendarDayModel calendarDayModel = new CalendarDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 3) {
                        calendarDayModel.setToday(true);
                    } else {
                        calendarDayModel.setToday(false);
                    }
                    calendarDayModel.setDay(firstDayOfMonth);
                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 3);
                    taskModelList.add(i, calendarDayModel);
                }
                break;
            case 7:
                for (int i = 0; i < totalDayInMonth + 5; i++) {
                    CalendarDayModel calendarDayModel = new CalendarDayModel();
                    if (currentMonth == month && currentYear == year && currentDate == i - 4) {
                        calendarDayModel.setToday(true);
                    } else {
                        calendarDayModel.setToday(false);
                    }
                    calendarDayModel.setDay(firstDayOfMonth);
                    calendarDayModel = setTaskModelListModel(calendarDayModel, i, 4);
                    taskModelList.add(i, calendarDayModel);
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

    private CalendarDayModel setTaskModelListModel(CalendarDayModel myDayModel, int i, int j) {
        myDayModel.setMonth(spMonth);
        myDayModel.setYear(spYear);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, spYear);
        cal.set(Calendar.MONTH, spMonth);
        cal.set(Calendar.DAY_OF_MONTH, (i - j));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        myDayModel.setDate("" + formatter.format(cal.getTime()));
        myDayModel.setDateSingleDigit("" + (i - j));
        return myDayModel;
    }

    private void updateMonthYear() {
        setTaskModelList(spMonth, spYear);
        for (int i = 0; i < taskModelList.size(); i++) {
            taskModelList.get(i).setSelected(false);
            taskModelList.get(i).setLeaveSelected(false);
        }
        spYear += spMonth / 12;
        spMonth %= 12;
        month_name_tv.setText(months.get(spMonth) + ", " + spYear);
        setDetailOnCalender();
        employeeCalendarAdapter = new CalenderViewAdapter(getContext(), taskModelList, this, _dateRange);
        calenderRv.setAdapter(employeeCalendarAdapter);
        calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
    }

    public void setDetailOnCalender() {

        // Log.d("", "spMonth..... " + String.format("%02d", spMonth) + "  startDateOfSelectedmonth.... " + startDateOfSelectedmonth);
        // leaveEmployeeDetailModel = CSApplicationHelper.application().getDatabaseInstance().leaveCalendarDetail_dao().getLeaveAndPresentDate(regNoStr, startDateOfSelectedmonth);

        Log.e("", "totalDayInMonth   " + totalDayInMonth);

        Attendance_for_calender_model attendanceForCalenderModel;
//        String presentDayStr;
        Leave_Detail_Model leaveDayStr;

        for (int i = 1; i <= totalDayInMonth; i++) {
            String startDateOfSelectedmonth = spYear + "-" + String.format("%02d", spMonth + 1) + "-" + String.format("%02d", i);
//            if (DateUtils.getInstance().dateFromShiftDate(DateUtils.getCurrentDate_format()).before(
//                    DateUtils.getInstance().dateFromShiftDate(startDateOfSelectedmonth)) ||
//                    DateUtils.getInstance().dateFromShiftDate(DateUtils.getCurrentDate_format()).equals(
//                            DateUtils.getInstance().dateFromShiftDate(startDateOfSelectedmonth))) {
         /*   List<MapTableRosterAndShift> mapTableRosterAndShifts = CSApplicationHelper.application().getDatabaseInstance()
                    .rosterDetailDao().getRosterList(CSShearedPrefence.getUser(), startDateOfSelectedmonth);*/
//            if (mapTableRosterAndShifts.size() > 0) {
                leaveDayStr = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
                        getLeaveRecord(CSShearedPrefence.getUser(), startDateOfSelectedmonth);
                if (leaveDayStr != null) {
                    totalLeaveCount++;
                    total_leave_txt.setText(String.valueOf(totalLeaveCount));
                }
          /*      presentDayStr = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                        getLeaveAndPresentDate(CSShearedPrefence.getUser(), startDateOfSelectedmonth);*/
                attendanceForCalenderModel = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                        getAttendanceDetailOn_calender(CSShearedPrefence.getUser(), startDateOfSelectedmonth);
                int firstDayOfMonth = getFirstDayOfMonth(spMonth, spYear);
                Log.e("splitPresentDayStr", "firstDayOfMonth   " + firstDayOfMonth);
                if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                    String splitPresentDayStr = attendanceForCalenderModel.getACT_START_TIME().substring(attendanceForCalenderModel.getACT_START_TIME()
                            .lastIndexOf("-") + 1);
                    Log.e("splitPresentDayStr", "splitPresentDayStr   " + splitPresentDayStr);
                    int presentDate = Integer.parseInt(splitPresentDayStr);
                    Log.e("splitPresentDayStr", "postNameTV   " + presentDate);
                    totalPresentDayCount++;
                    if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 1) {
                        extraDuty++;
                    }
                    extraDuty_TV.setText(String.valueOf(extraDuty));
                    presentCountTV.setText(String.valueOf(totalPresentDayCount));

                }
                switch (firstDayOfMonth) {
                    case 1:
                        taskmodalListPostion = i + 5;
                        taskModelList.get(taskmodalListPostion).setSelected(true);
                        if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                            taskModelList.get(taskmodalListPostion).setSelected(true);
                            taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID("P");
                            taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(attendanceForCalenderModel.getFLAG());
                            taskModelList.get(taskmodalListPostion).setExtraDuty(Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()));
                            taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(CSApplicationHelper.application().getDatabaseInstance().
                                    getLeaveTypeList_dao().getColorCode("100")));
                        } else {
                            if (leaveDayStr!= null) {

                                taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID(leaveDayStr.getLEAVE_TYPE());
                                taskModelList.get(taskmodalListPostion).setLeaveStatus(leaveDayStr.getLEAVE_STATUS());
                                taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(leaveDayStr.getUN_SYNC_DATA());
                                taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(leaveDayStr.getLEAVE_COLOR_CODE()));
                            }
                        }
                        break;
                    case 2:
                        taskmodalListPostion = i - 1;
                        taskModelList.get(taskmodalListPostion).setSelected(true);
                        if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                            taskModelList.get(taskmodalListPostion).setSelected(true);
                            taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID("P");// MEANS ATTENDANCE  TODAY
                            taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(attendanceForCalenderModel.getFLAG());
                            taskModelList.get(taskmodalListPostion).setExtraDuty(Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()));
                            taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(CSApplicationHelper.application().getDatabaseInstance().
                                    getLeaveTypeList_dao().getColorCode("100")));
                        } else {
                            if (leaveDayStr !=null) {
 // MEANS LEAVE  TODAY
                                taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID(leaveDayStr.getLEAVE_TYPE());
                                taskModelList.get(taskmodalListPostion).setLeaveStatus(leaveDayStr.getLEAVE_STATUS());
                                taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(leaveDayStr.getUN_SYNC_DATA());
                                taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(leaveDayStr.getLEAVE_COLOR_CODE()));
                            }
                        }
                        break;
                    case 3:
                        taskmodalListPostion = i;
                        taskModelList.get(taskmodalListPostion).setSelected(true);
                        if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                            taskModelList.get(taskmodalListPostion).setSelected(true);
                            taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(attendanceForCalenderModel.getFLAG());
                            taskModelList.get(taskmodalListPostion).setExtraDuty(Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()));
                            taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID("P");//  MEANS ATTENDANCE  TODAY
                            taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(CSApplicationHelper.application().getDatabaseInstance().
                                    getLeaveTypeList_dao().getColorCode("100")));
                        } else {
                            if (leaveDayStr!= null) {
                       // MEANS LEAVE  TODAY
                                taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID(leaveDayStr.getLEAVE_TYPE());
                                taskModelList.get(taskmodalListPostion).setLeaveStatus(leaveDayStr.getLEAVE_STATUS());
                                taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(leaveDayStr.getUN_SYNC_DATA());
                                taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(leaveDayStr.getLEAVE_COLOR_CODE()));
                            }
                        }
                        break;
                    case 4:
                        taskmodalListPostion = i + 1;
                        taskModelList.get(taskmodalListPostion).setSelected(true);
                        if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                            taskModelList.get(taskmodalListPostion).setSelected(true);
                            taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(attendanceForCalenderModel.getFLAG());
                            taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID("P");// MEANS ATTENDANCE  TODAY
                            taskModelList.get(taskmodalListPostion).setExtraDuty(Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()));
                            taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(CSApplicationHelper.application().getDatabaseInstance().
                                    getLeaveTypeList_dao().getColorCode("100")));
                        } else {
                            if (leaveDayStr != null) {
                          // MEANS LEAVE  TODAY
                                taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID(leaveDayStr.getLEAVE_TYPE());
                                taskModelList.get(taskmodalListPostion).setLeaveStatus(leaveDayStr.getLEAVE_STATUS());
                                taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(leaveDayStr.getUN_SYNC_DATA());
                                taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(leaveDayStr.getLEAVE_COLOR_CODE()));
                            }
                        }
                        break;
                    case 5:
                        taskmodalListPostion = i + 2;
                        taskModelList.get(taskmodalListPostion).setSelected(true);
                        if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                            taskModelList.get(taskmodalListPostion).setSelected(true);
                            taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(attendanceForCalenderModel.getFLAG());
                            taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID("P");// MEANS ATTENDANCE  TODAY
                            taskModelList.get(taskmodalListPostion).setExtraDuty(Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()));
                            taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(CSApplicationHelper.application().getDatabaseInstance().
                                    getLeaveTypeList_dao().getColorCode("100")));
                        } else {
                            if (leaveDayStr != null) {
                         // MEANS LEAVE  TODAY
                                taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID(leaveDayStr.getLEAVE_TYPE());
                                taskModelList.get(taskmodalListPostion).setLeaveStatus(leaveDayStr.getLEAVE_STATUS());
                                taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(leaveDayStr.getUN_SYNC_DATA());
                                taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(leaveDayStr.getLEAVE_COLOR_CODE()));
                            }
                        }
                        break;
                    case 6:
                        taskmodalListPostion = i + 3;
                        taskModelList.get(taskmodalListPostion).setSelected(true);
                        if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                            taskModelList.get(taskmodalListPostion).setSelected(true);
                            taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(attendanceForCalenderModel.getFLAG());
                            taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID("P");// MEANS ATTENDANCE  TODAY
                            taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(attendanceForCalenderModel.getFLAG());
                            taskModelList.get(taskmodalListPostion).setExtraDuty(Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()));
                            taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(CSApplicationHelper.application().getDatabaseInstance().
                                    getLeaveTypeList_dao().getColorCode("100")));
                        } else {
                            if (leaveDayStr != null) {
                            // MEANS LEAVE  TODAY
                                taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID(leaveDayStr.getLEAVE_TYPE());
                                taskModelList.get(taskmodalListPostion).setLeaveStatus(leaveDayStr.getLEAVE_STATUS());
                                taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(leaveDayStr.getUN_SYNC_DATA());
                                taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(leaveDayStr.getLEAVE_COLOR_CODE()));
                            }
                        }
                        break;
                    case 7:
                        taskmodalListPostion = i + 4;
                        taskModelList.get(taskmodalListPostion).setSelected(true);
                        if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                            taskModelList.get(taskmodalListPostion).setSelected(true);
                            taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(attendanceForCalenderModel.getFLAG());
                            taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID("P");// MEANS ATTENDANCE  TODAY
                            taskModelList.get(taskmodalListPostion).setExtraDuty(Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()));
                            taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(CSApplicationHelper.application().getDatabaseInstance().
                                    getLeaveTypeList_dao().getColorCode("100")));
                        } else {
                            if (leaveDayStr != null) {
                        // MEANS LEAVE  TODAY
                                taskModelList.get(taskmodalListPostion).setLEAVE_TYPE_ID(leaveDayStr.getLEAVE_TYPE());
                                taskModelList.get(taskmodalListPostion).setLeaveStatus(leaveDayStr.getLEAVE_STATUS());
                                taskModelList.get(taskmodalListPostion).setUN_SYNC_DATA(leaveDayStr.getUN_SYNC_DATA());
                                taskModelList.get(taskmodalListPostion).setColorCode(Color.parseColor(leaveDayStr.getLEAVE_COLOR_CODE()));
                            }
                        }
                        break;
                }
//            }

        }
    }


    @Override
    public void taskClicked(int date, String statusType) {

        Log.e("taskClicked", "click on task ..... " + date);
        String selecteddate = spYear + "-" + String.format("%02d", spMonth + 1) + "-" + String.format("%02d", date);
        Log.e("taskClicked", "selecteddate..... " + selecteddate);
        if (statusType.equalsIgnoreCase("ATTENDANCE")) {
            showAttendanceDetail_onDate_WiseAction(selecteddate);
        } else {
        showLeaveRecord(selecteddate);
        }
    }


    private void showLeaveRecord(String selectDate) {
        try {
            LeaveDetailTransaction leaveDetailTransaction = CSApplicationHelper.application().getDatabaseInstance().
                    leaveTransactionDetail_dao().recordDateBase(selectDate, CSShearedPrefence.getUser());
            Dialog dialog = new Dialog(csActivity, android.R.style.Theme_Light);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.view_leave_record_on_calender);
            TextView leave_reasonTV = dialog.findViewById(R.id.leave_reasonTV);
            LinearLayout main_layout = dialog.findViewById(R.id.main_layout);
            CardView leaveCardDetailCV = dialog.findViewById(R.id.leaveCardDetailCV);
            TextView leave_DateTV = dialog.findViewById(R.id.leave_DateTV);
            TextView leaveDescTV = dialog.findViewById(R.id.leaveDescTV);
            TextView leave_status_TV = dialog.findViewById(R.id.leave_status_TV);
            Button sync_BTN = dialog.findViewById(R.id.sync_BTN);
            TextView date_tv = dialog.findViewById(R.id.date_tv);
            TextView weekName_TV = dialog.findViewById(R.id.weekName_TV);
            AppCompatImageView cancelImg = dialog.findViewById(R.id.cancelImg);
            LinearLayout un_sync_Des_LY = dialog.findViewById(R.id.un_sync_Des_LY);
            RecyclerView recyclerview = dialog.findViewById(R.id.recyclerview);
            SimpleDateFormat sdf1 = DateUtils.getInstance().getDate_And_MonthWordFormat();
            String shownDay = sdf1.format(DateUtils.getInstance().dateFromShiftDate(selectDate));
            String dayOfWeek = DateUtils.getInstance().getWeek_WordFormat().format(DateUtils.getInstance().
                    dateFromShiftDate(selectDate));
            date_tv.setText( shownDay);
            weekName_TV.setText( dayOfWeek);
//            TextView attendanceDescriptionTextTV = dialog.findViewById(R.id.attendanceDescriptionTextTV);
            cancelImg.setOnClickListener(view -> dialog.dismiss());

            if (leaveDetailTransaction != null) {
                if(leaveDetailTransaction.getFlag().equalsIgnoreCase("1")){
                    un_sync_Des_LY.setVisibility(View.VISIBLE);
                    sync_BTN.setVisibility(View.VISIBLE);
                }else {
                    un_sync_Des_LY.setVisibility(View.GONE);
                    sync_BTN.setVisibility(View.GONE);
                }
                leaveCardDetailCV.setVisibility(View.VISIBLE);
                leaveDescTV.setVisibility(View.VISIBLE);
                leave_DateTV.setText(leaveDetailTransaction.getLeaveDate());
                leave_reasonTV.setText(CSApplicationHelper.application().getDatabaseInstance().getLeaveTypeList_dao().
                        singleLeave(String.valueOf(leaveDetailTransaction.getLeaveReasonId())).getLEAVE_TYPE());
                leave_status_TV.setText(CFUtil.returnLeaveStatusDescription
                        (String.valueOf(CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao()
                                .getDetails(leaveDetailTransaction.getLeaveMaster_Id()).getLeaveStatus())));
           /*     sync_BTN.setOnClickListener(view -> {
                    List<LeaveDetailTransaction> postLeaveTransaction = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao()
                            .getListToSync_on_SpecificDate("1",selectDate);
                    if (postLeaveTransaction.size() > 0) {
                        try {
                            JSONArray array = new JSONArray();

                            Log.e("getListToSync", "PostLeaveTransactionWorker" + postLeaveTransaction.size());
                            for (int i = 0; i < postLeaveTransaction.size(); i++) {
                                array.put(new JSONObject(postLeaveTransaction.get(i).getJSON()));
                            }
                            new VolleyAsyncClassPost(this, array, POST_LEAVE_DETAIL, POST_LEAVE_DETAIL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    List<LeaveMaster> postLeave=new ArrayList<>();
                    LeaveMaster leaveMaster=CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().
                            getDetails(leaveDetailTransaction.getLeaveMaster_Id());
                    if(leaveMaster.getLEAVE_REQUEST_TYPE().equalsIgnoreCase("M")) {
                       postLeave = CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().
                                getListToSync_on_start_end_date("1",leaveMaster.getLeaveStartDate(),leaveMaster.getLeaveEndDate());
                    }else {

                    }
                    if (postLeave.size() > 0) {
                        try {
                            JSONArray array = new JSONArray();

                            Log.e("getListToSync", "POST_LEAVE_MASTER" + postLeave.size());
                            for (int i = 0; i < postLeave.size(); i++) {
                                array.put(new JSONObject(postLeave.get(i).getJson()));
                            }
                            new VolleyAsyncClassPost(this, array, POST_LEAVE_MASTER, POST_LEAVE_MASTER);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });*/

            }
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLegendDialog(Context context, View lin) {

        PopupWindow dialog_AllergiesSelect = new PopupWindow(context);
        View v = View.inflate(context, R.layout.view_legend_screen, null);
        dialog_AllergiesSelect.setContentView(v);
        RecyclerView viewlegendRV = v.findViewById(R.id.viewlegendRV);
        viewlegendRV.setAdapter(new Viewlegend_Adapter(this, CSApplicationHelper.application().getDatabaseInstance().getLeaveTypeList_dao().getLeaveListForLegend()));
        dialog_AllergiesSelect.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_AllergiesSelect.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_AllergiesSelect.setFocusable(true);
        int OFFSET_X = -10;
        int OFFSET_Y = 80;

        dialog_AllergiesSelect.setBackgroundDrawable(new BitmapDrawable());
        Rect rect = CFUtil.locateView(lin);
        dialog_AllergiesSelect.showAtLocation(v, Gravity.NO_GRAVITY, rect.left + OFFSET_X, rect.top + OFFSET_Y);
    }

    private void showAttendanceDetail_onDate_WiseAction(String selectDate) {
        try {
            List<DailyAttendanceDetail> getDailyAttendanceList = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                    getAttendanceDetailRecordPresent(CSShearedPrefence.getUser(), selectDate);
            if (getDailyAttendanceList.size() != 0) {
                Dialog dialog = new Dialog(csActivity, android.R.style.Theme_Light);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.present_attendance_in_calender_dialog);
                TextView date_tv = dialog.findViewById(R.id.date_tv);
                TextView weekName_TV = dialog.findViewById(R.id.weekName_TV);
                LinearLayout main_layout = dialog.findViewById(R.id.main_layout);
                AppCompatImageView cancelImg = dialog.findViewById(R.id.cancelImg);
                Button sync_BTN = dialog.findViewById(R.id.sync_BTN);
                RecyclerView recyclerview = dialog.findViewById(R.id.recyclerview);

                SimpleDateFormat sdf1 = DateUtils.getInstance().getDate_And_MonthWordFormat();
                String shownDay = sdf1.format(DateUtils.getInstance().dateFromShiftDate(selectDate));
                String dayOfWeek = DateUtils.getInstance().getWeek_WordFormat().format(DateUtils.getInstance().
                        dateFromShiftDate(selectDate));
                date_tv.setText( shownDay);
                weekName_TV.setText( dayOfWeek);
                recyclerview.setAdapter(new PresentAttendanceInCalenderAdapter(csActivity, getDailyAttendanceList));
                cancelImg.setOnClickListener(view -> dialog.dismiss());
                dialog.show();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.VISIBLE;
    }

    @Override
    public void postDataFromVolleyInterface(JSONObject response, String tableName) {
        try {
            CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();

            Log.e("DAILY_ATTENDANCEDETAIL>", "DAILY_ATTENDANCEDETAIL.....   " + response.toString());
            if (response.optString("status").equals("true")) {
                JSONArray array = response.getJSONArray("data");
                //
                CSShearedPrefence.setAttendanceSyncTime(DateUtils.getInstance().getSaveDateTimeString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject idObject = array.getJSONObject(i);
                    String ID = idObject.optString("ID");
                    CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().updateFlag(ID, "0");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
