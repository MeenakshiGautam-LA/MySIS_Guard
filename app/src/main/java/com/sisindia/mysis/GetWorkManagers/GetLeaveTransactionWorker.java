package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.LeaveDetailTransaction;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sisindia.mysis.utils.Constants.GET_LEAVE_DETAIL;


public class GetLeaveTransactionWorker extends Worker implements Volley_Asynclass_Get.VolleyResponse {

    private ArrayList<LeaveDetailTransaction> leaveEmployeeDetailsArrayList;

    @NonNull
    @Override
    public Result doWork() {
        new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "leavedetail"),
                getTagURL(Constants.LEAVE_TRANSACTION_DETAILS, GET_LEAVE_DETAIL),
                "GetLeaveDetail");
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
        if (regNO.toLowerCase().contains("GetLeaveDetail".toLowerCase())) {
            try {
                JSONObject jsonObject1 = new JSONObject(loadedString);
                if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<LeaveDetailTransaction>>() {
                    }.getType();

//                    List<String> deletedItemList = new ArrayList<>();

                    JSONArray array = jsonObject1.getJSONArray("data");
                    leaveEmployeeDetailsArrayList = new Gson().fromJson(array.toString(), listType);

//                    for (int i = 0; i < leaveEmployeeDetailsArrayList.size(); i++) {
//                        JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(i);
//                        leaveEmployeeDetailsArrayList.get(i).setJSON(object.toString());
//                        leaveEmployeeDetailsArrayList.get(i).setFlag("0");
//                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
//                            deletedItemList.add(object.optString("ID"));
//                        }
//                    }

                    for (int j = 0; j < leaveEmployeeDetailsArrayList.size(); j++) {
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().getDetails(leaveEmployeeDetailsArrayList.get(j).getID()) == null) {
                            JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(j);
                            leaveEmployeeDetailsArrayList.get(j).setFlag("0");
                            leaveEmployeeDetailsArrayList.get(j).setJSON(object.toString());
                            CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().insertLeaveEmployee(leaveEmployeeDetailsArrayList.get(j));
                        } else {
                            if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().getDetails(leaveEmployeeDetailsArrayList.get(j).getID()).
                                    getFlag().equals("0")) {
                                CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().updateLeaveEmployee(leaveEmployeeDetailsArrayList.get(j));
                            }
                        }
                    }

//                    if (deletedItemList.size() > 0) {
//                        CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().deleteAll(deletedItemList);
//                    }


                } else {
                    Log.e("ELSEMEIN_AAYA", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
