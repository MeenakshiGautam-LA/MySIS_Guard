package com.sisindia.mysis.PostWorkerManager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.VolleyAsyncClassPost;
import com.sisindia.mysis.entity.NotificationModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.sisindia.mysis.utils.Constants.NOTIFICATION_TABLE;
import static com.sisindia.mysis.utils.Constants.POST_NOTIFICATION;

public class PostNotificationWorker extends Worker implements VolleyAsyncClassPost.VolleyPostResponse {
    @NonNull
    @Override
    public Result doWork() {

        List<NotificationModel> getListToSync = CSApplicationHelper.application().getDatabaseInstance().notification_dao().getListToSync("1");
        if (getListToSync.size() > 0) {
            try {
                JSONArray array = new JSONArray();
                Log.e("getListToSync", "PostAttendanceWork" + getListToSync.size());
                for (int i = 0; i < getListToSync.size(); i++) {
                    array.put(new JSONObject(getListToSync.get(i).getJson()));
                }
                new VolleyAsyncClassPost(this, array, POST_NOTIFICATION, NOTIFICATION_TABLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Result.SUCCESS;

    }

    @Override
    public void postDataFromVolleyInterface(JSONObject response, String tableName) {
        if (tableName.equals(NOTIFICATION_TABLE)) {
            try {
                Log.e("NOTIFICATION_TABLE>", "NOTIFICATION_TABLE.....   " + response.toString());
                if (response.optString("status").equals("true")) {
                    // JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray array = response.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject idObject = array.getJSONObject(i);
                        String ID = idObject.optString("ID");
                        CSApplicationHelper.application().getDatabaseInstance().notification_dao().updateFlag(ID, "0");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

