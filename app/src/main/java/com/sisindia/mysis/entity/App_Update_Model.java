package com.sisindia.mysis.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.sisindia.mysis.utils.Constants;

@Entity(tableName = Constants.APP_UPDATE_TABLE)
public class App_Update_Model {
    @PrimaryKey
    @NonNull
    private String VER_ID;

    @ColumnInfo(name = "APP_TYPE")
    @SerializedName("APP_TYPE")
    private String APP_TYPE;

    @ColumnInfo(name = "FILE_VERSION")
    @SerializedName("FILE_VERSION")
    private String FILE_VERSION;

    @ColumnInfo(name = "NAME")
    @SerializedName("NAME")
    private String NAME;

    @ColumnInfo(name = "DESCRIPTION")
    @SerializedName("DESCRIPTION")
    private String DESCRIPTION;

    @ColumnInfo(name = "FILE_URL")
    @SerializedName("FILE_URL")
    private String FILE_URL;

    @ColumnInfo(name = "VERSION_CODE")
    @SerializedName("VERSION_CODE")
    private String VERSION_CODE;

    @ColumnInfo(name = "FLAG")
    private String FLAG;

    @NonNull
    public String getVER_ID() {
        return VER_ID;
    }

    public void setVER_ID(@NonNull String VER_ID) {
        this.VER_ID = VER_ID;
    }

    public String getAPP_TYPE() {
        return APP_TYPE;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public void setAPP_TYPE(String APP_TYPE) {
        this.APP_TYPE = APP_TYPE;
    }

    public String getFILE_VERSION() {
        return FILE_VERSION;
    }

    public void setFILE_VERSION(String FILE_VERSION) {
        this.FILE_VERSION = FILE_VERSION;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getFILE_URL() {
        return FILE_URL;
    }

    public void setFILE_URL(String FILE_URL) {
        this.FILE_URL = FILE_URL;
    }

    public String getVERSION_CODE() {
        return VERSION_CODE;
    }

    public void setVERSION_CODE(String VERSION_CODE) {
        this.VERSION_CODE = VERSION_CODE;
    }
}
