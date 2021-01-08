package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.UnitDetailModel;

import java.util.List;

@Dao
public interface UnitDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insert(List<UnitDetailModel>unitDetailModels);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  singleInsert(UnitDetailModel unitDetailModels);
    @Query("delete from UNIT_DETAIL where id in (:idList)")
    void deleteAll(List<String> idList);
    @Update
    void update(UnitDetailModel task_model);

    @Delete
    void delete(UnitDetailModel task_model);
    @Query("select * from UNIT_DETAIL where ID=:ID")
    UnitDetailModel getDetails(String ID);
    @Query("select * from UNIT_DETAIL where IS_PRIMARY='1'")
    UnitDetailModel getBaseUnitDetail();
    @Query("select * from UNIT_DETAIL where IS_PRIMARY not in('1')")
    List<UnitDetailModel> getAdditionalUnit();

}
