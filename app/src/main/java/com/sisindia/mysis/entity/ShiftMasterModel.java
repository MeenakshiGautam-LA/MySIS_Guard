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
@Entity(tableName = Constants.SHIFTMASTER)
public class ShiftMasterModel extends CSObject {
    public String getDutyHourGet() {
        return dutyHourGet;
    }

    public void setDutyHourGet(String dutyHourGet) {
        this.dutyHourGet = dutyHourGet;
    }

    @ColumnInfo(name = "DUTY_HOUR")
    @SerializedName("DUTY_HRS")
    private String dutyHourGet;

    @NonNull
    @Override
    public String toString() {
        return shiftName;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    @Expose
    private String id = "";
    @ColumnInfo(name = "SHIFT_NAME")
    @SerializedName("SHIFT_NAME")
    @Expose
    private String shiftName;

    @ColumnInfo(name = "UNIT_CODE")
    @SerializedName("UNIT_CODE")
    @Expose
    private String unitCode;

    @ColumnInfo(name = "START_TIME")
    @SerializedName("START_TIME")
    @Expose
    private String startTime;

    @ColumnInfo(name = "END_TIME")
    @SerializedName("END_TIME")
    @Expose
    private String endTime;
    @ColumnInfo(name = "START_DATE")
    @SerializedName("START_DATE")
    @Expose
    private String startDate;

    @ColumnInfo(name = "END_DATE")
    @SerializedName("END_DATE")
    @Expose
    private String endDate;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    @Expose
    private String dateOfModified;

    @ColumnInfo(name = "JSON")
    private String Json;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @ColumnInfo(name = "FLAG")
    private String flag = "0";
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDateOfModified() {
        return dateOfModified;
    }

    public void setDateOfModified(String dateOfModified) {
        this.dateOfModified = dateOfModified;
    }

    public String getJson() {
        return Json;
    }

    public void setJson(String json) {
        Json = json;
    }
}

