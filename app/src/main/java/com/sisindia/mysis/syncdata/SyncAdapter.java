package com.sisindia.mysis.syncdata;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;

import static com.sisindia.mysis.utils.Constants.SYNC_ACCOUNT_30_MINUTE;

/**
 * Created by Rahul Rawat on 11/30/2016.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter implements GetDataFromServer.OnGetDataCompleted {

    public static final String TAG = "SyncAdapter";
    private Context context;

    /**
     * Content resolver, for performing database operations.
     */
    private final ContentResolver mContentResolver;

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        this.context = context;
    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        boolean _fcmSync = false;
        Log.i(TAG, "onPerformSync START" + account.name);
        if (extras != null) {
            String fcmrequest = extras.getString("sync");
            if (fcmrequest != null && !fcmrequest.equalsIgnoreCase("")) {
                if (fcmrequest.equalsIgnoreCase("1")) {
                    _fcmSync = true;

                    String api_name = extras.getString("api_name");
                    if (api_name.equalsIgnoreCase("uploadLocallyToServer")) {
                        DashboadApisMethod db = DashboadApisMethod.getInstance(getContext());
                        UpdateAlllocalDatatoServer.getInstance().sendData(db, getContext(), false);
                    } else {
                        GetDataFromServer.getInstance().initListener(this);
                        DashboadApisMethod db = DashboadApisMethod.getInstance(context);
                        GetDataFromServer.getInstance().syncgettabledata(db, context, false, api_name);
                        CSShearedPrefence.setBooleanPreference(getContext(), "SyncCompleted", true);
                    }
                }
            }
        }
        if (!_fcmSync) {
            boolean SyncCompleted = CSShearedPrefence.getBooleanPreference(getContext(), "SyncCompleted", false);
            boolean First30 = CSShearedPrefence.getBooleanPreference(getContext(), "First30", false);
            boolean FirstDay = CSShearedPrefence.getBooleanPreference(getContext(), "FirstDay", false);
            if (SyncCompleted) {
//                if (account.name.equalsIgnoreCase("Sync Once In a Day")) {
                if (account.name.equalsIgnoreCase(SYNC_ACCOUNT_30_MINUTE)) {
                    if (FirstDay) {
                        Log.e(TAG, "onPerformSync: SYNC_ACCOUNT_30_MINUTE get");
                        GetDataFromServer.getInstance().initListener(this);
                        DashboadApisMethod db = DashboadApisMethod.getInstance(context);
                        GetDataFromServer.getInstance().getData(db, context, null, false);

/*
                        try {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(TAG, "run: SYNC_ACCOUNT_30_MINUTE post");
                                    UpdateLocallyToServer.getInstance().sendData(db, context, false);
                                }
                            }, 3000);
*/
                        try {
                            Thread.sleep(8000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, "run: SYNC_ACCOUNT_30_MINUTE post");
                        UpdateLocallyToServer.getInstance().sendDataRoom();

/*
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
*/

                    }
                    CSShearedPrefence.setBooleanPreference(getContext(), "FirstDay", true);
                }
                /*if (account.name.equalsIgnoreCase(SYNC_ACCOUNT_30_MINUTE)) {
                    if (First30) {
//                        GetDataFromServer.getInstance().initListener(this);
//                        DashboadApisMethod db = DashboadApisMethod.getInstance(context);
//                        GetDataFromServer.getInstance().getData30min(db, context, null, false);

                        GetDataFromServer.getInstance().initListener(this);
                        DashboadApisMethod db = DashboadApisMethod.getInstance(context);
                        GetDataFromServer.getInstance().getData(db, context, null, false);
                    }
                    CSShearedPrefence.setBooleanPreference(getContext(), "First30", true);
                }*/
                Log.d("SyncCompleted", "SyncCompleted called successfully: " + account.name);
            }
        }
//        mContentResolver.notifyChange(,null,false);

        CSShearedPrefence.setBooleanPreference(getContext(), "SyncCompleted", true);
        CFUtil.checkAttendanceToCallService_When_Attendance_FromOther_Duty(getContext());
    }

    @Override
    public void OnCompleted() {

        DashboadApisMethod db = DashboadApisMethod.getInstance(getContext());
        UpdateLocallyToServer.getInstance().sendData(db, getContext(), false);
        CFUtil.checkAttendanceToCallService_When_Attendance_FromOther_Duty(getContext());


    }

    @Override
    public void OnError() {
    }
}
