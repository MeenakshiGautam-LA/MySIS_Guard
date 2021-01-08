//package com.sisindia.guardattendance.fragment;
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.graphics.drawable.ColorDrawable;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.sisindia.guardattendance.Adapter.AddLeaveCalendarAdapter;
//import com.sisindia.guardattendance.Adapter.EmployeeCalendarAdapter;
//import com.sisindia.guardattendance.Model.CalendarDayModel;
//import com.sisindia.guardattendance.entity.LeaveDetailTransaction;
//import com.sisindia.guardattendance.R;
//import com.sisindia.guardattendance.application.CSApplicationHelper;
//import com.sisindia.guardattendance.fragment.base.CSHeaderFragmentFragment;
//import com.sisindia.guardattendance.listener.RecyclerItemClickListener;
//import com.sisindia.guardattendance.utils.CSShearedPrefence;
//import com.sisindia.guardattendance.utils.DateUtils;
//import com.sisindia.guardattendance.utils.DialogClass;
//
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import butterknife.BindView;
//
//
//public class EmployeeTakeLeaveCalanderFragment extends CSHeaderFragmentFragment implements EmployeeCalendarAdapter.TaskCalendarClickListener,
//        RecyclerItemClickListener.OnItemClickListener, AddLeaveCalendarAdapter.CalendarOnDateClickListener {
//
//    @BindView(R.id.calenderRv)
//    RecyclerView calenderRv;
//    @BindView(R.id.radioGroup)
//    RadioGroup radioGroup;
//    @BindView(R.id.previous_month_iv)
//    ImageView previous_month_iv;
//    @BindView(R.id.next_month_iv)
//    ImageView next_month_iv;
//    @BindView(R.id.month_name_tv)
//    TextView month_name_tv;
//    @BindView(R.id.multiple_day)
//    RadioButton multiple_day;
//    @BindView(R.id.single_day)
//    RadioButton single_day;
//    @BindView(R.id.single_day_CardView)
//    CardView single_day_CardView;
//    @BindView(R.id.mutiple_day_from_to_linear_layout)
//    LinearLayout mutiple_day_from_to_linear_layout;
//    @BindView(R.id.selected_date)
//    TextView selected_date;
//    @BindView(R.id.selected_month)
//    TextView selected_month;
//    @BindView(R.id.from_date)
//    TextView from_date;
//    @BindView(R.id.to_date)
//    TextView to_date;
//    @BindView(R.id.raise_request_btn)
//            Button raise_request_btn;
//
//    int spMonth = Calendar.getInstance().get(Calendar.MONTH);
//    int spYear = Calendar.getInstance().get(Calendar.YEAR);
//    Calendar calendar;
//    List<CalendarDayModel> taskModelList = new ArrayList<>();
//    ArrayList<String> months = new ArrayList<>();
//    Boolean _dateRange = false, _rangeStartDate = false;
//    CalendarDayModel leaveEmployeeDetailModel;
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//    DialogClass dialogClass;
//    int totalDayInMonth;
//    int taskmodalListPostion;
//    EmployeeCalendarAdapter employeeCalendarAdapter;
//    String leaveReason,regNoStr="GAR000198";
//    Boolean _fromDateRange = false, customdate = false;
//    int startDateNo = 0, endDateNo = 0, datePostion;
//    AddLeaveCalendarAdapter addLeaveCalendarAdapter;
//
//    @Override
//    public int layoutResource() {
//        return R.layout.fragment_employee_take_leave_calander;
//    }
//
//    @Override
//    public void setUpMainHeaderView() {
//        super.setUpMainHeaderView();
//        setCalendar();
//    }
//
//
//    public void setCalendar() {
//        spMonth = Calendar.getInstance().get(Calendar.MONTH);
//        spYear = Calendar.getInstance().get(Calendar.YEAR);
//
//        calenderRv.setLayoutManager(new GridLayoutManager(getContext(), 7));
//        setTaskModelList(spMonth, spYear);
//        for (int i = 0; i < taskModelList.size(); i++) {
//            taskModelList.get(i).setSelected(false);
//            taskModelList.get(i).setLeaveSelected(false);
//        }
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton rb = (RadioButton) group.findViewById(checkedId);
//                if (null != rb && checkedId > -1) {
//                    Toast.makeText(getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
//                    switch (rb.getText().toString()) {
//                        case "Single Day":
//                            _fromDateRange = false;
//                            customdate = true;
//                            mutiple_day_from_to_linear_layout.setVisibility(View.GONE);
//                            single_day_CardView.setVisibility(View.VISIBLE);
//                            break;
//                        case "Mutiple Day":
//                            _fromDateRange = true;
//                            customdate = false;
//                            _rangeStartDate = true;
//                            mutiple_day_from_to_linear_layout.setVisibility(View.VISIBLE);
//                            single_day_CardView.setVisibility(View.GONE);
//                            break;
//
//                    }
//                }
//
//            }
//        });
//
//        Collections.addAll(months, getResources().getStringArray(R.array.month));
//        month_name_tv.setText(months.get(spMonth) + ", " + spYear);
//        // setLeaveDateOnCalendar();
//        // employeeCalendarAdapter = new EmployeeCalendarAdapter(getContext(), taskModelList, this, _dateRange);
//        addLeaveCalendarAdapter = new AddLeaveCalendarAdapter(csActivity, taskModelList, this::clickOnDate, _fromDateRange);
//        calenderRv.setAdapter(addLeaveCalendarAdapter);
//        calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
//
//        previous_month_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0; i < taskModelList.size(); i++) {
//                    taskModelList.get(i).setSelected(false);
//                }
//                spMonth += (11);
//                spYear--;
//                updateMonthYear();
//            }
//        });
//
//        next_month_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0; i < taskModelList.size(); i++) {
//                    taskModelList.get(i).setSelected(false);
//                }
//                spMonth++;
//                updateMonthYear();
//            }
//        });
//
//
//        raise_request_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog();
//            }
//        });
//    }
//
//
//    @Override
//    public void taskClicked(int date) {
//
//    }
//
//    public void setTaskModelList(int month, int year) {
//        int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        int firstDayOfMonth = getFirstDayOfMonth(month, year);
//        totalDayInMonth = getTotalNoOfDayInMonth(month, year);
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
//            taskModelList.get(i).setLeaveSelected(false);
//        }
//        spYear += spMonth / 12;
//        spMonth %= 12;
//        month_name_tv.setText(months.get(spMonth) + ", " + spYear);
//        //  setLeaveDateOnCalendar();
//        //employeeCalendarAdapter = new EmployeeCalendarAdapter(getContext(), taskModelList, this, _dateRange);
//        addLeaveCalendarAdapter = new AddLeaveCalendarAdapter(csActivity, taskModelList, this::clickOnDate, _fromDateRange);
//        calenderRv.setAdapter(addLeaveCalendarAdapter);
//        calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
//    }
//
//    @Override
//    public void clickOnDate(int date, String type) {
//        Log.e("", "click On Date ...   " + date);
//
//        if (_fromDateRange || customdate) {
//            for (int i = 0; i < taskModelList.size(); i++) {
//                taskModelList.get(i).setSelected(false);
//            }
//
//            if (_fromDateRange) {
//                if (_rangeStartDate) {
//
//                    for (int i = 0; i < taskModelList.size(); i++) {
//                        taskModelList.get(i).setDayNumber(0);
//                    }
//
//                    taskModelList.get(date).setDayNumber(1);
//                    _rangeStartDate = false;
//                    startDateNo = Integer.parseInt(taskModelList.get(date).getDateSingleDigit());
//                    from_date.setText(String.valueOf(startDateNo));
//                } else {
//                    endDateNo = Integer.parseInt(taskModelList.get(date).getDateSingleDigit());
//                    to_date.setText(String.valueOf(endDateNo));
//                    if (startDateNo < endDateNo) {
//                        taskModelList.get(date).setDayNumber(3);
//                    } else {
//                        // reset
//                        for (int i = 0; i < taskModelList.size(); i++) {
//                            taskModelList.get(i).setDayNumber(0);
//                        }
//                    }
//                    _rangeStartDate = true;
//                    setMiddleDates();
//                }
//
//            } else {
//                datePostion = date;
//                taskModelList.get(date).setSelected(true);
//                selected_date.setText(String.valueOf(taskModelList.get(date).getDateSingleDigit()));
//                selected_month.setText(month_name_tv.getText().toString().substring(0,month_name_tv.getText().toString().indexOf(",")));
//            }
//            addLeaveCalendarAdapter = new AddLeaveCalendarAdapter(csActivity, taskModelList, this, _fromDateRange);
//            calenderRv.setAdapter(addLeaveCalendarAdapter);
//            calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);
//        } else {
//            Toast.makeText(csActivity, "Please select date radio button", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void setMiddleDates() {
//        for (int i = 0; i < taskModelList.size(); i++) {
//            CalendarDayModel myDayModel = taskModelList.get(i);
//            /*if (Integer.parseInt(myDayModel.getDateSingleDigit()) == startDateNo) {
//                myDayModel.setDayNumber(1);
//            }
//            if (Integer.parseInt(myDayModel.getDateSingleDigit()) == endDateNo) {
//                myDayModel.setDayNumber(3);
//            }*/
//            if (Integer.parseInt(myDayModel.getDateSingleDigit()) > startDateNo && Integer.parseInt(myDayModel.getDateSingleDigit()) < endDateNo) {
//                taskModelList.get(i).set_dateRange(true);
//                taskModelList.get(i).setDayNumber(2);
//                myDayModel.setDayNumber(2);
//            }
//
//        }
//        // startDateNo = 0;
//        // endDateNo = 0;
//    }
//
//
//
//    public void showDialog() {
//        final Dialog dialog = new Dialog(getContext());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.leave_confirm_dialog);
//
//        ImageView dialogButton = dialog.findViewById(R.id.cancelImg);
//        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
//        Button confirmBtn = dialog.findViewById(R.id.leave_dialog_confirm_btn);
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton rb =  group.findViewById(checkedId);
//                if (null != rb && checkedId > -1) {
//                    Toast.makeText(getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
//                    leaveReason = rb.getText().toString();
//                }else {
//                    Toast.makeText(getContext(), "Please select leave reason", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (leaveReason == null){
//                    Toast.makeText(getContext(), "Please select leave reason", Toast.LENGTH_SHORT).show();
//                }else {
//                    int firstDayOfMonth = getFirstDayOfMonth(spMonth, spYear);
//                    switch (firstDayOfMonth) {
//                        case 1:
//                            if (_fromDateRange) {
//                                datePostion = startDateNo + 5;
//                                for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
//                                    int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
//                                    Log.e("save btn.. ", "from list date .....    " + date);
//                                    saveleave_In_DB(date, leaveReason);
//                                }
//                            } else {
//                                // datePostion = datePostion-5;
//                                saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), leaveReason);
//                            }
//                            break;
//                        case 2:
//                            if (_fromDateRange) {
//                                datePostion = startDateNo - 1;
//                                for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
//                                    int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
//                                    Log.e("save btn.. ", "from list date .....    " + date);
//                                    saveleave_In_DB(date, leaveReason);
//
//                                }
//                            } else {
//                                //datePostion = datePostion+1;
//                                saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), leaveReason);
//                            }
//                            break;
//                        case 3:
//                            if (_fromDateRange) {
//                                datePostion = startDateNo;
//                                for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
//                                    int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
//                                    Log.e("save btn.. ", "from list date .....    " + date);
//                                    saveleave_In_DB(date, leaveReason);
//
//                                }
//                            } else {
//                                saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), leaveReason);
//                            }
//                            break;
//                        case 4:
//                            if (_fromDateRange) {
//                                datePostion = startDateNo + 1;
//                                for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
//                                    int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
//                                    Log.e("save btn.. ", "from list date .....    " + date);
//                                    saveleave_In_DB(date, leaveReason);
//
//                                }
//                            } else {
//                                // datePostion = datePostion-1;
//                                saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), leaveReason);
//                            }
//                            break;
//                        case 5:
//                            if (_fromDateRange) {
//                                datePostion = startDateNo + 2;
//                                for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
//                                    int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
//                                    Log.e("save btn.. ", "from list date .....    " + date);
//                                    saveleave_In_DB(date, leaveReason);
//
//                                }
//                            } else {
//                                //datePostion = datePostion-2;
//                                saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), leaveReason);
//                            }
//                            break;
//                        case 6:
//                            if (_fromDateRange) {
//                                datePostion = startDateNo + 3;
//                                for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
//                                    int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
//                                    Log.e("save btn.. ", "from list date .....    " + date);
//                                    saveleave_In_DB(date, leaveReason);
//
//                                }
//                            } else {
//                                // datePostion = datePostion-3;
//                                saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), leaveReason);
//                            }
//                            break;
//                        case 7:
//                            if (_fromDateRange) {
//                                datePostion = startDateNo + 4;
//                                for (int i = 0; i < (endDateNo - startDateNo) + 1; i++) {
//                                    int date = Integer.parseInt(taskModelList.get(datePostion++).getDateSingleDigit());
//                                    Log.e("save btn.. ", "from list date .....    " + date);
//                                    saveleave_In_DB(date, leaveReason);
//
//                                }
//                            } else {
//                                //datePostion = datePostion-4;
//                                saveleave_In_DB(Integer.parseInt(taskModelList.get(datePostion).getDateSingleDigit()), leaveReason);
//                            }
//                            break;
//                    }
//                    dialog.dismiss();
//                   // saveleave_In_DB()
//                }
//            }
//        });
//
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//  private void saveleave_In_DB(int date, String leaveDescription) {
//        String insertLeaveReasonDate = spYear + "-" + String.format("%02d", spMonth + 1) + "-" + String.format("%02d", date) + " " + "00:00:00";
//        Log.e("", "inser leave at date....  " + insertLeaveReasonDate);
//        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkExist(regNoStr, insertLeaveReasonDate) != null) {
//            LeaveDetailTransaction leaveEmployeeDetails = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
//                    checkExist(regNoStr, insertLeaveReasonDate);
//           // leaveEmployeeDetails.setLeaveReason(leaveOptionSelect);
//        } else {
//            LeaveDetailTransaction leaveEmployeeDetail = new LeaveDetailTransaction();
//            leaveEmployeeDetail.setLeaveDate(insertLeaveReasonDate);
//            leaveEmployeeDetail.setIsOff("1");
//            leaveEmployeeDetail.setFlag("1");
//            leaveEmployeeDetail.setRegNo(regNoStr);
//            leaveEmployeeDetail.setID(String.valueOf(UUID.randomUUID()));
//            leaveEmployeeDetail.setLeaveReasonId(Integer.parseInt(leaveReason));
//            //leaveEmployeeDetail.setLeaveDescriptions(leaveDescription);
//            leaveEmployeeDetail.setJSON(createLeaveWeeklyJson(leaveEmployeeDetail));
//            CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().insert(leaveEmployeeDetail);
//        }
//      //  employeeCalendarAdapter.notifyDataSetChanged();
//    }
//
//    public static String createLeaveWeeklyJson(LeaveDetailTransaction leaveEmployeeDetails) {
//        String jsonParameter = null;
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("ID", leaveEmployeeDetails.getID());
//            jsonObject.put("REGNO", leaveEmployeeDetails.getRegNo());
//            jsonObject.put("LEAVE_DATE", leaveEmployeeDetails.getLeaveDate());
//            jsonObject.put("LEAVE_TYPE_ID", leaveEmployeeDetails.getLeaveReasonId());
//            jsonObject.put("IS_OFF", "1");
//            jsonObject.put("DATE_MODIFIED", DateUtils.getCurrentDate_TIME_format());
//            jsonObject.put("MODIFIED_BY", CSShearedPrefence.getUser_ID());
//            jsonObject.put("LEAVE_DESCRIPTION", leaveEmployeeDetails.getLeaveDescriptions());
//            jsonParameter = jsonObject.toString();
//        } catch (Exception e) {
//           // Crashlytics.logException(e);
//            e.printStackTrace();
//        }
//        return jsonParameter;
//    }
//
//}
