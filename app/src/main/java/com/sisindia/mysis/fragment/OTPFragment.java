package com.sisindia.mysis.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import com.sisindia.mysis.R;
import com.sisindia.mysis.ThemeHelper;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;
import com.sisindia.mysis.utils.PinEntryEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class OTPFragment extends CSHeaderFragmentFragment {
    @BindView(R.id.confirmBTN)
    Button confirmBTN;
    @BindView(R.id.otp_msgTV)
    TextView otp_msgTV;
    @BindView(R.id.oTP_ET)
    PinEntryEditText oTP_ET;
    @BindView(R.id.resendOTP)
    TextView resendOTP;
    @BindView(R.id.counter_LY)
    LinearLayout counter_LY;
    @BindView(R.id.counterTime_TV)
    TextView counterTime_TV;
    @BindView(R.id.main_layout)
    LinearLayout main_layout;

    @OnClick({R.id.confirmBTN, R.id.resendOTP})
    public void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.confirmBTN:
                CSAppUtil.closeKeyboard(csActivity, oTP_ET);
                validateAndScreenNavigation();
                break;
            case R.id.resendOTP:
                new Volley_Asynclass(csActivity, this, NetworkUtils.commonParameterFor_SignIN_By_MOB_REG("USERNAME", CSShearedPrefence.getUser()
                        , "223", "generateguardotp"), Constants.SIGN_URL, 1231, getResources().getString(R.string.please_wait));
                break;
        }
    }


    private boolean valiDate() {
        try {


            if (CSStringUtil.isEmptyView(oTP_ET)) {
                showToast(CSStringUtil.getString(R.string.valid_Number));
                return false;
            } else if (oTP_ET.getText().length() < 4) {
                showToast(CSStringUtil.getString(R.string.valid_OTP_digit));
                return false;
            } else if (!CSStringUtil.getTextFromView(oTP_ET).equals(CSShearedPrefence.getOTP())) {
                showToast(CSStringUtil.getString(R.string.valid_OTP));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void validateAndScreenNavigation() {
        try {
            if (valiDate()) {
                if (getArguments().getString("SCREEN").equalsIgnoreCase("FORGET_PIN")) {
                    new Volley_Asynclass(getContext(), this, NetworkUtils.update_User("changepin",
                            CSShearedPrefence.getUser(), CSShearedPrefence.getPassword(), getArguments().getString("PIN"), CSShearedPrefence.getDeviceToken())
                            , "ChangePassword", 1234);

                } else if (getArguments().getString("SCREEN").equalsIgnoreCase("FORGET_ACTION_FROM_VERIFY_SCREEN")) {
                    new Volley_Asynclass(getContext(), this, NetworkUtils.update_User("changepin",
                            CSShearedPrefence.getUser(), CSShearedPrefence.getPassword(), getArguments().getString("PIN"), CSShearedPrefence.getDeviceToken())
                            , "ChangePassword", 54321);

                } else {
                    CSAppUtil.closeKeyboard(csActivity, oTP_ET);
                    CSAppUtil.openFragmentByNavigation(fragmentView, R.id.action_OTPActivity_to_setPin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int layoutResource() {
        return R.layout.otp_screen;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        setViewBaseOnBundleReceived();
        if (CSShearedPrefence.isNightModeEnabled()) {

            main_layout.setBackground(null);

        }
    }

    @SuppressLint("SetTextI18n")
    private void setViewBaseOnBundleReceived() {
        try {
            if (getArguments().getString("SCREEN").equalsIgnoreCase("FORGET_PIN")) {
//                showToast(getArguments().getString("SCREEN"));
                otp_msgTV.setText(getResources().getString(R.string.otp_msg) + " " + CFUtil.maskPhoneNumberinStartFormat(CSApplicationHelper.application()
                        .getDatabaseInstance().user_detail_dao().getDetails().getPhone()));

            } else if (getArguments().getString("SCREEN").equalsIgnoreCase("FORGET_ACTION_FROM_VERIFY_SCREEN")) {
//                showToast(getArguments().getString("SCREEN"));
                otp_msgTV.setText(getResources().getString(R.string.otp_msg) + " " + CFUtil.maskPhoneNumberinStartFormat(CSApplicationHelper.application()
                        .getDatabaseInstance().user_detail_dao().getDetails().getPhone()));
            } else { // use for self login
                String phoneNumber = getArguments().getString("PHONE_NUMBER");
                otp_msgTV.setText(getResources().getString(R.string.otp_msg) + " " + CFUtil.maskPhoneNumberinStartFormat(phoneNumber));

            }
            edit_ActionListner();
            callCountDownMethod();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void edit_ActionListner() {
        try {

            if (oTP_ET != null) {
                oTP_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        show_Log_Data("onTextChanged", String.valueOf(s.toString().length()));
                        if (s.toString().length() == 4) {
                            confirmBTN.setBackgroundResource(R.drawable.background_border_app_bg);
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

    private void callCountDownMethod() {
        try {
            if (Integer.valueOf(CSShearedPrefence.getCounterTime()) > 0) {
                counter_LY.setVisibility(View.VISIBLE);
                new CountDownTimer(Integer.parseInt(CSShearedPrefence.getCounterTime()) * 1000,
                        1000) {// convert second to millisecond by 1000
                    @SuppressLint({"DefaultLocale", "SetTextI18n"})
                    @Override
                    public void onTick(long millisUntilFinished) {
                        resendOTP.setEnabled(false);
                        resendOTP.setAlpha(0.5f);

                        int seconds = (int) (millisUntilFinished / 1000);
                        int minutes = seconds / 60;
                        seconds = seconds % 60;
                        counterTime_TV.setText(String.format("%02d", minutes)
                                + ":" + String.format("%02d", seconds));
                    }

                    @Override
                    public void onFinish() {
                        resendOTP.setEnabled(true);
                        resendOTP.setAlpha(1.0f);
                        counter_LY.setVisibility(View.GONE);

                    }
                }.start();
            } else {
                counter_LY.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onGetResponse(String response, int requestCode) {
        if (requestCode == 1231) {
            if (response != null && !response.equals("")) {
                try {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").toLowerCase().equals("true")) {
                        jsonArray = jsonObject.getJSONArray("data");
                        CSShearedPrefence.saveOTP(jsonArray.getJSONObject(0).getString("OTP"));
                        CSShearedPrefence.counter_Time_OTP(jsonObject.getJSONArray("data").getJSONObject(0).getString("OTPExpireInSecond"));
                        callCountDownMethod();

                    } else {
                        showToast(jsonObject.getString("error"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1234) {
            show_Log_Data("PIN_CHANGE", response);
            try {
                JSONObject jsonObject;
                jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    CSShearedPrefence.setMpin(getArguments().getString("PIN"));
                    showDialog(CSStringUtil.getString(R.string.security_pin_change));
                } else {
                    showToast(jsonObject.getString("error"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == 54321) {
            show_Log_Data("PIN_CHANGE", response);
            try {
                JSONObject jsonObject;
                jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    CSShearedPrefence.setMpin(getArguments().getString("PIN"));
                    showDialog(CSStringUtil.getString(R.string.security_pin_change));

                } else {
                    showToast(jsonObject.getString("error"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showDialog(String msg) {
        Dialog dialog = new Dialog(csActivity, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(R.layout.dialog_for_pin_change_successfully);
        TextView text = dialog.findViewById(R.id.mobile_number);
        AppCompatImageView cancelImg = dialog.findViewById(R.id.cancelImg);
        text.setText(msg);
        cancelImg.setOnClickListener(v -> {
            if (getArguments().getString("SCREEN").equalsIgnoreCase("FORGET_ACTION_FROM_VERIFY_SCREEN")) {
                CSAppUtil.openFragmentByNavigationClearStack(fragmentView, R.id.changeAndForgetPin);
            } else {
                csActivity.onBackPressed();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }
}
