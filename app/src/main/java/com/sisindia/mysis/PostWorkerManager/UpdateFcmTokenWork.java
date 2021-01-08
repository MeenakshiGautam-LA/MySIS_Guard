package com.sisindia.mysis.PostWorkerManager;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.NetworkUtils;

public class UpdateFcmTokenWork extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    @NonNull
    @Override
    public Result doWork() {
        if(!CSStringUtil.isEmptyStr(CSShearedPrefence.getFcmToken())){
            new Volley_Asynclass_Get(this, NetworkUtils.formatForFCM_TOKEN(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "updatefcmtoken", CSShearedPrefence.getFcmToken()),
                    "ChangePassword", "ChangePassword");
        }

        return Result.SUCCESS;
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {

    }
}
