package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.LEAVE_TRANSACTION_DETAILS)
public class LeaveDetailTransaction extends CSObject {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    private String ID;
    @ColumnInfo(name = "REGNO")
    @SerializedName("REGNO")
    private String regNo;
    @ColumnInfo(name = "IS_OFF")
    @SerializedName("IS_OFF")
    private String isOff;
    @ColumnInfo(name = "LEAVE_TYPE_ID")
    @SerializedName("LEAVE_TYPE_ID")
    private int leaveReasonId;
    @ColumnInfo(name = "JSON")
    private String JSON;
    @ColumnInfo(name = "FLAG")
    private String flag="0";
    @ColumnInfo(name = "LEAVE_DATE")
    @SerializedName("LEAVE_DATE")
    private String leaveDate;

    @ColumnInfo(name = "LEAVE_DESCRIPTION")
    @SerializedName("LEAVE_DESCRIPTION")
    private String leaveDescriptions;
    @ColumnInfo(name = "LEAVE_MASTER_ID")
    @SerializedName("LEAVE_MASTER_ID")
    private String leaveMaster_Id;

    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    private String DATE_MODIFIED;

    public String getDATE_MODIFIED() {
        return DATE_MODIFIED;
    }

    public void setDATE_MODIFIED(String DATE_MODIFIED) {
        this.DATE_MODIFIED = DATE_MODIFIED;
    }
    public String getLeaveMaster_Id() {
        return leaveMaster_Id;
    }

    public void setLeaveMaster_Id(String leaveMaster_Id) {
        this.leaveMaster_Id = leaveMaster_Id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }


    public String getIsOff() {
        return isOff;
    }

    public void setIsOff(String isOff) {
        this.isOff = isOff;
    }

    public int getLeaveReasonId() {
        return leaveReasonId;
    }

    public void setLeaveReasonId(int leaveReasonId) {
        this.leaveReasonId = leaveReasonId;
    }

    public String getLeaveDescriptions() {
        return leaveDescriptions;
    }

    public void setLeaveDescriptions(String leaveDescriptions) {
        this.leaveDescriptions = leaveDescriptions;
    }

    @NonNull
    public String getID() {
        return ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }
}
