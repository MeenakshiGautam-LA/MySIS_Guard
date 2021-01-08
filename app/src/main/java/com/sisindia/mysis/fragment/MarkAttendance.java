package com.sisindia.mysis.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.sisindia.mysis.ApiCalling_Class;
import com.sisindia.mysis.GetWorkManagers.GetAttendanceWork;
import com.sisindia.mysis.GetWorkManagers.GetRosterWorker;
import com.sisindia.mysis.Model.Attendance_for_calender_model;
import com.sisindia.mysis.Model.Leave_Detail_Model;
import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.R;
import com.sisindia.mysis.Services.TrackingService;
import com.sisindia.mysis.activity.MainActivity_Guard;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.Flash_Message_Model;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSDrawable;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.CSUtil;
import com.sisindia.mysis.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MarkAttendance extends CSHeaderFragmentFragment {

    @BindView(R.id.todayShiftDetailCV)
    CardView todayShiftDetailCV;
    @BindView(R.id.todayShiftNameTV)
    TextView todayShiftNameTV;
    @BindView(R.id.unitNameTV)
    TextView unitNameTV;
    @BindView(R.id.postNameTV)
    TextView postNameTV;
    @BindView(R.id.nextDayPostNameTV)
    TextView nextDayPostNameTV;
    @BindView(R.id.todayShiftStartTimeTV)
    TextView todayShiftStartTimeTV;
    @BindView(R.id.nextDayDateTV)
    TextView nextDayDateTV;
    @BindView(R.id.nextDayShiftStartTimeTV)
    TextView nextDayShiftStartTimeTV;
    @BindView(R.id.nextDayShiftNameTV)
    TextView nextDayShiftNameTV;
    @BindView(R.id.nextDayUnitNameTV)
    TextView nextDayUnitNameTV;
    @BindView(R.id.dutyOutBTN)
    LinearLayout dutyOutBTN;
    @BindView(R.id.dutyInBTN)
    LinearLayout dutyInBTN;
    @BindView(R.id.dutyInTV)
    TextView dutyInTV;
    @BindView(R.id.dutyOutTV)
    TextView dutyOutTV;
    @BindView(R.id.markOtherDutyRL)
    RelativeLayout markOtherDutyRL;
    @BindView(R.id.todayTimePeroidTV)
    TextView todayTimePeroidTV;
    @BindView(R.id.todayDateTV)
    TextView todayDateTV;
    @BindView(R.id.userImageCV)
    CircleImageView userImageCV;
    @BindView(R.id.nextDayTimePeroidTV)
    TextView nextDayTimePeriodTV;
    @BindView(R.id.other_duty_icon_right_arrow)
    AppCompatImageView otherDutyActionIV;
    private MapTableRosterAndShift mapTableRosterAndShift;
    private int optionSelectionType = 0;
    /* @BindView(R.id.userImageCV)
     CircleImageView userImageCV;*/
    @BindView(R.id.noTodayRosterTV)
    TextView noTodayRosterTV;
    @BindView(R.id.noNextDayRosterTV)
    TextView noNextDayRosterTV;
    @BindView(R.id.todayRosterLY)
    RelativeLayout todayRosterRL;
    @BindView(R.id.nextDayRosterRL)
    RelativeLayout nextDayRosterRL;
    @BindView(R.id.duty_INTime_TV)
    TextView duty_INTime_TV;
    @BindView(R.id.duty_OUTTime_TV)
    TextView duty_OUTTime_TV;
    @BindView(R.id.attendance_Not_Mark_RL)
    RelativeLayout attendance_Not_Mark_RL;
    @BindView(R.id.appUpdateLY)
    LinearLayout appUpdateLY;
    @BindView(R.id.warning_TV)
    TextView warning_TV;
    @BindView(R.id.complete_Duty_TV)
   /* TextView complete_Duty_TV;
    @BindView(R.id.current_dutyHeaderTV)*/
            TextView current_dutyHeaderTV;
    //  @BindView(R.id.notificationFrameLayout)
    //FrameLayout notificationFrameLayout;
    boolean stopHandler = false;
    @BindView(R.id.profile_logo)
    CircleImageView userProfileIV;
    @BindView(R.id.rating_bar)
    RatingBar rating_bar;
    @BindView(R.id.userNameTV)
    TextView userNameTV;
    @BindView(R.id.postRankNameTV)
    TextView dutyRankNameTV;
    @BindView(R.id.regNoTV)
    TextView regNoTV;
    @BindView(R.id.timePeriodYearTV)
    TextView timePeriodYearTV;
    @BindView(R.id.next_month_iv)
    ImageView next_month_iv;
    @BindView(R.id.previous_month_iv)
    ImageView previous_month_iv;
    @BindView(R.id.presentCountTV)
    TextView presentCountTV;
    @BindView(R.id.leave_Count_TV)
    TextView leave_Count_TV;
    @BindView(R.id.workingHourTV)
    TextView workingHourTV;
    @BindView(R.id.month_name_tv)
    TextView month_name_tv;
    @BindView(R.id.warning_IV)
    ImageView warning_IV;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.flashDetailLY)
    LinearLayout flashDetailLY;
    private int totalDayInMonth, totalLeaveCount = 0, totalPresentDayCount = 0, extraDuty = 0;
    int spMonth = Calendar.getInstance().get(Calendar.MONTH);
    int spYear = Calendar.getInstance().get(Calendar.YEAR);
    private ArrayList<String> months = new ArrayList<>();
    private Timer timer;
    private TimerTask timerTask;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.locate_On_Map_RL)
    RelativeLayout locate_On_Map_RL;
    @BindView(R.id.superVisor_NameTV)
    TextView superVisor_NameTV;
    @BindView(R.id.call_Option_IV)
    AppCompatImageView call_Option_IV;
    @BindView(R.id.whatsApp_Option_IV)
    AppCompatImageView whatsApp_Option_IV;
    @BindView(R.id.message_Option_IV)
    AppCompatImageView message_Option_IV;
    @BindView(R.id.superVisor_Detail_LY)
    LinearLayout superVisor_Detail_LY;

    @BindView(R.id.header_layout)
    RelativeLayout headerLayout;
    @BindView(R.id.rating_tv)
    TextView rating_tv;
    @BindView(R.id.badges_TV)
    TextView badges_TV;
    @BindView(R.id.view_details_tv)
    TextView view_details_tv;

    @BindView(R.id.sync_home_iv)
    ImageView sync_home_iv;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.attendance_Mark_RL)
    LinearLayout attendance_Mark_RL;
    @BindView(R.id.duty_Hr_TV)
    TextView duty_Hr_TV;
    @BindView(R.id.duty_Min_TV)
    TextView duty_Min_TV;
    private ArrayList<DailyAttendanceDetail> dailyAttendanceDetailArrayList;

    private List<Flash_Message_Model> flash_message_modelList;

    @Override
    public int layoutResource() {
        return R.layout.mark_attendance;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        setViewConditions();
        sync_home_iv.setVisibility(View.VISIBLE);
        totalPresentDayCount = 0;
        totalLeaveCount = 0;
        extraDuty = 0;
        presentCountTV.setText("0");
        leave_Count_TV.setText("0");
        setDetailOnCalender();
        ((MainActivity_Guard) getActivity()).changeFooter_Icon_Color_from_Fragment(0);
        flash_message_modelList = CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().getAllRecords();
        if (flash_message_modelList.size() == 0) {
            flashDetailLY.setVisibility(View.GONE);
        } else {
            flashDetailLY.setVisibility(View.VISIBLE);
            flashMessagePager();
            timer_for_Flash();
        }
        Drawable mIcon = ContextCompat.getDrawable(getActivity(), R.drawable.white_arrow);

        if (CSShearedPrefence.isNightModeEnabled()) {
            previous_month_iv.setImageDrawable(mIcon);
            next_month_iv.setImageDrawable(mIcon);
            headerLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));


        } else {
            mIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), PorterDuff.Mode.MULTIPLY);
            otherDutyActionIV.setImageDrawable(mIcon);
            previous_month_iv.setImageDrawable(mIcon);
            next_month_iv.setImageDrawable(mIcon);
        }

        Collections.addAll(months, getResources().getStringArray(R.array.month));
        CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();
        UserDetailModel userDetailModel = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                getUserDetails(CSShearedPrefence.getUser());
        Glide.with(csActivity).load(userDetailModel.getProfile_Picture())
                .error(R.drawable.no_image_found)
                .dontAnimate()
                .thumbnail(0.5f)
                .into(userProfileIV);
        regNoTV.setText(userDetailModel.getRegNo());
        UserDetailModel userDetailModel1 = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                getUserDetails(CSShearedPrefence.getUser());
        timePeriodYearTV.setText(calculateYear() + " " + CSStringUtil.getString(R.string.working_since) + " " + userDetailModel1.getCOMPANY_NAME());
        userNameTV.setText(userDetailModel.getName());
        rating_tv.setText(String.valueOf(userDetailModel.getUser_rating()));
        badges_TV.setText(String.valueOf(userDetailModel.getUSER_BADGES()));
        rating_bar.setRating(userDetailModel.getUser_rating());
        dutyRankNameTV.setText(userDetailModel.getDesignation().toLowerCase());
        if (!CSStringUtil.isEmptyStr(userDetailModel.getSuperVisorName())) {
            superVisor_Detail_LY.setVisibility(View.VISIBLE);
            call_Option_IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + userDetailModel.getSuperVisorPhone()));
                    startActivity(intent);
                }
            });
            superVisor_NameTV.setText(userDetailModel.getSuperVisorName());
            whatsApp_Option_IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String contact = "+00 9876543210"; // use country code with your phone number
                    String url = "https://api.whatsapp.com/send?phone=" + userDetailModel.getSuperVisorPhone();
                    try {
                        PackageManager pm = csActivity.getPackageManager();
                        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    } catch (PackageManager.NameNotFoundException e) {
                        Toast.makeText(csActivity, "WhatsApp app not installed in your phone", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            message_Option_IV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (CSUtil.getDefaultSmsAppPackageName(requireContext()) != null) {
                            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                            smsIntent.setType("vnd.android-dir/mms-sms");
                            smsIntent.putExtra("address", userDetailModel.getSuperVisorPhone());
//                    smsIntent.putExtra("sms_body","Body of Message");
                            startActivity(smsIntent);
                        } else {
                            Toast.makeText(csActivity, "No app found to send message ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            superVisor_Detail_LY.setVisibility(View.GONE);
        }

      /*  if (CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getUserDetails
                (CSShearedPrefence.getUser()) != null) {

            Glide.with(csActivity).load(CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getUserDetails
                    (CSShearedPrefence.getUser()).getPicture())
                    .error(R.drawable.no_image_found)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .into(userImageCV);
        } else {
            Glide.with(csActivity).load(R.drawable.no_image_found)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .into(userImageCV);
        }*/
       /* userImageCV.setOnClickListener(view -> {
            CSAppUtil.openBottomSheetfragment(csActivity, R.id.guardProfile);
        });*/
     /*   notificationFrameLayout.setOnClickListener(view -> {
            CSAppUtil.openBottomSheetfragment(csActivity, R.id.notification);

        });*/
        month_name_tv.setText(months.get(spMonth) + ", " + spYear);

        previous_month_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalPresentDayCount = 0;
                totalLeaveCount = 0;
                extraDuty = 0;
                presentCountTV.setText("0");
                leave_Count_TV.setText("0");
                workingHourTV.setText("0");
               /* for (int i = 0; i < taskModelList.size(); i++) {
                    taskModelList.get(i).setSelected(false);
                    taskModelList.get(i).setLeaveSelected(false);
                }*/
                spMonth += (11);
                spYear--;
                updateMonthYear();
            }
        });
        next_month_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPresentDayCount = 0;
                totalLeaveCount = 0;
                extraDuty = 0;
                presentCountTV.setText("0");
                leave_Count_TV.setText("0");
//                workingHourTV.setText(String.valueOf(0));
               /* for (int i = 0; i < taskModelList.size(); i++) {
                    taskModelList.get(i).setSelected(false);
                    taskModelList.get(i).setLeaveSelected(false);
                }*/
                spMonth++;
                updateMonthYear();
            }
        });
        locate_On_Map_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geoLocation = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().getGeo_Location(DateUtils.getCurrentDate_format());
                if (!CSStringUtil.isEmptyStr(geoLocation)) {
//                    String direction = geoLocation.split(",")[1] + "," + geoLocation.split(",")[0];

//                    String my_data = String.format("http://maps.google.com/maps?saddr=" + direction);
                    String uri = "http://maps.google.com/maps?q=loc:" + Double.parseDouble(geoLocation.split(",")[1]) + "," + Double.parseDouble(geoLocation.split(",")[0]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);

                }
            }
        });
        view_details_tv.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("MONTH", spMonth);
            bundle.putInt("YEAR", spYear);
            CSAppUtil.openbottomSheetfragmentWithBundle(csActivity, R.id.CalendarLeavePresent, bundle);
        });
    }

    private String calculateYear() {
        String years = null;
        try {
            int currentDate = Integer.parseInt(DateUtils.getCurrentDate_format().split("-")[0]);
            int joiningdate = Integer.parseInt(CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().getEmployee
                    (CSShearedPrefence.getUser()).getJOININGDATE().split("-")[0]);
            if (currentDate > joiningdate) {
                years = String.valueOf(currentDate - joiningdate);
            } else {
                years = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return years;
    }

    @OnClick({R.id.dutyInBTN, R.id.dutyOutBTN, R.id.markOtherDutyRL, R.id.sync_home_iv})
    public void onClickMethod(View v) {
        switch (v.getId()) {
            case R.id.dutyInBTN:
                optionSelectionType = 0;
                csActivity.getCurrentLocationWithOutLoader(this);
                if (csActivity.locationPermissionAllowed == 1) {
                    try {
                        if (timerTask != null) {
                            timerTask.cancel();
                            //cancel timer task and assign null
                        }
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("SELF_MODEL", mapTableRosterAndShift);
                        bundle.putString("DUTY_STATUS", "DUTY_IN");
                        bundle.putString("SCREEN_TAG", "SELF_SCREEN");
                        CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_markAttendance_to_QR_Code_Scan, bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    detectLocationAllowed();
                }
                break;
            case R.id.dutyOutBTN:
                optionSelectionType = 1;
                csActivity.getCurrentLocationWithOutLoader(this);
                if (csActivity.locationPermissionAllowed == 1) {
                    try {
                        if (timerTask != null) {
                            timerTask.cancel();
                            //cancel timer task and assign null
                        }
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("SELF_MODEL", mapTableRosterAndShift);
                        bundle.putString("DUTY_STATUS", "DUTY_OUT");
                        bundle.putString("SCREEN_TAG", "SELF_SCREEN");
                        CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_markAttendance_to_QR_Code_Scan, bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    detectLocationAllowed();
                }
                break;
            case R.id.markOtherDutyRL:
                Bundle bundle = new Bundle();
                bundle.putString("aa", "app_description_1");

                if (csActivity.locationPermissionAllowed == 1) {
                    if (timerTask != null) {
                        timerTask.cancel();
                        //cancel timer task and assign null
                    }
                    csActivity.getCurrentLocationWithOutLoader(this);
                    CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_markAttendance_to_otherDuty_In_Out, bundle);

                } else {
                    if (timerTask != null) {
                        timerTask.cancel();
                        //cancel timer task and assign null
                    }
                    csActivity.getCurrentLocationWithOutLoader(this);
                    optionSelectionType = 2;
                    detectLocationAllowed();
                    CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_markAttendance_to_otherDuty_In_Out, bundle);

                }
                break;

            case R.id.sync_home_iv:
                if (!CSAppUtil.isNetworkNotAvailable(csActivity, false)) {
                    ApiCalling_Class calling_class = new ApiCalling_Class(csActivity);
                    calling_class.push_All_Apis();
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
                } else {
                    CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.no_internet));
                }

                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setNextDayShiftDetails() {
        try {
            MapTableRosterAndShift shiftDetail = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().
                    getShiftForNextDay(DateUtils.getCurrentDate_TIME_format());
            if (shiftDetail == null) {
                nextDayRosterRL.setVisibility(View.GONE);
                noNextDayRosterTV.setVisibility(View.VISIBLE);
            } else {
                nextDayRosterRL.setVisibility(View.VISIBLE);
                noNextDayRosterTV.setVisibility(View.GONE);
                nextDayShiftStartTimeTV.setText(DateUtils.convert24_Hrs_To12_HrsFormat(shiftDetail.getSTART_TIME()));
                nextDayShiftNameTV.setText(shiftDetail.getSHIFT_NAME());
                nextDayTimePeriodTV.setText(CFUtil.returnHourPeriod(shiftDetail.getSTART_TIME()));

                SimpleDateFormat sdf1 = DateUtils.getInstance().getDate_And_MonthWordFormat();
                String shownDay = sdf1.format(DateUtils.getInstance().dateFromShiftDate(shiftDetail.getROSTER_DATE()));

                String dayOfWeek = DateUtils.getInstance().getWeek_WordFormat().format(DateUtils.getInstance().dateFromShiftDate(shiftDetail.getROSTER_DATE()));
                nextDayDateTV.setText(dayOfWeek + " " + shownDay);
                nextDayUnitNameTV.setText(shiftDetail.getUNIT_NAME() + " (" + shiftDetail.getUNIT_CODE() + ")");
                nextDayPostNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().postName(shiftDetail.getDUTY_POST_ID()));
            }
            nextDayUnitNameTV.setOnClickListener(view -> CSDialogUtil.showUnitName(shiftDetail.getUNIT_NAME() +
                    " (" + shiftDetail.getUNIT_CODE() + ")"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setNextDayShiftDetails_InCurrent() {
        try {
            MapTableRosterAndShift shiftDetail = CSApplicationHelper.application().getDatabaseInstance().
                    rosterDetailDao().getShiftForNextDay(DateUtils.getCurrentDate_TIME_format());
            if (shiftDetail == null) {
                nextDayRosterRL.setVisibility(View.GONE);
                todayRosterRL.setVisibility(View.GONE);
                locate_On_Map_RL.setVisibility(View.GONE);

                noNextDayRosterTV.setVisibility(View.VISIBLE);
//                noTodayRosterTV.setVisibility(View.VISIBLE);
                todayShiftDetailCV.setVisibility(View.GONE);
            } else {
                current_dutyHeaderTV.setText(CSStringUtil.getString(R.string.next_duty));
                nextDayRosterRL.setVisibility(View.GONE);
                todayShiftStartTimeTV.setText(DateUtils.convert24_Hrs_To12_HrsFormat(shiftDetail.getSTART_TIME()));
                todayShiftNameTV.setText(shiftDetail.getSHIFT_NAME());
                todayTimePeroidTV.setText(CFUtil.returnHourPeriod(shiftDetail.getSTART_TIME()));

                SimpleDateFormat sdf1 = DateUtils.getInstance().getDate_And_MonthWordFormat();
                String shownDay = sdf1.format(DateUtils.getInstance().dateFromShiftDate(shiftDetail.getROSTER_DATE()));

                String dayOfWeek = DateUtils.getInstance().getWeek_WordFormat().format(DateUtils.getInstance().dateFromShiftDate(shiftDetail.getROSTER_DATE()));
                todayDateTV.setText(dayOfWeek + " " + shownDay);
                unitNameTV.setText(shiftDetail.getUNIT_NAME() + " (" + shiftDetail.getUNIT_CODE() + ")");
                postNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().postName(shiftDetail.getDUTY_POST_ID()));
                unitNameTV.setOnClickListener(view -> CSDialogUtil.showUnitName(shiftDetail.getUNIT_NAME() +
                        " (" + shiftDetail.getUNIT_CODE() + ")"));
                dutyInBTN.setEnabled(false);
                dutyOutBTN.setEnabled(false);
                dutyOutBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                dutyOutTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                dutyInBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                dutyInTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTodayMarkdata() {
        try {
            todayShiftStartTimeTV.setText(DateUtils.convert24_Hrs_To12_HrsFormat(mapTableRosterAndShift.getSTART_TIME()));
//            todayShiftStartTimeTV.setText(mapTableRosterAndShift.getSTART_TIME().split(":")[0] + ":" + mapTableRosterAndShift.getSTART_TIME().split(":")[1]);
            todayShiftNameTV.setText(mapTableRosterAndShift.getSHIFT_NAME());
            todayTimePeroidTV.setText(CFUtil.returnHourPeriod(mapTableRosterAndShift.getSTART_TIME()));
            SimpleDateFormat sdf1 = DateUtils.getInstance().getDate_And_MonthWordFormat();
            String shownDay = sdf1.format(DateUtils.getInstance().dateFromShiftDate(mapTableRosterAndShift.getSHIFT_START_TIME().split(" ")[0]));
            String dayOfWeek = DateUtils.getInstance().getWeek_WordFormat().format(DateUtils.getInstance().dateFromShiftDate(mapTableRosterAndShift.getSHIFT_START_TIME().split(" ")[0]));
            todayDateTV.setText(dayOfWeek + " " + shownDay);
            unitNameTV.setText(mapTableRosterAndShift.getUNIT_NAME() + " (" + mapTableRosterAndShift.getUNIT_CODE() + ")");
            postNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().postName(mapTableRosterAndShift.getDUTY_POST_ID()));
            setNextDayShiftDetails();
            unitNameTV.setOnClickListener(view -> CSDialogUtil.showUnitName(mapTableRosterAndShift.getUNIT_NAME() +
                    " (" + mapTableRosterAndShift.getUNIT_CODE() + ")"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dialog_onClick(int requestCode) {
        if (requestCode == 12345) {
            csActivity.getCurrentLocationWithOutLoader(this);
            detectLocationAllowed();

        }
    }


    @SuppressLint("SetTextI18n")
    private void setViewConditions() {
        try {
            CFUtil.checkAttendanceToCallService_When_Attendance_FromOther_Duty(csActivity);
            mapTableRosterAndShift = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().
                    getShiftDetailOnRosterBase(DateUtils.getCurrentDate_TIME_format());
            if (mapTableRosterAndShift != null) {

                showViewAfterMinutes(null, mapTableRosterAndShift);
                setWarningHeaderDescription();
                CSShearedPrefence.setunitName(CSApplicationHelper.application().getDatabaseInstance().siteDetail_DAO().getSiteName(mapTableRosterAndShift.getUNIT_CODE()));
                CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getAttendanceDetailForTodayWithShift_UnitBase(
                        mapTableRosterAndShift.getREGNO(), mapTableRosterAndShift.getSHIFT_ID(), mapTableRosterAndShift.getUNIT_CODE(),
                        DateUtils.getCurrentDate_TIME_format()).observe(this, dailyAttendanceDetail -> {
                    if (dailyAttendanceDetail != null) {
                        setTodayMarkdata();
                        //******************* DISABLE DUTY IN and DUTY OUT WHEN MARK ATTENDANCE ONCE IN A DAY********************************************************
                        if (dailyAttendanceDetail.getDuty_status().equalsIgnoreCase("DUTY_OUT")) {
                            duty_OUTTime_TV.setVisibility(View.VISIBLE);
                            duty_INTime_TV.setVisibility(View.VISIBLE);
                            duty_INTime_TV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                            duty_OUTTime_TV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                           /* complete_Duty_TV.setVisibility(View.VISIBLE);
                            complete_Duty_TV.setText(CSStringUtil.getString(R.string.complete_duty_today));*/
                           /* duty_OUTTime_TV.setText(dailyAttendanceDetail.getACTENDTIME().split(" ")[1].split(":")[0] + " : " +
                                    dailyAttendanceDetail.getACTENDTIME().split(" ")[1].split(":")[1] + " " + CFUtil.returnHourPeriod(
                                    dailyAttendanceDetail.getACTENDTIME().split(" ")[1]));
 */
                            duty_INTime_TV.setText(DateUtils.convert24_Hrs_To12_HrsWith_AM_OR_PMFormat(dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1]));
                            duty_OUTTime_TV.setText(DateUtils.convert24_Hrs_To12_HrsWith_AM_OR_PMFormat(dailyAttendanceDetail.getACTENDTIME().split(" ")[1]));
//                            CFUtil.checkAttendanceToCallService_When_Attendance_FromOther_Duty(csActivity);
                           /* duty_INTime_TV.setText(dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1].split(":")[0] + " : " +
                                    dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1].split(":")[1] + " " + CFUtil.returnHourPeriod(
                                    dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1]));*/
                            dutyInBTN.setEnabled(false);
                            dutyOutBTN.setEnabled(false);
                            dutyOutBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                            dutyOutTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));

                            dutyInBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                            dutyInTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                        }
                        //****************************DISABLE DUTY IN WHEN ATTENDANCE TABLE RECORD FOUND ON PUNCH DATE**********************************************************
                        else {

                            attendance_Not_Mark_RL.setVisibility(View.GONE);
                            duty_INTime_TV.setVisibility(View.VISIBLE);
                           /* duty_INTime_TV.setText(dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1].split(":")[0] + " : " +
                                    dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1].split(":")[1] + " " + CFUtil.returnHourPeriod(
                                    dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1]));
*/
                            attendance_Mark_RL.setVisibility(View.VISIBLE);
                            String duty_In_Time = dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1];
                            duty_Hr_TV.setText(duty_In_Time.split(":")[0]);
                            duty_Min_TV.setText(duty_In_Time.split(":")[1]);
                            duty_INTime_TV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                            duty_INTime_TV.setText(DateUtils.convert24_Hrs_To12_HrsWith_AM_OR_PMFormat(dailyAttendanceDetail.
                                    getACTSTARTTIME().split(" ")[1]));
//                            CFUtil.checkAttendanceToCallService_When_Attendance_FromOther_Duty(csActivity);

                            if (DateUtils.dateTime_Difference(DateUtils.getCurrentDate_TIME_format(), mapTableRosterAndShift.getSHIFT_END_TIME()) < 1) {
                                dutyOutBTN.setEnabled(true);
                                dutyInBTN.setEnabled(false);
                                dutyOutBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                                dutyOutTV.setTextColor(getResources().getColor(R.color.white));
                                dutyInBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                                dutyInTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                            } else if (DateUtils.compareDate_Nd_TimeFormat(DateUtils.getCurrentDate_TIME_format(), mapTableRosterAndShift.getDUTY_OUT_DISABLE_TIME())) {
                                attendance_Not_Mark_RL.setVisibility(View.GONE);
                                dutyInBTN.setEnabled(false);
                                dutyInBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                                dutyInTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                                dutyOutBTN.setEnabled(false);
                                dutyOutBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                                dutyOutTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                                duty_OUTTime_TV.setVisibility(View.VISIBLE);
                                duty_OUTTime_TV.setText(CSStringUtil.getString(R.string.auto_Out));
                            } else {
                                dutyOutBTN.setEnabled(true);
                                dutyInBTN.setEnabled(false);
                                dutyOutBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                                dutyOutTV.setTextColor(getResources().getColor(R.color.white));
                                dutyInBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                                dutyInTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                            }
                        }
                    } else {
                        //************************************ SET VIEW OF TODAY ROSTER DATE WHEN ATTENDANCE NOT FOUND ON DATE MARK******************************************************************
                        setTodayMarkdata();
                        if (DateUtils.dateTime_Difference(DateUtils.getCurrentDate_TIME_format(), mapTableRosterAndShift.getSHIFT_END_TIME()) < 1) { // DUTY IN ALLOW  ONLY WHEN
                            // DIFFERENCE BETWEEN END TIME AND CURRENT TIME = 1 HOUR LEFT
//                            complete_Duty_TV.setVisibility(View.GONE);
                            attendance_Not_Mark_RL.setVisibility(View.GONE);
                            duty_INTime_TV.setVisibility(View.VISIBLE);
                            duty_INTime_TV.setText(CSStringUtil.getString(R.string.shift_In_Over));
                            dutyInBTN.setEnabled(false);
                            dutyInBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                            dutyInTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                            dutyOutBTN.setEnabled(false);
                            dutyOutBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                            dutyOutTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                        } else if (DateUtils.compareDate_Nd_TimeFormat(DateUtils.getCurrentDate_TIME_format(), mapTableRosterAndShift.getDUTY_OUT_DISABLE_TIME())) { // DISABLE BOTH
//                            OPTION  DUTY IN AND DUTY OUT WHEN CUREENT TIME IS GREATER THEN  SHIFT END OUT DISABLE TIME
                            attendance_Not_Mark_RL.setVisibility(View.GONE);
                            dutyInBTN.setEnabled(false);
                            dutyInBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                            dutyInTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                            dutyOutBTN.setEnabled(false);
                            dutyOutBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                            dutyOutTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                        } else if (DateUtils.compareDate_Nd_TimeFormat(DateUtils.getCurrentDate_TIME_format(), mapTableRosterAndShift.getSHIFT_START_TIME())) {
                            if (CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().markAttendanceTodayWithShift_unitBase
                                    (CSShearedPrefence.getUser(),
                                            mapTableRosterAndShift.getSHIFT_ID(),
                                            mapTableRosterAndShift.getUNIT_CODE(), DateUtils.getCurrentDate_TIME_format()) == null) {
                                dutyInBTN.setBackgroundResource(R.drawable.red_rounded_circle);
                                dutyInTV.setTextColor(getResources().getColor(R.color.white));
//                                attendance_Not_Mark_RL.setVisibility(View.VISIBLE);
                                dutyOutBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                                dutyOutTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                                dutyOutBTN.setEnabled(false);
                            } else {

                                dutyInBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                                dutyInTV.setTextColor(getResources().getColor(R.color.white));
//                                attendance_Not_Mark_RL.setVisibility(View.GONE);
                                dutyOutBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                                dutyOutTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                                dutyOutBTN.setEnabled(false);
                                dutyInBTN.setEnabled(true);
                            }
                        } else {
                            dutyInBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                            dutyInTV.setTextColor(getResources().getColor(R.color.white));
//                            attendance_Not_Mark_RL.setVisibility(View.GONE);
                            dutyOutBTN.setBackgroundResource(R.drawable.light_grey_round_background);
                            dutyOutTV.setTextColor(getResources().getColor(R.color.duty_out_dark_mode_disable));
                            dutyOutBTN.setEnabled(false);
                            dutyInBTN.setEnabled(true);
                        }

                    }
                });
            } else {

                //********************************** WHEN ROSTER NOT FOUND ON DATE THEN VIEW NEXT DAY SHIFT FROM ROSTER****************************************************************************************
                setNextDayShiftDetails_InCurrent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeServiceIf_Running_on_previous_day() {
        try {
            DailyAttendanceDetail dailyAttendanceDetail = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                    checkAttendancePunchToday(CSShearedPrefence.getUser(), DateUtils.getInstance().getPreviousDateCalender(DateUtils.getCurrentDate_format()));

            if (dailyAttendanceDetail != null) {
                if (dailyAttendanceDetail.getDuty_status().equalsIgnoreCase("DUTY_OUT")) {
                    if (CFUtil.isMyServiceRunning(csActivity, TrackingService.class)) {
                        Intent mServiceIntent = new Intent(csActivity, TrackingService.class);
                        csActivity.stopService(mServiceIntent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void detectLocationAllowed() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!stopHandler) {
                    handler.postDelayed(this, 2000);
                }
                Log.e("locationPermi", "" + csActivity.locationPermissionAllowed);

                if (csActivity.locationPermissionAllowed == 1) {  // MEAN LOCATION PERMISSION GRANT
//                    handler.sendEmptyMessageDelayed(0, 0000);

                    if (optionSelectionType == 1) { // FOR DUTY OUT
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("SELF_MODEL", mapTableRosterAndShift);
                            bundle.putString("DUTY_STATUS", "DUTY_OUT");
                            bundle.putString("SCREEN_TAG", "SELF_SCREEN");
                            CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_markAttendance_to_QR_Code_Scan, bundle);
                            stopHandler = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (optionSelectionType == 0) {  // FOR DUTY IN
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("SELF_MODEL", mapTableRosterAndShift);
                            bundle.putString("DUTY_STATUS", "DUTY_IN");
                            bundle.putString("SCREEN_TAG", "SELF_SCREEN");
                            CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_markAttendance_to_QR_Code_Scan, bundle);
                            stopHandler = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        stopHandler = true;
                    }
                }
            }
        }, 2000);
    }

    private void updateMonthYear() {

      /*  setTaskModelList(spMonth, spYear);
        for (int i = 0; i < taskModelList.size(); i++) {
            taskModelList.get(i).setSelected(false);
            taskModelList.get(i).setLeaveSelected(false);
        }*/
        spYear += spMonth / 12;
        spMonth %= 12;
        month_name_tv.setText(months.get(spMonth) + ", " + spYear);
        setDetailOnCalender();
       /* employeeCalendarAdapter = new CalenderViewAdapter(getContext(), taskModelList, this, _dateRange);
        calenderRv.setAdapter(employeeCalendarAdapter);
        calenderRv.getRecycledViewPool().setMaxRecycledViews(0, 0);*/
    }

    public void setDetailOnCalender() {
        totalDayInMonth = getTotalNoOfDayInMonth(spMonth, spYear);
        Log.e("", "totalDayInMonth   " + totalDayInMonth);

        String presentDayStr;
        Leave_Detail_Model leaveDayStr;
        Attendance_for_calender_model attendanceForCalenderModel;
        for (int i = 1; i <= totalDayInMonth; i++) {
            String startDateOfSelectedmonth = spYear + "-" + String.format("%02d", spMonth + 1) + "-" + String.format("%02d", i);
            List<MapTableRosterAndShift> mapTableRosterAndShifts = CSApplicationHelper.application().getDatabaseInstance()
                    .rosterDetailDao().getRosterList(CSShearedPrefence.getUser(), startDateOfSelectedmonth);
            if (mapTableRosterAndShifts.size() > 0) {
                leaveDayStr = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
                        getLeaveRecord(CSShearedPrefence.getUser(), startDateOfSelectedmonth);
                if (leaveDayStr != null) {
                    totalLeaveCount++;
                    leave_Count_TV.setText(String.valueOf(totalLeaveCount));
                }
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
                    workingHourTV.setText(String.valueOf(extraDuty));
                    presentCountTV.setText(String.valueOf(totalPresentDayCount));

                }
            } else {
                attendanceForCalenderModel = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                        getAttendanceDetailOn_calender(CSShearedPrefence.getUser(), startDateOfSelectedmonth);
                int firstDayOfMonth = getFirstDayOfMonth(spMonth, spYear);
                Log.e("splitPresentDayStr", "firstDayOfMonth   " + firstDayOfMonth);
                if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 0) {
                    String splitPresentDayStr = attendanceForCalenderModel.getACT_START_TIME().substring(attendanceForCalenderModel.getACT_START_TIME().
                            lastIndexOf("-") + 1);
                    Log.e("splitPresentDayStr", "splitPresentDayStr   " + splitPresentDayStr);

                    int presentDate = Integer.parseInt(splitPresentDayStr);
                    Log.e("splitPresentDayStr", "postNameTV   " + presentDate);
                    totalPresentDayCount++;
                    presentCountTV.setText(String.valueOf(totalPresentDayCount));
                    if (Integer.parseInt(attendanceForCalenderModel.getEXTRA_DUTY()) > 1) {
                        extraDuty++;
                    }
                    workingHourTV.setText(String.valueOf(extraDuty));

                }
                leaveDayStr = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().
                        getLeaveRecord(CSShearedPrefence.getUser(), startDateOfSelectedmonth);
                Log.e("", "leave day ....   " + leaveDayStr);
                if (leaveDayStr != null) {
                    totalLeaveCount++;
                    leave_Count_TV.setText(String.valueOf(totalLeaveCount));

                }
            }
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

    private void showViewAfterMinutes(DailyAttendanceDetail dailyAttendanceDetail, MapTableRosterAndShift mapTableRosterAndShift) {

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(),
                    mapTableRosterAndShift.getSHIFT_START_TIME()) <= 15 && DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(),
                    mapTableRosterAndShift.getSHIFT_START_TIME()) > 0) {
                setWarningHeaderDescription();
            }

        };
        timer = new Timer();
        long DELAY_MS = 1000 * 1 * 60;
        long PERIOD_MS = 1000 * 1 * 60;
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                Log.e("showViewAfterMinutes", "TIMER");
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

      /*  final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                if (!stopHandler) {
                    handler.postDelayed(this, 60000);
                }
                setWarningHeaderDescription();
                   *//* if(aaa>2){
                        handler.removeMessages(0);
                        Log.e("locationPermi", "showViewAfterMinutes" + "finish");
//                        handler.removeCallbacks(this::run);
                        stopHandler=true;
                    }else {
                        Log.e("locationPermi", "showViewAfterMinutes" + csActivity.locationPermissionAllowed);
                    }*//*
            }
        }, 60000);*/

    }

    @SuppressLint("SetTextI18n")
    private void setWarningHeaderDescription() {
        try {
            if (DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(),
                    mapTableRosterAndShift.getSHIFT_START_TIME()) <= 15 &&
                    DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(),
                            mapTableRosterAndShift.getSHIFT_START_TIME()) > 0) {
                if (!DateUtils.compareDate_Nd_TimeFormat(mapTableRosterAndShift.getDUTY_IN_ENABLE_TIME(), mapTableRosterAndShift.getSHIFT_START_TIME())) {
                    Log.e("locationPermi", "showViewAfterMinutes" + csActivity.locationPermissionAllowed);

                    attendance_Not_Mark_RL.setVisibility(View.VISIBLE);
                    if (CSShearedPrefence.isNightModeEnabled()) { // NIGHT MODE TRUE
                        attendance_Not_Mark_RL.setBackground(CSDrawable.getDrawable(R.color.app_default_red_color));
                        warning_IV.setImageDrawable(CSDrawable.getDrawable(R.drawable.warning_night_mode));
                        warning_TV.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        warning_IV.setImageDrawable(CSDrawable.getDrawable(R.drawable.not_marked_icon));
                        attendance_Not_Mark_RL.setBackground(CSDrawable.getDrawable(R.color.attendance_not_mark_backGround));
                        warning_TV.setTextColor(getResources().getColor(R.color.app_default_red_color));
                    }
                    warning_TV.setText(CSStringUtil.getString(R.string.attendance_not_marked));
                    Glide.with(csActivity).load(CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getPicture(CSShearedPrefence.getUser()))
                            .placeholder(R.drawable.no_image_found)
                            .into(userImageCV);
                }
            } else {
                Glide.with(csActivity).load(CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getPicture(CSShearedPrefence.getUser()))
                        .placeholder(R.drawable.no_image_found)
                        .into(userImageCV);
                attendance_Not_Mark_RL.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
 /*   private void setWarningHeaderDescription_with_timer_minute() {
        try {
            if (DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(),
                    mapTableRosterAndShift.getSHIFT_START_TIME()) <= 15 && DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(),
                    mapTableRosterAndShift.getSHIFT_START_TIME()) > 0) {
                if (!DateUtils.compareDate_Nd_TimeFormat(mapTableRosterAndShift.getDUTY_IN_ENABLE_TIME(), mapTableRosterAndShift.getSHIFT_START_TIME())) {
                    Log.e("locationPermi", "showViewAfterMinutes" + csActivity.locationPermissionAllowed);

                    attendance_Not_Mark_RL.setVisibility(View.VISIBLE);
                    if (CSShearedPrefence.isNightModeEnabled()) { // NIGHT MODE TRUE
                        attendance_Not_Mark_RL.setBackground(CSDrawable.getDrawable(R.color.app_default_red_color));
                        warning_IV.setImageDrawable(CSDrawable.getDrawable(R.drawable.warning_night_mode));
                        warning_TV.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        warning_IV.setImageDrawable(CSDrawable.getDrawable(R.drawable.not_marked_icon));
                        attendance_Not_Mark_RL.setBackground(CSDrawable.getDrawable(R.color.attendance_not_mark_backGround));
                        warning_TV.setTextColor(getResources().getColor(R.color.app_default_red_color));
                    }
                    warning_TV.setText(CSStringUtil.getString(R.string.duty_remaining_time_start_tag) + " " + DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(),
                            mapTableRosterAndShift.getSHIFT_START_TIME())
                            + " " + CSStringUtil.getString(R.string.duty_remaining_time_end_tag));

                }
            } else if (DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(), DateUtils.getInstance().
                    getDutyInDateTime11(mapTableRosterAndShift.getSHIFT_START_TIME(), 10)) <= 10
                    && DateUtils.getInstance().getMinutesOnly(DateUtils.getCurrentDate_TIME_format(), DateUtils.getInstance().
                    getDutyInDateTime11(mapTableRosterAndShift.getSHIFT_START_TIME(), 10)) > 0) {
                if (CSShearedPrefence.isNightModeEnabled()) { // NIGHT MODE TRUE
                    attendance_Not_Mark_RL.setBackground(CSDrawable.getDrawable(R.color.app_default_red_color));
                    warning_IV.setImageDrawable(CSDrawable.getDrawable(R.drawable.warning_night_mode));

                    warning_TV.setTextColor(getResources().getColor(R.color.white));
                } else {
                    warning_IV.setImageDrawable(CSDrawable.getDrawable(R.drawable.not_marked_icon));
                    attendance_Not_Mark_RL.setBackground(CSDrawable.getDrawable(R.color.attendance_not_mark_backGround));
                    warning_TV.setTextColor(getResources().getColor(R.color.app_default_red_color));
                }
                warning_TV.setText(CSStringUtil.getString(R.string.mark_In_Time_Away_start_tag) + " " + DateUtils.getInstance().getMinutesOnly(mapTableRosterAndShift.getSHIFT_START_TIME(),
                        DateUtils.getCurrentDate_TIME_format()) + " " + CSStringUtil.getString(R.string.minutes));
            } else {
                attendance_Not_Mark_RL.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void timer_for_Flash() {
        try {

            if (flash_message_modelList.size() > 0) {
                final Handler handler = new Handler();
                final Runnable Update = () -> {
                    if (viewPager.getCurrentItem() == flash_message_modelList.size() - 1) {
                        viewPager.setCurrentItem(0);
                    } else
                        viewPager.setCurrentItem(getItem(1), true);
                };
                timer = new Timer();
                long DELAY_MS = 1000;
                long PERIOD_MS = 7000;
                timer.schedule(new TimerTask() { // task to be scheduled

                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, DELAY_MS, PERIOD_MS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timerTask != null) {
            timerTask.cancel();
            //cancel timer task and assign null
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void flashMessagePager() {
        addBottomDots(0);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    // or get a reference to the ViewPager and cast it to ViewParent
                    viewPager.requestDisallowInterceptTouchEvent(false);
                    timer.cancel();
                    return false; //This is important, if you return TRUE the action of swipe will not take place.
                case MotionEvent.ACTION_DOWN:
                    viewPager.requestDisallowInterceptTouchEvent(false);
                    timer.cancel();
                    break;
                case MotionEvent.ACTION_UP:
                    viewPager.requestDisallowInterceptTouchEvent(true);
                    timer_for_Flash();
            }
            return false;
        });


    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(final int position) {

            addBottomDots(position);
//            btnSkip.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            Log.d("TAG", "onPageScrolled: ");
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.e("instantiateItem>>>", "" + position);
            layoutInflater = (LayoutInflater) csActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.view_pager_single_view, container, false);
            TextView flashDesc_TV = view.findViewById(R.id.flashDesc_TV);
            LinearLayout flashDecLY = view.findViewById(R.id.flashDecLY);
//            LinearLayout linearLayout = view.findViewById(R.id.ll_layout);
//            switchCondition(view, position);
            flashDesc_TV.setText(flash_message_modelList.get(position).getMESSAGE());
            flashDecLY.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        if (!CSStringUtil.isEmptyStr(flash_message_modelList.get(position).getACTION_URL())) {
                            String url = flash_message_modelList.get(position).getACTION_URL();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            csActivity.startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(csActivity, "WhatsApp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    //e.printStackTrace();
                    //this will log the page number that was click
                    //Log.e("pageSelected", "This page was clicked: " + position);
                }
            });
            container.addView(view);

//            if (position == layoutDrawable.length - 1) {
//                View start = view.findViewById(R.id.button_tour);
//                if (start != null) {
//                    start.setOnClickListener(v -> switchCondition(position));
//                }
//            }
            return view;
        }


        @Override
        public int getCount() {
            if (flash_message_modelList != null)
                return flash_message_modelList.size();
            else
                return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void addBottomDots(int currentPage) {
        ImageView[] dots = new ImageView[flash_message_modelList.size()];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(csActivity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (int) csActivity.getResources().getDimension(R.dimen.eight_dp),
                    (int) csActivity.getResources().getDimension(R.dimen.eight_dp));
            layoutParams.setMargins((int) csActivity.getResources().getDimension(R.dimen.sevenDp),
                    (int) csActivity.getResources().getDimension(R.dimen.two_dp),
                    (int) csActivity.getResources().getDimension(R.dimen.two_dp),
                    (int) csActivity.getResources().getDimension(R.dimen.two_dp));
            dots[i].setLayoutParams(layoutParams);
            dots[i].setBackground(CSDrawable.getDrawable(R.drawable.circle_red_img));
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setBackground(CSDrawable.getDrawable(R.drawable.background_circle_line_thin));
    }

    private void startHomePageSync(Context context) {
        progress_bar.setVisibility(View.VISIBLE);

        OneTimeWorkRequest getRosterWorker = new OneTimeWorkRequest.Builder(GetRosterWorker.class).build();
        OneTimeWorkRequest getAttendanceWork = new OneTimeWorkRequest.Builder(GetAttendanceWork.class).build();

        WorkManager.getInstance()
                .beginWith(getAttendanceWork)
                .then(getRosterWorker)
                .enqueue();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (context != null) {
                    progress_bar.setVisibility(View.GONE);
                    setViewConditions();
                }
            }
            /*UI refresh*/

        }, 8000);
    }


}
