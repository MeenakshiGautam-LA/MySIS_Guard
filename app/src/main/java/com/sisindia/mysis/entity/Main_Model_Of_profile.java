package com.sisindia.mysis.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sisindia.mysis.entity.OtherDuty_WithQrCode.UnitDetail;

import java.util.List;

public class Main_Model_Of_profile {
    @SerializedName("ProfileDetail")
    @Expose
    private List<UserDetailModel> profileDetail = null;
    @SerializedName("UnitDetail")
    @Expose
    private List<UnitDetailModel> unitDetail = null;

    public List<UserDetailModel> getProfileDetail() {
        return profileDetail;
    }

    public void setProfileDetail(List<UserDetailModel> profileDetail) {
        this.profileDetail = profileDetail;
    }

    public List<UnitDetailModel> getUnitDetail() {
        return unitDetail;
    }

    public void setUnitDetail(List<UnitDetailModel> unitDetail) {
        this.unitDetail = unitDetail;
    }

}
