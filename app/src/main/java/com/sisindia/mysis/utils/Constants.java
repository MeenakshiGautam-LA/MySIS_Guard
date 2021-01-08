package com.sisindia.mysis.utils;

import android.os.Environment;

public class Constants
{
    //LOCAl SERVER
    //public static final String BASE_URL = "http://sfcc.sisersys.com:92/Rest.svc/";
    //BETA SERVER


    public static final String BASE_URL = "https://mysis.sisersys.com:8444/Rest.svc/";
    public final static long LOCATION_FETCH_IN_MINUTES = 10; // In Minutes
    public final static long TRACKING_10_MINUTES = 600000; // In millisecond
    public static final String PARAM_KEY_Deleted = "Deleted";
    public static final int APP_DATABASE_VERSION = 5;
    public static final String PROFILE_TABLE = "PROFILE_TABLE";
    public static final String ATTENANCE_PIC_TABLE = "ATTENANCE_PIC_TABLE";
    public static final String GET_LEAVE_TYPE_LIST = "GET_LEAVE_TYPE_LIST";
    public final static String EMPLOYEEDETAILS_TABLE = "EMPLOYEE_DETAIL_TABLE";
    public static final String DATABASE = "GUARD.db";
    public static final String UPLOAD_IMAGE_TAG = "UploadAttendanceImage";
    public final static String SHIFTMASTER = "SHIFT_MASTER";
    public final static String USER_DETAIL = "USER_DETAIL";
    public static final String GET_UNIT_DUTY_POST_DETAILS = "GetUnitDutyPostDetail";
    public static final String GET_UNIT_SHIFT_MASTER = "GetUnitShiftMaster";
    public static final String Roster_TABLE = "ROSTER_DETAIL";
    public static final String Attendance_Master_Table = "ATTENDANCE_MASTER";
    public static final String DUTY_POST_DETAIL = "DUTY_POST_DETAIL";
    public static final String LEAVE_TRANSACTION_DETAILS = "LEAVE_TRANSACTION_DETAILS";
    public final static String SERVICEINFO = "SERVICE_INFO";
    public final static String LEAVE_MASTER = "LEAVE_MASTER";
    public final static String DAILY_ATTENDANCEDETAIL = "DAILY_ATTENDANCE";
    public static final String EMPTY_UUID = "00000000-0000-0000-0000-000000000000";
    public static final String GET_ATTENDANCE_DETAIL = "GetUnitDailyAttendanceDetail";
    public static final String GET_SITE = "GetSite";
    public static final String PARAM_KEY_DELETED = "DELETED";
    public static final String UNIT_DETAIL = "UNIT_DETAIL";

    public final static String GETSITEDETAILS = "GET_SITE_DETAIL";
    public final static String TRACKING_INFO = "TRACKING_INFO";
    public static final String BASE_DIRECTORY = Environment.getExternalStorageDirectory().toString()+"/GUARD";
    /* GET */
    public static final String GET_UPDATED_VERSION = BASE_URL + "GetAppVersion";
    public static final String GET_CHECK_UPDATE = "GetAppVersion";
    public final static String GETCUSTOMERDETAILS = "GET_CUSTOMER_DETAILS";
    public final static String CHANGE_CONTACT_NO_URL_TAG = "UserDetails";
    public static final String GET_REASON_DETAILS = "GetReasonDetails";
    public static final String GET_FLASH_MESSAGE = "GetUserFlashMessage";
    public final static String GET_REASON_DETAIL = "GET_REASON_DETAIL";
    public static final String OTHER_DUTY_WITH_QR_CODE_URL ="GetUnitDetailsQrIdWiseNEW" ;
    public final static String SET_PIN_TAG = "changePassword";
    public static final String LOGINURL = "Login";
    public static final String GET_EMPLOYEE_PICTURE = "GetEmployeePicture";
    public static final String GET_LEAVE_DETAIL = "GetLeaveDetail";
    public static final String GET_SERVICE_INFO_URL = "GetServiceInfo";
    public static final String GET_EMPLOYEE = "GetEmployee";
    public static final String SIGN_URL = "CreateUser";
    public static final String OTHER_EMPLOYEE_DETAIL_TAG = "GetRosterDetailEmployeeWise";
    public static final String GET_ROSTER_URL = "GetEmployeeRosterDetail";
    public static final String GET_ROSTER_URL_NEW = "GetEmployeeRosterDetailNEW";
    public static final String GET_NOTIFICATION = "GetNotificationDetail";
    public static final String GET_PROFILE_DETAIL = "GetUserProfile";

    public static final int SIGNIN = 01;
    public static final int LOGIN = 02;
    /*POST*/
    public static final String PostUnitShiftMaster = "PostUnitShiftMaster";
    public static final String POST_TRACKING_URL = "PostTrackingDetail";
    public static final String POST_DAILY_ATTENDANCE = "PostEmployeeAttendance";
    public static final String POST_NOTIFICATION = "PostNotificationList";
    public static final String POST_LEAVE_MASTER = "PostLeaveMaster";
    public static final String POST_LEAVE_DETAIL = "PostLeaveDetail";
    public static final String POST_DUTY_POST_DETAIL_URL = "PostUnitDutyPostDetails";
    public static  final String SYNC_ACCOUNT_30_MINUTE ="MY SIS";
    public static  final String APP_TYPE ="GUARD";

    public static final String NOTIFICATION_TABLE ="NOTIFICATION_TABLE" ;
    public static final String PACKAGE_NAME ="com.sisindia.mysis" ;
    public static final String FLASH_MESSAGE_TABLE = "FLASH_MESSAGE";
    public static final String APP_UPDATE_TABLE = "APP_UPDATE_TABLE";

}
