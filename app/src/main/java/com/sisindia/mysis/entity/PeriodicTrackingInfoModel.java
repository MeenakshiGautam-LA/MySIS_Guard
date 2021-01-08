package com.sisindia.mysis.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.sisindia.mysis.utils.Constants;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = Constants.TRACKING_INFO)
public class PeriodicTrackingInfoModel {
    @PrimaryKey
    @ColumnInfo(name = "ID")
    @Expose
    @NotNull
    String ID;

    @ColumnInfo(name = "FLAG")
    String FLAG = "2";

    @ColumnInfo(name = "DATE_MODIFIED")
    String DATE_MODIFIED;

    @ColumnInfo(name = "JSON")
    String JSON;


    @NotNull
    public String getID() {
        return ID;
    }

    public void setID(@NotNull String ID) {
        this.ID = ID;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getDATE_MODIFIED() {
        return DATE_MODIFIED;
    }

    public void setDATE_MODIFIED(String DATE_MODIFIED) {
        this.DATE_MODIFIED = DATE_MODIFIED;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }
}

