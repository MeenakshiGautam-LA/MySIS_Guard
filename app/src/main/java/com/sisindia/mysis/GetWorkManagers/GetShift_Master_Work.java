package com.sisindia.mysis.GetWorkManagers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.ShiftMasterModel;
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

import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;
import static com.sisindia.mysis.utils.Constants.SHIFTMASTER;

public class GetShift_Master_Work extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";
    private List<ShiftMasterModel> shiftMasterModelList;
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
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), SHIFTMASTER);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_UNIT_SHIFT_MASTER).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_UNIT_SHIFT_MASTER).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        return Uri.parse(url).buildUpon().appendQueryParameter("UnitCode", "").build().toString();
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
    }

    private void showNotification(String task, String desc) {

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        String channelId = "task_channel";
        String channelName = "task_name";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1, builder.build());

    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {

        Log.e("getDataFromVolleyIn>>", "" + loadedString);
        Log.e("regNO>>", "" + regNO);
        try {
            JSONObject jsonObject = new JSONObject(loadedString);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<ShiftMasterModel>>() {
                }.getType();
                List<String> deletedEmpList = new ArrayList<>();

                shiftMasterModelList = new Gson().fromJson(jsonObject.getJSONArray("UnitShiftMaster").toString(), listType);
                if (shiftMasterModelList != null) {

                    for (int i = 0; i < shiftMasterModelList.size(); i++) {
                        JSONObject object = jsonObject.getJSONArray("UnitShiftMaster").getJSONObject(i);
                        shiftMasterModelList.get(i).setJson(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedEmpList.add(shiftMasterModelList.get(i).getId());
                        }
                    }
                    for (int i = 0; i < shiftMasterModelList.size(); i++) {
//                        shiftMasterModelList.get(i).setDutyHours(String.valueOf(DateUtils.getInstance().getDutyHours(jsonObject.getJSONArray("UnitShiftMaster").getJSONObject(i).getString("DUTY_HRS"))));
                        shiftMasterModelList.get(i).setJson(jsonObject.getJSONArray("UnitShiftMaster").getJSONObject(i).toString());
                    }
                }
                for (int j = 0; j < shiftMasterModelList.size(); j++) {
                    if (CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().getShiftDetail(shiftMasterModelList.get(j).getId()) == null) {
                        CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().insertsingle(shiftMasterModelList.get(j));
                    } else {
                        if (CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().getShiftDetail(shiftMasterModelList.get(j).getId()).getFlag().equals("0")) {
                            CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().update(shiftMasterModelList.get(j));
                        }
                    }
                }
                if (deletedEmpList.size() > 0) {
                    CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().deleteAll(deletedEmpList);
                }
                //************************** save  MASTER table in DB *****************************************************************
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + String.valueOf(e));
        }
    }

}

