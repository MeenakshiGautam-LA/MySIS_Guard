package com.sisindia.mysis.Services;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.sisindia.mysis.ApiCalling_Class;
import com.sisindia.mysis.PostWorkerManager.PostAttendanceWork;
import com.sisindia.mysis.PostWorkerManager.Post_MarkAttendance_Selfie_Worker;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSAppUtil;

public class BackgroundApiService extends IntentService {

    public BackgroundApiService() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null){
            String message = intent.getStringExtra("message");
            ApiCalling_Class calling_class=new ApiCalling_Class(this);
            if(message.equalsIgnoreCase("POST_ATTENDANCE")){
//                callPost_Nd_GET_DailyAttendanceDetailWorker();
                calling_class.push_Attendance_detail();
            }else if(message.equalsIgnoreCase("POST_ATTENDANCE_WHEN_MARK_ATTENDANCE")){
                calling_class.postAttendance_details_when_mark_attendance();
            }
            else {
                calling_class.push_All_Apis();
//                CFUtil.call_Get_DataFrom_Server();
            }
        }
    }
    private void callPost_Nd_GET_DailyAttendanceDetailWorker() {
        if (CSAppUtil.isNetworkAvailable(this, false)) {
            OneTimeWorkRequest postAttendanceWork = new OneTimeWorkRequest.Builder(PostAttendanceWork.class).build();
            OneTimeWorkRequest postEmployeeSelfieWorker = new OneTimeWorkRequest.Builder(Post_MarkAttendance_Selfie_Worker.class).build();

            WorkManager.getInstance().beginWith(postAttendanceWork)
                    .then(postEmployeeSelfieWorker)
                    .enqueue();

        }
    }
}
