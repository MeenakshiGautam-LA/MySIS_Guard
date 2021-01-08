package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.Attendance_Master_Table)
public class AttendanceMasterModel extends CSObject {

    @ColumnInfo(name = "FINAL_START_TIME")
    private String final_Start_Time;
    @ColumnInfo(name = "FINAL_END_TIME")
    private String final_End_Time;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    @Expose
    private String iD;
    @ColumnInfo(name = "UNIT_CODE")
    @SerializedName("UNIT_CODE")
    @Expose
    private String uNITCODE;
    @SerializedName("DUTY_POST_ID")
    @Expose
    private String dUTYPOSTID;
    @ColumnInfo(name = "REGNO")
    @SerializedName("REGNO")
    @Expose
    private String rEGNO;
    @ColumnInfo(name = "EMP_RANK")
    @SerializedName("EMP_RANK")
    @Expose
    private String eMPRANK;
    @ColumnInfo(name = "DUTY_RANK")
    @SerializedName("DUTY_RANK")
    @Expose
    private String dUTYRANK;
    @ColumnInfo(name = "SHIFT_ID")
    @SerializedName("SHIFT_ID")
    @Expose
    private String sHIFTID;
    @ColumnInfo(name = "SHIFT_START_TIME")
    @SerializedName("SHIFT_START_TIME")
    @Expose
    private String sHIFTSTARTTIME;
    @ColumnInfo(name = "SHIFT_END_TIME")
    @SerializedName("SHIFT_END_TIME")
    @Expose
    private String sHIFTENDTIME;
    @ColumnInfo(name = "DUTY_STATUS")
    @SerializedName("DUTY_STATUS")
    @Expose
    private String duty_status;
    @ColumnInfo(name = "ACT_START_TIME")
    @SerializedName("ACT_START_TIME")
    @Expose
    private String aCTSTARTTIME;
    @ColumnInfo(name = "ACT_END_TIME")
    @SerializedName("ACT_END_TIME")
    @Expose
    private String aCTENDTIME;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    @Expose
    private String DATE_MODIFIED;
    @ColumnInfo(name = "SHIFT_STATUS")
    @SerializedName("SHIFT_STATUS")
    @Expose
    private String shiftStatus = "0";
    @ColumnInfo(name = "JSON")
    private String JSON;
    @ColumnInfo(name = "FLAG")
    private String flag = "0";
    @ColumnInfo(name = "VOILATE")
    private String VOILATE = "0";
    @ColumnInfo(name = "provideReason")
    @SerializedName("MODIFY_REASON_ID")
    @Expose
    private String provideReason;
    @ColumnInfo(name = "VIOLATION_REMARK")
    @SerializedName("VIOLATION_REMARK")
    private String violationRemark;
    @ColumnInfo(name = "SHIFT_CLOSER_ID")
    @SerializedName("SHIFT_CLOSE_ID")
    private String shift_CloserId;

    public String getShift_CloserId() {
        return shift_CloserId;
    }

    public void setShift_CloserId(String shift_CloserId) {
        this.shift_CloserId = shift_CloserId;
    }


    public String getViolationRemark() {
        return violationRemark;
    }

    public void setViolationRemark(String violationRemark) {
        this.violationRemark = violationRemark;
    }


    public String getVOILATE() {
        return VOILATE;
    }

    public void setVOILATE(String VOILATE) {
        this.VOILATE = VOILATE;
    }

    public String getProvideReason() {
        return provideReason;
    }

    public void setProvideReason(String provideReason) {
        this.provideReason = provideReason;
    }

    public String getDuty_status() {
        return duty_status;
    }

    public void setDuty_status(String duty_status) {
        this.duty_status = duty_status;
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }


    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getUNITCODE() {
        return uNITCODE;
    }

    public void setUNITCODE(String uNITCODE) {
        this.uNITCODE = uNITCODE;
    }


    public String getDUTYPOSTID() {
        return dUTYPOSTID;
    }

    public void setDUTYPOSTID(String dUTYPOSTID) {
        this.dUTYPOSTID = dUTYPOSTID;
    }


    public String getREGNO() {
        return rEGNO;
    }

    public void setREGNO(String rEGNO) {
        this.rEGNO = rEGNO;
    }

    public String getEMPRANK() {
        return eMPRANK;
    }

    public void setEMPRANK(String eMPRANK) {
        this.eMPRANK = eMPRANK;
    }

    public String getDUTYRANK() {
        return dUTYRANK;
    }

    public void setDUTYRANK(String dUTYRANK) {
        this.dUTYRANK = dUTYRANK;
    }


    public String getSHIFTID() {
        return sHIFTID;
    }

    public void setSHIFTID(String sHIFTID) {
        this.sHIFTID = sHIFTID;
    }


    public String getSHIFTSTARTTIME() {
        return sHIFTSTARTTIME;
    }

    public void setSHIFTSTARTTIME(String sHIFTSTARTTIME) {
        this.sHIFTSTARTTIME = sHIFTSTARTTIME;
    }

    public String getSHIFTENDTIME() {
        return sHIFTENDTIME;
    }

    public void setSHIFTENDTIME(String sHIFTENDTIME) {
        this.sHIFTENDTIME = sHIFTENDTIME;
    }


    public String getACTSTARTTIME() {
        return aCTSTARTTIME;
    }

    public void setACTSTARTTIME(String aCTSTARTTIME) {
        this.aCTSTARTTIME = aCTSTARTTIME;
    }

    public String getACTENDTIME() {
        return aCTENDTIME;
    }

    public void setACTENDTIME(String aCTENDTIME) {
        this.aCTENDTIME = aCTENDTIME;
    }


    public String getDATE_MODIFIED() {
        return DATE_MODIFIED;
    }

    public void setDATE_MODIFIED(String dATEMODIFIED) {
        this.DATE_MODIFIED = dATEMODIFIED;
    }



   /* public String getShiftCloserPunchInTime() {
        return shiftCloserPunchInTime;
    }

    public void setShiftCloserPunchInTime(String shiftCloserPunchInTime) {
        this.shiftCloserPunchInTime = shiftCloserPunchInTime;
    }

    public String getShiftCloserPunchOutTime() {
        return shiftCloserPunchOutTime;
    }

    public void setShiftCloserPunchOutTime(String shiftCloserPunchOutTime) {
        this.shiftCloserPunchOutTime = shiftCloserPunchOutTime;
    }*/

    public String getShiftStatus() {
        return shiftStatus;
    }

    public void setShiftStatus(String shiftStatus) {
        this.shiftStatus = shiftStatus;
    }


    public String getFinal_Start_Time() {
        return final_Start_Time;
    }

    public void setFinal_Start_Time(String final_Start_Time) {
        this.final_Start_Time = final_Start_Time;
    }

    public String getFinal_End_Time() {
        return final_End_Time;
    }

    public void setFinal_End_Time(String final_End_Time) {
        this.final_End_Time = final_End_Time;
    }
}
