package com.sisindia.mysis.Model;

import java.util.Calendar;

public class MyDayModel {
    private int dayNumber;
    private Calendar myCalendar;
    private boolean selected;
    private Boolean today;

    private String Date, dateSingleDigit, dateType;//S- start, M-middle, E-end

    private int day, month, year;

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Calendar getMyCalendar() {
        return myCalendar;
    }

    public void setMyCalendar(Calendar myCalendar) {
        this.myCalendar = myCalendar;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Boolean getToday() {
        return today;
    }

    public void setToday(Boolean today) {
        this.today = today;
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
}
