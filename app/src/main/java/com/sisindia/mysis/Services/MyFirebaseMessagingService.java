package com.sisindia.mysis.Services;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.MainActivity_Guard;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.Constants;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    //    public static final String ACCOUNT_TYPE = "com.sisindia.guardattendance";
    public static final String ACCOUNT_TYPE = "com.sisindia.mysis";
    private static final String TAG = "FirebaseMessage";
    String fcm_sync;
    private boolean _showNotification = true;
    String CHANNEL_ID = "MY SIS";
    String CHANNEL_NAME = "MY SIS";
    String CHANNEL_DESCRIPTION = "MY SIS";
    String image_Url;
    Bitmap myBitmap;
    private int randomNumber;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        randomNumber = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "onNewToken: m " + remoteMessage.getNotification().getBody());
        }

        Log.i(TAG, "onMessageReceived get Url: " + remoteMessage.getData().containsKey("url"));


        if (remoteMessage.getData().size() > 0) {
            if (remoteMessage.getData().containsKey("is_sync")) {
                fcm_sync = remoteMessage.getData().get("is_sync");
                String api_name = remoteMessage.getData().get("table_name");
                Log.i(TAG, "onMessageReceived data Recieve : " + fcm_sync + " , " + api_name );
                if ((fcm_sync != null) && !fcm_sync.equalsIgnoreCase("")) {
                    if (fcm_sync.equalsIgnoreCase("1")) {
                        if ((api_name != null) && !api_name.equalsIgnoreCase("")) {
                            Bundle b = new Bundle();
                            b.putString("sync", fcm_sync);
                            b.putString("api_name", api_name);
                            b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                            b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

                            //-----------Request Sync from fcm null contains the string from server
//                            Account account = getAccount("Sync Once In a Day");
                            Account account = getAccount(Constants.SYNC_ACCOUNT_30_MINUTE);
                            ContentResolver.requestSync(account, ACCOUNT_TYPE, b);
                        }
                    }
                    else {
                        // Toast.makeText(this, "No Notification Found", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "No Notification Found ");
                    }
                }
            }

            if (remoteMessage.getData().containsKey("url")) {
                image_Url = remoteMessage.getData().get("url");
                if (image_Url != null && !image_Url.equalsIgnoreCase("")) {
                    URL url = null;
                    try {
                        url = new URL(image_Url);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream in = connection.getInputStream();
                        myBitmap = BitmapFactory.decodeStream(in);
                        String body = remoteMessage.getData().get("message");
                        showNotification_WithUrl(body, myBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (remoteMessage.getData().containsKey("ShowNotification")) {
                        if (remoteMessage.getData().get("ShowNotification") != null && !remoteMessage.getData().get("ShowNotification").isEmpty()) {
                            _showNotification = remoteMessage.getData().get("ShowNotification").equals("1");
                            showNotification_withoutUrl(remoteMessage.getData().get("message"));
                        }
                    }
                }
            } else {
                if (remoteMessage.getData().containsKey("ShowNotification")) {
                    if (remoteMessage.getData().get("ShowNotification") != null && !remoteMessage.getData().get("ShowNotification").isEmpty()) {
                        _showNotification = remoteMessage.getData().get("ShowNotification").equals("1");
                        showNotification_withoutUrl(remoteMessage.getData().get("message"));
                    }
                }
            }

            if (remoteMessage.getData().containsKey("CreateDatabaseBackup")) {
                if (remoteMessage.getData().get("CreateDatabaseBackup") != null && !remoteMessage.getData().get("CreateDatabaseBackup").isEmpty() && remoteMessage.getData().get("CreateDatabaseBackup").equals("1")) {
                    // db backup
                    CSApplicationHelper.MMApplication.exportXMLDb();
                }
            }

        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        CSShearedPrefence.setFcmToken(s);
        Log.d(TAG, "onNewToken: " + s);
    }

    public static Account getAccount(String accountname) {
        return new Account(accountname, ACCOUNT_TYPE);
    }

    public void wakeOn() {
        try {
            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = pm.isScreenOn();
            if (isScreenOn == false) {
                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
                wl.acquire(10000);
                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");
                wl_cpu.acquire(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotification_WithUrl(String body, Bitmap myBitmap) {
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_expand_layout);
        expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//        expandedView.setTextViewText(R.id.content_title, CHANNEL_DESCRIPTION);
        expandedView.setTextViewText(R.id.content_title, body);
//        expandedView.setTextViewText(R.id.content_text, body);
        expandedView.setImageViewBitmap(R.id.notification_img, myBitmap);

        //Set pending intent to builder
        Intent i = new Intent(this, MainActivity_Guard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;
        // retrieves android.app.NotificationManager
        NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (mChannel == null) {
                mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
                mChannel.setDescription(CHANNEL_DESCRIPTION);
                mChannel.enableVibration(true);
//                mChannel.setLightColor(Color.GREEN);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }

            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            mBuilder.setContentTitle(CHANNEL_DESCRIPTION)
//                    .setSmallIcon(R.mipmap.notification_icon)
                    .setSmallIcon(R.drawable.ic_notification)
//                    .setSmallIcon(R.drawable.notification_app_icon)
                    .setContentText(body) //show icon on status bar
                    .setCustomBigContentView(expandedView)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setDefaults(Notification.DEFAULT_ALL);
            notificationManager.notify(randomNumber, mBuilder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(R.drawable.ic_notification)
//                    .setSmallIcon(R.drawable.notification_app_icon)
                    .setContentTitle(CHANNEL_DESCRIPTION)
                    .setContentText(body) //show icon on status bar
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setCustomBigContentView(expandedView)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle());
            notificationManager.notify(randomNumber, builder.build());
        }

    }

    private void showNotification_withoutUrl(String body) {
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_collapse_layout);
        expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
//        expandedView.setTextViewText(R.id.content_text,body);
        expandedView.setTextViewText(R.id.content_title, CHANNEL_DESCRIPTION);
        expandedView.setTextViewText(R.id.content_text, body);

        //Set pending intent to builder
        Intent i = new Intent(this, MainActivity_Guard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;
        // retrieves android.app.NotificationManager
        NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (mChannel == null) {
                mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
                mChannel.setDescription(CHANNEL_DESCRIPTION);
                mChannel.enableVibration(true);
//                mChannel.setLightColor(Color.GREEN);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }

            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            mBuilder.setContentTitle(CHANNEL_DESCRIPTION)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentText(body) //show icon on status bar
                    .setCustomBigContentView(expandedView)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setDefaults(Notification.DEFAULT_ALL);
            notificationManager.notify(randomNumber, mBuilder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(CHANNEL_DESCRIPTION)
                    .setContentText(body) //show icon on status bar
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setCustomBigContentView(expandedView)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle());
            notificationManager.notify(randomNumber, builder.build());
        }
    }
}
