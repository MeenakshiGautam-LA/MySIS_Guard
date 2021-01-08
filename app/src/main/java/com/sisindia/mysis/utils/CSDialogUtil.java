package com.sisindia.mysis.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.listener.DialogCallback;

public class CSDialogUtil {

    public static void showInfoDialog(AppCompatActivity context, String messageString) {
        showInfoDialog(context, "", messageString, CSStringUtil.getString(R.string.ok), CSStringUtil.getString(R.string.Cancel), false, 0, null, null);
    }

    public static void showInfoDialog(BaseActivity context, String messageString, DialogCallback dialogCallback, Integer requestcode, boolean cancelVisible) {
        showInfoDialog(context, "", messageString, CSStringUtil.getString(R.string.ok), CSStringUtil.getString(R.string.Cancel), cancelVisible, requestcode, dialogCallback, null);
    }

    public static void showAlertDialogMark_Attendance_Other_Already(AppCompatActivity context, DailyAttendanceDetail dailyAttendanceDetail,
                                                                    DialogCallback dialogCallback, int requestCode) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.DialogBox);
        LayoutInflater inflater = context.getLayoutInflater();
        ad.setInverseBackgroundForced(true);

        final View dialog = inflater.inflate(R.layout.dialog_for_already_mark_duty, null);
        ad.setView(dialog);
        TextView shiftNameTV = dialog.findViewById(R.id.shiftNameTV);
        TextView unitNameTV = dialog.findViewById(R.id.unitNameTV);
        TextView duty_INTime_TV = dialog.findViewById(R.id.duty_INTime_TV);
        LinearLayout linearLY = dialog.findViewById(R.id.linearLY);
        unitNameTV.setText(dailyAttendanceDetail.getOTHER_DUTY_UNIT_NAME());
        shiftNameTV.setText(dailyAttendanceDetail.getOTHER_DUTY_SHIFT_NAME());
        duty_INTime_TV.setText(dailyAttendanceDetail.getACTSTARTTIME().split(" ")[1]);

        final AlertDialog alertDialog = ad.create();
//    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCanceledOnTouchOutside(false);
        linearLY.setOnClickListener(view -> {
            if (dialogCallback != null) {
                dialogCallback.dialog_onClick(requestCode);
            }
            if (alertDialog != null)
                alertDialog.dismiss();
        });
        alertDialog.show();
    }

    public static void showInfoDialog(AppCompatActivity context,
                                      String titleString,
                                      String messageString,
                                      String okText,
                                      String canceltext,
                                      boolean isCancelVisile,
                                      final int requestCode,
                                      final DialogCallback dialogCallback,
                                      final Object object) {

        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.DialogBox);
        LayoutInflater inflater = context.getLayoutInflater();
        ad.setInverseBackgroundForced(true);
        final View dialogView = inflater.inflate(R.layout.layout_dialog_box, null);
        ad.setView(dialogView);
        final TextView message = dialogView.findViewById(R.id.message);
        final TextView okButton = dialogView.findViewById(R.id.ok_action);
        final LinearLayout linearLY = dialogView.findViewById(R.id.linearLY);
        final TextView title = dialogView.findViewById(R.id.dialog_title);
        final TextView cancel = dialogView.findViewById(R.id.cancel_action);
        if (isCancelVisile) {
            cancel.setVisibility(View.VISIBLE);

        } else {
            linearLY.setGravity(Gravity.CENTER);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
//            params.gravity = Gravity.CENTER;
//            okButton.setLayoutParams(params);

            cancel.setVisibility(View.GONE);
        }


        if (CSStringUtil.isNonEmptyStr(titleString)) {
            title.setText(titleString);
        } else {
            title.setVisibility(View.GONE);
        }

        if (CSStringUtil.isNonEmptyStr(messageString)) {
            message.setText(messageString);
            message.setVisibility(View.VISIBLE);
            message.setTextSize(16);
        }

        if (CSStringUtil.isNonEmptyStr(okText)) {
            okButton.setText(okText);
        }

        if (isCancelVisile) {
            cancel.setVisibility(View.VISIBLE);
        }

        if (CSStringUtil.isNonEmptyStr(canceltext)) {
            cancel.setText(canceltext);
        }
      /*  okButton.setTextColor(CSApplicationHelper.application().getResources().getColor(R.color.black));
        cancel.setTextColor(CSApplicationHelper.application().getResources().getColor(R.color.black));*/

        final AlertDialog alertDialog = ad.create();
       /* if(Build.VERSION.SDK_INT >= 23) {
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }else {
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }*/

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                alertDialog.dismiss();
                if (dialogCallback != null) {
                    dialogCallback.dialog_onCancel();
                }
            }
            return true;
        });
        okButton.setOnClickListener(v -> {

            if (dialogCallback != null) {
                dialogCallback.dialog_onClick(requestCode);
            }
            if (alertDialog != null)
                alertDialog.dismiss();
        });
        cancel.setOnClickListener(v -> {
            if (dialogCallback != null) {
                dialogCallback.dialog_onCancel();
            }
            alertDialog.dismiss();
        });
        Activity activity = (Activity) context;

        if (!activity.isFinishing()) {

            alertDialog.show();
        }
    }

    public static void staticDialog(AppCompatActivity context,
                                    String titleString,
                                    String messageString,
                                    String okText,
                                    String canceltext,
                                    boolean isCancelVisile,
                                    final int requestCode,
                                    final Object object) {

        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.DialogBox);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_dialog_box, null);
        ad.setView(dialogView);

        final TextView message = dialogView.findViewById(R.id.message);

        final TextView okButton = dialogView.findViewById(R.id.ok_action);
        final LinearLayout linearLY = dialogView.findViewById(R.id.linearLY);
        final TextView title = dialogView.findViewById(R.id.dialog_title);
        final TextView cancel = dialogView.findViewById(R.id.cancel_action);
        if (isCancelVisile) {
            cancel.setVisibility(View.VISIBLE);

        } else {
            linearLY.setGravity(Gravity.CENTER);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
//            params.gravity = Gravity.CENTER;
//            okButton.setLayoutParams(params);
            cancel.setVisibility(View.GONE);
        }


        if (CSStringUtil.isNonEmptyStr(titleString)) {
            title.setText(titleString);
        } else {
            title.setVisibility(View.GONE);
        }

        if (CSStringUtil.isNonEmptyStr(messageString)) {
            message.setText(messageString);
            message.setVisibility(View.VISIBLE);
            message.setTextSize(16);
        }

        if (CSStringUtil.isNonEmptyStr(okText)) {
            okButton.setText(okText);
        }

        if (isCancelVisile) {
            cancel.setVisibility(View.VISIBLE);
        }

        if (CSStringUtil.isNonEmptyStr(canceltext)) {
            cancel.setText(canceltext);
        }
      /*  okButton.setTextColor(CSApplicationHelper.application().getResources().getColor(R.color.black));
        cancel.setTextColor(CSApplicationHelper.application().getResources().getColor(R.color.black));*/


        final AlertDialog alertDialog = ad.create();
//        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                alertDialog.dismiss();

            }
            return true;
        });

        okButton.setOnClickListener(v -> {


            if (alertDialog != null)
                alertDialog.dismiss();
        });
        cancel.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    public static void showUnitName(String unitName) {

        Dialog dialog = new Dialog(CSApplicationHelper.application().getActivity(), android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(R.layout.single_text_view);
        TextView unitNameTV = dialog.findViewById(R.id.unitNameTV);
        AppCompatImageView cancelImg = dialog.findViewById(R.id.cancelImg);
        unitNameTV.setText(unitName);
        cancelImg.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

}