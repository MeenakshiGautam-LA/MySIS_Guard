package com.sisindia.mysis.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.BuildCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sisindia.mysis.LanguageActivity;
import com.sisindia.mysis.R;
import com.sisindia.mysis.ThemeHelper;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;


public class DialogClass extends Dialog implements
        View.OnClickListener {
    private View fragmentView;
    private BaseActivity csActivity;

    public DialogClass(Context context, View fragmentView) {
        super(context);
        this.fragmentView = fragmentView;
    }

    public DialogClass(Context context) {
        super(context);

    }

   /* public DialogClass(Context context, View fragmentView, BaseActivity csActivity1) {
        super(context);
        this.fragmentView = fragmentView;

        this.csActivity = csActivity1;

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_contact_successfully_request);
    }

    @Override
    public void onClick(View view) {

    }

    public void openBottomMenuBar(final BaseActivity mContext) {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mContext);
        View sheetView = mBottomSheetDialog.getLayoutInflater().inflate(R.layout.bottom_menu_bar_layout, null);
        mBottomSheetDialog.setContentView(sheetView);
//        setupFullHeight(mBottomSheetDialog,mContext);
        LinearLayout dutyMenuLinear = sheetView.findViewById(R.id.dutyMenuLinear);
        ToggleButton mode_dark_on_of_TB = sheetView.findViewById(R.id.mode_dark_on_of_TB);
        LinearLayout langMenuLinear = sheetView.findViewById(R.id.langMenuLinear);
        LinearLayout checkUpdateLY = sheetView.findViewById(R.id.checkUpdateLY);
        TextView app_version_TV = sheetView.findViewById(R.id.app_version_TV);
        try {
            app_version_TV.setText(mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LinearLayout calenderMenuLinear = sheetView.findViewById(R.id.calenderMenuLinear);
        LinearLayout syncMenuLinear = sheetView.findViewById(R.id.syncMenuLinear);
        LinearLayout profileMenuLinear = sheetView.findViewById(R.id.profileMenuLinear);

        LinearLayout helpMenuLinear = sheetView.findViewById(R.id.helpMenuLinear);
        LinearLayout salaryMenuLinear = sheetView.findViewById(R.id.salMenuLinear);
        LinearLayout leaveMenuLinear = sheetView.findViewById(R.id.leaveMenuLinear);
        //   LinearLayout salaryMenuLinear = sheetView.findViewById(R.id.salaryMenuLinear);
        //   LinearLayout leaveMenuLinear = sheetView.findViewById(R.id.leaveMenuLinear);


        LinearLayout sulabhLoanLinear = sheetView.findViewById(R.id.sulabhLoanLinear);
        LinearLayout akrMenuLinear = sheetView.findViewById(R.id.akrMenuLinear);
        LinearLayout notificationLY = sheetView.findViewById(R.id.notificationLY);
        checkUpdateLY.setOnClickListener(view -> {
            CSAppUtil.openBottomSheetfragment(mContext, R.id.checkUpdate);
            mBottomSheetDialog.dismiss();

        });
        akrMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();

                show_Dialog(CSStringUtil.getString(R.string.comming_soon));
//                mContext.startActivity(new Intent(mContext, AnnualKitReplacmentActivity.class));
            }
        });

        sulabhLoanLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                show_Dialog(CSStringUtil.getString(R.string.comming_soon));
//                mContext.startActivity(new Intent(mContext, SulabhLoanActivity.class));
            }
        });

        profileMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CSAppUtil.openBottomSheetfragment(mContext, R.id.guardProfile);
                mBottomSheetDialog.dismiss();

//        CSAppUtil.openFragmentByNavigation(fragmentView,R.id.guardProfile);
            }
        });
        notificationLY.setOnClickListener(view -> {
            CSAppUtil.openBottomSheetfragment(csActivity, R.id.notification);
            mBottomSheetDialog.dismiss();
        });
        calenderMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CSAppUtil.openBottomSheetfragment(mContext, R.id.CalendarLeavePresent);
                mBottomSheetDialog.dismiss();

//                mContext.startActivity(new Intent(mContext, DutyLeaveCalendarActivity.class));
            }
        });

        dutyMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CSAppUtil.openBottomSheetfragmentWithStack(mContext, R.id.markAttendance, true);
                mBottomSheetDialog.dismiss();

            }
        });
        syncMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CSAppUtil.openBottomSheetfragment(mContext, R.id.synServer);
                mBottomSheetDialog.dismiss();
//                mContext.startActivity(new Intent(mContext, Sync_landing_img.class));
            }
        });

        helpMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();
                show_Dialog(CSStringUtil.getString(R.string.comming_soon));
//                mContext.startActivity(new Intent(mContext, HelpActivity.class));
            }
        });

        salaryMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();
                show_Dialog(CSStringUtil.getString(R.string.comming_soon));
//                mContext.startActivity(new Intent(mContext, SalarySlipActivity.class));
            }
        });
        if (CSShearedPrefence.isNightModeEnabled()) {
            mode_dark_on_of_TB.setChecked(true);
        }
        else {
            mode_dark_on_of_TB.setChecked(false);
        }
        mode_dark_on_of_TB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    ThemeHelper.applyTheme(ThemeHelper.DARK_MODE);


            /* if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                 if (BuildCompat.isAtLeastQ()) {
                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                 } else {
                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                 }
             }

                else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme
                    }*/
                    CSShearedPrefence.setNightModeEnabled(true);
                    Toast.makeText(mContext, CSStringUtil.getString(R.string.Dark_Mode_Enabled), Toast.LENGTH_LONG).show();
                }
                else
                    {
                        ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE);
                  /*  if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//                        csActivity.setTheme((R.style.DigitalAppTheme_Android_Q_Disabled));
                        if (BuildCompat.isAtLeastQ()) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                        }
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //For night mode off theme

                    }*/
                    Toast.makeText(mContext, CSStringUtil.getString(R.string.Dark_Mode_Disabled), Toast.LENGTH_LONG).show();
                    CSShearedPrefence.setNightModeEnabled(false);
                }
                Intent i = mContext.getPackageManager().getLaunchIntentForPackage(
                        mContext.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(i);
                mContext.finish();
            }
        });

        leaveMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
//                show_Dialog(CSStringUtil.getString(R.string.comming_soon));//
                CSAppUtil.openBottomSheetfragment(mContext, R.id.LeaveStatusFragment);


            }
        });
        mBottomSheetDialog.show();
    }

    public static void show_Dialog(String unitName) {
//        Dialog dialog = new Dialog(CSApplicationHelper.application().getActivity(), android.R.style.Theme_Light);
        Dialog dialog = new Dialog(CSApplicationHelper.application().getActivity(), R.style.DialogBox);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_box);
        final TextView okButton = dialog.findViewById(R.id.ok_action);
        final TextView title = dialog.findViewById(R.id.dialog_title);
        final TextView cancel = dialog.findViewById(R.id.cancel_action);
        cancel.setVisibility(View.GONE);
        title.setText(unitName);
        okButton.setOnClickListener(view -> dialog.dismiss());
        if (!CSApplicationHelper.application().getActivity().isFinishing()) {
            dialog.show();
        }
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog, AppCompatActivity activity) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight(activity);
        if (layoutParams != null) {
            layoutParams.height = (windowHeight);
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight(AppCompatActivity activity) {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
