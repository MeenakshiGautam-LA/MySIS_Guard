package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sisindia.mysis.entity.ShiftMasterModel;

import java.util.List;


@Dao
public interface ShifMaster_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ShiftMasterModel> getCustomerdetails);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertsingle(ShiftMasterModel getCustomerdetails);

    @Query("delete from SHIFT_MASTER where id in (:idList)")
    void deleteAll(List<String> idList);
    @Update
    void update(ShiftMasterModel task_model);

    @Delete
    void delete(ShiftMasterModel task_model);

    @Query("select SHIFT_NAME from SHIFT_MASTER")
    String getShiftname();

    @Query("select SHIFT_NAME from SHIFT_MASTER where ID=:shiftID")
    String getShiftnameBy_ID(String shiftID);

    @Query("select * from SHIFT_MASTER where UNIT_CODE=:unitCode")
    List<ShiftMasterModel> getDetails(String unitCode);

    @Query("select * from SHIFT_MASTER M  where UNIT_CODE=:unitCode and :currentTime < M.START_TIME")
    List<ShiftMasterModel> getUpcomingShiftName(String unitCode, String currentTime);

    //    @Query("DELETE FROM " + "CUSTOMER_FAVOURITE_TABLE" + " WHERE " + "USERNAME" + " = '" + end_address + "'")
//    int updateTour(long tid, String end_address);
//    @Query("SELECT distinct SM.ID,SCM.ID as shiftCloserId,SCM.SHIFT_ID,SM.DUTY_HOUR as DUTY_HRS, SM.Shift_Name, SM.Start_Time, SM.End_Time FROM SHIFT_CLOSER_MASTER SCM INNER join SHIFT_MASTER SM on SM.ID=SCM.SHIFT_ID and datetime('now','localtime')between datetime(SCM.shift_start_time,'-45 minutes')and datetime(SCM.shift_end_time) and SM.unit_code=:uniCode group by SM.ID order by datetime(SCM.shift_start_time), SM.Shift_Name ")
////    @Query("Select M.ID, M.SHIFT_NAME,D.SHIFT_ID as Temporary_Shift_ID, M.START_TIME, M.END_TIME, sum(D.SHIFT_STRENGTH) as SHIFT_STRENGTH,M.DUTY_HRS_CONVERT from Shift_MASTER M \n" +
////            "inner join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.SHIFT_ID Where M.UNIT_CODE=:unicode \n" +
////            "and \n" +
////            "datetime(date(:date), :startTime) BETWEEN datetime(date('now', 'localtime'), M.START_TIME) and datetime(datetime(date('now', 'localtime'), M.START_TIME), M.DUTY_HRS_CONVERT || ' hour') and datetime(date('now', 'localtime')) BETWEEN M.START_DATE and M.END_DATE\n" +
////            "Group By M.ID, M.Shift_Name, M.START_TIME, M.END_TIME order by datetime(:date,M.START_TIME) desc")
////    @Query("Select M.ID, M.Shift_Name,D.Shift_ID as Temporary_Shift_ID, M.Start_Time, M.End_Time, sum(D.Shift_Strength) as SHIFT_STRENGTH from Shift_MASTER M inner join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.Shift_ID Where M.Unit_Code=:unicode  and  M.START_TIME >= time('now')  Group By M.ID, M.Shift_Name, M.Start_Time, M.End_Time")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<ShiftDetailCountModals> getShiftDetail_Strength(String uniCode);
//
//
////    @Query("Select M.ID,:dutyStatus, M.SHIFT_NAME,D.SHIFT_ID as Temporary_Shift_ID, M.START_TIME, M.END_TIME, sum(D.SHIFT_STRENGTH) as SHIFT_STRENGTH\n" +
////            "            from Shift_MASTER M \n" +
////            "                 inner join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.SHIFT_ID\n" +
////            "            Where M.UNIT_CODE=:unicode and datetime(:date,time(M.END_TIME))    >= datetime(:date_And_Time,'15 minutes') Group By M.ID, M.Shift_Name, M.START_TIME, M.END_TIME order by datetime(:date,M.END_TIME) desc")
////    List<ShiftMasterModel>getShift_for_OUT_Status(String unicode, String date, String date_And_Time,String dutyStatus);
////    List<ShiftMasterModel> getShiftDetail_Strength(String unicode);
//
//    @Query("Select M.ID, M.Shift_Name,D.Shift_ID as Temporary_Shift_ID, M.Start_Time, M.End_Time, sum(D.Shift_Strength) as SHIFT_STRENGTH from Shift_MASTER M inner join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.Shift_ID Where M.Unit_Code=:unicode and :startTime  between  M.START_TIME and M.End_Time  Group By M.ID, M.Shift_Name, M.Start_Time, M.End_Time")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<TestModel> getShiftDetail_Strength111(String unicode, String startTime);
//
//    @Query("Select M.ID, M.Shift_Name,D.Shift_ID as Temporary_Shift_ID, M.Start_Time, M.End_Time, sum(D.Shift_Strength) as SHIFT_STRENGTH from Shift_MASTER M inner join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.Shift_ID Where M.Unit_Code=:unicode    Group By M.ID, M.Shift_Name, M.Start_Time, M.End_Time")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<ShiftMasterModel> getAll_ShiftList(String unicode);
//

//    @Query("Select M.ID, M.Shift_Name,D.Shift_ID as Temporary_Shift_ID, M.Start_Time, M.End_Time, sum(D.Shift_Strength) as SHIFT_STRENGTH,M.DUTY_HOUR from Shift_MASTER M inner join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.Shift_ID Where M.Unit_Code=:unicode Group By M.ID, M.Shift_Name, M.Start_Time, M.End_Time")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<ShiftDetailCountModals> getAllShiftDetail(String unicode);
//
//    @Query("Select M.ID, M.Shift_Name,M.ID as Temporary_Shift_ID, M.Start_Time, M.End_Time, sum(IFNULL(D.Shift_Strength,0)) as SHIFT_STRENGTH from Shift_MASTER M left join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.Shift_ID Where M.Unit_Code=:unicode and :date between date(M.START_DATE) and date(M.END_DATE) Group By M.ID, M.Shift_Name, M.Start_Time, M.End_Time order by M.Start_Time asc")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<ShiftDetailCountModals> getAllShiftDetail1(String unicode, String date);
//
//    @Query("seleCT COUNT(ROSTER_ID) from DAILY_TRANSACTION_ROSTER inner join MAIN_ROSTER on DAILY_TRANSACTION_ROSTER.ROSTER_ID = MAIN_ROSTER.ID where MAIN_ROSTER.SHIFT_ID=:shiftId and MAIN_ROSTER.ROSTER_DATE=:date ")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    Integer getTotalCountOfRosterDefineOnShift(String shiftId, String date);
//
//    //    @Query("Select M.ID,M.START_DATE,M.END_DATE, M.Shift_Name,D.Shift_ID as Temporary_Shift_ID, M.Start_Time, M.End_Time, sum(D.Shift_Strength) as SHIFT_STRENGTH from Shift_MASTER M inner join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.Shift_ID Where M.Unit_Code=:unitcode and M.Id=:ID Group By M.ID, M.Shift_Name, M.Start_Time, M.End_Time")
//    @Query("select * from shift_Master where id=:shiftId and UNIT_CODE=:unitcode")
//    ShiftMasterModel getshiftDetail(String unitcode, String shiftId);
//
//    @Query("SELECT max(DATE_MODIFIED) FROM " + Constants.SHIFTMASTER)
//    String maxModifiedDate();
//
//    @Query("Select M.ID, M.Shift_Name,D.Shift_ID as Temporary_Shift_ID, M.Start_Time, M.End_Time, sum(D.Shift_Strength) as SHIFT_STRENGTH \n" +
//            "from Shift_MASTER M inner join UnitPOST_SHIFT_DEPLOYMENT_DETAIL D on M.ID=D.Shift_ID Where M.Unit_Code=:unicode Group By M.ID, M.Shift_Name, M.Start_Time, M.End_Time")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<ShiftMasterModel> getShiftDetail_Strength22222(String unicode);
//
//    @Query("Select SHIFT_ID,  SHIFT_START_TIME as START_TIME, SHIFT_END_TIME as END_TIME, TOTAL_DUTY_IN as totalDuty_in, TOTAL_DUTY_OUT as totalDuty_OUT from (Select SHIFT_ID,  SHIFT_START_TIME, SHIFT_END_TIME, Count(Distinct REGNO) As TOTAL_DUTY_IN, Sum(case when DUTY_STATUS='DUTY_OUT' then 1 else 0 end) as TOTAL_DUTY_OUT from DAILY_ATTENDANCE Where UNIT_CODE=:unitCode  Group by SHIFT_ID, SHIFT_START_TIME, SHIFT_END_TIME) M where TOTAL_DUTY_IN>TOTAL_DUTY_OUT and TOTAL_DUTY_OUT>0\n" +
//            "Union\n" +
//            "Select SHIFT_ID,  SHIFT_START_TIME, SHIFT_END_TIME, TOTAL_DUTY_IN, TOTAL_DUTY_OUT from (Select SHIFT_ID,  SHIFT_START_TIME, SHIFT_END_TIME, Count(Distinct REGNO) As TOTAL_DUTY_IN, Sum(case when DUTY_STATUS='DUTY_OUT' then 1 else 0 end) as TOTAL_DUTY_OUT from DAILY_ATTENDANCE Where UNIT_CODE=:unitCode Group by SHIFT_ID, SHIFT_START_TIME, SHIFT_END_TIME) M where TOTAL_DUTY_IN>TOTAL_DUTY_OUT and SHIFT_END_TIME<= datetime('now', 'localtime','15 minute') Order by SHIFT_END_TIME DESC\n")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<DutyOutDetailWithStregth> getDuty_OutDetailWith_Strength(String unitCode);
//
//    @Query("select * from SHIFT_MASTER where FLAG=:flag")
//    public List<ShiftMasterModel> getListToSync(String flag);
//
//    @Query("Update SHIFT_MASTER set FLAG=:flag where ID=:id")
//    void updateFlag(String id, String flag);
//
//
    @Query("select * from SHIFT_MASTER where ID=:ID")
    ShiftMasterModel getShiftDetail(String ID);

    @Query("SELECT max(DATE_MODIFIED) FROM SHIFT_MASTER")
    String maxModifiedDate();
//    @Query("select UDPD.ID, UDPD.POST_NAME,UPSDD.SHIFT_STRENGTH,UDPDD.SERVICE_CODE,SI.SYMBOL from unitDuty_post_detail UDPD inner join shift_master SM on SM.unit_code=:unitCode inner join unitpost_shift_deployment_detail UPSDD on UPSDD.post_id=UDPD.ID and UPSDD.shift_id=:shiftId inner join unit_duty_POST_DEPLOYMENT_DETAIL UDPDD on UDPDD.post_id=UDPD.id and UDPDD.service_code=UPSDD.service_code inner join service_info SI on SI.service_code= UDPDD.service_code group by UPSDD.id order by udpd.id")
//    List<DutyPost_On_Rank_Base_Query_Model> postWithRankBaseList(String shiftId, String unitCode);
}
