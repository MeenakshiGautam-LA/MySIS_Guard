//package com.example.digitalguard;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.AppCompatImageView;
//
//import com.bumptech.glide.Glide;
//import com.example.digitalguard.Model.OtherUnitEmployeeDetailModel;
//import com.example.digitalguard.activity.base.BaseActivity;
//import com.example.digitalguard.application.CSApplicationHelper;
//import com.example.digitalguard.entity.DailyAttendanceDetail;
//import com.example.digitalguard.entity.UserDetailModel;
//import com.example.digitalguard.utils.CSStringUtil;
//import com.example.digitalguard.utils.DateUtils;
//
//import java.text.SimpleDateFormat;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class MarkDuty_Confirmation_ViewScreen extends BaseActivity {
//    TextView mark_another_duty_txt;
//    @BindView(R.id.cancelBtn)
//    AppCompatImageView cancelBtn;
//    @BindView(R.id.userNameTV)
//    TextView userNameTV;
//    @BindView(R.id.dutyRankNameTV)
//    TextView dutyRankNameTV;
//    @BindView(R.id.attendanceMarkedDateTV)
//    TextView attendanceMarkedDateTV;
//    @BindView(R.id.shiftNameTV)
//    TextView shiftNameTV;
//    @BindView(R.id.shiftTimeTV)
//    TextView shiftTimeTV;
//    @BindView(R.id.deployAddressTV)
//    TextView deployAddressTV;
//    @BindView(R.id.postNameTV)
//    TextView postNameTV;
//    @BindView(R.id.dutyStatusTV)
//    TextView dutyStatusTV;
//    @BindView(R.id.dutyInTimeTV)
//    TextView dutyInTimeTV;
//    @BindView(R.id.descriptionTextTV)
//    TextView descriptionTextTV;
//    @BindView(R.id.userImageCV)
//    CircleImageView userImageCV;
//    private DailyAttendanceDetail dailyAttendanceDetail;
//    private OtherUnitEmployeeDetailModel otherUnitEmployeeDetailModel;
//    private UserDetailModel userDetailModelForOther;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mark_attendance_duty_in_success);
//        ButterKnife.bind(this);
//        mark_another_duty_txt = findViewById(R.id.mark_another_duty_txt);
//        cancelBtn = findViewById(R.id.cancelBtn);
//
//        mark_another_duty_txt.setOnClickListener(this);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            if (bundle.getString("SCREEN_TAG").equalsIgnoreCase("OTHER_DUTY_PUNCH_SCREEN")) {
//                String dutyStatus = bundle.getString("DUTY_STATUS");
//                otherUnitEmployeeDetailModel = (OtherUnitEmployeeDetailModel) bundle.getSerializable("OTHER_UNIT_DUTY_EMPLOYEE");
//                dailyAttendanceDetail = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
//                        getAttendanceDetail(otherUnitEmployeeDetailModel.getREGNO(), dutyStatus);
//                setOtherUnitDutyEmployeeDetail(otherUnitEmployeeDetailModel);
//                if (dailyAttendanceDetail.getDuty_status().equalsIgnoreCase("DUTY_OUT")) {
//                    descriptionTextTV.setText(CSStringUtil.getString(R.string.duty_out_successfully));
//                } else {
//                    descriptionTextTV.setText(CSStringUtil.getString(R.string.duty_In_successfully));
//
//                }
//                Log.e("OTHER_UNIT_DUTY", "" + otherUnitEmployeeDetailModel.getREGNO() + dutyStatus);
//
//            } else {
//
//                String regNo = getIntent().getExtras().getString("REGNO");
//                String dutyStatus = getIntent().getExtras().getString("DUTY_STATUS");
//                Log.e("SELF_EMPLOYEE_LOGIN", "" + regNo + dutyStatus);
//                dailyAttendanceDetail = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
//                        getAttendanceDetail(regNo, dutyStatus);
//                setUserDetail();
//                if (dailyAttendanceDetail.getDuty_status().equalsIgnoreCase("DUTY_OUT")) {
//                    descriptionTextTV.setText(CSStringUtil.getString(R.string.duty_out_successfully));
//                } else {
//                    descriptionTextTV.setText(CSStringUtil.getString(R.string.duty_In_successfully));
//
//                }
//            }
//        }
//
//
//    }
//
//    @OnClick({R.id.cancelBtn})
//    public void onClickMethod(View v) {
//
//        switch (v.getId()) {
//            case R.id.cancelBtn:
//                onBackPressed();
//                break;
//        }
//        //  Intent in = new Intent(MarkDuty_Confirmation_ViewScreen.this, MarkAttendanceDutyOutActivity.class);
//    }
//
//    @Override
//    public int getScreenName() {
//        return 0;
//    }
//
//    private void setUserDetail() {
//        UserDetailModel userDetailModel = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getDetails(dailyAttendanceDetail.getREGNO());
//        if (userDetailModel != null) {
//            userNameTV.setText(userDetailModel.getName());
//            dutyRankNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().gertServiceDetail(dailyAttendanceDetail.getDUTYRANK()).getSERVICENAME()
//                    + "( " + CSApplicationHelper.application().getDatabaseInstance().serviceDAO().gertServiceDetail(dailyAttendanceDetail.getDUTYRANK()).getSYMBOL() + " )");
//            postNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().
//                    postName(dailyAttendanceDetail.getDUTYPOSTID()));
//            shiftNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().
//                    getShiftnameBy_ID(dailyAttendanceDetail.getSHIFTID()));
//
//            SimpleDateFormat sdf1 = DateUtils.getInstance().getDate_And_MonthWordFormat();
//            String shownDay = sdf1.format(DateUtils.getInstance().dateFromShiftDate(DateUtils.getCurrentDate_format()));
//
//            String dayOfWeek = DateUtils.getInstance().getWeek_WordFormat().format(DateUtils.getInstance().dateFromShiftDate(DateUtils.getCurrentDate_format()));
//
//            Log.e("shownDay", "" + dayOfWeek);
//            Log.e("shownDay", "" + shownDay);
//            attendanceMarkedDateTV.setText(dayOfWeek + " " + shownDay);
//            shiftTimeTV.setText(CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().
//                    getShiftDetail(dailyAttendanceDetail.getSHIFTID()).getStartTime());
//            dutyStatusTV.setText(dailyAttendanceDetail.getDuty_status().toLowerCase());
//            dutyInTimeTV.setText(dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1]);
//            Glide.with(this).load(userDetailModel.getPicture()).into(userImageCV);
//
//        }
//    }
//
//    private void setOtherUnitDutyEmployeeDetail(OtherUnitEmployeeDetailModel otherUnitEmployeeDetailModel) {
//        userDetailModelForOther=CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
//                getDetails(otherUnitEmployeeDetailModel.getREGNO());
//        userNameTV.setText(otherUnitEmployeeDetailModel.getEmpName());
//        dutyRankNameTV.setText(otherUnitEmployeeDetailModel.getOther_DutyRankName()
//                + "( " + otherUnitEmployeeDetailModel.getOtherDuty_RankSymbol() + " )");
//        postNameTV.setText(otherUnitEmployeeDetailModel.getOther_Duty_post_Name());
//        shiftNameTV.setText(otherUnitEmployeeDetailModel.getSHIFT_NAME());
//
//        SimpleDateFormat sdf1 = DateUtils.getInstance().getDate_And_MonthWordFormat();
//        String shownDay = sdf1.format(DateUtils.getInstance().dateFromShiftDate(DateUtils.getCurrentDate_format()));
//
//        String dayOfWeek = DateUtils.getInstance().getWeek_WordFormat().format(DateUtils.getInstance().dateFromShiftDate(DateUtils.getCurrentDate_format()));
//
//        Log.e("shownDay", "" + dayOfWeek);
//        Log.e("shownDay", "" + shownDay);
//        attendanceMarkedDateTV.setText(dayOfWeek + " " + shownDay);
//        shiftTimeTV.setText(otherUnitEmployeeDetailModel.getSTART_TIME());
//        dutyStatusTV.setText(dailyAttendanceDetail.getDuty_status().toLowerCase());
//        dutyInTimeTV.setText(dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1]);
//        Glide.with(this).load(userDetailModelForOther.getPicture()).into(userImageCV);
//        CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().delete(userDetailModelForOther);
//
//    }
//}
//
