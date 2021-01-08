package com.sisindia.mysis.GetWorkManagers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.RosterModel;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.CSUtil;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sisindia.mysis.utils.Constants.GET_ROSTER_URL_NEW;
import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;
import static com.sisindia.mysis.utils.Constants.Roster_TABLE;

public class GetRosterWorker extends Worker implements Volley_Asynclass_Get.VolleyResponse {
    private static final String WORK_RESULT = "work_result";
    private List<RosterModel> rosterModelList;

    @NonNull
    @Override
    public Result doWork() {
        new Volley_Asynclass_Get(this, NetworkUtils.paramterForRoster_NEW(getLastDateModified()),
                GET_ROSTER_URL_NEW, "GET_ROSTER_URL_NEW");

        return Result.SUCCESS;
    }

    private String getLastDateModified() {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), Roster_TABLE);
       /* if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(Constants.GET_ROSTER_URL).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(Constants.GET_ROSTER_URL).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }*/
//        return Uri.parse(url).buildUpon().build().toString();
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
        }
        return maxModifiedDate;
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {
        Log.e("GetRosterWorker>>>", "" + loadedString);
        parseRosterDetail(loadedString);
    }

    private void parseRosterDetail(String loadedString) {
        try {
            JSONObject jsonObject = new JSONObject(loadedString);
            if (jsonObject.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<RosterModel>>() {
                }.getType();
                List<String> deletedEmpList = new ArrayList<>();
                rosterModelList = new Gson().fromJson(jsonObject.getJSONArray("data").toString(), listType);
                if (rosterModelList != null) {

                    for (int i = 0; i < rosterModelList.size(); i++) {
                        JSONObject object = jsonObject.getJSONArray("data").getJSONObject(i);
                        rosterModelList.get(i).setJson(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedEmpList.add(rosterModelList.get(i).getID());
                        }
                    }
                    for (int i = 0; i < rosterModelList.size(); i++) {
                        Log.e("ROSTER_DATE", "IFcompareTime" + rosterModelList.get(i).getROSTERDATE());

                        if (DateUtils.compareTimeT1BeforeT2(rosterModelList.get(i).getStart_time(),
                                rosterModelList.get(i).getEnd_time())) {
                            Log.e("IF_SYSTEMTIME", "IFcompareTime");
                            rosterModelList.get(i).setStart_time(DateUtils.getContcat_Date_Time_second(rosterModelList.get(i).getROSTERDATE(),
                                    rosterModelList.get(i).getStart_time()));
                            rosterModelList.get(i).setEnd_time(DateUtils.getContcat_Date_Time_second(rosterModelList.get(i).getROSTERDATE(),
                                    rosterModelList.get(i).getEnd_time()));
                        } else {
                            Log.e("IF_SYSTEMTIME", "ELSEE_IFcompareTime");
                            if (DateUtils.compareTimeT1BeforeT2(DateUtils.systemTime(), rosterModelList.get(i).getStart_time())) {
                                Log.e("IF_SYSTEMTIME", "IF_COMPARE");
                                String tomorrowDate = DateUtils.getInstance().getNextDateCalender(rosterModelList.get(i).getROSTERDATE());

                                rosterModelList.get(i).setEnd_time(DateUtils.getContcat_Date_Time_second(tomorrowDate,
                                        rosterModelList.get(i).getEnd_time()));

                                rosterModelList.get(i).setStart_time(DateUtils.getContcat_Date_Time_second(rosterModelList.get(i).getROSTERDATE(),
                                        rosterModelList.get(i).getStart_time()));
                            } else {
                                Log.e("IF_SYSTEMTIME", "ELSE_COMPARE");
                                rosterModelList.get(i).setStart_time(DateUtils.getContcat_Date_Time_second(rosterModelList.get(i).getROSTERDATE(),
                                        rosterModelList.get(i).getStart_time()));
                                String tomorrowDate = DateUtils.getInstance().getNextDateCalender(rosterModelList.get(i).getROSTERDATE());

                                rosterModelList.get(i).setEnd_time(DateUtils.getContcat_Date_Time_second(tomorrowDate,
                                        rosterModelList.get(i).getEnd_time()));

                            }
                        }
                        rosterModelList.get(i).setJson(jsonObject.getJSONArray("data").getJSONObject(i).toString());
                        CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().singleInsert(rosterModelList.get(i));

                    }
                    if (deletedEmpList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().rosterDetailDao().deleteAll(deletedEmpList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

