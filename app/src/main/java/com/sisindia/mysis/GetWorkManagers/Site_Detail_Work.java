package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.GetSiteDetail;
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

import static com.sisindia.mysis.utils.Constants.GETSITEDETAILS;
import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;

public class Site_Detail_Work extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";
    private List<GetSiteDetail> siteDetailList;

    @NonNull
    @Override
    public Result doWork() {
        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL(), "GetSite");
        return Result.SUCCESS;
    }

    private String getTagURL() {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), GETSITEDETAILS);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_SITE).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_SITE).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        return Uri.parse(url).buildUpon().appendQueryParameter("UnitCode", "").build().toString();
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        try {
            JSONObject jsonObject = new JSONObject(loadedString);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {

                Type listType = new TypeToken<List<GetSiteDetail>>() {
                }.getType();
                List<String> deletedEmpList = new ArrayList<>();

                siteDetailList = new Gson().fromJson(jsonObject.getJSONArray("Site").toString(), listType);
                if (siteDetailList != null) {
                    for (int i = 0; i < siteDetailList.size(); i++) {
                        siteDetailList.get(i).setJSON(jsonObject.getJSONArray("Site").getJSONObject(i).toString());
                    }
                    for (int i = 0; i < siteDetailList.size(); i++) {
                        JSONObject object = jsonObject.getJSONArray("Site").getJSONObject(i);
                        siteDetailList.get(i).setJSON(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedEmpList.add(siteDetailList.get(i).getID());
                        }
                    }
                    if (deletedEmpList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().siteDetail_DAO().deleteAll(deletedEmpList);
                    }
                    CSApplicationHelper.application().getDatabaseInstance().siteDetail_DAO().insert(siteDetailList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}

