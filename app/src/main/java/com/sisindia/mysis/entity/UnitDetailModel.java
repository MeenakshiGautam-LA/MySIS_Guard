package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;

import java.io.Serializable;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.UNIT_DETAIL)
public class UnitDetailModel extends CSObject implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    private String ID;
    @ColumnInfo(name = "REGNO")
    @SerializedName("REGNO")
    private String REGNO;
    @ColumnInfo(name = "UNIT_CODE")
    @SerializedName("UNIT_CODE")
    private String UNIT_CODE;
    @ColumnInfo(name = "SITE_NAME")
    @SerializedName("SITE_NAME")
    private String SITE_NAME;
    @ColumnInfo(name = "BRANCH_CODE")
    @SerializedName("BRANCH_CODE")
    private String BRANCH_CODE;
    @ColumnInfo(name = "BRANCH_NAME")
    @SerializedName("BRANCH_NAME")
    private String BRANCH_NAME;
    @ColumnInfo(name = "IS_PRIMARY")
    @SerializedName("IS_PRIMARY")
    private String IS_PRIMARY;
    @ColumnInfo(name = "DELETED")
    @SerializedName("DELETED")
    private String DELETED;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    private String DATE_MODIFIED;
    @ColumnInfo(name = "FLAG")
    private String FLAG="0";
    @ColumnInfo(name = "JSON")
    private String JSON;

    @NonNull
    public String getID() {
        return ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }

    public String getREGNO() {
        return REGNO;
    }

    public void setREGNO(String REGNO) {
        this.REGNO = REGNO;
    }

    public String getUNIT_CODE() {
        return UNIT_CODE;
    }

    public void setUNIT_CODE(String UNIT_CODE) {
        this.UNIT_CODE = UNIT_CODE;
    }

    public String getSITE_NAME() {
        return SITE_NAME;
    }

    public void setSITE_NAME(String SITE_NAME) {
        this.SITE_NAME = SITE_NAME;
    }

    public String getBRANCH_CODE() {
        return BRANCH_CODE;
    }

    public void setBRANCH_CODE(String BRANCH_CODE) {
        this.BRANCH_CODE = BRANCH_CODE;
    }

    public String getBRANCH_NAME() {
        return BRANCH_NAME;
    }

    public void setBRANCH_NAME(String BRANCH_NAME) {
        this.BRANCH_NAME = BRANCH_NAME;
    }

    public String getIS_PRIMARY() {
        return IS_PRIMARY;
    }

    public void setIS_PRIMARY(String IS_PRIMARY) {
        this.IS_PRIMARY = IS_PRIMARY;
    }

    public String getDELETED() {
        return DELETED;
    }

    public void setDELETED(String DELETED) {
        this.DELETED = DELETED;
    }

    public String getDATE_MODIFIED() {
        return DATE_MODIFIED;
    }

    public void setDATE_MODIFIED(String DATE_MODIFIED) {
        this.DATE_MODIFIED = DATE_MODIFIED;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }
}
