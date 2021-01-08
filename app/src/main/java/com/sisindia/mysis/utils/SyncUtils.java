package com.sisindia.mysis.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sisindia.mysis.syncdata.GenericAccountService;

import static com.sisindia.mysis.utils.Constants.PACKAGE_NAME;
import static com.sisindia.mysis.utils.Constants.SYNC_ACCOUNT_30_MINUTE;

public class SyncUtils {
    //  SYNC_INTERVAL should be in seconds, not in milliseconds.
    public static final String ACCOUNT_TYPE = PACKAGE_NAME;
    private static final String CONTENT_AUTHORITY = PACKAGE_NAME;
//    private static final String CONTENT_AUTHORITY2 = "com.sisindia.guardattendance";

    private static final String PREF_SETUP_COMPLETE = "setup_complete";
    private static final long SYNC_FREQUENCY = 43200; //--ENTERIN SECONDS 1nce a 60 seconds =1min --12 hr
    private static final long SYNC_FREQUENCY_15 = 900; //--30 minutes--take 1800 SECONDS
    private static final long SYNC_FREQUENCY_30 = 1800; //--30 minutes--take 1800 SECONDS
    private static final long SYNC_FLEX_TIME = 60; //--1 minutes--take 60 SECONDS
    private static boolean newAccount = false;

    public SyncUtils() {
    }

    /**
     * Create an entry for this application in the system account list, if it isn't already there.
     *
     * @param context Context
     */
    public static void CreateSyncAccount(Context context) {

        boolean setupComplete = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

        // Create account, if it's missing. (Either first run, or user has deleted account.)
        //  Account account = getAccount();
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

   /*    if (accountManager.addAccountExplicitly(account, null, null)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(
                    account, CONTENT_AUTHORITY, new Bundle(),SYNC_FREQUENCY);
            newAccount = true;
        }*/

//        setUpSyncOne(accountManager);
        setUpSynctwo(accountManager);

        // Schedule an initial sync if we detect problems with either our account or our local
        // data has been deleted. (Note that it's possible to clear app data WITHOUT affecting
        // the account list, so wee need to check both.)
        if (newAccount || !setupComplete) {
            TriggerRefresh();
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean(PREF_SETUP_COMPLETE, true).apply();
        }
    }

    public static void configurePeriodicSync(Account account, long syncInterval, long flexTime) {
        Bundle data = new Bundle();
        data.putString("sync", "1");
        data.putString("api_name", "All");
        data.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        data.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder()
                    .syncPeriodic(syncInterval, flexTime)
                    .setSyncAdapter(account, CONTENT_AUTHORITY)
                    .setExtras(data).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, data, syncInterval);
        }
    }

    //    public static void syncImmediately(Context context) {
//        Log.i("MyServiceSyncAdapter", "syncImmediately");
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
//        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
//    }
//    public static Account getSyncAccount(Context context) {
//        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE); // Get an instance of the Android account manager
//        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type)); // Create the account type and default account
//
//        // If the password doesn't exist, the account doesn't exist
//        if (accountManager.getPassword(newAccount) == null) {
//            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
//                Log.e("MyServiceSyncAdapter", "getSyncAccount Failed to create new account.");
//                return null;
//            }
//            onAccountCreated(newAccount, context);
//        }
//        return newAccount;
//    }
//    private static void onAccountCreated(Account newAccount, Context context) {
//        Log.i("MyServiceSyncAdapter", "onAccountCreated");
//        MyServiceSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
//        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
//        syncImmediately(context);
//    }
//    private static void onAccountCreated(Account newAccount, Context context) {
//        Log.i("MyServiceSyncAdapter", "onAccountCreated");
//        MyServiceSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
//        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
//        syncImmediately(context);
//    }
    public static void setUpSynctwo(AccountManager accountManager) {
        //  Account account = GenericAccountService.GetAccount();
        Account account = getAccount(SYNC_ACCOUNT_30_MINUTE);

        if (accountManager.addAccountExplicitly(account, null, null)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            configurePeriodicSync(account, SYNC_FREQUENCY_30, SYNC_FLEX_TIME);
//            ContentResolver.addPeriodicSync(
//                    account, CONTENT_AUTHORITY2, new Bundle(), SYNC_FREQUENCY_30);
//            ContentResolver.requestSync(account,CONTENT_AUTHORITY,new Bundle());
            newAccount = true;
        }
    }


    /**
     * Helper method to trigger an immediate sync ("refresh").
     * <p>
     * <p>This should only be used when we need to preempt the normal sync schedule. Typically, this
     * means the user has pressed the "refresh" button.
     * <p>
     * Note that SYNC_EXTRAS_MANUAL will cause an immediate sync, without any optimization to
     * preserve battery life. If you know new data is available (perhaps via a GCM notification),
     * but the user is not actively waiting for that data, you should omit this flag; this will give
     * the OS additional freedom in scheduling your sync request.
     */
    public static void TriggerRefresh() {
        Log.e("SyncUtil", "TriggerRefresh called");
        Bundle b = new Bundle();
        // Disable sync backoff and ignore sync preferences. In other words...perform sync NOW!
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
       /* ContentResolver.requestSync(
               getAccount("Sync Once In a Day"),      // Sync account
                CONTENT_AUTHORITY, // Content authority
                b);                                      // Extras

        ContentResolver.requestSync(
                getAccount(SYNC_ACCOUNT_15_MINUTE),      // Sync account
                CONTENT_AUTHORITY2, // Content authority
                b);*/

    }

    public static boolean isSyncActived() {

        Account account = GenericAccountService.GetAccount();
        if (ContentResolver.isSyncActive(account, CONTENT_AUTHORITY)) {
            // sync is enable
            Log.e("SyncUtil", "isSyncActived = true");
            return true;
        }
        Log.e("SyncUtil", "isSyncActived = false");
        return false;


    }

    public static Account getAccount(String accountname) {
        return new Account(accountname, ACCOUNT_TYPE);
    }
}