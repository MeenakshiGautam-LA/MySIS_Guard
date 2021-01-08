package com.sisindia.mysis.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.utils.Constants;

@Entity(tableName = Constants.FLASH_MESSAGE_TABLE)
public class Flash_Message_Model {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    private String ID;
    @ColumnInfo(name = "USER_ID")
    private String USER_ID;
    @ColumnInfo(name = "MESSAGE")
    private String MESSAGE;
    @ColumnInfo(name = "IMAGE_ID")
    private String IMAGE_ID;
    @ColumnInfo(name = "DELETED")
    private String DELETED;
    @ColumnInfo(name = "ACTION_URL")
    private String ACTION_URL;
    @ColumnInfo(name = "CREATED_BY")
    private String CREATED_BY;
    @ColumnInfo(name = "CREATED_ON")
    private String CREATED_ON;
    @ColumnInfo(name = "MODIFIED_BY")
    private String MODIFIED_BY;
    @ColumnInfo(name = "DATE_MODIFIED")
    private String DATE_MODIFIED;
    @ColumnInfo(name = "JSON")
    private String JSON;

    @ColumnInfo(name = "FLAG")
    private String FLAG="0";


    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    @NonNull
    public String getID() {
        return ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getIMAGE_ID() {
        return IMAGE_ID;
    }

    public void setIMAGE_ID(String IMAGE_ID) {
        this.IMAGE_ID = IMAGE_ID;
    }

    public String getDELETED() {
        return DELETED;
    }

    public void setDELETED(String DELETED) {
        this.DELETED = DELETED;
    }

    public String getACTION_URL() {
        return ACTION_URL;
    }

    public void setACTION_URL(String ACTION_URL) {
        this.ACTION_URL = ACTION_URL;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
    }

    public String getCREATED_ON() {
        return CREATED_ON;
    }

    public void setCREATED_ON(String CREATED_ON) {
        this.CREATED_ON = CREATED_ON;
    }

    public String getMODIFIED_BY() {
        return MODIFIED_BY;
    }

    public void setMODIFIED_BY(String MODIFIED_BY) {
        this.MODIFIED_BY = MODIFIED_BY;
    }

    public String getDATE_MODIFIED() {
        return DATE_MODIFIED;
    }

    public void setDATE_MODIFIED(String DATE_MODIFIED) {
        this.DATE_MODIFIED = DATE_MODIFIED;
    }
}
