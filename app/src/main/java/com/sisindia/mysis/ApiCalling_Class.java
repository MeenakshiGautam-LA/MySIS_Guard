package com.sisindia.mysis;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.ProfilePictureMultipartsRequest;
import com.sisindia.mysis.asynctask.VolleyAsyncClassPost;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.dataBase.EmployeDetailsModel;
import com.sisindia.mysis.entity.AttendanceGuardPicModel;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.Duty_PostDetail_Model;
import com.sisindia.mysis.entity.Flash_Message_Model;
import com.sisindia.mysis.entity.GetSiteDetail;
import com.sisindia.mysis.entity.LeaveDetailTransaction;
import com.sisindia.mysis.entity.LeaveMaster;
import com.sisindia.mysis.entity.LeaveTypeListModel;
import com.sisindia.mysis.entity.Main_Model_Of_profile;
import com.sisindia.mysis.entity.NotificationModel;
import com.sisindia.mysis.entity.PeriodicTrackingInfoModel;
import com.sisindia.mysis.entity.RosterModel;
import com.sisindia.mysis.entity.ServiceInfoDetail;
import com.sisindia.mysis.entity.ShiftMasterModel;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.CSUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sisindia.mysis.utils.Constants.DAILY_ATTENDANCEDETAIL;
import static com.sisindia.mysis.utils.Constants.FLASH_MESSAGE_TABLE;
import static com.sisindia.mysis.utils.Constants.GETSITEDETAILS;
import static com.sisindia.mysis.utils.Constants.GET_LEAVE_DETAIL;
import static com.sisindia.mysis.utils.Constants.GET_ROSTER_URL_NEW;
import static com.sisindia.mysis.utils.Constants.NOTIFICATION_TABLE;
import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;
import static com.sisindia.mysis.utils.Constants.POST_DAILY_ATTENDANCE;
import static com.sisindia.mysis.utils.Constants.POST_LEAVE_DETAIL;
import static com.sisindia.mysis.utils.Constants.POST_LEAVE_MASTER;
import static com.sisindia.mysis.utils.Constants.POST_NOTIFICATION;
import static com.sisindia.mysis.utils.Constants.POST_TRACKING_URL;
import static com.sisindia.mysis.utils.Constants.Roster_TABLE;
import static com.sisindia.mysis.utils.Constants.SHIFTMASTER;
import static com.sisindia.mysis.utils.Constants.USER_DETAIL;

public class ApiCalling_Class implements Volley_Asynclass_Get.VolleyResponse, VolleyAsyncClassPost.VolleyPostResponse {
    private List<RosterModel> rosterModelList;
    private Context context;
    private ArrayList<DailyAttendanceDetail> dailyAttendanceDetailArrayList;
    private ArrayList<Flash_Message_Model> flash_message_modelArrayList;
    private List<EmployeDetailsModel> employeDetailsModelList;
    private Main_Model_Of_profile main_model_of_profiles;
    private ArrayList<LeaveDetailTransaction> leaveEmployeeDetailsArrayList;
    private List<ServiceInfoDetail> serviceInfoDetailList;
    private List<NotificationModel> notificationModelList;
    private List<GetSiteDetail> siteDetailList;
    private List<ShiftMasterModel> shiftMasterModelList;
    private List<Duty_PostDetail_Model> unitDutyPostDetailList;
    private ArrayList<LeaveMaster> leaveEmployeeMastersList;
    private ArrayList<LeaveTypeListModel> leaveTypeListModels;

    public ApiCalling_Class(Context context) {
        this.context = context;
    }

    public void calling_Api_On_verifyPin_() {
        List<DailyAttendanceDetail> postAttendance = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getListToSync("1");
        if (postAttendance.size() > 0) {
            try {
                JSONArray array = new JSONArray();

                Log.e("getListToSync", "PostAttendanceWork" + postAttendance.size());
                for (int i = 0; i < postAttendance.size(); i++) {
                    array.put(new JSONObject(postAttendance.get(i).getJSON()));
                }
                new VolleyAsyncClassPost(this, array, POST_DAILY_ATTENDANCE, DAILY_ATTENDANCEDETAIL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "UnitDailyAttendanceDetail"), getTagURL(Constants.GET_ATTENDANCE_DETAIL, DAILY_ATTENDANCEDETAIL),
                    "GetUnitDailyAttendanceDetail");
        }

        List<AttendanceGuardPicModel> postAttendance_Picture = CSApplicationHelper.application().getDatabaseInstance().
                attendancePicDao().getListToSync("1");

        if (postAttendance_Picture.size() > 0) {
            try {
                for (int i = 0; i < postAttendance_Picture.size(); i++) {

                    File file = convertbyteToFile(postAttendance_Picture.get(i).getPicture());


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
                            }, getPostDataParam(postAttendance_Picture.get(i)));
                    CSApplicationHelper.application().addToRequestQueue(imageUploadReq, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(),
                FLASH_MESSAGE_TABLE);

        new Volley_Asynclass_Get(this, NetworkUtils.flash_Message_Json_Parameter(maxModifiedDate == null ? "" : maxModifiedDate, "flashmessage")
                , Constants.GET_FLASH_MESSAGE,
                FLASH_MESSAGE_TABLE);
        new Volley_Asynclass_Get(this, NetworkUtils.paramterForRoster_NEW(getLastDateModified()),
                GET_ROSTER_URL_NEW, "GET_ROSTER_URL_NEW");

        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()),
                getTagURL(Constants.GET_EMPLOYEE, Constants.EMPLOYEEDETAILS_TABLE),
                "EMPLOYEE_DETAILS");
        new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL(Constants.GET_PROFILE_DETAIL,
                USER_DETAIL), "GET_PROFILE_DATA");
        new Volley_Asynclass_Get(ApiCalling_Class.this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), get_site_URL(Constants.GET_UNIT_SHIFT_MASTER, SHIFTMASTER),
                "GET_SHIFT_DETAILS");
    }

    public void push_Attendance_detail() {
        List<DailyAttendanceDetail> postAttendance = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getListToSync("1");
        if (postAttendance.size() > 0) {
            try {
                JSONArray array = new JSONArray();

                Log.e("getListToSync", "PostAttendanceWork" + postAttendance.size());
                for (int i = 0; i < postAttendance.size(); i++) {
                    array.put(new JSONObject(postAttendance.get(i).getJSON()));
                }
                new VolleyAsyncClassPost(this, array, POST_DAILY_ATTENDANCE, DAILY_ATTENDANCEDETAIL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "UnitDailyAttendanceDetail"), getTagURL(Constants.GET_ATTENDANCE_DETAIL, DAILY_ATTENDANCEDETAIL),
                    "GetUnitDailyAttendanceDetail");
        }

        List<AttendanceGuardPicModel> postAttendance_Picture = CSApplicationHelper.application().getDatabaseInstance().
                attendancePicDao().getListToSync("1");

        if (postAttendance_Picture.size() > 0) {
            try {
                for (int i = 0; i < postAttendance_Picture.size(); i++) {

                    File file = convertbyteToFile(postAttendance_Picture.get(i).getPicture());

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
                            }, getPostDataParam(postAttendance_Picture.get(i)));
                    CSApplicationHelper.application().addToRequestQueue(imageUploadReq, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void postAttendance_details_when_mark_attendance() {
        List<DailyAttendanceDetail> postAttendance = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getListToSync("1");
        if (postAttendance.size() > 0) {
            try {
                JSONArray array = new JSONArray();

                Log.e("getListToSync", "PostAttendanceWork" + postAttendance.size());
                for (int i = 0; i < postAttendance.size(); i++) {
                    array.put(new JSONObject(postAttendance.get(i).getJSON()));
                }
                new VolleyAsyncClassPost(this, array, POST_DAILY_ATTENDANCE, DAILY_ATTENDANCEDETAIL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "UnitDailyAttendanceDetail"), getTagURL(Constants.GET_ATTENDANCE_DETAIL, DAILY_ATTENDANCEDETAIL),
                    "GetUnitDailyAttendanceDetail");

        }

        List<AttendanceGuardPicModel> postAttendance_Picture = CSApplicationHelper.application().getDatabaseInstance().
                attendancePicDao().getListToSync("1");

        if (postAttendance_Picture.size() > 0) {
            try {
                new Volley_Asynclass_Get(ApiCalling_Class.this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                        CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), get_site_URL(Constants.GET_UNIT_SHIFT_MASTER, SHIFTMASTER),
                        "GET_SHIFT_DETAILS");

                for (int i = 0; i < postAttendance_Picture.size(); i++) {

                    File file = convertbyteToFile(postAttendance_Picture.get(i).getPicture());

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
                            }, getPostDataParam(postAttendance_Picture.get(i)));
                    CSApplicationHelper.application().addToRequestQueue(imageUploadReq, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void push_All_Apis() {
        try {
            CSApplicationHelper.application().getInstance_ProgresssBar().show();
            List<NotificationModel> postNotification = CSApplicationHelper.application().getDatabaseInstance().notification_dao().getListToSync("1");
            if (postNotification.size() > 0) {
                try {
                    JSONArray array = new JSONArray();
                    Log.e("getListToSync", "PostAttendanceWork" + postNotification.size());
                    for (int i = 0; i < postNotification.size(); i++) {
                        array.put(new JSONObject(postNotification.get(i).getJson()));
                    }
                    new VolleyAsyncClassPost(this, array, POST_NOTIFICATION, NOTIFICATION_TABLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            List<LeaveDetailTransaction> postLeaveTransaction = CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao()
                    .getListToSync("1");
            if (postLeaveTransaction.size() > 0) {
                try {
                    JSONArray array = new JSONArray();

                    Log.e("getListToSync", "PostLeaveTransactionWorker" + postLeaveTransaction.size());
                    for (int i = 0; i < postLeaveTransaction.size(); i++) {
                        array.put(new JSONObject(postLeaveTransaction.get(i).getJSON()));
                    }
                    new VolleyAsyncClassPost(this, array, POST_LEAVE_DETAIL, POST_LEAVE_DETAIL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            List<LeaveMaster> postLeave = CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().getListToSync("1");
            if (postLeave.size() > 0) {
                try {
                    JSONArray array = new JSONArray();

                    Log.e("getListToSync", "POST_LEAVE_MASTER" + postLeave.size());
                    for (int i = 0; i < postLeave.size(); i++) {
                        array.put(new JSONObject(postLeave.get(i).getJson()));
                    }
                    new VolleyAsyncClassPost(this, array, POST_LEAVE_MASTER, POST_LEAVE_MASTER);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            List<PeriodicTrackingInfoModel> getListToSync = CSApplicationHelper.application().getDatabaseInstance().
                    trackingInfoDAO().getListToSync("1");
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

            List<DailyAttendanceDetail> postAttendance = CSApplicationHelper.application().getDatabaseInstance().markAttendanceDao().getListToSync("1");
            if (postAttendance.size() > 0) {
                try {
                    JSONArray array = new JSONArray();

                    Log.e("getListToSync", "PostAttendanceWork" + postAttendance.size());
                    for (int i = 0; i < postAttendance.size(); i++) {
                        array.put(new JSONObject(postAttendance.get(i).getJSON()));
                    }
                    new VolleyAsyncClassPost(this, array, POST_DAILY_ATTENDANCE, DAILY_ATTENDANCEDETAIL);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                        CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "UnitDailyAttendanceDetail"), getTagURL(Constants.GET_ATTENDANCE_DETAIL, DAILY_ATTENDANCEDETAIL),
                        "GetUnitDailyAttendanceDetail");
            }

            List<AttendanceGuardPicModel> postAttendance_Picture = CSApplicationHelper.application().getDatabaseInstance().
                    attendancePicDao().getListToSync("1");

            if (postAttendance_Picture.size() > 0) {
                try {
                    for (int i = 0; i < postAttendance_Picture.size(); i++) {

                        File file = convertbyteToFile(postAttendance_Picture.get(i).getPicture());


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
                                }, getPostDataParam(postAttendance_Picture.get(i)));
                        CSApplicationHelper.application().addToRequestQueue(imageUploadReq, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(),
                    FLASH_MESSAGE_TABLE);

            new Volley_Asynclass_Get(this, NetworkUtils.flash_Message_Json_Parameter(maxModifiedDate == null ? "" : maxModifiedDate, "flashmessage")
                    , Constants.GET_FLASH_MESSAGE, FLASH_MESSAGE_TABLE);


            new Volley_Asynclass_Get(this, NetworkUtils.paramterForRoster_NEW(getLastDateModified()),
                    GET_ROSTER_URL_NEW, "GET_ROSTER_URL_NEW");

            new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()),
                    getTagURL(Constants.GET_EMPLOYEE, Constants.EMPLOYEEDETAILS_TABLE),
                    "EMPLOYEE_DETAILS");

            new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL(Constants.GET_PROFILE_DETAIL,
                    USER_DETAIL), "GET_PROFILE_DATA");

            new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "leavedetail"),
                    getTagURL_leave(Constants.LEAVE_TRANSACTION_DETAILS, GET_LEAVE_DETAIL), "GetLeaveDetail");

            new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL_leave(Constants.SERVICEINFO, Constants.GET_SERVICE_INFO_URL), "GetServiceInfo");
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), Constants.GET_SERVICE_INFO_URL, "GetServiceInfo");

            new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), CFUtil.parameter_Format_single(Constants.GET_UNIT_SHIFT_MASTER,
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL_leave(NOTIFICATION_TABLE, Constants.GET_NOTIFICATION), "GET_NOTIFICATION");


            new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), get_site_URL(Constants.GET_SITE, GETSITEDETAILS), "GetSite");


            /*new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), getTagURL(Constants.GET_EMPLOYEE_PICTURE, EMPLOYEEDETAILS_TABLE),
                    "EMPLOYEE_PICTURE");*/
//                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), tag, CSShearedPrefence.getUser());

            UserDetailModel userDetailModel = CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().getDetails(CSShearedPrefence.getUser());
            if (userDetailModel != null) {
                new Volley_Asynclass_Get(this, NetworkUtils.changeSelf_ContactnoParamter(userDetailModel.getChange_ContactNo(),
                        CSShearedPrefence.getDeviceToken()), Constants.CHANGE_CONTACT_NO_URL_TAG, "CHANGE_CONTACTNO");
            }

            new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
//                CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), CFUtil.parameter_Format_single(Constants.GET_UNIT_SHIFT_MASTER,
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), get_site_URL(Constants.GET_UNIT_SHIFT_MASTER, SHIFTMASTER), "GET_SHIFT_DETAILS");


            new Volley_Asynclass_Get(this, NetworkUtils.getEmployeeDeatils_JSON(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword()), CFUtil.parameter_Format(Constants.GET_UNIT_DUTY_POST_DETAILS,
                    "UnitCode", "", "lastModifiedDatetime", ""), "GetUnitDutyPostDetail");


            if (!CSStringUtil.isEmptyStr(CSShearedPrefence.getFcmToken())) {
                new Volley_Asynclass_Get(this, NetworkUtils.formatForFCM_TOKEN(CSShearedPrefence.getUser(),
                        CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "updatefcmtoken", CSShearedPrefence.getFcmToken()),
                        "ChangePassword", "updatefcmtoken");
            }

            new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "leavemaster"),
                    getTagURL_leave(Constants.LEAVE_MASTER, GET_LEAVE_DETAIL),
                    "leavemaster");


            new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                    CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "leavetypelist"), GET_LEAVE_DETAIL,
                    "leavetypelist");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String get_site_URL(String apiTag, String tableName) {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), tableName);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(apiTag).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(apiTag).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        return Uri.parse(url).buildUpon().appendQueryParameter("UnitCode", "").build().toString();
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
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
    public void getDataFromVolleyInterFace(String loadedString, String requestCode1) {

        if (requestCode1.equalsIgnoreCase("GET_ROSTER_URL_NEW")) {
            parseRosterDetail(loadedString);
        } else if (requestCode1.equalsIgnoreCase("GetUnitDailyAttendanceDetail")) {
            parseAttendanceData(loadedString);
        } else if (requestCode1.equalsIgnoreCase(Constants.FLASH_MESSAGE_TABLE)) {
            parseFlashMessage(loadedString);
        } else if (requestCode1.equals("EMPLOYEE_DETAILS")) {
            try {
                Log.e("EMPLOYEE_DETAILS>>", "" + loadedString);
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<EmployeDetailsModel>>() {
                    }.getType();

                    List<String> deletedEmpList = new ArrayList<>();

                    employeDetailsModelList = new Gson().fromJson(jsonObject.getJSONArray("Employee").toString(), listType);
                    for (int i = 0; i < employeDetailsModelList.size(); i++) {
                        JSONObject object = jsonObject.getJSONArray("Employee").getJSONObject(i);
                        employeDetailsModelList.get(i).setJson(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedEmpList.add(employeDetailsModelList.get(i).getID());
                        }
                    }

                    for (int j = 0; j < employeDetailsModelList.size(); j++) {
                        if (CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().getDetails(employeDetailsModelList.get(j).getID()) == null) {
                            CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().insertSingleRecord(employeDetailsModelList.get(j));
                        } else {
                            if (CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().getDetails(employeDetailsModelList.get(j).getID()).getFlag().equals("0")) {
                                CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().update(employeDetailsModelList.get(j));
                            }
                        }
                    }
                    if (deletedEmpList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().getEmployee_dao().deleteAll(deletedEmpList);
                    }
                } else {
                    Log.e("ELSEMEIN_AAYA", "");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode1.equalsIgnoreCase("GET_PROFILE_DATA")) {
            try {
                JSONObject jsonObject1 = new JSONObject(loadedString);
                if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<Main_Model_Of_profile>() {
                    }.getType();
                    List<String> deletedItemList = new ArrayList<>();

                    main_model_of_profiles = new Gson().fromJson(jsonObject1.getJSONObject("data").toString(), listType);
//                main_model_of_profiles.getProfileDetail().get(0).setFlag("0");
                    main_model_of_profiles.getProfileDetail().get(0).setSave_JSON(jsonObject1.getJSONObject("data").getJSONArray("ProfileDetail").getJSONObject(0).toString());
                    CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                            insert(main_model_of_profiles.getProfileDetail());
                    for (int i = 0; i < main_model_of_profiles.getUnitDetail().size(); i++) {
                        JSONObject object = jsonObject1.getJSONObject("data").getJSONArray("UnitDetail").getJSONObject(i);
                        main_model_of_profiles.getUnitDetail().get(i).setJSON(object.toString());
                        if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                            deletedItemList.add(object.optString("ID"));
                        }
                    }
                    for (int j = 0; j < main_model_of_profiles.getUnitDetail().size(); j++) {
                        if (CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().
                                getDetails(main_model_of_profiles.getUnitDetail().get(j).getID()) == null) {
                            CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().
                                    singleInsert(main_model_of_profiles.getUnitDetail().get(j));
                        } else {
                            if (CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().
                                    getDetails(main_model_of_profiles.getUnitDetail().get(j).getID()).getFLAG().equals("0")) {
                                CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().
                                        update(main_model_of_profiles.getUnitDetail().get(j));
                            }
                        }
                    }

                    if (deletedItemList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().unitDetailDao().deleteAll(deletedItemList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode1.toLowerCase().contains("GetLeaveDetail".toLowerCase())) {
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
                        JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(j);

                        leaveEmployeeDetailsArrayList.get(j).setJSON(object.toString());
                        if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().getDetails(leaveEmployeeDetailsArrayList.get(j).getID()) == null) {
//                            JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(j);
//                            leaveEmployeeDetailsArrayList.get(j).setFlag("0");
//                            leaveEmployeeDetailsArrayList.get(j).setJSON(object.toString());
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
        } else if (requestCode1.equalsIgnoreCase("GetServiceInfo")) {
            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<ServiceInfoDetail>>() {
                    }.getType();
                    List<String> deletedEmpList = new ArrayList<>();
                    serviceInfoDetailList = new Gson().fromJson(jsonObject.getJSONArray("ServiceInfo").toString(), listType);
                    if (serviceInfoDetailList != null) {
                        for (int i = 0; i < serviceInfoDetailList.size(); i++) {
                            JSONObject object = jsonObject.getJSONArray("ServiceInfo").getJSONObject(i);
                            serviceInfoDetailList.get(i).setJSON(object.toString());
                            if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                                deletedEmpList.add(serviceInfoDetailList.get(i).getID());
                            }
                        }
                        for (int i = 0; i < serviceInfoDetailList.size(); i++) {
                            serviceInfoDetailList.get(i).setJSON(jsonObject.getJSONArray("ServiceInfo").getJSONObject(i).toString());
                        }
                        CSApplicationHelper.application().getDatabaseInstance().serviceDAO().insert(serviceInfoDetailList);
                    }
                    if (deletedEmpList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().serviceDAO().deleteAll(deletedEmpList);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode1.equalsIgnoreCase("GET_NOTIFICATION")) {
            Log.e("notificationModelList>>", "" + loadedString);
            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<NotificationModel>>() {
                    }.getType();
                    List<String> deletedEmpList = new ArrayList<>();

                    notificationModelList = new Gson().fromJson(jsonObject.getJSONArray("data").toString(), listType);
                    if (notificationModelList != null) {

                        for (int i = 0; i < notificationModelList.size(); i++) {
                            JSONObject object = jsonObject.getJSONArray("data").getJSONObject(i);
                            notificationModelList.get(i).setJson(object.toString());
                            if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                                deletedEmpList.add(notificationModelList.get(i).getId());
                            }
                        }
                        for (int i = 0; i < notificationModelList.size(); i++) {
                            notificationModelList.get(i).setFlag("0");
//                        notificationModelList.get(i).setDutyHours(String.valueOf(DateUtils.getInstance().getDutyHours(jsonObject.getJSONArray("UnitShiftMaster").getJSONObject(i).getString("DUTY_HRS"))));
                            notificationModelList.get(i).setJson(jsonObject.getJSONArray("data").getJSONObject(i).toString());
                        }
                    }
                    for (int j = 0; j < notificationModelList.size(); j++) {
                        if (CSApplicationHelper.application().getDatabaseInstance().notification_dao().getDetail(notificationModelList.get(j).getId()) == null) {
                            CSApplicationHelper.application().getDatabaseInstance().notification_dao().insertSingle(notificationModelList.get(j));
                        } else {
                            if (CSApplicationHelper.application().getDatabaseInstance().notification_dao().getDetail(notificationModelList.get(j).getId()).getFlag().equals("0")) {
                                CSApplicationHelper.application().getDatabaseInstance().notification_dao().update(notificationModelList.get(j));
                            }
                        }
                    }
                    if (deletedEmpList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().notification_dao().deleteAll(deletedEmpList);
                    }
                    //************************** save  MASTER table in DB *****************************************************************
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + String.valueOf(e));
            }
        } else if (requestCode1.equalsIgnoreCase("GetSite")) {
            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {

                    Type listType = new TypeToken<List<GetSiteDetail>>() {
                    }.getType();
                    List<String> deletedEmpList = new ArrayList<>();

                    siteDetailList = new Gson().fromJson(jsonObject.getJSONArray("Site").toString(), listType);
                    if (siteDetailList != null) {
                        for (int i = 0; i < siteDetailList.size(); i++) {
                            siteDetailList.get(i).setJSON(jsonObject.getJSONArray("Site").getJSONObject(i).toString());
                        }
                        for (int i = 0; i < siteDetailList.size(); i++) {
                            JSONObject object = jsonObject.getJSONArray("Site").getJSONObject(i);
                            siteDetailList.get(i).setJSON(object.toString());
                            if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                                deletedEmpList.add(siteDetailList.get(i).getID());
                            }
                        }
                        if (deletedEmpList.size() > 0) {
                            CSApplicationHelper.application().getDatabaseInstance().siteDetail_DAO().deleteAll(deletedEmpList);
                        }
                        CSApplicationHelper.application().getDatabaseInstance().siteDetail_DAO().insert(siteDetailList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        /*else if (requestCode1.equalsIgnoreCase("EMPLOYEE_PICTURE")) {
            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                JSONArray jsonArray;
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    jsonArray = jsonObject.getJSONArray("EmployeePicture");
                    CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().
                            updateUserPicture(jsonArray.getJSONObject(0).getString("REGNO"), getImageBYTE(
                                    jsonArray.getJSONObject(0).getString("PICTURE")),
                                    jsonArray.getJSONObject(0).getString("UnitCode"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        else if (requestCode1.equalsIgnoreCase("CHANGE_CONTACTNO")) {
            Log.e("CHANGE_CONTACTNO>>", "" + loadedString);
            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    CSApplicationHelper.application().getDatabaseInstance().user_detail_dao().updateWithSyncServer(jsonArray.
                            getJSONObject(0).getString("RegNo"), jsonArray.
                            getJSONObject(0).getString("PendingApproval"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode1.equalsIgnoreCase("GET_SHIFT_DETAILS")) {
            Log.e("GET_SHIFT_DETAILS>>", "" + loadedString);
            CSApplicationHelper.application().getInstance_ProgresssBar().dismiss();
            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<ShiftMasterModel>>() {
                    }.getType();
                    List<String> deletedEmpList = new ArrayList<>();

                    shiftMasterModelList = new Gson().fromJson(jsonObject.getJSONArray("UnitShiftMaster").toString(), listType);
                    if (shiftMasterModelList != null) {

                        for (int i = 0; i < shiftMasterModelList.size(); i++) {
                            JSONObject object = jsonObject.getJSONArray("UnitShiftMaster").getJSONObject(i);
                            shiftMasterModelList.get(i).setJson(object.toString());
                            if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                                deletedEmpList.add(shiftMasterModelList.get(i).getId());
                            }
                        }
                        for (int i = 0; i < shiftMasterModelList.size(); i++) {
//                        shiftMasterModelList.get(i).setDutyHours(String.valueOf(DateUtils.getInstance().getDutyHours(jsonObject.getJSONArray("UnitShiftMaster").getJSONObject(i).getString("DUTY_HRS"))));
                            shiftMasterModelList.get(i).setJson(jsonObject.getJSONArray("UnitShiftMaster").getJSONObject(i).toString());
                        }
                    }
                    for (int j = 0; j < shiftMasterModelList.size(); j++) {
                        if (CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().getShiftDetail(shiftMasterModelList.get(j).getId()) == null) {
                            CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().insertsingle(shiftMasterModelList.get(j));
                        } else {
                            if (CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().getShiftDetail(shiftMasterModelList.get(j).getId()).getFlag().equals("0")) {
                                CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().update(shiftMasterModelList.get(j));
                            }
                        }
                    }
                    if (deletedEmpList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().deleteAll(deletedEmpList);
                    }
                    //************************** save  MASTER table in DB *****************************************************************
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + String.valueOf(e));
            }
        } else if (requestCode1.equalsIgnoreCase("GetUnitDutyPostDetail")) {
            Log.e("GetUnitDutyPostDetail>>", "" + loadedString);

            try {
                JSONObject jsonObject = new JSONObject(loadedString);
                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<Duty_PostDetail_Model>>() {
                    }.getType();
                    List<String> deletedEmpList = new ArrayList<>();

                    unitDutyPostDetailList = new Gson().fromJson(jsonObject.getJSONArray("UnitDutyPostDetail").toString(), listType);
                    if (unitDutyPostDetailList != null) {
                        for (int i = 0; i < unitDutyPostDetailList.size(); i++) {
                            unitDutyPostDetailList.get(i).setJson(jsonObject.getJSONArray("UnitDutyPostDetail").getJSONObject(i).toString());
                        }
                        for (int i = 0; i < unitDutyPostDetailList.size(); i++) {
                            JSONObject object = jsonObject.getJSONArray("UnitDutyPostDetail").getJSONObject(i);
                            unitDutyPostDetailList.get(i).setJson(object.toString());
                            if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                                deletedEmpList.add(unitDutyPostDetailList.get(i).getID());
                            }
                        }
                    }


                    for (int j = 0; j < unitDutyPostDetailList.size(); j++) {
                        if (CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().getDutyPostDetail(unitDutyPostDetailList.get(j).getID()) == null) {
                            CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().insertSingleRecord(unitDutyPostDetailList.get(j));
                        } else {
                            if (CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().getDutyPostDetail(unitDutyPostDetailList.get(j).getID()).getFlag().equals("0")) {
                                CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().update(unitDutyPostDetailList.get(j));
                            }
                        }
                    }
                    if (deletedEmpList.size() > 0) {
                        CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().deleteAll(deletedEmpList);
                    }
                    //************************** save  MASTER table in DB *****************************************************************
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode1.equalsIgnoreCase("updatefcmtoken")) {
            Log.e("updatefcmtoken>>", "" + loadedString);

        } else if (requestCode1.equalsIgnoreCase("leavemaster")) {
            Log.e("leavemaster>>>", "GetLeaveDetail ....   " + loadedString);

            try {
                JSONObject jsonObject1 = new JSONObject(loadedString);
                if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<LeaveMaster>>() {
                    }.getType();
//                List<String> deletedItemList = new ArrayList<>();
                    leaveEmployeeMastersList = new Gson().fromJson(jsonObject1.getJSONArray("data").toString(), listType);
//                for (int i = 0; i < leaveEmployeeMastersList.size(); i++) {
//                    JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(i);
//                    leaveEmployeeMastersList.get(i).setJson(object.toString());
//                    leaveEmployeeMastersList.get(i).setFlag("0");
//                    if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
//                        deletedItemList.add(object.optString("ID"));
//                    }
//                }
                    for (int j = 0; j < leaveEmployeeMastersList.size(); j++) {
                        JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(j);
//                            leaveEmployeeMastersList.get(j).setFlag("0");
                        leaveEmployeeMastersList.get(j).setJson(object.toString());
                        if (CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().getDetails(leaveEmployeeMastersList.get(j).getID()) == null) {

                            CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().insertLeaveEmployee(leaveEmployeeMastersList.get(j));
                        } else {
                            String flag = CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().getDetails(leaveEmployeeMastersList.get(j).getID()).getFlag();
                            if (flag != null && flag.equals("0")) {
                                CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().updateLeaveEmployee(leaveEmployeeMastersList.get(j));
                            }
                        }
                    }
//                if (deletedItemList.size() > 0) {
//                    CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().deleteAll(deletedItemList);
//                }
                    //************************** save  MASTER table in DB *****************************************************************
                } else {
                    Log.e("ELSEMEIN_AAYA", "");
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + String.valueOf(e));
            }
        } else if (requestCode1.equalsIgnoreCase("leavetypelist")) {

            Log.e("leavetypelist>>>", "GetLeaveDetail ....   " + loadedString);

            try {
                JSONObject jsonObject1 = new JSONObject(loadedString);
                if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                    Type listType = new TypeToken<List<LeaveTypeListModel>>() {
                    }.getType();
//                List<String> deletedItemList = new ArrayList<>();
                    leaveTypeListModels = new Gson().fromJson(jsonObject1.getJSONArray("data").toString(), listType);
//                for (int i = 0; i < leaveEmployeeMastersList.size(); i++) {
//                    JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(i);
//                    leaveEmployeeMastersList.get(i).setJson(object.toString());
//                    leaveEmployeeMastersList.get(i).setFlag("0");
//                    if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
//                        deletedItemList.add(object.optString("ID"));
//                    }
//                }
                    CSApplicationHelper.application().getDatabaseInstance().getLeaveTypeList_dao().insert(leaveTypeListModels);

                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + String.valueOf(e));
            }
        }


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
                new Volley_Asynclass_Get(this, NetworkUtils.commonParameter(CSShearedPrefence.getUser(),
                        CSShearedPrefence.getDeviceToken(), CSShearedPrefence.getPassword(), "UnitDailyAttendanceDetail"),
                        getTagURL(Constants.GET_ATTENDANCE_DETAIL, DAILY_ATTENDANCEDETAIL), "GetUnitDailyAttendanceDetail");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (tableName.equalsIgnoreCase(Constants.POST_TRACKING_URL)) {
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
        } else if (tableName.equalsIgnoreCase(Constants.POST_LEAVE_MASTER)) {
            try {
                Log.e("POST_LEAVE_MASTER>", "POST_LEAVE_MASTER.....   " + response);
                if (response.optString("status").equals("true")) {
                    // JSONObject jsonObject = response.getJSONObject("data");
                    JSONArray array = response.getJSONArray("data");
                    //
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject idObject = array.getJSONObject(i);
                        String ID = idObject.optString("ID");
                        CSApplicationHelper.application().getDatabaseInstance().leave_Master_Dao().updateFlag(ID, "0");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (tableName.equalsIgnoreCase(Constants.POST_LEAVE_DETAIL)) {
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
        } else if (tableName.equalsIgnoreCase(Constants.NOTIFICATION_TABLE)) {
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

    private void parseAttendanceData(String loadedString) {
        try {
            Log.e("parseAttendanceData>", "parseAttendanceData.....   " + loadedString);

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
    }

    private String getTagURL(String apiTag, String tableName) {
        String url = "";
        String maxModifiedDate = GetDataFromServer.getInstance().getMaxModifiedDate(CSApplicationHelper.application().getDatabaseInstance(), tableName);
        if (CSStringUtil.isEmptyStr(maxModifiedDate)) {
            maxModifiedDate = "";
            url = Uri.parse(apiTag).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();

        } else {
            url = Uri.parse(apiTag).buildUpon().appendQueryParameter("lastModifiedDatetime", maxModifiedDate).build().toString();
        }
        Log.e("getUser", "maxModifiedDate>>>" + CSShearedPrefence.getUser());
//        return Uri.parse(url).buildUpon().appendQueryParameter("REGNO", CSShearedPrefence.getUser()).build().toString();
        return Uri.parse(url).buildUpon().appendQueryParameter("REGNO", CSShearedPrefence.getUser()).build().toString();
// return CFUtil.parameter_Format(Constants.GET_ATTENDANCE_DETAIL, "lastModifiedDatetime", maxModifiedDate, "REGNO", CSShearedPrefence.getUser());
    }

    public static String getTagURL_leave(String tableName, String urlParam) {
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

    private void parseFlashMessage(String loadedString) {
        try {
            Log.e("FLASH_MESSAGE", "" + loadedString);

            JSONObject jsonObject1 = new JSONObject(loadedString);
            if (jsonObject1.getString("status").toLowerCase().equals("true")) {
                Type listType = new TypeToken<List<Flash_Message_Model>>() {
                }.getType();
                List<String> deletedItemList = new ArrayList<>();

                flash_message_modelArrayList = new Gson().fromJson(jsonObject1.getJSONArray("data").toString(), listType);
                for (int i = 0; i < flash_message_modelArrayList.size(); i++) {
                    JSONObject object = jsonObject1.getJSONArray("data").getJSONObject(i);
                    flash_message_modelArrayList.get(i).setJSON(object.toString());
                    if (CSUtil.isDeleted(object.optString(PARAM_KEY_DELETED))) {
                        deletedItemList.add(object.optString("ID"));
                    }
                }
                for (int i = 0; i < flash_message_modelArrayList.size(); i++) {
                    flash_message_modelArrayList.get(i).setJSON(jsonObject1.getJSONArray("data").getJSONObject(i).toString());
                }
                for (int j = 0; j < flash_message_modelArrayList.size(); j++) {
                    if (CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().getDetails(flash_message_modelArrayList.get(j).getID())
                            == null) {
                        CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().singleInsert(flash_message_modelArrayList.get(j));
                    } else {
                        if (CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().getDetails(flash_message_modelArrayList.get(j).getID()).getFLAG().equals("0")) {
                            CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().update(flash_message_modelArrayList.get(j));
                        }
                    }
                }

                if (deletedItemList.size() > 0) {
                    CSApplicationHelper.application().getDatabaseInstance().flashMessageDao().deleteAll(deletedItemList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
