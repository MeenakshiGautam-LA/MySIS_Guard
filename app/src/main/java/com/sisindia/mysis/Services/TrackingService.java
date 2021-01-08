package com.sisindia.mysis.Services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.sisindia.mysis.ApiCalling_Class;
import com.sisindia.mysis.BuildConfig;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.PeriodicTrackingInfoModel;
import com.sisindia.mysis.entity.RosterModel;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static com.sisindia.mysis.utils.Constants.TRACKING_10_MINUTES;

public class TrackingService extends Service implements GoogleApiClient.ConnectionCallbacks,
        ResultCallback<LocationSettingsResult>, GoogleApiClient.OnConnectionFailedListener {
    String CHANNEL_ID = "my_channel_01";
    private Timer timer;
    private TimerTask timerTask;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;

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
                .setContentTitle("Currently You Are On Duty!!")
                //.setSmallIcon(R.mipmap.notification_icon)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText("MySIS").build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        if (CSApplicationHelper.application().getDatabaseInstance() != null) {
           /* RosterModel temp = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().
                    getshiftTime_InService(DateUtils.getCurrentDate_format(), CSShearedPrefence.getUser());*/
            detectLocationAllowed();
            syncDataTo_Server();
          /*  timer = new Timer();
            timerTask = new TimerTask() {
                public void run() {
                    getCurrentLocation();
                }
            };
            timer.schedule(timerTask, 1 * 60 * 1000, 60 * 1000 * 10); // period = 10 minutes*/
           /* if (temp != null) {
                Date SystemDateTime = DateUtils.getInstance().dateFromSaveFormat(getCurrentDate_TIME_format());
                if (SystemDateTime.after(DateUtils.getInstance().dateFromSaveFormat(temp.getEnd_time()))) {
                    stopSelf();
                }
            }*/
        }
        return START_STICKY;
    }

    private void detectLocationAllowed() {


        final Handler handler = new Handler();
        final Runnable Update = () -> {
            getCurrentLocation();
            RosterModel temp = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().
                    getshiftTime_InService(DateUtils.getCurrentDate_format(), CSShearedPrefence.getUser());
            if (temp != null) {
                Date SystemDateTime = DateUtils.getInstance().dateFromSaveFormat(getCurrentDate_TIME_format());
                if (SystemDateTime.after(DateUtils.getInstance().dateFromSaveFormat(temp.getEnd_time()))
                ||SystemDateTime.equals(DateUtils.getInstance().dateFromSaveFormat(temp.getEnd_time()))) {
                    stopSelf();
                }
            }
        };
        timer = new Timer();
        long DELAY_MS = 0;
        long PERIOD_MS = 1000*60*10;
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);




/*
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               *//* if (!stopHandler) {
                    handler.postDelayed(this, 2000);
                }*//*
                handler.postDelayed(this, 6000);

                getCurrentLocation();
                RosterModel temp = CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().
                        getshiftTime_InService(DateUtils.getCurrentDate_format(), CSShearedPrefence.getUser());
                if (temp != null) {
                    Date SystemDateTime = DateUtils.getInstance().dateFromSaveFormat(getCurrentDate_TIME_format());
                    if (SystemDateTime.after(DateUtils.getInstance().dateFromSaveFormat(temp.getEnd_time()))) {
                        stopSelf();
                    }
                }
            }
        }, 6000); // 10 minutes= 1 minutes=60*1000*/
    }

    private void syncDataTo_Server() {

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            ApiCalling_Class calling_class = new ApiCalling_Class(getApplicationContext());
            calling_class.push_All_Apis();
        };
        timer = new Timer();
        long DELAY_MS = 0;
        long PERIOD_MS = 1000*60*30;
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


      /*  final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000 * 60 * 30);
                ApiCalling_Class calling_class = new ApiCalling_Class(getApplicationContext());
                calling_class.push_All_Apis();
            }
        }, 1000 * 60 * 30); // 30 minutes or 1 minutes=60*1000*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        stoptimertask();
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }

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
            json.put("REGNO", CSShearedPrefence.getUser());
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

    private void getCurrentLocation() {
        initializeSettingApi();

    }

    private void initializeSettingApi() {


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2 * 1000);
        locationRequest.setFastestInterval(0);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest
                    , new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if (location != null) {
                                String maxModifiedDate = CSApplicationHelper.application().getDatabaseInstance().trackingInfoDAO()
                                        .getMaxModifiedDate();
                                if(!CSStringUtil.isEmptyStr(maxModifiedDate)){
                                    if (DateUtils.dateTime_Difference_In_MilliSecond(maxModifiedDate, DateUtils.getCurrentDate_TIME_format())>= TRACKING_10_MINUTES) {
                                        SaveInTable(location.getLatitude(), location.getLongitude());
                                        mGoogleApiClient.disconnect();
                                    }
                                }else {
                                    SaveInTable(location.getLatitude(), location.getLongitude());
                                    mGoogleApiClient.disconnect();
                                }
                                /*if (DateUtils.dateTime_Difference_In_MilliSecond(maxModifiedDate, DateUtils.getCurrentDate_TIME_format())>= TRACKING_10_MINUTES) {
                                    SaveInTable(location.getLatitude(), location.getLongitude());
                                }
*/

                            }
                        }
                    });
            LocationSettingsRequest.Builder builder = new
                    LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(this);
        }
    }

    private void SaveInTable(final double latitude, final double longitude) {
        try {
            createJsonForStore(latitude, longitude);
        } catch (Exception e) {                                                 //
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }
}
