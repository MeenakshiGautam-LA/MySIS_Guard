package com.sisindia.mysis.Model;

public class Attendance_for_calender_model {
    private String FLAG;
    private String EXTRA_DUTY="1";
    private String ACT_START_TIME;

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getEXTRA_DUTY() {
        return EXTRA_DUTY;
    }

    public void setEXTRA_DUTY(String EXTRA_DUTY) {
        this.EXTRA_DUTY = EXTRA_DUTY;
    }

    public String getACT_START_TIME() {
        return ACT_START_TIME;
    }

    public void setACT_START_TIME(String ACT_START_TIME) {
        this.ACT_START_TIME = ACT_START_TIME;
    }

}
