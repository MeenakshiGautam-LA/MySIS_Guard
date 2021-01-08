package com.sisindia.mysis.syncdata;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.PopupWindow;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.VolleyError;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.GetWorkManagers.GetAttendanceWork;
import com.sisindia.mysis.application.CSApplication;
import com.sisindia.mysis.dataBase.AppDatabase;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.Constants;

/**
 * Created by Rahul Rawat on 11/2/2016.
 */

public class GetDataFromServer implements DashboadApisMethod.SyncListener {

    private static GetDataFromServer instance;
    private ProgressDialog pDialog;
    OnGetDataCompleted listner;
    PopupWindow popupWindow;
    OneTimeWorkRequest shiftMasterWork;

    public interface OnGetDataCompleted {
        void OnCompleted();

        void OnError();
    }

    public void initListener(OnGetDataCompleted listener) {
        this.listner = listener;
    }

    private GetDataFromServer() {
    }

    public static synchronized GetDataFromServer getInstance() {

        if (instance == null) {
            instance = new GetDataFromServer();
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
        boolean showDialog;
        DashboadApisMethod db;
        String apiname = "";
        AppDatabase dbManagerInstance;

        TaskRunner(Context context, DashboadApisMethod db, boolean showDialog) {
            this.context = context;
            this.db = db;
            this.showDialog = showDialog;
            dbManagerInstance = AppDatabase.getDatabase(context);

        }

        TaskRunner(Context context, DashboadApisMethod db, boolean showDialog, String api_name) {
            this.context = context;
            this.db = db;
            this.showDialog = showDialog;
            this.apiname = api_name;
            dbManagerInstance = AppDatabase.getDatabase(context);

        }


        @Override
        protected String doInBackground(String... params) {

            if (apiname.equalsIgnoreCase("")) {
                getAllApis(context);
            } else {

                if (apiname.equalsIgnoreCase("All")) {
                    getAllApis(context);
                } else {
                    String lastModifiedDate;
                    lastModifiedDate = dbManagerInstance.getEmployee_dao().maxModifiedDate();
                    Log.d("MainActivity", "getusertarget lastModifiedDate = " + lastModifiedDate);

                    if (apiname.equalsIgnoreCase("TABLE_NAME")) {
//                        db.getusertarget(lastModifiedDate);
                    } else if (apiname.equalsIgnoreCase("TABLE_NAME")) {
//                        db.getsitemodulelist(lastModifiedDate);
                    }
                    try {
                        Thread.sleep(8000);
                        UpdateLocallyToServer.getInstance().sendData(db, context, false);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                /*WorkManager.getInstance().getStatusById(shiftMasterWork.getId()).observe(CSApplicationHelper.application().getActivity(),
                        workStatus -> {
                            if (workStatus != null) {
                                Toast.makeText(CSApplicationHelper.application().getActivity(), "Sync Completed: " +
                                        String.valueOf(workStatus.getState().name()), Toast.LENGTH_LONG).show();

                            }

                            if (workStatus != null && workStatus.getState().isFinished()) {
                                // ... do something with the result ...
//                        Toast.makeText(CSApplicationHelper.application().getActivity(), "Work Finished", Toast.LENGTH_LONG).show();
*//*
                            if (listner != null)
                                listner.OnCompleted();
*//*
//                                UpdateLocallyToServer.getInstance().sendData(db, context, false);
                            }

                        });*/
                Thread.sleep(8000);
                UpdateLocallyToServer.getInstance().sendData(db, context, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {
            if (showDialog) {
                setProgressDialogMsg("Please wait while loading data... ", context);
            }
        }


    }

    void getAllApis(Context context) {
        CFUtil.call_Get_DataFrom_Server();
    }

    private class TaskRunner30MINUTES extends AsyncTask<String, String, String> {

        private Context context;
        boolean showDialog;
        DashboadApisMethod db;


        TaskRunner30MINUTES(Context context, DashboadApisMethod db, boolean showDialog) {
            this.context = context;
            this.db = db;
            this.showDialog = showDialog;

        }


        @Override
        protected String doInBackground(String... params) {
            OneTimeWorkRequest getAttendanceWork = new OneTimeWorkRequest.Builder(GetAttendanceWork.class).build();



            WorkManager.getInstance()
                    .beginWith(getAttendanceWork)
                    .enqueue();


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                UpdateLocallyToServer.getInstance().sendData(db, context, false);
                if (showDialog) {
                    if (pDialog != null) {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {

            if (showDialog) {
                setProgressDialogMsg("Please wait while loading data... ", context);
            }
        }
    }

    public synchronized boolean getData(DashboadApisMethod db, Context context, PopupWindow popUp, boolean showDialog) {

        popupWindow = popUp;

        db.initSyncListener(this);
        getAllApis(context);
        try {
            Thread.sleep(8000);
            UpdateLocallyToServer.getInstance().sendData(db, context, false);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        new TaskRunner(context, db, showDialog, "").execute();
        return true;
    }


    public synchronized boolean syncgettabledata(DashboadApisMethod db, Context context, boolean showDialog, String table_name) {

        db.initSyncListener(this);
        new TaskRunner(context, db, showDialog, table_name).execute();
        return true;
    }

    public synchronized boolean getData30min(DashboadApisMethod db, Context context, PopupWindow popUp, boolean showDialog) {

        popupWindow = popUp;

        db.initSyncListener(this);
        getAllApis(context);
        try {
            Thread.sleep(8000);
            UpdateLocallyToServer.getInstance().sendData(db, context, false);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }//        new TaskRunner30MINUTES(context, db, showDialog).execute();
        return true;
    }


    @Override
    public void OnSyncGetDataCompleted() {
        try {
            if (popupWindow != null)
                if (popupWindow.isShowing())
                    popupWindow.dismiss();

            if (pDialog != null) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }

            if (listner != null) {
                listner.OnCompleted();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnSyncPostDataCompleted() {
        Log.d("", "");
    }

    @Override
    public void OnSyncError(VolleyError error) {

        Log.e("GetDataFromServer ", "Error " + error.getMessage());
       /* if (pDialog != null) {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }*/

        if (listner != null) {
            listner.OnError();
        }

    }

    /*Dhiraj Code Here for module wise syncing*/
    public synchronized boolean syncModuleWise(DashboadApisMethod db, Context context, boolean showDialog, String table_name, boolean onlyFetchDataFromServer) {
//        db.initSyncListener(this);
        new GetModuleData(context, db, showDialog, table_name, onlyFetchDataFromServer).execute();
        return true;
    }

    private class GetModuleData extends AsyncTask<String, String, String> {

        private Context context;
        boolean showDialog;
        DashboadApisMethod db;
        String table_name;
        AppDatabase dbManagerInstance;
        boolean onlyFetchDataFromServer;

        GetModuleData(Context context, DashboadApisMethod db, boolean showDialog, String table_name, boolean onlyFetchDataFromServer) {
            this.context = context;
            this.db = db;
            this.showDialog = showDialog;
            this.table_name = table_name;
            this.onlyFetchDataFromServer = onlyFetchDataFromServer;
            dbManagerInstance = AppDatabase.getDatabase(context);

        }


        @Override
        protected String doInBackground(String... params) {
            try {

                String lastModifiedDate = "";
                if (table_name.equals("NativeProfile") || table_name.equals("sync_delete")) {
                    lastModifiedDate = CSShearedPrefence.getInstance().getString("modified_date", null);
                } else {
                    lastModifiedDate = dbManagerInstance.getEmployee_dao().maxModifiedDate();
                }
                switch (table_name) {
                    case "OPP":
//                        db.getOpportunity(lastModifiedDate);
                        break;


                    case "EMD":
                        db.getEMD(lastModifiedDate);
                        break;

                    default:
                        break;
                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                Thread.sleep(2000);
                if (!onlyFetchDataFromServer) {
                    UpdateLocallyToServer.getInstance().sendDataModuleWise(db, context, false, table_name);
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {

        }
    }

        public String getMaxModifiedDate(AppDatabase dbManagerInstance, String tableName) {

            String modifiedDate = null;

            switch (tableName) {
                case Constants.DAILY_ATTENDANCEDETAIL:
                    modifiedDate = dbManagerInstance.markAttendanceDao().maxModifiedDate();
                    break;
                case Constants.NOTIFICATION_TABLE:
                    modifiedDate = dbManagerInstance.notification_dao().maxModifiedDate();
                    break;
                case Constants.LEAVE_TRANSACTION_DETAILS:
                    modifiedDate = dbManagerInstance.leaveTransactionDetail_dao().maxModifiedDate();
                    break;
                case Constants.LEAVE_MASTER:
                    modifiedDate = dbManagerInstance.leave_Master_Dao().maxModifiedDate();
                    break;

                case Constants.EMPLOYEEDETAILS_TABLE:
                    modifiedDate = dbManagerInstance.getEmployee_dao().maxModifiedDate();
                    break;
                case Constants.SERVICEINFO:
                    modifiedDate = dbManagerInstance.serviceDAO().maxModifiedDate();
                    break;

                case Constants.SHIFTMASTER:
                    modifiedDate = dbManagerInstance.shifMaster_dao().maxModifiedDate();
                    break;
                case Constants.GETSITEDETAILS:
                    modifiedDate = dbManagerInstance.siteDetail_DAO().maxModifiedDate();
                    break;
                case Constants.Roster_TABLE:
                    modifiedDate = dbManagerInstance.rosterDetailDao().maxModifiedDate();
                    break;
                case Constants.FLASH_MESSAGE_TABLE:
                    modifiedDate = dbManagerInstance.flashMessageDao().maxModifiedDate();
                    break;
                case Constants.USER_DETAIL:
                    modifiedDate = dbManagerInstance.user_detail_dao().maxModifiedDate();
                    break;
            }

            return modifiedDate;
        }
}
