package com.sisindia.mysis.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.sisindia.mysis.entity.GetSiteDetail;

import java.util.List;


@Dao
public interface SiteDetail_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<GetSiteDetail> rankMatrixList);


    @Update
    void update(GetSiteDetail task_model);
    @Query("delete from GET_SITE_DETAIL where id in (:idList)")
    void deleteAll(List<String> idList);
    @Delete
    void delete(GetSiteDetail task_model);

    @Query("select * from GET_SITE_DETAIL")
    List<GetSiteDetail> getDetails();

    @Query("select * from   GET_SITE_DETAIL where UNIT_CODE= :unit_code")
    List<GetSiteDetail> getDetails(String unit_code);
    @Query("select SITE_NAME from   GET_SITE_DETAIL where UNIT_CODE= :unit_code")
   String getSiteName(String unit_code);

    @Query("SELECT max(DATE_MODIFIED) FROM GET_SITE_DETAIL")
    String maxModifiedDate();

    @Query("select * from GET_SITE_DETAIL")
    LiveData<List<GetSiteDetail>> getlivedata();

    @Query("select CREATE_SHIFT_ALLOWED from GET_SITE_DETAIL where UNIT_CODE=:unitCode")
    int createShiftPermissionAllowed(String unitCode);

    @Query("select CREATE_ROSTER_ALLOWED from GET_SITE_DETAIL where UNIT_CODE=:unitCode")
    int createRosterPermissionAllowed(String unitCode);

    @Query("select MESS_ATTENDANCE_ALLOWED from GET_SITE_DETAIL where UNIT_CODE=:unitCode")
    int messAttendanceAllowed(String unitCode);

    @Query("select WEEKLY_OFF_ALLOWED from GET_SITE_DETAIL where UNIT_CODE=:unitCode")
    int weeklyOffAllowed(String unitCode);

    @Query("select EDIT_SHIFT_ALLOWED from GET_SITE_DETAIL where UNIT_CODE=:unitCode")
    int editShiftAllowed(String unitCode);


}
