package com.sisindia.mysis.fragment;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import butterknife.BindView;

public class DutyDetailFragment extends CSHeaderFragmentFragment {

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


    @Override
    public int layoutResource() {
        return R.layout.activity_calendar_day_wise;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();
        getDutyDetail();
    }

    public void getDutyDetail() {
        if (getArguments() != null) {
            String date = getArguments().getString("DATE");
            Log.e("date", "Date" + date);
        }
        mapTableRosterAndShift = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().
                getShiftDetailOnRosterBase(DateUtils.getCurrentDate_format(), "GAR000198");


        if (mapTableRosterAndShift == null) {
            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.no_shift_for_today_in_Roster), this, 111, false);
        } else {
            time_txt.setText(mapTableRosterAndShift.getSTART_TIME().split(":")[0] + ":" + mapTableRosterAndShift.getSTART_TIME().split(":")[1]);
            txt_shift.setText(mapTableRosterAndShift.getSHIFT_NAME());
          //  post_rank_txt.setText(CFUtil.returnHourPeriod(mapTableRosterAndShift.getSTART_TIME()));
            post_rank_txt.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().gertServiceDetail(mapTableRosterAndShift.getDUTY_RANK()).getSERVICENAME()
                    + "( " + CSApplicationHelper.application().getDatabaseInstance().serviceDAO().gertServiceDetail(mapTableRosterAndShift.getDUTY_RANK()).getSYMBOL() + " )");
            postNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().postName(mapTableRosterAndShift.getDUTY_POST_ID()));

        }


    }
}
