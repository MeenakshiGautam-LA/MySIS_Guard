package com.sisindia.mysis.GetWorkManagers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.App_Update_Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

import static com.sisindia.mysis.utils.Constants.GET_CHECK_UPDATE;

public class CheckAppUpdate_Worker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private App_Update_Model app_update_model;

    @NonNull
    @Override
    public Result doWork() {
//        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword())
//                , CFUtil.parameter_Format(Constants.GET_EMPLOYEE, "RegNo", CSShearedPrefence.getUser(), "lastModifiedDatetime", ""),
//                "EMPLOYEE_DETAILS");

//        new Volley_Asynclass(this, this, CSApplicationHelper.application().getPostDataParam(), GET_CHECK_UPDATE, 000);

        new Volley_Asynclass_Get(this, CSApplicationHelper.application().getPostDataParam(),
                GET_CHECK_UPDATE, "APP_UPDATE");

        return Result.SUCCESS;
    }


    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {

        Log.e("APP_UPDATE>>", "" + loadedString);
        if (regNO.equals("APP_UPDATE")) {
            try {
                JSONObject response = new JSONObject(loadedString);
                if (response.optString("status").equals("true")) {

                    JSONArray jsonArray = response.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        Type listType = new TypeToken<App_Update_Model>() {
                        }.getType();
                        app_update_model = new Gson().fromJson(jsonArray.getJSONObject(0).toString(), listType);
                        String app_ver_code = CSApplicationHelper.application().getPackageManager().getPackageInfo(CSApplicationHelper.application().
                                getPackageName(), 0).versionName;
                        if (!app_ver_code.equalsIgnoreCase(app_update_model.getFILE_VERSION())) {
//                            show_App_Update_Dialog();
                            app_update_model.setFLAG("1");
                            CSApplicationHelper.application().getDatabaseInstance().app_update_dao().insert(app_update_model);
                      /*  downloadUpdateBTN.setOnClickListener(view -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sisindia.guardattendance"));
                            startActivity(intent);
                        });*/
                        } else {
                            CSApplicationHelper.application().getDatabaseInstance().app_update_dao().delete(app_update_model);

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

