package com.sisindia.mysis.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Adapter.LeaveLandingAdapter;
import com.sisindia.mysis.Adapter.ViewLeaveAdapter;
import com.sisindia.mysis.Adapter.View_Leave_typeList_Adapter;
import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.Model.LeaveLandingModel;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.LeaveMaster;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.listener.RecyclerItemClickListener;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class LeaveStatusFragment extends CSHeaderFragmentFragment implements ViewLeaveAdapter.ItemClciked {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.apply_leave_btn)
    Button apply_leave_btn;
    @BindView(R.id.leave_history_txt)
    TextView leave_history_txt;
    @BindView(R.id.backPressIV)
    AppCompatImageView backPressIV;

    LeaveLandingAdapter leaveLandingAdapter;
    Context context;
    List<LeaveLandingModel> leaveLandingModelArrayList;
    List<CalendarDayModel> leaveEmployeeDetailModel;
    LeaveLandingModel leaveLandingModel;
    int spMonth = Calendar.getInstance().get(Calendar.MONTH);
    int spYear = Calendar.getInstance().get(Calendar.YEAR);

    List<Integer> leaveDateList = new ArrayList<>();
    List<Integer> leaveMonthList = new ArrayList<>();
    List<String> leaveReasonList = new ArrayList<>();
    List<String> _isApproved = new ArrayList<>();
    List<Integer> rangeDate = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();
    private List<LeaveMaster> leaveMasterList;
    private ViewLeaveAdapter viewLeaveAdapter;
    private int leaveClickPosition = 0;

    @Override
    public int layoutResource() {
        return R.layout.activity_leave_landing;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        Drawable mIcon = ContextCompat.getDrawable(getActivity(), R.drawable.white_arrow);
        backPressIV.setRotation(180.0f);
        if (CSShearedPrefence.isNightModeEnabled()) {
            backPressIV.setImageDrawable(mIcon);


        } else {
            mIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), PorterDuff.Mode.MULTIPLY);
            backPressIV.setImageDrawable(mIcon);
        }
        leaveLandingModelArrayList = new ArrayList<>();
//        setLeaveStatus();
        backPressIV.setOnClickListener(view -> csActivity.onBackPressed());
        apply_leave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle cbundle = new Bundle();
                //cbundle.putString("REGNO", mapTableRosterAndShift.getREGNO());
                cbundle.putString("DUTY_STATUS", "DUTY_OUT");
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_calendar_employee_take_leave, cbundle);
            }
        });

        leave_history_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle cbundle = new Bundle();
                //cbundle.putString("REGNO", mapTableRosterAndShift.getREGNO());
                cbundle.putString("DUTY_STATUS", "DUTY_OUT");
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_leave_history_fragment_open, cbundle);
            }
        });
        getLeaveDatFromTable();
    }

    private void getLeaveDatFromTable() {
        CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().getLeaveDetails().observe(this,
                leaveMaster -> {
                    if (leaveMaster.size() > 0) {
                        leaveMasterList = leaveMaster;
                        for (LeaveMaster leaveMaster1 : leaveMasterList) {
                            leaveMaster1.setCountLeaveDays(CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
                                    countLeaveDays(leaveMaster1.getID()));
                        }
                        viewLeaveAdapter = new ViewLeaveAdapter(this, leaveMasterList);
                        recyclerView.setAdapter(viewLeaveAdapter);

                        viewLeaveAdapter.setItemListener(this);
                    }
                });


    }


/*
    public void setLeaveStatus() {
        leaveDateList.clear();
        leaveEmployeeDetailModel = CSApplicationHelper.application().getDatabaseInstance().leaveEmployeeDetail_dao().
                getLeaveHistory(CSShearedPrefence.getUser());
        Collections.addAll(months, getResources().getStringArray(R.array.month));
        for (CalendarDayModel leaveEmployeeDetails1 : leaveEmployeeDetailModel) {
            String leaveDatefromDatabase = leaveEmployeeDetails1.getLEAVE_DATE();
            String leaveReason = leaveEmployeeDetails1.getLEAVE_TYPE_ID();
            Log.d("", "leaveDatefromDatabase..... " + leaveReason);
            String leaveDate = leaveDatefromDatabase.substring(leaveDatefromDatabase.lastIndexOf("-") + 1, leaveDatefromDatabase.indexOf(" "));
            String leaveMonth = leaveDatefromDatabase.substring(leaveDatefromDatabase.indexOf("-") + 1, leaveDatefromDatabase.lastIndexOf("-"));
            Log.d("", "leaveDate..... " + leaveDate);
            Log.d("", "leaveMonth..... " + leaveMonth);
            Log.d("", "months size..... " + months.size());
            leaveDateList.add(Integer.parseInt(leaveDate));
            leaveMonthList.add(Integer.parseInt(leaveMonth));
            leaveReasonList.add(leaveReason);
            _isApproved.add(leaveEmployeeDetails1.getIS_OFF());
            Log.d("", "months from database..... " + months.get(Integer.parseInt(leaveMonth)));
        }

        if (leaveDateList.size() > 0) {
            int i = 0, j;
            do {
                j = i + 1;
                while (j < leaveDateList.size()) {
                    if (leaveDateList.get(j) - leaveDateList.get(i) != j - i)
                        break;
                    rangeDate.add(leaveDateList.get(j));
                    j++;
                }
                if (i == j - 1) {
                    Log.e("log>>>", "date range false " + leaveDateList.get(i));
                    //leaveLandingModel = new LeaveLandingModel(leaveReasonList.get(i), "1", String.valueOf(leaveDateList.get(i)), leaveStatus);
                    leaveLandingModel = new LeaveLandingModel();
                    leaveLandingModel.setLeave_date(String.valueOf(leaveDateList.get(i)) + " " + months.get(leaveMonthList.get(i)-1));
                    leaveLandingModel.setLeave_days("1 day");
                    leaveLandingModel.setLeave_reason(leaveReasonList.get(i));
                    leaveLandingModel.setLeave_status(_isApproved.get(i));
                    leaveLandingModel.set_isClicked(false);
                    leaveLandingModelArrayList.add(leaveLandingModel);
                } else {
                    //Log.d("", "date range true " + date.get(i) + "-" + date.get(j - 1));
                    Log.d("", "date range true " + leaveDateList.get(i) + "-" + leaveDateList.get(j - 1) + "middle date " + rangeDate);
                    int toatlLeaveDay = (leaveDateList.get(j - 1) - leaveDateList.get(i)) + 1;
                    // leaveLandingModel = new LeaveLandingModel(leaveReasonList.get(i), String.valueOf(toatlLeaveDay), String.valueOf(leaveDateList.get(i)), leaveStatus);
                    leaveLandingModel = new LeaveLandingModel();
                    leaveLandingModel.setLeave_date(String.valueOf(leaveDateList.get(i)) + " " + months.get(leaveMonthList.get(i)-1) + " - " + String.valueOf(leaveDateList.get(j - 1)) + " " + months.get(leaveMonthList.get(i)-1));
                    leaveLandingModel.setLeave_days(String.valueOf(toatlLeaveDay) + " days");
                    leaveLandingModel.setLeave_reason(leaveReasonList.get(i));
                    leaveLandingModel.setLeave_status(_isApproved.get(i));
                    leaveLandingModel.set_isClicked(false);
//                    leaveLandingModel.setLeaveId();
                    leaveLandingModelArrayList.add(leaveLandingModel);
                }
                i = j;
            } while (i < leaveDateList.size());
        }

        leaveLandingAdapter = new LeaveLandingAdapter(context, leaveLandingModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        leaveLandingAdapter.setItemListener(this);
        recyclerView.setAdapter(leaveLandingAdapter);
    }
*/

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    @Override
    public void onItemClicked(View view, int postion) {
        if (view.getId() == R.id.cancel_request) {
            leaveClickPosition = postion;
            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.cancelled_leave_confirm), this, 4321, true);
//            CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
//                    deleteTransactionBaseOnMaster_ID(leaveMaster.getID());

        } else {
            Log.e("", "on item click listener in leave status postion... " + postion);
            for (int i = 0; i < leaveMasterList.size(); i++) {
                ((LeaveMaster) leaveMasterList.get(i)).setIschecked(false);
            }

        /*for (int i = 0; i < leaveLandingModelArrayList.size(); i++) {f
            ((LeaveLandingModel) leaveLandingModelArrayList.get(i)).set_isClicked(false);
        }
        leaveLandingModel = (LeaveLandingModel) leaveLandingModelArrayList.get(postion);
        leaveLandingModel.set_isClicked(true);
        leaveLandingAdapter.notifyDataSetChanged();*/
            leaveMasterList.get(postion).setIschecked(true);
            viewLeaveAdapter.notifyDataSetChanged();
        }

    }

    public void removeAt(int position) {
        leaveMasterList.remove(position);
        viewLeaveAdapter.notifyItemRemoved(position);
        viewLeaveAdapter.notifyItemRangeChanged(position, leaveMasterList.size());
    }

    private String updateJson(String json) {
        String returnJson = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("LEAVE_STATUS")) {
                jsonObject.put("LEAVE_STATUS", "2");
            }
            returnJson = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    @Override
    public void dialog_onClick(int requestCode) {
        if (requestCode == 4321) { // confirm to cancel leave
            CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().
                    updateRecordAsCancel(leaveMasterList.get(leaveClickPosition).getID(), updateJson(leaveMasterList.get(leaveClickPosition).getJson()));
            removeAt(leaveClickPosition);
        }
    }
/*
    public void showDialog() {
        try {
            Dialog dialog = new Dialog(csActivity, android.R.style.Theme_Light);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.setContentView(R.layout.leave_cancel_dialog);
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

           */
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
            });*//*


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
                       */
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
                        }*//*

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
*/

}
