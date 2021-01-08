package com.sisindia.mysis.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.DateUtils;

public class StartReceiver extends BroadcastReceiver {
    Intent mServiceIntent;


    @Override
    public void onReceive(Context context, Intent intent) {
        /*if(CSApplicationHelper.application().getDatabaseInstance()!=null){
            DailyAttendanceDetail dailyAttendanceDetail= CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().
                    markAttendanceToday(CSShearedPrefence.getUser(),
                            DateUtils.getCurrentDate_format());
            if(dailyAttendanceDetail!=null){
                mServiceIntent = new Intent(context, TrackingService.class);
                if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        mSensorService = new TrackingService();
                        context.startForegroundService(mServiceIntent);
                    } else {
                        mSensorService = new TrackingService();
                        context.startService(mServiceIntent);
                    }
                }
            }
        }

*/
    }

}
