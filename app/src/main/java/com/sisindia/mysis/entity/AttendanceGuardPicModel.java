package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.ATTENANCE_PIC_TABLE)
public class AttendanceGuardPicModel extends CSObject {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    private String id;
    @ColumnInfo(name = "PICTURE")
    private byte[] picture;
    @ColumnInfo(name = "REGNO")
    private String regNo;
    @ColumnInfo(name = "ATTENDANCE_ID")
    private String attendanceID;
    @ColumnInfo(name = "DATE_MODIFIED")
    private String dateModified;
    @ColumnInfo(name = "FLAG")
    private String flag = "0";
    @ColumnInfo(name = "JSON")
    private String json;
    @ColumnInfo(name = "DUTY_STATUS")
    private String DUTY_STATUS;

    @NonNull
    public String getId() {
        return id;
    }

    public String getDUTY_STATUS() {
        return DUTY_STATUS;
    }

    public void setDUTY_STATUS(String DUTY_STATUS) {
        this.DUTY_STATUS = DUTY_STATUS;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
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


}
