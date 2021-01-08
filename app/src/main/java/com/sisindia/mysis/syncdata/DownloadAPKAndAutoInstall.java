package com.sisindia.mysis.syncdata;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import androidx.core.app.NotificationCompat;import com.sisindia.mysis.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Rahul Rawat on 12/15/2016.
 */

public class DownloadAPKAndAutoInstall extends AsyncTask<String, Integer, Void> {

    public Context context;
    public NotificationManager notificationManager;
    public Notification notification;
    public NotificationCompat.Builder builder;

    String file_url;


    public DownloadAPKAndAutoInstall(Context context, String file_url) {
        this.context = context;

        this.file_url = file_url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // configure the notification

        long when = System.currentTimeMillis();

        builder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name) + " Downloading...")
                .setSmallIcon(R.mipmap.ic_launcher).setWhen(when)
                .setProgress(100, 0, false);

        notification = builder.build();

        notification.flags = notification.flags
                | Notification.FLAG_ONGOING_EVENT;

        context.getApplicationContext();
        notificationManager = (NotificationManager) context
                .getApplicationContext().getSystemService(
                        Context.NOTIFICATION_SERVICE);
        notificationManager.notify(42, notification);

    }

    @Override
    protected Void doInBackground(String... params) {

        try {// http://115.248.115.254/mylibrary/Software/SalesMaxx.apk
            URL url = new URL(this.file_url);
            URLConnection c = url.openConnection();
            // c.setRequestMethod("GET");
            // c.setDoOutput(true);
            // c.setUseCaches(false);
            c.connect();
            int size = c.getContentLength();

            Log.e("download apk", "download size = " + size);

            String PATH = Environment.getExternalStorageDirectory()
                    + "/SalesMaxx/download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "SalesMaxx.apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            int byt = 0;
            int count = 0;
            while ((len1 = is.read(buffer)) != -1) { // System.out.println("byte downloaded = "+byt);
                fos.write(buffer, 0, len1);
                byt = byt + len1;
                int t = (byt * 100 / size); // System.out.println("byte calculated = "+t);
                if (count == 10000) {
                    count = 0;
                    publishProgress(t);
                }
                count++;
            }
            fos.close();
            is.close();

        } catch (IOException e) {

            Log.e("downloadAPK", "e = " + e.toString());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        int mDownloadProgress = progress[0];
        builder.setProgress(100, mDownloadProgress, false);
        notification = builder.build();
        // inform the progress bar of updates in progress
        notificationManager.notify(42, notification);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        stopNotification();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory()
                        + "/SalesMaxx/download/"
                        + "SalesMaxx.apk")),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

//        copyLocalDBInPhone();
    }

    @Override
    protected void onCancelled() {
        stopNotification();
    }

    @Override
    protected void onCancelled(Void result) {
        stopNotification();
    }

    void stopNotification() {
        notificationManager.cancel(42);
    }

}