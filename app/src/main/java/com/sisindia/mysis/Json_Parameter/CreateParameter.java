package com.sisindia.mysis.Json_Parameter;

import android.location.Location;

import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.Model.Other_Duty_Mark_Model;
import com.sisindia.mysis.entity.AttendanceGuardPicModel;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.LeaveMaster;
import com.sisindia.mysis.entity.ProfileModel;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.DateUtils;

import org.json.JSONObject;

public class CreateParameter {
    public static String changeContactJson(ProfileModel profileModel) {
        String jsonReturn = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", profileModel.getId());
            jsonObject.put("DATE_MODIFIED", profileModel.getDateModified());
            jsonObject.put("CONTACT_NUMBER", profileModel.getContactNumber());
            jsonObject.put("APPROVED", profileModel.getApproved());
            jsonReturn = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonReturn;
    }
    public static String leaveMasterJson(LeaveMaster leaveMaster) {
        String jsonReturn = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", leaveMaster.getID());
            jsonObject.put("DATE_MODIFIED", leaveMaster.getDATE_MODIFIED());
            jsonObject.put("REGNO", leaveMaster.getREGNO());
            jsonObject.put("LEAVE_STATUS", leaveMaster.getLeaveStatus());
            jsonObject.put("LEAVE_START_DATE", leaveMaster.getLeaveStartDate());
            jsonObject.put("LEAVE_END_DATE", leaveMaster.getLeaveEndDate());
            jsonObject.put("LEAVE_TYPE", leaveMaster.getLEAVE_TYPE());
            jsonObject.put("LEAVE_REQUEST_TYPE", leaveMaster.getLEAVE_REQUEST_TYPE());
            jsonReturn = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonReturn;
    }

    public static String createJsonForAttendancePicture(AttendanceGuardPicModel guardPicModel) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();

            jsonObject.put("ID", guardPicModel.getId());
            jsonObject.put("DATE_MODIFIED", guardPicModel.getDateModified());
            jsonObject.put("ATTENDANCE_ID", guardPicModel.getAttendanceID());
            jsonObject.put("REGNO", guardPicModel.getRegNo());
            jsonObject.put("DATE_MODIFIED", guardPicModel.getDateModified());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public static String setJson_mark_Attendance(MapTableRosterAndShift mapTableRosterAndShift,
                                                 String duty_status, DailyAttendanceDetail checkReturn, Location location) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", checkReturn.getID());
            jsonObject.put("UNIT_CODE", mapTableRosterAndShift.getUNIT_CODE());
            jsonObject.put("REGNO", mapTableRosterAndShift.getREGNO());
            jsonObject.put("SHIFT_ID", mapTableRosterAndShift.getSHIFT_ID());
            jsonObject.put("SHIFT_START_TIME", checkReturn.getSHIFTSTARTTIME());
            jsonObject.put("SHIFT_END_TIME", checkReturn.getSHIFTENDTIME());
            jsonObject.put("FINAL_START_TIME", checkReturn.getACTSTARTTIME());
            jsonObject.put("DUTY_POST_ID", checkReturn.getDUTYPOSTID());
           /* if (location != null) {
                jsonObject.put("DUTY_IN_LAT", String.valueOf(location.getLongitude()));
                jsonObject.put("DUTY_IN_LNG", String.valueOf(location.getLongitude()));
            }*/
            jsonObject.put("ATTENDANCE_MODE", "DEVICE");
//            jsonObject.put("VIOLATION_REMARK", "");
            if (duty_status.equals("DUTY_IN")) {
                if(!CSStringUtil.isEmptyStr(CSShearedPrefence.getLatAndLongitude())){
                    jsonObject.put("DUTY_IN_LAT_LNG", CSShearedPrefence.getLatAndLongitude());
                };

                jsonObject.put("ACT_START_TIME", DateUtils.getCurrentDate_TIME_format());
                jsonObject.put("ACT_END_TIME", "");
                jsonObject.put("FINAL_END_TIME", "");

            } else {
                if(!CSStringUtil.isEmptyStr(CSShearedPrefence.getLatAndLongitude())){
                    jsonObject.put("DUTY_OUT_LAT_LNG", CSShearedPrefence.getLatAndLongitude());
                }
                jsonObject.put("ACT_START_TIME", checkReturn.getACTSTARTTIME());
                jsonObject.put("ACT_END_TIME", DateUtils.getCurrentDate_TIME_format());
                jsonObject.put("FINAL_END_TIME", DateUtils.getCurrentDate_TIME_format());
            }
            jsonObject.put("DUTY_RANK", mapTableRosterAndShift.getDUTY_RANK());
//            jsonObject.put("SHIFT_CLOSE_ID", shift_closer_id);
//            jsonObject.put("VALIDATE", voilate);
//            jsonObject.put("ATTENDANCE_MODE", attendanceMode);
            jsonObject.put("CREATED_BY", CSShearedPrefence.getUser_ID());
            jsonObject.put("DATE_ENTERED", DateUtils.getInstance().getSaveDateTimeString());
            jsonObject.put("EXTENSION_HOURS", "");
            jsonObject.put("EXTENSION", 0);
            jsonObject.put("DUTY_STATUS", duty_status);
            jsonObject.put("SHIFT_STATUS", "0");
            jsonObject.put("VALIDATE", checkReturn.getVOILATE());
            jsonObject.put("VALIDATE_REMARK", checkReturn.getViolationRemark());
            jsonObject.put("MODIFY_REASON_ID", Constants.EMPTY_UUID);

        } catch (Exception e) {

        }
        return jsonObject.toString();
    }

    public static String setJsonForOtherUnitDutyEmployee(Other_Duty_Mark_Model other_duty_mark_model,
                                                         String duty_status, DailyAttendanceDetail checkReturn, Location location) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", checkReturn.getID());
            jsonObject.put("UNIT_CODE", other_duty_mark_model.getRosterDetail().getUNITCODE());
            jsonObject.put("REGNO", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
            jsonObject.put("SHIFT_ID", other_duty_mark_model.getRosterDetail().getSHIFTID());
            jsonObject.put("SHIFT_START_TIME", checkReturn.getSHIFTSTARTTIME());
            jsonObject.put("SHIFT_END_TIME", checkReturn.getSHIFTENDTIME());
            jsonObject.put("FINAL_START_TIME", checkReturn.getACTSTARTTIME());
            jsonObject.put("DUTY_POST_ID", checkReturn.getDUTYPOSTID());
            jsonObject.put("ATTENDANCE_MODE", "OTHER_DUTY");
           /* if (location != null) {
                jsonObject.put("LAT", String.valueOf(location.getLongitude()));
                jsonObject.put("LNG", String.valueOf(location.getLongitude()));
            }*/
//            jsonObject.put("VIOLATION_REMARK", "");
            if (duty_status.equals("DUTY_IN")) {
                if(!CSStringUtil.isEmptyStr(CSShearedPrefence.getLatAndLongitude())){
                    jsonObject.put("DUTY_IN_LAT_LNG", CSShearedPrefence.getLatAndLongitude());

                }

                jsonObject.put("ACT_START_TIME", DateUtils.getCurrentDate_TIME_format());
                jsonObject.put("ACT_END_TIME", "");
                jsonObject.put("FINAL_END_TIME", "");

            } else {
                if(!CSStringUtil.isEmptyStr(CSShearedPrefence.getLatAndLongitude())){
                    jsonObject.put("DUTY_OUT_LAT_LNG", CSShearedPrefence.getLatAndLongitude());

                }

                jsonObject.put("ACT_START_TIME", checkReturn.getACTSTARTTIME());
                jsonObject.put("ACT_END_TIME", DateUtils.getCurrentDate_TIME_format());
                jsonObject.put("FINAL_END_TIME", DateUtils.getCurrentDate_TIME_format());
            }
            jsonObject.put("DUTY_RANK", other_duty_mark_model.getRosterDetail().getDUTYRANK());
//            jsonObject.put("SHIFT_CLOSE_ID", shift_closer_id);
//            jsonObject.put("VALIDATE", voilate);
//            jsonObject.put("ATTENDANCE_MODE", attendanceMode);
            jsonObject.put("CREATED_BY", CSShearedPrefence.getUser_ID());
            jsonObject.put("DATE_ENTERED", DateUtils.getInstance().getSaveDateTimeString());
            jsonObject.put("EXTENSION_HOURS", "");
            jsonObject.put("EXTENSION", 0);
            jsonObject.put("DUTY_STATUS", duty_status);
            jsonObject.put("SHIFT_STATUS", "0");
            jsonObject.put("VALIDATE", checkReturn.getVOILATE());
            jsonObject.put("VALIDATE_REMARK", checkReturn.getViolationRemark());
            jsonObject.put("MODIFY_REASON_ID", Constants.EMPTY_UUID);

        } catch (Exception e) {
e.printStackTrace();
        }
        return jsonObject.toString();
    }
    public static String setJsonForOtherUnitDutyEmployee_OUT(Other_Duty_Mark_Model other_duty_mark_model,
                                                         String duty_status, DailyAttendanceDetail checkReturn, Location location) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", checkReturn.getID());
            jsonObject.put("UNIT_CODE", other_duty_mark_model.getDailyAttendanceDetail().getUNITCODE());
            jsonObject.put("REGNO", other_duty_mark_model.getEmployeDetailsModel().getREGNO());
            jsonObject.put("SHIFT_ID", other_duty_mark_model.getDailyAttendanceDetail().getSHIFTID());
            jsonObject.put("SHIFT_START_TIME", checkReturn.getSHIFTSTARTTIME());
            jsonObject.put("SHIFT_END_TIME", checkReturn.getSHIFTENDTIME());
            jsonObject.put("FINAL_START_TIME", checkReturn.getACTSTARTTIME());
            jsonObject.put("DUTY_POST_ID", checkReturn.getDUTYPOSTID());
            jsonObject.put("ATTENDANCE_MODE", "OTHER_DUTY");
           /* if (location != null) {
                jsonObject.put("LAT", String.valueOf(location.getLongitude()));
                jsonObject.put("LNG", String.valueOf(location.getLongitude()));
            }*/
//            jsonObject.put("VIOLATION_REMARK", "");
            if (duty_status.equals("DUTY_IN")) {
                if(!CSStringUtil.isEmptyStr(CSShearedPrefence.getLatAndLongitude())){
                    jsonObject.put("DUTY_IN_LAT_LNG", CSShearedPrefence.getLatAndLongitude());

                }
                jsonObject.put("ACT_START_TIME", DateUtils.getCurrentDate_TIME_format());
                jsonObject.put("ACT_END_TIME", "");
                jsonObject.put("FINAL_END_TIME", "");

            } else {
                if(!CSStringUtil.isEmptyStr(CSShearedPrefence.getLatAndLongitude())){
                    jsonObject.put("DUTY_OUT_LAT_LNG", CSShearedPrefence.getLatAndLongitude());

                }
                jsonObject.put("ACT_START_TIME", checkReturn.getACTSTARTTIME());
                jsonObject.put("ACT_END_TIME", DateUtils.getCurrentDate_TIME_format());
                jsonObject.put("FINAL_END_TIME", DateUtils.getCurrentDate_TIME_format());
            }
            jsonObject.put("DUTY_RANK", other_duty_mark_model.getDailyAttendanceDetail().getDUTYRANK());
//            jsonObject.put("SHIFT_CLOSE_ID", shift_closer_id);
//            jsonObject.put("VALIDATE", voilate);
//            jsonObject.put("ATTENDANCE_MODE", attendanceMode);
            jsonObject.put("CREATED_BY", CSShearedPrefence.getUser_ID());
            jsonObject.put("DATE_ENTERED", DateUtils.getInstance().getSaveDateTimeString());
            jsonObject.put("EXTENSION_HOURS", "");
            jsonObject.put("EXTENSION", 0);
            jsonObject.put("DUTY_STATUS", duty_status);
            jsonObject.put("SHIFT_STATUS", "0");
            jsonObject.put("VALIDATE", checkReturn.getVOILATE());
            jsonObject.put("VALIDATE_REMARK", checkReturn.getViolationRemark());
            jsonObject.put("MODIFY_REASON_ID", Constants.EMPTY_UUID);

        } catch (Exception e) {
        e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
