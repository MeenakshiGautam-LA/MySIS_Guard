package com.sisindia.mysis.syncdata;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sisindia.mysis.application.CSApplication;
import com.sisindia.mysis.utils.CFUtil;

/**
 * Created by Rahul Rawat on 11/2/2016.
 */

public class UpdateAlllocalDatatoServer {

    private static UpdateAlllocalDatatoServer instance;
    private ProgressDialog pDialog;

    private UpdateAlllocalDatatoServer() {

    }

    public static synchronized UpdateAlllocalDatatoServer getInstance() {

        if (instance == null) {
            instance = new UpdateAlllocalDatatoServer();
        }
        return instance;
    }


    void setProgressDialogMsg(String msg, Context context) {

        if (context == null) {
            context = CSApplication.getAppContext();
        }

        if (context != null) {

            pDialog = new ProgressDialog(context);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setMessage(msg);
            if (pDialog != null) {
                if (!pDialog.isShowing())
                    pDialog.show();
            }
        }
    }


    void uploadCalendar(Context context, DashboadApisMethod db) {

/*
        List<Meetings> meetingsList = DatabaseManagerTwo.getInstance(context).getAllMeetings(null);

        if (meetingsList.size() > 0) {

            Iterator iterator = meetingsList.iterator();

            while (iterator.hasNext()) {

                Meetings model = (Meetings) iterator.next();


                try {
                    db.postData(new JSONObject(model.getJson_string()), RestConstant.TABLE_MEETINGS, context, RestConstant.CREATE_MEETING);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }

            }
        }

*/
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

            /*********EVENT LOG OR ACTIVITY LOG***********/
            uploadCalendar(context, db); //tested done.

            /*********MEETING LOG***********/
            // uploadMeetingNotes(context, db); need work with ashu


            return null;
        }


        @Override
        protected void onPostExecute(String result) {

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
        // db.initSyncListener(this);
        new TaskRunner(context, db, showDialog).execute();
    }


}
