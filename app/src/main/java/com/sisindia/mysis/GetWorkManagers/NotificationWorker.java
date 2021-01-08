package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.NotificationModel;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.CSUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sisindia.mysis.utils.Constants.NOTIFICATION_TABLE;
import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;

public class NotificationWorker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";
    private List<NotificationModel> notificationModelList;

    @NonNull
    @Override
    public Result doWork() {

        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), CFUtil.parameter_Format_single(Constants.GET_UNIT_SHIFT_MASTER,
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL(), "0");


        return Result.SUCCESS;
    }

    private String getTagURL() {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), NOTIFICATION_TABLE);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_NOTIFICATION).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_NOTIFICATION).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        return Uri.parse(url).buildUpon().build().toString();
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {

        Log.e("getDataFromVolleyIn>>", "" + loadedString);
        try {
            JSONObject jsonObject = new JSONObject(loadedString);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<NotificationModel>>() {
                }.getType();
                List<String> deletedEmpList = new ArrayList<>();

                notificationModelList = new Gson().fromJson(jsonObject.getJSONArray("data").toString(), listType);
                if (notificationModelList != null) {

                    for (int i = 0; i < notificationModelList.size(); i++) {
                        JSONObject object = jsonObject.getJSONArray("data").getJSONObject(i);
                        notificationModelList.get(i).setJson(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedEmpList.add(notificationModelList.get(i).getId());
                        }
                    }
                    for (int i = 0; i < notificationModelList.size(); i++) {
                        notificationModelList.get(i).setFlag("0");
//                        notificationModelList.get(i).setDutyHours(String.valueOf(DateUtils.getInstance().getDutyHours(jsonObject.getJSONArray("UnitShiftMaster").getJSONObject(i).getString("DUTY_HRS"))));
                        notificationModelList.get(i).setJson(jsonObject.getJSONArray("data").getJSONObject(i).toString());
                    }
                }
                for (int j = 0; j < notificationModelList.size(); j++) {
                    if (CSApplicationHelper.application().getDatabaseInstance().notification_dao().getDetail(notificationModelList.get(j).getId()) == null) {
                        CSApplicationHelper.application().getDatabaseInstance().notification_dao().insertSingle(notificationModelList.get(j));
                    } else {
                        if (CSApplicationHelper.application().getDatabaseInstance().notification_dao().getDetail(notificationModelList.get(j).getId()).getFlag().equals("0")) {
                            CSApplicationHelper.application().getDatabaseInstance().notification_dao().update(notificationModelList.get(j));
                        }
                    }
                }
                if (deletedEmpList.size() > 0) {
                    CSApplicationHelper.application().getDatabaseInstance().notification_dao().deleteAll(deletedEmpList);
                }
                //************************** save  MASTER table in DB *****************************************************************
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + String.valueOf(e));
        }
    }

}



