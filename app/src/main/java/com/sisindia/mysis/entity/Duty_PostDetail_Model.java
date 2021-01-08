package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.DUTY_POST_DETAIL)
public class Duty_PostDetail_Model extends CSObject {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    @Expose
    private String iD;
    @ColumnInfo(name = "POST_NAME")
    @SerializedName("POST_NAME")
    @Expose
    private String pOSTNAME;

    @ColumnInfo(name = "QR_CODE")
    @SerializedName("QR_ID")
    @Expose
    private String QRCODE;
    @ColumnInfo(name = "LANDMARK")
    @SerializedName("LANDMARK")
    @Expose
    private String lANDMARK;

    @ColumnInfo(name = "ADDRESS")
    @SerializedName("ADDRESS")
    @Expose
    private String aDDRESS;
    @ColumnInfo(name = "GEO_LOCATION")
    @SerializedName("GEO_LOCATION")
    @Expose
    private String gEOLOCATION;
    @ColumnInfo(name = "GEO_ADDRESS")
    @SerializedName("GEO_ADDRESS")
    @Expose
    private String gEOADDRESS;

    @ColumnInfo(name = "STRENGTH")
    @SerializedName("STRENGTH")
    @Expose
    private Integer sTRENGTH;
    @ColumnInfo(name = "UNIT_ID")
    @SerializedName("UNIT_ID")
    @Expose
    private String uNITID;
    @ColumnInfo(name = "UNIT_CODE")
    @SerializedName("UNIT_CODE")
    @Expose
    private String uNITCODE;
    @ColumnInfo(name = "IS_TEMPORARY")
    @SerializedName("IS_TEMPORARY")
    @Expose
    private Boolean iSTEMPORARY;
    @ColumnInfo(name = "IS_CRITICAL")
    @SerializedName("IS_CRITICAL")
    @Expose
    private Integer iSCRITICAL;
    @ColumnInfo(name = "START_DATE")
    @SerializedName("START_DATE")
    @Expose
    private String sTARTDATE;
    @ColumnInfo(name = "END_DATE")
    @SerializedName("END_DATE")
    @Expose
    private String eNDDATE;
    @SerializedName("CREATED_BY")
    @Expose
    private String cREATEDBY;
    @ColumnInfo(name = "MODIFIED_BY")
    @SerializedName("MODIFIED_BY")
    @Expose
    private String mODIFIEDBY;
    @ColumnInfo(name = "CREATED_ON")
    @SerializedName("CREATED_ON")
    @Expose
    private String cREATEDON;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    @Expose
    private String dATEMODIFIED;
    @ColumnInfo(name = "DELETED")
    @SerializedName("DELETED")
    @Expose
    private Integer dELETED;
    @ColumnInfo(name = "SHIFT_ID")
    @SerializedName("SHIFT_ID")
    @Expose
    private String sHIFTID;

    @ColumnInfo(name = "FLAG")
    private String flag = "0";
    @ColumnInfo(name = "JSON")
    private String json;

    public String getQRCODE() {
        return QRCODE;
    }

    public void setQRCODE(String QRCODE) {
        this.QRCODE = QRCODE;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getPOSTNAME() {
        return pOSTNAME;
    }

    public void setPOSTNAME(String pOSTNAME) {
        this.pOSTNAME = pOSTNAME;
    }

    public String getLANDMARK() {
        return lANDMARK;
    }

    public void setLANDMARK(String lANDMARK) {
        this.lANDMARK = lANDMARK;
    }

    public String getADDRESS() {
        return aDDRESS;
    }

    public void setADDRESS(String aDDRESS) {
        this.aDDRESS = aDDRESS;
    }

    public String getGEOLOCATION() {
        return gEOLOCATION;
    }

    public void setGEOLOCATION(String gEOLOCATION) {
        this.gEOLOCATION = gEOLOCATION;
    }

    public String getGEOADDRESS() {
        return gEOADDRESS;
    }

    public void setGEOADDRESS(String gEOADDRESS) {
        this.gEOADDRESS = gEOADDRESS;
    }

    public Integer getSTRENGTH() {
        return sTRENGTH;
    }

    public void setSTRENGTH(Integer sTRENGTH) {
        this.sTRENGTH = sTRENGTH;
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

    public Boolean getISTEMPORARY() {
        return iSTEMPORARY;
    }

    public void setISTEMPORARY(Boolean iSTEMPORARY) {
        this.iSTEMPORARY = iSTEMPORARY;
    }

    public Integer getISCRITICAL() {
        return iSCRITICAL;
    }

    public void setISCRITICAL(Integer iSCRITICAL) {
        this.iSCRITICAL = iSCRITICAL;
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

    public String getCREATEDBY() {
        return cREATEDBY;
    }

    public void setCREATEDBY(String cREATEDBY) {
        this.cREATEDBY = cREATEDBY;
    }

    public String getMODIFIEDBY() {
        return mODIFIEDBY;
    }

    public void setMODIFIEDBY(String mODIFIEDBY) {
        this.mODIFIEDBY = mODIFIEDBY;
    }

    public String getCREATEDON() {
        return cREATEDON;
    }

    public void setCREATEDON(String cREATEDON) {
        this.cREATEDON = cREATEDON;
    }

    public String getDATEMODIFIED() {
        return dATEMODIFIED;
    }

    public void setDATEMODIFIED(String dATEMODIFIED) {
        this.dATEMODIFIED = dATEMODIFIED;
    }

    public Integer getDELETED() {
        return dELETED;
    }

    public void setDELETED(Integer dELETED) {
        this.dELETED = dELETED;
    }

    public String getSHIFTID() {
        return sHIFTID;
    }

    public void setSHIFTID(String sHIFTID) {
        this.sHIFTID = sHIFTID;
    }

}
