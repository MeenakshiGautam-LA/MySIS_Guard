package com.sisindia.mysis.GetWorkManagers;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.Duty_PostDetail_Model;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;

public class GetUnitDutyPostDetail_WORK extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";
    private List<Duty_PostDetail_Model> unitDutyPostDetailList;

    @NonNull
    @Override
    public Result doWork() {


        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), CFUtil.parameter_Format(Constants.GET_UNIT_DUTY_POST_DETAILS,
                "UnitCode", "", "lastModifiedDatetime", ""), "GetUnitDutyPostDetail");
        return Result.SUCCESS;
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        try {
            JSONObject jsonObject = new JSONObject(loadedString);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<Duty_PostDetail_Model>>() {
                }.getType();
                List<String> deletedEmpList = new ArrayList<>();

                unitDutyPostDetailList = new Gson().fromJson(jsonObject.getJSONArray("UnitDutyPostDetail").toString(), listType);
                if (unitDutyPostDetailList != null) {
                    for (int i = 0; i < unitDutyPostDetailList.size(); i++) {
                        unitDutyPostDetailList.get(i).setJson(jsonObject.getJSONArray("UnitDutyPostDetail").getJSONObject(i).toString());
                    }
                    for (int i = 0; i < unitDutyPostDetailList.size(); i++) {
                        JSONObject object = jsonObject.getJSONArray("UnitDutyPostDetail").getJSONObject(i);
                        unitDutyPostDetailList.get(i).setJson(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedEmpList.add(unitDutyPostDetailList.get(i).getID());
                        }
                    }
                }


                for (int j = 0; j < unitDutyPostDetailList.size(); j++) {
                    if (CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().getDutyPostDetail(unitDutyPostDetailList.get(j).getID()) == null) {
                        CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().insertSingleRecord(unitDutyPostDetailList.get(j));
                    } else {
                        if (CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().getDutyPostDetail(unitDutyPostDetailList.get(j).getID()).getFlag().equals("0")) {
                            CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().update(unitDutyPostDetailList.get(j));
                        }
                    }
                }
                if (deletedEmpList.size() > 0) {
                    CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().deleteAll(deletedEmpList);
                }
                //************************** save  MASTER table in DB *****************************************************************
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

