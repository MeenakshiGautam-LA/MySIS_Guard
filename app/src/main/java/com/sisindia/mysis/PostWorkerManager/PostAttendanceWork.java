package com.sisindia.mysis.PostWorkerManager;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.VolleyAsyncClassPost;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.CSUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.sisindia.mysis.utils.Constants.DAILY_ATTENDANCEDETAIL;
import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;
import static com.sisindia.mysis.utils.Constants.POST_DAILY_ATTENDANCE;

public class PostAttendanceWork extends Worker implements VolleyAsyncClassPost.VolleyPostResponse {
    @NonNull
    @Override
    public Result doWork() {

        List<DailyAttendanceDetail> getListToSync = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getListToSync("1");
        if (getListToSync.size() > 0) {
            try {
                JSONArray array = new JSONArray();

                Log.e("getListToSync", "PostAttendanceWork" + getListToSync.size());
                for (int i = 0; i < getListToSync.size(); i++) {
                    array.put(new JSONObject(getListToSync.get(i).getJSON()));
                }
                new VolleyAsyncClassPost(this, array, POST_DAILY_ATTENDANCE, DAILY_ATTENDANCEDETAIL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Result.SUCCESS;

    }

    @Override
    public void postDataFromVolleyInterface(JSONObject response, String tableName) {
        if (tableName.equals(DAILY_ATTENDANCEDETAIL)) {
            try {
                Log.e("DAILY_ATTENDANCEDETAIL>", "DAILY_ATTENDANCEDETAIL.....   " + response.toString());
                if (response.optString("status").equals("true")) {
                    // JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray array = response.getJSONArray("data");
                    //
                    CSShearedPrefence.setAttendanceSyncTime(DateUtils.getInstance().getSaveDateTimeString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject idObject = array.getJSONObject(i);
                        String ID = idObject.optString("ID");
                        CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().updateFlag(ID, "0");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
