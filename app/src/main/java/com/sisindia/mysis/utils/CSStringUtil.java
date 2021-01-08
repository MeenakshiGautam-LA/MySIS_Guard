package com.sisindia.mysis.utils;

import android.graphics.drawable.Drawable;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sisindia.mysis.application.CSApplicationHelper;


public class CSStringUtil {


    public static Drawable getDrawableForID(int id) {
        return CSDrawable.getDrawable(id);
    }

    public static Drawable getDrawableForName(String name) {
        return getDrawableForID(getIdForName(name,"drawable"));
    }

    public static int getIdForName(String name,String resouce) {
        return CSApplicationHelper.application().getResourceId(name, "string");
    }

    public static String getStringForID(int id) {
        return CSApplicationHelper.application().getResources().getString(id);
    }

    public static String getStringForName(String name) {
        return getStringForID(getIdForName(name,"string"));
    }


    public static String getString(int id){
        return CSApplicationHelper.application().getResources().getString(id);
    }


    // check view is not null
    public static boolean isNonEmpty(View view){
        return !isEmptyView(view);
    }

    public static boolean isEqual(String viewStr1,String viewStr2) {
        return (viewStr1).equals(viewStr2);
    }

    public static boolean isEmptyStr(String _v) {
        return _v == null || _v.trim().length() == 0 || _v.equalsIgnoreCase("null");
    }
    public static boolean isNonEmptyStr(String _v) {
        return !isEmptyStr(_v);
    }

    public static boolean isEmptyView(View view) {
        return isEmptyStr(getTextFromView(view));
    }

    //get text form view
    public static String getTextFromView(View view) {
        return (view != null) ? (( view instanceof TextView) ? ((TextView) view).getText().toString().trim() : "" ) :(( view instanceof EditText) ? ((EditText) view).getText().toString().trim() : "" )  ;
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isNonValidEmail(CharSequence target) {
        return !isValidEmail(target);
    }

    public static boolean isNonValidPass(CharSequence target) {
        return target.length()<8&&target.length()>32;
    }

    public static boolean isNonAlphaNumeric(String target) {
        return !target.matches("[A-Za-z0-9]+");
    }

    public static boolean isValidPhone(CharSequence target) {
        return target != null && Patterns.PHONE.matcher(target).matches();
    }

    public  static boolean isNonValidPhone(CharSequence target) {
        return !isValidPhone(target);
    }

    public static boolean isNonEmptyView(EditText _v, int valueId){
        if(isEmptyView(_v)) {
            CSAppUtil.showError(_v,valueId);
            return false;
        }
        return true;
    }

    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "Jun";

            case 7:
                return "Jul";

            case 8:
                return "Aug";

            case 9:
                return "Sep";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";
        }

        return "";
    }
}
