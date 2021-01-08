package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.PROFILE_TABLE)
public class ProfileModel extends CSObject {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "REGNO")
    private String regNO;
    @ColumnInfo(name = "ID")
    private String id;
    @ColumnInfo(name = "CONTACT_NO")
    private String contactNumber;
    @ColumnInfo(name = "FLAG")
    private String flag;
    @ColumnInfo(name = "JSON")
    private String json;
    @ColumnInfo(name = "APPROVED")
    private String approved;
    @ColumnInfo(name = "DATE_MODIFIED")
    private String dateModified;


    public String getRegNO() {
        return regNO;
    }

    public void setRegNO(String regNO) {
        this.regNO = regNO;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }


}
