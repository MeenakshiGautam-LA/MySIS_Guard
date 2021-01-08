package com.sisindia.mysis.fragment;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;
import com.sisindia.mysis.utils.PinEntryEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeAndForgetPin extends CSHeaderFragmentFragment {
    @BindView(R.id.txt_pin_entry1)
    PinEntryEditText pinET;
    @BindView(R.id.submit_pin_btn)
    AppCompatButton submitBTN;
    @BindView(R.id.headingTV)
    TextView headingTV;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitleTV;
    @BindView(R.id.belowHeaderTextTV)
    TextView belowHeaderTextTV;
    @BindView(R.id.forget_current_pin)
    TextView forgetPIN_TV;
    private int changeViewAction = 0;
    @BindView(R.id.backPressLY)
    LinearLayout backPressLY;
    @BindView(R.id.card_viewLY)
    LinearLayout card_viewLY;
    @BindView(R.id.backPressIV)
    ImageView backPressIV;
    @BindView(R.id.main_layout)
    RelativeLayout main_layout;

    @Override
    public int layoutResource() {
        return R.layout.activity_change_pin;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        setViewBaseOnBundleReceived();
        edit_ActionListner();
        Drawable mIcon= ContextCompat.getDrawable(getActivity(), R.drawable.white_arrow);

        if(CSShearedPrefence.isNightModeEnabled()){
            backPressIV.setImageDrawable(mIcon);
            main_layout.setBackground(null);

        }

    }

    private void setViewBaseOnBundleReceived() {
        try {
            if (getArguments().getString("SCREEN").equalsIgnoreCase("FORGET_ACTION_FROM_VERIFY_SCREEN")) {
                changeViewAction = 4;
                forgetPIN_TV.setVisibility(View.GONE);
                toolbarTitleTV.setText(CSStringUtil.getString(R.string.forget_pin));
                headingTV.setText(CSStringUtil.getString(R.string.enter_new_pin));
                belowHeaderTextTV.setText(CSStringUtil.getString(R.string.enter_below_message_set_pin));
                submitBTN.setText(CSStringUtil.getString(R.string.confirm));
                pinET.getText().clear();
                submitBTN.setBackgroundResource(R.drawable.next_button_gray_background);
                submitBTN.setTextColor(getResources().getColor(R.color.upper_light_black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.submit_pin_btn, R.id.forget_current_pin, R.id.backPressLY})
    public void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.submit_pin_btn:
                validateAndScreenNavigation();
                break;
            case R.id.forget_current_pin:
                try {
                    animationOnmanuallyInput();
                    forgetPIN_TV.setVisibility(View.GONE);
                    toolbarTitleTV.setText(CSStringUtil.getString(R.string.forget_pin));
                    headingTV.setText(CSStringUtil.getString(R.string.enter_new_pin));
                    belowHeaderTextTV.setText(CSStringUtil.getString(R.string.enter_below_message_set_pin));
                    submitBTN.setText(CSStringUtil.getString(R.string.confirm));
                    pinET.getText().clear();
                    submitBTN.setBackgroundResource(R.drawable.next_button_gray_background);
                    submitBTN.setTextColor(getResources().getColor(R.color.upper_light_black));
                    changeViewAction = 3;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.backPressLY:
                csActivity.onBackPressed();
                break;

        }
    }

    private void animationOnmanuallyInput() {
        Animation animation1 =
                AnimationUtils.loadAnimation(csActivity, R.anim.blink_anim);
        card_viewLY.startAnimation(animation1);
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    private void validateAndScreenNavigation() {
        try {
            if (changeViewAction == 0) { // condition for enter currentPassword and Validate For Match
                if (valiDate()) {
                    forgetPIN_TV.setVisibility(View.GONE);
                    pinET.getText().clear();
                    pinET.requestFocus();
                    headingTV.setText(CSStringUtil.getString(R.string.enter_new_pin));
                    belowHeaderTextTV.setText(CSStringUtil.getString(R.string.enter_below_message_set_pin));
                    submitBTN.setText(CSStringUtil.getString(R.string.change));
                    submitBTN.setBackgroundResource(R.drawable.next_button_gray_background);
                    submitBTN.setTextColor(getResources().getColor(R.color.upper_light_black));
                    changeViewAction = 1;
                }
            } else if (changeViewAction == 1) {//condition for enter NEW Password and Validate
                new Volley_Asynclass(csActivity, this, NetworkUtils.create_MPIN(CSStringUtil.getTextFromView(pinET), "setpin"),
                        Constants.SET_PIN_TAG, 141);

            } else if (changeViewAction == 3) {//condition ForgetPassword Click and HIT API OF OTP
                new Volley_Asynclass(csActivity, this, NetworkUtils.commonParameterFor_SignIN_By_MOB_REG("USERNAME",
                        CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getDetails().getPhone()
                        , "223", "GENERATEOTP"), Constants.SIGN_URL, 1231, getResources().getString(R.string.please_wait));


            } else if (changeViewAction == 4) {//condition ForgetPassword  from verify OTP screen Click and HIT API OF OTP

                new Volley_Asynclass(csActivity, this, NetworkUtils.commonParameterFor_SignIN_By_MOB_REG("USERNAME", CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getDetails().getPhone()
                        , "223", "GENERATEOTP"), Constants.SIGN_URL, 1231, getResources().getString(R.string.please_wait));


            }
            CSAppUtil.closeKeyboard(csActivity, pinET);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean valiDate() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
                            submitBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                            submitBTN.setTextColor(getResources().getColor(R.color.white));
                        } else {
                            submitBTN.setBackgroundResource(R.drawable.next_button_gray_background);
                            submitBTN.setTextColor(getResources().getColor(R.color.upper_light_black));
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

    @Override
    public void onGetResponse(String response, int requestCode) {
        if (requestCode == 1231) { // use for get OTP for update PIN
            if (response != null && !response.equals("")) {
                try {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").toLowerCase().equals("true")) {
                        jsonArray = jsonObject.getJSONArray("data");
                        CSShearedPrefence.saveOTP(jsonArray.getJSONObject(0).getString("OTP"));
                        CSShearedPrefence.counter_Time_OTP(jsonObject.getJSONArray("data").getJSONObject(0).getString("OTPExpireInSecond"));
                        Bundle bundle = new Bundle();
                        if (changeViewAction == 3) {
                            bundle.putString("SCREEN", "FORGET_PIN");
                        } else if (changeViewAction == 4) {
                            bundle.putString("SCREEN", "FORGET_ACTION_FROM_VERIFY_SCREEN");
                        }

                        bundle.putString("PIN", CSStringUtil.getTextFromView(pinET));

                        CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_changeAndForgetPin_to_OTPFragment, bundle);
                    } else {
                        showToast(jsonObject.getString("error"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 141) {
            parseCreateMpinReponse(response);
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
            csActivity.onBackPressed();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void parseCreateMpinReponse(String response) {
        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                new Volley_Asynclass(csActivity, this, NetworkUtils.create_MPIN(CSStringUtil.getTextFromView(pinET), "updatefcmtoken"),
                        Constants.SET_PIN_TAG, 1433);
                CSShearedPrefence.setMpin(CSStringUtil.getTextFromView(pinET));
                showDialog(CSStringUtil.getString(R.string.security_pin_change));
            } else {
                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.no_internet));
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
