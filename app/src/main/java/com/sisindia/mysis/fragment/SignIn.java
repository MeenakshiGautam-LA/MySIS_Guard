package com.sisindia.mysis.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.R;
import com.sisindia.mysis.Services.BackgroundApiService;
import com.sisindia.mysis.activity.MainActivity_Guard;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.listener.TextWatcherListener;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.Custom_TextWatcher;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sisindia.mysis.utils.Constants.LOGINURL;


public class SignIn extends CSHeaderFragmentFragment implements TextWatcherListener {

    @BindView(R.id.signInBTN)
    Button signInBTN;
    @BindView(R.id.mobileET)
    AppCompatEditText mobileET;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.signByRegistrationTV)
    TextView signByRegistrationTV;
    @BindView(R.id.mobileNoAction)
    TextView mobileNoAction;
    private Integer checkSelectReg_Or_ByMobile = 0;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.mobile_no_des_LY)
    LinearLayout mobile_no_des_LY;
    @BindView(R.id.by_reg_no_des_LY)
    LinearLayout by_reg_no_des_LY;
    @BindView(R.id.sign_in_using_header_TV)
    TextView sign_in_using_header_TV;




    @Override
    public int layoutResource() {
        return R.layout.sign_in_layout;
    }

    @Nullable
    @OnClick({R.id.signInBTN, R.id.signByRegistrationTV, R.id.mobileNoAction})
    void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.signInBTN:
                if (permissionAllowed)
                    validateAndScreenNavigation();
                else
                    checkPermissions();
                break;
            case R.id.signByRegistrationTV:
                animationOnSignUpSelection();
                checkSelectReg_Or_ByMobile = 1;
                textInputLayout.setHint(CSStringUtil.getString(R.string.enter_Registration_no));
                mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});

                mobileET.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                mobileET.getText().clear();
                by_reg_no_des_LY.setVisibility(View.GONE);
                mobile_no_des_LY.setVisibility(View.VISIBLE);
                sign_in_using_header_TV.setText(CSStringUtil.getString(R.string.sign_using_registration));
                break;
            case R.id.mobileNoAction:
                checkSelectReg_Or_ByMobile = 0;
                animationOnSignUpSelection();
                textInputLayout.setHint(CSStringUtil.getString(R.string.enter_mobile_no));
                mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                mobileET.setInputType(InputType.TYPE_CLASS_NUMBER);
                mobileET.getText().clear();
                by_reg_no_des_LY.setVisibility(View.VISIBLE);
                mobile_no_des_LY.setVisibility(View.GONE);
                sign_in_using_header_TV.setText(CSStringUtil.getString(R.string.sign_in_using_mobile));
                break;
        }
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private void storeDeviceId() {
        if (CSStringUtil.isEmptyStr(CSShearedPrefence.getDeviceToken())) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                CSShearedPrefence.saveDeviceToken(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) csActivity.getSystemService(Context.TELEPHONY_SERVICE);
                CSShearedPrefence.saveDeviceToken(telephonyManager.getDeviceId());
            }
        }
    }

    private void validateAndScreenNavigation() {
        try {
            if (valiDate()) {

                CSAppUtil.closeKeyboard(csActivity, mobileET);
                if (CSStringUtil.isEmptyStr(CSShearedPrefence.getMpin())) {
//                    if (checkSelectReg_Or_ByMobile == 0)// *****************IT USE  TO CHECK BY REG NO  BASES********************************

                    new Volley_Asynclass(csActivity, this, NetworkUtils.commonParameterFor_SignIN_By_MOB_REG("USERNAME",
                            CSStringUtil.getTextFromView(mobileET)
                            , "223", "ispinset"), Constants.SIGN_URL, 1222, getResources().getString(R.string.please_wait));
                   /* else
                        new Volley_Asynclass(csActivity, this, NetworkUtils.commonParameterFor_SignIN_By_MOB_REG("USERNAME",
                                CSStringUtil.getTextFromView(mobileET)
                                , "223", "ispinset"), Constants.SIGN_URL, 1222, getResources().getString(R.string.please_wait));*/
                } else {

                    new Volley_Asynclass(csActivity, this, NetworkUtils.loginparamater(CSShearedPrefence.getUser(),
                            CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "LOGINURL"), LOGINURL,
                            Constants.LOGIN, getResources().getString(R.string.please_wait));

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onGetResponse(String response, int requestCode) {
        if (requestCode == Constants.SIGNIN) {   // for OTP RESPONSE
            if (response != null && !response.equals("")) {
                try {
                    JSONObject jsonObject;
                    JSONArray jsonArray;
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").toLowerCase().equals("true")) {
                        jsonArray = jsonObject.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            CSShearedPrefence.saveOTP(jsonArray.getJSONObject(0).getString("OTP"));
                            CSShearedPrefence.setPassword(jsonArray.getJSONObject(0).getString("Password"));
                            CSShearedPrefence.counter_Time_OTP(jsonObject.getJSONArray("data").getJSONObject(0).getString("OTPExpireInSecond"));
                            Bundle bundle = new Bundle();
                            bundle.putString("SCREEN", "FROM_SIGN_IN");
                            bundle.putString("PHONE_NUMBER", jsonObject.getJSONArray("data").getJSONObject(0).getString("Phone"));
                            CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_signActivity_to_OTPActivity, bundle);
                        } else {
                            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.no_internet));
                        }
                    } else {
                        showToast(jsonObject.getString("error"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == Constants.LOGIN) {
            parseResponseLogin(response);
        } else if (requestCode == 1222) { // Check user Login First Time OR already set PIN
            checkLoginAlreadyInResponse(response);
        }
    }

    private void checkLoginAlreadyInResponse(String response) {
        try {
            JSONObject jsonObject;
            JSONArray jsonArray;
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray.length() > 0) {
                    CSShearedPrefence.setUser(CSStringUtil.getTextFromView(mobileET));
                    CSShearedPrefence.setPassword(jsonArray.getJSONObject(0).getString("Password"));
                    CSShearedPrefence.setMpin(jsonArray.getJSONObject(0).getString("PIN"));
                    if (jsonArray.getJSONObject(0).getString("IsActive").equalsIgnoreCase("1")) {
                        new Volley_Asynclass(csActivity, this, NetworkUtils.loginparamater(CSShearedPrefence.getUser(),
                                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "LOGINURL"), LOGINURL,
                                Constants.LOGIN, getResources().getString(R.string.please_wait));

                    } else {
                        new Volley_Asynclass(csActivity, this, NetworkUtils.commonParameterFor_SignIN_By_MOB_REG("USERNAME",
                                CSStringUtil.getTextFromView(mobileET)
                                , "223", "generateguardotp"), Constants.SIGN_URL, Constants.SIGNIN, getResources().getString(R.string.please_wait));
                    }
                }
            } else {
                dialog_User_account_invalid(CSStringUtil.getTextFromView(mobileET));
//                showToast(jsonObject.getString("error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean valiDate() {
        try {

            if (CSStringUtil.isEmptyView(mobileET)) {
                showToast(CSStringUtil.getString(R.string.valid_Number));
                return false;
            } else if (checkSelectReg_Or_ByMobile == 0) {

                if (mobileET.getText().length() < 10) {
                    showToast(CSStringUtil.getString(R.string.mobile_number_Valid));
                    return false;
                }
            } else {
                if (mobileET.getText().length() < 9) {
                    showToast(CSStringUtil.getString(R.string.Registration_number_Valid));
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        new Custom_TextWatcher(mobileET, this, R.id.mobileET);
        checkPermissions();
    }

    private void changeInputTypeAccordingSelection() {
        if (CSStringUtil.getTextFromView(mobileET).length() != 0) {
            if (CSStringUtil.getTextFromView(mobileET).length() == 9) {
                textInputLayout.setHint(CSStringUtil.getString(R.string.enter_Registration_no));
                mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                mobileET.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
//                mobileET.getText().clear();
            } else {
                textInputLayout.setHint(CSStringUtil.getString(R.string.enter_mobile_no));
                mobileET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                mobileET.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        }
    }

    @Override
    public void onTextChangeMethod(String value, int viewId) {

        if (checkSelectReg_Or_ByMobile == 0) {
            if (value.length() == 10) {
                CSAppUtil.closeKeyboard(csActivity, signInBTN);
                signInBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                signInBTN.setTextColor(getResources().getColor(R.color.white));
                changeInputTypeAccordingSelection();
            } else {
                signInBTN.setBackgroundResource(R.drawable.next_button_gray_background);
                signInBTN.setTextColor(getResources().getColor(R.color.light_black));
            }
        } else if (value.length() == 9) {
            CSAppUtil.closeKeyboard(csActivity, signInBTN);
            signInBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
            signInBTN.setTextColor(getResources().getColor(R.color.white));
            changeInputTypeAccordingSelection();
        } else {
            signInBTN.setBackgroundResource(R.drawable.next_button_gray_background);
            signInBTN.setTextColor(getResources().getColor(R.color.light_black));
        }
    }

    private void parseResponseLogin(String response) {
        try {

            JSONObject jsonObject;
            JSONArray jsonArray, superVisorJson_Array;
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                jsonArray = jsonObject.getJSONArray("data");

                if (jsonArray.length() > 0) {
                    superVisorJson_Array = jsonArray.getJSONObject(0).getJSONArray("Supervisor");
                    UserDetailModel userDetailModel;
                    Type listType = new TypeToken<UserDetailModel>() {
                    }.getType();
                    storeDeviceId();
                    userDetailModel = new Gson().fromJson(jsonArray.getJSONObject(0).toString(), listType);
                    if (superVisorJson_Array.length() > 0) {
                        userDetailModel.setSuperVisorName(superVisorJson_Array.getJSONObject(0).getString("Name"));
                        userDetailModel.setSuperVisorPhone(superVisorJson_Array.getJSONObject(0).getString("Phone"));
                        userDetailModel.setSuperVisorEmail(superVisorJson_Array.getJSONObject(0).getString("Email"));
                    }
                    userDetailModel.setSave_JSON(jsonArray.getJSONObject(0).toString());
                    CSShearedPrefence.setUser_ID(userDetailModel.getID());
                    CSShearedPrefence.setUnitcode(userDetailModel.getUNIT_CODE());
                    CSShearedPrefence.setUser(userDetailModel.getRegNo().toUpperCase());
                    CSShearedPrefence.setLoggedInUserName(userDetailModel.getName());
                    CSShearedPrefence.setPassword(jsonArray.getJSONObject(0).getString("Password"));
//                    CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().insert(userDetailModel);
//                    CFUtil.call_Get_DataFrom_Server();
                    Intent intent = new Intent(csActivity, BackgroundApiService.class);
                    intent.putExtra("message", "CALL_ALL_API");
                    csActivity.startService(intent);
                    CSShearedPrefence.setAlreadyLaunch();
                    CSShearedPrefence.setVersionCode(CFUtil.setAppVersionName(csActivity));
                    CSShearedPrefence.setIsLoggedIn(true);
                    CSAppUtil.openActivity(MainActivity_Guard.class);
                    csActivity.finish();
                } else {
                    showToast(CSStringUtil.getString(R.string.invalid_credentials_sign_IN));
                }
            } else {
                dialog_User_account_invalid(CSStringUtil.getTextFromView(mobileET));
//                showToast(jsonObject.getString("error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animationOnSignUpSelection() {
        Animation animation1 =
                AnimationUtils.loadAnimation(csActivity, R.anim.blink_anim);
        cardview.startAnimation(animation1);
    }

    @Override
    public void onFail(String message) {
        Log.e("onFail>>>>", "" + message);
    }


    @SuppressLint("SetTextI18n")
    public void dialog_User_account_invalid(String userName) {

        Dialog dialog = new Dialog(CSApplicationHelper.application().getActivity(), android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.user_account_not_valid);
        TextView user_account_invalid_TV = dialog.findViewById(R.id.user_account_invalid_TV);
        TextView back_TV = dialog.findViewById(R.id.back_TV);
        if (checkSelectReg_Or_ByMobile == 0) {  // for mobile no
            user_account_invalid_TV.setText(CSStringUtil.getStringForID(R.string.user_account_invalid_mobile_start) + " " + userName + " " +
                    CSStringUtil.getStringForID(R.string.user_account_invalid_end));
        } else {
            user_account_invalid_TV.setText(CSStringUtil.getStringForID(R.string.user_account_invalid_reg_start) + " " + userName + " " +
                    CSStringUtil.getStringForID(R.string.user_account_invalid_end));
        }
        back_TV.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}
