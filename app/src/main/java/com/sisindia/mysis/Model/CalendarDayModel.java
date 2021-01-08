package com.sisindia.mysis.Model;

import androidx.room.Ignore;

import java.util.Calendar;

public class CalendarDayModel {
    @Ignore
    private int dayNumber;
    @Ignore
    private Calendar myCalendar;
    @Ignore
    private Boolean selected;
    @Ignore
    private Boolean leaveSelected;
    @Ignore
    private Boolean today;
    @Ignore
    private Boolean _dateRange = false;
    @Ignore
    private String Date, dateSingleDigit;
    @Ignore
    private int day, month, year;
    @Ignore
    private boolean alreadyLeaveApply;
    @Ignore
    private String leaveReasonId;

    @Ignore
    private String leaveStatus;

    @Ignore
    private String UN_SYNC_DATA="0";

    @Ignore
    private int colorCode;

    @Ignore
    private int extraDuty;

    private String REGNO, IS_OFF, LEAVE_TYPE_ID, LEAVE_DATE, JSON, ID, LEAVE_DESCRIPTION;

    public String getLeaveReasonId() {
        return leaveReasonId;
    }
    public void setLeaveReasonId(String leaveReasonId) {
        this.leaveReasonId = leaveReasonId;
    }

    public boolean isAlreadyLeaveApply() {
        return alreadyLeaveApply;
    }

    public void setAlreadyLeaveApply(boolean alreadyLeaveApply) {
        this.alreadyLeaveApply = alreadyLeaveApply;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public int getExtraDuty() {
        return extraDuty;
    }

    public void setExtraDuty(int extraDuty) {
        this.extraDuty = extraDuty;
    }


    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getUN_SYNC_DATA() {
        return UN_SYNC_DATA;
    }

    public void setUN_SYNC_DATA(String UN_SYNC_DATA) {
        this.UN_SYNC_DATA = UN_SYNC_DATA;
    }

    public Calendar getMyCalendar() {
        return myCalendar;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public void setMyCalendar(Calendar myCalendar) {
        this.myCalendar = myCalendar;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getToday() {
        return today;
    }

    public void setToday(Boolean today) {
        this.today = today;
    }

    public Boolean get_dateRange() {
        return _dateRange;
    }

    public void set_dateRange(Boolean _dateRange) {
        this._dateRange = _dateRange;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDateSingleDigit() {
        return dateSingleDigit;
    }

    public void setDateSingleDigit(String dateSingleDigit) {
        this.dateSingleDigit = dateSingleDigit;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getREGNO() {
        return REGNO;
    }

    public void setREGNO(String REGNO) {
        this.REGNO = REGNO;
    }

    public String getIS_OFF() {
        return IS_OFF;
    }

    public void setIS_OFF(String IS_OFF) {
        this.IS_OFF = IS_OFF;
    }

    public String getLEAVE_TYPE_ID() {
        return LEAVE_TYPE_ID;
    }

    public void setLEAVE_TYPE_ID(String LEAVE_TYPE_ID) {
        this.LEAVE_TYPE_ID = LEAVE_TYPE_ID;
    }

    public String getLEAVE_DATE() {
        return LEAVE_DATE;
    }

    public void setLEAVE_DATE(String LEAVE_DATE) {
        this.LEAVE_DATE = LEAVE_DATE;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLEAVE_DESCRIPTION() {
        return LEAVE_DESCRIPTION;
    }

    public void setLEAVE_DESCRIPTION(String LEAVE_DESCRIPTION) {
        this.LEAVE_DESCRIPTION = LEAVE_DESCRIPTION;
    }

    public Boolean getLeaveSelected() {
        return leaveSelected;
    }

    public void setLeaveSelected(Boolean leaveSelected) {
        this.leaveSelected = leaveSelected;
    }
}
