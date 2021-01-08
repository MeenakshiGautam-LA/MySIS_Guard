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
@Entity(tableName = Constants.Roster_TABLE)
public class RosterModel extends CSObject {
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

    @ColumnInfo(name = "GEO_LOCATION")
    @SerializedName("GEO_LOCATION")
    private String geoLocation;

    @ColumnInfo(name = "DUTY_IN_ENABLE_TIME")
    @SerializedName("DUTY_IN_ENABLE_TIME")
    @Expose
    private String DUTY_IN_ENABLE_TIME = "";
    @ColumnInfo(name = "DUTY_OUT_DISABLE_TIME")
    @SerializedName("DUTY_OUT_DISABLE_TIME")
    @Expose
    private String DUTY_OUT_DISABLE_TIME = "";
    @ColumnInfo(name = "SHIFT_ID")
    @SerializedName("SHIFT_ID")
    @Expose
    private String sHIFTID;

    @ColumnInfo(name = "UNIT_NAME")
    @SerializedName("UNIT_NAME")
    @Expose
    private String unitname;
    @ColumnInfo(name = "ROSTER_DATE")
    @SerializedName("ROSTER_DATE")
    @Expose
    private String rOSTERDATE;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    @Expose
    private String dATEMODIFIED;

    @ColumnInfo(name = "REGNO")
    @SerializedName("REGNO")
    @Expose
    private String rEGNO;

    @ColumnInfo(name = "ROSTER_ID")
    @SerializedName("ROSTER_ID")
    @Expose
    private String rOSTERID;
    @ColumnInfo(name = "DUTY_POST_ID")
    @SerializedName("DUTY_POST_ID")
    @Expose
    private String dUTYPOSTID;

    @ColumnInfo(name = "DUTY_RANK")
    @SerializedName("DUTY_RANK")
    @Expose
    private String dUTYRANK;
    @ColumnInfo(name = "JSON")
    private String json;
    @ColumnInfo(name = "FLAG")
    private String flag = "0";
    @ColumnInfo(name = "SHIFT_START_TIME")
    @SerializedName("START_TIME")
    @Expose
    private String start_time = "";
    @ColumnInfo(name = "SHIFT_END_TIME")
    @SerializedName("END_TIME")
    @Expose
    private String end_time = "";
    @ColumnInfo(name = "QR_CODE")
    @SerializedName("QR_ID")
    @Expose
    private String qr_Code = "";

    public String getDUTY_IN_ENABLE_TIME() {
        return DUTY_IN_ENABLE_TIME;
    }

    public void setDUTY_IN_ENABLE_TIME(String DUTY_IN_ENABLE_TIME) {
        this.DUTY_IN_ENABLE_TIME = DUTY_IN_ENABLE_TIME;
    }

    public String getDUTY_OUT_DISABLE_TIME() {
        return DUTY_OUT_DISABLE_TIME;
    }

    public void setDUTY_OUT_DISABLE_TIME(String DUTY_OUT_DISABLE_TIME) {
        this.DUTY_OUT_DISABLE_TIME = DUTY_OUT_DISABLE_TIME;
    }

    public String getQr_Code() {
        return qr_Code;
    }

    public void setQr_Code(String qr_Code) {
        this.qr_Code = qr_Code;
    }


    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }


    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getSHIFTID() {
        return sHIFTID;
    }

    public void setSHIFTID(String sHIFTID) {
        this.sHIFTID = sHIFTID;
    }

    public String getROSTERDATE() {
        return rOSTERDATE;
    }

    public void setROSTERDATE(String rOSTERDATE) {
        this.rOSTERDATE = rOSTERDATE;
    }



    public String getDATEMODIFIED() {
        return dATEMODIFIED;
    }

    public void setDATEMODIFIED(String dATEMODIFIED) {
        this.dATEMODIFIED = dATEMODIFIED;
    }


    public String getREGNO() {
        return rEGNO;
    }

    public void setREGNO(String rEGNO) {
        this.rEGNO = rEGNO;
    }

    public String getROSTERID() {
        return rOSTERID;
    }

    public void setROSTERID(String rOSTERID) {
        this.rOSTERID = rOSTERID;
    }

    public String getDUTYPOSTID() {
        return dUTYPOSTID;
    }

    public void setDUTYPOSTID(String dUTYPOSTID) {
        this.dUTYPOSTID = dUTYPOSTID;
    }

    public String getDUTYRANK() {
        return dUTYRANK;
    }

    public void setDUTYRANK(String dUTYRANK) {
        this.dUTYRANK = dUTYRANK;
    }


}

