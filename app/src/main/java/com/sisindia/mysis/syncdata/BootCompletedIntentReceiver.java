package com.sisindia.mysis.syncdata;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import static com.sisindia.mysis.utils.Constants.PACKAGE_NAME;
import static com.sisindia.mysis.utils.Constants.SYNC_ACCOUNT_30_MINUTE;

/**
 * Created by SalesMaxx on 7/3/2017.
 */

public class BootCompletedIntentReceiver extends BroadcastReceiver {

    public static final String ACCOUNT_TYPE = PACKAGE_NAME;


    @Override
    public void onReceive(Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Bundle b = new Bundle();
            b.putString("sync", "1");
            b.putString("api_name", "All");
            b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

            //-----------Request Sync from fcm null contains the string from server
//            Account account = getAccount("Sync Once In a Day");
            Account account2 = getAccount(SYNC_ACCOUNT_30_MINUTE);

//            ContentResolver.addPeriodicSync(
//                    account, ACCOUNT_TYPE, new Bundle(), SYNC_FREQUENCY);


//            ContentResolver.addPeriodicSync(
//                    account2, ACCOUNT_TYPE, new Bundle(), SYNC_FREQUENCY_30);

//            ContentResolver.requestSync(account, ACCOUNT_TYPE, b);
            ContentResolver.requestSync(account2, ACCOUNT_TYPE, b);

        }
    }

    public static Account getAccount(String accountname) {
        return new Account(accountname, ACCOUNT_TYPE);
    }
}