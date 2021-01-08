package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;
import com.google.gson.annotations.SerializedName;


@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.LEAVE_MASTER)
public class LeaveMaster extends CSObject {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    private String ID;

    @ColumnInfo(name = "LEAVE_TYPE")
    @SerializedName("LEAVE_TYPE")
    private int LEAVE_TYPE;
    @ColumnInfo(name = "LEAVE_STATUS")
    @SerializedName("LEAVE_STATUS")
    private int leaveStatus;
    @ColumnInfo(name = "FLAG")
    private String flag="0";
    @ColumnInfo(name = "JSON")
    private String json;
    @ColumnInfo(name = "LEAVE_START_DATE")
    @SerializedName("LEAVE_START_DATE")
    private String leaveStartDate;
    @ColumnInfo(name = "LEAVE_END_DATE")
    @SerializedName("LEAVE_END_DATE")
    private String leaveEndDate;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    private String DATE_MODIFIED;
    @ColumnInfo(name = "LEAVE_REQUEST_TYPE")
    @SerializedName("LEAVE_REQUEST_TYPE")
    private String LEAVE_REQUEST_TYPE;
    @Ignore
    private int countLeaveDays;
    @Ignore
    private boolean ischecked = false;
    @ColumnInfo(name = "REGNO")
    @SerializedName("REGNO")
    private String REGNO;
//    @ColumnInfo(name = "DELETED")
//    @SerializedName("DELETED")
//    private String DELETED;


//    public String getDELETED() {
//        return DELETED;
//    }
//
//    public void setDELETED(String DELETED) {
//        this.DELETED = DELETED;
//    }

    public String getREGNO() {
        return REGNO;
    }

    public void setREGNO(String REGNO) {
        this.REGNO = REGNO;
    }

    public int getLEAVE_TYPE() {
        return LEAVE_TYPE;
    }

    public void setLEAVE_TYPE(int LEAVE_TYPE) {
        this.LEAVE_TYPE = LEAVE_TYPE;
    }

    public int getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(int leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public int getCountLeaveDays() {
        return countLeaveDays;
    }

    public void setCountLeaveDays(int countLeaveDays) {
        this.countLeaveDays = countLeaveDays;
    }

    public String getLEAVE_REQUEST_TYPE() {
        return LEAVE_REQUEST_TYPE;
    }

    public void setLEAVE_REQUEST_TYPE(String LEAVE_REQUEST_TYPE) {
        this.LEAVE_REQUEST_TYPE = LEAVE_REQUEST_TYPE;
    }

    @NonNull
    public String getID() {
        return ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(String leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public String getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(String leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public String getDATE_MODIFIED() {
        return DATE_MODIFIED;
    }

    public void setDATE_MODIFIED(String DATE_MODIFIED) {
        this.DATE_MODIFIED = DATE_MODIFIED;
    }

}
