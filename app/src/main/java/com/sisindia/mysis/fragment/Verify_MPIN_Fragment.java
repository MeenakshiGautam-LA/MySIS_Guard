package com.sisindia.mysis.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.sisindia.mysis.ApiCalling_Class;
import com.sisindia.mysis.GetWorkManagers.EmployeeDetailWorker;
import com.sisindia.mysis.GetWorkManagers.Flash_Message_Worker;
import com.sisindia.mysis.GetWorkManagers.GetAttendanceWork;
import com.sisindia.mysis.GetWorkManagers.GetRosterWorker;
import com.sisindia.mysis.PostWorkerManager.PostAttendanceWork;
import com.sisindia.mysis.PostWorkerManager.Post_MarkAttendance_Selfie_Worker;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.RosterModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.utils.PinEntryEditText;
import com.sisindia.mysis.view.CSProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Verify_MPIN_Fragment extends CSHeaderFragmentFragment {
    @BindView(R.id.confirmBTN)
    Button confirmBTN;
    @BindView(R.id.pinET)
    PinEntryEditText pinET;
    @BindView(R.id.headingTV)
    TextView headingTV;
    @BindView(R.id.forgetPinTV)
    TextView forgetPinTV;
    @BindView(R.id.main_layout)
    LinearLayout main_layout;
    private List<RosterModel> rosterModelList;
    private ArrayList<DailyAttendanceDetail> dailyAttendanceDetailArrayList;

    @Override
    public int layoutResource() {
        return R.layout.set_pin;
    }

    private CSProgressBar csProgressBar;

    @OnClick({R.id.confirmBTN, R.id.forgetPinTV})
    public void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.confirmBTN:
                validateAndScreenNavigation();
                break;
            case R.id.forgetPinTV:
                pinET.getText().clear();
                Bundle bundle = new Bundle();
                bundle.putString("SCREEN", "FORGET_ACTION_FROM_VERIFY_SCREEN");
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.changeAndForgetPin, bundle);
                break;
        }
    }

    private boolean valiDate() {
        if (CSStringUtil.isEmptyView(pinET)) {
            showToast(CSStringUtil.getString(R.string.valid_Number));
            return false;
        } else if (pinET.getText().length() < 4) {
            showToast(CSStringUtil.getString(R.string.valid_OTP_digit));
            return false;
        } else if (!CSStringUtil.getTextFromView(pinET).equals(CSShearedPrefence.getMpin())) {
            showToast(CSStringUtil.getString(R.string.not_match));
            return false;
        }

        return true;
    }

    private void validateAndScreenNavigation() {
        if (valiDate()) {
            if (csProgressBar == null)
                csProgressBar = new CSProgressBar(CSApplicationHelper.application().getActivity());
            csProgressBar.setMessage(CSApplicationHelper.application().getActivity().getResources().getString(R.string.please_wait));
            csProgressBar.show();
            CSAppUtil.closeKeyboard(csActivity, pinET);
            CSShearedPrefence.setMpin(CSStringUtil.getTextFromView(pinET));
            CSApplicationHelper.application().getInstance_ProgresssBar().show();
            if (!CSAppUtil.isNetworkNotAvailable(csActivity, false)) {
//                WorkManager.getInstance().cancelAllWork();
//                callGetRosterDetailWorker();

                ApiCalling_Class apiCalling_class = new ApiCalling_Class(csActivity);
                apiCalling_class.calling_Api_On_verifyPin_();
                hold_for_the_setUP();
            } else {
                hold_for_the_setUP();
            }
        }
    }

    private void callGetRosterDetailWorker() {
        OneTimeWorkRequest getRosterWorker = new OneTimeWorkRequest.Builder(GetRosterWorker.class).build();
        OneTimeWorkRequest getAttendanceWork = new OneTimeWorkRequest.Builder(GetAttendanceWork.class).build();
        OneTimeWorkRequest postAttendanceWork = new OneTimeWorkRequest.Builder(PostAttendanceWork.class).build();
        OneTimeWorkRequest postAttendanceWorkSelfie = new OneTimeWorkRequest.Builder(Post_MarkAttendance_Selfie_Worker.class).build();
        OneTimeWorkRequest getFlashMsg = new OneTimeWorkRequest.Builder(Flash_Message_Worker.class).build();
        OneTimeWorkRequest getEmployeeDetailsWorker = new OneTimeWorkRequest.Builder(EmployeeDetailWorker.class).build();
        WorkManager.getInstance()
                .beginWith(postAttendanceWork)
                .then(getRosterWorker)
                .then(getAttendanceWork)
                .then(postAttendanceWorkSelfie)
                .then(getEmployeeDetailsWorker)
                .then(getFlashMsg)
                .enqueue();
    }

    public void hold_for_the_setUP() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                csProgressBar.dismiss();
                CSShearedPrefence.setJugaaar("");
                CSAppUtil.openFragmentByNavigation(fragmentView, R.id.action_MPIN_Fragment2_to_markAttendance);
            }
        }, 5000);

    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        headingTV.setText(CSStringUtil.getString(R.string.Enter_pin));
        edit_ActionListner();
        if (CSShearedPrefence.isNightModeEnabled()) {

            main_layout.setBackground(null);
        }
      /*  if (CSShearedPrefence.isNightModeEnabled()) {
            previous_month_iv.setImageDrawable(mIcon);
            previous_month_iv.setRotation(180.0f);
            next_month_iv.setImageDrawable(mIcon);
            calender_main_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }*/
        forgetPinTV.setVisibility(View.VISIBLE);
        pinET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateAndScreenNavigation();
                }
                return true;
            }
        });
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    private void edit_ActionListner() {
        try {

            if (pinET != null) {
                pinET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().length() == 4) {
                            CSAppUtil.closeKeyboard(csActivity, confirmBTN);
                            confirmBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                            confirmBTN.setTextColor(getResources().getColor(R.color.white));
                        } else {
                            confirmBTN.setBackgroundResource(R.drawable.next_button_gray_background);
                            confirmBTN.setTextColor(getResources().getColor(R.color.upper_light_black));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
