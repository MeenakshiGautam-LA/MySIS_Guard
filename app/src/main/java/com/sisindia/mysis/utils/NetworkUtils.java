package com.sisindia.mysis.utils;

import com.sisindia.mysis.BuildConfig;

import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {
    public static Map loginparamater(String register_number,
                                     String deviceId, String password, String result) {

        Map<String, String> jsonObject = null;
        try {

            jsonObject = new HashMap();
            jsonObject.put("USERNAME", CSShearedPrefence.getUser());
            jsonObject.put("PASSWORD", password);
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("DEVICEID", deviceId);
            jsonObject.put("AuthType", "DEVICE");
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
//            jsonObject.put("RESULT",result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map formatForFCM_TOKEN(String register_number, String deviceId, String password, String action, String fcmToken) {

        Map<String, String> jsonObject = null;
        try {
            jsonObject = new HashMap();
            jsonObject.put("UserName", register_number);
            jsonObject.put("DeviceID", deviceId);
            jsonObject.put("Password", password);
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("AuthType", "DEVICE");
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
            jsonObject.put("ACTION", action);
            jsonObject.put("FCMTOKEN", fcmToken);

        } catch (Exception e) {

        }
        return jsonObject;
    }

    public static Map forOtherDuty_WithQR_Code(String searchRegNo, String qrCode) {
        Map<String, String> jsonObject = null;
        try {
            jsonObject = new HashMap<>();
            jsonObject.put("USERNAME", CSShearedPrefence.getUser());
            jsonObject.put("PASSWORD", CSShearedPrefence.getPassword());
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("QrId", qrCode);
            jsonObject.put("AuthType", "");
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("REGNO", searchRegNo);
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map getEmployeeDeatils_JSON(String register_number, String deviceId, String password) {

        Map<String, String> jsonObject = null;
        try {
            jsonObject = new HashMap();
            jsonObject.put("UserName", register_number);
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("Password", password);
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("AuthType", "DEVICE");
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);

        } catch (Exception e) {

        }
        return jsonObject;
    }

    public static Map changeSelf_ContactnoParamter(String changeContactNO, String deviceId) {

        Map<String, String> jsonObject = null;
        try {
            jsonObject = new HashMap();
            jsonObject.put("UserName", CSShearedPrefence.getUser());
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("Password", CSShearedPrefence.getPassword());
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("AuthType", "DEVICE");
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
            jsonObject.put("REGNO", CSShearedPrefence.getUser());
            jsonObject.put("PHONE", changeContactNO);
            jsonObject.put("ACTION", "changemobilerequest");

        } catch (Exception e) {

        }
        return jsonObject;
    }

    public static Map create_MPIN(String mpin, String action) {

        Map<String, String> jsonObject = null;
        try {

            jsonObject = new HashMap();
            jsonObject.put("USERNAME", CSShearedPrefence.getUser());
            jsonObject.put("ACTION", action);
            jsonObject.put("pin", mpin);
            jsonObject.put("FCMTOKEN", CSShearedPrefence.getFcmToken());
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map commonParameter(String register_number, String deviceId, String password, String action) {

        Map<String, String> jsonObject = null;
        try {
            jsonObject = new HashMap();
            jsonObject.put("UserName", register_number);
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("Password", password);
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("AuthType", "DEVICE");
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
            jsonObject.put("ACTION", action);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map commonParameterFor_SignIN_By_MOB_REG(String keyFor_Mob_REG, String mobileNO, String deviceId, String action) {

        Map<String, String> jsonObject = null;
        try {
            jsonObject = new HashMap();
            jsonObject.put(keyFor_Mob_REG, mobileNO);
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("AUTHTYPE", "DEVICE");
            jsonObject.put("ACTION", action);
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map paramterForRoster_NEW(String lastDateModified) {

        Map<String, String> jsonObject = null;
        try {
            jsonObject = new HashMap();
            jsonObject.put("UserName", CSShearedPrefence.getUser());
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("Password", CSShearedPrefence.getPassword());
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("AuthType", "DEVICE");
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
            jsonObject.put("RegNo", CSShearedPrefence.getUser());
            jsonObject.put("DATE_MODIFIED", lastDateModified);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map update_User(String actionname, String regNO, String password, String pin, String deviceId) {

        Map<String, String> jsonObject = null;
        try {

            jsonObject = new HashMap();
            jsonObject.put("ACTION", actionname);
            jsonObject.put("UserName", regNO);
            jsonObject.put("PASSWORD", password);
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("PIN", pin);
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map otherEmployeeHeaderParameterByMobileNo(String rosterDate, String mobileNo, String dutyStatus) {
        Map<String, String> jsonObject = null;

        try {

            jsonObject = new HashMap();
            jsonObject.put("USERNAME", CSShearedPrefence.getUser());
            jsonObject.put("PASSWORD", CSShearedPrefence.getPassword());
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("RosterDate", rosterDate);
            jsonObject.put("Mobile", mobileNo);
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("DUTYSTATUS", dutyStatus);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map otherEmployeeHeaderParameterByRegNo(String rosterDate, String regNo, String dutyStatus) {
        Map<String, String> jsonObject = null;

        try {

            jsonObject = new HashMap();
            jsonObject.put("USERNAME", CSShearedPrefence.getUser());
            jsonObject.put("PASSWORD", CSShearedPrefence.getPassword());
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("RosterDate", rosterDate);
            jsonObject.put("Regno", regNo);
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("DUTYSTATUS", dutyStatus);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Map flash_Message_Json_Parameter(String lastModifiedDatetime,String action) {

        Map<String, String> jsonObject = null;
        try {
            jsonObject = new HashMap();
            jsonObject.put("UserName", CSShearedPrefence.getUser());
            jsonObject.put("DeviceID", CSShearedPrefence.getDeviceToken());
            jsonObject.put("USERNAME", CSShearedPrefence.getUser());
            jsonObject.put("PASSWORD", CSShearedPrefence.getPassword());
            jsonObject.put("MPIN", CSShearedPrefence.getMpin());
            jsonObject.put("AuthType", "DEVICE");
            jsonObject.put("AppType", Constants.APP_TYPE);
            jsonObject.put("VERSION", BuildConfig.VERSION_NAME);
            jsonObject.put("ACTION", action);
            jsonObject.put("lastModifiedDatetime", lastModifiedDatetime);
        } catch (Exception e) {

        }
        return jsonObject;
    }
}
