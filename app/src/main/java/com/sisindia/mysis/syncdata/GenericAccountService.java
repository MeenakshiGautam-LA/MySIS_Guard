package com.sisindia.mysis.syncdata;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static com.sisindia.mysis.utils.Constants.PACKAGE_NAME;


public class GenericAccountService
        extends Service {
    public static final String ACCOUNT_NAME = "sync";
    private static final String ACCOUNT_TYPE = PACKAGE_NAME;
    private static final String TAG = GenericAccountService.class.getSimpleName();
    private Authenticator mAuthenticator;

    public GenericAccountService() {
    }


    public static Account GetAccount() {

        return new Account("sync", ACCOUNT_TYPE);
    }

    public IBinder onBind(Intent paramIntent) {

        return this.mAuthenticator.getIBinder();
    }

    public void onCreate() {

        Log.d(TAG, "Service created");
        this.mAuthenticator = new Authenticator(this);
    }

    public void onDestroy() {

        Log.d(TAG, "Service destroyed");
    }
}

