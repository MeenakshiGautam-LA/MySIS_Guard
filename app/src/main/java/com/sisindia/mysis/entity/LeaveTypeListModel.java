package com.sisindia.mysis.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.sisindia.mysis.utils.Constants;

@Entity(tableName = Constants.GET_LEAVE_TYPE_LIST)
public class LeaveTypeListModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    private String ID;
    @ColumnInfo(name = "LEAVE_ID")
    @SerializedName("LEAVE_ID")
    private String LEAVE_ID;
    @ColumnInfo(name = "LEAVE_TYPE")
    @SerializedName("LEAVE_TYPE")
    private String LEAVE_TYPE;
    @ColumnInfo(name = "LEAVE_CODE")
    @SerializedName("LEAVE_CODE")
    private String LEAVE_CODE;
    @ColumnInfo(name = "COLOR_CODE")
    @SerializedName("COLOR_CODE")
    private String COLOR_CODE;
    @ColumnInfo(name = "REMARK")
    @SerializedName("REMARK")
    private String REMARK;

    @ColumnInfo(name = "FOR_SELF")
    @SerializedName("FOR_SELF")
    private String FOR_SELF;
    @SerializedName("VISIBLE")
    @ColumnInfo(name = "VISIBLE")
    private String VISIBLE;


    public String getVISIBLE() {
        return VISIBLE;
    }

    public void setVISIBLE(String VISIBLE) {
        this.VISIBLE = VISIBLE;
    }

    public String getFOR_SELF() {
        return FOR_SELF;
    }

    public void setFOR_SELF(String FOR_SELF) {
        this.FOR_SELF = FOR_SELF;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLEAVE_ID() {
        return LEAVE_ID;
    }

    public void setLEAVE_ID(String LEAVE_ID) {
        this.LEAVE_ID = LEAVE_ID;
    }

    public String getLEAVE_TYPE() {
        return LEAVE_TYPE;
    }

    public void setLEAVE_TYPE(String LEAVE_TYPE) {
        this.LEAVE_TYPE = LEAVE_TYPE;
    }

    public String getLEAVE_CODE() {
        return LEAVE_CODE;
    }

    public void setLEAVE_CODE(String LEAVE_CODE) {
        this.LEAVE_CODE = LEAVE_CODE;
    }

    public String getCOLOR_CODE() {
        return COLOR_CODE;
    }

    public void setCOLOR_CODE(String COLOR_CODE) {
        this.COLOR_CODE = COLOR_CODE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

}
