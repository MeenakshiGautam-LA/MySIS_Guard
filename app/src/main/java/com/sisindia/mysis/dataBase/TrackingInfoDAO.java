package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sisindia.mysis.entity.PeriodicTrackingInfoModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TrackingInfoDAO {
    @Insert
    void insert(PeriodicTrackingInfoModel periodicTrackingInfoModel);

    @Query("select * from TRACKING_INFO where FLAG=:flag")
    List<PeriodicTrackingInfoModel> getListToSync(String flag);

    @Query("update TRACKING_INFO set FLAG =:flag where ID in (:IDs)")
    void updateFlag(ArrayList<String> IDs, String flag);

    @Query("delete from TRACKING_INFO where ID=:id")
    void deleteRecord(String id);

    @Query("select max(DATE_MODIFIED) from TRACKING_INFO")
    String getMaxModifiedDate();
}
