
package com.sisindia.mysis.GetWorkManagers.Model;

import android.annotation.SuppressLint;

import com.sisindia.mysis.entity.base.CSObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressLint("ParcelCreator")
public class EmployeeModel extends CSObject {
    @SerializedName("RegNo")
    @Expose
    private String regNo;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("Rank")
    @Expose
    private String rank;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("BranchCode")
    @Expose
    private String branchCode;
    @SerializedName("DoB")
    @Expose
    private String doB;
    @SerializedName("JoiningDt")
    @Expose
    private String joiningDt;
    @SerializedName("MOBILE")
    @Expose
    private String mOBILE;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Gender")
    @Expose
    private String gender;

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDoB() {
        return doB;
    }

    public void setDoB(String doB) {
        this.doB = doB;
    }

    public String getJoiningDt() {
        return joiningDt;
    }

    public void setJoiningDt(String joiningDt) {
        this.joiningDt = joiningDt;
    }

    public String getMOBILE() {
        return mOBILE;
    }

    public void setMOBILE(String mOBILE) {
        this.mOBILE = mOBILE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
