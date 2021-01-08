package com.sisindia.mysis.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sisindia.mysis.Adapter.AddLeaveCalendarAdapter;
import com.sisindia.mysis.Adapter.EmployeeCalendarAdapter;
import com.sisindia.mysis.Adapter.View_Leave_typeList_Adapter;
import com.sisindia.mysis.Adapter.Viewlegend_Adapter;
import com.sisindia.mysis.Json_Parameter.CreateParameter;
import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.entity.LeaveDetailTransaction;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.LeaveMaster;
import com.sisindia.mysis.entity.LeaveTypeListModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.listener.RecyclerItemClickListener;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.utils.DialogClass;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


public class EmployeeTakeLeaveScreen extends CSHeaderFragmentFragment implements EmployeeCalendarAdapter.TaskCalendarClickListener,
        RecyclerItemClickListener.OnItemClickListener, AddLeaveCalendarAdapter.CalendarOnDateClickListener {

    @BindView(R.id.calenderRv)
    RecyclerView calenderRv;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.previous_month_iv)
    ImageView previous_month_iv;
    @BindView(R.id.next_month_iv)
    ImageView next_month_iv;
    @BindView(R.id.month_name_tv)
    TextView month_name_tv;
    @BindView(R.id.multiple_day)
    RadioButton multiple_day;
    @BindView(R.id.single_day)
    RadioButton single_day;
    @BindView(R.id.single_day_CardView)
    CardView single_day_CardView;
    @BindView(R.id.mutiple_day_from_to_linear_layout)
    LinearLayout mutiple_day_from_to_linear_layout;
    @BindView(R.id.selected_date)
    TextView selected_date;
    @BindView(R.id.selected_month)
    TextView selected_month;
    @BindView(R.id.from_date)
    TextView from_date;
    @BindView(R.id.to_date)
    TextView to_date;
    @BindView(R.id.raise_request_btn)
    Button raise_request_btn;
    @BindView(R.id.backPressLY)
    LinearLayout backPressLY;
    @BindView(R.id.main_layout)
    LinearLayout main_layout;
    int spMonth = Calendar.getInstance().get(Calendar.MONTH);
    int spYear = Calendar.getInstance().get(Calendar.YEAR);
    private Calendar calendar;
    private List<CalendarDayModel> taskModelList = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private Boolean _dateRange = false, _rangeStartDate = false;
    private CalendarDayModel leaveEmployeeDetailModel;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private DialogClass dialogClass;
    private Integer totalDayInMonth;
    private int taskmodalListPostion;
    EmployeeCalendarAdapter employeeCalendarAdapter;
    private Boolean _fromDateRange = false, customdate = true, leaveAlready = false;
    int startDateNo = 0, endDateNo = 0, datePostion, selectStartDatePosition = 0, selectEndDatePosition;
    AddLeaveCalendarAdapter addLeaveCalendarAdapter;
    @BindView(R.id.backPressIV)
    AppCompatImageView backPressIV;
    @BindView(R.id.viewLegendTV)
    TextView viewLegendTV;
    private LeaveMaster leaveMaster;
    private Integer leaveTypeClickPosition = -1;
    private View_Leave_typeList_Adapter view_leave_typeList_adapter;
    private List<LeaveTypeListModel> leaveTypeListModelList;
    private String startDate = "", endDate = "";
    private List<CalendarDayModel> db_insertList = new ArrayList<>();
    private List<String> datesList = new ArrayList<>();

    @Override
    public int layoutResource() {
        return R.layout.fragment_employee_take_leave_calander;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        setCalendar();
        Drawable mIcon= ContextCompat.getDrawable(getActivity(), R.drawable.white_arrow);

        if(CSShearedPrefence.isNightModeEnabled()){
            previous_month_iv.setImageDrawable(mIcon);
            next_month_iv.setImageDrawable(mIcon);
            backPressIV.setImageDrawable(mIcon);
            backPressIV.setRotation(180.0f);
            previous_month_iv.setRotation(180.0f);
            main_layout.setBackground(null);
        }else {
            mIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red_Color), PorterDuff.Mode.MULTIPLY);
            previous_month_iv.setImageDrawable(mIcon);
            next_month_iv.setImageDrawable(mIcon);
            previous_month_iv.setRotation(180.0f);
        }
        backPressIV.setOnClickListener(view -> csActivity.onBackPressed());
        viewLegendTV.setOnClickListener(view -> showLegendDialog(csActivity, viewLegendTV));
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    @OnClick({R.id.backPressLY, R.id.raise_request_btn})
    public void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.backPressIV:
                csActivity.onBackPressed();
                break;
            case R.id.raise_request_btn:
                showDialog();

                break;
        }
    }
    public void setCalendar() {
        try {
            spMonth = Calendar.getInstance().get(Calendar.MONTH);
            spYear = Calendar.getInstance().get(Calendar.YEAR);

            calenderRv.setLayoutManager(new GridLayoutManager(getContext(), 7));
            setTaskModelList(spMonth, spYear);
            for (int i = 0; i < taskModelList.size(); i++) {
                taskModelList.get(i).setSelected(false);
                taskModelList.get(i).setLeaveSelected(false);
            }
            selected_month.setText(new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime()));
            selected_date.setText("" + Calendar.getInstance().get(Calendar.DATE));
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    if (null != rb && checkedId > -1) {
                        switch (rb.getText().toString()) {
                            case "Single Day":
                                _fromDateRange = false;
                                endDate = "";
                                startDate = "";
                                customdate = true;
                                mutiple_day_from_to_linear_layout.setVisibility(View.GONE);
                                single_day_CardView.setVisibility(View.VISIBLE);
                                for (int i = 0; i < taskModelList.size(); i++) {
                                    taskModelList.get(i).setSelected(false);
                                }
                                setRaiseButtonDisable();
                                addLeaveCalendarAdapter = new AddLeaveCalendarAdapter(csActivity, taskModelList,
                                        EmployeeTakeLeaveScreen.this::clickOnDate, _fromDateRange, startDate, endDate);
                                calenderRv.setAdapter(addLeaveCalendarAdapter);
                                calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
                                break;
                            case "Multiple Days":
                                _fromDateRange = true;
                                customdate = false;
                                _rangeStartDate = true;
                                mutiple_day_from_to_linear_layout.setVisibility(View.VISIBLE);
                                single_day_CardView.setVisibility(View.GONE);
                                for (int i = 0; i < taskModelList.size(); i++) {
                                    taskModelList.get(i).setSelected(false);
                                }
                                setRaiseButtonDisable();
                                addLeaveCalendarAdapter = new AddLeaveCalendarAdapter(csActivity, taskModelList, EmployeeTakeLeaveScreen.this::clickOnDate, _fromDateRange, startDate, endDate);
                                calenderRv.setAdapter(addLeaveCalendarAdapter);
                                calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
                                break;

                        }
                    }

                }
            });

            Collections.addAll(months, getResources().getStringArray(R.array.month));
            month_name_tv.setText(months.get(spMonth) + ", " + spYear);
            // setLeaveDateOnCalendar();
            // employeeCalendarAdapter = new EmployeeCalendarAdapter(getContext(), taskModelList, this, _dateRange);
            addLeaveCalendarAdapter = new AddLeaveCalendarAdapter(csActivity, taskModelList, this::clickOnDate, _fromDateRange, startDate, endDate);
            calenderRv.setAdapter(addLeaveCalendarAdapter);
            calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);

            previous_month_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  for (int i = 0; i < taskModelList.size(); i++) {
                        taskModelList.get(i).setSelected(false);
                    }*/
                    spMonth += (11);
                    spYear--;
                    updateMonthYear();
//                    BackupdateMonthYear();
                }
            });

            next_month_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* for (int i = 0; i < taskModelList.size(); i++) {
                        taskModelList.get(i).setSelected(false);
                    }*/
                    spMonth++;
                    updateMonthYear();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void taskClicked(int date) {
    }

    public void setTaskModelList(int month, int year) {
        try {
            int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int firstDayOfMonth = getFirstDayOfMonth(month, year);
            totalDayInMonth = getTotalNoOfDayInMonth(month, year);
            Log.e("Day>>>", "firstDayOfMonth... " + firstDayOfMonth);
            Log.e("month>>>", "totalDayInMonth... " + totalDayInMonth);
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
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
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
            if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(
                    formatter.format(cal.getTime()), CSShearedPrefence.getUser())) {
                myDayModel.setAlreadyLeaveApply(CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(
                        formatter.format(cal.getTime()), CSShearedPrefence.getUser()));
                if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
                        recordDateBase(formatter.format(cal.getTime()), CSShearedPrefence.getUser()) != null) {
                    LeaveDetailTransaction leaveDetailTransaction = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
                            recordDateBase(formatter.format(cal.getTime()), CSShearedPrefence.getUser());
                    myDayModel.setLeaveReasonId(String.valueOf(leaveDetailTransaction.getLeaveReasonId()));
                    myDayModel.setColorCode(Color.parseColor(CSApplicationHelper.application().getDatabaseInstance().getLeaveTypeList_dao().getColorCode(
                            String.valueOf(leaveDetailTransaction.getLeaveReasonId()))));

                }
            } else {
                myDayModel.setAlreadyLeaveApply(false);
                myDayModel.setLeaveReasonId("0");
            }
            myDayModel.setDateSingleDigit("" + (i - j));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myDayModel;
    }

    private void updateMonthYear() {
        try {

            setTaskModelList(spMonth, spYear);
            for (int i = 0; i < taskModelList.size(); i++) {

                taskModelList.get(i).setSelected(false);
                taskModelList.get(i).setLeaveSelected(false);

                if (!CFUtil.isEmptyStr(startDate)) {

                    if (DateUtils.getInstance().dateFromShiftDate(startDate).equals(DateUtils.getInstance().dateFromShiftDate(taskModelList.get(i).getDate()))) {
                        taskModelList.get(i).setDayNumber(1);

                    } else if (!CFUtil.isEmptyStr(endDate)) {
                        if (DateUtils.getInstance().dateFromShiftDate(taskModelList.get(i).getDate()).equals(DateUtils.getInstance().dateFromShiftDate(endDate))) {
                            taskModelList.get(i).setDayNumber(3);
                        } else if (DateUtils.getInstance().dateFromShiftDate(taskModelList.get(i).getDate()).after(DateUtils.getInstance().dateFromShiftDate(startDate))
                                && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                                dateFromShiftDate(taskModelList.get(i).getDate()))) {
                            taskModelList.get(i).setDayNumber(2);
                        }
                    }

                }
            }


            spYear += spMonth / 12;
            spMonth %= 12;
            month_name_tv.setText(months.get(spMonth) + ", " + spYear);
            //  setLeaveDateOnCalendar();
            //employeeCalendarAdapter = new EmployeeCalendarAdapter(getContext(), taskModelList, this, _dateRange);
            addLeaveCalendarAdapter = new AddLeaveCalendarAdapter(csActivity, taskModelList, this::clickOnDate, _fromDateRange, startDate, endDate);
            calenderRv.setAdapter(addLeaveCalendarAdapter);
            calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void clickOnDate(int date, String type) {
        show_Log_Data("clickOnDate", "" + type);
        for (int i = 0; i < taskModelList.size(); i++) {
            taskModelList.get(i).setSelected(false);
        }

        if (_fromDateRange) {
            if (_rangeStartDate) {

                for (int i = 0; i < taskModelList.size(); i++) {
                    taskModelList.get(i).setDayNumber(0);
                    endDate = "";
                }
                selectStartDatePosition = date;
                db_insertList.clear();
                datesList.clear();
                db_insertList.add(taskModelList.get(date));
                taskModelList.get(date).setDayNumber(1);
                _rangeStartDate = false;
                startDate = taskModelList.get(date).getDate();
//                datesList.add(startDate);
                startDateNo = Integer.parseInt(taskModelList.get(date).getDateSingleDigit());
                from_date.setText(convertDateTo_DD_MM_Format(taskModelList.get(selectStartDatePosition).getDate()));
                raise_request_btn.setBackgroundResource(R.drawable.light_grey_round_background);
                raise_request_btn.setEnabled(false);
                to_date.setText(CSStringUtil.getString(R.string.to));
                raise_request_btn.setTextColor(getResources().getColor(R.color.light_black));
            } else {

                endDateNo = Integer.parseInt(taskModelList.get(date).getDateSingleDigit());
                endDate = taskModelList.get(date).getDate();
                to_date.setText(String.valueOf(endDateNo));
                datesList = getDates(startDate, endDate);
                raise_request_btn.setBackgroundResource(R.drawable.background_border_red_app_bg);
                raise_request_btn.setEnabled(true);
                raise_request_btn.setTextColor(getResources().getColor(R.color.white));
                setRaiseButtonEnable();
                /*if (startDateNo < endDateNo) {
                    taskModelList.get(date).setDayNumber(3);
                } else {
                    // reset
                    for (int i = 0; i < taskModelList.size(); i++) {
                        taskModelList.get(i).setDayNumber(0);
                    }
                }*/
                if (DateUtils.getInstance().dateFromShiftDate(startDate).before(DateUtils.getInstance().dateFromShiftDate(endDate))) {
                    taskModelList.get(date).setDayNumber(3);
                    db_insertList.add(taskModelList.get(date));
                } else {
                    // reset
                    for (int i = 0; i < taskModelList.size(); i++) {
                        taskModelList.get(i).setDayNumber(0);
                        startDate = "";
                        endDate = "";
                        db_insertList.clear();
                        datesList.clear();
                        raise_request_btn.setBackgroundResource(R.drawable.next_button_gray_background);
                        raise_request_btn.setEnabled(false);
                        raise_request_btn.setTextColor(getResources().getColor(R.color.light_black));
                        to_date.setText(CSStringUtil.getString(R.string.to));
                    }
                }
                selectEndDatePosition = date;
                _rangeStartDate = true;
                setMiddleDates();

                to_date.setText(convertDateTo_DD_MM_Format(taskModelList.get(selectEndDatePosition).getDate()));
            }
        } else {
            raise_request_btn.setEnabled(true);
            selectStartDatePosition = date;
            selectEndDatePosition = date;
            datePostion = date;
            taskModelList.get(date).setSelected(true);
            selected_date.setText(String.valueOf(taskModelList.get(date).getDateSingleDigit()));
            selected_month.setText(month_name_tv.getText().toString().substring(0, month_name_tv.getText().toString().indexOf(",")));
            setRaiseButtonEnable();
        }
        if (leaveAlready) {
            leaveAlready = false; // comment then first day is select show
            for (int i = 0; i < taskModelList.size(); i++) {
                taskModelList.get(i).setDayNumber(0);
            }
        }
//        addLeaveCalendarAdapter = new AddLeaveCalendarAdapter(csActivity, taskModelList, this, _fromDateRange,curreentDateTag);
//        calenderRv.setAdapter(addLeaveCalendarAdapter);
        addLeaveCalendarAdapter.notifyDataSetChanged();
//        calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);

    }

    private void setRaiseButtonEnable() {
        raise_request_btn.setBackgroundResource(R.drawable.background_border_red_app_bg);
        raise_request_btn.setTextColor(getResources().getColor(R.color.white));
    }

    private void setRaiseButtonDisable() {
        raise_request_btn.setBackgroundResource(R.drawable.next_button_gray_background);
        raise_request_btn.setTextColor(getResources().getColor(R.color.text_color_hint));
    }

    private void setMiddleDates() {
        int firstDayOfMonth = getFirstDayOfMonth(spMonth, spYear);
        totalDayInMonth = getTotalNoOfDayInMonth(spMonth, spYear);
        Log.e("Day>>>", "firstDayOfMonth... " + firstDayOfMonth);
        Log.e("month>>>", "totalDayInMonth... " + totalDayInMonth);

        switch (firstDayOfMonth) {
            case (1):
                for (int i = 6; i < taskModelList.size(); i++) {
                    CalendarDayModel myDayModel = taskModelList.get(i);

                    if (DateUtils.getInstance().dateFromShiftDate(myDayModel.getDate()).after(DateUtils.getInstance().
                            dateFromShiftDate(startDate)) && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                            dateFromShiftDate(myDayModel.getDate()))) {
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                            leaveAlready = true;
                            break;
                        } else {
                            leaveAlready = false;
                            taskModelList.get(i).set_dateRange(true);
                            taskModelList.get(i).setDayNumber(2);
                            myDayModel.setDayNumber(2);
                            db_insertList.add(taskModelList.get(i));
                            Log.e("db_insertList", "INSERT_DATE" + taskModelList.get(i).getDate());
                            Log.e("db_insertList>>>", "MIDDLE" + db_insertList.size());
                        }


                    }

                }
                break;
            case (2):
                for (int i = 0; i < taskModelList.size(); i++) {
                    CalendarDayModel myDayModel = taskModelList.get(i);

                    if (DateUtils.getInstance().dateFromShiftDate(myDayModel.getDate()).after(DateUtils.getInstance().
                            dateFromShiftDate(startDate)) && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                            dateFromShiftDate(myDayModel.getDate()))) {
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                            leaveAlready = true;
                            break;
                        } else {
                            leaveAlready = false;
                            taskModelList.get(i).set_dateRange(true);
                            taskModelList.get(i).setDayNumber(2);
                            myDayModel.setDayNumber(2);
                            db_insertList.add(taskModelList.get(i));
                            Log.e("db_insertList", "INSERT_DATE" + taskModelList.get(i).getDate());
                            Log.e("db_insertList>>>", "MIDDLE" + db_insertList.size());
                        }


                    }
                }
                break;
            case 3:
                for (int i = 1; i < taskModelList.size(); i++) {
                    CalendarDayModel myDayModel = taskModelList.get(i);

                    if (DateUtils.getInstance().dateFromShiftDate(myDayModel.getDate()).after(DateUtils.getInstance().
                            dateFromShiftDate(startDate)) && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                            dateFromShiftDate(myDayModel.getDate()))) {
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                            leaveAlready = true;
                            break;
                        } else {
                            leaveAlready = false;
                            taskModelList.get(i).set_dateRange(true);
                            taskModelList.get(i).setDayNumber(2);
                            myDayModel.setDayNumber(2);
                            db_insertList.add(taskModelList.get(i));
                            Log.e("db_insertList", "INSERT_DATE" + taskModelList.get(i).getDate());
                            Log.e("db_insertList>>>", "MIDDLE" + db_insertList.size());
                        }


                    }
                }
                break;
            case 4:
                for (int i = 2; i < taskModelList.size(); i++) {
                    CalendarDayModel myDayModel = taskModelList.get(i);

                    if (DateUtils.getInstance().dateFromShiftDate(myDayModel.getDate()).after(DateUtils.getInstance().
                            dateFromShiftDate(startDate)) && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                            dateFromShiftDate(myDayModel.getDate()))) {
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                            leaveAlready = true;
                            break;
                        } else {
                            leaveAlready = false;
                            taskModelList.get(i).set_dateRange(true);
                            taskModelList.get(i).setDayNumber(2);
                            myDayModel.setDayNumber(2);
                            db_insertList.add(taskModelList.get(i));
                            Log.e("db_insertList", "INSERT_DATE" + taskModelList.get(i).getDate());
                            Log.e("db_insertList>>>", "MIDDLE" + db_insertList.size());
                        }


                    }
                }
                break;
            case 5:
                for (int i = 3; i < taskModelList.size(); i++) {
                    CalendarDayModel myDayModel = taskModelList.get(i);

                    if (DateUtils.getInstance().dateFromShiftDate(myDayModel.getDate()).after(DateUtils.getInstance().
                            dateFromShiftDate(startDate)) && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                            dateFromShiftDate(myDayModel.getDate()))) {
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                            leaveAlready = true;
                            break;
                        } else {
                            leaveAlready = false;
                            taskModelList.get(i).set_dateRange(true);
                            taskModelList.get(i).setDayNumber(2);
                            myDayModel.setDayNumber(2);
                            db_insertList.add(taskModelList.get(i));
                            Log.e("db_insertList", "INSERT_DATE" + taskModelList.get(i).getDate());
                            Log.e("db_insertList>>>", "MIDDLE" + db_insertList.size());
                        }


                    }
                }
                break;
            case 6:
                for (int i = 4; i < taskModelList.size(); i++) {
                    CalendarDayModel myDayModel = taskModelList.get(i);

                    if (DateUtils.getInstance().dateFromShiftDate(myDayModel.getDate()).after(DateUtils.getInstance().
                            dateFromShiftDate(startDate)) && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                            dateFromShiftDate(myDayModel.getDate()))) {
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                            leaveAlready = true;
                            break;
                        } else {
                            leaveAlready = false;
                            taskModelList.get(i).set_dateRange(true);
                            taskModelList.get(i).setDayNumber(2);
                            myDayModel.setDayNumber(2);
                            db_insertList.add(taskModelList.get(i));
                            Log.e("db_insertList", "INSERT_DATE" + taskModelList.get(i).getDate());
                            Log.e("db_insertList>>>", "MIDDLE" + db_insertList.size());
                        }


                    }
                }
                break;
            case 7:
                for (int i = 5; i < taskModelList.size(); i++) {
                    CalendarDayModel myDayModel = taskModelList.get(i);

                    if (DateUtils.getInstance().dateFromShiftDate(myDayModel.getDate()).after(DateUtils.getInstance().
                            dateFromShiftDate(startDate)) && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                            dateFromShiftDate(myDayModel.getDate()))) {
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                            leaveAlready = true;
                            break;
                        } else {
                            leaveAlready = false;
                            taskModelList.get(i).set_dateRange(true);
                            taskModelList.get(i).setDayNumber(2);
                            myDayModel.setDayNumber(2);
                            db_insertList.add(taskModelList.get(i));
                            Log.e("db_insertList", "INSERT_DATE" + taskModelList.get(i).getDate());
                            Log.e("db_insertList>>>", "MIDDLE" + db_insertList.size());
                        }


                    }
                }
                break;
        }


       /* try {
            for (int i = 0; i < taskModelList.size(); i++) {

                CalendarDayModel myDayModel = taskModelList.get(i);
            *//*if (Integer.parseInt(myDayModel.getDateSingleDigit()) == startDateNo) {
                myDayModel.setDayNumber(1);
            }
            if (Integer.parseInt(myDayModel.getDateSingleDigit()) == endDateNo) {
                myDayModel.setDayNumber(3);
            }*//*
         *//* if(i>=myDayModel.getDay()){
                if (endDatePosition > i) {
                    if (DateUtils.getInstance().dateFromShiftDate(startDate).before(DateUtils.getInstance()
                            .dateFromShiftDate(taskModelList.get(endDatePosition).getDate()))) {

                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
                                checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                            leaveAlready = true;
                            break;
                        } else {
                            leaveAlready = false;
                            taskModelList.get(i).set_dateRange(true);
                            taskModelList.get(i).setDayNumber(2);
                            myDayModel.setDayNumber(2);
                        }


                    }
                } else {
                    break;
                }

            }*//*

//                if (Integer.parseInt(myDayModel.getDateSingleDigit()) > startDateNo && Integer.parseInt(myDayModel.getDateSingleDigit()) < endDateNo) {
                if (DateUtils.getInstance().dateFromShiftDate(myDayModel.getDate()).after(DateUtils.getInstance().
                        dateFromShiftDate(startDate)) && DateUtils.getInstance().dateFromShiftDate(endDate).after(DateUtils.getInstance().
                        dateFromShiftDate(myDayModel.getDate()))) {
                    if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(taskModelList.get(i).getDate(), CSShearedPrefence.getUser())) {
                        CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));
                        leaveAlready = true;
                        break;
                    } else {
                        leaveAlready = false;
                        taskModelList.get(i).set_dateRange(true);
                        taskModelList.get(i).setDayNumber(2);
                        myDayModel.setDayNumber(2);
                        db_insertList.add(taskModelList.get(i));
                        Log.e("db_insertList>>>","MIDDLE"+db_insertList.size());
                    }

                }


            }
            // startDateNo = 0;
            // endDateNo = 0;
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }*/
    }


    @SuppressLint("SetTextI18n")
    public void showDialog() {
        try {
            Dialog dialog = new Dialog(csActivity, android.R.style.Theme_Light);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.setContentView(R.layout.leave_confirm_dialog);
            RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
            RecyclerView leaveTypeRV = dialog.findViewById(R.id.leavetypeRV);
            leaveTypeListModelList = CSApplicationHelper.application().getDatabaseInstance().
                    getLeaveTypeList_dao().getLeaveList();
            view_leave_typeList_adapter = new View_Leave_typeList_Adapter(this, leaveTypeListModelList, leaveTypeClickPosition);
            leaveTypeRV.setAdapter(view_leave_typeList_adapter);
            leaveTypeRV.addOnItemTouchListener(new RecyclerItemClickListener(csActivity, this));

            TextView leave_dateTV = dialog.findViewById(R.id.leave_dateTV);
            TextView countLeaveDaysTV = dialog.findViewById(R.id.countLeaveDaysTV);
            Button confirmBtn = dialog.findViewById(R.id.leave_dialog_confirm_btn);
            AppCompatImageView cancelIV = dialog.findViewById(R.id.cancelIV);
            cancelIV.setOnClickListener(view -> dialog.dismiss());
            if (!_fromDateRange) {
                countLeaveDaysTV.setText(1 + " " + CSStringUtil.getString(R.string.days));
                leave_dateTV.setText(convertDateTo_DD_MM_Format(taskModelList.get(selectStartDatePosition).getDate()));
            } else {
                countLeaveDaysTV.setText(datesList.size() + " " + CSStringUtil.getString(R.string.days));

                leave_dateTV.setText(convertDateTo_DD_MM_Format(startDate) +
                        " - " + convertDateTo_DD_MM_Format(endDate));
            }

           /* radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = group.findViewById(checkedId);

                    if (null != rb && checkedId > -1) {
                        int position = group.indexOfChild(rb);
                        Log.e("selectedId", "" + position);
                        switch (position) {
                            case 0:
                                leaveReason = CFUtil.returnLeaveReasonID(R.string.sick_txt);
                                break;
                            case 1:
                                leaveReason = CFUtil.returnLeaveReasonID(R.string.family_emergency_txt);
                                break;
                            case 2:
                                leaveReason = CFUtil.returnLeaveReasonID(R.string.other_reason_txt);
                                break;
                        }

                    } else {
                        Toast.makeText(getContext(), "Please select leave reason", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (leaveTypeClickPosition == -1) {
                        Toast.makeText(csActivity, "Please select leave reason", Toast.LENGTH_SHORT).show();
                    } else {
                        saveLeaveMasterTable();
                        if (_fromDateRange) {
                            for (int i = 0; i < datesList.size(); i++) {
                                saveleave_In_DB(datesList.get(i), Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                            }

                        } else {
                            saveleave_In_DB(taskModelList.get(datePostion).getDate(),
                                    Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));

                        }
                       /* int firstDayOfMonth = getFirstDayOfMonth(spMonth, spYear);
                        switch (firstDayOfMonth) {
                            case 1:
                                if (_fromDateRange) {
                                    datePostion = startDateNo + 5;
                                    for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
                                        int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
                                        Log.e("save btn.. ", "from list date CASE 1 .....    " + date);
                                        saveleave_In_DB(date, Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                                    }
                                } else {
                                    // datePostion = datePostion-5;
                                    saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()),
                                            Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                                }
                                break;
                            case 2:
                                if (_fromDateRange) {
                                    datePostion = startDateNo - 1;
                                    for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
                                        int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
                                        Log.e("save btn.. ", "from list date CASE 2 .....    " + date);
                                        saveleave_In_DB(date, Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));

                                    }
                                } else {
                                    //datePostion = datePostion+1;
                                    saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                                }
                                break;
                            case 3:
                                if (_fromDateRange) {
                                    datePostion = startDateNo;
                                    for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
                                        int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
                                        Log.e("save btn.. ", "from list date CASE 3 .....    " + date);
                                        saveleave_In_DB(date, Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));

                                    }
                                } else {
                                    saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                                }
                                break;
                            case 4:
                                if (_fromDateRange) {
                                    datePostion = startDateNo + 1;
                                    for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
                                        int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
                                        Log.e("save btn.. ", "from list date CASE 4 .....    " + date);
                                        saveleave_In_DB(date, Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));

                                    }
                                } else {
                                    // datePostion = datePostion-1;
                                    saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                                }
                                break;
                            case 5:
                                if (_fromDateRange) {
                                    datePostion = startDateNo + 2;
                                    for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
                                        int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
                                        Log.e("save btn.. ", "from list date CASE 5.....    " + date);
                                        saveleave_In_DB(date, Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));

                                    }
                                } else {
                                    //datePostion = datePostion-2;
                                    saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                                }
                                break;
                            case 6:
                                if (_fromDateRange) {
                                    datePostion = startDateNo + 3;
                                    for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
                                        int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
                                        Log.e("save btn.. ", "from list date CASE 6 .....    " + date);
                                        saveleave_In_DB(date, Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));

                                    }
                                } else {
                                    // datePostion = datePostion-3;
                                    saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                                }
                                break;
                            case 7:
                                if (_fromDateRange) {
                                    datePostion = startDateNo + 4;
                                    for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
                                        int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
                                        Log.e("save btn.. ", "from list date CASE 7.....    " + date);
                                        saveleave_In_DB(date, Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));

                                    }
                                } else {
                                    //datePostion = datePostion-4;
                                    saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
                                }
                                break;
                        }*/
                        dialog.dismiss();
                        confirmation_for_Apply_leave(CSStringUtil.getTextFromView(countLeaveDaysTV),CSStringUtil.getTextFromView(leave_dateTV)
                        ,Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));

                        // saveleave_In_DB()
                    }
                }
            });

//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void confirmation_for_Apply_leave(String countDays, String leaveDate, int leaveID) {
        try {
            Dialog dialog = new Dialog(csActivity, android.R.style.Theme_Light);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.setContentView(R.layout.confirmation_for_leave_apply);


            TextView leave_dateTV = dialog.findViewById(R.id.leave_dateTV);
            TextView countLeaveDaysTV = dialog.findViewById(R.id.countLeaveDaysTV);
            TextView leave_type_Name_TV = dialog.findViewById(R.id.leave_type_Name_TV);
            Button confirmBtn = dialog.findViewById(R.id.doneBTN);
            AppCompatImageView cancelIV = dialog.findViewById(R.id.cancelIV);
            leave_type_Name_TV.setText(CSApplicationHelper.application().getDatabaseInstance().
                    getLeaveTypeList_dao().getLeaveTypeName(String.valueOf(leaveID)));
            cancelIV.setOnClickListener(view ->{
                dialog.dismiss();
                csActivity.onBackPressed();
            });
                countLeaveDaysTV.setText(countDays + " " + CSStringUtil.getString(R.string.days));
                leave_dateTV.setText(leaveDate);
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        dialog.dismiss();
                        csActivity.onBackPressed();
                        // saveleave_In_DB()
                    }

            });

//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveLeaveMasterTable() {
        leaveMaster = new LeaveMaster();
        leaveMaster.setFlag("1");
        leaveMaster.setREGNO(CSShearedPrefence.getUser());
        leaveMaster.setDATE_MODIFIED(DateUtils.getCurrentDate_TIME_format());
        leaveMaster.setID(String.valueOf(UUID.randomUUID()));
        leaveMaster.setLEAVE_TYPE(Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
        leaveMaster.setLeaveStatus(0);
//        leaveMaster.setDELETED("0");
       /* leaveMaster.setLeaveStartDate(taskModelList.get(selectStartDatePosition).getDate());
        leaveMaster.setLeaveEndDate(taskModelList.get(selectEndDatePosition).getDate());*/
        if (!_fromDateRange) {
            leaveMaster.setLEAVE_REQUEST_TYPE("S");
            leaveMaster.setLeaveStartDate(taskModelList.get(selectStartDatePosition).getDate());
            leaveMaster.setLeaveEndDate(taskModelList.get(selectEndDatePosition).getDate());
        } else {
            leaveMaster.setLEAVE_REQUEST_TYPE("M");
            leaveMaster.setLeaveStartDate(startDate);
            leaveMaster.setLeaveEndDate(endDate);
        }
        leaveMaster.setJson(CreateParameter.leaveMasterJson(leaveMaster));

        CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().singleInsert(leaveMaster);
//    leaveMaster.setJson();
    }

    private void saveleave_In_DB(String date, int leaveDescription) {
        try {
            @SuppressLint("DefaultLocale")
//            String insertLeaveReasonDate = spYear + "-" + String.format("%02d", spMonth + 1) + "-" + String.format("%02d", date);
                    String insertLeaveReasonDate = date;
//            String insertLeaveReasonDate = spYear + "-" + String.format("%02d", spMonth + 1) + "-" + String.format("%02d", date) + " " + "00:00:00";
            Log.e("", "inser leave at date....  " + insertLeaveReasonDate);
//            if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkExist(CSShearedPrefence.getUser(),
//                    insertLeaveReasonDate) != null) {
////                LeaveEmployeeDetails leaveEmployeeDetails = CSApplicationHelper.application().getDatabaseInstance().leaveEmployeeDetail_dao().
////                        checkExist(CSShearedPrefence.getUser(), insertLeaveReasonDate);
//                // leaveEmployeeDetails.setLeaveReason(leaveOptionSelect);
//            } else {
            LeaveDetailTransaction leaveEmployeeDetail = new LeaveDetailTransaction();
            leaveEmployeeDetail.setLeaveDate(insertLeaveReasonDate);
            leaveEmployeeDetail.setIsOff("1");
            leaveEmployeeDetail.setFlag("1");
            leaveEmployeeDetail.setDATE_MODIFIED(DateUtils.getCurrentDate_TIME_format());
            leaveEmployeeDetail.setLeaveMaster_Id(leaveMaster.getID());
            leaveEmployeeDetail.setRegNo(CSShearedPrefence.getUser());
            leaveEmployeeDetail.setID(String.valueOf(UUID.randomUUID()));
            leaveEmployeeDetail.setLeaveReasonId(Integer.parseInt(leaveTypeListModelList.get(leaveTypeClickPosition).getLEAVE_ID()));
            //leaveEmployeeDetail.setLeaveDescriptions(leaveDescription);
            leaveEmployeeDetail.setJSON(createLeaveWeeklyJson(leaveEmployeeDetail));
            CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().insert(leaveEmployeeDetail);
//            }
            //  employeeCalendarAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createLeaveWeeklyJson(LeaveDetailTransaction leaveEmployeeDetails) {
        String jsonParameter = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", leaveEmployeeDetails.getID());
            jsonObject.put("REGNO", leaveEmployeeDetails.getRegNo());
            jsonObject.put("LEAVE_DATE", leaveEmployeeDetails.getLeaveDate());
            jsonObject.put("LEAVE_TYPE_ID", leaveEmployeeDetails.getLeaveReasonId());
            jsonObject.put("IS_OFF", "1");
            jsonObject.put("DATE_MODIFIED", DateUtils.getCurrentDate_TIME_format());
            jsonObject.put("MODIFIED_BY", CSShearedPrefence.getUser_ID());
            jsonObject.put("LEAVE_MASTER_ID", leaveEmployeeDetails.getLeaveMaster_Id());
            jsonObject.put("LEAVE_DESCRIPTION", leaveEmployeeDetails.getLeaveDescriptions());
            jsonParameter = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParameter;

    }

    public void showLegendDialog(Context context, View lin) {

        PopupWindow dialog_AllergiesSelect = new PopupWindow(context);
        View v = View.inflate(context, R.layout.view_legend_screen, null);
        dialog_AllergiesSelect.setContentView(v);
        RecyclerView viewlegendRV = v.findViewById(R.id.viewlegendRV);
        viewlegendRV.setAdapter(new Viewlegend_Adapter(this, CSApplicationHelper.application().getDatabaseInstance().getLeaveTypeList_dao().getLeaveList()));
        dialog_AllergiesSelect.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_AllergiesSelect.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_AllergiesSelect.setFocusable(true);
        int OFFSET_X = -10;
        int OFFSET_Y = 80;

        dialog_AllergiesSelect.setBackgroundDrawable(new BitmapDrawable());
        Rect rect = CFUtil.locateView(lin);
        dialog_AllergiesSelect.showAtLocation(v, Gravity.NO_GRAVITY, rect.left + OFFSET_X, rect.top + OFFSET_Y);
    }

    private String convertDateTo_DD_MM_Format(String date) {
        String reuiredDate = "";
        Date convertToDate = DateUtils.getInstance().dateFromShiftDate(date);
        reuiredDate = DateUtils.getInstance().getDate_And_MonthWordFormat().format(convertToDate);
        Log.e("DATE_CONVERT>>>>", "" + "convertToDate" + convertToDate + "reuiredDate" + reuiredDate);

        return reuiredDate;
    }

    @Override
    public void onItemClick(View view, int position) {
        leaveTypeClickPosition = position;
        show_Log_Data("onItemClick", String.valueOf(+position));
        view_leave_typeList_adapter.update(leaveTypeClickPosition);
        ;
    }

    private List<String> getDates(String dateString1, String dateString2) {
//        ArrayList<String> dates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        while (!cal1.after(cal2)) {
            if (DateUtils.getInstance().dateFromShiftDate(endDate).equals(cal1.getTime())) {
                datesList.add(DateUtils.getInstance().getDateFormat().format(cal1.getTime()));
                show_Log_Data("BETWEEn_DATE", DateUtils.getInstance().getDateFormat().format(cal1.getTime()));
                break;
            } else {
                datesList.add(DateUtils.getInstance().getDateFormat().format(cal1.getTime()));
                show_Log_Data("BETWEEn_DATE", DateUtils.getInstance().getDateFormat().format(cal1.getTime()));
            }
            cal1.add(Calendar.DATE, 1);

        }
        return datesList;
    }
}

