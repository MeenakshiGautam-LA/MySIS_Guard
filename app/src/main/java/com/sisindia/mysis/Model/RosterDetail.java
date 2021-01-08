
package com.sisindia.mysis.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RosterDetail implements Serializable {
    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("UNIT_CODE")
    @Expose
    private String UNITCODE;
    @SerializedName("UNIT_NAME")
    @Expose
    private String UNITNAME;
    @SerializedName("REGNO")
    @Expose
    private String REGNO;
    @SerializedName("SHIFT_ID")
    @Expose
    private String SHIFTID;
    @SerializedName("SHIFT_NAME")
    @Expose
    private String SHIFTNAME;
    @SerializedName("DUTY_POST_ID")
    @Expose
    private String DUTYPOSTID;
    @SerializedName("DUTY_POST_NAME")
    @Expose
    private String DUTYPOSTNAME;
    @SerializedName("QR_ID")
    @Expose
    private String QRID;
    @SerializedName("DUTY_RANK")
    @Expose
    private String DUTYRANK;
    @SerializedName("DUTY_RANK_NAME")
    @Expose
    private String DUTYRANKNAME;
    @SerializedName("DUTY_SYMBOL")
    @Expose
    private String DUTYSYMBOL;
    @SerializedName("ROSTER_DATE")
    @Expose
    private String ROSTERDATE;
    @SerializedName("SHIFT_START_TIME")
    @Expose
    private String SHIFTSTARTTIME;
    @SerializedName("SHIFT_END_TIME")
    @Expose
    private String SHIFTENDTIME;
    @SerializedName("DUTY_HRS")
    @Expose
    private String DUTYHRS;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUNITCODE() {
        return UNITCODE;
    }

    public void setUNITCODE(String UNITCODE) {
        this.UNITCODE = UNITCODE;
    }

    public String getUNITNAME() {
        return UNITNAME;
    }

    public void setUNITNAME(String UNITNAME) {
        this.UNITNAME = UNITNAME;
    }

    public String getREGNO() {
        return REGNO;
    }

    public void setREGNO(String REGNO) {
        this.REGNO = REGNO;
    }

    public String getSHIFTID() {
        return SHIFTID;
    }

    public void setSHIFTID(String SHIFTID) {
        this.SHIFTID = SHIFTID;
    }

    public String getSHIFTNAME() {
        return SHIFTNAME;
    }

    public void setSHIFTNAME(String SHIFTNAME) {
        this.SHIFTNAME = SHIFTNAME;
    }

    public String getDUTYPOSTID() {
        return DUTYPOSTID;
    }

    public void setDUTYPOSTID(String DUTYPOSTID) {
        this.DUTYPOSTID = DUTYPOSTID;
    }

    public String getDUTYPOSTNAME() {
        return DUTYPOSTNAME;
    }

    public void setDUTYPOSTNAME(String DUTYPOSTNAME) {
        this.DUTYPOSTNAME = DUTYPOSTNAME;
    }

    public String getQRID() {
        return QRID;
    }

    public void setQRID(String QRID) {
        this.QRID = QRID;
    }

    public String getDUTYRANK() {
        return DUTYRANK;
    }

    public void setDUTYRANK(String DUTYRANK) {
        this.DUTYRANK = DUTYRANK;
    }

    public String getDUTYRANKNAME() {
        return DUTYRANKNAME;
    }

    public void setDUTYRANKNAME(String DUTYRANKNAME) {
        this.DUTYRANKNAME = DUTYRANKNAME;
    }

    public String getDUTYSYMBOL() {
        return DUTYSYMBOL;
    }

    public void setDUTYSYMBOL(String DUTYSYMBOL) {
        this.DUTYSYMBOL = DUTYSYMBOL;
    }

    public String getROSTERDATE() {
        return ROSTERDATE;
    }

    public void setROSTERDATE(String ROSTERDATE) {
        this.ROSTERDATE = ROSTERDATE;
    }

    public String getSHIFTSTARTTIME() {
        return SHIFTSTARTTIME;
    }

    public void setSHIFTSTARTTIME(String SHIFTSTARTTIME) {
        this.SHIFTSTARTTIME = SHIFTSTARTTIME;
    }

    public String getSHIFTENDTIME() {
        return SHIFTENDTIME;
    }

    public void setSHIFTENDTIME(String SHIFTENDTIME) {
        this.SHIFTENDTIME = SHIFTENDTIME;
    }

    public String getDUTYHRS() {
        return DUTYHRS;
    }

    public void setDUTYHRS(String DUTYHRS) {
        this.DUTYHRS = DUTYHRS;
    }



}
