package com.sisindia.mysis.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.asynctask.Volley_Asynclass;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;
import com.sisindia.mysis.utils.PinEntryEditText;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPin extends CSHeaderFragmentFragment {
    @BindView(R.id.confirmBTN)
    Button confirmBTN;
    @BindView(R.id.pinET)
    PinEntryEditText pinET;
    @BindView(R.id.static_desc_TV)
    TextView static_desc_TV;

    @OnClick({R.id.confirmBTN})
    public void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.confirmBTN:
                validateAndScreenNavigation();
                break;
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void validateAndScreenNavigation() {
        try {
            if (valiDate()) {
                CSAppUtil.closeKeyboard(csActivity, pinET);
                new Volley_Asynclass(csActivity, this, NetworkUtils.create_MPIN(CSStringUtil.getTextFromView(pinET), "setpin"),
                        Constants.SET_PIN_TAG, 141);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int layoutResource() {
        return R.layout.set_pin;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        static_desc_TV.setVisibility(View.VISIBLE);
        edit_ActionListner();

    }

    @Override
    public void onGetResponse(String jsonObject, int requestCode) {
        if (requestCode == 141) {
            parseCreateMpinReponse(jsonObject);
        }
    }

    private void parseCreateMpinReponse(String response) {
        try {
            JSONObject jsonObject;
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                new Volley_Asynclass(csActivity, this, NetworkUtils.create_MPIN(CSStringUtil.getTextFromView(pinET), "updatefcmtoken"),
                        Constants.SET_PIN_TAG, 142);

                CSShearedPrefence.setMpin(CSStringUtil.getTextFromView(pinET));
                csActivity.onBackPressed();
            } else {
                CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.no_internet));
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

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
                            confirmBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
                            confirmBTN.setTextColor(getResources().getColor(R.color.white));
                        } else {
                            confirmBTN.setBackgroundResource(R.drawable.next_button_gray_background);
                            confirmBTN.setTextColor(getResources().getColor(R.color.text_color_hint));
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
