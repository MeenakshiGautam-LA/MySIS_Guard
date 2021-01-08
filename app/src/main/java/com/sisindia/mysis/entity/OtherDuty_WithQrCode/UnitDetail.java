
package com.sisindia.mysis.entity.OtherDuty_WithQrCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitDetail {

    @SerializedName("UNIT_ID")
    @Expose
    private String uNITID;
    @SerializedName("UNIT_CODE")
    @Expose
    private String uNITCODE;
    @SerializedName("UNIT_NAME")
    @Expose
    private String uNITNAME;
    @SerializedName("POST_ID")
    @Expose
    private String pOSTID;
    @SerializedName("POST_NAME")
    @Expose
    private String pOSTNAME;

    public String getUNITID() {
        return uNITID;
    }

    public void setUNITID(String uNITID) {
        this.uNITID = uNITID;
    }

    public String getUNITCODE() {
        return uNITCODE;
    }

    public void setUNITCODE(String uNITCODE) {
        this.uNITCODE = uNITCODE;
    }

    public String getUNITNAME() {
        return uNITNAME;
    }

    public void setUNITNAME(String uNITNAME) {
        this.uNITNAME = uNITNAME;
    }

    public String getPOSTID() {
        return pOSTID;
    }

    public void setPOSTID(String pOSTID) {
        this.pOSTID = pOSTID;
    }

    public String getPOSTNAME() {
        return pOSTNAME;
    }

    public void setPOSTNAME(String pOSTNAME) {
        this.pOSTNAME = pOSTNAME;
    }

}
