package com.sisindia.mysis.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import butterknife.BindView;

public class DutyRosterFragment extends CSHeaderFragmentFragment {

    @BindView(R.id.postNameTV)
    TextView postNameTV;
//    @BindView(R.id.unit_address_txt)
//    TextView unit_address_txt;
    @BindView(R.id.post_txt)
    TextView post_txt;
    @BindView(R.id.post_rank_txt)
    TextView post_rank_txt;
    @BindView(R.id.txt_shift)
    TextView txt_shift;
    @BindView(R.id.time_txt)
    TextView time_txt;
    @BindView(R.id.month_txt)
    TextView month_txt;
    @BindView(R.id.icon_right_arrow)
    ImageView icon_right_arrow;
    @BindView(R.id.icon_left_arrow)
    ImageView icon_left_arrow;


    private MapTableRosterAndShift mapTableRosterAndShift;
    ArrayList<String> months = new ArrayList<>();
    int spMonth = Calendar.getInstance().get(Calendar.MONTH);
    int spYear = Calendar.getInstance().get(Calendar.YEAR);
    String date;

    @Override
    public int layoutResource() {
        return R.layout.activity_calendar_day_wise_planned_duty;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();
        if (getArguments() != null) {
            date = getArguments().getString("DATE");
        }
        getDutyDetail();

        Collections.addAll(months, getResources().getStringArray(R.array.month));
        //month_txt.setText(months.get(spMonth) + ", " + spYear);
        month_txt.setText(date);
        icon_left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month_txt.setText(DateUtils.getInstance().getPreviousDateCalender(month_txt.getText().toString()));
                date = month_txt.getText().toString();
                getDutyDetail();
            }
        });

        icon_right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date =DateUtils.getInstance().getNextDateCalender(month_txt.getText().toString());
                Log.e("date","date... "+date);
                month_txt.setText(date);
                getDutyDetail();
            }
        });
    }

    public void getDutyDetail() {

        Log.e("date", "Date    " + date);
        mapTableRosterAndShift = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().
                getShiftDetailOnRosterBase(date, "GAR000198");

        if (mapTableRosterAndShift == null) {
            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.no_shift_for_today_in_Roster), this, 111, false);
        } else {
            time_txt.setText(mapTableRosterAndShift.getSTART_TIME().split(":")[0] + ":" + mapTableRosterAndShift.getSTART_TIME().split(":")[1]);
            txt_shift.setText(mapTableRosterAndShift.getSHIFT_NAME());
            //  post_rank_txt.setText(CFUtil.returnHourPeriod(mapTableRosterAndShift.getSTART_TIME()));
            post_rank_txt.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().gertServiceDetail(mapTableRosterAndShift.getDUTY_RANK()).getSERVICENAME()
                    + "( " + CSApplicationHelper.application().getDatabaseInstance().serviceDAO().gertServiceDetail(mapTableRosterAndShift.getDUTY_RANK()).getSYMBOL() + " )");
            post_txt.setText(CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().postName(mapTableRosterAndShift.getDUTY_POST_ID()));

        }


    }


    private void updateMonthYear() {
        spYear += spMonth / 12;
        spMonth %= 12;
        month_txt.setText(months.get(spMonth) + ", " + spYear);
//        date = spYear + "-" + String.format("%02d", spMonth + 1) + "-" + String.format("%02d", month_txt.getText().toString().split(" ")[1]);
    }

}
