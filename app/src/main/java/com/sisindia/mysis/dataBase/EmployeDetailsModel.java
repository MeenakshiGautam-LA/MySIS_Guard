
package com.sisindia.mysis.dataBase;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.EMPLOYEEDETAILS_TABLE)
public class EmployeDetailsModel extends CSObject implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    @Expose
    private String ID;

    @ColumnInfo(name = "REGNO")
    @SerializedName("REGNO")
    @Expose
    private String REGNO;
    @ColumnInfo(name = "EMP_NAME")

    @SerializedName("EMP_NAME")
    @Expose
    private String EMPNAME;
    @ColumnInfo(name = "GENDER")
    @SerializedName("Gender")
    @Expose
    private String gender;
    @ColumnInfo(name = "RANK")
    @SerializedName("RANK")
    @Expose
    private String RANK;
    @ColumnInfo(name = "UNIT_CODE")
    @SerializedName("UNIT_CODE")
    @Expose
    private String UNITCODE;
    @ColumnInfo(name = "BILL_TYPE")
    @SerializedName("BILL_TYPE")
    @Expose
    private String BILLTYPE;
    @ColumnInfo(name = "DATE_OF_BIRTH")
    @SerializedName("DATE_OF_BIRTH")
    @Expose
    private String DATEOFBIRTH;
    @ColumnInfo(name = "JOINING_DATE")
    @SerializedName("JOINING_DATE")
    @Expose
    private String JOININGDATE;
    @ColumnInfo(name = "MOBILE")
    @SerializedName("MOBILE")
    @Expose
    private String MOBILE;

    @ColumnInfo(name = "IsActive")
    @SerializedName("IsActive")
    private int IsActive;

    @ColumnInfo(name = "EMAIL")
    @SerializedName("EMAIL")
    @Expose
    private String EMAIL;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    @Expose
    private String DATEMODIFIED;

    @ColumnInfo(name = "PICTURE")
    @SerializedName("PROFILE_IMAGE_URL")
    @Expose
    private String PICTURE;

    @ColumnInfo(name = "isSelected")
    private Boolean isSelected;
    @ColumnInfo(name = "BANK_IFSC")
    @SerializedName("BankIfscCode")
    @Expose
    private String bankIfscCode;

    @ColumnInfo(name = "BANK_NAME")
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @ColumnInfo(name = "BANK_ADDRESS")
    @SerializedName("BankAddress")
    @Expose
    private String bankAddress;
    @ColumnInfo(name = "ACCOUNT_NUMBER")
    @SerializedName("AccountNo")
    @Expose
    private String bankAccountNumber="";
    @ColumnInfo(name = "BANK_LOGO")
    @SerializedName("BankLogo")
    @Expose
    private String bankLogo;
    @ColumnInfo(name = "FLAG")
    private String flag = "0";
    @ColumnInfo(name = "JSON")
    private String json;
    @ColumnInfo(name = "TEMP_SYMBOL")
    private String TEMP_SYMBOL;
    @ColumnInfo(name = "AUTH_STRENGTH")
    private String AUTH_STRENGTH;
    @ColumnInfo(name = "EMP_STRENGTH")
    private String EMP_STRENGTH;

    @ColumnInfo(name = "IsBankDetailUpdated")
    private String IsBankDetailUpdated;
    @ColumnInfo(name = "UniformKitExpiryDate")
    private String UniformKitExpiryDate;

    @ColumnInfo(name = "GunLicenceExpiryDate")
    private String GunLicenceExpiryDate;

    @ColumnInfo(name = "DLExpiryDate")
    private String DLExpiryDate;

    @ColumnInfo(name = "IsUanNoUpdated")
    private String IsUanNoUpdated;

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

    public String getEMPNAME() {
        return EMPNAME;
    }

    public void setEMPNAME(String EMPNAME) {
        this.EMPNAME = EMPNAME;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRANK() {
        return RANK;
    }

    public void setRANK(String RANK) {
        this.RANK = RANK;
    }

    public String getUNITCODE() {
        return UNITCODE;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public void setUNITCODE(String UNITCODE) {
        this.UNITCODE = UNITCODE;
    }

    public String getBILLTYPE() {
        return BILLTYPE;
    }

    public void setBILLTYPE(String BILLTYPE) {
        this.BILLTYPE = BILLTYPE;
    }

    public String getDATEOFBIRTH() {
        return DATEOFBIRTH;
    }

    public void setDATEOFBIRTH(String DATEOFBIRTH) {
        this.DATEOFBIRTH = DATEOFBIRTH;
    }

    public String getJOININGDATE() {
        return JOININGDATE;
    }

    public void setJOININGDATE(String JOININGDATE) {
        this.JOININGDATE = JOININGDATE;
    }

    public String getPICTURE() {
        return PICTURE;
    }

    public void setPICTURE(String PICTURE) {
        this.PICTURE = PICTURE;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getDATEMODIFIED() {
        return DATEMODIFIED;
    }

    public void setDATEMODIFIED(String DATEMODIFIED) {
        this.DATEMODIFIED = DATEMODIFIED;
    }



    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getBankIfscCode() {
        return bankIfscCode;
    }

    public void setBankIfscCode(String bankIfscCode) {
        this.bankIfscCode = bankIfscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTEMP_SYMBOL() {
        return TEMP_SYMBOL;
    }

    public void setTEMP_SYMBOL(String TEMP_SYMBOL) {
        this.TEMP_SYMBOL = TEMP_SYMBOL;
    }

    public String getAUTH_STRENGTH() {
        return AUTH_STRENGTH;
    }

    public void setAUTH_STRENGTH(String AUTH_STRENGTH) {
        this.AUTH_STRENGTH = AUTH_STRENGTH;
    }

    public String getEMP_STRENGTH() {
        return EMP_STRENGTH;
    }

    public void setEMP_STRENGTH(String EMP_STRENGTH) {
        this.EMP_STRENGTH = EMP_STRENGTH;
    }

    public String getIsBankDetailUpdated() {
        return IsBankDetailUpdated;
    }

    public void setIsBankDetailUpdated(String isBankDetailUpdated) {
        IsBankDetailUpdated = isBankDetailUpdated;
    }

    public String getUniformKitExpiryDate() {
        return UniformKitExpiryDate;
    }

    public void setUniformKitExpiryDate(String uniformKitExpiryDate) {
        UniformKitExpiryDate = uniformKitExpiryDate;
    }

    public String getGunLicenceExpiryDate() {
        return GunLicenceExpiryDate;
    }

    public void setGunLicenceExpiryDate(String gunLicenceExpiryDate) {
        GunLicenceExpiryDate = gunLicenceExpiryDate;
    }

    public String getDLExpiryDate() {
        return DLExpiryDate;
    }

    public void setDLExpiryDate(String DLExpiryDate) {
        this.DLExpiryDate = DLExpiryDate;
    }

    public String getIsUanNoUpdated() {
        return IsUanNoUpdated;
    }

    public void setIsUanNoUpdated(String isUanNoUpdated) {
        IsUanNoUpdated = isUanNoUpdated;
    }
}
