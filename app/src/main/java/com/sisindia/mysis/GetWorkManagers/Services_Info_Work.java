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

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.ServiceInfoDetail;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.CSUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;
import static com.sisindia.mysis.utils.Constants.SERVICEINFO;

public class Services_Info_Work extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";

    private List<ServiceInfoDetail> serviceInfoDetailList;

    @NonNull
    @Override
    public Result doWork() {


        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL(), "GetServiceInfo");
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), Constants.GET_SERVICE_INFO_URL, "GetServiceInfo");


        return Result.SUCCESS;
    }

    private String getTagURL() {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), SERVICEINFO);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_SERVICE_INFO_URL).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_SERVICE_INFO_URL).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        return Uri.parse(url).buildUpon().build().toString();
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
                Type listType = new TypeToken<List<ServiceInfoDetail>>() {
                }.getType();
                List<String> deletedEmpList = new ArrayList<>();
                serviceInfoDetailList = new Gson().fromJson(jsonObject.getJSONArray("ServiceInfo").toString(), listType);
                if (serviceInfoDetailList != null) {
                    for (int i = 0; i < serviceInfoDetailList.size(); i++) {
                        JSONObject object = jsonObject.getJSONArray("ServiceInfo").getJSONObject(i);
                        serviceInfoDetailList.get(i).setJSON(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedEmpList.add(serviceInfoDetailList.get(i).getID());
                        }
                    }
                    for (int i = 0; i < serviceInfoDetailList.size(); i++) {
                        serviceInfoDetailList.get(i).setJSON(jsonObject.getJSONArray("ServiceInfo").getJSONObject(i).toString());
                    }
                    CSApplicationHelper.application().getDatabaseInstance().serviceDAO().insert(serviceInfoDetailList);
                }
                if (deletedEmpList.size() > 0) {
                    CSApplicationHelper.application().getDatabaseInstance().serviceDAO().deleteAll(deletedEmpList);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


