package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.Main_Model_Of_profile;
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
import static com.sisindia.mysis.utils.Constants.USER_DETAIL;

public class GetProfile_Unit_DetailWorker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";

    private Main_Model_Of_profile main_model_of_profiles;

    @NonNull
    @Override
    public Result doWork() {
        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL(),
                "GetUnitDailyAttendanceDetail");
        return Result.SUCCESS;
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        try {
            JSONObject jsonObject1 = new JSONObject(loadedString);
            if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<Main_Model_Of_profile>() {
                }.getType();
                List<String> deletedItemList = new ArrayList<>();

                main_model_of_profiles = new Gson().fromJson(jsonObject1.getJSONObject("data").toString(), listType);
//                main_model_of_profiles.getProfileDetail().get(0).setFlag("0");
                main_model_of_profiles.getProfileDetail().get(0).setSave_JSON(jsonObject1.getJSONObject("data").getJSONArray("ProfileDetail").getJSONObject(0).toString());
                CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                        insert(main_model_of_profiles.getProfileDetail());
                for (int i = 0; i < main_model_of_profiles.getUnitDetail().size(); i++) {
                    JSONObject object = jsonObject1.getJSONObject("data").getJSONArray("UnitDetail").getJSONObject(i);
                    main_model_of_profiles.getUnitDetail().get(i).setJSON(object.toString());
                    if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                        deletedItemList.add(object.optString("ID"));
                    }
                }
                for (int j = 0; j < main_model_of_profiles.getUnitDetail().size(); j++) {
                    if (CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().
                            getDetails(main_model_of_profiles.getUnitDetail().get(j).getID()) == null) {
                        CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().
                                singleInsert(main_model_of_profiles.getUnitDetail().get(j));
                    } else {
                        if (CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().
                                getDetails(main_model_of_profiles.getUnitDetail().get(j).getID()).getFLAG().equals("0")) {
                            CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().
                                    update(main_model_of_profiles.getUnitDetail().get(j));
                        }
                    }
                }

                if (deletedItemList.size() > 0) {
                    CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().deleteAll(deletedItemList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTagURL() {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), USER_DETAIL);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_PROFILE_DETAIL).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_PROFILE_DETAIL).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        return Uri.parse(url).buildUpon().appendQueryParameter("REGNO", CSShearedPrefence.getUser()).build().toString();
    }
}

