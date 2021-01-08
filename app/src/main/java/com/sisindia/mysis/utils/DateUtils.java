package com.sisindia.mysis.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static String TAG = DateUtils.class.getSimpleName();

    private static DateUtils instance = null;
    private static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    private static String ddMMMyyyy = "dd-MMM-yyyy";
    public final static String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS"; //2017-01-03 14:40:38.11
    public final static String yyyy_MM_dd = "yyyy-MM-dd"; //2017-01-03
    public final static String HH_mm_SS = "HH:mm:ss"; //2017-01-03 14:40:38.11
    public final static String HH_mm = "HH:mm"; //2017-01-03 14:40:38.11
    public final static String DD_MMM = "dd MMM "; //14 Jan
    public final static String d_dd_MMM_yyyy = "d | dd MM yy"; //14 January 19
    public final static String WEEK_WORD = "EEEE"; // THURSDAY


    private final static SimpleDateFormat saveFormatter = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
    private final static SimpleDateFormat saveDateFormat = new SimpleDateFormat(yyyy_MM_dd, Locale.getDefault());
    private final static SimpleDateFormat shortFormat = new SimpleDateFormat(ddMMMyyyy, Locale.getDefault());
    private final static SimpleDateFormat inFormatter = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss_SSS, Locale.getDefault());
    private final static SimpleDateFormat onlyTimeInFormatter = new SimpleDateFormat(HH_mm_SS, Locale.getDefault());
    private final static SimpleDateFormat onlyTimeOutFormatter = new SimpleDateFormat(HH_mm, Locale.getDefault());
    private final static SimpleDateFormat date_MonthFormatatter = new SimpleDateFormat(DD_MMM, Locale.getDefault());
    private final static SimpleDateFormat week_WordFormat = new SimpleDateFormat(WEEK_WORD, Locale.getDefault());

    private DateUtils() {
    }

    public static DateUtils getInstance() {
        if (instance == null)
            instance = new DateUtils();
        return instance;
    }

    public SimpleDateFormat getWeek_WordFormat() {
        return week_WordFormat;
    }

    public String getSaveFormatter(Date date) {
        return saveFormatter.format(date);
    }

    public SimpleDateFormat getDate_TimeFormat() {
        return saveFormatter;
    }

    public SimpleDateFormat getDateFormat() {
        return saveDateFormat;
    }

    public SimpleDateFormat getDate_And_MonthWordFormat() {
        return date_MonthFormatatter;
    }

    public String getUserReadableFromSaveFormat(String dt) {
        try {
            return shortFormat.format(saveFormatter.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Invalid Date";
    }

    public String getDateByMonthNameFormat(String dt) {
        try {
            return shortFormat.format(saveDateFormat.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Invalid Date";
    }

    public Date dateFromSaveFormat(String saveString) {
        Date date = null;
        try {
            date = saveFormatter.parse(saveString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getInFormatterParse(String dateString) {
        Date date = null;
        try {
            date = inFormatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String shiftDateDisplay(Date date) {
        return saveDateFormat.format(date);
    }

    public String returnDateAnd_MonthWordOnly(Date date) {
        return date_MonthFormatatter.format(date);
    }

    public Date dateFromShiftDate(String dt) {
        Date date = new Date();
        try {
            date = saveDateFormat.parse(dt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }


    public String timeLaps(String fromDate) {
        String msg = "";
        int years = 0, months = 0, days = 0, hours = 0, minutes = 0;

        Calendar currentCal = Calendar.getInstance();
        Calendar fromCal = Calendar.getInstance();
        Date fromDt = dateFromSaveFormat(fromDate);
        if (fromDt == null)
            fromDt = new Date();
        fromCal.setTime(fromDt);

        years = currentCal.get(Calendar.YEAR) - fromCal.get(Calendar.YEAR);
        months = currentCal.get(Calendar.MONTH) - fromCal.get(Calendar.MONTH);
        days = currentCal.get(Calendar.DAY_OF_YEAR) - fromCal.get(Calendar.DAY_OF_YEAR);
        hours = currentCal.get(Calendar.HOUR_OF_DAY) - fromCal.get(Calendar.HOUR_OF_DAY);
        minutes = currentCal.get(Calendar.MINUTE) - fromCal.get(Calendar.MINUTE);
        Log.d("Dhiraj", currentCal + "" + fromCal);

        if (years > 0) {
            if (years == 1)
                msg = "1 year";
            else
                msg = years + " years";
        } else if (months > 0) {
            if (months == 1)
                msg = "1 month";
            else
                msg = months + " months";
        } else if (days > 0) {
            if (days == 1)
                msg = "1 day";
            else
                msg = days + " days";
        } else if (hours > 0) {
            if (hours == 1)
                msg = "1 hour";
            else
                msg = hours + " hours";
        } else if (minutes > 0) {
            if (minutes == 1)
                msg = "1 minute";
            else
                msg = minutes + " minutes";
        } else {
            msg = "---";
        }

        return msg + " ago";
    }

    public static Date getContcat_Date_Time(String date_time) {

        Date date = new Date();
        try {
            date = saveDateFormat.parse(date_time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getContcat_Date_Time_second(String date1, String time) {
        String conCat = date1 + " " + time;

        Date date = new Date();
        String asa = null;
        try {
            date = saveFormatter.parse(conCat);
            asa = saveFormatter.format(date);
            return asa;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return asa;
    }

    public String getDateString() {

        return saveDateFormat.format(new Date());
    }

    public String getSaveDateTimeString() {
        return saveFormatter.format(new Date());
    }

    public static boolean compareTimeT1BeforeT2(String startTime, String endTime) {
        Log.e("CURRENT_TIME", "CURRENT_TIME" + startTime);
        Log.e("CURRENT_TIME", "SHIFT_TIME" + endTime);
        String pattern = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {

            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);
            if (date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compareDate(String startDate, String enddate, boolean check_33DayBefore) {
        String pattern = yyyy_MM_dd;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date1, date2;
        try {
            if (check_33DayBefore) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(cal.getTime());
                cal.add(Calendar.DATE, -33);
                Date dateBefore33Days = cal.getTime();
                date1 = dateBefore33Days;
            } else {
                date1 = sdf.parse(startDate);
            }
            date2 = sdf.parse(enddate);
            if (date1.equals(date2)) {
                return true;
            } else if (date1.after(date2)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[] getDutyInDateTime() {
        Date dt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.MINUTE, 45);

        String sa = getSaveFormatter(calendar.getTime());
        Log.d("", "Date Util getDutyInDateTime .... " + sa);
        return sa.split(" ");
    }
    public String getDutyInDateTime11(String dateTime, int flexTime) {
        String sa=null;
                try{
                    Date date11 = DateUtils.getInstance().getDate_TimeFormat().parse(dateTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date11);
                    calendar.add(Calendar.MINUTE, flexTime);

                    sa = getSaveFormatter(calendar.getTime());
                    Log.e("getDutyInDateTime11", "Date Util getDutyInDateTime .... " + sa);
                }catch (Exception e){
                    e.printStackTrace();
                }

        return sa;
    }

    public String getNextDateCalender(String date) {
        String tomorrowDate = null;
        Calendar calendar = Calendar.getInstance();
        try {
            Date date11 = DateUtils.getInstance().getDateFormat().parse(date);
            Log.e("ELSE_MEIN_GAYA", "33333111222>>>>" + date11);
            calendar.setTime(date11);
            calendar.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            Log.e("ELSE_MEIN_GAYA", "33333111222>>>>" + String.valueOf(e));
            e.printStackTrace();
            return null;
        }
        tomorrowDate = DateUtils.getInstance().shiftDateDisplay(calendar.getTime());
        return tomorrowDate;
    }

    public String getPreviousDateCalender(String date) {
        String tomorrowDate = null;
        Calendar calendar = Calendar.getInstance();
        try {
            Date date11 = DateUtils.getInstance().getDateFormat().parse(date);
            Log.e("ELSE_MEIN_GAYA", "33333111222>>>>" + date11);
            calendar.setTime(DateUtils.getInstance().getDateFormat().parse(date));
            calendar.add(Calendar.DATE, -1);
        } catch (ParseException e) {
            Log.e("ELSE_MEIN_GAYA", "33333111222>>>>" + String.valueOf(e));
            e.printStackTrace();
            return null;
        }
        tomorrowDate = DateUtils.getInstance().shiftDateDisplay(calendar.getTime());
        return tomorrowDate;
    }

    public float getDutyHours(String dh) {
        float dutyH = 0.0f;
        try {
            if (dh.contains(":")) {
                dutyH = Float.parseFloat((dh.split(":")[0]));
                dutyH += (Float.parseFloat((dh.split(":")[1]))) / 60.0f;
            } else {
                dutyH = Float.parseFloat(dh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dutyH;
    }

    public String getTime(String dateTimeStr) {
        try {
            Date date = null;
            if (dateTimeStr.contains("-")) {
                date = saveFormatter.parse(dateTimeStr);
                return onlyTimeOutFormatter.format(date);
            } else if (dateTimeStr.contains(":")) {
                return onlyTimeOutFormatter.format(onlyTimeInFormatter.parse(dateTimeStr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTimeStr;
    }

    public Date addThreeYearAdvance(String dateTime) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date11 = DateUtils.getInstance().dateFromSaveFormat(dateTime);
            Log.e("ELSE_MEIN_GAYA", "33333111222>>>>" + date11);
            calendar.setTime(date11);
            calendar.add(Calendar.YEAR, 3);
        } catch (Exception e) {
            Log.e("ELSE_MEIN_GAYA", "33333111222>>>>" + String.valueOf(e));
            e.printStackTrace();
            return null;
        }

        return calendar.getTime();
    }

    public String getHourAndMinutes(String startTime, String endTime) {
        String hourMinutes = "00:00";
        try {
            long t = saveFormatter.parse(endTime).getTime() - saveFormatter.parse(startTime).getTime();
            int minute = (int) ((t / (1000 * 60)) % 60);
            int hour = (int) ((t / (1000 * 60 * 60)) % 24);
            hourMinutes = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hourMinutes;
    }

    public int getMinutesOnly(String startTime, String endTime) {
        int hourMinutes = 0;
        try {
            long t = saveFormatter.parse(endTime).getTime() - saveFormatter.parse(startTime).getTime();
            int minute = (int) ((t / (1000 * 60)));
            Log.e("getMinutesOnly", "33333111222>>>>" + String.valueOf(minute));

            hourMinutes = minute;// + ":" + String.format("%02d", minute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hourMinutes;
    }

    public static boolean compareDate_Nd_TimeFormat(String startTime, String endTime) {
        SimpleDateFormat sdf = DateUtils.getInstance().getDate_TimeFormat();

        try {

            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);
            if (date1.after(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compareDate(String startTime, String endTime) {
        SimpleDateFormat sdf = DateUtils.getInstance().getDateFormat();

        try {

            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);
            if (date1.equals(date2)) {
                return true;
            } else if (date1.before(date2)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String showHourMinutesFromDate(Date calendar) {
        String output = "00:00";
        try {
            output = onlyTimeOutFormatter.format(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String getCurrentDate_format() {
        String date = new SimpleDateFormat(yyyy_MM_dd, Locale.getDefault()).format(new Date());
        return date;
    }

    public static String getCurrentDate_TIME_format() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        return date;
    }

    public static String systemTime() {
        Date date = new Date();
        String[] d = date.toString().split(" ");
        Log.e("split", "" + d[3]);
        String time = d[3];
        String timeSplit[] = time.split(":");
        String hour = timeSplit[0];
        String min = timeSplit[1];
        String sec = timeSplit[2];
        Log.e("timeSplit", "" + hour + "\n" + min + "\n" + sec + "\n" + date.getHours());
        return hour + ":" + min + ":" + sec;
    }
    public static String convert24_Hrs_To12_HrsFormat(String hrs_and_Min){
        String time=null;
        try {
//            String _24HourTime = "22:15";
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm");
            Date _24HourDt = _24HourSDF.parse(hrs_and_Min);

            Log.e("DATETIME","_12HourDt"+_12HourSDF.format(_24HourDt));
            System.out.println(_12HourSDF.format(_24HourDt));
            time=_12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
    public static String convert24_Hrs_To12_HrsWith_AM_OR_PMFormat(String hrs_and_Min){
        String time=null;
        try {
//            String _24HourTime = "22:15";
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(hrs_and_Min);

            Log.e("DATETIME","_12HourDt"+_12HourSDF.format(_24HourDt));
            System.out.println(_12HourSDF.format(_24HourDt));
            time=_12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
    public static long dateTime_Difference(String firstDateTime,String second_Date_Time){

        String dateStart = firstDateTime;
        String dateStop = second_Date_Time;
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = saveFormatter.parse(dateStart);
            d2 = saveFormatter.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }
// Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        Log.e("dateTime_Difference>>>>",""+"SECONDS>>"+diffSeconds+"diffMinutes>>"+diffMinutes+"diffHours>>"+diffHours+
                "DIFF_MILL"+Math.abs(diff)+"diff"+diff);

       /* if(diffHours<=1 && diffMinutes<=0){
            return 0;
        }else if(diffHours>1) {
            return diffHours;
        }*/
        return diffHours;
    }
    public static long dateTime_Difference_In_MilliSecond(String firstDateTime,String second_Date_Time){

        String dateStart = firstDateTime;
        String dateStop = second_Date_Time;
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = saveFormatter.parse(dateStart);
            d2 = saveFormatter.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("dateTime_Difference>>>>",""+"firstDateTime>>"+firstDateTime+"second_Date_Time>>"+second_Date_Time);
// Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        Log.e("dateTime_Difference>>>>",""+"SECONDS>>"+diffSeconds+"diffMinutes>>"+diffMinutes+"diffHours>>"+diffHours+
                "DIFF_MILL"+Math.abs(diff)+"diff"+diff);

       /* if(diffHours<=1 && diffMinutes<=0){
            return 0;
        }else if(diffHours>1) {
            return diffHours;
        }*/
        return Math.abs(diff);
    }
}
