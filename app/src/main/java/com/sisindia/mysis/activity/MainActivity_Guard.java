package com.sisindia.mysis.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.BuildConfig;
import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.App_Update_Model;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DialogClass;
import com.sisindia.mysis.utils.SyncUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sisindia.mysis.utils.Constants.GET_CHECK_UPDATE;

public class MainActivity_Guard extends BaseActivity implements SetDateTimeDialog.iSetDateTimeDialogListener, Volley_Asynclass_Get.VolleyResponse {
    @BindView(R.id.footerBottomSheet_LY)
    LinearLayout footerBottomSheet_LY;
    @BindView(R.id.calenderLY)
    LinearLayout dutyLY;
    @BindView(R.id.menuLY)
    LinearLayout menuLY;
    @BindView(R.id.dutyLY)
    LinearLayout homeLY;
    @BindView(R.id.duty_IN_IV)
    AppCompatImageView duty_IN_IV;
    @BindView(R.id.calender_IV)
    AppCompatImageView calender_IV;
    private SetDateTimeDialog dateTimeDialog;
    private static final int SET_DATE_REQUEST_CODE = 112;

    @Override
    public int getScreenName() {
        return R.string.app_name;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CFUtil.screenShotDisableInApp(this);
        setContentView(R.layout.guard_main_layout);
        ButterKnife.bind(this);
        FirebaseCrashlytics firebaseCrashlytics = FirebaseCrashlytics.getInstance();
        firebaseCrashlytics.setUserId(CSShearedPrefence.getUser());
        firebaseCrashlytics.setCustomKey("USERNAME", CSShearedPrefence.getLoggedInUserName());
        CSApplicationHelper.application().setActivity(this);
        initializeApiProcess();
//        checkForBackgroundRunning();

        menuLY.setOnClickListener(view -> {
            if (CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().checkEmployeeIs_Active(CSShearedPrefence.getUser())) {
                DialogClass dialogClass = new DialogClass(this);
                dialogClass.openBottomMenuBar(this);
            } else {
                CSDialogUtil.showInfoDialog(this, CSStringUtil.getString(R.string.user_not_active));
            }
        });
        dutyLY.setOnClickListener(view -> {
            if (CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().checkEmployeeIs_Active(CSShearedPrefence.getUser())) {
//                duty_IN_IV.setImageResource(R.drawable.icon_duty);
//                calender_IV.setImageResource(R.drawable.calender_selected_background);
                CSAppUtil.openBottomSheetfragment(this, R.id.CalendarLeavePresent);
                duty_IN_IV.setImageResource(R.drawable.duty_white_icon);
                calender_IV.setImageResource(R.drawable.duty_selected);
            } else {
                CSDialogUtil.showInfoDialog(this, CSStringUtil.getString(R.string.user_not_active));
            }
        });
        homeLY.setOnClickListener(view -> {
            if (CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().checkEmployeeIs_Active(CSShearedPrefence.getUser())) {
//                duty_IN_IV.setImageResource(R.drawable.duty_in_selected_background);
//                calender_IV.setImageResource(R.drawable.icon_calendar);
                duty_IN_IV.setImageResource(R.drawable.home_selected);
                calender_IV.setImageResource(R.drawable.duty_un_selected);
                CSAppUtil.openBottomSheetfragmentWithStack(this, R.id.markAttendance, true);
            } else {
                CSDialogUtil.showInfoDialog(this, CSStringUtil.getString(R.string.user_not_active));
            }
        });
    }

    private void initializeApiProcess() {
        try {
            if (!CSShearedPrefence.getInstance().getBoolean("AlreadyLoggedIn", false)) {
                Log.e("LocationDetector", "SYN INSIDE");
                SyncUtils.CreateSyncAccount(this);
            }
            CSShearedPrefence.setBooleanPreference(this, "AlreadyLoggedIn", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show_App_Update_Dialog(App_Update_Model app_update_model) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_app_update);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Button update_now_btn = dialog.findViewById(R.id.update_now_btn);
        update_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(app_update_model.getFILE_URL()));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CSApplicationHelper.application().setActivity(this);
//        duty_IN_IV.setImageResource(R.drawable.duty_white_icon);
//        calender_IV.setImageResource(R.drawable.duty_un_selected);
        if (!BuildConfig.DEBUG) {
            new Volley_Asynclass_Get(this::getDataFromVolleyInterFace, CSApplicationHelper.application().getPostDataParam(),
                    GET_CHECK_UPDATE, "APP_UPDATE");
            if (!((MainActivity_Guard) this).isFinishing()) {
                CSApplicationHelper.application().getDatabaseInstance().app_update_dao().getData().observe(this, app_update_model1 -> {
                    if (app_update_model1 != null) {
                        int app_ver_code = 0;
                        try {
                            app_ver_code = getPackageManager().getPackageInfo(CSApplicationHelper.application().
                                    getPackageName(), 0).versionCode;
                            if (app_ver_code < Integer.parseInt(app_update_model1.getVERSION_CODE())) {
                                show_App_Update_Dialog(app_update_model1);
                        /*CSApplicationHelper.application().getDatabaseInstance().app_update_dao().checkApp_Update().
                                observe(this, app_update_model -> {
                                    if (app_update_model!= null) {
                                        show_App_Update_Dialog(app_update_model);
                                    }else {
                                        PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.
                                                Builder(CheckAppUpdate_Worker.class,15, TimeUnit.MINUTES)
                                                .setBackoffCriteria(
                                                        BackoffPolicy.LINEAR,
                                                        PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                                                        TimeUnit.MILLISECONDS)

                                                .build();
                                        WorkManager.getInstance().enqueue(periodicWorkRequest);


                                    }
                                });*/
                            } else {
                                /*PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.
                                        Builder(CheckAppUpdate_Worker.class, 15, TimeUnit.MINUTES)
                                        .setBackoffCriteria(
                                                BackoffPolicy.LINEAR,
                                                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                                                TimeUnit.MILLISECONDS)
                                        .build();
                                WorkManager.getInstance().enqueue(periodicWorkRequest);*/
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else {
                       /* PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.
                                Builder(CheckAppUpdate_Worker.class, 15, TimeUnit.MINUTES)
                                .setBackoffCriteria(
                                        BackoffPolicy.LINEAR,
                                        PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                                        TimeUnit.MILLISECONDS)

                                .build();
                        WorkManager.getInstance().enqueue(periodicWorkRequest);*/
                    }
                });

                if (!isTimeAutomaticTimeZone(this)) {
                    //show dialog
                    dateTimeDialog = new SetDateTimeDialog(MainActivity_Guard.this, "Automatic Time Zone is Disabled,\n\nPlease On for use application.",
                            this);
                    dateTimeDialog.setCancelable(false);
                    dateTimeDialog.show();
                } else if (!isTimeAutomaticTime(this)) {
                    //show dialog
                    dateTimeDialog = new SetDateTimeDialog(MainActivity_Guard.this, "Automatic Date and Time is Off,\n\nPlease On for use application.",
                            this);
                    dateTimeDialog.setCancelable(false);
                    dateTimeDialog.show();
                }
            }
        }
    }

    public boolean isTimeAutomaticTimeZone(Context c) {
        return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) == 1;
    }

    public static boolean isTimeAutomaticTime(Context c) {
        return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  duty_IN_IV.setImageResource(R.drawable.duty_white_icon);
        calender_IV.setImageResource(R.drawable.duty_un_selected);*/
    }

    @Override
    public void openDateSettings() {
        startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), SET_DATE_REQUEST_CODE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (dateTimeDialog != null) {
            dateTimeDialog.dismiss();
        }
    }


    private void checkForBackgroundRunning() {
        Intent intent = new Intent();

        String manufacturer = android.os.Build.MANUFACTURER.toLowerCase();

        switch (manufacturer) {

            case "xiaomi":
                intent.setComponent(new ComponentName("com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                break;
            case "oppo":
                intent.setComponent(new ComponentName("com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity"));

                break;
            case "vivo":
                intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                break;
        }

        List<ResolveInfo> arrayList = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (arrayList.size() > 0) {
            startActivity(intent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent2 = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent2.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent2.setData(Uri.parse("package:" + packageName));
                startActivity(intent2);
            }
        }
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {

        Log.e("APP_UPDATE>>", "" + loadedString);
        App_Update_Model app_update_model;
        if (regNO.equals("APP_UPDATE")) {
            try {
                JSONObject response = new JSONObject(loadedString);
                if (response.optString("status").equals("true")) {

                    JSONArray jsonArray = response.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        Type listType = new TypeToken<App_Update_Model>() {
                        }.getType();
                        app_update_model = new Gson().fromJson(jsonArray.getJSONObject(0).toString(), listType);
                        int app_ver_code = CSApplicationHelper.application().getPackageManager().getPackageInfo(CSApplicationHelper.application().
                                getPackageName(), 0).versionCode;
                        if (app_ver_code < Integer.parseInt(app_update_model.getVERSION_CODE())) {
//                            show_App_Update_Dialog();
                            app_update_model.setFLAG("1");
                            CSApplicationHelper.application().getDatabaseInstance().app_update_dao().insert(app_update_model);
                      /*  downloadUpdateBTN.setOnClickListener(view -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sisindia.guardattendance"));
                            startActivity(intent);
                        });*/
                        } else {
                            CSApplicationHelper.application().getDatabaseInstance().app_update_dao().delete(app_update_model);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void changeFooter_Icon_Color_from_Fragment(int value) { //value= 0 for home , value =1 for duty
        if (value == 0) {
            duty_IN_IV.setImageResource(R.drawable.home_selected);
            calender_IV.setImageResource(R.drawable.duty_un_selected);
        } else {
            duty_IN_IV.setImageResource(R.drawable.home_un_selected);
            calender_IV.setImageResource(R.drawable.duty_selected);
        }
    }

}
