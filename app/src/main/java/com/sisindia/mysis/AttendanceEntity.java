package com.sisindia.mysis;

public class AttendanceEntity {
    public String getAttendancetext() {
        return attendancetext;
    }

    public void setAttendancetext(String attendancetext) {
        this.attendancetext = attendancetext;
    }

    String attendancetext;

    public AttendanceEntity(String attendancetext) {
        this.attendancetext = attendancetext;

    }


}
