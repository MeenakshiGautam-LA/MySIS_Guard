
package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;

import java.io.Serializable;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.GETSITEDETAILS)
public class GetSiteDetail extends CSObject implements Serializable {
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
    @ColumnInfo(name = "SITE_NAME")
    @SerializedName("SITE_NAME")
    @Expose
    private String sITENAME;

    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    @Expose
    private String dATEMODIFIED;

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    @ColumnInfo(name = "JSON")
    private String JSON;

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    @ColumnInfo(name = "FLAG")
    private String FLAG = "0";

    public int getWeeklyOffAllowed() {
        return weeklyOffAllowed;
    }

    public void setWeeklyOffAllowed(int weeklyOffAllowed) {
        this.weeklyOffAllowed = weeklyOffAllowed;
    }

    public int getCreateRosterAllowed() {
        return createRosterAllowed;
    }

    public void setCreateRosterAllowed(int createRosterAllowed) {
        this.createRosterAllowed = createRosterAllowed;
    }

    public int getCreateShiftAllowed() {
        return createShiftAllowed;
    }

    public void setCreateShiftAllowed(int createShiftAllowed) {
        this.createShiftAllowed = createShiftAllowed;
    }

    public int getEditShiftAllowed() {
        return editShiftAllowed;
    }

    public void setEditShiftAllowed(int editShiftAllowed) {
        this.editShiftAllowed = editShiftAllowed;
    }

    public int getMessAttendanceAllowed() {
        return messAttendanceAllowed;
    }

    public void setMessAttendanceAllowed(int messAttendanceAllowed) {
        this.messAttendanceAllowed = messAttendanceAllowed;
    }

    @ColumnInfo(name = "WEEKLY_OFF_ALLOWED")
    @SerializedName("WEEKLY_OFF_ALLOWED")
    private int weeklyOffAllowed;
    @ColumnInfo(name = "CREATE_ROSTER_ALLOWED")
    @SerializedName("CREATE_ROSTER_ALLOWED")
    private int createRosterAllowed;
    @ColumnInfo(name = "CREATE_SHIFT_ALLOWED")
    @SerializedName("CREATE_SHIFT_ALLOWED")
    private int createShiftAllowed;
    @ColumnInfo(name = "EDIT_SHIFT_ALLOWED")
    @SerializedName("EDIT_SHIFT_ALLOWED")
    private int editShiftAllowed;
    @ColumnInfo(name = "MESS_ATTENDANCE_ALLOWED")
    @SerializedName("MESS_ATTENDANCE_ALLOWED")
    private int messAttendanceAllowed;

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


    public String getSITENAME() {
        return sITENAME;
    }

    public void setSITENAME(String sITENAME) {
        this.sITENAME = sITENAME;
    }

    public String getDATEMODIFIED() {
        return dATEMODIFIED;
    }

    public void setDATEMODIFIED(String dATEMODIFIED) {
        this.dATEMODIFIED = dATEMODIFIED;
    }

}
