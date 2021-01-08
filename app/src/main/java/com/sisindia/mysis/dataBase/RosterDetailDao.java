package com.sisindia.mysis.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.entity.RosterModel;

import java.util.List;

@Dao
public interface RosterDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RosterModel> rosterModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void singleInsert(RosterModel rosterModels);

    @Query("delete from ROSTER_DETAIL where id in (:idList)")
    void deleteAll(List<String> idList);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("select RD.QR_CODE,RD.REGNO,RD.UNIT_NAME,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where datetime(:dateTime) between datetime(RD.shift_start_time,'-45 minutes')and datetime(RD.shift_end_time)order by RD.roster_date desc limit 1") // working query just change for add 5 hour in shift end time
//    @Query("select RD.DUTY_IN_ENABLE_TIME,RD.DUTY_OUT_DISABLE_TIME, RD.SHIFT_START_TIME,RD.SHIFT_END_TIME, RD.QR_CODE,RD.REGNO,RD.UNIT_NAME,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where datetime(:dateTime) between datetime(RD.DUTY_IN_ENABLE_TIME) and datetime(datetime(RD.DUTY_OUT_DISABLE_TIME),'+5 hours')order by RD.roster_date desc limit 1")
    @Query("select RD.GEO_LOCATION,RD.DUTY_IN_ENABLE_TIME,RD.DUTY_OUT_DISABLE_TIME, RD.SHIFT_START_TIME,RD.SHIFT_END_TIME, RD.QR_CODE,RD.REGNO,RD.UNIT_NAME,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where datetime(:dateTime) between datetime(RD.DUTY_IN_ENABLE_TIME) and datetime(datetime(RD.DUTY_OUT_DISABLE_TIME),'+5 hours')order by RD.roster_date desc limit 1")
    MapTableRosterAndShift getShiftDetailOnRosterBase(String dateTime);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("select RD.QR_CODE,RD.REGNO,RD.UNIT_NAME,RD.SHIFT_ID,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where  REGNO=:regNO  and datetime(:dateTime) between datetime(RD.shift_start_time,'-45 minutes')and datetime(RD.shift_end_time)order by RD.roster_date \n")
    @Query("select RD.GEO_LOCATION,RD.JSON,RD.DUTY_IN_ENABLE_TIME,RD.DUTY_OUT_DISABLE_TIME,RD.SHIFT_START_TIME,RD.SHIFT_END_TIME, RD.QR_CODE,RD.REGNO,RD.UNIT_NAME,RD.SHIFT_ID,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where  REGNO=:regNO  and datetime(:dateTime) between datetime(RD.DUTY_IN_ENABLE_TIME) and datetime(datetime(RD.DUTY_OUT_DISABLE_TIME),'+5 hours')order by RD.roster_date desc limit 1 \n")
    MapTableRosterAndShift getShiftDetailOnRosterBaseByRegNO(String regNO, String dateTime);

    @Query("select RD.GEO_LOCATION,RD.DUTY_IN_ENABLE_TIME,RD.DUTY_OUT_DISABLE_TIME,RD.QR_CODE,RD.REGNO,RD.UNIT_NAME,RD.SHIFT_ID,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,\n" +
            "SM.SHIFT_NAME,RD.ROSTER_DATE\n" +
            " from ROSTER_DETAIL RD inner join ShIFT_MASTER\n" +
            " SM on SM.ID=RD.SHIFT_ID  where  datetime( RD.SHIFT_START_TIME) between DATETIME(:dateTime) and DATETIME(:dateTime,'+7 day')  AND datetime(:dateTime) not BETWEEN datetime(RD.DUTY_IN_ENABLE_TIME) and datetime(RD.DUTY_OUT_DISABLE_TIME)\norder by SHIFT_START_TIME  limit 1")
//    @Query("select RD.REGNO,RD.UNIT_NAME,RD.SHIFT_ID,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where RD.ROSTER_DATE=:date order by RD.roster_date desc limit 1")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    MapTableRosterAndShift getShiftForNextDay(String dateTime);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select RD.GEO_LOCATION,RD.DUTY_IN_ENABLE_TIME,RD.DUTY_OUT_DISABLE_TIME,RD.SHIFT_START_TIME,RD.SHIFT_END_TIME,  RD.QR_CODE,RD.REGNO,RD.UNIT_NAME,RD.SHIFT_ID,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where RD.REGNO=:regNO order by RD.roster_date desc limit 1")
    MapTableRosterAndShift getShiftDetailOnRegNoBase(String regNO);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select RD.GEO_LOCATION,RD.DUTY_IN_ENABLE_TIME,RD.DUTY_OUT_DISABLE_TIME, RD.SHIFT_START_TIME,RD.SHIFT_END_TIME, RD.REGNO,RD.UNIT_NAME,RD.SHIFT_ID,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where RD.ROSTER_DATE=:date order by RD.roster_date desc limit 1")
    LiveData<MapTableRosterAndShift> getShiftliveData(String date);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select RD.GEO_LOCATION,RD.DUTY_IN_ENABLE_TIME,RD.DUTY_OUT_DISABLE_TIME,RD.SHIFT_START_TIME,RD.SHIFT_END_TIME, RD.QR_CODE,RD.REGNO,RD.UNIT_NAME,RD.SHIFT_ID,SM.UNIT_CODE,RD.DUTY_POST_ID,RD.DUTY_RANK,SM.START_TIME,SM.END_TIME,RD.SHIFT_ID,SM.ID,SM.SHIFT_NAME,RD.ROSTER_DATE from ROSTER_DETAIL RD inner join ShIFT_MASTER SM on SM.ID=RD.SHIFT_ID where RD.ROSTER_DATE=:date and RD.REGNO=:regNo order by RD.roster_date desc limit 1")
    MapTableRosterAndShift getShiftDetailOnRosterBase(String date, String regNo);

    @Query("select * from ROSTER_DETAIL RD where RD.ROSTER_DATE=:date and RD.REGNO=:regNo order by RD.roster_date desc limit 1")
    RosterModel getshiftTime_InService(String date, String regNo);

    @Query("SELECT max(DATE_MODIFIED) FROM ROSTER_DETAIL")
    String maxModifiedDate();

    @Query("select * from ROSTER_DETAIL where REGNO=:regNo and ROSTER_DATE=:date")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<MapTableRosterAndShift> getRosterList(String regNo, String date);
    @Query("select GEO_LOCATION from roster_DETAIL where date(:date) between date(DUTY_IN_ENABLE_TIME)and date(DUTY_OUT_DISABLE_TIME) order by roster_date desc limit 1\n")
    String getGeo_Location(String date);
}
