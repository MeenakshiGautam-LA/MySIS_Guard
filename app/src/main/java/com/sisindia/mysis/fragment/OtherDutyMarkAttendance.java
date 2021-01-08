package com.sisindia.mysis.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.vision.text.Line;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.Adapter.ShiftAdapter;
import com.sisindia.mysis.Model.Other_Duty_Mark_Model;
import com.sisindia.mysis.Model.RosterDetail;
import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.SelfieActivity;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass;
import com.sisindia.mysis.entity.OtherDuty_WithQrCode.EmployeeDetail;
import com.sisindia.mysis.entity.OtherDuty_WithQrCode.Other_QR_CodeMainModel;
import com.sisindia.mysis.entity.OtherDuty_WithQrCode.ShiftDetail;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.listener.RecyclerItemClickListener;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class OtherDutyMarkAttendance extends CSHeaderFragmentFragment {
    @BindView(R.id.reg_no_tv)
    TextView reg_no_tv;
    @BindView(R.id.userNameTV)
    TextView userNameTV;
    @BindView(R.id.odma_emp_rank_tv)
    TextView odma_emp_rank_tv;
    @BindView(R.id.unitCodeTV)
    TextView unitCodeTV;
    @BindView(R.id.dutyRankNameTV)
    TextView dutyRankNameTV;
    @BindView(R.id.userImageCV)
    CircleImageView userImageCV;
    @BindView(R.id.unit_code_tv)
    TextView unit_code_tv;
    @BindView(R.id.unitNameTV)
    TextView unitNameTV;
    @BindView(R.id.postNameTV)
    TextView postNameTV;

    @BindView(R.id.rank_sp)
    Spinner rank_sp;
    @BindView(R.id.otherDuty_IN_BTN)
    AppCompatButton otherDuty_IN_BTN;
    @BindView(R.id.main_layoutLY)
    LinearLayout main_layoutLY;
    @BindView(R.id.cancel_IV)
    AppCompatImageView cancel_IV;
    @BindView(R.id.shift_RV)
    RecyclerView shift_RV;
    @BindView(R.id.cancelBTN)
    AppCompatButton cancelBTN;
    private int shiftPosition = -1;
    private Other_QR_CodeMainModel other_qr_codeMainModel;
    private Other_Duty_Mark_Model other_duty_mark_model;
    private ShiftAdapter shiftAdapter;
    //    private OtherUnitEmployeeDetailModel otherUnitEmployeeDetailModel = new OtherUnitEmployeeDetailModel();
    private EmployeeDetail employeeDetail;

    @Override
    public int layoutResource() {
        return R.layout.other_duty_mark_attendance;
    }

    @Override
    public void dialog_onClick(int requestCode) {
        if (requestCode == 18988) {
            csActivity.onBackPressed();
        }
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        cancelBTN.setOnClickListener(view -> csActivity.onBackPressed());
        Drawable mIcon = ContextCompat.getDrawable(getActivity(), R.drawable.icon_cross);

        if (CSShearedPrefence.isNightModeEnabled()) {
            cancel_IV.setImageDrawable(mIcon);
            main_layoutLY.setBackgroundColor(getResources().getColor(R.color.white_background));

        } else {
            mIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), PorterDuff.Mode.MULTIPLY);
            cancel_IV.setImageDrawable(mIcon);

        }
        if (getArguments() != null) {
            String searchRegNo = getArguments().getString("REGNO");
            String qr_code = getArguments().getString("QRCODE");
            other_duty_mark_model = (Other_Duty_Mark_Model) getArguments().getSerializable("OTHER_MARK_DUTY_MODEL");
            new Volley_Asynclass(csActivity, this, NetworkUtils.forOtherDuty_WithQR_Code(searchRegNo, qr_code),
                    Constants.OTHER_DUTY_WITH_QR_CODE_URL
                    , 000, CSStringUtil.getString(R.string.please_wait));
//            showToast(getArguments().getString("DUTY_STATUS"));

        }
        cancel_IV.setOnClickListener(view -> csActivity.onBackPressed());
        otherDuty_IN_BTN.setOnClickListener(view -> {
            try {
                if (other_qr_codeMainModel != null) {
                    if (other_qr_codeMainModel.getShiftDetail().size() > 0) {
                        if(shiftPosition==-1){
                            CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.please_select_shift));
                        }else {
                            passBundleToSelfieCapture();
                        }
                    } else {
                        CSDialogUtil.showInfoDialog(csActivity, CSStringUtil.getString(R.string.other_mark_shift_not_allowed));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetResponse(String response, int requestCode) {
        show_Log_Data("OTHER_MARK_SCREEN", response);

        try {
            JSONObject jsonObject, jsonData;
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                main_layoutLY.setVisibility(View.VISIBLE);
                jsonData = jsonObject.getJSONObject("data");
                Type listType = new TypeToken<Other_QR_CodeMainModel>() {
                }.getType();
                other_qr_codeMainModel = new Gson().fromJson(jsonData.toString(), listType);
//                setShiftNameInSpinner(other_qr_codeMainModel);
                setShiftDetail(other_qr_codeMainModel);
//                    setDutyRankeInSpinner(other_qr_codeMainModel);
                unitCodeTV.setText(other_qr_codeMainModel.getUnitDetail().get(0).getUNITCODE());
                userNameTV.setText(other_duty_mark_model.getEmployeDetailsModel().getEMPNAME() + " (" +
                        other_duty_mark_model.getEmployeDetailsModel().getREGNO()
                        + ")");
                postNameTV.setText(CSStringUtil.getString(R.string.post) + " " + other_qr_codeMainModel.getUnitDetail().get(0).getPOSTNAME());
                odma_emp_rank_tv.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().
                        getSymbol(other_duty_mark_model.getEmployeDetailsModel().getRANK()));
                dutyRankNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().
                        getSymbolFullName(other_duty_mark_model.getEmployeDetailsModel().getRANK())
                        + "( " + other_duty_mark_model.getEmployeDetailsModel().getRANK() + " )");
                unitNameTV.setText(other_qr_codeMainModel.getUnitDetail().get(0).getUNITNAME() + " (" + (other_qr_codeMainModel.getUnitDetail().get(0).
                        getUNITCODE() + ")"));
                Glide.with(csActivity).load(other_duty_mark_model.getEmployeDetailsModel().getPICTURE())
                        .placeholder(R.drawable.no_image_found)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .into(userImageCV);
                unitNameTV.setOnClickListener(view -> CSDialogUtil.showUnitName(other_qr_codeMainModel.getUnitDetail().get(0).getUNITNAME()));

            } else {
                CSDialogUtil.showInfoDialog(csActivity, jsonObject.getString("error"), this, 18988, false);
               /* Dialog dialog = new Dialog(CSApplicationHelper.application().getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.layout_dialog_box);
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

                final TextView message = dialog.findViewById(R.id.message);

                final TextView okButton = dialog.findViewById(R.id.ok_action);
                final TextView title = dialog.findViewById(R.id.dialog_title);
                final TextView cancel = dialog.findViewById(R.id.cancel_action);
                cancel.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
                okButton.setOnClickListener(view -> {
                    dialog.dismiss();
                    csActivity.onBackPressed();
                });
                message.setText(jsonObject.getString("error"));
                dialog.show();*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setShiftDetail(Other_QR_CodeMainModel other_qr_codeMainModel) {
        shiftAdapter = new ShiftAdapter(this, other_qr_codeMainModel.getShiftDetail());
        shift_RV.setAdapter(shiftAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        for (int i = 0; i < other_qr_codeMainModel.getShiftDetail().size(); i++) {
            if (i != position) {
                other_qr_codeMainModel.getShiftDetail().get(i).setSelected(false);
            } else {
                shiftPosition = position;
            }
        }
        show_Log_Data("onItemClick", String.valueOf(shiftPosition));
        shiftAdapter = new ShiftAdapter(this, other_qr_codeMainModel.getShiftDetail());
        shift_RV.setAdapter(shiftAdapter);
    }

    private void passBundleToSelfieCapture() {
        try {
            RosterDetail rosterDetail = new RosterDetail();
            rosterDetail.setSHIFTSTARTTIME(other_qr_codeMainModel.getShiftDetail().get(shiftPosition).getSTARTTIME());
            rosterDetail.setSHIFTENDTIME(other_qr_codeMainModel.getShiftDetail().get(shiftPosition).getENDTIME());
            rosterDetail.setDUTYRANK(other_duty_mark_model.getEmployeDetailsModel().getRANK());
            rosterDetail.setDUTYPOSTID(other_qr_codeMainModel.getUnitDetail().get(0).getPOSTID());
            rosterDetail.setREGNO(other_duty_mark_model.getEmployeDetailsModel().getREGNO());
            rosterDetail.setSHIFTID(other_qr_codeMainModel.getShiftDetail().get(shiftPosition).getID());
            rosterDetail.setSHIFTSTARTTIME(other_qr_codeMainModel.getShiftDetail().get(shiftPosition).getSTARTTIME());
            rosterDetail.setSHIFTENDTIME(other_qr_codeMainModel.getShiftDetail().get(shiftPosition).getENDTIME());
            rosterDetail.setUNITCODE(other_qr_codeMainModel.getUnitDetail().get(0).getUNITCODE());
            rosterDetail.setDUTYPOSTNAME((other_qr_codeMainModel.getUnitDetail().get(0).getPOSTNAME()));
            rosterDetail.setDUTYSYMBOL(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().
                    getSymbol(other_duty_mark_model.getEmployeDetailsModel().getRANK()));
            rosterDetail.setDUTYRANKNAME(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().getSymbolFullName(
                    other_duty_mark_model.getEmployeDetailsModel().getRANK()));
            rosterDetail.setUNITNAME(other_qr_codeMainModel.getUnitDetail().get(0).getUNITNAME());
            rosterDetail.setSHIFTNAME(other_qr_codeMainModel.getShiftDetail().get(shiftPosition).getSHIFTNAME());
            rosterDetail.setROSTERDATE(DateUtils.getCurrentDate_format());
            other_duty_mark_model.setRosterDetail(rosterDetail);
            Bundle bundle = new Bundle();
            bundle.putSerializable("OTHERUNITEMPLOYEE", other_duty_mark_model);
            bundle.putString("SCREEN_TAG", "OTHER_DUTY_PUNCH_SCREEN");
            bundle.putString("DUTY_STATUS", getArguments().getString("DUTY_STATUS"));
            Intent intent = new Intent(csActivity, SelfieActivity.class);
            intent.putExtras(bundle);
            csActivity.startActivity(intent);
            CSAppUtil.openFragmentByNavigationClearStack(fragmentView, R.id.otherDutyEmp_SearchBy_QR_CodeByRegno);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
