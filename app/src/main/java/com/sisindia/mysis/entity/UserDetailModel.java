
package com.sisindia.mysis.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sisindia.mysis.utils.Constants;


@Entity(tableName = Constants.USER_DETAIL)
public class UserDetailModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    private String ID;
    @ColumnInfo(name = "USERNAME")
    @SerializedName("UserName")
    @Expose
    private String userName;
    @ColumnInfo(name = "UNIT_CODE")
    @SerializedName("UnitCode")
    private String UNIT_CODE;

    @ColumnInfo(name = "COMPANY_NAME")
    @SerializedName("COMPANY_NAME")
    @Expose
    private String COMPANY_NAME;

    @NonNull
    public String getID() {
        return ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }

    @ColumnInfo(name = "REGNO")
    @SerializedName("RegNo")
    @Expose
    private String regNo;
    @ColumnInfo(name = "FULLNAME")
    @SerializedName("Name")
    @Expose
    private String name;
    @ColumnInfo(name = "PHONE")
    @SerializedName("Phone")
    @Expose
    private String phone;
    @ColumnInfo(name = "EMAIL")
    @SerializedName("Email")
    @Expose
    private String email;
    @ColumnInfo(name = "DESIGNATION")
    @SerializedName("Designation")
    @Expose
    private String designation;
    @ColumnInfo(name = "BRANCH_CODE")
    @SerializedName("BranchCode")
    @Expose
    private String branchCode;
    @ColumnInfo(name = "BRANCH_NAME")
    @SerializedName("BranchName")
    @Expose
    private String branchName;
    @ColumnInfo(name = "EXPIRY_DATE")
    @SerializedName("ExpiryDate")
    @Expose
    private String expiryDate;

    @ColumnInfo(name = "PROFILE_PICTURE")
    @SerializedName("Picture")
    private String profile_Picture="";

    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("DATE_MODIFIED")
    @Expose
    private String dATEMODIFIED;
    @ColumnInfo(name = "JSON")
    private String save_JSON;
    @ColumnInfo(name = "EMP_RANK")
    @SerializedName("Rank")
    @Expose
    private String dutyRank;

    @ColumnInfo(name = "USER_RATING")
    @SerializedName("USER_RATING")
    private int user_rating=0;

    @ColumnInfo(name = "USER_BADGES")
    @SerializedName("USER_BADGES")
    private int USER_BADGES=0;
    public String getProfile_Picture() {
        return profile_Picture;
    }

    public void setProfile_Picture(String profile_Picture) {
        this.profile_Picture = profile_Picture;
    }

    @ColumnInfo(name = "PENDING_APPROVAL")
    @SerializedName("PendingApproval")
    @Expose
    private String pendingStatus = "0";

    @ColumnInfo(name = "CHANGE_CONTACT_NO")
    @SerializedName("ChangedMobile")
    @Expose
    private String change_ContactNo;
    @ColumnInfo(name = "FLAG")
    private String flag = "0";
    @ColumnInfo(name = "SUPER_VISIOR_NAME")
    @SerializedName("Sup_Name")
    private String superVisorName;
    @ColumnInfo(name = "SUPER_VISIOR_PHONE")
    @SerializedName("Sup_Mobile")
    private String superVisorPhone;
    @ColumnInfo(name = "SUPER_VISIOR_EMAIL")
    @SerializedName("Sup_Email")
    private String superVisorEmail;
    @ColumnInfo(name = "supRegNo")
    @SerializedName("Sup_RegNo")
    @Expose
    private String supRegNo;
    @ColumnInfo(name = "supPicture")
    @SerializedName("Sup_Picture")
    @Expose
    private String supPicture;
    @ColumnInfo(name = "aIRegNo")
    @SerializedName("AI_RegNo")
    @Expose
    private String aiRegNo;

    @ColumnInfo(name = "aIName")
    @SerializedName("AI_Name")
    @Expose
    private String aiName;

    @ColumnInfo(name = "aIEmail")
    @SerializedName("AI_Email")
    @Expose
    private String aiEmail;
    @ColumnInfo(name = "aIMobile")
    @SerializedName("AI_Mobile")
    @Expose
    private String aiMobile;
    @ColumnInfo(name = "AI_Picture")
    @SerializedName("AI_Picture")
    @Expose
    private String aiPicture;
    @ColumnInfo(name = "HELPLINE_NO")
    @SerializedName("Customer_Support_No")
    private String Customer_Support_No;

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public void setCOMPANY_NAME(String COMPANY_NAME) {
        this.COMPANY_NAME = COMPANY_NAME;
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCustomer_Support_No() {
        return Customer_Support_No;
    }

    public void setCustomer_Support_No(String customer_Support_No) {
        Customer_Support_No = customer_Support_No;
    }

    public String getdATEMODIFIED() {
        return dATEMODIFIED;
    }

    public void setdATEMODIFIED(String dATEMODIFIED) {
        this.dATEMODIFIED = dATEMODIFIED;
    }

    public String getPendingStatus() {
        return pendingStatus;
    }

    public int getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(int user_rating) {
        this.user_rating = user_rating;
    }

    public int getUSER_BADGES() {
        return USER_BADGES;
    }

    public void setUSER_BADGES(int USER_BADGES) {
        this.USER_BADGES = USER_BADGES;
    }

    public void setPendingStatus(String pendingStatus) {
        this.pendingStatus = pendingStatus;
    }

    public String getSuperVisorName() {
        return superVisorName;
    }

    public void setSuperVisorName(String superVisorName) {
        this.superVisorName = superVisorName;
    }

    public String getSuperVisorPhone() {
        return superVisorPhone;
    }

    public void setSuperVisorPhone(String superVisorPhone) {
        this.superVisorPhone = superVisorPhone;
    }

    public String getSuperVisorEmail() {
        return superVisorEmail;
    }

    public void setSuperVisorEmail(String superVisorEmail) {
        this.superVisorEmail = superVisorEmail;
    }

    public String getChange_ContactNo() {
        return change_ContactNo;
    }

    public void setChange_ContactNo(String change_ContactNo) {
        this.change_ContactNo = change_ContactNo;
    }


    public String getDutyRank() {
        return dutyRank;
    }

    public String getAiRegNo() {
        return aiRegNo;
    }

    public void setAiRegNo(String aiRegNo) {
        this.aiRegNo = aiRegNo;
    }

    public String getAiName() {
        return aiName;
    }

    public void setAiName(String aiName) {
        this.aiName = aiName;
    }

    public String getAiEmail() {
        return aiEmail;
    }

    public void setAiEmail(String aiEmail) {
        this.aiEmail = aiEmail;
    }

    public String getAiMobile() {
        return aiMobile;
    }

    public void setAiMobile(String aiMobile) {
        this.aiMobile = aiMobile;
    }

    public String getAiPicture() {
        return aiPicture;
    }

    public void setAiPicture(String aiPicture) {
        this.aiPicture = aiPicture;
    }

    public void setDutyRank(String dutyRank) {
        this.dutyRank = dutyRank;
    }


    public String getSave_JSON() {
        return save_JSON;
    }

    public void setSave_JSON(String save_JSON) {
        this.save_JSON = save_JSON;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSupRegNo() {
        return supRegNo;
    }

    public void setSupRegNo(String supRegNo) {
        this.supRegNo = supRegNo;
    }

    public String getSupPicture() {
        return supPicture;
    }

    public void setSupPicture(String supPicture) {
        this.supPicture = supPicture;
    }

    public String getDATEMODIFIED() {
        return dATEMODIFIED;
    }

    public void setDATEMODIFIED(String dATEMODIFIED) {
        this.dATEMODIFIED = dATEMODIFIED;
    }

    public String getUNIT_CODE() {
        return UNIT_CODE;
    }

    public void setUNIT_CODE(String UNIT_CODE) {
        this.UNIT_CODE = UNIT_CODE;
    }


}
