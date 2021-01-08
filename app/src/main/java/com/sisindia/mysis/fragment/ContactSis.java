package com.sisindia.mysis.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.vision.text.Line;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactSis extends CSHeaderFragmentFragment {
    @BindView(R.id.backPressLY)
    LinearLayout backPressLY;
    @BindView(R.id.areaManagerName_TV)
    TextView areaManagerName_TV;
    @BindView(R.id.areaManagerContact_TV)
    TextView areaManagerContact_TV;
    @BindView(R.id.call_To_AreaManager_TV)
    TextView call_To_AreaManager_TV;
    @BindView(R.id.call_To_Supervisor_TV)
    TextView call_To_Supervisor_TV;
    @BindView(R.id.superVisorName_TV)
    TextView superVisorName_TV;
    @BindView(R.id.superVisorContact_TV)
    TextView superVisorContact_TV;
    @BindView(R.id.superVisor_IV)
    CircleImageView superVisor_IV;
    @BindView(R.id.areaManager_IV)
    CircleImageView areaManager_IV;
    @BindView(R.id.superVisor_Detail_LY)
    LinearLayout superVisor_Detail_LY;
    @BindView(R.id.seniorDetail_Main_LY)
    LinearLayout seniorDetail_Main_LY;
    @BindView(R.id.areaManager_Detail_LY)
    LinearLayout areaManager_Detail_LY;
    @BindView(R.id.main_layout)
    LinearLayout main_layout;
    @BindView(R.id.helpLineNo_TV)
    TextView helpLineNo_TV;
    @BindView(R.id.call_to_helpLine)
    TextView call_to_helpLine;
    @BindView(R.id.backPressIV)
    AppCompatImageView backPressIV;

    @Override
    public int layoutResource() {
        return R.layout.contact_sis_screen;
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    @OnClick({R.id.backPressLY})
    public void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.backPressLY:
                csActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        setValues();
        Drawable mIcon = ContextCompat.getDrawable(getActivity(), R.drawable.white_arrow);
        if (CSShearedPrefence.isNightModeEnabled()) {
            backPressIV.setImageDrawable(mIcon);
            main_layout.setBackgroundColor(getResources().getColor(R.color.white_background));

        } else {
            mIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), PorterDuff.Mode.MULTIPLY);
            backPressIV.setImageDrawable(mIcon);

        }

    }

    private void setValues() {
        UserDetailModel userDetailModel = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getUserDetails(CSShearedPrefence.getUser());
        if (userDetailModel != null) {
            if (!CSStringUtil.isEmptyStr(userDetailModel.getSuperVisorName())) {  // IF SUPERVISOR DETAIL NOT BLANK
                superVisor_Detail_LY.setVisibility(View.VISIBLE);
                superVisorName_TV.setText(userDetailModel.getSuperVisorName());
                superVisorContact_TV.setText(userDetailModel.getSuperVisorPhone());
                Glide.with(csActivity).load(userDetailModel.getSupPicture()).
                        placeholder(R.drawable.no_image_found).
                        diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).
                        into(superVisor_IV);
                call_To_Supervisor_TV.setOnClickListener(view -> callingIntent(userDetailModel.getSuperVisorPhone()));

            } else {
                superVisor_Detail_LY.setVisibility(View.GONE);
            }
            if (!CSStringUtil.isEmptyStr(userDetailModel.getAiName())) {  // IF AREA MANAGER DETAIL NOT BLANK
                areaManager_Detail_LY.setVisibility(View.VISIBLE);
                areaManagerName_TV.setText(userDetailModel.getAiName());
                areaManagerContact_TV.setText(userDetailModel.getAiMobile());
                Glide.with(csActivity).load(userDetailModel.getAiPicture()).
                        placeholder(R.drawable.no_image_found).
                        diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).
                into(areaManager_IV);
                call_To_AreaManager_TV.setOnClickListener(view -> callingIntent(userDetailModel.getAiMobile()));
            } else {
                areaManager_Detail_LY.setVisibility(View.GONE);

            }
            if(CSStringUtil.isEmptyStr(userDetailModel.getAiName())&&CSStringUtil.isEmptyStr(userDetailModel.getSuperVisorName())){
                seniorDetail_Main_LY.setVisibility(View.GONE);
            }else {
                seniorDetail_Main_LY.setVisibility(View.VISIBLE);
            }
            helpLineNo_TV.setText(userDetailModel.getCustomer_Support_No());
            call_to_helpLine.setOnClickListener(view -> callingIntent(userDetailModel.getChange_ContactNo()));
        }
    }
    private void callingIntent(String contactNO){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactNO));
        startActivity(intent);
    }
}
