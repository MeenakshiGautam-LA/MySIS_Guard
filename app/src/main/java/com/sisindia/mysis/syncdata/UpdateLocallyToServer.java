package com.sisindia.mysis.syncdata;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.VolleyError;
import com.sisindia.mysis.PostWorkerManager.PostAttendanceWork;
import com.sisindia.mysis.PostWorkerManager.PostLeaveMaster_Worker;
import com.sisindia.mysis.PostWorkerManager.PostLeaveTransactionWorker;
import com.sisindia.mysis.PostWorkerManager.PostNotificationWorker;
import com.sisindia.mysis.PostWorkerManager.Post_MarkAttendance_Selfie_Worker;
import com.sisindia.mysis.PostWorkerManager.Post_TrackingDetailWorker;
import com.sisindia.mysis.application.CSApplication;
import com.sisindia.mysis.application.CSApplicationHelper;

/**
 * Created by Rahul Rawat on 11/2/2016.
 */

public class UpdateLocallyToServer implements DashboadApisMethod.SyncListener {

    private static UpdateLocallyToServer instance;
    private ProgressDialog pDialog;
    OneTimeWorkRequest postAttendanceWork;

    private UpdateLocallyToServer() {

    }

    public static synchronized UpdateLocallyToServer getInstance() {

        if (instance == null) {
            instance = new UpdateLocallyToServer();
        }
        return instance;
    }


    void setProgressDialogMsg(String msg, Context context) {

        try {
            if (context == null) {
                context = CSApplication.getAppContext();
            }

            if (context != null) {
                pDialog = new ProgressDialog(context);
                pDialog.setCancelable(false);
                pDialog.setMessage(msg);
                if (pDialog != null) {
                    if (!pDialog.isShowing())
                        pDialog.show();
                }
            }
        } catch (Exception e) {
            Log.e("", e.toString());
        }

    }


    private class TaskRunner extends AsyncTask<String, String, String> {

        private Context context;
        DashboadApisMethod db;
        boolean showDialog;

        TaskRunner(Context context, DashboadApisMethod db, boolean showDialog) {
            this.context = context;
            this.db = db;
            this.showDialog = showDialog;
        }

        @Override
        protected String doInBackground(String... params) {
            postAttendanceWork = new OneTimeWorkRequest.Builder(PostAttendanceWork.class).build();

            WorkManager.getInstance()
                    .beginWith(postAttendanceWork)
                    .enqueue();
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            try {
                WorkManager.getInstance().getStatusById(postAttendanceWork.getId()).observe(CSApplicationHelper.application().getActivity(),
                        workStatus -> {
                            if (workStatus != null) {
                                Toast.makeText(CSApplicationHelper.application().getActivity(), "Sync Completed: " +
                                        String.valueOf(workStatus.getState().name()), Toast.LENGTH_LONG).show();

                            }

                            if (workStatus != null && workStatus.getState().isFinished()) {
                                // ... do something with the result ...
//                        Toast.makeText(CSApplicationHelper.application().getActivity(), "Work Finished", Toast.LENGTH_LONG).show();
//                            listner.OnCompleted();
                            }

                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (context != null) {
                if (pDialog != null) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }
            }
        }
        @Override
        protected void onPreExecute() {
            if (pDialog != null && showDialog)
                setProgressDialogMsg("Please wait while uploading data....", context);
        }
    }

    public synchronized void sendData(DashboadApisMethod db, Context context, boolean showDialog) {
        db.initSyncListener(this);
        postAttendanceWork = new OneTimeWorkRequest.Builder(PostAttendanceWork.class).build();
        OneTimeWorkRequest post_trackingDetailWorker = new OneTimeWorkRequest.Builder(Post_TrackingDetailWorker.class).build();
        OneTimeWorkRequest postNotificationWorker = new OneTimeWorkRequest.Builder(PostNotificationWorker.class).build();
        OneTimeWorkRequest postLeaveMaster_Worker = new OneTimeWorkRequest.Builder(PostLeaveMaster_Worker.class).build();
        OneTimeWorkRequest postLeaveTransactionWorker = new OneTimeWorkRequest.Builder(PostLeaveTransactionWorker.class).build();
        OneTimeWorkRequest post_markAttendance_selfie_worker=new OneTimeWorkRequest.Builder(Post_MarkAttendance_Selfie_Worker.class).build();
        WorkManager.getInstance()
                .beginWith(postAttendanceWork)
                .then(post_trackingDetailWorker)
                .then(postLeaveMaster_Worker)
                .then(postLeaveTransactionWorker)
                .then(postNotificationWorker)
                .then(post_markAttendance_selfie_worker)
                .enqueue();
    }

    public synchronized void sendDataRoom() {
        postAttendanceWork = new OneTimeWorkRequest.Builder(PostAttendanceWork.class).build();
        OneTimeWorkRequest post_trackingDetailWorker = new OneTimeWorkRequest.Builder(Post_TrackingDetailWorker.class).build();
        OneTimeWorkRequest postNotificationWorker = new OneTimeWorkRequest.Builder(PostNotificationWorker.class).build();
        OneTimeWorkRequest postLeaveMaster_Worker = new OneTimeWorkRequest.Builder(PostLeaveMaster_Worker.class).build();
        OneTimeWorkRequest postLeaveTransactionWorker = new OneTimeWorkRequest.Builder(PostLeaveTransactionWorker.class).build();
        OneTimeWorkRequest post_markAttendance_selfie_worker=new OneTimeWorkRequest.Builder(Post_MarkAttendance_Selfie_Worker.class).build();
        WorkManager.getInstance()
                .beginWith(postAttendanceWork)
                .then(post_trackingDetailWorker)
                .then(postLeaveMaster_Worker)
                .then(postLeaveTransactionWorker)
                .then(postNotificationWorker)
                .then(post_markAttendance_selfie_worker)
                .enqueue();

    }
    @Override
    public void OnSyncGetDataCompleted() {
    }

    @Override
    public void OnSyncPostDataCompleted() {
        if (pDialog != null) {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    @Override
    public void OnSyncError(VolleyError error) {
        if (pDialog != null) {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }


    public synchronized void sendDataModuleWise(DashboadApisMethod db, Context context, boolean showDialog, String apiname) {
//        db.initSyncListener(this);
        new sendModuleData(context, db, showDialog, apiname).execute();
    }

    private class sendModuleData extends AsyncTask<String, String, String> {

        private Context context;
        DashboadApisMethod db;
        boolean showDialog;
        String apiname = "";

        sendModuleData(Context context, DashboadApisMethod db, boolean showDialog, String apiname) {
            this.context = context;
            this.db = db;
            this.showDialog = showDialog;
            this.apiname = apiname;
        }

        @Override
        protected String doInBackground(String... params) {

            switch (apiname) {

                case "emd":
                    /********EMD data***********/
                    break;

                default:
                    break;

            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
/*
            if (showDialog)
                setProgressDialogMsg("Please wait while uploading data....", context);
*/
        }
    }

}
