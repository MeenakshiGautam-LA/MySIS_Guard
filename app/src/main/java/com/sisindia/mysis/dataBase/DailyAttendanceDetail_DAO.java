package com.sisindia.mysis.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

import com.sisindia.mysis.Model.Attendance_for_calender_model;
import com.sisindia.mysis.entity.DailyAttendanceDetail;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface DailyAttendanceDetail_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DailyAttendanceDetail employeDetailsModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(ArrayList<DailyAttendanceDetail> employeDetailsModels);

    @Query("delete from DAILY_ATTENDANCE where id in (:idList)")
    void deleteAll(List<String> idList);

    @Query("select * from DAILY_ATTENDANCE where ID=:ID")
    DailyAttendanceDetail getDetails(String ID);

    @Query("select * from DAILY_ATTENDANCE where REGNO=:regNo and datetime(:date) BETWEEN datetime(SHIFT_START_TIME,'-45 minutes') and datetime(SHIFT_END_TIME) and DUTY_STATUS='DUTY_IN'order by date_modified desc limit 1")
    DailyAttendanceDetail markAttendanceToday(String regNo, String date);

    @Update
    void update(DailyAttendanceDetail task_model);

    @Delete
    void delete(DailyAttendanceDetail task_model);

    //    @Query("select * from DAILY_ATTENDANCE where REGNO=:regNo and DUTY_STATUS=:dutyTag and datetime(:currentDateNd_Time) BETWEEN datetime(SHIFT_START_TIME) and datetime(SHIFT_END_TIME)")
    @Query("select * from DAILY_ATTENDANCE where REGNO=:regNo and DUTY_STATUS=:dutyTag and datetime(:currentDateNd_Time) BETWEEN datetime(SHIFT_START_TIME,'-45 minutes') and datetime(datetime(SHIFT_END_TIME),'+5 hours')order by SHIFT_START_TIME desc limit 1")
    DailyAttendanceDetail getAttendanceDetail(String regNo, String dutyTag, String currentDateNd_Time);

    @Query("select * from DAILY_ATTENDANCE where REGNO=:regNo  and datetime(:currentDateNd_Time) BETWEEN datetime(SHIFT_START_TIME) and datetime(datetime(SHIFT_END_TIME),'+5 hours')")
    DailyAttendanceDetail getRecord_after_add_hour_in_shift_end_time(String regNo, String currentDateNd_Time);

    @Query("select * from DAILY_ATTENDANCE where REGNO=:regNo  and  datetime(:dateTime) BETWEEN datetime(shift_start_time) and datetime(shift_end_time) order by date_modified desc limit 1")
    DailyAttendanceDetail getAttendanceDetailForToday(String regNo, String dateTime);

    @Query("select EXISTS (select * from DAILY_ATTENDANCE where REGNO=:regNo and DUTY_STATUS='DUTY_IN'and UNIT_CODE=:unitCode)")
    boolean checkDutyStatus(String regNo, String unitCode);
//    @Query("select EXISTS (select * from DAILY_ATTENDANCE where REGNO=:regNo and SHIFT_ID=:shiftID and UNIT_CODE=:unitCode)")
//    boolean checkBaseOnShift(String regNo, String shiftID,String currentDate);

    @Query("select * from DAILY_ATTENDANCE where REGNO=:regNo and DUTY_STATUS='DUTY_IN'and UNIT_CODE=:unitCode")
    LiveData<DailyAttendanceDetail> checkDutyStatusIN(String regNo, String unitCode);

    //    @Query("select * from daILY_ATTENDANCE where regno=:regNo and date(shift_start_time)=:date")
    @Query("select * from daILY_ATTENDANCE where regno=:regNo and date(ACT_START_TIME)=:date")
    List<DailyAttendanceDetail> getAttendanceDetailRecordPresent(String regNo, String date);

    //    @Query("SELECT *, (datetime(:currentDate)) as DT from DAILY_ATTENDANCE where (DUTY_STATUS=:dutyIN or ((datetime()) between datetime(SHIFT_START_TIME) AND datetime(datetime(SHIFT_END_TIME), '45 minute'))) and UNIT_CODE=:unitCode")
//    @Query("SELECT * from DAILY_ATTENDANCE where DUTY_STATUS=:dutyIN and UNIT_CODE=:unitCode and date(:currentDate)=date(SHIFT_END_TIME)")
//    @Query("SELECT * from DAILY_ATTENDANCE where  UNIT_CODE=:unitCode and date(:currentDate)=date(SHIFT_START_TIME)GROUP BY date(SHIFT_START_TIME),date(SHIFT_END_TIME)")
    @Query("SELECT da1.* from DAILY_ATTENDANCE da1 left JOIN DAILY_ATTENDANCE da2 on da1.SHIFT_ID = da2.SHIFT_ID where da1.UNIT_CODE=:unitCode \n" +
            "and date(:currentDate) = date(da1.SHIFT_START_TIME) or (datetime('now', 'localtime') BETWEEN datetime(da2.shift_start_time) and datetime(da2.shift_end_time, '9 hour'))\n" +
            "and da1.DUTY_STATUS = 'DUTY_OUT'\n" +
            "GROUP BY date(da1.SHIFT_START_TIME), date(da1.SHIFT_END_TIME) order by SHIFT_END_TIME DESC")
//    @Query("select  SHIFT_ID,ID,DUTY_STATUS  from  DAILY_ATTENDANCE where  DUTY_STATUS=:dutyIN and UNIT_CODE=:unitCode and SHIFT_START_DATE=:currentDate")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<DailyAttendanceDetail> get_Duty_OUT(String unitCode, String currentDate);

    @Query("select COUNT(DISTINCT REGNO) from DAILY_ATTENDANCE where SHIFT_ID=:shiftID and UNIT_CODE=:unitCode and datetime(:currentDate) BETWEEN datetime( SHIFT_START_TIME) and datetime(SHIFT_END_TIME,:dutyHours || ' hour')")
    String countAttendance_Shift_BASE(String shiftID, String unitCode, String currentDate, String dutyHours);

    @Query("SELECT COUNT(DISTINCT REGNO) from DAILY_ATTENDANCE where SHIFT_ID=:shiftID and UNIT_CODE=:unitCode and date(SHIFT_START_TIME)=:currentDate")
    String countAttendance_Shift_BASE(String shiftID, String unitCode, String currentDate);

    //    @Query("select COUNT(DISTINCT REGNO) from DAILY_ATTENDANCE where SHIFT_ID=:shiftID and UNIT_CODE=:unitCode and  datetime(:currentDate) between datetime(SHIFT_START_TIME) and datetime(shift_end_time) and DUTY_STATUS=:dutyStatus")
    @Query("SELECT COUNT(DISTINCT REGNO) from DAILY_ATTENDANCE where SHIFT_ID=:shiftID and UNIT_CODE=:unitCode and DUTY_STATUS=:dutyStatus group by SHIFT_ID, DATE(SHIFT_START_TIME)")
    String countAttendance_Shift_BASE1(String shiftID, String unitCode, String dutyStatus);

    @Query("SELECT SHIFT_ID,ID, SHIFT_STATUS,datetime(SHIFT_START_TIME),SHIFT_CLOSER_ID  from DAILY_ATTENDANCE where UNIT_CODE=:unitCode and date(SHIFT_START_TIME)=:date")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<DailyAttendanceDetail> getList_of_ShiftID(String unitCode, String date);

    @Query("SELECT distinct SHIFT_ID  from DAILY_ATTENDANCE where UNIT_CODE=:unitCode and SHIFT_STATUS=:shiftStatus and date(SHIFT_START_TIME)=:date")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Ignore
    @Transaction
    List<String> getList_of_pendingandCompleteShiftID(String unitCode, String shiftStatus, String date);

    @Query("SELECT * from DAILY_ATTENDANCE where REGNO=:regNo and date(SHIFT_START_TIME)=:currentdate and UNIT_CODE=:unitCode and SHIFT_ID=:shiftID and DUTY_STATUS=:DutyStatus Order by ID desc limit 1")
    DailyAttendanceDetail checkRegNo_Exist(String regNo, String currentdate, String unitCode, String shiftID, String DutyStatus);

    @Query("SELECT distinct emp_rank from DAILY_ATTENDANCE where date(SHIFT_START_TIME)=:currentdate and UNIT_CODE=:unitCode and SHIFT_ID=:shift_id and DUTY_STATUS=:duty_status")
    List<String> getDuty_Rank_List(String currentdate, String unitCode, String shift_id, String duty_status);

    @Query("SELECT * from DAILY_ATTENDANCE where  date(SHIFT_START_TIME)=:currentdate and UNIT_CODE=:unitCode and SHIFT_ID=:shift_id and Duty_status=:dutyStatus order by ID desc")
    List<DailyAttendanceDetail> getBulk_Daily_Duty_IN(String currentdate, String unitCode, String dutyStatus, String shift_id);

    @Query("SELECT COUNT(REGNO) from DAILY_ATTENDANCE where SHIFT_ID=:shiftID and UNIT_CODE=:unitCode and date(SHIFT_START_TIME)=:currentDate and DUTY_STATUS=:dutystatus")
    String countAll_Shift_Record(String shiftID, String unitCode, String currentDate, String dutystatus);

    /*@Query("Update DAILY_ATTENDANCE set FINAL_START_TIME=:punchIn, FINAL_END_TIME=:punchOut,ACT_END_TIME=:punchOut,  DUTY_STATUS=:dutyStatus, DATE_MODIFIED =:modifiedDate, FLAG=:flag,JSON=:json where REGNO=:regNo and UNIT_CODE=:unit_code and date(SHIFT_START_TIME)=:date")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    void update_dailyAttendance(String punchIn, String punchOut, String reason, String unit_code, String regNo, String date, String dutyStatus, String flag, String modifiedDate, String json);
*/
    @Query("SELECT ACT_END_TIME,FINAL_END_TIME,ID,SHIFT_ID  from DAILY_ATTENDANCE where UNIT_CODE=:unitCode and date(SHIFT_START_TIME)=:date and SHIFT_ID=:shiftId")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<DailyAttendanceDetail> getPunchOutTimeFromAttendance(String unitCode, String date, String shiftId);

    @Query("Update DAILY_ATTENDANCE set SHIFT_STATUS=:status,provideReason=:provideReasonId, FINAL_END_TIME=:final_end_Time, ACT_END_TIME=:actEndTime, DATE_MODIFIED =:modifiedDate, FLAG=:flag, JSON=:json, DUTY_STATUS=:dutyOut where SHIFT_ID=:shiftID and UNIT_CODE=:unit_code and date(SHIFT_START_TIME)=:date and REGNO=:regNo")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    void update_dailyAttendanceShiftStatus(String unit_code, String shiftID, String date, String status, String final_end_Time,
                                           String actEndTime, String regNo, String flag, String modifiedDate, String json, String dutyOut, String provideReasonId);


    @Query("SELECT * from DAILY_ATTENDANCE where REGNO=:regNo and date(SHIFT_START_TIME)=:currentdate and UNIT_CODE=:unitCode Order by ID desc limit 1")
    DailyAttendanceDetail check_Reg_for_Duty_hours(String regNo, String currentdate, String unitCode);

    @Query("SELECT ifnull(Cast ((MAX(JulianDay(ACT_END_TIME)) - JulianDay(MIN(ACT_START_TIME))) * 24 As Integer),0) as totalHours from daily_attendance where regNo=:regNO and :currentdate between date(SHIFT_end_TIME)and date(SHIFT_start_TIME) and UNIT_CODE=:unitCode\n")
    int Calculate_totalHour(String regNO, String currentdate, String unitCode);

    @Query("SELECT VOILATE from DAILY_ATTENDANCE whERE REGNO=:regNO and UNIT_CODE=:unitcode and SHIFT_ID=:shiftID  and DUTY_STATUS='DUTY_IN'and DUTY_STATUS=:dutystatus and date(SHIFT_START_TIME)=:currentDate")
    int checkVoilation_Exist(String regNO, String unitcode, String currentDate, String shiftID, String dutystatus);

    @Query("SELECT * from DAILY_ATTENDANCE where REGNO=:regNo and date(SHIFT_START_TIME)=:currentdate and UNIT_CODE=:unitCode and SHIFT_ID=:shiftID and DUTY_STATUS=:DutyStatus Order by ID desc limit 1")
    DailyAttendanceDetail checkRegNo_Exist1(String regNo, String currentdate, String unitCode, String shiftID, String DutyStatus);

//    @Query("select SHIFT_NAME from DAILY_ATTENDANCE where REGNO=:regNo Order by date(SHIFT_START_TIME) DESC LIMIT 1")
//    String getLastShiftForMarkedAttendance(String regNo);

    @Query("SELECT * from DAILY_ATTENDANCE where FLAG=:flag")
    public List<DailyAttendanceDetail> getListToSync(String flag);

    @Query("SELECT * from DAILY_ATTENDANCE where FLAG=:flag and REGNO=:regNO")
    public List<DailyAttendanceDetail> pendingSyncToServerList(String flag, String regNO);

    @Query("SELECT max(DATE_MODIFIED) FROM DAILY_ATTENDANCE")
    String maxModifiedDate();

    @Query("Update DAILY_ATTENDANCE set FLAG=:flag where ID=:id")
    void updateFlag(String id, String flag);

    //    @Query("select count(*) from Daily_attendance where UNIT_CODE=:unitCode  and SHIFT_ID=:shiftID and date(SHIFT_START_TIME)=:currentDate and DUTY_STATUS=:dutyStatus")
    @Query("SELECT count(da1.SHIFT_ID) from DAILY_ATTENDANCE da1 left JOIN DAILY_ATTENDANCE da2 on da1.id = da2.id where da1.UNIT_CODE=:unitCode and date(:currentDate) = date(da1.SHIFT_START_TIME) or (datetime('now', 'localtime') BETWEEN datetime(da2.shift_start_time) and datetime(da2.shift_end_time)) and da1.DUTY_STATUS = :dutyStatus and da1.shift_id=:shiftID GROUP BY da1.shift_id, date(da1.SHIFT_START_TIME), date(da1.SHIFT_END_TIME)\n")
    Integer checkRecordExist_forDuty_OUT(String unitCode, String shiftID, String currentDate, String dutyStatus);

    @Query("SELECT * from DAILY_ATTENDANCE where REGNO=:regNo  and DUTY_STATUS='DUTY_IN' Order by SHIFT_START_TIME desc limit 1")
    DailyAttendanceDetail checkRegnoForDutyOut(String regNo);

    @Query("SELECT DISTINCT SHIFT_STATUS  from DAILY_ATTENDANCE where UNIT_CODE=:unitCode and date(SHIFT_START_TIME)=:date and SHIFT_ID=:shiftId")
    String getShiftStatus(String unitCode, String date, String shiftId);

    // @Query("Update DAILY_ATTENDANCE set shiftStatus=:status  where SHIFT_ID=:shiftID and UNIT_CODE=:unit_code and date(SHIFT_START_TIME)=:date")
    @Update
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    void update_dailyAttendanceShiftStatuss(List<DailyAttendanceDetail> dailyAttendanceDetail);

    @Query("SELECT * from DAILY_ATTENDANCE where Unit_CODE=:unitCode and RegNo=:regNo and DUTY_STATUS='DUTY_IN'")
    List<DailyAttendanceDetail> regNoMultipleList(String unitCode, String regNo);

    @Query("Update DAILY_ATTENDANCE set DATE_MODIFIED=:currentDateTime, FLAG='1', JSON=:json,DUTY_STATUS=:dutyStatus,ACT_END_TIME=:punchEndTime where REGNO=:regNo and UNIT_CODE=:unitCode")
    void updateRegNoMultiple(String regNo, String unitCode, String punchEndTime, String dutyStatus, String json, String currentDateTime);

    @Query("Update DAILY_ATTENDANCE set SHIFT_STATUS=:status, FINAL_END_TIME=:final_end_time, ACT_END_TIME=:act_end_time, FINAL_START_TIME=:finalStartTime, DATE_MODIFIED =:modifiedDate, FLAG=:flag,DUTY_STATUS=:dutyStatus where SHIFT_ID=:shiftID and UNIT_CODE=:unit_code and date(SHIFT_START_TIME)=:date and REGNO=:regNo")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    void updateDailyAttendanceFinalEndTime(String unit_code, String shiftID, String date, String final_end_time, String act_end_time, String finalStartTime, String regNo, String flag, String modifiedDate, String dutyStatus, String status);

//    @Query("select SHM.SHIFT_ID,DTR.ID,DTR.REGNO,DTR.duty_post_id as dUTYPOSTID,DTR.duty_rank as DUTY_RANK,SHM.SHIFT_start_time as SHIFT_START_TIME,SHM.shift_end_time as SHIFT_END_TIME,SHM.ID as SHIFT_CLOSER_ID from Daily_trANSACTION_ROSTER DTR inner join Main_Roster MR on MR.ID=DTR .ROSTER_ID inner join Shift_closer_master SHM on SHM.shift_id=MR.shift_id where DTR.REGNO=:regNo and date(DTR.roster_date)=:currentDate group by MR.SHIFT_ID")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    DailyAttendanceDetail existRegNoInRoster(String regNo, String currentDate);

    @Query("SELECT ifnull(Cast ((MAX(JulianDay(ACT_END_TIME)) - JulianDay(MIN(ACT_START_TIME))) * 24 As Integer),0) as totalHours from daily_attendance where regNo=:regNo and date(SHIFT_START_TIME)=:attendanceDate and UNIT_CODE=:unitCode")
    String calculateDutyHourBaseOnRegNO(String unitCode, String regNo, String attendanceDate);

    @Query("SELECT date(ACT_START_TIME) from daily_attendance where REGNO=:regNo and date(ACT_START_TIME)=:date order by act_start_time desc limit 1")
    String getLeaveAndPresentDate(String regNo, String date);

    @Query("select flag ,date(ACT_START_TIME) as ACT_START_TIME ,count(regno) as EXTRA_DUTY from daILY_ATTENDANCE where REGNO=:regNo and date(ACT_START_TIME)=:date")
    Attendance_for_calender_model getAttendanceDetailOn_calender(String regNo, String date);

    //    @Query("select * from DAILY_ATTENDANCE where REGNO=:regNo and shift_id=:shift_id AND UNIT_CODE=:unitCode and datetime(:dateTime) BETWEEN datetime(shift_start_time,'-45 minutes') and datetime(shift_end_time) order by date_modified desc limit 1") // working just chnage for 5 hours add in shift end timr
    @Query("SELECT * from DAILY_ATTENDANCE where REGNO=:regNo and shift_id=:shift_id AND UNIT_CODE=:unitCode and datetime(:dateTime) BETWEEN datetime(shift_start_time,'-45 minutes') and datetime(datetime(SHIFT_END_TIME),'+5 hours')order by date_modified desc limit 1")
    LiveData<DailyAttendanceDetail> getAttendanceDetailForTodayWithShift_UnitBase(String regNo, String shift_id, String unitCode, String dateTime);

    @Query("SELECT * from DAILY_ATTENDANCE where SHIFT_ID=:shift_id and UNIT_CODE=:unitCode and  REGNO=:regNo and datetime(:date) between datetime(SHIFT_START_TIME) and datetime(shift_end_time) order by date_modified desc limit 1")
    DailyAttendanceDetail markAttendanceTodayWithShift_unitBase(String regNo, String shift_id, String unitCode, String date);

    @Query("SELECT DUTY_STATUS from DAILY_ATTENDANCE where REGNO=:regNo  and SHIFT_ID=:shiftId and UNIT_CODE=:unitCode and datetime(:currentDateTime) between datetime(SHIFT_START_TIME,'-45 minutes') and datetime(datetime(SHIFT_END_TIME),'+5 hours')")
    String checkSelfAttendanceToday(String regNo, String shiftId, String unitCode, String currentDateTime);

    @Query("select DUTY_STATUS from DAILY_ATTENDANCE where REGNO=:regNo  and SHIFT_ID=:shiftId and datetime(:currentDateTime) BETWEEN datetime(shift_start_time,'-45 minutes') and datetime(datetime(SHIFT_END_TIME),'+5 hours')")
    String checkInOtherMarkScreen(String regNo, String shiftId, String currentDateTime);

    @Query(" SELECT sum (time(ACT_END_TIME) - time(ACT_START_TIME)) FROM DAILY_ATTENDANCE where REGNO=:regno and DUTY_STATUS='DUTY_OUT'")
    String getTotalWorkingHour_By_RegNo(String regno);

    @Query("select * from DAILY_ATTENDANCE where REGNO=:regNo and date(:currentDate) BETWEEN date(SHIFT_START_TIME) and date(SHIFT_END_TIME) order by SHIFT_START_TIME desc limit 1\n")
    DailyAttendanceDetail checkAttendancePunchToday(String regNo, String currentDate);

    @Query("SELECT JSON from daily_attendance where REGNO=:regNo and date(ACT_START_TIME)=:date and FLAG='1' order by act_start_time desc limit 1")
    String getSelectedMonth_Json(String regNo, String date);
}




