package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sisindia.mysis.entity.AttendanceGuardPicModel;

import java.util.List;

@Dao
public interface AttendancePicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AttendanceGuardPicModel attendanceGuardPicModel);
    @Query("select * from ATTENANCE_PIC_TABLE where ATTENDANCE_ID=:attendanceID and REGNO=:regNo order by date_modified desc limit 1")
   AttendanceGuardPicModel getAttendanceGuardPicModel(String attendanceID,String regNo);

    @Query("update ATTENANCE_PIC_TABLE set PICTURE=:picture,FLAG='1',DATE_MODIFIED=:dateModified,JSON=:json where ATTENDANCE_ID=:attendanceID")
    void updatePicture(byte[] picture, String attendanceID, String dateModified, String json);

    @Query("select * from ATTENANCE_PIC_TABLE where  REGNO=:regNo")
    AttendanceGuardPicModel getEmployeePicture(String regNo);
    @Query("select * from ATTENANCE_PIC_TABLE where FLAG=:flag")
    public List<AttendanceGuardPicModel> getListToSync(String flag);

    @Query("delete from  ATTENANCE_PIC_TABLE where  ID=:id and FLAG='1'")
    void deleteRecord(String id);
}
