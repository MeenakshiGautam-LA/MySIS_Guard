package com.sisindia.mysis.fragment;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Adapter.LeaveHistoryAdapter;
import com.sisindia.mysis.Adapter.LeaveLandingAdapter;
import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.Model.LeaveLandingModel;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class LeaveHistoryFragment extends CSHeaderFragmentFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    List<Integer> leaveDateList = new ArrayList<>();
    List<Integer> leaveMonthList = new ArrayList<>();
    List<String> leaveReasonList = new ArrayList<>();
    List<String> _isApproved = new ArrayList<>();
    List<Integer> rangeDate = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();

    List<CalendarDayModel> leaveEmployeeDetailModel;
    List<LeaveLandingModel> leaveLandingModelArrayList;
    LeaveLandingModel leaveLandingModel;
    LeaveLandingAdapter leaveLandingAdapter;
    LeaveHistoryAdapter leaveHistoryAdapter;
    @BindView(R.id.backPressIV)
    AppCompatImageView backPressIV;

    @Override
    public int layoutResource() {
        return R.layout.activity_leave_history;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        leaveLandingModelArrayList = new ArrayList<>();
        setLeaveHistory();
        backPressIV.setOnClickListener(view -> csActivity.onBackPressed());
    }
    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    public void setLeaveHistory() {
        leaveDateList.clear();
        leaveEmployeeDetailModel = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().getLeaveHistory("GAR000198");
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
                    leaveLandingModelArrayList.add(leaveLandingModel);
                }
                i = j;
            } while (i < leaveDateList.size());
        }

        leaveHistoryAdapter = new LeaveHistoryAdapter(getContext(), leaveLandingModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //leaveLandingAdapter.setItemListener(this);
        recyclerview.setAdapter(leaveHistoryAdapter);
    }
}
