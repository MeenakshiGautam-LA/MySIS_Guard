package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.Flash_Message_Model;
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
import static com.sisindia.mysis.utils.Constants.FLASH_MESSAGE_TABLE;
import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;

public class Flash_Message_Worker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";

    private ArrayList<Flash_Message_Model> flash_message_modelArrayList;

    @NonNull
    @Override
    public Result doWork() {
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(),
                FLASH_MESSAGE_TABLE);

        new Volley_Asynclass_Get(this, NetworkUtils.flash_Message_Json_Parameter(maxModifiedDate == null ?"":maxModifiedDate , "flashmessage")
                , Constants.GET_FLASH_MESSAGE,
                FLASH_MESSAGE_TABLE);
        return Result.SUCCESS;
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        try {
            Log.e("FLASH_MESSAGE",""+loadedString);

            JSONObject jsonObject1 = new JSONObject(loadedString);
            if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<Flash_Message_Model>>() {
                }.getType();
                List<String> deletedItemList = new ArrayList<>();

                flash_message_modelArrayList = new Gson().fromJson(jsonObject1.getJSONArray("data").toString(), listType);
                for (int i = 0; i < flash_message_modelArrayList.size(); i++) {
                    JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(i);
                    flash_message_modelArrayList.get(i).setJSON(object.toString());
                    if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                        deletedItemList.add(object.optString("ID"));
                    }
                }
                for (int i = 0; i < flash_message_modelArrayList.size(); i++) {
                    flash_message_modelArrayList.get(i).setJSON(jsonObject1.getJSONArray("data").getJSONObject(i).toString());
                }
                for (int j = 0; j < flash_message_modelArrayList.size(); j++) {
                    if (CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().getDetails(flash_message_modelArrayList.get(j).getID())
                            == null) {
                        CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().singleInsert(flash_message_modelArrayList.get(j));
                    } else {
                        if (CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().getDetails(flash_message_modelArrayList.get(j).getID()).getFLAG().equals("0")) {
                            CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().update(flash_message_modelArrayList.get(j));
                        }
                    }
                }

                if (deletedItemList.size() > 0) {
                    CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().deleteAll(deletedItemList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTagURL() {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), FLASH_MESSAGE_TABLE);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_FLASH_MESSAGE).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_FLASH_MESSAGE).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        Log.e("getUser", "maxModifiedDate>>>" + CSShearedPrefence.getUser());
//        return Uri.parse(url).buildUpon().appendQueryParameter("REGNO", CSShearedPrefence.getUser()).build().toString();
        return Uri.parse(url).buildUpon().appendQueryParameter("REGNO", CSShearedPrefence.getUser()).build().toString();
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
    }
}

