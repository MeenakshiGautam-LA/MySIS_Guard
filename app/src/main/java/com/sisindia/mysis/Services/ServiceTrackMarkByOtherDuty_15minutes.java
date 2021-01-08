package com.sisindia.mysis.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.sisindia.mysis.BuildConfig;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.PeriodicTrackingInfoModel;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.DateUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ServiceTrackMarkByOtherDuty_15minutes extends Service {
    String CHANNEL_ID = "my_channel_01";
    private Timer timer;
    private TimerTask timerTask;

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
        }
//        addNotification();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("CHECKING ATTENDANCE")
                .setSmallIcon(R.mipmap.notification_icon)
                .setContentText("SIS").build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(getApplicationContext(), "RUNNING", Toast.LENGTH_LONG).show();
        if (CSApplicationHelper.application().getDatabaseInstance() != null) {

            timer = new Timer();

        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();


    }

    private int batteryLevel() {
        int batteryPercentage = 0;
        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            batteryPercentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }
        return batteryPercentage;
    }

    private void createJsonForStore(double latitude, double longitude) {
        try {
            JSONObject json = new JSONObject();
            String id = UUID.randomUUID().toString();
            json.put("ID", id);
            json.put("LATITUDE", String.valueOf(latitude));
            json.put("LONGITUDE", String.valueOf(longitude));
            json.put("TRACKING_DATE", getCurrentDate_TIME_format());
            json.put("DATE_MODIFIED", getCurrentDate_TIME_format());
            json.put("DEVICE_ID", CSShearedPrefence.getDeviceToken());
            json.put("IS_LOGGED_IN", CSShearedPrefence.isLoggedIn());
            json.put("CREATED_BY", CSShearedPrefence.getUser_ID());
            json.put("APP_VERSION", BuildConfig.VERSION_CODE);
            json.put("APP_VERSION_NAME", BuildConfig.VERSION_NAME);
            json.put("BATTERY_LEVEL", batteryLevel());

            PeriodicTrackingInfoModel periodicTrackingInfoModel = new PeriodicTrackingInfoModel();
            periodicTrackingInfoModel.setDATE_MODIFIED(getCurrentDate_TIME_format());
            periodicTrackingInfoModel.setFLAG("1");
            periodicTrackingInfoModel.setID(String.valueOf(UUID.randomUUID()));
            periodicTrackingInfoModel.setJSON(json.toString());
            CSApplicationHelper.application().getDatabaseInstance().trackingInfoDAO().insert(periodicTrackingInfoModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public static String getCurrentDate_TIME_format() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        return date;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
