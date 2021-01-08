package com.sisindia.mysis.Model;

public class Leave_Detail_Model {
    private String ID;
    private String LEAVE_STATUS;
    private String LEAVE_DATE;
    private String LEAVE_TYPE;
    private String LEAVE_REQUEST_TYPE;

    private String UN_SYNC_DATA;

    private String LEAVE_COLOR_CODE;


    public String getLEAVE_COLOR_CODE() {
        return LEAVE_COLOR_CODE;
    }

    public void setLEAVE_COLOR_CODE(String LEAVE_COLOR_CODE) {
        this.LEAVE_COLOR_CODE = LEAVE_COLOR_CODE;
    }

    public String getLEAVE_TYPE() {
        return LEAVE_TYPE;
    }

    public void setLEAVE_TYPE(String LEAVE_TYPE) {
        this.LEAVE_TYPE = LEAVE_TYPE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLEAVE_STATUS() {
        return LEAVE_STATUS;
    }

    public void setLEAVE_STATUS(String LEAVE_STATUS) {
        this.LEAVE_STATUS = LEAVE_STATUS;
    }

    public String getUN_SYNC_DATA() {
        return UN_SYNC_DATA;
    }

    public void setUN_SYNC_DATA(String UN_SYNC_DATA) {
        this.UN_SYNC_DATA = UN_SYNC_DATA;
    }

    public String getLEAVE_DATE() {
        return LEAVE_DATE;
    }

    public void setLEAVE_DATE(String LEAVE_DATE) {
        this.LEAVE_DATE = LEAVE_DATE;
    }

    public String getLEAVE_REQUEST_TYPE() {
        return LEAVE_REQUEST_TYPE;
    }

    public void setLEAVE_REQUEST_TYPE(String LEAVE_REQUEST_TYPE) {
        this.LEAVE_REQUEST_TYPE = LEAVE_REQUEST_TYPE;
    }
}
