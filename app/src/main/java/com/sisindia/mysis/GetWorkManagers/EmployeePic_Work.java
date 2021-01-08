package com.sisindia.mysis.GetWorkManagers;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;

import static com.sisindia.mysis.utils.Constants.EMPLOYEEDETAILS_TABLE;

public class EmployeePic_Work extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";


    @NonNull
    @Override
    public Result doWork() {
        String tag = Constants.GET_EMPLOYEE_PICTURE + "?" + "RegNo=" + CSShearedPrefence.getUser();

//            Log.e("EmployeDetailsModel", "Value " + CSShearedPrefence.getUser());

        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL(), CSShearedPrefence.getUser());
//                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), tag, CSShearedPrefence.getUser());

        return Result.SUCCESS;
    }

    private String getTagURL() {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), EMPLOYEEDETAILS_TABLE);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_EMPLOYEE_PICTURE).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_EMPLOYEE_PICTURE).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        return Uri.parse(url).buildUpon().appendQueryParameter("RegNo", CSShearedPrefence.getUser()).build().toString();
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        try {
            JSONObject jsonObject = new JSONObject(loadedString);
            JSONArray jsonArray;
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                jsonArray = jsonObject.getJSONArray("EmployeePicture");
                /*CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                        updateUserPicture(jsonArray.getJSONObject(0).getString("REGNO"), getImageBYTE(
                                jsonArray.getJSONObject(0).getString("PICTURE")),
                                jsonArray.getJSONObject(0).getString("UnitCode"));*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public byte[] getImageBYTE(String base64ImageData) {
        FileOutputStream fos = null;
        byte[] decodedString = null;
        try {
            if (base64ImageData != null) {
                fos = getApplicationContext().openFileOutput("imageName.png", Context.MODE_PRIVATE);
                decodedString = android.util.Base64.decode(base64ImageData, android.util.Base64.DEFAULT);
                fos.write(decodedString);
                fos.flush();
                fos.close();
            }

        } catch (Exception e) {

        } finally {
            if (fos != null) {
                fos = null;
            }
        }
        return decodedString;
    }
}
