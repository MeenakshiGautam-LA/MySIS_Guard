
package com.sisindia.mysis.entity.OtherDuty_WithQrCode;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Other_QR_CodeMainModel {

    @SerializedName("UnitDetail")
    @Expose
    private List<UnitDetail> unitDetail = null;

 /*   @SerializedName("EmployeeMaster")
    @Expose
    private List<EmployeeDetail> employeeDetailList = null;*/

   /* @SerializedName("RosterDetail")
    @Expose
    private List<Object> rosterDetail = null;*/
    @SerializedName("ShiftDetail")
    @Expose
    private List<ShiftDetail> shiftDetail = null;

//    @SerializedName("UnitContractStrengthDetail")
//    @Expose
//    private List<UnitContractStrengthDetail> unitContractStrengthDetail = null;

   /* public List<EmployeeDetail> getEmployeeDetailList() {
        return employeeDetailList;
    }

    public void setEmployeeDetailList(List<EmployeeDetail> employeeDetailList) {
        this.employeeDetailList = employeeDetailList;
    }*/

    public List<UnitDetail> getUnitDetail() {
        return unitDetail;
    }

    public void setUnitDetail(List<UnitDetail> unitDetail) {
        this.unitDetail = unitDetail;
    }

    /*public List<Object> getRosterDetail() {
        return rosterDetail;
    }

    public void setRosterDetail(List<Object> rosterDetail) {
        this.rosterDetail = rosterDetail;
    }
*/
    public List<ShiftDetail> getShiftDetail() {
        return shiftDetail;
    }

    public void setShiftDetail(List<ShiftDetail> shiftDetail) {
        this.shiftDetail = shiftDetail;
    }

   /* public List<UnitContractStrengthDetail> getUnitContractStrengthDetail() {
        return unitContractStrengthDetail;
    }

    public void setUnitContractStrengthDetail(List<UnitContractStrengthDetail> unitContractStrengthDetail) {
        this.unitContractStrengthDetail = unitContractStrengthDetail;
    }*/

}
