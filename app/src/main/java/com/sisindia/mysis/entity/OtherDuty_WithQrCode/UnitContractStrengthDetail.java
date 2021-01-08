
package com.sisindia.mysis.entity.OtherDuty_WithQrCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitContractStrengthDetail {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("UNIT_CODE")
    @Expose
    private String uNITCODE;
    @SerializedName("SYMBOL")
    @Expose
    private String SYMBOL;
    @SerializedName("SERVICE_CODE")
    @Expose
    private String sERVICECODE;
    @SerializedName("DUTY_HRS")
    @Expose
    private Integer dUTYHRS;
    @SerializedName("STRENGTH")
    @Expose
    private Double sTRENGTH;
    @SerializedName("RESTRICT_EMP_RANK_DUTY_RANK_SAME")
    @Expose
    private Boolean rESTRICTEMPRANKDUTYRANKSAME;
    @SerializedName("IS_NON_BILLING")
    @Expose
    private Boolean iSNONBILLING;
    @SerializedName("IS_TEMPORARY")
    @Expose
    private Boolean iSTEMPORARY;
    @SerializedName("START_DATE")
    @Expose
    private String sTARTDATE;
    @SerializedName("SERVICE_NAME")
    @Expose
    private String SERVICE_NAME;
    @SerializedName("END_DATE")
    @Expose
    private String eNDDATE;
    @SerializedName("OT_APPLICABLE")
    @Expose
    private Boolean oTAPPLICABLE;
    @SerializedName("MAX_OT_HRS")
    @Expose
    private Integer mAXOTHRS;
    @SerializedName("IS_WEEKLY_OFF")
    @Expose
    private Boolean iSWEEKLYOFF;
    @SerializedName("WEEKLY_OFF_TYPE")
    @Expose
    private String wEEKLYOFFTYPE;
    @SerializedName("FIX_WEEKLY_OFF_DAYS")
    @Expose
    private String fIXWEEKLYOFFDAYS;
    @SerializedName("REASON_FOR_ADD_EDIT")
    @Expose
    private String rEASONFORADDEDIT;
    @SerializedName("REASON_FOR_ADD_EDIT_OTHER")
    @Expose
    private String rEASONFORADDEDITOTHER;
    public String getSERVICE_NAME() {
        return SERVICE_NAME;
    }

    public void setSERVICE_NAME(String SERVICE_NAME) {
        this.SERVICE_NAME = SERVICE_NAME;
    }

    public String getSYMBOL() {
        return SYMBOL;
    }

    public void setSYMBOL(String SYMBOL) {
        this.SYMBOL = SYMBOL;
    }

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

    public String getSERVICECODE() {
        return sERVICECODE;
    }

    public void setSERVICECODE(String sERVICECODE) {
        this.sERVICECODE = sERVICECODE;
    }

    public Integer getDUTYHRS() {
        return dUTYHRS;
    }

    public void setDUTYHRS(Integer dUTYHRS) {
        this.dUTYHRS = dUTYHRS;
    }

    public Double getSTRENGTH() {
        return sTRENGTH;
    }

    public void setSTRENGTH(Double sTRENGTH) {
        this.sTRENGTH = sTRENGTH;
    }

    public Boolean getRESTRICTEMPRANKDUTYRANKSAME() {
        return rESTRICTEMPRANKDUTYRANKSAME;
    }

    public void setRESTRICTEMPRANKDUTYRANKSAME(Boolean rESTRICTEMPRANKDUTYRANKSAME) {
        this.rESTRICTEMPRANKDUTYRANKSAME = rESTRICTEMPRANKDUTYRANKSAME;
    }

    public Boolean getISNONBILLING() {
        return iSNONBILLING;
    }

    public void setISNONBILLING(Boolean iSNONBILLING) {
        this.iSNONBILLING = iSNONBILLING;
    }

    public Boolean getISTEMPORARY() {
        return iSTEMPORARY;
    }

    public void setISTEMPORARY(Boolean iSTEMPORARY) {
        this.iSTEMPORARY = iSTEMPORARY;
    }

    public String getSTARTDATE() {
        return sTARTDATE;
    }

    public void setSTARTDATE(String sTARTDATE) {
        this.sTARTDATE = sTARTDATE;
    }

    public String getENDDATE() {
        return eNDDATE;
    }

    public void setENDDATE(String eNDDATE) {
        this.eNDDATE = eNDDATE;
    }

    public Boolean getOTAPPLICABLE() {
        return oTAPPLICABLE;
    }

    public void setOTAPPLICABLE(Boolean oTAPPLICABLE) {
        this.oTAPPLICABLE = oTAPPLICABLE;
    }

    public Integer getMAXOTHRS() {
        return mAXOTHRS;
    }

    public void setMAXOTHRS(Integer mAXOTHRS) {
        this.mAXOTHRS = mAXOTHRS;
    }

    public Boolean getISWEEKLYOFF() {
        return iSWEEKLYOFF;
    }

    public void setISWEEKLYOFF(Boolean iSWEEKLYOFF) {
        this.iSWEEKLYOFF = iSWEEKLYOFF;
    }

    public String getWEEKLYOFFTYPE() {
        return wEEKLYOFFTYPE;
    }

    public void setWEEKLYOFFTYPE(String wEEKLYOFFTYPE) {
        this.wEEKLYOFFTYPE = wEEKLYOFFTYPE;
    }

    public String getFIXWEEKLYOFFDAYS() {
        return fIXWEEKLYOFFDAYS;
    }

    public void setFIXWEEKLYOFFDAYS(String fIXWEEKLYOFFDAYS) {
        this.fIXWEEKLYOFFDAYS = fIXWEEKLYOFFDAYS;
    }

    public String getREASONFORADDEDIT() {
        return rEASONFORADDEDIT;
    }

    public void setREASONFORADDEDIT(String rEASONFORADDEDIT) {
        this.rEASONFORADDEDIT = rEASONFORADDEDIT;
    }

    public String getREASONFORADDEDITOTHER() {
        return rEASONFORADDEDITOTHER;
    }

    public void setREASONFORADDEDITOTHER(String rEASONFORADDEDITOTHER) {
        this.rEASONFORADDEDITOTHER = rEASONFORADDEDITOTHER;
    }

}
