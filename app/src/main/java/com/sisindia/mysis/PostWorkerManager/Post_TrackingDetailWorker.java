package com.sisindia.mysis.PostWorkerManager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.VolleyAsyncClassPost;
import com.sisindia.mysis.entity.PeriodicTrackingInfoModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.sisindia.mysis.utils.Constants.POST_TRACKING_URL;

public class Post_TrackingDetailWorker extends Worker implements VolleyAsyncClassPost.VolleyPostResponse {
    @NonNull
    @Override
    public Result doWork() {
        List<PeriodicTrackingInfoModel> getListToSync = CSApplicationHelper.application().getDatabaseInstance().trackingInfoDAO().getListToSync("1");
        if (getListToSync.size() > 0) {
            try {
                JSONArray array = new JSONArray();
                for (int i = 0; i < getListToSync.size(); i++) {
                    array.put(new JSONObject(getListToSync.get(i).getJSON()));
                }
                new VolleyAsyncClassPost(this, array, POST_TRACKING_URL, POST_TRACKING_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Result.SUCCESS;

    }

    @Override
    public void postDataFromVolleyInterface(JSONObject response, @NotNull String tableName) {
        if (tableName.equals(POST_TRACKING_URL)) {
            try {
                Log.e("POST_TRACKING_URL>", "POST_TRACKING_URL.....   " + response.toString());
                if (response.optString("status").equals("true")) {
                    JSONArray array = response.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject idObject = array.getJSONObject(i);
                        String ID = idObject.optString("ID");
                        CSApplicationHelper.application().getDatabaseInstance().trackingInfoDAO().deleteRecord(ID);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

