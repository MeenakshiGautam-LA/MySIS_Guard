package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;


import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.Model.Leave_Detail_Model;
import com.sisindia.mysis.entity.LeaveDetailTransaction;

import java.util.List;

@Dao
public interface LeaveEmployeeDetail_Dao {

    @Query("select * from LEAVE_TRANSACTION_DETAILS")
    public List<LeaveDetailTransaction> getLeaveDetails();

    @Query("SELECT max(DATE_MODIFIED) FROM LEAVE_TRANSACTION_DETAILS")
    String maxModifiedDate();

    @Query("select * from LEAVE_TRANSACTION_DETAILS where ID=:ID")
    LeaveDetailTransaction getDetails(String ID);

    @Query("delete from LEAVE_TRANSACTION_DETAILS where id in (:idList)")
    void deleteAll(List<String> idList);

    @Update
    void updateLeaveEmployee(LeaveDetailTransaction task_model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLeaveEmployee(LeaveDetailTransaction leaveEmployeeDetails);

    /*@Query("select EDT.iD, EDT.EMP_NAME,EDT.PICTURE,EDT.REGNO, SI.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE EDT inner join SERVICE_INFO SI on EDT.RANK = SI.SERVICE_CODE  where EDT.UNIT_CODE=:unitcode")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<EmployeDetailsModel> getEmployeeLeaveDetails(String unitcode);
*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LeaveDetailTransaction leaveEmployeeDetails);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertList(List<LeaveDetailTransaction> leaveEmployeeDetails);

    @Query("delete from LEAVE_TRANSACTION_DETAILS where LEAVE_MASTER_ID=:leaveMasterId")
    void deleteTransactionBaseOnMaster_ID(String leaveMasterId);

    @Query("SELECT * FROM LEAVE_TRANSACTION_DETAILS WHERE REGNO =:regNo and date(LEAVE_DATE)=:date")
    LeaveDetailTransaction checkExist(String regNo, String date);

    @Query("select count(*) from LEAVE_TRANSACTION_DETAILS where LEAVE_MASTER_ID=:leaveMasterId")
    int countLeaveDays(String leaveMasterId);

    @Query("Update LEAVE_TRANSACTION_DETAILS set LEAVE_DATE=:leaveDate, LEAVE_TYPE_ID=:leaveReason, FLAG='1', JSON=:json where REGNO=:regNo and date(LEAVE_DATE)=:leaveDate")
    void updateRecord(String regNo, String leaveDate, String leaveReason, String json);

//    @Query("select EXISTS(select * from LEAVE_TRANSACTION_DETAILS where Date(LEAVE_DATE)=Date(:leaveDate) and REGNO=:regNo)")
    @Query("select EXISTS(select * from LEAVE_TRANSACTION_DETAILS LTD left join Leave_master LM on LM.id=LTD.leave_master_id where Date(LEAVE_DATE)=Date(:leaveDate) and LM.leave_status not in ('2')and LTD.REGNO=:regNo)\n")
    boolean checkRecordInDB(String leaveDate, String regNo);

    @Query("select EXISTS(select * from LEAVE_TRANSACTION_DETAILS)")
    boolean checkRecordExists();

    @Query("select * from LEAVE_TRANSACTION_DETAILS where date(LEAVE_DATE)=:leaveDate and REGNO=:regNo")
    LeaveDetailTransaction recordDateBase(String leaveDate, String regNo);

    @Query("select * from LEAVE_TRANSACTION_DETAILS where REGNO=:regNo order by leave_date")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<CalendarDayModel> getLeaveHistory(String regNo);

//    //    @Query("select LEAVE_TYPE_ID from LEAVE_TRANSACTION_DETAILS where REGNO=:regNo and date(LEAVE_DATE)=:date")
//    @Query("select LEAVE_TYPE_ID from LEAVE_TRANSACTION_DETAILS where REGNO=:regNo and date(LEAVE_DATE)=:date")
//    String getLeaveDate(String regNo, String date);

    @Query("select * from LEAVE_TRANSACTION_DETAILS where REGNO=:regNo  order by leave_date")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<CalendarDayModel> getTotalLeaveHistoryInMonth(String regNo);

//    @Query("select LM.Leave_Type, LM.id,LM.leave_status, LTD.leave_date,LM.leave_request_type from leave_masteR LM left join LEAVE_TRANSACTION_DETAILS LTD on LM.id=LTD.leave_master_id where leave_status not in('2') and date(LTD.LEAVE_DATE)=:date and LTD.REGNO=:regNo")
    @Query("select GLTL.color_code as LEAVE_COLOR_CODE,LM.Leave_Type,LM.flag as UN_SYNC_DATA, LM.id,LM.leave_status, LTD.leave_date,LM.leave_request_type from leave_masteR LM left join LEAVE_TRANSACTION_DETAILS LTD on LM.id=LTD.leave_master_id inner join get_leave_type_list GLTL on GLTL.Leave_id=LM.Leave_type where leave_status not in('2') and date(LTD.LEAVE_DATE)=:date and LTD.REGNO=:regNo and GLTL.leave_id=LM.Leave_Type")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    Leave_Detail_Model getLeaveRecord(String regNo, String date);


   /* @Query("select * from leAVE_EMPLOYEE_DETAILS where REGNO=:regNo and date(leave_date) between :month and DATE(:month, 'start of month', '+1 month', '-1 day') order by leave_date")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<CalendarDayModel> getLeaveDateOfMonth(String regNo, String month);
*/
  /*  @Query("select * from LEAVE_EMPLOYEE_DETAILS where date(leave_date)=:date and REGNO=:regno")
    LeaveEmployeeDetails getleaveDetailsByDate(String date, String regno);

    @Query("Update LEAVE_EMPLOYEE_DETAILS set  LEAVE_DESCRIPTION=:leaveDescription, JSON=:json, FLAG='1' where REGNO=:regNo and date(LEAVE_DATE)=:date")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    void updateLeaveReason(String regNo, String json, String leaveDescription, String date);

    @Query("delete FROM LEAVE_EMPLOYEE_DETAILS where REGNO=:regno and date(LEAVE_DATE)=:date")
    void deleteLeaveReason(String regno, String date);
*/
   /* @Query("seleCT LED.LEAVE_TYPE_ID, LED.LEAVE_DATE,LEM.LEAVE_CODE from leAVE_EMPLOYEE_DETAILS LED inner join LEAVE_EMPLOYEE_MASTER LEM on LED.LEAVE_TYPE_ID =LEM.ID where LED.LEAVE_DATE between :startDate and :endDate and REGNO=:regno")
    List<LeaveReportModel> getLeaveReport(String startDate, String endDate, String regno);
*/
   /* @Query("seleCT LED.LEAVE_TYPE_ID, LED.LEAVE_DATE,LEM.LEAVE_CODE from leAVE_EMPLOYEE_DETAILS LED inner join LEAVE_EMPLOYEE_MASTER LEM on LED.LEAVE_TYPE_ID =LEM.ID where date(LED.LEAVE_DATE)=:date and REGNO=:regno")
    LeaveReportModel getLeaveReport1(String date, String regno);
    */
 /*   @Query("seleCT EDT.REGNO,EDT.EMP_NAME,EDT.PICTURE,SI.SYMBOL from  EMPLOYEE_DETAIL_TABLE EDT  inner join SERVICE_INFO SI on SI.SERVICE_CODE= EDT.RANK ")
    List<Roaster_Report_Model> getRoasterReport();*/

   /* @Query("seleCT LED.LEAVE_TYPE_ID,LED.REGNO,LED.LEAVE_DATE from leAVE_EMPLOYEE_DETAILS LED  where date(LED.LEAVE_DATE) between :startDate and :endDate")
    List<RoasterReportContanctModel> getRoasterReport(String startDate, String endDate);
*/
   /* @Query("seleCT EDT.REGNO,EDT.EMP_NAME,EDT.PICTURE,SI.SYMBOL from  EMPLOYEE_DETAIL_TABLE EDT  inner join SERVICE_INFO SI on SI.SERVICE_CODE= EDT.RANK where UNIT_CODE=:unitCode")
    List<DutyHoursReportEmployeeModel> getEmployeeList(String unitCode);
*/
   /* @Query("seleCT * from leave_employee_master where ID=:id")
    LeaveEmployeeMaster getLeaveTypeId(String id);
*/
   /* @Query("seleCT EDT.REGNO,EDT.EMP_NAME,EDT.PICTURE,SI.SYMBOL,MA.MEAL_COUNT,MA.DAYS_PRESENT from  EMPLOYEE_DETAIL_TABLE EDT  inner join SERVICE_INFO SI on SI.SERVICE_CODE= EDT.RANK inner join MESS_ATTENDANCE MA on EDT.REGNO= MA.REGNO and EDT.UNIT_CODE=MA.UNIT_CODE where EDT.UNIT_CODE=:unitCode and MA.MESS_MONTH=:month and MA.MESS_YEAR=:year")
    List<SaveReportModel> getMessReport(String unitCode, String month, String year);
*/

    @Query("select * from LEAVE_TRANSACTION_DETAILS where FLAG=:flag")
    public List<LeaveDetailTransaction> getListToSync(String flag);

    @Query("select * from LEAVE_TRANSACTION_DETAILS where FLAG=:flag and LEAVE_DATE=:date")
    public List<LeaveDetailTransaction> getListToSync_on_SpecificDate(String flag,String date);

    @Query("Update LEAVE_TRANSACTION_DETAILS set FLAG=:flag where ID=:id")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    void updateFlag(String id, String flag);
}
