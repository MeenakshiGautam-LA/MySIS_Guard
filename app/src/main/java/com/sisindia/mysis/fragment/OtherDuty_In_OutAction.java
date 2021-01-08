package com.sisindia.mysis.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.sisindia.mysis.ApiCalling_Class;
import com.sisindia.mysis.PostWorkerManager.PostAttendanceWork;
import com.sisindia.mysis.PostWorkerManager.Post_MarkAttendance_Selfie_Worker;
import com.sisindia.mysis.R;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;

import butterknife.BindView;
import butterknife.OnClick;

public class
OtherDuty_In_OutAction extends CSHeaderFragmentFragment {
    @BindView(R.id.otherDuty_IN_BTN)
    AppCompatButton otherDuty_IN_BTN;
    @BindView(R.id.otherDuty_Out_BTN)
    AppCompatButton otherDuty_Out_BTN;
    @BindView(R.id.backPressIV)
    AppCompatImageView backPressIV;

    @Override
    public int layoutResource() {
        return R.layout.other_duty_in_out_action;
    }

    @OnClick({R.id.otherDuty_Out_BTN, R.id.otherDuty_IN_BTN, R.id.backPressIV})
    public void onClickMethod(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.otherDuty_IN_BTN:
                ApiCalling_Class calling_class=new ApiCalling_Class(csActivity);
                calling_class.push_Attendance_detail();
//                call_PostAttendanceData();
                bundle.putString("DUTY_STATUS", "DUTY_IN");
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDuty_In_Out_to_otherDutyEmp_SearchBy_QR_CodeByRegno, bundle);
                break;
            case R.id.otherDuty_Out_BTN:
//                call_PostAttendanceData();
                ApiCalling_Class calling_class1=new ApiCalling_Class(csActivity);
                calling_class1.push_Attendance_detail();
                bundle.putString("DUTY_STATUS", "DUTY_OUT");
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_otherDuty_In_Out_to_otherDutyEmp_SearchBy_QR_CodeByRegno, bundle);

                break;
            case R.id.backPressIV:

                csActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();

        Drawable mIcon = ContextCompat.getDrawable(getActivity(), R.drawable.white_arrow);
        if (CSShearedPrefence.isNightModeEnabled()) {
            backPressIV.setImageDrawable(mIcon);


        } else {
            mIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), PorterDuff.Mode.MULTIPLY);
            backPressIV.setImageDrawable(mIcon);

        }

    }
    private void call_PostAttendanceData() {
        OneTimeWorkRequest postAttendanceWork = new OneTimeWorkRequest.Builder(PostAttendanceWork.class).build();
//        OneTimeWorkRequest postAttendanceWorkSelfie = new OneTimeWorkRequest.Builder(Post_MarkAttendance_Selfie_Worker.class).build();
        WorkManager.getInstance()
                .beginWith(postAttendanceWork)
//                .then(postAttendanceWorkSelfie)
                .enqueue();
        //triggerSync();
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

}
