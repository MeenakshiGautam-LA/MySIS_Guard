package com.sisindia.mysis.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;


import com.sisindia.mysis.entity.LeaveDetailTransaction;
import com.sisindia.mysis.entity.LeaveMaster;

import java.util.List;

@Dao
public interface LeaveMaster_Dao {

    @Query("select * from LEAVE_MASTER where LEAVE_STATUS not in('2')")
    LiveData<List<LeaveMaster>> getLeaveDetails();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<LeaveMaster> insertLeaveMaster);

    @Query("SELECT max(DATE_MODIFIED) FROM LEAVE_MASTER")
    String maxModifiedDate();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void singleInsert(LeaveMaster insertLeaveMaster);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLeaveEmployeeDetailsList(List<LeaveDetailTransaction> insertLeaveMaster);

    @Query("update  LEAVE_MASTER set LEAVE_STATUS='2', FLAG='1',JSON=:json where ID=:leaveMasterId")
    void updateRecordAsCancel(String leaveMasterId,String json);

    @Query("Update LEAVE_MASTER set FLAG=:flag where ID=:id")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    void updateFlag(String id, String flag);

    @Query("select * from LEAVE_MASTER where ID=:ID")
    LeaveMaster getDetails(String ID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLeaveEmployee(LeaveMaster leaveMaster);

    @Update
    void updateLeaveEmployee(LeaveMaster task_model);

    @Query("select * from LEAVE_MASTER where FLAG=:flag")
    public List<LeaveMaster> getListToSync(String flag);

    @Query("select * from LEAVE_MASTER where FLAG=:flag and date(:startDate) between date(LEAVE_START_DATE) and date(LEAVE_END_DATE)")
    public List<LeaveMaster> getListToSync_on_start_end_date(String flag,String startDate);


}
