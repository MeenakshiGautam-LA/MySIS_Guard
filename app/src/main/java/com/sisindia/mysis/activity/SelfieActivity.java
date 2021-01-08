package com.sisindia.mysis.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.google.android.cameraview.CameraView;
import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.Model.Other_Duty_Mark_Model;
import com.sisindia.mysis.R;
import com.sisindia.mysis.Services.TrackingService;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.AttendanceGuardPicModel;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.utils.ImageCompresssion;
import com.sisindia.mysis.utils.OnCompressListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.sisindia.mysis.Json_Parameter.CreateParameter.createJsonForAttendancePicture;
import static com.sisindia.mysis.Json_Parameter.CreateParameter.setJsonForOtherUnitDutyEmployee;
import static com.sisindia.mysis.Json_Parameter.CreateParameter.setJsonForOtherUnitDutyEmployee_OUT;
import static com.sisindia.mysis.Json_Parameter.CreateParameter.setJson_mark_Attendance;
public class SelfieActivity extends BaseActivity {
    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;
    @BindView(R.id.retake_TV)
    TextView retake_TV;
    @BindView(R.id.submit_TV)
    TextView submit_TV;
    @BindView(R.id.userNameTV)
    TextView userNameTV;
    @BindView(R.id.postRankNameTV)
    TextView dutyRankNameTV;
    @BindView(R.id.unitNameTV)
    TextView unitNameTV;
    @BindView(R.id.postNameTV)
    TextView postNameTV;
    @BindView(R.id.postRankTV)
    TextView postRankTV;
    @BindView(R.id.shiftNameTV)
    TextView shiftNameTV;
    @BindView(R.id.captureLY)
    LinearLayout captureLY;
    @BindView(R.id.userImageCV)
    CircleImageView userImageCV;
    @BindView(R.id.unitCodeTV)
    TextView unitCodeTV;
    private String attendanceUUID = null;
    private Location location;
    @BindView(R.id.mCameraView)
    CameraView mCameraView;
    @BindView(R.id.cancelBtn)
    AppCompatImageView cancelBtn;
    private Handler mBackgroundHandler;
    private String dutyStatus = null;
    private MapTableRosterAndShift mapTableRosterAndShift;
    private byte[] pictureBlob = null;
    private Other_Duty_Mark_Model other_duty_mark_model;
    private Bundle bundle;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CSApplicationHelper.application().setActivity(this);
        CFUtil.screenShotDisableInApp(this); // disable screenShot
        setContentView(R.layout.capture_image_of_user_layout);
        ButterKnife.bind(this);
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }
        setViewBaseOnCondition();
        cancelBtn.setOnClickListener(view -> onBackPressed());
        retake_TV.setOnClickListener(view -> {
            if (bundle.getString("SCREEN_TAG").equalsIgnoreCase("OTHER_DUTY_PUNCH_SCREEN")) {

//                bundle.putSerializable("OTHERUNITEMPLOYEE", other_duty_mark_model);
//                bundle.putString("SCREEN_TAG", "OTHER_DUTY_PUNCH_SCREEN");
//                bundle.putString("DUTY_STATUS", bundle.getString("SCREEN_TAG"));
                Intent intent = new Intent(this, SelfieActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else {
//                bundle.putString("SCREEN_TAG", "SELF_MODEL");
//                bundle.putString("DUTY_STATUS", bundle.getString("SCREEN_TAG"));
                Intent intent = new Intent(this, SelfieActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            /*findViewById(R.id.frame_layout).setVisibility(View.VISIBLE);

            findViewById(R.id.bottomLinearView).setVisibility(View.VISIBLE);
            findViewById(R.id.retakeSubmitLinear).setVisibility(View.GONE);
            findViewById(R.id.viewCaptureImageIV).setVisibility(View.GONE);*/
        });
        captureLY.setOnClickListener(view -> {
            if (mCameraView != null) {
                mCameraView.takePicture();
            }
        });
     /*   if (isFaceDetect) {

        }else {
            captureLY.setOnClickListener(view -> {
                try {
                    getCurrentLocation();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            descriptionTextTV.setVisibility(View.GONE);
                            passDataToNextScreen();
                        }
                    }, 5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }*/
        Drawable mIcon = ContextCompat.getDrawable(this, R.drawable.icon_cross);

        if (CSShearedPrefence.isNightModeEnabled()) {
            cancelBtn.setImageDrawable(mIcon);


        } else {
            mIcon.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.MULTIPLY);
            cancelBtn.setImageDrawable(mIcon);

        }
    }

    private void setViewBaseOnCondition() {
//        getCurrentLocationWithOutLoader(this);
        try {
            bundle = getIntent().getExtras();
            if (bundle != null) {
// ***************************If Condition use for mark Attendance which from Other Unit Duty Employee Punch*********************************************
                if (bundle.getString("SCREEN_TAG").equalsIgnoreCase("OTHER_DUTY_PUNCH_SCREEN")) {
                    other_duty_mark_model = (Other_Duty_Mark_Model) bundle.getSerializable("OTHERUNITEMPLOYEE");
                    Log.e("SCREEN_TAG111..", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
                    setUserDetailForOtherUnitDutyMark();
                    dutyStatus = bundle.getString("DUTY_STATUS");
                }
                //*************** Use For Mark Attendance For Self Employee LOGIN*********************************************************************
                else {
//                    String rosterDate = bundle.getString("ROSTER_DATE");
                    dutyStatus = bundle.getString("DUTY_STATUS");
                    mapTableRosterAndShift = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().
                            getShiftDetailOnRosterBaseByRegNO(CSShearedPrefence.getUser(), DateUtils.getCurrentDate_TIME_format());
                    Log.e("SelfieActivity", "" + mapTableRosterAndShift.getREGNO() + dutyStatus + "DATE" + mapTableRosterAndShift.getROSTER_DATE());
                    setUserDetail();
                    unitNameTV.setOnClickListener(view -> CSDialogUtil.showUnitName(mapTableRosterAndShift.getUNIT_NAME()));
                }
            }
            submit_TV.setOnClickListener(view -> {
                passDataToNextScreen();

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void passDataToNextScreen() {
        if(mGoogleApiClient!=null){
            mGoogleApiClient.disconnect();
        }

        //************************* If Condition use for mark Attendance which from Other Unit Duty Employee Punch*********************************************************
        if (bundle.getString("SCREEN_TAG").equalsIgnoreCase("OTHER_DUTY_PUNCH_SCREEN")) {
            if (dutyStatus.equalsIgnoreCase("DUTY_IN")) {
                otherUnitDutyEmployee_Mark_IN(other_duty_mark_model);

            } else {
                        /*DailyAttendanceDetail dailyAttendanceDetail = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                                getAttendanceDetail(other_duty_mark_model.getEmployeDetailsModel().getREGNO(), "DUTY_IN", DateUtils.getCurrentDate_TIME_format());
                       */
                DailyAttendanceDetail dailyAttendanceDetail = other_duty_mark_model.getDailyAttendanceDetail();
                otherUnitDutyEmployee_Mark_OUT(other_duty_mark_model, dailyAttendanceDetail);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("OTHER_UNIT_DUTY_EMPLOYEE", other_duty_mark_model);
            bundle.putString("DUTY_STATUS", dutyStatus);
            bundle.putString("SCREEN_TAG", "OTHER_DUTY_PUNCH_SCREEN");
//                    bundle.putString("LATITUDE",String.valueOf(location.getLatitude()));
//                    bundle.putString("LONGITUDE",String.valueOf(location.getLongitude()));
            Intent intent = new Intent(SelfieActivity.this, MarkDuty_Confirmation_ViewScreen.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            //*********************Use For Mark Attendance For Self Employee LOGIN**********************************************************
            if (dutyStatus.equalsIgnoreCase("DUTY_IN")) {
                saveAttendanceRecordWhen_IN(mapTableRosterAndShift);
            } else {
                DailyAttendanceDetail dailyAttendanceDetail = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                        getAttendanceDetail(mapTableRosterAndShift.getREGNO(), "DUTY_IN", DateUtils.getCurrentDate_TIME_format());
                saveAttendanceRecordWhen_OUT(mapTableRosterAndShift, dailyAttendanceDetail);
            }
            Bundle bundle = new Bundle();
            bundle.putString("REGNO", mapTableRosterAndShift.getREGNO());
            bundle.putString("DUTY_STATUS", dutyStatus);
            bundle.putString("SCREEN_TAG", "SELF_MARK_ATTENDANCE");
//                    bundle.putString("LATITUDE",String.valueOf(location.getLatitude()));
//                    bundle.putString("LONGITUDE",String.valueOf(location.getLongitude()));
            Intent intent = new Intent(SelfieActivity.this, MarkDuty_Confirmation_ViewScreen.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        }
    }

    @Override
    public int getScreenName() {
        return 0;
    }

    private void setViewVisibility(int id) {
        View view = findViewById(id);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void getCurrent_Location(Location location) {
        this.location = location;
        Log.e("getCurrent_Location" + location.getLatitude(), "\n" + location.getLongitude());

    }

    @SuppressLint("SetTextI18n")
    private void setUserDetail() {
        try {
            UserDetailModel userDetailModel = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                    getUserDetails(mapTableRosterAndShift.getREGNO());
            if (userDetailModel != null) {
                userNameTV.setText(userDetailModel.getName());
                dutyRankNameTV.setText(CFUtil.getKeyDataFromJSON(mapTableRosterAndShift.getJSON(),"SERVICE_NAME")
                        + "( " + CFUtil.getKeyDataFromJSON(mapTableRosterAndShift.getJSON(),"SYMBOL") + " )");
              /*  dutyRankNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().gertServiceDetail(mapTableRosterAndShift.getDUTY_RANK()).getSERVICENAME()
                        + "( " + CSApplicationHelper.application().getDatabaseInstance().serviceDAO().gertServiceDetail(mapTableRosterAndShift.
                        getDUTY_RANK()).getSYMBOL() + " )");*/
               /* postNameTV.setText(CSStringUtil.getString(R.string.post) + " " +
                        CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().
                                postName(mapTableRosterAndShift.getDUTY_POST_ID()));*/
                postNameTV.setText(CSStringUtil.getString(R.string.post) + " " +CFUtil.getKeyDataFromJSON(mapTableRosterAndShift.getJSON(),"POST_NAME").toUpperCase());
               /* shiftNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().
                        getShiftnameBy_ID(mapTableRosterAndShift.getSHIFT_ID()));*/
                shiftNameTV.setText(CFUtil.getKeyDataFromJSON(mapTableRosterAndShift.getJSON(),"SHIFT_NAME").toUpperCase());
                Glide.with(this).load(userDetailModel.getProfile_Picture())
                        .error(R.drawable.no_image_found)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .into(userImageCV);
                unitNameTV.setText(mapTableRosterAndShift.getUNIT_NAME());
                unitCodeTV.setText("(" + mapTableRosterAndShift.getUNIT_CODE() + ")");
               /* postRankTV.setText(CSStringUtil.getString(R.string.post_rank) + " " + CSApplicationHelper.application().getDatabaseInstance().serviceDAO().
                        getSymbol(mapTableRosterAndShift.getDUTY_RANK()).toUpperCase());*/
//                postRankTV.setText(CSStringUtil.getString(R.string.post_rank) + " " +  CFUtil.getKeyDataFromJSON(mapTableRosterAndShift.getJSON(),"SYMBOL").toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUserDetailForOtherUnitDutyMark() {
        try {
            userNameTV.setText(other_duty_mark_model.getEmployeDetailsModel().getEMPNAME());
            if (other_duty_mark_model.getRosterDetail() != null) {
                dutyRankNameTV.setText(other_duty_mark_model.getRosterDetail().getDUTYRANKNAME()
                        + "( " + other_duty_mark_model.getRosterDetail().getDUTYSYMBOL() + " )");
                postNameTV.setText(CSStringUtil.getString(R.string.post) + " " + other_duty_mark_model.getRosterDetail().getDUTYPOSTNAME());
                shiftNameTV.setText(other_duty_mark_model.getRosterDetail().getSHIFTNAME());
                unitNameTV.setText(other_duty_mark_model.getRosterDetail().getUNITNAME() + " (" + other_duty_mark_model.getRosterDetail().getUNITCODE() + ")");

            } else {
                if (other_duty_mark_model.getDailyAttendanceDetail() != null) {
                    dutyRankNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().getSymbolFullName(
                            other_duty_mark_model.getEmployeDetailsModel().getRANK())
                            + "( " + CSApplicationHelper.application().getDatabaseInstance().serviceDAO().getSymbol(
                            other_duty_mark_model.getEmployeDetailsModel().getRANK()) + " )");


                    postNameTV.setText(CSStringUtil.getString(R.string.post) + " " + other_duty_mark_model.getDailyAttendanceDetail().getDUTY_POST_NAME());
                    shiftNameTV.setText(other_duty_mark_model.getDailyAttendanceDetail().getOTHER_DUTY_SHIFT_NAME());
                    unitNameTV.setText(other_duty_mark_model.getDailyAttendanceDetail().getOTHER_DUTY_UNIT_NAME() + " (" + other_duty_mark_model.getDailyAttendanceDetail().getUNITCODE() + ")");

                }
            }
            Glide.with(this).load(other_duty_mark_model.getEmployeDetailsModel().getPICTURE())
                    .error(R.drawable.no_image_found)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .into(userImageCV);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAttendanceRecordWhen_IN(MapTableRosterAndShift mapTableRosterAndShift) {
        try {
            attendanceUUID = String.valueOf(UUID.randomUUID());
            DailyAttendanceDetail dailyAttendanceDetail = new DailyAttendanceDetail();
            dailyAttendanceDetail.setREGNO(mapTableRosterAndShift.getREGNO());
            dailyAttendanceDetail.setID(attendanceUUID);
            dailyAttendanceDetail.setEMPRANK(mapTableRosterAndShift.getDUTY_RANK());
            dailyAttendanceDetail.setUNITCODE(mapTableRosterAndShift.getUNIT_CODE());
            dailyAttendanceDetail.setViolationRemark("");
            dailyAttendanceDetail.setDUTYPOSTID(mapTableRosterAndShift.getDUTY_POST_ID());
            dailyAttendanceDetail.setACTSTARTTIME(DateUtils.getCurrentDate_TIME_format());
            dailyAttendanceDetail.setFinal_Start_Time(DateUtils.getCurrentDate_TIME_format());
            dailyAttendanceDetail.setSHIFTSTARTTIME(mapTableRosterAndShift.getSHIFT_START_TIME());
            dailyAttendanceDetail.setSHIFTENDTIME(mapTableRosterAndShift.getSHIFT_END_TIME());
            dailyAttendanceDetail.setDuty_status(dutyStatus);
            dailyAttendanceDetail.setFlag("1");
            dailyAttendanceDetail.setDUTYRANK(mapTableRosterAndShift.getDUTY_RANK());
            dailyAttendanceDetail.setSHIFTID(mapTableRosterAndShift.getSHIFT_ID());
//            dailyAttendanceDetail.setDATE_MODIFIED(DateUtils.getCurrentDate_TIME_format());

            if (pictureBlob == null) {
                dailyAttendanceDetail.setVOILATE("1");
                dailyAttendanceDetail.setViolationRemark(CFUtil.appRules(R.string.face_not_detect));
            } else {
                dailyAttendanceDetail.setVOILATE("0");
                dailyAttendanceDetail.setViolationRemark("");
                insertAttendancepictureWhenDutyIN(dailyAttendanceDetail);
            }
            dailyAttendanceDetail.setJSON(setJson_mark_Attendance(mapTableRosterAndShift,
                    "DUTY_IN", dailyAttendanceDetail, location));
            CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().insert(dailyAttendanceDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //************** TO START SERVICE IF SELF MARK ATTENDANCE ********************************************
       /* if (CSShearedPrefence.getUser().equalsIgnoreCase(mapTableRosterAndShift.getREGNO())) {
            if (CFUtil.isMyServiceRunning(this, TrackingService.class)) {
            } else {
                Intent mServiceIntent = new Intent(this, TrackingService.class);
                if (Build.VERSION.SDK_INT >= 26) {
                    startForegroundService(mServiceIntent);
                    // Call some material design APIs here
                } else {
                    startService(mServiceIntent);
                    // Implement this feature without material design
                }
            }
        }*/

    }

    private void insertAttendancepictureWhenDutyIN(DailyAttendanceDetail dailyAttendanceDetail) {
        try {
            AttendanceGuardPicModel guardPicModel = new AttendanceGuardPicModel();
            guardPicModel.setId(String.valueOf(UUID.randomUUID()));
            guardPicModel.setAttendanceID(dailyAttendanceDetail.getID());
            guardPicModel.setRegNo(dailyAttendanceDetail.getREGNO());
            guardPicModel.setDateModified(DateUtils.getCurrentDate_TIME_format());
            guardPicModel.setFlag("1");
            guardPicModel.setDUTY_STATUS("IN");
            guardPicModel.setPicture(pictureBlob);
            guardPicModel.setJson(createJsonForAttendancePicture(guardPicModel));
            CSApplicationHelper.application().getDatabaseInstance().attendancePicDao().insert(guardPicModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertFileToByte(File file) {
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
//        byte[] bArray = new byte[(int) file.length()];
        pictureBlob = new byte[(int) file.length()];
        try {
            fis = new FileInputStream(file);
            fis.read(pictureBlob);
//            fis.read(bArray);
            fis.close();
        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }

    }

    private void updateAttendancepictureWhenDutyOUT(DailyAttendanceDetail dailyAttendanceDetail) {
        try {
//            AttendanceGuardPicModel attendanceGuardPicModel = CSApplicationHelper.application().getDatabaseInstance().attendancePicDao().
//                    getAttendanceGuardPicModel(dailyAttendanceDetail.getID(), dailyAttendanceDetail.getREGNO());
            AttendanceGuardPicModel guardPicModel = new AttendanceGuardPicModel();


            guardPicModel.setId(String.valueOf(UUID.randomUUID()));
            guardPicModel.setAttendanceID(dailyAttendanceDetail.getID());
            guardPicModel.setRegNo(dailyAttendanceDetail.getREGNO());
            guardPicModel.setDateModified(DateUtils.getCurrentDate_TIME_format());
            guardPicModel.setFlag("1");
            guardPicModel.setDUTY_STATUS("OUT");
            guardPicModel.setPicture(pictureBlob);
            guardPicModel.setJson(createJsonForAttendancePicture(guardPicModel));

//            if (attendanceGuardPicModel == null) {
//                guardPicModel.setId(String.valueOf(UUID.randomUUID()));
//                guardPicModel.setAttendanceID(dailyAttendanceDetail.getID());
//                guardPicModel.setRegNo(dailyAttendanceDetail.getREGNO());
//                guardPicModel.setDateModified(DateUtils.getCurrentDate_TIME_format());
//                guardPicModel.setFlag("1");
//                guardPicModel.setDUTY_STATUS("OUT");
//                guardPicModel.setPicture(pictureBlob);
//                guardPicModel.setJson(createJsonForAttendancePicture(guardPicModel));
//
//            } else {
//                guardPicModel.setId(attendanceGuardPicModel.getId());
//                guardPicModel.setAttendanceID(attendanceGuardPicModel.getAttendanceID());
//                guardPicModel.setRegNo(attendanceGuardPicModel.getRegNo());
//                guardPicModel.setDateModified(DateUtils.getCurrentDate_TIME_format());
//                guardPicModel.setFlag("1");
//                guardPicModel.setDUTY_STATUS("OUT");
//                guardPicModel.setPicture(pictureBlob);
//                guardPicModel.setJson(createJsonForAttendancePicture(guardPicModel));
//
//            }
            CSApplicationHelper.application().getDatabaseInstance().attendancePicDao().insert(guardPicModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void otherUnitDutyEmployee_Mark_IN(Other_Duty_Mark_Model other_duty_mark_model) {
        try {

            attendanceUUID = String.valueOf(UUID.randomUUID());
            DailyAttendanceDetail dailyAttendanceDetail = new DailyAttendanceDetail();
            dailyAttendanceDetail.setREGNO(other_duty_mark_model.getEmployeDetailsModel().getREGNO());
            dailyAttendanceDetail.setID(attendanceUUID);
            dailyAttendanceDetail.setEMPRANK(other_duty_mark_model.getRosterDetail().getDUTYRANK());
            dailyAttendanceDetail.setUNITCODE(other_duty_mark_model.getRosterDetail().getUNITCODE());
            dailyAttendanceDetail.setViolationRemark("");
            dailyAttendanceDetail.setDUTYPOSTID(other_duty_mark_model.getRosterDetail().getDUTYPOSTID());
            dailyAttendanceDetail.setACTSTARTTIME(DateUtils.getCurrentDate_TIME_format());
            dailyAttendanceDetail.setFinal_Start_Time(DateUtils.getCurrentDate_TIME_format());
            dailyAttendanceDetail.setSHIFTSTARTTIME(other_duty_mark_model.getRosterDetail().getSHIFTSTARTTIME());
            dailyAttendanceDetail.setSHIFTENDTIME(other_duty_mark_model.getRosterDetail().getSHIFTENDTIME());

            if (pictureBlob == null) {
                dailyAttendanceDetail.setVOILATE("1");
                dailyAttendanceDetail.setViolationRemark(CFUtil.appRules(R.string.face_not_detect));
            } else {
                dailyAttendanceDetail.setVOILATE("0");
                dailyAttendanceDetail.setViolationRemark("");
                insertAttendancepictureWhenDutyIN(dailyAttendanceDetail);
            }
            /*if (DateUtils.compareTimeT1BeforeT2(other_duty_mark_model.getRosterDetail().getSHIFTSTARTTIME(),
                    other_duty_mark_model.getRosterDetail().getSHIFTENDTIME())) {
                Log.e("IF_SYSTEMTIME", "IFcompareTime");
                dailyAttendanceDetail.setSHIFTSTARTTIME(DateUtils.getContcat_Date_Time_second(other_duty_mark_model.getRosterDetail().getROSTERDATE(),
                        other_duty_mark_model.getRosterDetail().getSHIFTSTARTTIME()));

                dailyAttendanceDetail.setSHIFTENDTIME(DateUtils.getContcat_Date_Time_second(other_duty_mark_model.getRosterDetail().getROSTERDATE(),
                        other_duty_mark_model.getRosterDetail().getSHIFTENDTIME()));


            } else {
                Log.e("IF_SYSTEMTIME", "ELSEcompareTime");
                if (DateUtils.compareTimeT1BeforeT2(DateUtils.systemTime(), other_duty_mark_model.getRosterDetail().getSHIFTSTARTTIME())) {
                    Log.e("IF_SYSTEMTIME", "IF_COMPARE");
                    String tomorrowDate = DateUtils.getInstance().getNextDateCalender(other_duty_mark_model.getRosterDetail().getROSTERDATE());

                    dailyAttendanceDetail.setSHIFTENDTIME(DateUtils.getContcat_Date_Time_second(tomorrowDate,
                            other_duty_mark_model.getRosterDetail().getSHIFTENDTIME()));

                    dailyAttendanceDetail.setSHIFTSTARTTIME(DateUtils.getContcat_Date_Time_second(other_duty_mark_model.getRosterDetail().getROSTERDATE(),
                            other_duty_mark_model.getRosterDetail().getSHIFTSTARTTIME()));
                } else {
                    Log.e("IF_SYSTEMTIME", "ELSE_COMPARE");
                    dailyAttendanceDetail.setSHIFTSTARTTIME(DateUtils.getContcat_Date_Time_second(other_duty_mark_model.getRosterDetail().getROSTERDATE(),
                            other_duty_mark_model.getRosterDetail().getSHIFTSTARTTIME()));
                    String tomorrowDate = DateUtils.getInstance().getNextDateCalender(other_duty_mark_model.getRosterDetail().getROSTERDATE());

                    dailyAttendanceDetail.setSHIFTENDTIME(DateUtils.getContcat_Date_Time_second(tomorrowDate,
                            other_duty_mark_model.getRosterDetail().getSHIFTENDTIME()));
                }
            }*/
//        dailyAttendanceDetail.setATTENDANCEMODE(attendanceMode);
            dailyAttendanceDetail.setDuty_status(dutyStatus);
            dailyAttendanceDetail.setFlag("1");
            dailyAttendanceDetail.setDUTYRANK(other_duty_mark_model.getRosterDetail().getDUTYRANK());
            dailyAttendanceDetail.setSHIFTID(other_duty_mark_model.getRosterDetail().getSHIFTID());
            dailyAttendanceDetail.setJSON(setJsonForOtherUnitDutyEmployee(other_duty_mark_model,
                    "DUTY_IN", dailyAttendanceDetail, location));
            dailyAttendanceDetail.setDATE_MODIFIED(DateUtils.getCurrentDate_TIME_format());
            other_duty_mark_model.setDailyAttendanceDetail(dailyAttendanceDetail);
            CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().insert(dailyAttendanceDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveAttendanceRecordWhen_OUT(MapTableRosterAndShift mapTableRosterAndShift, DailyAttendanceDetail checkDailytable) {
        try {
            DailyAttendanceDetail dailyAttendanceDetail = new DailyAttendanceDetail();
            dailyAttendanceDetail.setREGNO(mapTableRosterAndShift.getREGNO());
            dailyAttendanceDetail.setID(checkDailytable.getID());
            dailyAttendanceDetail.setEMPRANK(mapTableRosterAndShift.getDUTY_RANK());
            dailyAttendanceDetail.setUNITCODE(mapTableRosterAndShift.getUNIT_CODE());
            dailyAttendanceDetail.setViolationRemark("");
            dailyAttendanceDetail.setDUTYPOSTID(mapTableRosterAndShift.getDUTY_POST_ID());

            dailyAttendanceDetail.setACTSTARTTIME(checkDailytable.getACTSTARTTIME());
            dailyAttendanceDetail.setACTENDTIME(DateUtils.getCurrentDate_TIME_format());
            dailyAttendanceDetail.setFinal_Start_Time(checkDailytable.getACTSTARTTIME());

            dailyAttendanceDetail.setSHIFTSTARTTIME(checkDailytable.getSHIFTSTARTTIME());
            dailyAttendanceDetail.setSHIFTENDTIME(checkDailytable.getSHIFTENDTIME());
            dailyAttendanceDetail.setDuty_status("DUTY_OUT");
            dailyAttendanceDetail.setEMPRANK(checkDailytable.getDUTYRANK());
            dailyAttendanceDetail.setFlag("1");
            dailyAttendanceDetail.setDUTYRANK(checkDailytable.getDUTYRANK());
            dailyAttendanceDetail.setSHIFTID(checkDailytable.getSHIFTID());


            dailyAttendanceDetail.setDATE_MODIFIED(DateUtils.getCurrentDate_TIME_format());


            if (pictureBlob == null) {
                dailyAttendanceDetail.setVOILATE("1");
                dailyAttendanceDetail.setViolationRemark(CFUtil.appRules(R.string.face_not_detect));
            } else {
                dailyAttendanceDetail.setVOILATE("0");
                dailyAttendanceDetail.setViolationRemark("");
                updateAttendancepictureWhenDutyOUT(dailyAttendanceDetail);
            }
            dailyAttendanceDetail.setJSON(setJson_mark_Attendance(mapTableRosterAndShift,
                    "DUTY_OUT", dailyAttendanceDetail, location));
            CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().insert(dailyAttendanceDetail);
            if (CSShearedPrefence.getUser().equalsIgnoreCase(mapTableRosterAndShift.getREGNO())) {

                Intent intent = new Intent(this, TrackingService.class);
                stopService(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void otherUnitDutyEmployee_Mark_OUT(Other_Duty_Mark_Model other_duty_mark_model, DailyAttendanceDetail checkDailytable) {
        try {
            DailyAttendanceDetail dailyAttendanceDetail = new DailyAttendanceDetail();
            dailyAttendanceDetail.setREGNO(checkDailytable.getREGNO());
            dailyAttendanceDetail.setID(checkDailytable.getID());
            dailyAttendanceDetail.setEMPRANK(checkDailytable.getDUTYRANK());
            dailyAttendanceDetail.setUNITCODE(checkDailytable.getUNITCODE());
            dailyAttendanceDetail.setViolationRemark("");
            dailyAttendanceDetail.setDUTYPOSTID(checkDailytable.getDUTYPOSTID());
            dailyAttendanceDetail.setACTSTARTTIME(checkDailytable.getACTSTARTTIME());
            dailyAttendanceDetail.setACTENDTIME(DateUtils.getCurrentDate_TIME_format());
            dailyAttendanceDetail.setFinal_Start_Time(checkDailytable.getACTSTARTTIME());
            dailyAttendanceDetail.setSHIFTSTARTTIME(checkDailytable.getSHIFTSTARTTIME());
            dailyAttendanceDetail.setSHIFTENDTIME(checkDailytable.getSHIFTENDTIME());
//        dailyAttendanceDetail.setATTENDANCEMODE(attendanceMode);
            dailyAttendanceDetail.setDuty_status("DUTY_OUT");
            dailyAttendanceDetail.setEMPRANK(checkDailytable.getDUTYRANK());
            dailyAttendanceDetail.setFlag("1");
            dailyAttendanceDetail.setDUTYRANK(checkDailytable.getDUTYRANK());
            dailyAttendanceDetail.setSHIFTID(checkDailytable.getSHIFTID());

            dailyAttendanceDetail.setDATE_MODIFIED(DateUtils.getCurrentDate_TIME_format());
//            other_duty_mark_model.setDailyAttendanceDetail(dailyAttendanceDetail);

            if (pictureBlob == null) {
                dailyAttendanceDetail.setVOILATE("1");
                dailyAttendanceDetail.setViolationRemark(CFUtil.appRules(R.string.face_not_detect));
            } else {
                dailyAttendanceDetail.setVOILATE("0");
                dailyAttendanceDetail.setViolationRemark("");
                updateAttendancepictureWhenDutyOUT(dailyAttendanceDetail);
            }
            dailyAttendanceDetail.setJSON(setJsonForOtherUnitDutyEmployee_OUT(other_duty_mark_model,
                    "DUTY_OUT", dailyAttendanceDetail, location));
            CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().insert(dailyAttendanceDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private File convertbyteToFile(byte[] bytes) {
        String FILEPATH = Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg";
        File file = new File(FILEPATH);
        try {
            // Initialize a pointer
            // in file using OutputStream
            OutputStream os = new FileOutputStream(file);
            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e);
        }
        return file;
    }

    private void compressImage(File file) {         //compression method

        ImageCompresssion.get(this)
                .load(file)
                .putGear(ImageCompresssion.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {

                        if (file != null) {
                            float fileSizeInBytes = file.length();
                            float fileSizeInKB = fileSizeInBytes / 1024;
                            Log.e("compressImage", "" + fileSizeInKB);
                            Log.e("compressImage", "" + fileSizeInBytes);
                            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                            convertFileToByte(file);
                            CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();
/*
                            Glide.with(SelfieActivity.this).load(file)
                                    .error(R.drawable.no_image_found)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)

                                    .into(((ImageView) findViewById(R.id.viewCaptureImageIV)));
*/

                        }
//                        if(csprogress!=null && csprogress.isShowing())
//                            csprogress.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();
//                        if(csprogress!=null && csprogress.isShowing())
//                            csprogress.cancel();
                        e.printStackTrace();
                    }
                }).launch();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        }
    }

    @Override
    protected void onPause() {
        mCameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            pictureBlob = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBackgroundHandler.getLooper().quitSafely();
            } else {
                mBackgroundHandler.getLooper().quit();
            }
            mBackgroundHandler = null;
            pictureBlob = null;
        }
    }


    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    private CameraView.Callback mCallback
            = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {

            pictureBlob = data;
//            getCurrentLocationWithOutLoader();
            float fileSizeInBytes = convertbyteToFile(pictureBlob).length();
            float fileSizeInKB = fileSizeInBytes / 1024;
            Log.e("compressImage", "BEFORE" + fileSizeInKB);
            Log.e("compressImage", "BEFORE" + fileSizeInBytes);


//                    new ImageCompressionLayout().execute( convertbyteToFile(pictureBlob).getAbsolutePath());
//                    Glide.with(SelfieActivity.this).load(pictureBlob)
//                            .error(R.drawable.no_image_found)
//                            .into(((ImageView) findViewById(R.id.viewCaptureImageIV)));
            findViewById(R.id.frame_layout).setVisibility(View.GONE);
            findViewById(R.id.bottomLinearView).setVisibility(View.GONE);
            findViewById(R.id.retakeSubmitLinear).setVisibility(View.VISIBLE);
            findViewById(R.id.submit_TV).setVisibility(View.VISIBLE);
            setViewVisibility(R.id.viewCaptureImageIV);
            Glide.with(SelfieActivity.this).load(data)
                    .error(R.drawable.no_image_found)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .into(((ImageView) findViewById(R.id.viewCaptureImageIV)));
            CSApplicationHelper.application().getInstance_ProgresssBar().show();
            getBackgroundHandler().post(new Runnable() {
                @Override
                public void run() {
                    compressImage(convertbyteToFile(pictureBlob));
               /*     File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "picture.jpg");
                    OutputStream os = null;
                    try {
                        os = new FileOutputStream(file);
                        os.write(data);
                        os.close();
                    } catch (IOException e) {
                        Log.w(TAG, "Cannot write to " + file, e);
                    } finally {
                        if (os != null) {
                            try {
                                os.close();
                            } catch (IOException e) {
                                // Ignore
                            }
                        }
                    }*/
                }
            });
        }

    };
}

