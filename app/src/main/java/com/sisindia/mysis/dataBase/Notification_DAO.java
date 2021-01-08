package com.sisindia.mysis.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sisindia.mysis.entity.NotificationModel;

import java.util.List;

@Dao
public interface Notification_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NotificationModel> notificationModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingle(NotificationModel notificationModels);

    @Query("SELECT max(DATE_MODIFIED) FROM NOTIFICATION_TABLE")
    String maxModifiedDate();

    @Query("SELECT * FROM NOTIFICATION_TABLE where ID=:id")
    NotificationModel getDetail(String id);

    @Query("SELECT * FROM NOTIFICATION_TABLE where datetime(EXPIRY_DATE)>=datetime(:currentDateTime)")
    List<NotificationModel> getNotificationList(String currentDateTime);

    @Update
    void update(NotificationModel task_model);

    @Query("delete from NOTIFICATION_TABLE where ID in (:idList)")
    void deleteAll(List<String> idList);

    @Query("select count(ID) from NOTIFICATION_TABLE where READ_STATUS=0 and datetime(EXPIRY_DATE)>=datetime(:currentDateTime)")
    LiveData<Integer> countUnRead(String currentDateTime);

    @Query("update NOTIFICATION_TABLE set FLAG='1',DATE_READ=:readDate,READ_STATUS=1,JSON=:json,DATE_MODIFIED=:readDate where ID=:id")
    void updateReadStatus(String id, String readDate, String json);

    @Query("select * from NOTIFICATION_TABLE where FLAG=:flag")
     List<NotificationModel> getListToSync(String flag);

    @Query("Update NOTIFICATION_TABLE set FLAG=:flag where ID=:id")
    void updateFlag(String id, String flag);
}
