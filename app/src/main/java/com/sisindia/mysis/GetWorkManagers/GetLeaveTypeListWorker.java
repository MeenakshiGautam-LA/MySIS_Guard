package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.LeaveTypeListModel;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sisindia.mysis.utils.Constants.GET_LEAVE_DETAIL;

public class GetLeaveTypeListWorker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private ArrayList<LeaveTypeListModel> leaveTypeListModels;

    @NonNull
    @Override
    public Result doWork() {
        new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "leavetypelist"), GET_LEAVE_DETAIL,
                "GetLeaveDetail");
//        new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "leavemaster"), "GetLeaveDetail", "GetLeaveDetail");
        return Result.SUCCESS;
    }
    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        Log.e("GetLeaveDetail>>>", "GetLeaveDetail ....   " + loadedString + "  regNO...   " + regNO);

        try {
            JSONObject jsonObject1 = new JSONObject(loadedString);
            if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<LeaveTypeListModel>>() {
                }.getType();
//                List<String> deletedItemList = new ArrayList<>();
                leaveTypeListModels = new Gson().fromJson(jsonObject1.getJSONArray("data").toString(), listType);
//                for (int i = 0; i < leaveEmployeeMastersList.size(); i++) {
//                    JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(i);
//                    leaveEmployeeMastersList.get(i).setJson(object.toString());
//                    leaveEmployeeMastersList.get(i).setFlag("0");
//                    if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
//                        deletedItemList.add(object.optString("ID"));
//                    }
//                }
               CSApplicationHelper.application().getDatabaseInstance().getLeaveTypeList_dao().insert(leaveTypeListModels);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + String.valueOf(e));
        }
    }
}
