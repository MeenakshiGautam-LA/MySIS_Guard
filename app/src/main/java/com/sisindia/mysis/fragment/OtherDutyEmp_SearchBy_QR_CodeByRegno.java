package com.sisindia.mysis.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.king.zxing.CaptureHelper;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;
import com.sisindia.mysis.Model.Other_Duty_Mark_Model;
import com.sisindia.mysis.Model.RosterDetail;
import com.sisindia.mysis.R;
import com.sisindia.mysis.ThemeHelper;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass;
import com.sisindia.mysis.dataBase.EmployeDetailsModel;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.GetSiteDetail;
import com.sisindia.mysis.entity.ServiceInfoDetail;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.listener.TextWatcherListener;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.Custom_TextWatcher;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;

public class OtherDutyEmp_SearchBy_QR_CodeByRegno extends CSHeaderFragmentFragment implements
        OnCaptureCallback, TextWatcherListener {

    @BindView(R.id.searchBy_manuallyLY)
    LinearLayout searchBy_manuallyLY;
    @BindView(R.id.enterManually_Des_LY)
    LinearLayout enterManually_Des_LY;
    @BindView(R.id.searchbyQR_Scan_RL)
    RelativeLayout searchbyQR_Scan_RL;
    @BindView(R.id.scanQRCode_Des_LY)
    LinearLayout scanQRCode_Des_LY;
    @BindView(R.id.searchEmp_BTN)
    AppCompatButton searchEmp_BTN;
    @BindView(R.id.mobileET)
    AppCompatEditText mobileET;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    private CaptureHelper mCaptureHelper;
    @BindView(R.id.viewfinderView)
    ViewfinderView viewfinderView;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.backPressIV)
    AppCompatImageView backPressIV;
    @BindView(R.id.cancel_actionIV)
    AppCompatImageView cancel_actionIV;
    @BindView(R.id.regNoAction)
    TextView regNoAction;
    @BindView(R.id.mobileNoAction)
    TextView mobileNoAction;
    @BindView(R.id.disable_by_reg_or_MobileLY)
    LinearLayout disable_by_reg_or_MobileLY;
    @BindView(R.id.manuallyHeaderLY)
    LinearLayout manuallyHeaderLY;
    @BindView(R.id.scanByQr_CodeLY)
    LinearLayout scanByQr_CodeLY;
    @BindView(R.id.selfTV)
    TextView selfTV;
    private int manuallySelectionTypeValue = 1;
    private boolean checkScanResult = false;
    private String dutyStatus = null;
    private EmployeDetailsModel employeDetailsModel;
    //    private OtherUnitEmployeeDetailModel mapTableRosterAndShift;
    private Other_Duty_Mark_Model other_duty_mark_model;
    private String scanRegNo = null;

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        new Custom_TextWatcher(mobileET, this, R.id.mobileET);
        if (getArguments() != null) {
            dutyStatus = getArguments().getString("DUTY_STATUS");
        }
        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView);
        mCaptureHelper.setOnCaptureCallback(this);
        mCaptureHelper.onCreate();
        mCaptureHelper.continuousScan(true);
        backPressIV.setOnClickListener(v -> {
            CSAppUtil.closeKeyboard(csActivity, mobileET);
            csActivity.onBackPressed();
        });
        cancel_actionIV.setOnClickListener(v -> {
            CSAppUtil.closeKeyboard(csActivity, mobileET);
            csActivity.onBackPressed();
        });
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
    public int layoutResource() {
        return R.layout.otherduty_emp_search;
    }

    @OnClick({R.id.enterManually_Des_LY, R.id.scanQRCode_Des_LY, R.id.searchEmp_BTN, R.id.regNoAction,
            R.id.mobileNoAction, R.id.selfTV})
    public void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.enterManually_Des_LY:
                checkScanResult = false;
                scanRegNo = null;
                manuallySelectionTypeValue = 1;
                mobileET.getText().clear();
                animationOnmanuallyInput();
                searchBy_manuallyLY.setVisibility(View.VISIBLE);
                manuallyHeaderLY.setVisibility(View.VISIBLE);
                scanByQr_CodeLY.setVisibility(View.GONE);
                disable_by_reg_or_MobileLY.setVisibility(View.VISIBLE);
                scanQRCode_Des_LY.setVisibility(View.VISIBLE);
                searchbyQR_Scan_RL.setVisibility(View.GONE);
                enterManually_Des_LY.setVisibility(View.GONE);
                break;
            case R.id.scanQRCode_Des_LY:
                scanRegNo = null;
                manuallyHeaderLY.setVisibility(View.GONE);
                scanByQr_CodeLY.setVisibility(View.VISIBLE);
                checkScanResult = false;
                searchBy_manuallyLY.setVisibility(View.GONE);
                disable_by_reg_or_MobileLY.setVisibility(View.GONE);
                scanQRCode_Des_LY.setVisibility(View.GONE);
                searchbyQR_Scan_RL.setVisibility(View.VISIBLE);
                enterManually_Des_LY.setVisibility(View.VISIBLE);
                break;
            case R.id.regNoAction:
                scanRegNo = null;
                manuallySelectionTypeValue = 0;
                checkScanResult = false;
                animationOnmanuallyInput();
                textInputLayout.setHint(CSStringUtil.getString(R.string.enter_Registration_no));
                mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                mobileET.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                mobileET.getText().clear();
                mobileNoAction.setVisibility(View.VISIBLE);
                regNoAction.setVisibility(View.GONE);
                CSAppUtil.closeKeyboard(csActivity, mobileET);
                break;
            case R.id.mobileNoAction:
                scanRegNo = null;
                checkScanResult = false;
                manuallySelectionTypeValue = 1;
                animationOnmanuallyInput();
                textInputLayout.setHint(CSStringUtil.getString(R.string.enter_Contact_no));
                mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                mobileET.setInputType(InputType.TYPE_CLASS_NUMBER);
                mobileET.getText().clear();
                regNoAction.setVisibility(View.VISIBLE);
                mobileNoAction.setVisibility(View.GONE);
                CSAppUtil.closeKeyboard(csActivity, mobileET);
                break;
            case R.id.selfTV:
                if (manuallySelectionTypeValue == 0) { // mean set RegNo Option MODE
                    textInputLayout.setHint(CSStringUtil.getString(R.string.enter_Registration_no));
                    mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                    mobileET.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    mobileET.setText(CSShearedPrefence.getUser());
                } else {
                    textInputLayout.setHint(CSStringUtil.getString(R.string.enter_Contact_no));
                    mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                    mobileET.setInputType(InputType.TYPE_CLASS_NUMBER);
                    UserDetailModel userDetailModel = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                            getUserDetails(CSShearedPrefence.getUser());
                    if (userDetailModel != null) {
                        if (!CSStringUtil.isEmptyStr(userDetailModel.getPhone())) {
                            mobileET.setText(userDetailModel.getPhone());
                        }
                    }

                }
                break;
            case R.id.searchEmp_BTN:
                try {
                    if (CSAppUtil.isNetworkNotAvailable(csActivity, false)) {
                        CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.no_internet));
                    } else {

                        if (manuallySelectionTypeValue == 0) { // *****************IT USE  TO CHECK BY REG NO  BASES********************************
                            if (CSStringUtil.isEmptyStr(mobileET.getText().toString())
                                    || CSStringUtil.getTextFromView(mobileET).length() < 9) {
                                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.Registration_number_Valid), this, 111, false);
                            } else {
                                new Volley_Asynclass(csActivity, OtherDutyEmp_SearchBy_QR_CodeByRegno.this, NetworkUtils.
                                        otherEmployeeHeaderParameterByRegNo(
                                                DateUtils.getCurrentDate_format(), CSStringUtil.getTextFromView(mobileET), dutyStatus),
                                        Constants.OTHER_EMPLOYEE_DETAIL_TAG, 000);
                            }

                        } else {//**********************IT USE  TO CHECK BY MOBILE NO BASES**********************************************
                            if (CSStringUtil.isEmptyStr(mobileET.getText().toString())
                                    || CSStringUtil.getTextFromView(mobileET).length() < 10) {
                                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.mobile_number_Valid), this, 111, false);
                            } else {
                                new Volley_Asynclass(csActivity, OtherDutyEmp_SearchBy_QR_CodeByRegno.this, NetworkUtils.otherEmployeeHeaderParameterByMobileNo(
                                        DateUtils.getCurrentDate_format(), CSStringUtil.getTextFromView(mobileET), dutyStatus),
                                        Constants.OTHER_EMPLOYEE_DETAIL_TAG, 000);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showAlertDailogTo_NavigateNextScreen(EmployeDetailsModel employeDetailsModel) {
        AlertDialog.Builder ad = new AlertDialog.Builder(csActivity, R.style.DialogBox);
        LayoutInflater inflater = csActivity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dailog_before_navigate_in_qrcode_scan, null);
        ad.setView(dialogView);

        final TextView employeeNameTV = dialogView.findViewById(R.id.employeeNameTV);

        final TextView regNoTV = dialogView.findViewById(R.id.regNoTV);
        final Button confirmBTN = dialogView.findViewById(R.id.confirmBTN);
        final TextView cancelBTN = dialogView.findViewById(R.id.cancelBTN);
        if (employeDetailsModel != null) {
            employeeNameTV.setText(employeDetailsModel.getEMPNAME());
            regNoTV.setText(employeDetailsModel.getREGNO());
        }

        final AlertDialog alertDialog = ad.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                alertDialog.dismiss();
            }
            return true;
        });

        cancelBTN.setOnClickListener(v -> {
            checkScanResult = false;
            if (alertDialog != null)
                alertDialog.dismiss();
        });
        confirmBTN.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("DUTY_STATUS", dutyStatus);
            bundle.putString("SCREEN_TAG", "OTHER_DUTY_SCREEN");
            bundle.putString("USERNAME", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
            bundle.putSerializable("EMPLOYEEDETAIL", other_duty_mark_model);
            CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDutyEmp_SearchBy_QR_CodeByRegno_to_QR_Code_Scan, bundle);
            mobileET.getText().clear();
            checkScanResult = false;
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    @Override
    public void dialog_onClick(int requestCode) {
        if (requestCode == 12345) {
            checkScanResult = false;
        } else if (requestCode == 12221) {
            checkScanResult = false;
        } else if (requestCode == 04321) {
            checkScanResult = false;
        }
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }


    @Override
    public void onGetResponse(String response, int requestCode) {
        if (requestCode == 1234) {// IMAGE GET OF EMPLOYEE WHEN ENTER REG OR MOBILE NO
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray;
                /*if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    jsonArray = jsonObject.getJSONArray("EmployeePicture");
                    other_duty_mark_model.getEmployeDetailsModel().setPICTURE(getImageBYTE(jsonArray.
                            getJSONObject(0).getString("PICTURE")));

                }*/
                if (other_duty_mark_model.getDailyAttendanceDetail() != null) {
                    if (other_duty_mark_model.getDailyAttendanceDetail().getDuty_status().equalsIgnoreCase(getArguments().getString("DUTY_STATUS"))) {
                        CSDialogUtil.showAlertDialogMark_Attendance_Other_Already(csActivity, other_duty_mark_model.getDailyAttendanceDetail(), this,
                                04321);

                    } else {
                        if (CSStringUtil.isEmptyStr(scanRegNo)) { // THIS CONDITION IS USE TO SHOW ALERT DIALOG WHEN USE QRCODE METHOD
                            Bundle bundle = new Bundle();
                            bundle.putString("DUTY_STATUS", dutyStatus);
                            bundle.putString("SCREEN_TAG", "OTHER_DUTY_SCREEN");
                            bundle.putString("USERNAME", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
                            bundle.putSerializable("EMPLOYEEDETAIL", other_duty_mark_model);
                            CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDutyEmp_SearchBy_QR_CodeByRegno_to_QR_Code_Scan, bundle);
                            mobileET.getText().clear();
                        } else {
                            showAlertDailogTo_NavigateNextScreen(other_duty_mark_model.getEmployeDetailsModel());
                        }
                    }
                } else {
                    if (getArguments().getString("DUTY_STATUS").equalsIgnoreCase("DUTY_OUT")) {
                        CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.already_puch_duty_in),
                                OtherDutyEmp_SearchBy_QR_CodeByRegno.this, 12221, false);
                    } else {
                        if (CSStringUtil.isEmptyStr(scanRegNo)) { // THIS CONDITION IS USE TO SHOW ALERT DIALOG WHEN USE QRCODE METHOD
                            Bundle bundle = new Bundle();
                            bundle.putString("DUTY_STATUS", dutyStatus);
                            bundle.putString("SCREEN_TAG", "OTHER_DUTY_SCREEN");
                            bundle.putString("USERNAME", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
                            bundle.putSerializable("EMPLOYEEDETAIL", other_duty_mark_model);
                            CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDutyEmp_SearchBy_QR_CodeByRegno_to_QR_Code_Scan, bundle);
                            mobileET.getText().clear();
                        } else {
                            showAlertDailogTo_NavigateNextScreen(other_duty_mark_model.getEmployeDetailsModel());
                        }
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            parseOtherDetail(response);
        }
    }

    public byte[] getImageBYTE(String base64ImageData) {
        FileOutputStream fos = null;
        byte[] decodedString = null;
        try {
            if (base64ImageData != null) {
                fos = csActivity.getApplicationContext().openFileOutput("imageName.png", Context.MODE_PRIVATE);
                decodedString = android.util.Base64.decode(base64ImageData, android.util.Base64.DEFAULT);
                fos.write(decodedString);
                fos.flush();
                fos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos = null;
            }
        }
        return decodedString;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCaptureHelper == null) {
            mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView);

        }
        mCaptureHelper.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCaptureHelper == null) {
            mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView);
        }
        mCaptureHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCaptureHelper != null) {
            mCaptureHelper.onDestroy();
        }
    }

    @Override
    public boolean onResultCallback(String result) {

        String splitChar = null;
        if (!checkScanResult) {
            checkScanResult = true;
            if (result != null) {
                if (result.contains("\r\n")) {
                    splitChar = "\r\n";
                } else {
                    splitChar = "\n";
                }

                if (result.split(splitChar).length > 1) {

                    scanRegNo = result.split(splitChar)[1].toUpperCase();

//                    Toast.makeText(csActivity, scanRegNo, Toast.LENGTH_LONG).show();
                    employeDetailsModel = split_RegNo_by_SCAN(result.split(splitChar)[1], CSShearedPrefence.getUnitcode());
                    if (employeDetailsModel != null) {

                        new Volley_Asynclass(csActivity, OtherDutyEmp_SearchBy_QR_CodeByRegno.this, NetworkUtils.
                                otherEmployeeHeaderParameterByRegNo(
                                        DateUtils.getCurrentDate_format(), scanRegNo, dutyStatus),
                                Constants.OTHER_EMPLOYEE_DETAIL_TAG, 000);
                    } else {
                        if (CSAppUtil.isNetworkAvailable(csActivity, false)) {
                            new Volley_Asynclass(csActivity, OtherDutyEmp_SearchBy_QR_CodeByRegno.this, NetworkUtils.
                                    otherEmployeeHeaderParameterByRegNo(
                                            DateUtils.getCurrentDate_format(), scanRegNo, dutyStatus),
                                    Constants.OTHER_EMPLOYEE_DETAIL_TAG, 000);
                        }
                    }
                }
            } else {
                Toast.makeText(csActivity, "No result found", Toast.LENGTH_LONG).show();
            }

        }
        return true;
    }

    public static EmployeDetailsModel split_RegNo_by_SCAN(String regNO, String unitcode) {
        EmployeDetailsModel employeDetailsModel = CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().
                checkRegNO(regNO.toUpperCase(), unitcode);
        return employeDetailsModel;
    }

    private void animationOnmanuallyInput() {
        Animation animation1 = AnimationUtils.loadAnimation(csActivity, R.anim.blink_anim);
        searchBy_manuallyLY.startAnimation(animation1);
    }

    @Override
    public void onTextChangeMethod(String value, int viewId) {
        try {

            if (manuallySelectionTypeValue == 1) {
                if (value.length() == 10) {
                    CSAppUtil.closeKeyboard(csActivity, mobileET);
                    searchEmp_BTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                    searchEmp_BTN.setTextColor(getResources().getColor(R.color.white));
                } else {
                    searchEmp_BTN.setBackgroundResource(R.drawable.next_button_gray_background);
                    searchEmp_BTN.setTextColor(getResources().getColor(R.color.light_black));
                }
            } else if (value.length() == 9) {
                CSAppUtil.closeKeyboard(csActivity, mobileET);
                searchEmp_BTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                searchEmp_BTN.setTextColor(getResources().getColor(R.color.white));

            } else {
                searchEmp_BTN.setBackgroundResource(R.drawable.next_button_gray_background);
                searchEmp_BTN.setTextColor(getResources().getColor(R.color.light_black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseOtherDetail(String response) {
        try {
//            CSApplicationHelper.application().getInstance_ProgresssBar().show();
            EmployeDetailsModel employeeDetail = null;
            DailyAttendanceDetail dailyAttendanceDetail = null;
            RosterDetail rosterDetail;
            GetSiteDetail getSiteDetail;
            other_duty_mark_model = new Other_Duty_Mark_Model();
            JSONObject jsonObject, dataJsonObject;
            JSONArray EmnployeeJsonArray = null, attendanceJsonArray = null, rosterDetailJsonArray = null, unitDetailJsonArray = null;
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                dataJsonObject = jsonObject.getJSONObject("data");
                if (dataJsonObject.has("EmployeeDetail")) {
                    EmnployeeJsonArray = dataJsonObject.getJSONArray("EmployeeDetail");
                }
                if (dataJsonObject.has("RosterDetail")) {
                    rosterDetailJsonArray = dataJsonObject.getJSONArray("RosterDetail");
                }
                if (dataJsonObject.has("AttendanceDetail")) {
                    attendanceJsonArray = dataJsonObject.getJSONArray("AttendanceDetail");
                }
                if (dataJsonObject.has("EmployeeDetail")) {
                    unitDetailJsonArray = dataJsonObject.getJSONArray("UnitDetail");
                }
                if (EmnployeeJsonArray.length() > 0) {
                    Type listType = new TypeToken<EmployeDetailsModel>() {
                    }.getType();
                    employeeDetail = new Gson().fromJson(EmnployeeJsonArray.getJSONObject(0).toString(), listType);
                    show_Log_Data("EmployeeDetail", employeeDetail.getREGNO());
                    other_duty_mark_model.setEmployeDetailsModel(employeeDetail);
                    if (unitDetailJsonArray.length() > 0) {
                        Type listType3 = new TypeToken<GetSiteDetail>() {
                        }.getType();
                        getSiteDetail = new Gson().fromJson(unitDetailJsonArray.getJSONObject(0).toString(), listType3);
                        other_duty_mark_model.setSiteDetail(getSiteDetail);
                    }
                    if (attendanceJsonArray.length() > 0) {
                        Type listType1 = new TypeToken<DailyAttendanceDetail>() {
                        }.getType();
                        dailyAttendanceDetail = new Gson().fromJson(attendanceJsonArray.getJSONObject(0).toString(), listType1);
                        show_Log_Data("dailyAttendanceDetail", dailyAttendanceDetail.getREGNO());
                        other_duty_mark_model.setDailyAttendanceDetail(dailyAttendanceDetail);
                    }

                    if (rosterDetailJsonArray.length() > 0) {
                        Type listType2 = new TypeToken<RosterDetail>() {
                        }.getType();
                        rosterDetail = new Gson().fromJson(rosterDetailJsonArray.getJSONObject(0).toString(), listType2);
                        show_Log_Data("rosterModel", rosterDetail.getREGNO());
                        other_duty_mark_model.setRosterDetail(rosterDetail);
                    }
                    confirmGuard_Profile(other_duty_mark_model.getEmployeDetailsModel());
              /*      String tag = Constants.GET_EMPLOYEE_PICTURE + "?" + "RegNo=" + other_duty_mark_model.getEmployeDetailsModel().getREGNO();
                    new Volley_Asynclass(csActivity, this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                            CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), tag, 1234);*/

                 /*   if (other_duty_mark_model.getDailyAttendanceDetail() != null) {
                        if (other_duty_mark_model.getDailyAttendanceDetail().getDuty_status().equalsIgnoreCase(getArguments().getString("DUTY_STATUS"))) {
                            CSDialogUtil.showAlertDialogMark_Attendance_Other_Already(csActivity, other_duty_mark_model.getDailyAttendanceDetail(),this,
                                    04321);

                        } else {
                            if (CSStringUtil.isEmptyStr(scanRegNo)) { // THIS CONDITION IS USE TO SHOW ALERT DIALOG WHEN USE QRCODE METHOD
                                Bundle bundle = new Bundle();
                                bundle.putString("DUTY_STATUS", dutyStatus);
                                bundle.putString("SCREEN_TAG", "OTHER_DUTY_SCREEN");
                                bundle.putString("USERNAME", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
                                bundle.putSerializable("EMPLOYEEDETAIL", other_duty_mark_model);
                                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDutyEmp_SearchBy_QR_CodeByRegno_to_QR_Code_Scan, bundle);
                                mobileET.getText().clear();
                            } else {
                                showAlertDailogTo_NavigateNextScreen(other_duty_mark_model.getEmployeDetailsModel());
                            }
                        }
                    } else {
                        if (getArguments().getString("DUTY_STATUS").equalsIgnoreCase("DUTY_OUT")) {
                            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.already_puch_duty_in),
                                    OtherDutyEmp_SearchBy_QR_CodeByRegno.this, 12221, false);
                        } else {
                            if (CSStringUtil.isEmptyStr(scanRegNo)) { // THIS CONDITION IS USE TO SHOW ALERT DIALOG WHEN USE QRCODE METHOD
                                Bundle bundle = new Bundle();
                                bundle.putString("DUTY_STATUS", dutyStatus);
                                bundle.putString("SCREEN_TAG", "OTHER_DUTY_SCREEN");
                                bundle.putString("USERNAME", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
                                bundle.putSerializable("EMPLOYEEDETAIL", other_duty_mark_model);
                                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDutyEmp_SearchBy_QR_CodeByRegno_to_QR_Code_Scan, bundle);
                                mobileET.getText().clear();
                            } else {
                                showAlertDailogTo_NavigateNextScreen(other_duty_mark_model.getEmployeDetailsModel());
                            }
                        }


                    }*/

                } else {
                    CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.employee_detail_not_found));
                }

            } else {
                    CSDialogUtil.showInfoDialog(csActivity, jsonObject.getString("error"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmGuard_Profile(EmployeDetailsModel employeDetailsModel) {
        Dialog dialog = new Dialog(CSApplicationHelper.application().getActivity(), android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm_guard_profile);
        TextView userNameTV = dialog.findViewById(R.id.userNameTV);
        TextView duty_symbol_TV = dialog.findViewById(R.id.duty_symbol_TV);
        TextView postNameTV = dialog.findViewById(R.id.postNameTV);
        AppCompatImageView cancelImg = dialog.findViewById(R.id.cancel_IV);
        AppCompatImageView userIV = dialog.findViewById(R.id.userIV);
        Button cancelBTN = dialog.findViewById(R.id.cancelBTN);
        Button confirmBTN = dialog.findViewById(R.id.confirmBTN);
        AppCompatImageView cancel_IV = dialog.findViewById(R.id.cancel_IV);
        userNameTV.setText(employeDetailsModel.getEMPNAME());
        String symbol = CSApplicationHelper.application().getDatabaseInstance().serviceDAO().getSymbol(employeDetailsModel.getRANK());
        duty_symbol_TV.setText(symbol);
        Glide.with(csActivity).load(employeDetailsModel.getPICTURE()).placeholder(R.drawable.no_image_found).into(userIV);
        cancelImg.setOnClickListener(view -> dialog.dismiss());
        cancelBTN.setOnClickListener(view -> {
            dialog.dismiss();
        });
        if (CSShearedPrefence.isNightModeEnabled()) {
            Drawable mIcon = ContextCompat.getDrawable(getActivity(), R.drawable.icon_cross);
            cancel_IV.setColorFilter(ContextCompat.getColor(csActivity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);

            ThemeHelper.applyTheme(ThemeHelper.DARK_MODE);

        } else {
            ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE);
        }
        confirmBTN.setOnClickListener(view -> {
            if (other_duty_mark_model.getDailyAttendanceDetail() != null) {
                if (other_duty_mark_model.getDailyAttendanceDetail().getDuty_status().equalsIgnoreCase(getArguments().getString("DUTY_STATUS"))) {
                    CSDialogUtil.showAlertDialogMark_Attendance_Other_Already(csActivity, other_duty_mark_model.getDailyAttendanceDetail(), this,
                            04321);

                } else {
                    if (CSStringUtil.isEmptyStr(scanRegNo)) { // THIS CONDITION IS USE TO SHOW ALERT DIALOG WHEN USE QRCODE METHOD
                        Bundle bundle = new Bundle();
                        bundle.putString("DUTY_STATUS", dutyStatus);
                        bundle.putString("SCREEN_TAG", "OTHER_DUTY_SCREEN");
                        bundle.putString("USERNAME", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
                        bundle.putSerializable("EMPLOYEEDETAIL", other_duty_mark_model);
                        CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDutyEmp_SearchBy_QR_CodeByRegno_to_QR_Code_Scan, bundle);
                        mobileET.getText().clear();
                    } else {
                        showAlertDailogTo_NavigateNextScreen(other_duty_mark_model.getEmployeDetailsModel());
                    }
                }
            } else {
                if (getArguments().getString("DUTY_STATUS").equalsIgnoreCase("DUTY_OUT")) {
                    CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.already_puch_duty_in),
                            OtherDutyEmp_SearchBy_QR_CodeByRegno.this, 12221, false);
                } else {
                    if (CSStringUtil.isEmptyStr(scanRegNo)) { // THIS CONDITION IS USE TO SHOW ALERT DIALOG WHEN USE QRCODE METHOD
                        Bundle bundle = new Bundle();
                        bundle.putString("DUTY_STATUS", dutyStatus);
                        bundle.putString("SCREEN_TAG", "OTHER_DUTY_SCREEN");
                        bundle.putString("USERNAME", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
                        bundle.putSerializable("EMPLOYEEDETAIL", other_duty_mark_model);
                        CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDutyEmp_SearchBy_QR_CodeByRegno_to_QR_Code_Scan, bundle);
                        mobileET.getText().clear();
                    } else {
                        showAlertDailogTo_NavigateNextScreen(other_duty_mark_model.getEmployeDetailsModel());
                    }
                }


            }
            dialog.dismiss();
        });

        dialog.show();
    }
}
