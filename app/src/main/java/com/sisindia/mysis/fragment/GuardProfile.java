package com.sisindia.mysis.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sisindia.mysis.Adapter.AdditionalUnitAdapter;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.dataBase.EmployeDetailsModel;
import com.sisindia.mysis.entity.UnitDetailModel;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class GuardProfile extends CSHeaderFragmentFragment {
    @BindView(R.id.contact_change_btn)
    AppCompatButton changeContactNoBTN;
    @BindView(R.id.pin_change_btn)
    AppCompatButton changePinBTN;
    @BindView(R.id.bankNameTV)
    TextView bankNameTV;
    @BindView(R.id.accountHolderNameTV)
    TextView accountHolderNameTV;
    @BindView(R.id.accountHolderNumberTV)
    TextView accountHolderNumberTV;
    @BindView(R.id.bankAddressTV)
    TextView bankAddressTV;
    @BindView(R.id.ifsc_CodeTV)
    TextView ifsc_CodeTV;
    @BindView(R.id.contactNO_TV)
    TextView contactNO_TV;
    @BindView(R.id.timePeriodYearTV)
    TextView timePeriodYearTV;
    @BindView(R.id.profile_logo)
    CircleImageView userProfileIV;
    @BindView(R.id.userNameTV)
    TextView userNameTV;
    @BindView(R.id.postRankNameTV)
    TextView dutyRankNameTV;
    @BindView(R.id.regNoTV)
    TextView regNoTV;
    @BindView(R.id.profileLY)
    LinearLayout profileLY;
    @BindView(R.id.pendingDescriptionTV)
    TextView pendingDescriptionTV;
    @BindView(R.id.bank_logo_IV)
    ImageView bank_logo_IV;
    @BindView(R.id.contact_Sis_LY)
    RelativeLayout contact_Sis_LY;
    @BindView(R.id.baseUnitName_TV)
    TextView baseUnitName_TV;
    @BindView(R.id.baseUnitCode_TV)
    TextView baseUnitCode_TV;
    @BindView(R.id.additionalUnit_RV)
    RecyclerView additionalUnit_RV;
    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;


    @Override
    public int layoutResource() {
        return R.layout.profile_screen;
    }

    @OnClick({R.id.pin_change_btn, R.id.contact_change_btn, R.id.profileLY, R.id.contact_Sis_LY})
    public void onClickMethod(View view) {
        switch (view.getId()) {
            case R.id.contact_change_btn:
                CSAppUtil.openFragmentByNavigation(fragmentView, R.id.action_guardProfile_to_changeContactNumber);
                break;
            case R.id.pin_change_btn:
                Bundle bundle = new Bundle();
                bundle.putString("SCREEN", "FROM_GUARD_PROFILE");
                CSAppUtil.openFragmentByNavi_Pass_Bundle(fragmentView, R.id.action_guardProfile_to_changeAndForgetPin, bundle);
                break;
            case R.id.profileLY:
                csActivity.onBackPressed();
                break;
            case R.id.contact_Sis_LY:
                CSAppUtil.openFragmentByNavigation(fragmentView, R.id.action_guardProfile_to_contactSis);
                break;
        }
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        setValue();
        setBase_Nd_Additional_UnitDetail();
        if (CSShearedPrefence.isNightModeEnabled()) {

            frame_layout.setBackground(null);
            profileLY.setBackground(null);
//            calender_main_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @SuppressLint("SetTextI18n")
    private void setValue() {
        try {

            LiveData<UserDetailModel> profileModelLiveData = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                    profileDetails(CSShearedPrefence.getUser());
            profileModelLiveData.observe(this, profileModel -> {
                if (profileModel != null) {
                    if (profileModel.getPendingStatus().equalsIgnoreCase("1")) {
                        contactNO_TV.setText(profileModel.getChange_ContactNo());
                        pendingDescriptionTV.setVisibility(View.VISIBLE);
                        pendingDescriptionTV.setText(CSStringUtil.getString(R.string.pending_For_Approved));
                    } else {
                        pendingDescriptionTV.setVisibility(View.GONE);
                        contactNO_TV.setText(profileModel.getPhone());
                    }
                    timePeriodYearTV.setText(calculateYear() + " " + CSStringUtil.getString(R.string.working_since) + " " + profileModel.getCOMPANY_NAME());

                }

                /*else {
                    pendingDescriptionTV.setVisibility(View.GONE);
                    if(CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getDetails()!=null){
                        contactNO_TV.setText(CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getDetails().getPhone());
                    }
                }*/

            });
            EmployeDetailsModel employeDetailsModel = CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().
                    getEmployee(CSShearedPrefence.getUser());
            if (employeDetailsModel != null) {
                bankNameTV.setText(employeDetailsModel.getBankName());
                regNoTV.setText(employeDetailsModel.getREGNO());
                accountHolderNameTV.setText(employeDetailsModel.getEMPNAME());
                bankAddressTV.setText(employeDetailsModel.getBankAddress());
                ifsc_CodeTV.setText(employeDetailsModel.getBankIfscCode());
                accountHolderNumberTV.setText(employeDetailsModel.getBankAccountNumber());
                UserDetailModel userDetailModel = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                        getUserDetails(CSShearedPrefence.getUser());
                Glide.with(csActivity).load(userDetailModel.getProfile_Picture())
                        .error(R.drawable.no_image_found)
                        .circleCrop().
                        diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .into(userProfileIV);
                if (!CSStringUtil.isEmptyStr(employeDetailsModel.getBankLogo())) {
                    Glide.with(csActivity).load(employeDetailsModel.getBankLogo())
                            .placeholder(R.drawable.no_image_found)
                            .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)

                            .into(bank_logo_IV);
                } else {
                    Glide.with(csActivity).load(R.drawable.no_image_found)
                            .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                            .into(bank_logo_IV);
                }
                userNameTV.setText(userDetailModel.getName());
                dutyRankNameTV.setText(userDetailModel.getDesignation().toLowerCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String calculateYear() {
        String years = null;
        try {
            int currentDate = Integer.parseInt(DateUtils.getCurrentDate_format().split("-")[0]);
            int joiningDate = Integer.parseInt(CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().getEmployee
                    (CSShearedPrefence.getUser()).getJOININGDATE().split("-")[0]);
            if (currentDate > joiningDate) {
                years = String.valueOf(currentDate - joiningDate);
            } else {
                years = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return years;
    }

    private void setBase_Nd_Additional_UnitDetail() {
        UnitDetailModel baseUnitDetail = CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().getBaseUnitDetail();
        if (baseUnitDetail != null) {
            baseUnitCode_TV.setText(baseUnitDetail.getUNIT_CODE());
            baseUnitName_TV.setText(baseUnitDetail.getSITE_NAME());
        }
        List<UnitDetailModel> additionalUnitList = CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().getAdditionalUnit();
        additionalUnit_RV.setAdapter(new AdditionalUnitAdapter(csActivity, additionalUnitList));
    }

}
