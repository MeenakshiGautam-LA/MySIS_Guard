package com.sisindia.mysis.PostWorkerManager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.VolleyAsyncClassPost;
import com.sisindia.mysis.entity.LeaveDetailTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.sisindia.mysis.utils.Constants.POST_LEAVE_DETAIL;

public class PostLeaveTransactionWorker extends Worker implements VolleyAsyncClassPost.VolleyPostResponse {
    @NonNull
    @Override
    public Result doWork() {

        List<LeaveDetailTransaction> getListToSync = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao()
                .getListToSync("1");
        if (getListToSync.size() > 0) {
            try {
                JSONArray array = new JSONArray();

                Log.e("getListToSync", "PostLeaveTransactionWorker" + getListToSync.size());
                for (int i = 0; i < getListToSync.size(); i++) {
                    array.put(new JSONObject(getListToSync.get(i).getJSON()));
                }
                new VolleyAsyncClassPost(this, array, POST_LEAVE_DETAIL, POST_LEAVE_DETAIL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Result.SUCCESS;

    }

    @Override
    public void postDataFromVolleyInterface(JSONObject response, String tableName) {
        if (tableName.equals(POST_LEAVE_DETAIL)) {
            try {
                Log.e("PostLeaveTransaction>", "PostLeaveTransactionWorker.....   " + response.toString());
                if (response.optString("status").equals("true")) {
                    // JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray array = response.getJSONArray("data");
                    //
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject idObject = array.getJSONObject(i);
                        String ID = idObject.optString("ID");
                        CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().updateFlag(ID, "0");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
