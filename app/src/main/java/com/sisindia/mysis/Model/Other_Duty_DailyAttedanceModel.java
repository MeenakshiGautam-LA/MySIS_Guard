//package com.sisindia.guardattendance.Model;
//
//import androidx.annotation.NonNull;
//import androidx.room.ColumnInfo;
//import androidx.room.PrimaryKey;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class Other_Duty_DailyAttedanceModel implements Serializable {
//
//    private String final_Start_Time;
//    @ColumnInfo(name = "FINAL_END_TIME")
//    private String final_End_Time;
//    @PrimaryKey
//    @NonNull
//    @SerializedName("ID")
//    @Expose
//    private String iD;
//    @SerializedName("UNIT_CODE")
//    @Expose
//    private String uNITCODE;
//    @SerializedName("DUTY_POST_ID")
//    @Expose
//    private String dUTYPOSTID;
//    @SerializedName("REGNO")
//    @Expose
//    private String rEGNO;
//    @SerializedName("EMP_RANK")
//    @Expose
//    private String eMPRANK;
//    @SerializedName("DUTY_RANK")
//    @Expose
//    private String dUTYRANK;
//    @SerializedName("SHIFT_ID")
//    @Expose
//    private String sHIFTID;
//    @SerializedName("SHIFT_START_TIME")
//    @Expose
//    private String sHIFTSTARTTIME;
//    @SerializedName("SHIFT_END_TIME")
//    @Expose
//    private String sHIFTENDTIME;
//    @SerializedName("DUTY_STATUS")
//    @Expose
//    private String duty_status;
//    @SerializedName("ACT_START_TIME")
//    @Expose
//    private String aCTSTARTTIME;
//    @SerializedName("ACT_END_TIME")
//    @Expose
//    private String aCTENDTIME;
//    @SerializedName("DATE_MODIFIED")
//    @Expose
//    private String DATE_MODIFIED;
//    @SerializedName("SHIFT_STATUS")
//    @Expose
//    private String shiftStatus = "0";
//    @SerializedName("SHIFT_CLOSE_ID")
//    private String shift_CloserId;
//    @SerializedName("QR_ID")
//    private String qr_Code;
//
//
//    public String getQr_Code() {
//        return qr_Code;
//    }
//
//    public void setQr_Code(String qr_Code) {
//        this.qr_Code = qr_Code;
//    }
//
//    public String getShift_CloserId() {
//        return shift_CloserId;
//    }
//
//    public void setShift_CloserId(String shift_CloserId) {
//        this.shift_CloserId = shift_CloserId;
//    }
//
//
//    public String getDuty_status() {
//        return duty_status;
//    }
//
//    public void setDuty_status(String duty_status) {
//        this.duty_status = duty_status;
//    }
//
//    public String getID() {
//        return iD;
//    }
//
//    public void setID(String iD) {
//        this.iD = iD;
//    }
//
//    public String getUNITCODE() {
//        return uNITCODE;
//    }
//
//    public void setUNITCODE(String uNITCODE) {
//        this.uNITCODE = uNITCODE;
//    }
//
//
//    public String getDUTYPOSTID() {
//        return dUTYPOSTID;
//    }
//
//    public void setDUTYPOSTID(String dUTYPOSTID) {
//        this.dUTYPOSTID = dUTYPOSTID;
//    }
//
//
//    public String getREGNO() {
//        return rEGNO;
//    }
//
//    public void setREGNO(String rEGNO) {
//        this.rEGNO = rEGNO;
//    }
//
//    public String getEMPRANK() {
//        return eMPRANK;
//    }
//
//    public void setEMPRANK(String eMPRANK) {
//        this.eMPRANK = eMPRANK;
//    }
//
//    public String getDUTYRANK() {
//        return dUTYRANK;
//    }
//
//    public void setDUTYRANK(String dUTYRANK) {
//        this.dUTYRANK = dUTYRANK;
//    }
//
//
//    public String getSHIFTID() {
//        return sHIFTID;
//    }
//
//    public void setSHIFTID(String sHIFTID) {
//        this.sHIFTID = sHIFTID;
//    }
//
//
//    public String getSHIFTSTARTTIME() {
//        return sHIFTSTARTTIME;
//    }
//
//    public void setSHIFTSTARTTIME(String sHIFTSTARTTIME) {
//        this.sHIFTSTARTTIME = sHIFTSTARTTIME;
//    }
//
//    public String getSHIFTENDTIME() {
//        return sHIFTENDTIME;
//    }
//
//    public void setSHIFTENDTIME(String sHIFTENDTIME) {
//        this.sHIFTENDTIME = sHIFTENDTIME;
//    }
//
//
//    public String getACTSTARTTIME() {
//        return aCTSTARTTIME;
//    }
//
//    public void setACTSTARTTIME(String aCTSTARTTIME) {
//        this.aCTSTARTTIME = aCTSTARTTIME;
//    }
//
//    public String getACTENDTIME() {
//        return aCTENDTIME;
//    }
//
//    public void setACTENDTIME(String aCTENDTIME) {
//        this.aCTENDTIME = aCTENDTIME;
//    }
//
//
//    public String getDATE_MODIFIED() {
//        return DATE_MODIFIED;
//    }
//
//    public void setDATE_MODIFIED(String dATEMODIFIED) {
//        this.DATE_MODIFIED = dATEMODIFIED;
//    }
//
//    public String getShiftStatus() {
//        return shiftStatus;
//    }
//
//    public void setShiftStatus(String shiftStatus) {
//        this.shiftStatus = shiftStatus;
//    }
//
//
//    public String getFinal_Start_Time() {
//        return final_Start_Time;
//    }
//
//    public void setFinal_Start_Time(String final_Start_Time) {
//        this.final_Start_Time = final_Start_Time;
//    }
//
//    public String getFinal_End_Time() {
//        return final_End_Time;
//    }
//
//    public void setFinal_End_Time(String final_End_Time) {
//        this.final_End_Time = final_End_Time;
//    }
//}
