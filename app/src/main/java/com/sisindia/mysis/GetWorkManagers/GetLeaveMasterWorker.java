package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.LeaveMaster;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sisindia.mysis.utils.Constants.GET_LEAVE_DETAIL;

public class GetLeaveMasterWorker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private ArrayList<LeaveMaster> leaveEmployeeMastersList;

    @NonNull
    @Override
    public Result doWork() {
        new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "leavemaster"),
                getTagURL(Constants.LEAVE_MASTER, GET_LEAVE_DETAIL),
                "GetLeaveDetail");
//        new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "leavemaster"), "GetLeaveDetail", "GetLeaveDetail");

        return Result.SUCCESS;
    }

    public static String getTagURL(String tableName, String urlParam) {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), tableName);

        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(urlParam).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(urlParam).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        return Uri.parse(url).buildUpon().build().toString();

    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        Log.e("GetLeaveDetail>>>", "GetLeaveDetail ....   " + loadedString + "  regNO...   " + regNO);

        try {
            JSONObject jsonObject1 = new JSONObject(loadedString);
            if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<LeaveMaster>>() {
                }.getType();
//                List<String> deletedItemList = new ArrayList<>();
                leaveEmployeeMastersList = new Gson().fromJson(jsonObject1.getJSONArray("data").toString(), listType);
//                for (int i = 0; i < leaveEmployeeMastersList.size(); i++) {
//                    JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(i);
//                    leaveEmployeeMastersList.get(i).setJson(object.toString());
//                    leaveEmployeeMastersList.get(i).setFlag("0");
//                    if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
//                        deletedItemList.add(object.optString("ID"));
//                    }
//                }
                for (int j = 0; j < leaveEmployeeMastersList.size(); j++) {
                    if (CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().getDetails(leaveEmployeeMastersList.get(j).getID()) == null) {
                        JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(j);
                        leaveEmployeeMastersList.get(j).setFlag("0");
                        leaveEmployeeMastersList.get(j).setJson(object.toString());
                        CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().insertLeaveEmployee(leaveEmployeeMastersList.get(j));
                    } else {
                        String flag = CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().getDetails(leaveEmployeeMastersList.get(j).getID()).getFlag();
                        if (flag != null && flag.equals("0")) {
                            CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().updateLeaveEmployee(leaveEmployeeMastersList.get(j));
                        }
                    }
                }
//                if (deletedItemList.size() > 0) {
//                    CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().deleteAll(deletedItemList);
//                }
                //************************** save  MASTER table in DB *****************************************************************
            } else {
                Log.e("ELSEMEIN_AAYA", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + String.valueOf(e));
        }
    }
}
