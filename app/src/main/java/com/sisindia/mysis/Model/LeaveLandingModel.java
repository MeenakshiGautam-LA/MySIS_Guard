package com.sisindia.mysis.Model;

public class LeaveLandingModel {

    /* public LeaveLandingModel(String leave_reason, String leave_days, String leave_date, String leave_status) {

         this.leave_reason = leave_reason;
         this.leave_days = leave_days;
         this.leave_date = leave_date;
         this.leave_status = leave_status;

     }

     public LeaveLandingModel() { }
 */
    String leave_reason;

    String leaveId;
    String leave_days;
    String leave_date;
    String leave_status;
    Boolean _isClicked = false;

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public Boolean get_isClicked() {
        return _isClicked;
    }

    public void set_isClicked(Boolean _isClicked) {
        this._isClicked = _isClicked;
    }


    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }


    public String getLeave_days() {
        return leave_days;
    }

    public void setLeave_days(String leave_days) {
        this.leave_days = leave_days;
    }


    public String getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(String leave_date) {
        this.leave_date = leave_date;
    }


    public String getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(String leave_status) {
        this.leave_status = leave_status;
    }


}
