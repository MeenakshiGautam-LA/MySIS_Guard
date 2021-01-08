package com.sisindia.mysis.fragment;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.listener.TextWatcherListener;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Custom_TextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeContactNumber extends CSHeaderFragmentFragment implements TextWatcherListener {
    @BindView(R.id.raise_request_btn)
    Button proceedBTN;
    @BindView(R.id.mobileET)
    AppCompatEditText mobileET;
    @BindView(R.id.backPressIV)
    AppCompatImageView backPressIV;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.backPressLY)
    LinearLayout backPressLY;

    @Override
    public int layoutResource() {
        return R.layout.change_contact_number;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        new Custom_TextWatcher(mobileET, this, R.id.mobileET);
        backPressIV.setOnClickListener(v -> csActivity.onBackPressed());
        proceedBTN.setText(CSStringUtil.getString(R.string.raise_a_request));
    }

    @Override
    public void onTextChangeMethod(String value, int viewId) {
        try {
        if (value.length() == 10) {
            proceedBTN.setBackgroundResource(R.drawable.background_border_red_app_bg);
            proceedBTN.setTextColor(getResources().getColor(R.color.white));
        } else {
            proceedBTN.setBackgroundResource(R.drawable.next_button_gray_background);
            proceedBTN.setTextColor(getResources().getColor(R.color.light_black));

        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    @Nullable
    @OnClick({R.id.raise_request_btn,R.id.backPressLY})
    void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.raise_request_btn:
                if (CSStringUtil.getTextFromView(mobileET).length() < 10) {
                    showToast(CSStringUtil.getString(R.string.inValid_Number));
                } else {
                    insertIn_Table();
                    showDialog(CSStringUtil.getTextFromView(mobileET));
                }
                break;
            case R.id.backPressLY:
                csActivity.onBackPressed();
                break;
        }
    }

    public void showDialog(String msg) {
        Dialog dialog = new Dialog(CSApplicationHelper.application().getActivity(), android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.change_contact_successfully_request);
        TextView text = dialog.findViewById(R.id.mobile_number);
        AppCompatImageView cancelImg = dialog.findViewById(R.id.cancelImg);

        AppCompatButton contact_change_btn = dialog.findViewById(R.id.contact_change_btn);
        text.setText(msg);
        cancelImg.setOnClickListener(v -> {
            csActivity.onBackPressed();
            dialog.dismiss();
        });
        contact_change_btn.setOnClickListener(view -> {
            csActivity.onBackPressed();
            dialog.dismiss();
        });
        dialog.show();

    }

    private void insertIn_Table() {
        CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().updateContactNo(CSShearedPrefence.getUser(),CSShearedPrefence.getUnitcode(),
                CSStringUtil.getTextFromView(mobileET));
    }
}
