package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.sisindia.mysis.entity.AttendanceMasterModel;

@Dao
public interface AttendanceMasterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AttendanceMasterModel attendancemasterModel);
}
