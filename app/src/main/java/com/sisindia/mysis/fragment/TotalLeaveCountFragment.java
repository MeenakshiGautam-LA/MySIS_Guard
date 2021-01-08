package com.sisindia.mysis.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Adapter.TotalLeaveCountAdapter;
import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSAppUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class TotalLeaveCountFragment extends CSHeaderFragmentFragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.icon_left_arrow)
    ImageView icon_left_arrow;
    @BindView(R.id.next_month_arrow)
    ImageView next_month_arrow;

    @BindView(R.id.month_txt)
    TextView month_txt;
    @BindView(R.id.totalPresentDay)
    TextView totalPresentDay;
    @BindView(R.id.totalleaveDay)
    TextView totalLeaveDay;
    @BindView(R.id.all)
    TextView all;


    List<CalendarDayModel> leaveEmployeeDetailModel;
    TotalLeaveCountAdapter totalLeaveCountAdapter;
    ArrayList<String> months = new ArrayList<>();
    int spMonth = Calendar.getInstance().get(Calendar.MONTH);
    int spYear = Calendar.getInstance().get(Calendar.YEAR);


    @Override
    public int layoutResource() {
        return R.layout.activity_calendar_leave;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();

        if (getArguments()!=null){
            totalLeaveDay.setText(getArguments().getString("totalLeaveDay"));
            totalPresentDay.setText(getArguments().getString("totalPresentDay"));
        }


        setLeaveData();

        Collections.addAll(months, getResources().getStringArray(R.array.month));
        month_txt.setText(months.get(spMonth) + ", " + spYear);

        icon_left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spMonth += (11);
                spYear--;
                updateMonthYear();
            }
        });

        next_month_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spMonth++;
                updateMonthYear();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle cbundle = new Bundle();
                cbundle.putString("REGNO", "GAR000198");
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_CalendarLeavePresent_fragment_open, cbundle);
            }
        });
    }

    public void setLeaveData() {

        leaveEmployeeDetailModel = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().getLeaveHistory("GAR000198");
        for (CalendarDayModel leaveEmployeeDetails1 : leaveEmployeeDetailModel) {
            String leaveDatefromDatabase = leaveEmployeeDetails1.getLEAVE_DATE();
            String leaveReason = leaveEmployeeDetails1.getLEAVE_TYPE_ID();
            Log.d("", "leaveDatefromDatabase..... " + leaveReason);
            String leaveDate = leaveDatefromDatabase.substring(leaveDatefromDatabase.lastIndexOf("-") + 1, leaveDatefromDatabase.indexOf(" "));
            String leaveMonth = leaveDatefromDatabase.substring(leaveDatefromDatabase.indexOf("-") + 1, leaveDatefromDatabase.lastIndexOf("-"));
            Log.d("", "leaveDate..... " + leaveDate);
            Log.d("", "leaveMonth..... " + leaveMonth);
            // Log.d("", "months size..... " + months.size());
           /* leaveDateList.add(Integer.parseInt(leaveDate));
            leaveMonthList.add(Integer.parseInt(leaveMonth));
            leaveReasonList.add(leaveReason);
            _isApproved.add(leaveEmployeeDetails1.getIS_OFF());
            Log.d("", "months from database..... " + months.get(Integer.parseInt(leaveMonth)));*/
        }
        totalLeaveCountAdapter = new TotalLeaveCountAdapter(getContext(), leaveEmployeeDetailModel);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(totalLeaveCountAdapter);
    }

    private void updateMonthYear() {
        spYear += spMonth / 12;
        spMonth %= 12;
        month_txt.setText(months.get(spMonth) + ", " + spYear);
    }
}
