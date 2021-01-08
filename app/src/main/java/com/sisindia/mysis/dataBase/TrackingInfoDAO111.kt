//package com.sisindia.guardattendance.dataBase
//
//import androidx.room.*
//import com.sisindia.guardattendance.entity.PeriodicTrackingInfoModel111
//
//@Dao
//interface TrackingInfoDAO111 {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(trackingInfoModel: PeriodicTrackingInfoModel111)
//
//    @Update
//    fun update(trackingInfoModel: PeriodicTrackingInfoModel111)
//
//    @Delete
//    fun delete(trackingInfoModel: PeriodicTrackingInfoModel111)
//
//    @Query("delete from TRACKING_INFO where ID in (:IDs)")
//    fun deleteRow(IDs: ArrayList<String>)
//
//    @Query("update TRACKING_INFO set FLAG =:flag where ID in (:IDs)")
//    fun updateFlag(IDs: ArrayList<String>, flag: String)
//
//    @Query("select * from TRACKING_INFO where FLAG=:flag")
//    fun getListToSync(flag: String): List<PeriodicTrackingInfoModel111>
//
//
//    @Query("DELETE from TRACKING_INFO where ID=:id")
//    fun deleteRecord(id: String)
//}