
package com.sisindia.mysis.entity.OtherDuty_WithQrCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShiftDetail {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("SHIFT_NAME")
    @Expose
    private String sHIFTNAME;
    @SerializedName("SHIFT_DISCRIPTION")
    @Expose
    private String sHIFTDISCRIPTION;
    @SerializedName("UNIT_ID")
    @Expose
    private String uNITID;
    @SerializedName("UNIT_CODE")
    @Expose
    private String uNITCODE;
    @SerializedName("START_DATE")
    @Expose
    private String sTARTDATE;
    @SerializedName("START_TIME")
    @Expose
    private String sTARTTIME;
    @SerializedName("END_DATE")
    @Expose
    private String eNDDATE;
    @SerializedName("END_TIME")
    @Expose
    private String eNDTIME;
    @SerializedName("DUTY_HRS")
    @Expose
    private String dUTYHRS;
    @SerializedName("IS_TEMPORARY")
    @Expose
    private Boolean iSTEMPORARY;
    @SerializedName("ACTIVE_DAYS")
    @Expose
    private String aCTIVEDAYS;


    private boolean isSelected=false;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }


    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getSHIFTNAME() {
        return sHIFTNAME;
    }

    public void setSHIFTNAME(String sHIFTNAME) {
        this.sHIFTNAME = sHIFTNAME;
    }

    public String getSHIFTDISCRIPTION() {
        return sHIFTDISCRIPTION;
    }

    public void setSHIFTDISCRIPTION(String sHIFTDISCRIPTION) {
        this.sHIFTDISCRIPTION = sHIFTDISCRIPTION;
    }

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

    public String getSTARTDATE() {
        return sTARTDATE;
    }

    public void setSTARTDATE(String sTARTDATE) {
        this.sTARTDATE = sTARTDATE;
    }

    public String getSTARTTIME() {
        return sTARTTIME;
    }

    public void setSTARTTIME(String sTARTTIME) {
        this.sTARTTIME = sTARTTIME;
    }

    public String getENDDATE() {
        return eNDDATE;
    }

    public void setENDDATE(String eNDDATE) {
        this.eNDDATE = eNDDATE;
    }

    public String getENDTIME() {
        return eNDTIME;
    }

    public void setENDTIME(String eNDTIME) {
        this.eNDTIME = eNDTIME;
    }

    public String getDUTYHRS() {
        return dUTYHRS;
    }

    public void setDUTYHRS(String dUTYHRS) {
        this.dUTYHRS = dUTYHRS;
    }

    public Boolean getISTEMPORARY() {
        return iSTEMPORARY;
    }

    public void setISTEMPORARY(Boolean iSTEMPORARY) {
        this.iSTEMPORARY = iSTEMPORARY;
    }

    public String getACTIVEDAYS() {
        return aCTIVEDAYS;
    }

    public void setACTIVEDAYS(String aCTIVEDAYS) {
        this.aCTIVEDAYS = aCTIVEDAYS;
    }

}
