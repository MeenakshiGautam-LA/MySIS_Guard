package com.sisindia.mysis.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;

public class CSShearedPrefence {

    private static SharedPreferences.Editor dataPrefsEditor;
    private static SharedPreferences dataPreferences;


    private static final String SESSION_ID = "SESSION_ID";
    private static final String VERSION = "VERSION";
    private static final String JUGAAR = "JUGAAR";
    private static final String FCM_TOKEN = "FCM_TOKEN";
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String DEVICE_TOKEN = "DEVICE_TOKEN";
    private static final String ATTENDANCE_SYNC_TIME = "ATTENDANCE_SYNC_TIME";
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String USER = "USER";
    private static final String USER_ID = "USER_ID";
    private static final String OTP = "OTP";
    private static final String COUNTER_TIME = "COUNTER_TIME";
    private static final String READTIME = "OTP_TIME";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_INSERT_TODAY_SHIFT_IN_CLOSER = "insert_in_shift_closer";
    private static final String PASSWORD = "PASSOWRD";
    private static final String UNITCODE = "UNITCODE";
    private static final String UNITID = "UNITID";
    private static final String LAT_AND_LONGITUDE = "LAT_AND_LONGITUDE";
    private static final String UNIT_NAME = "UNIT_NAME";
    private static final String TEMPORARY = "TEMPORARY";
    private static final String ExpiryDate = "EXPIRY_DATE";
    public static final String LOGGED_IN_USER_NAME = "LOGGED_IN_USER_NAME";
    public static final String SELECTED_DATE = "SELECTED_DATE";
    public static final String EXPORT_DIRECTORY = "EXPORTDIRECTORY";
    public static final String IMPORT_DIRECTORY = "IMPORTDIRECTORY";
    public static final String MPIN = "MPIN";

    public static void setExportDirectory(String dir) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(EXPORT_DIRECTORY, dir);
        dataPrefsEditor.apply();
    }

    public static void setImportDirectory(String dir) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(IMPORT_DIRECTORY, dir);
        dataPrefsEditor.apply();
    }

    public static String getExportDirectory() {
        return getInstance().getString(EXPORT_DIRECTORY, "");
    }


    public static void setVersionCode(int dir) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putInt(VERSION, dir);
        dataPrefsEditor.apply();
    }

    public static int getVersion() {
        return getInstance().getInt(VERSION, -1);
    }
    public static void setFcmToken(String dir) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(FCM_TOKEN, dir);
        dataPrefsEditor.apply();
    }

    public static String getFcmToken() {
        return getInstance().getString(FCM_TOKEN, "");
    }

    public static String getImportDirectory() {
        return getInstance().getString(IMPORT_DIRECTORY, "");
    }

    public static void setAlreadyLaunch() {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, true);
        dataPrefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, true);
        dataPrefsEditor.apply();
    }

    public static boolean isAlreadyLaunch()
    {
        return getInstance().getBoolean(IS_FIRST_TIME_LAUNCH, false);
    }

    public static void setJugaaar(String value) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(JUGAAR, value);
        dataPrefsEditor.apply();
    }

    public static String isgetJugaar() {
        return getInstance().getString(JUGAAR, "");
    }


    public static void setShiftInsertedForToday() {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putBoolean(IS_INSERT_TODAY_SHIFT_IN_CLOSER, true);
        dataPrefsEditor.apply();
    }

    public static boolean isShiftInsertedForToday() {
        return getInstance().getBoolean(IS_INSERT_TODAY_SHIFT_IN_CLOSER, false);
    }

    public static SharedPreferences getInstance() {
        if (dataPreferences == null) {
            dataPreferences = CSApplicationHelper.application().getSharedPreferences("dataPrefs", Context.MODE_PRIVATE);
        }
        return dataPreferences;
    }

    public static void setShutDownAlreadyShow(boolean data) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putBoolean("IS_SHUT_MESSAGE", data);
        dataPrefsEditor.apply();
//        setAlreadyLaunch();
    }

    public static boolean isShutDownAlreadyShow() {
        return getInstance().getBoolean("IS_SHUT_MESSAGE", false);
    }


    public static void setIsLoggedIn(boolean data) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putBoolean(IS_LOGIN, data);
//        boolean temp = getInstance().getBoolean("IS_SHUT_MESSAGE", false);
//        int tempTheme = getInstance().getInt("theme", R.style.AppThemeBlue);
        if (!data) {
            dataPrefsEditor.clear();
        }
        dataPrefsEditor.apply();
//        setAlreadyLaunch();
//        setShutDownAlreadyShow(temp);
//        setThemeId(tempTheme);
    }

    public static boolean isLoggedIn() {
        return getInstance().getBoolean(IS_LOGIN, false);
    }


    public static void setPassword(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(PASSWORD, user);
        dataPrefsEditor.apply();
    }

    public static void setMpin(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(MPIN, user);
        dataPrefsEditor.apply();
    }

    public static void setUser_ID(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(USER_ID, user);
        dataPrefsEditor.apply();
    }

    public static String getUser_ID() {
        return getInstance().getString(USER_ID, "");
    }

    public static String getMpin() {
        return getInstance().getString(MPIN, "");
    }

    public static void setGeoLocation(String value) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(LAT_AND_LONGITUDE, value);
        dataPrefsEditor.apply();
    }

    public static String getLatAndLongitude() {
        return getInstance().getString(LAT_AND_LONGITUDE, "");
    }

    public static void setUser(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(USER, user);
        dataPrefsEditor.apply();
    }

    public static String getUser() {
        return getInstance().getString(USER, "");
    }

    public static void setExpiredDate(String date) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(ExpiryDate, date);
        dataPrefsEditor.apply();
    }

    public static String getExpiryDate() {
        return getInstance().getString(ExpiryDate, "");
    }

    public static void setTemporary(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(TEMPORARY, user);
        dataPrefsEditor.apply();
    }

    public static String getTemporary() {
        return getInstance().getString(TEMPORARY, "");
    }

    public static String getPassword() {
        return getInstance().getString(PASSWORD, "");
    }


    public static void setUnitcode(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(UNITCODE, user);
        dataPrefsEditor.apply();
    }

    public static String getUnitcode() {
        return getInstance().getString(UNITCODE, "");
    }

    public static void setUnitID(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(UNITID, user);
        dataPrefsEditor.apply();
    }

    public static String getUnitName() {
        return getInstance().getString(UNIT_NAME, "");
    }

    public static void setunitName(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(UNIT_NAME, user);
        dataPrefsEditor.apply();
    }

    public static String getUnitID() {
        return getInstance().getString(UNITID, "");
    }

    public static void setSessionId(String userId) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(SESSION_ID, userId);
        dataPrefsEditor.apply();
    }

    public static String getSessionId() {
        return getInstance().getString(SESSION_ID, "");
    }

    public static void saveDeviceToken(String userId) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(DEVICE_TOKEN, userId);
        dataPrefsEditor.apply();
    }

    public static void saveOTP(String otp) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(OTP, otp);
        dataPrefsEditor.apply();
    }

    public static void counter_Time_OTP(String otp) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(COUNTER_TIME, otp);
        dataPrefsEditor.apply();
    }

    public static String getOTP() {

        return getInstance().getString(OTP, "");
    }

    public static String getCounterTime() {

        return getInstance().getString(COUNTER_TIME, "");
    }

    public static String getDeviceToken() {
        return getInstance().getString(DEVICE_TOKEN, "");
    }

    public static int getThemeId() {
        return getInstance().getInt("theme", R.style.DigitalAppTheme);
    }

    public static void setThemeId(int userId) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putInt("theme", userId);
        dataPrefsEditor.apply();
    }

    public static boolean isNightModeEnabled() {
        return getInstance().getBoolean(NIGHT_MODE, false);
    }

    public static void setNightModeEnabled(boolean nightModeEnabled) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putBoolean(NIGHT_MODE, nightModeEnabled);
        dataPrefsEditor.apply();
    }

    public static String getOTP_READ_TIME() {
        return getInstance().getString(READTIME, "");
    }

    public static void setNightId(int userId) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putInt("night", userId);
        dataPrefsEditor.apply();
    }

    public static int getWhatsNewCount() {
        return getInstance().getInt("whats_new", 0);
    }

    public static void setWhatsNewCount(int userId) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putInt("whats_new", userId);
        dataPrefsEditor.apply();
    }


    public static void setLoggedInUserName(String user) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(LOGGED_IN_USER_NAME, user);
        dataPrefsEditor.apply();
    }

    public static String getLoggedInUserName() {
        return getInstance().getString(LOGGED_IN_USER_NAME, "");
    }

    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        return getInstance().getBoolean(key, defaultValue);
    }

    public static boolean setBooleanPreference(Context context, String key, boolean value) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putBoolean(key, value);
        return dataPrefsEditor.commit();
    }

    public static void setAttendanceSyncTime(String saveFormatTime) {
        dataPrefsEditor = getInstance().edit();
        dataPrefsEditor.putString(ATTENDANCE_SYNC_TIME, saveFormatTime);
        dataPrefsEditor.apply();
    }






}

