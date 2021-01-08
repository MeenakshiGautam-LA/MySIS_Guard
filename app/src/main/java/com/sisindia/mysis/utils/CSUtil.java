package com.sisindia.mysis.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Telephony;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.sisindia.mysis.application.CSApplicationHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class CSUtil {

    public static String getFileContent(String fileName) {
        return streamToString(getFileFromAsset(fileName));
    }

    public static boolean isDeleted(String deleted) {
        ArrayList<String> deletedSet = new ArrayList<>();
        deletedSet.add("1");
        deletedSet.add("yes");
        deletedSet.add("true");
        deletedSet.add("delete");
        deletedSet.add("deleted");
        return deletedSet.contains(deleted.toLowerCase());
    }

    public static InputStream getFileFromAsset(String fileName) {
        try {
            if (isNonEmptyStr(fileName)) {
                return CSApplicationHelper.application().getActivity().getAssets().open(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isNonEmptyStr(String fileName) {
        return !isEmptyStr(fileName);
    }

    public static boolean isEmptyStr(String _v) {
        return _v == null || _v.trim().length() == 0 || _v.equalsIgnoreCase("null");
    }

    public static String streamToString(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            StringBuilder fileContent = new StringBuilder();
            String str;
            while ((str = bufferReader.readLine()) != null) {
                fileContent.append(str.trim());
            }
            return fileContent.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encodeBase64(String data) {
        byte[] bytes = null;
        try {
            bytes = data.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] encodedBytes = Base64.encode(bytes, Base64.DEFAULT);
        return new String(encodedBytes);
    }

    public static String getDefaultSmsAppPackageName(@NonNull Context context) {
        String defaultSmsPackageName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context);
            return defaultSmsPackageName;
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_DEFAULT).setType("vnd.android-dir/mms-sms");
            final List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
            if (resolveInfos != null && !resolveInfos.isEmpty())
                return resolveInfos.get(0).activityInfo.packageName;

        }
        return null;
    }
}

