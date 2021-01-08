package com.sisindia.mysis.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.king.zxing.CaptureHelper;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;
import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.Model.Other_Duty_Mark_Model;
import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.SelfieActivity;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.dataBase.EmployeDetailsModel;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class QR_Code_Scan extends CSHeaderFragmentFragment implements OnCaptureCallback {
    private CaptureHelper mCaptureHelper;
    @BindView(R.id.viewfinderView)
    ViewfinderView viewfinderView;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.userNameTV)
    TextView userNameTV;
    @BindView(R.id.postRankNameTV)
    TextView postRankNameTV;
    @BindView(R.id.temporaryBTN)
    AppCompatButton temporaryBTN;
    @BindView(R.id.backPressIV)
    AppCompatImageView backPressIV;
    @BindView(R.id.userImageCV)
    CircleImageView userImageCV;
    private MapTableRosterAndShift mapTableRosterAndShift;
    private Other_Duty_Mark_Model other_duty_mark_model;
    //    private OtherUnitEmployeeDetailModel otherUnitEmployeeDetailModel;
    private String qrCodeResult = null, userName = null; // USERNAME USE FOR WHEN ROSTER NOT FOUND AND REQUIRED USERNAME MEAN REGNO, MOBILE FROM OTHER SEARCH
    private boolean checkScanResult = false;
    private EmployeDetailsModel employeeDetail;

    @Override
    public int layoutResource() {
        return R.layout.qr_code_scanner;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if (mCaptureHelper == null) {
                mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView);
            }
            mCaptureHelper.onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        setViewBundleReceivedBase();
        temporaryBTN.setVisibility(View.GONE);
        backPressIV.setOnClickListener(v -> csActivity.onBackPressed());
        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView);
        mCaptureHelper.setOnCaptureCallback(this);
        mCaptureHelper.onCreate();
        mCaptureHelper.continuousScan(true);
    }

    @SuppressLint("SetTextI18n")
    private void setViewBundleReceivedBase() {
        try {
            if (getArguments() != null) { // when come from OTHER DUTY SCREEN
                if (getArguments().getString("SCREEN_TAG").equalsIgnoreCase("OTHER_DUTY_SCREEN")) {
                    other_duty_mark_model = (Other_Duty_Mark_Model) getArguments().getSerializable("EMPLOYEEDETAIL");
                    if (other_duty_mark_model.getEmployeDetailsModel() != null) {
                        userName = getArguments().getString("USERNAME");
//                        showToast(userName);
                        setUserDetailOfOtherDutyEmployee();
                    }
                } else { // WHEN COME FROM SELF MARK  SCREEN
                    mapTableRosterAndShift = getArguments().getParcelable("SELF_MODEL");
                    setUserDetail();
                    userName = mapTableRosterAndShift.getREGNO();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (mCaptureHelper == null) {
                mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView);
            }
            mCaptureHelper.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (mCaptureHelper == null) {
                mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView);
            }
            mCaptureHelper.onPause();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mCaptureHelper != null) {
                mCaptureHelper.onDestroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dialog_onClick(int requestCode) {
        if (requestCode == 1222) { // ROSTER NOT FOUND AND CONTINUE TO MARK ATTENDANCE
            if (CSAppUtil.isNetworkNotAvailable(csActivity, false)) {
                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.no_internet));
            } else {
                checkScanResult = false;
                Bundle bundle = new Bundle();
                bundle.putString("REGNO", userName);
                bundle.putString("QRCODE", qrCodeResult.trim());
                bundle.putSerializable("OTHER_MARK_DUTY_MODEL", other_duty_mark_model);
                bundle.putString("DUTY_STATUS", getArguments().getString("DUTY_STATUS"));
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_QR_Code_Scan_to_otherDutyMarkAttendance, bundle);
            }

        } else if (requestCode == 12345) {
            csActivity.onBackPressed();
        } else if (requestCode == 12221) { // when cancel in dialog
            checkScanResult = false;
        } else if (requestCode == 12345678) { // when cancel in dialog
            checkScanResult = false;
        }
    }

    @Override
    public void dialog_onCancel() {
        checkScanResult = false;
    }

    @Override
    public boolean onResultCallback(String result) {
        qrCodeResult = CSAppUtil.getScanLocationQR(result);
        if (!checkScanResult) {
            checkScanResult = true;
            if (getArguments().getString("SCREEN_TAG").equalsIgnoreCase("OTHER_DUTY_SCREEN")) {

                // *************************WHNEN DAILY ATTENDANCE  RECORD FOUND*************************************************************
                if (other_duty_mark_model.getDailyAttendanceDetail() != null) {
                    if (getArguments().getString("DUTY_STATUS").equalsIgnoreCase("DUTY_OUT")) {
                        if (!other_duty_mark_model.getDailyAttendanceDetail().getQr_Code().equalsIgnoreCase(qrCodeResult.trim())) {
                            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.qr_code_mismatch), this,
                                    12345678, false);

                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("OTHERUNITEMPLOYEE", other_duty_mark_model);
                            bundle.putString("SCREEN_TAG", "OTHER_DUTY_PUNCH_SCREEN");
                            bundle.putString("DUTY_STATUS", getArguments().getString("DUTY_STATUS"));
                            Intent intent = new Intent(csActivity, SelfieActivity.class);
                            intent.putExtras(bundle);
                            csActivity.startActivity(intent);
                            CSAppUtil.openFragmentByNavigationClearStack(fragmentView, R.id.otherDutyEmp_SearchBy_QR_CodeByRegno);
                            csActivity.onBackPressed();
                            checkScanResult = false;
                        }
                    }
                }

                //*******************************WHNEN DAILY ATTENDANCE  RECORD NOT FOUND*****************************************************
                else {
                    if (other_duty_mark_model.getRosterDetail() != null) {
                        if (qrCodeResult.equalsIgnoreCase(other_duty_mark_model.getRosterDetail().getQRID())) {
                            if (getArguments().getString("DUTY_STATUS").equalsIgnoreCase("DUTY_OUT")) {
                                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.already_puch_duty_in), QR_Code_Scan.this,
                                        12221, false);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("OTHERUNITEMPLOYEE", other_duty_mark_model);
                                bundle.putString("SCREEN_TAG", "OTHER_DUTY_PUNCH_SCREEN");
                                bundle.putString("DUTY_STATUS", getArguments().getString("DUTY_STATUS"));
                                Intent intent = new Intent(csActivity, SelfieActivity.class);
                                intent.putExtras(bundle);
                                csActivity.startActivity(intent);
                                CSAppUtil.openFragmentByNavigationClearStack(fragmentView, R.id.otherDutyEmp_SearchBy_QR_CodeByRegno);
                                csActivity.onBackPressed();
                                checkScanResult = false;
                            }
                        } else {
                            if (getArguments().getString("DUTY_STATUS").equalsIgnoreCase("DUTY_OUT")) {
                                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.already_puch_duty_in), QR_Code_Scan.this,
                                        12221, false);
                            } else {
                                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.continue_to_mark_attendance), QR_Code_Scan.this,
                                        1222, true);
                            }
                        }
                    } else {
                        if (getArguments().getString("DUTY_STATUS").equalsIgnoreCase("DUTY_OUT")) {
                            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.already_puch_duty_in), QR_Code_Scan.this,
                                    12221, false);
                        } else {
                            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.continue_to_mark_attendance), QR_Code_Scan.this,
                                    1222, true);
                        }

                    }
                }
            } else {
                // ***************************COME FROM SELF  MARK SCREEN  FLOW******************************
                if (mapTableRosterAndShift != null) {
                    if (qrCodeResult.equalsIgnoreCase(mapTableRosterAndShift.getQR_CODE())) {

                        // **************************THIS CONDITION IS CHECK  EMPLOYEE MARK ATTENDANCE TODAY   **************************************

                        String dutyStatus = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                                checkSelfAttendanceToday(mapTableRosterAndShift.getREGNO(),
                                        mapTableRosterAndShift.getSHIFT_ID(), mapTableRosterAndShift.getUNIT_CODE(),
                                        DateUtils.getCurrentDate_TIME_format());
                        if (CSStringUtil.isEmptyStr(dutyStatus)) {
                            if (getArguments().getString("DUTY_STATUS").equalsIgnoreCase("DUTY_OUT")) {
                                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.NOt_MARK_DUTY_TODAY), QR_Code_Scan.this,
                                        12221, false);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("SCREEN_TAG", "SELF_MODEL");
                                bundle.putString("DUTY_STATUS", getArguments().getString("DUTY_STATUS"));
                                Intent intent = new Intent(csActivity, SelfieActivity.class);
                                intent.putExtras(bundle);
                                csActivity.startActivity(intent);
                                CSAppUtil.openFragmentByNavigationClearStack(fragmentView, R.id.otherDutyEmp_SearchBy_QR_CodeByRegno);
                                csActivity.onBackPressed();
                                checkScanResult = false;
                            }

                        } else if (dutyStatus.equalsIgnoreCase(getArguments().getString("DUTY_STATUS"))) {
                            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.already_puch_duty_in), this, 12345, false);

                        } else {

                            Bundle bundle = new Bundle();
                            bundle.putString("SCREEN_TAG", "SELF_MODEL");
                            bundle.putString("DUTY_STATUS", getArguments().getString("DUTY_STATUS"));
                            Intent intent = new Intent(csActivity, SelfieActivity.class);
                            intent.putExtras(bundle);
                            csActivity.startActivity(intent);
                            CSAppUtil.openFragmentByNavigationClearStack(fragmentView, R.id.otherDutyEmp_SearchBy_QR_CodeByRegno);
                            csActivity.onBackPressed();
                            checkScanResult = false;

                        }
                    } else {
                       /* CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.continue_to_mark_attendance), QR_Code_Scan.this,
                                1222, true);*/
                        CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.qr_code_mismatch), this,
                                12345678, false);

                    }
                } else {
                    CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.qr_code_mismatch), this,
                            12345678, false);

                }

            }

        }


        return true;
    }

    @SuppressLint("SetTextI18n")
    private void setUserDetail() {
        try {
            UserDetailModel userDetailModel = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao()
                    .getUserDetails(mapTableRosterAndShift.getREGNO());
            if (userDetailModel != null) {
                userNameTV.setText(userDetailModel.getName());
                Glide.with(this).load(userDetailModel.getProfile_Picture()).
                        error(R.drawable.no_image_found)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .into(userImageCV);
                postRankNameTV.setText(CSApplicationHelper.application().
                        getDatabaseInstance().serviceDAO().getSymbol(mapTableRosterAndShift.getDUTY_RANK()).toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUserDetailOfOtherDutyEmployee() {
        try {
            userNameTV.setText(other_duty_mark_model.getEmployeDetailsModel().getEMPNAME());
            postRankNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().getSymbol(
                    other_duty_mark_model.getEmployeDetailsModel().getRANK()));
            Glide.with(this).load(other_duty_mark_model.getEmployeDetailsModel().getPICTURE()).
                    error(R.drawable.no_image_found)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .into(userImageCV);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
