package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.dataBase.EmployeDetailsModel;
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

public class EmployeeDetailWorker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private List<EmployeDetailsModel> employeDetailsModelList;

    @NonNull
    @Override
    public Result doWork() {
//        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword())
//                , CFUtil.parameter_Format(Constants.GET_EMPLOYEE, "RegNo", CSShearedPrefence.getUser(), "lastModifiedDatetime", ""),
//                "EMPLOYEE_DETAILS");


        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()),
                getTagURL(Constants.EMPLOYEEDETAILS_TABLE, Constants.GET_EMPLOYEE),
                "EMPLOYEE_DETAILS");

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
        return Uri.parse(url).buildUpon().appendQueryParameter("RegNo",CSShearedPrefence.getUser()).build().toString();

    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {

        Log.e("EMPLOYEE_DETAILS>>", "" + loadedString);
        Log.e("regNO>>", "" + regNO);
        if (regNO.equals("EMPLOYEE_DETAILS")) {
            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<EmployeDetailsModel>>() {
                    }.getType();

                    List<String> deletedEmpList = new ArrayList<>();

                    employeDetailsModelList = new Gson().fromJson(jsonObject.getJSONArray("Employee").toString(), listType);
                    for (int i = 0; i < employeDetailsModelList.size(); i++) {
                        JSONObject object = jsonObject.getJSONArray("Employee").getJSONObject(i);
                        employeDetailsModelList.get(i).setJson(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedEmpList.add(employeDetailsModelList.get(i).getID());
                        }
                    }

                    for (int j = 0; j < employeDetailsModelList.size(); j++) {
                        if (CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().getDetails(employeDetailsModelList.get(j).getID()) == null) {
                            CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().insertSingleRecord(employeDetailsModelList.get(j));
                        } else {
                            if (CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().getDetails(employeDetailsModelList.get(j).getID()).getFlag().equals("0")) {
                                CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().update(employeDetailsModelList.get(j));
                            }
                        }
                    }
                    if (deletedEmpList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().deleteAll(deletedEmpList);
                    }
                } else {
                    Log.e("ELSEMEIN_AAYA", "");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

