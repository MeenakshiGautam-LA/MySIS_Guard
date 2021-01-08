package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.Flash_Message_Model;

import java.util.List;

@Dao
public interface FlashMessageDao {
    @Insert
    void insert(List<Flash_Message_Model> flash_message_modelList);

    @Query("select * from FLASH_MESSAGE where ID=:ID")
    Flash_Message_Model getDetails(String ID);

    @Query("select * from FLASH_MESSAGE")
    List<Flash_Message_Model> getAllRecords();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void singleInsert(Flash_Message_Model employeDetailsModels);

    @Update
    void update(Flash_Message_Model flash_message_model);

    @Query("SELECT max(DATE_MODIFIED) FROM FLASH_MESSAGE")
    String maxModifiedDate();

    @Query("delete from FLASH_MESSAGE where id in (:idList)")
    void deleteAll(List<String> idList);
}
