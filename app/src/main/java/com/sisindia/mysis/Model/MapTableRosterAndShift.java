package com.sisindia.mysis.Model;

import android.annotation.SuppressLint;

import com.sisindia.mysis.entity.base.CSObject;

@SuppressLint("ParcelCreator")
public class MapTableRosterAndShift extends CSObject {
    private String UNIT_CODE;
    private String START_TIME;
    private String END_TIME;
    private String SHIFT_NAME;
    private String SHIFT_ID;
    private String REGNO;
    private String DUTY_POST_ID;
    private String DUTY_RANK;
    private String UNIT_NAME;
    private String ROSTER_DATE;
    private String QR_CODE;
    private String SHIFT_START_TIME;
    private String SHIFT_END_TIME;
    private String DUTY_IN_ENABLE_TIME;
    private String JSON;
    private String DUTY_OUT_DISABLE_TIME;

    private String GEO_LOCATION;


    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }


    public String getDUTY_IN_ENABLE_TIME() {
        return DUTY_IN_ENABLE_TIME;
    }

    public void setDUTY_IN_ENABLE_TIME(String DUTY_IN_ENABLE_TIME) {
        this.DUTY_IN_ENABLE_TIME = DUTY_IN_ENABLE_TIME;
    }

    public String getGEO_LOCATION() {
        return GEO_LOCATION;
    }

    public void setGEO_LOCATION(String GEO_LOCATION) {
        this.GEO_LOCATION = GEO_LOCATION;
    }

    public String getDUTY_OUT_DISABLE_TIME() {
        return DUTY_OUT_DISABLE_TIME;
    }

    public void setDUTY_OUT_DISABLE_TIME(String DUTY_OUT_DISABLE_TIME) {
        this.DUTY_OUT_DISABLE_TIME = DUTY_OUT_DISABLE_TIME;
    }

    public String getSHIFT_START_TIME() {
        return SHIFT_START_TIME;
    }

    public void setSHIFT_START_TIME(String SHIFT_START_TIME) {
        this.SHIFT_START_TIME = SHIFT_START_TIME;
    }

    public String getSHIFT_END_TIME() {
        return SHIFT_END_TIME;
    }

    public void setSHIFT_END_TIME(String SHIFT_END_TIME) {
        this.SHIFT_END_TIME = SHIFT_END_TIME;
    }

    public String getQR_CODE() {
        return QR_CODE;
    }

    public void setQR_CODE(String QR_CODE) {
        this.QR_CODE = QR_CODE;
    }

    public String getROSTER_DATE() {
        return ROSTER_DATE;
    }

    public void setROSTER_DATE(String ROSTER_DATE) {
        this.ROSTER_DATE = ROSTER_DATE;
    }


    public String getUNIT_NAME() {
        return UNIT_NAME;
    }

    public void setUNIT_NAME(String UNIT_NAME) {
        this.UNIT_NAME = UNIT_NAME;
    }


    public String getREGNO() {
        return REGNO;
    }

    public void setREGNO(String REGNO) {
        this.REGNO = REGNO;
    }

    public String getSHIFT_ID() {
        return SHIFT_ID;
    }

    public void setSHIFT_ID(String SHIFT_ID) {
        this.SHIFT_ID = SHIFT_ID;
    }


    public String getDUTY_POST_ID() {
        return DUTY_POST_ID;
    }

    public void setDUTY_POST_ID(String DUTY_POST_ID) {
        this.DUTY_POST_ID = DUTY_POST_ID;
    }

    public String getDUTY_RANK() {
        return DUTY_RANK;
    }

    public void setDUTY_RANK(String DUTY_RANK) {
        this.DUTY_RANK = DUTY_RANK;
    }


    public String getUNIT_CODE() {
        return UNIT_CODE;
    }

    public void setUNIT_CODE(String UNIT_CODE) {
        this.UNIT_CODE = UNIT_CODE;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getSHIFT_NAME() {
        return SHIFT_NAME;
    }

    public void setSHIFT_NAME(String SHIFT_NAME) {
        this.SHIFT_NAME = SHIFT_NAME;
    }

}
