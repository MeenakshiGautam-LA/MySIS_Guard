package com.sisindia.mysis.PostWorkerManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sisindia.mysis.BuildConfig;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.ProfilePictureMultipartsRequest;
import com.sisindia.mysis.asynctask.WebServiceFileUploadViaPost;
import com.sisindia.mysis.entity.AttendanceGuardPicModel;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post_MarkAttendance_Selfie_Worker extends Worker implements
        WebServiceFileUploadViaPost.AsyncRequestListenerViaPost {
    @NonNull
    @Override
    public Result doWork() {
        List<AttendanceGuardPicModel> getListToSync = CSApplicationHelper.application().getDatabaseInstance().attendancePicDao().getListToSync("1");

        if (getListToSync.size() > 0) {
            try {
                for (int i = 0; i < getListToSync.size(); i++) {

                    File file = convertbyteToFile(getListToSync.get(i).getPicture());


                    ProfilePictureMultipartsRequest imageUploadReq = new ProfilePictureMultipartsRequest(
                            Constants.BASE_URL + Constants.UPLOAD_IMAGE_TAG,
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("EmailBGService", "Service $$$$$ Multiple Attatachmet failed area");
                                }
                            }, file, "testing",

                            new ProfilePictureMultipartsRequest.ProfilePicUpdateListener() {
                                @Override
                                public void onProfilePicUpdateSuccessfully(String response) {
                                    Log.e("EmailBGService", "Service Server Returned Successs ID :" + response);

                                    try {
                                        Log.e("DAILY_ATTENDANCEDETAIL>", "DAILY_ATTENDANCEDETAIL.....   " + response.toString());
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.optString("status").equals("true")) {
                                            // JSONObject jsonObject = response.getJSONObject("data");
                                            JSONArray array = jsonObject.getJSONArray("data");
                                            //
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject idObject = array.getJSONObject(i);
                                                String ID = idObject.optString("ID");
                                                CSApplicationHelper.application().getDatabaseInstance().attendancePicDao().deleteRecord(ID);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, getPostDataParam(getListToSync.get(i)));
                    CSApplicationHelper.application().addToRequestQueue(imageUploadReq, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Result.SUCCESS;
    }

    public Map<String, String> getPostDataParam(AttendanceGuardPicModel attendanceGuardPicModel) {
        HashMap postDataParam = new HashMap<>();
//        postDataParam.put("Content-Type", "application/json");
        postDataParam.put("UserName", CSShearedPrefence.getUser());
        postDataParam.put("AttendanceId", attendanceGuardPicModel.getAttendanceID());
        postDataParam.put("DutyStatus", attendanceGuardPicModel.getDUTY_STATUS());
        postDataParam.put("ImageId", attendanceGuardPicModel.getId());
        postDataParam.put("Password", CSShearedPrefence.getPassword());
        postDataParam.put("DeviceID", CSShearedPrefence.getDeviceToken());
        postDataParam.put("AppType", Constants.APP_TYPE);
        postDataParam.put("VERSION", BuildConfig.VERSION_NAME);
        return postDataParam;
    }
//    private File convertByteToFile(Bitmap bmp) {
//        FileInputStream fileInputStream = null;
//        File file = null;
//        try {
//
//            File dir = new File(path_external);
//            if (!dir.exists())
//                dir.mkdirs();
//            file = new File(dir, "sketchpad" + 1 + ".png");
//            FileOutputStream fOut = new FileOutputStream(file);
//
//            bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
//            fOut.flush();
//            fOut.close();
//            System.out.println("Done");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file;
//    }

    public static Bitmap getBitmapFromBytes(byte[] bytes) {

        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
    }

    private File convertbyteToFile(byte[] bytes) {
        String FILEPATH = Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg";

        File file = new File(FILEPATH);

        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream
                    os
                    = new FileOutputStream(file);

            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return file;
    }

    @Override
    public void onRequestComplete(String loadedString) {
        Log.e("onRequestComplete>>", "" + loadedString);

    }
}
