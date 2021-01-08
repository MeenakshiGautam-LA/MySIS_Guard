
package com.sisindia.mysis.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = Constants.SERVICEINFO)
public class ServiceInfoDetail {
    @PrimaryKey
    @NonNull
@ColumnInfo(name = "ID")
    @SerializedName("ID")
    @Expose
    private String iD;
    @ColumnInfo(name = "SERVICE_CODE")
    @SerializedName("SERVICE_CODE")
    @Expose
    private String sERVICECODE;
    @ColumnInfo(name = "SERVICE_NAME")
    @SerializedName("SERVICE_NAME")
    @Expose
    private String sERVICENAME;
    @ColumnInfo(name = "SYMBOL")
    @SerializedName("SYMBOL")
    @Expose
    private String sYMBOL;
    @ColumnInfo(name = "TEMP_EMP_ALLOWED")
    @SerializedName("TEMP_EMP_ALLOWED")
    @Expose
    private Boolean tEMPEMPALLOWED;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    @Expose
    private String dATEMODIFIED;
    @ColumnInfo(name = "FLAG")
private String flag="0";

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    @ColumnInfo(name = "JSON")
private String JSON;
    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getSERVICECODE() {
        return sERVICECODE;
    }

    public void setSERVICECODE(String sERVICECODE) {
        this.sERVICECODE = sERVICECODE;
    }

    public String getSERVICENAME() {
        return sERVICENAME;
    }

    public void setSERVICENAME(String sERVICENAME) {
        this.sERVICENAME = sERVICENAME;
    }

    public String getSYMBOL() {
        return sYMBOL;
    }

    public void setSYMBOL(String sYMBOL) {
        this.sYMBOL = sYMBOL;
    }

    public Boolean getTEMPEMPALLOWED() {
        return tEMPEMPALLOWED;
    }

    public void setTEMPEMPALLOWED(Boolean tEMPEMPALLOWED) {
        this.tEMPEMPALLOWED = tEMPEMPALLOWED;
    }

    public String getDATEMODIFIED() {
        return dATEMODIFIED;
    }

    public void setDATEMODIFIED(String dATEMODIFIED) {
        this.dATEMODIFIED = dATEMODIFIED;
    }

}
