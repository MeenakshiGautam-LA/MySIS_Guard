package com.sisindia.mysis.Model;

import java.io.Serializable;

public class OtherUnitEmployeeDetailModel implements Serializable {
    private String UNIT_CODE;
    private String START_TIME;
    private String END_TIME;
    private String SHIFT_NAME;
    private String SHIFT_ID;
    private String REGNO;
    private String DUTY_POST_ID;
    private String DUTY_RANK;
    private String empName, unitName;
    private String other_Duty_post_Name;
    private String other_DutyRankName;
    private String otherDutyShiftName;
    private String otherDuty_RankSymbol;
    private String rosterDate;
    private String mobileNumber;
    private String empRank, empDoJ, empStatus;
    private byte[] picture;
    private String QR_Code;
    private String dutyHours, branchCode, postingUnit, postingUnitName, branchName, regionCode, regionName, dutyStatus;

    public String getQR_Code() {
        return QR_Code;
    }

    public void setQR_Code(String QR_Code) {
        this.QR_Code = QR_Code;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getEmpRank() {
        return empRank;
    }

    public void setEmpRank(String empRank) {
        this.empRank = empRank;
    }

    public String getEmpDoJ() {
        return empDoJ;
    }

    public void setEmpDoJ(String empDoJ) {
        this.empDoJ = empDoJ;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }


    public String getDutyHours() {
        return dutyHours;
    }

    public void setDutyHours(String dutyHours) {
        this.dutyHours = dutyHours;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getPostingUnit() {
        return postingUnit;
    }

    public void setPostingUnit(String postingUnit) {
        this.postingUnit = postingUnit;
    }

    public String getPostingUnitName() {
        return postingUnitName;
    }

    public void setPostingUnitName(String postingUnitName) {
        this.postingUnitName = postingUnitName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    public String getRosterDate() {
        return rosterDate;
    }

    public void setRosterDate(String rosterDate) {
        this.rosterDate = rosterDate;
    }


    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOtherDuty_RankSymbol() {
        return otherDuty_RankSymbol;
    }

    public void setOtherDuty_RankSymbol(String otherDuty_RankSymbol) {
        this.otherDuty_RankSymbol = otherDuty_RankSymbol;
    }

    public String getOther_Duty_post_Name() {
        return other_Duty_post_Name;
    }

    public void setOther_Duty_post_Name(String other_Duty_post_Name) {
        this.other_Duty_post_Name = other_Duty_post_Name;
    }

    public String getOther_DutyRankName() {
        return other_DutyRankName;
    }

    public void setOther_DutyRankName(String other_DutyRankName) {
        this.other_DutyRankName = other_DutyRankName;
    }

    public String getOtherDutyShiftName() {
        return otherDutyShiftName;
    }

    public void setOtherDutyShiftName(String otherDutyShiftName) {
        this.otherDutyShiftName = otherDutyShiftName;
    }

    public String getREGNO() {
        return REGNO;
    }

    public void setREGNO(String REGNO) {
        this.REGNO = REGNO;
    }

    public String getSHIFT_ID() {
        return SHIFT_ID;
    }

    public void setSHIFT_ID(String SHIFT_ID) {
        this.SHIFT_ID = SHIFT_ID;
    }


    public String getDUTY_POST_ID() {
        return DUTY_POST_ID;
    }

    public void setDUTY_POST_ID(String DUTY_POST_ID) {
        this.DUTY_POST_ID = DUTY_POST_ID;
    }

    public String getDUTY_RANK() {
        return DUTY_RANK;
    }

    public void setDUTY_RANK(String DUTY_RANK) {
        this.DUTY_RANK = DUTY_RANK;
    }


    public String getUNIT_CODE() {
        return UNIT_CODE;
    }

    public void setUNIT_CODE(String UNIT_CODE) {
        this.UNIT_CODE = UNIT_CODE;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getSHIFT_NAME() {
        return SHIFT_NAME;
    }

    public void setSHIFT_NAME(String SHIFT_NAME) {
        this.SHIFT_NAME = SHIFT_NAME;
    }

}
