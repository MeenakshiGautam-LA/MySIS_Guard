package com.sisindia.mysis.GetWorkManagers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChangeContactNoWorker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    @NonNull
    @Override
    public Result doWork() {
        UserDetailModel userDetailModel=CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getDetails(CSShearedPrefence.getUser());
        if(userDetailModel!=null){
            new Volley_Asynclass_Get(this, NetworkUtils.changeSelf_ContactnoParamter(userDetailModel.getChange_ContactNo(),
                    CSShearedPrefence.getDeviceToken()),Constants.CHANGE_CONTACT_NO_URL_TAG, "CHANGE_CONTACTNO");
        }
        return Result.SUCCESS;
    }
    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {

        Log.e("CHANGE_CONTACTNO>>", "" + loadedString);
        Log.e("regNO>>", "" + regNO);
        if (regNO.equals("CHANGE_CONTACTNO")) {
            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    JSONArray jsonArray;
                    jsonArray=jsonObject.getJSONArray("data");
                    CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().updateWithSyncServer(jsonArray.
                            getJSONObject(0).getString("RegNo"),jsonArray.
                            getJSONObject(0).getString("PendingApproval"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}


