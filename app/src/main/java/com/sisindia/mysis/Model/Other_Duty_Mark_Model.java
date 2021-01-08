package com.sisindia.mysis.Model;

import com.sisindia.mysis.dataBase.EmployeDetailsModel;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.GetSiteDetail;

import java.io.Serializable;

public class Other_Duty_Mark_Model implements Serializable {
    private EmployeDetailsModel employeDetailsModel;
    private DailyAttendanceDetail dailyAttendanceDetail;
    private GetSiteDetail siteDetail;
    private RosterDetail rosterDetail;


    public RosterDetail getRosterDetail() {
        return rosterDetail;
    }

    public void setRosterDetail(RosterDetail rosterDetail) {
        this.rosterDetail = rosterDetail;
    }


    public EmployeDetailsModel getEmployeDetailsModel() {
        return employeDetailsModel;
    }

    public void setEmployeDetailsModel(EmployeDetailsModel employeDetailsModel) {
        this.employeDetailsModel = employeDetailsModel;
    }

    public DailyAttendanceDetail getDailyAttendanceDetail() {
        return dailyAttendanceDetail;
    }

    public void setDailyAttendanceDetail(DailyAttendanceDetail dailyAttendanceDetail) {
        this.dailyAttendanceDetail = dailyAttendanceDetail;
    }

    public GetSiteDetail getSiteDetail() {
        return siteDetail;
    }

    public void setSiteDetail(GetSiteDetail siteDetail) {
        this.siteDetail = siteDetail;
    }
}
