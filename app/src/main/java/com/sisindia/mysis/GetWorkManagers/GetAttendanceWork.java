package com.sisindia.mysis.GetWorkManagers;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import com.sisindia.mysis.PostWorkerManager.PostAttendanceWork;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
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

import static com.sisindia.mysis.utils.Constants.DAILY_ATTENDANCEDETAIL;
import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;

public class GetAttendanceWork extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";

    private ArrayList<DailyAttendanceDetail> dailyAttendanceDetailArrayList;

    @NonNull
    @Override
    public Result doWork() {
        new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "UnitDailyAttendanceDetail"), getTagURL(),
                "GetUnitDailyAttendanceDetail");
        return Result.SUCCESS;
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        try {
            JSONObject jsonObject1 = new JSONObject(loadedString);
            if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<DailyAttendanceDetail>>() {
                }.getType();
                List<String> deletedItemList = new ArrayList<>();

                dailyAttendanceDetailArrayList = new Gson().fromJson(jsonObject1.getJSONArray("UnitDailyAttendanceDetail").toString(), listType);
                for (int i = 0; i < dailyAttendanceDetailArrayList.size(); i++) {
                    JSONObject object = jsonObject1.getJSONArray("UnitDailyAttendanceDetail").getJSONObject(i);
                    dailyAttendanceDetailArrayList.get(i).setJSON(object.toString());
                    if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                        deletedItemList.add(object.optString("ID"));
                    }
                }

                for (int i = 0; i < dailyAttendanceDetailArrayList.size(); i++) {
                    dailyAttendanceDetailArrayList.get(i).setJSON(jsonObject1.getJSONArray("UnitDailyAttendanceDetail").getJSONObject(i).toString());
                }
                for (int j = 0; j < dailyAttendanceDetailArrayList.size(); j++) {
                    if (CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getDetails(dailyAttendanceDetailArrayList.get(j).getID()) == null) {
                        CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().insert(dailyAttendanceDetailArrayList.get(j));
                    } else {
                        if (CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getDetails(dailyAttendanceDetailArrayList.get(j).getID()).getFlag().equals("0")) {
                            CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().update(dailyAttendanceDetailArrayList.get(j));
                        }
                    }
                }

                if (deletedItemList.size() > 0) {
                    CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().deleteAll(deletedItemList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        OneTimeWorkRequest postAttendanceWork = new OneTimeWorkRequest.Builder(PostAttendanceWork.class).build();
        WorkManager.getInstance()
                .beginWith(postAttendanceWork)
                .enqueue();
    }

    private String getTagURL() {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), DAILY_ATTENDANCEDETAIL);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_ATTENDANCE_DETAIL).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_ATTENDANCE_DETAIL).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        Log.e("getUser", "maxModifiedDate>>>" + CSShearedPrefence.getUser());
//        return Uri.parse(url).buildUpon().appendQueryParameter("REGNO", CSShearedPrefence.getUser()).build().toString();
        return Uri.parse(url).buildUpon().appendQueryParameter("REGNO", CSShearedPrefence.getUser()).build().toString();
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
    }
}
