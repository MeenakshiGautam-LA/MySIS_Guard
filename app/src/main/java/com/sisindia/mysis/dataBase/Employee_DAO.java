package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sisindia.mysis.utils.Constants;

import java.util.List;

@Dao
public interface Employee_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<EmployeDetailsModel> employeDetailsModels);

    @Update
    void update(EmployeDetailsModel task_model);

    @Delete
    void delete(EmployeDetailsModel task_model);

    @Query("select * from EMPLOYEE_DETAIL_TABLE Where REGNO=:regNo and UNIT_CODE=:unitCode")
    EmployeDetailsModel checkRegNO(String regNo, String unitCode);

    @Query("delete from " + Constants.EMPLOYEEDETAILS_TABLE + " where id in (:idList)")
    void deleteAll(List<String> idList);

    @Query("select * from " + Constants.EMPLOYEEDETAILS_TABLE + "")
    List<EmployeDetailsModel> getDetails();

    @Query("select * from EMPLOYEE_DETAIL_TABLE where UNIT_CODE=:unitCode ")
    List<EmployeDetailsModel> getAllList(String unitCode);

    //
//    //    @Query("select * from EMPLOYEE_DETAIL_TABLE where UNIT_CODE=:unitCode and RANK=:dutyRank")
//    @Query("select  s.* from EMPLOYEE_DETAIL_TABLE s where s.REGNO not in (select  REGNO from DAILY_TRANSACTION_ROSTER where ROSTER_DATE=:date) and s.Unit_Code=:unitCode and s.rank=:dutyRank")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<Employee_detail_Query_Model> getAllList_base_serviceCode(String unitCode, String dutyRank, String date);
//
//    @Query("select * from EMPLOYEE_DETAIL_TABLE where RANK=:rank and UNIT_CODE=:unitCode ")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<Employee_detail_Query_Model> getEmployeeOnRankBase(String rank, String unitCode);
//
//    @Query("select REGNO from " + Constants.EMPLOYEEDETAILS_TABLE + "")
//    String[] getAllEmpRegNo();
//
    @Query("Update EMPLOYEE_DETAIL_TABLE set PICTURE=:end_address Where REGNO=:regNo and UNIT_CODE=:unitCode")
    void updateImage(String regNo, byte[] end_address, String unitCode);

//    @Query("select * from EMPLOYEE_DETAIL_TABLE Where REGNO=:regNo and UNIT_CODE=:unitCode")
//    EmployeDetailsModel checkRegNO(String regNo, String unitCode);
//
//    @Query("select * from EMPLOYEE_DETAIL_TABLE Where REGNO=:regNo and UNIT_CODE=:unitCode")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    Employee_detail_Query_Model checkRegNO1(String regNo, String unitCode);
//
//    @Query("SELECT COUNT(REGNO) FROM EMPLOYEE_DETAIL_TABLE")
//    int countList();
//
//    @Query("select s.SYMBOL from SERVICE_INFO s where SERVICE_CODE=:rank")
//    String employee_rank(String rank);
//
//    @Query("select s.SERVICE_NAME from SERVICE_INFO s where SERVICE_CODE=:rank")
//    String employee_serviceName_when_symbol_NULL(String rank);
//
//    @Query("select  s.SERVICE_NAME from SERVICE_INFO s where SERVICE_CODE=:dutyrank ")
//    String get_DUTY_rank(String dutyrank);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insert_newRecord(EmployeDetailsModel employeDetailsModels);
//
//    @Query("Update EMPLOYEE_DETAIL_TABLE set MOBILE=:mobile_number,FLAG='1',JSON=:json where REGNO=:regno")
//    void update_mobile(String mobile_number, String regno, String json);
//
//    @Query("Update EMPLOYEE_DETAIL_TABLE set EMAIL=:email,FLAG='1',JSON=:json where REGNO=:regno")
//    void update_email(String email, String regno, String json);
//
////    select s.REGNO from EMPLOYEE_DETAIL_TABLE s inner join DAILY_TRANSACTION_ROSTER d on s.REGNO!=d.REGNO  Where s.Unit_Code='GAR-UNT033212' and s.rank='SER000001'
//
//    //@Query("SELECT DAILY_ATTENDANCE.ID,DAILY_ATTENDANCE.UNIT_CODE, date(DAILY_ATTENDANCE.SHIFT_START_TIME) as SHIFT_START_TIME,  time(DAILY_ATTENDANCE.FINAL_START_TIME) as FINAL_START_TIME, time(DAILY_ATTENDANCE.FINAL_END_TIME) as FINAL_END_TIME from DAILY_ATTENDANCE WHERE REGNO=:regNo and date(DAILY_ATTENDANCE.SHIFT_START_TIME) between date(:startDate) and date(:endDate) order by  date(DAILY_ATTENDANCE.SHIFT_START_TIME)")
//    @Query("SELECT DA.ID,DA.UNIT_CODE, date(DA.SHIFT_START_TIME) as SHIFT_START_TIME, MIN(time(DA.ACT_START_TIME)) as FINAL_START_TIME, MAX(time(DA.ACT_END_TIME)) as FINAL_END_TIME,ifnull(Cast ((MAX(JulianDay(ACT_END_TIME)) - JulianDay(MIN(ACT_START_TIME))) * 24 As Integer),0) as dutyHours from DAILY_ATTENDANCE DA WHERE REGNO=:regNo and date(DA.SHIFT_START_TIME) between date(:startDate) and date(:endDate) group by date(DA.SHIFT_START_TIME) order by date(DA.SHIFT_START_TIME)")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<DailyAttendanceQueryModel> getWorkHours(String startDate, String endDate, String regNo);
//    //List<DailyAttendanceDetail> getWorkHours(String startDate, String endDate, String regNo);

   /* @Query("SELECT DAILY_ATTENDANCE.ID,DAILY_ATTENDANCE.SHIFT_START_TIME, DAILY_ATTENDANCE.ACT_END_TIME from DAILY_ATTENDANCE WHERE UNIT_CODE=:unitCode and REGNO=:regNo and :month between DAILY_ATTENDANCE.SHIFT_START_DATE and DAILY_ATTENDANCE.sHIFTENDDATE")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<DailyAttendanceDetail> getWorkHours(String month, String unitCode, String regNo);*/

    @Query("SELECT max(DATE_MODIFIED) FROM " + Constants.EMPLOYEEDETAILS_TABLE)
    String maxModifiedDate();

//    @Query("select * from EMPLOYEE_DETAIL_TABLE where unit_code not in (select unit_code from get_site_detail except select :unitCode unit_code )\n")
//    List<EmployeDetailsModel> getEmployeeWithOtherUnit(String unitCode);


    @Query("update EMPLOYEE_DETAIL_TABLE set JSON=:json,FLAG='1',PICTURE=:picture where REGNO=:regNo")
    void updateEmployeeJson(String json, String regNo, byte[] picture);

    @Query("select * from EMPLOYEE_DETAIL_TABLE where FLAG=:flag")
    public List<EmployeDetailsModel> getListToSync(String flag);

    @Query("Update EMPLOYEE_DETAIL_TABLE set FLAG=:flag where ID=:id")
    void updateFlag(String id, String flag);

    @Query("select * from EMPLOYEE_DETAIL_TABLE where ID=:ID")
    EmployeDetailsModel getDetails(String ID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleRecord(EmployeDetailsModel employeDetailsModels);

    @Query("SELECT * FROM EMPLOYEE_DETAIL_TABLE where REGNO=:regNo")
    EmployeDetailsModel getEmployee(String regNo);

    @Query("select exists(select isactive from emploYEE_DETAIL_TABLE where isactive=1 and REGNO=:regNo)")
    boolean checkEmployeeIs_Active(String regNo);
}


