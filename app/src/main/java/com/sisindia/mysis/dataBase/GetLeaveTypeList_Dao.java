package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sisindia.mysis.entity.LeaveTypeListModel;

import java.util.List;

@Dao
public interface GetLeaveTypeList_Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void singleInsert(LeaveTypeListModel leaveTypeListModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<LeaveTypeListModel> leaveTypeListModel);

    @Query("select * from GET_LEAVE_TYPE_LIST where FOR_SELF='1'and VISIBLE='1'")
    List<LeaveTypeListModel> getLeaveList();
    @Query("select * from GET_LEAVE_TYPE_LIST where VISIBLE='1'")
    List<LeaveTypeListModel> getLeaveListForLegend();

    @Query("select * from GET_LEAVE_TYPE_LIST where LEAVE_ID=:leaveId")
    LeaveTypeListModel singleLeave(String leaveId);
    @Query("select COLOR_CODE from GET_LEAVE_TYPE_LIST where LEAVE_ID=:leaveId")
    String getColorCode (String leaveId);
    @Query("select LEAVE_TYPE from GET_LEAVE_TYPE_LIST where LEAVE_ID=:leaveId")
    String getLeaveTypeName (String leaveId);

}
