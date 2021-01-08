package com.sisindia.mysis.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.sisindia.mysis.GetWorkManagers.ChangeContactNoWorker;
import com.sisindia.mysis.GetWorkManagers.EmployeeDetailWorker;
import com.sisindia.mysis.GetWorkManagers.Flash_Message_Worker;
import com.sisindia.mysis.GetWorkManagers.GetAttendanceWork;
import com.sisindia.mysis.GetWorkManagers.GetLeaveMasterWorker;
import com.sisindia.mysis.GetWorkManagers.GetLeaveTransactionWorker;
import com.sisindia.mysis.GetWorkManagers.GetLeaveTypeListWorker;
import com.sisindia.mysis.GetWorkManagers.GetProfile_Unit_DetailWorker;
import com.sisindia.mysis.GetWorkManagers.GetRosterWorker;
import com.sisindia.mysis.GetWorkManagers.GetShift_Master_Work;
import com.sisindia.mysis.GetWorkManagers.GetUnitDutyPostDetail_WORK;
import com.sisindia.mysis.GetWorkManagers.NotificationWorker;
import com.sisindia.mysis.GetWorkManagers.Services_Info_Work;
import com.sisindia.mysis.GetWorkManagers.Site_Detail_Work;
import com.sisindia.mysis.PostWorkerManager.UpdateFcmTokenWork;
import com.sisindia.mysis.R;
import com.sisindia.mysis.Services.TrackingService;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplication;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.DailyAttendanceDetail;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

//import com.sisindia.guardattendance.GetWorkManagers.NotificationWorker;


public class CFUtil {
    private static final int MINTUES = 6000;

    //    public static void loadlocalFile(String localFileName,final ;LocalFileTaskCallback taskCallback) {
//        String responseString = getFileContent(localFileName);
//        if (taskCallback != null) {
//            taskCallback.onTaskComplete(responseString);
//        }
//    }
    public static String parameter_Format(String appendTag, String tag1, String tagValue1, String tag2, String tagValue2) {
        String concatTag = null;
        concatTag = appendTag + "?" + tag1 + "=" + tagValue1 + "&&" + tag2 + "=" + tagValue2;

        return concatTag;
    }

    public static String parameter_Format_single(String appendTag, String tag1, String tagValue1) {
        String concatTag = null;
        concatTag = appendTag + "?" + tag1 + "=" + tagValue1;

        return concatTag;
    }

    public static void screenShotDisableInApp(BaseActivity context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
    }


    public static String getFileContent(String fileName) {
        return streamToString(getFileFromAsset(fileName));
    }

    public static InputStream getFileFromAsset(String fileName) {
        try {
            if (isNonEmptyStr(fileName)) {
                return CSApplicationHelper.application().getActivity().getAssets().open(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isNonEmptyStr(String fileName) {
        return !isEmptyStr(fileName);
    }

    public static boolean isEmptyStr(String _v) {
        return _v == null || _v.trim().length() == 0 || _v.equalsIgnoreCase("null");
    }

    public static String streamToString(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            StringBuilder fileContent = new StringBuilder();
            String str;
            while ((str = bufferReader.readLine()) != null) {
                fileContent.append(str.trim());
            }
            return fileContent.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void call_Get_DataFrom_Server() {
        WorkManager.getInstance().cancelAllWork();
        /*if (!BuildConfig.DEBUG) {
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.
                    Builder(CheckAppUpdate_Worker.class, 15, TimeUnit.MINUTES)
                    .setBackoffCriteria(
                            BackoffPolicy.LINEAR,
                            PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                            TimeUnit.MILLISECONDS)
                    .build();
            WorkManager.getInstance().enqueueUniquePeriodicWork("CheckAppUpdate", ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);
        }*/
        OneTimeWorkRequest getRosterWorker = new OneTimeWorkRequest.Builder(GetRosterWorker.class).build();
        OneTimeWorkRequest getProfile_Unit_Detail = new OneTimeWorkRequest.Builder(GetProfile_Unit_DetailWorker.class).build();
        OneTimeWorkRequest getLeaveTransactionWorker = new OneTimeWorkRequest.Builder(GetLeaveTransactionWorker.class).build();
        OneTimeWorkRequest getDailyAttendanceWorker = new OneTimeWorkRequest.Builder(GetAttendanceWork.class).build();
        OneTimeWorkRequest services_Info_Work = new OneTimeWorkRequest.Builder(Services_Info_Work.class).build();
        OneTimeWorkRequest employeeDetailWorker = new OneTimeWorkRequest.Builder(EmployeeDetailWorker.class).build();
        OneTimeWorkRequest notificationWorker = new OneTimeWorkRequest.Builder(NotificationWorker.class).build();
        OneTimeWorkRequest getSiteDetailWorker = new OneTimeWorkRequest.Builder(Site_Detail_Work.class).build();
//        OneTimeWorkRequest employeePicWorker = new OneTimeWorkRequest.Builder(EmployeePic_Work.class).build();
        OneTimeWorkRequest changeContactNoWorker = new OneTimeWorkRequest.Builder(ChangeContactNoWorker.class).build();
        OneTimeWorkRequest getShiftMasterWorker = new OneTimeWorkRequest.Builder(GetShift_Master_Work.class).build();
        OneTimeWorkRequest getUnitDutyPostDetail_WORK = new OneTimeWorkRequest.Builder(GetUnitDutyPostDetail_WORK.class).build();
        OneTimeWorkRequest updateFcmTokenWork = new OneTimeWorkRequest.Builder(UpdateFcmTokenWork.class).build();
        OneTimeWorkRequest getLeaveMasterWorker = new OneTimeWorkRequest.Builder(GetLeaveMasterWorker.class).build();
        OneTimeWorkRequest getLeaveTypeListWorker = new OneTimeWorkRequest.Builder(GetLeaveTypeListWorker.class).build();
        OneTimeWorkRequest flash_Message_Worker = new OneTimeWorkRequest.Builder(Flash_Message_Worker.class).build();
        WorkManager.getInstance()
                .beginWith(getShiftMasterWorker)
                .then(getProfile_Unit_Detail)
                .then(getUnitDutyPostDetail_WORK)
                .then(getLeaveTransactionWorker)
                .then(getLeaveMasterWorker)
                .then(getLeaveTypeListWorker)
                .then(notificationWorker)
                .then(flash_Message_Worker)
                .then(getRosterWorker, services_Info_Work, getSiteDetailWorker)
                .then(employeeDetailWorker, changeContactNoWorker, getDailyAttendanceWorker, updateFcmTokenWork)
                .enqueue();
    }

    private void ShowToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();


        Log.e("WIDTH  :", String.valueOf(location.width()));
        Log.e("HEIGHT  :", String.valueOf(location.height()));
        Log.e("left  :", String.valueOf(location.left));
        Log.e("right :", String.valueOf(location.right));
        Log.e("top   :", String.valueOf(location.top));
        Log.e("bottom :", String.valueOf(location.bottom));
        return location;
    }

    public static byte[] getImageBYTE(Context context, String base64ImageData) {
        FileOutputStream fos = null;
        byte[] decodedString = null;
        try {
            if (base64ImageData != null) {
                fos = context.openFileOutput("imageName.png", Context.MODE_PRIVATE);
                decodedString = android.util.Base64.decode(base64ImageData, android.util.Base64.DEFAULT);
                fos.write(decodedString);
                fos.flush();
                fos.close();
            }

        } catch (Exception e) {

        } finally {
            if (fos != null) {
                fos = null;
            }
        }
        return decodedString;
    }

    public static String returnHourPeriod(String time) {
        String period = null;
        if (Integer.parseInt(time.split(":")[0]) < 12) {
            return "AM";
        } else if (Integer.parseInt(time.split(":")[0]) > 12) {
            return "PM";
        } else if (Integer.parseInt(time.split(":")[0]) == 12) {
            if (Integer.parseInt(time.split(":")[1]) == 00) {
                return "Noon";
            } else {
                return "PM";
            }

        }
        return period;
    }

    public static String convertString(String str) {
        String returnString = "";
        if (CSStringUtil.isEmptyStr(str)) {
            return returnString;
        } else {
            // Create a char array of given String
            char ch[] = str.toCharArray();
            for (int i = 0; i < str.length(); i++) {

                // If first character of a word is found
                if (i == 0 && ch[i] != ' ' ||
                        ch[i] != ' ' && ch[i - 1] == ' ') {

                    // If it is in lower-case
                    if (ch[i] >= 'a' && ch[i] <= 'z') {

                        // Convert into Upper-case
                        ch[i] = (char) (ch[i] - 'a' + 'A');
                    }
                }

                // If apart from first character
                // Any one is in Upper-case
                else if (ch[i] >= 'A' && ch[i] <= 'Z')

                    // Convert into Lower-Case
                    ch[i] = (char) (ch[i] + 'a' - 'A');
            }

            // Convert the char array to equivalent String
            returnString = new String(ch);
            return returnString;
        }
    }

    public static Drawable leaveReasonBackGround(int colorCode) {
        Drawable value;

        value = changeDrawableShapeColorDynamically(CSApplicationHelper.application(), R.drawable.leave_type_circle, colorCode);
        return value;
    }

    public static int returnLeaveReasonDescriptionText(String leaveId) {
        int value;
        switch (leaveId) {
//            case "1":
//                value = R.string.sick_txt;
//                break;
//            case "2":
//                value = R.string.sick_txt;
//                break;
//            case "3":
//                value = R.string.sick_txt;
//                break;
//            case "4":
//                value = R.string.sick_txt;
//                break;
//            case "7":
//                value = R.string.sick_txt;
//                break;
            case "7":
                value = R.string.sick_txt;
                break;
            case "8":
                value = R.string.family_emergency_txt;
                break;
            case "9":
                value = R.string.other_reason_txt;
                break;
            default:
                value = 0;


        }
        return value;
    }

    public static int returnLeaveStatusDescription(String leaveId) {
        int value;
        switch (leaveId) {

            case "0":
                value = R.string.pending_For_Approved;
                break;
            case "1":
                value = R.string.leave_Approved;
                break;
            case "2":
                value = R.string.leave_cancelled;
                break;

            default:
                value = R.string.pending_For_Approved;
        }
        return value;
    }

    public static String appRules(int leaveId) {
        String value;
        switch (leaveId) {

            case R.string.face_not_detect:
                value = "13";
                break;

            default:
                value = "0";
        }
        return value;
    }

    public static int returnLeaveReasonID(int leaveId) {
        int value;
        switch (leaveId) {

            case R.string.sick_txt:
                value = 7;
                break;
            case R.string.family_emergency_txt:
                value = 8;
                break;
            case R.string.other_reason_txt:
                value = 9;
                break;

            default:
                value = 0;
        }
        return value;
    }

    public static String maskPhoneNumberinStartFormat(String inputPhoneNum) {
        return inputPhoneNum.replaceAll("\\(", "-")
                .replaceAll("\\)", "-")
                .replaceAll(" ", "-")
                .replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static int setAppVersionName(Context context) {
        int app_ver_code = 0;
        try {
            app_ver_code = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return app_ver_code;
    }

    private static Drawable changeDrawableShapeColorDynamically(CSApplication application, int drawable, int colorCode) {
        Drawable mDrawable = ContextCompat.getDrawable(application, drawable);
        mDrawable.setColorFilter(new PorterDuffColorFilter(colorCode, PorterDuff.Mode.MULTIPLY));

        /* final int sdk = android.os.Build.VERSION.SDK_INT;
         *//*  if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            value = drawable;
            holder.rv_day_tv.setBackgroundDrawable(mDrawable);
        } else {
            value = drawable;
            holder.rv_day_tv.setBackground(mDrawable);
        }*/
        return mDrawable;
    }

    public static String getKeyDataFromJSON(String json, String key) {
        String keyValue = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has(key)) {
                keyValue = jsonObject.getString(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyValue;
    }

    public static void checkAttendanceToCallService_When_Attendance_FromOther_Duty(Context context) {
        if (CSApplicationHelper.application().getDatabaseInstance() != null) {


            DailyAttendanceDetail dailyAttendanceDetail = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                    checkAttendancePunchToday(CSShearedPrefence.getUser(), DateUtils.getCurrentDate_format());

            DailyAttendanceDetail dailyAttendanceDetail_previous_day = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                    checkAttendancePunchToday(CSShearedPrefence.getUser(), DateUtils.getInstance().getPreviousDateCalender(DateUtils.getCurrentDate_format()));
            if (dailyAttendanceDetail_previous_day != null) {
                if (dailyAttendanceDetail_previous_day.getDuty_status().equalsIgnoreCase("DUTY_OUT")) {
                    try {
                        if (CFUtil.isMyServiceRunning(context, TrackingService.class)) {
                            Intent mServiceIntent = new Intent(context, TrackingService.class);
                            context.stopService(mServiceIntent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (dailyAttendanceDetail != null) {
                Log.e("IN_SERVICE>>>>>>", dailyAttendanceDetail.getREGNO());
                if (dailyAttendanceDetail.getDuty_status().equalsIgnoreCase("DUTY_IN")) {
                    try {
                        if (CFUtil.isMyServiceRunning(context, TrackingService.class)) {
                        } else {
                            Intent mServiceIntent = new Intent(context, TrackingService.class);
                            if (Build.VERSION.SDK_INT >= 26) {
                                context.startForegroundService(mServiceIntent);
                            } else {
                                context.startService(mServiceIntent);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
//                    if (CFUtil.isMyServiceRunning(context, TrackingService.class)) {
//                    } else {
                    Intent mServiceIntent = new Intent(context, TrackingService.class);
                    context.stopService(mServiceIntent);

//                    }
                }
            }
        }
    }
}

