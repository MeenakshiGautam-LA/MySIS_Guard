package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sisindia.mysis.entity.ServiceInfoDetail;

import java.util.List;


@Dao
public interface ServiceInfoDetail_DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ServiceInfoDetail> serviceInfoDetailList);

    @Query("select * from SERVICE_INFO where SERVICE_CODE=:serviceCode")
    ServiceInfoDetail gertServiceDetail(String serviceCode);

    @Query("delete from SERVICE_INFO where id in (:idList)")
    void deleteAll(List<String> idList);

    @Update
    void update(ServiceInfoDetail task_model);

    @Delete
    void delete(ServiceInfoDetail task_model);

    //    @Query("select * from " + Constants.SERVICEINFO + "")
//    List<ServiceInfoDetail> getDetails();
//
//    //    @Query("DELETE FROM " + "CUSTOMER_FAVOURITE_TABLE" + " WHERE " + "USERNAME" + " = '" + end_address + "'")
////    int updateTour(long tid, String end_address);
//    @Query("select EMPLOYEE_DETAIL_TABLE.ID,EMPLOYEE_DETAIL_TABLE.REGNO from EMPLOYEE_DETAIL_TABLE")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<EmployeDetailsModel> getEmployeeList();
//
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE not in (select unit_code from get_site_detail except select :unitcode unit_code )")
//    List<EmployeDetailsModel> getDetail(String unitcode);
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE and EMPLOYEE_DETAIL_TABLE.picture is null where EMPLOYEE_DETAIL_TABLE.UNIT_CODE not in (select unit_code from get_site_detail except select :unitcode unit_code )")
//    List<EmployeDetailsModel> getDetailPictureNull(String unitcode);
//
//    @Query("Select M.ID,M.UNIT_CODE, M.SERVICE_CODE, D.SYMBOL as TEMP_SYMBOL, sum(M.STRENGTH) as AUTH_STRENGTH, ifnull(E.STRENGTH,0) As EMP_STRENGTH from SITE_STRENGTH M inner join SERVICE_INFO D on M.SERVICE_CODE=D.SERVICE_CODE Left Outer Join (Select UNIT_CODE, RANK as SERVICE_CODE, Count(DISTINCT REGNO) as STRENGTH from EMPLOYEE_DETAIL_TABLE Group By UNIT_CODE, RANK) E on E.UNIT_CODE=M.UNIT_CODE and E.SERVICE_CODE=M.SERVICE_CODE Where M.UNIT_CODE=:unit_code Group by M.UNIT_CODE, M.SERVICE_CODE, D.SYMBOL Order By M.SERVICE_CODE")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<EmployeDetailsModel> getUnitStrengths(String unit_code);
//
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE=:unitcode order by EMPLOYEE_DETAIL_TABLE.EMP_NAME")
//    List<EmployeDetailsModel> getEmployeeDetailByAsendingSort(String unitcode);
//
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE=:unitcode order by EMPLOYEE_DETAIL_TABLE.EMP_NAME desc")
//    List<EmployeDetailsModel> getEmployeeDetailByDescendingSort(String unitcode);
//
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE=:unitcode and EMPLOYEE_DETAIL_TABLE.REGNO=:regNO")
//    EmployeDetailsModel getDetail_roster(String unitcode, String regNO);
//
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE=:unitcode and EMPLOYEE_DETAIL_TABLE.REGNO=:regNO")
//    EmployeDetailsModel getShiftCloserEmpDetail(String unitcode, String regNO);
//
//    @Query("select ed.REGNO,ed.iD, ed.EMP_NAME,ed.PICTURE,s.symbol as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE ed left join DAILY_ATTENDANCE da on ed.UNIT_CODE=da.UNIT_CODE and ed.REGNO = da.REGNO and  date(da.SHIFT_START_TIME) =:date inner join SERVICE_INFO s on ed.rank=s.service_code where ed.UNIT_CODE=:unitcode and da.SHIFT_ID is null")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    List<EmployeDetailsModel> getDayCloserEmpDetail1(String unitcode, String date);
//
//   /* @Query("select DA.ID,ED.isSelected, ED.REGNO,ED.EMP_NAME,ED.RANK,ED.UNIT_CODE, SI.SYMBOL as TEMP_SYMBOL,DA.DUTY_RANK,DA.dUTYPOSTNAME,DA.SHIFT_START_TIME,DA.provideReason,DA.SHIFT_END_TIME,DA.ACT_START_TIME,DA.ACT_END_TIME,DA.FINAL_START_TIME,DA.FINAL_END_TIME,ED.PICTURE,DA.SHIFT_START_TIME,DA.SHIFT_END_TIME,DA.EMP_RANK,DA.dUTYPOSTID from EMPLOYEE_DETAIL_TABLE ED inner join SERVICE_INFO SI on ED.RANK = SI.SERVICE_CODE inner join DAILY_ATTENDANCE DA on DA.UNIT_CODE = ED.UNIT_CODE and ED.REGNO= DA.REGNO and  date(SHIFT_START_TIME)=:date where ED.UNIT_CODE=:unitCode and SHIFT_ID=:shiftId group by DA.REGNO")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Ignore
//    List<ShiftCloserModal> getShiftCloserDeatil(String unitCode, String date, String shiftId);*/
//
//   @Query("select DA.ID,ED.isSelected, ED.REGNO,ED.EMP_NAME,ED.RANK,ED.UNIT_CODE, SI.SYMBOL as TEMP_SYMBOL,DA.DUTY_RANK,UPD.POST_NAME as dutyPostName,DA.SHIFT_START_TIME,DA.provideReason, MIN(DA.ACT_START_TIME) as ACT_START_TIME, MAX(DA.ACT_END_TIME) as ACT_END_TIME,DA.FINAL_START_TIME,DA.FINAL_END_TIME,ED.PICTURE,DA.SHIFT_START_TIME,DA.SHIFT_END_TIME,DA.EMP_RANK,DA.dUTYPOSTID as DUTY_POST_ID,ifnull(Cast ((MAX(JulianDay(ACT_END_TIME)) - JulianDay(MIN(ACT_START_TIME))) * 24 As Integer),0) as dutyHours from EMPLOYEE_DETAIL_TABLE ED inner join SERVICE_INFO SI on ED.RANK = SI.SERVICE_CODE inner join DAILY_ATTENDANCE DA on DA.UNIT_CODE = ED.UNIT_CODE and ED.REGNO= DA.REGNO and date(SHIFT_START_TIME)=:date inner join UnitDuty_Post_Detail UPD on DA.dUTYPOSTID = UPD.ID where ED.UNIT_CODE=:unitCode and SHIFT_ID=:shiftId group by DA.REGNO order by dutyHours, UPD.POST_NAME, SI.SYMBOL, ED.EMP_NAME")
//   @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//   @Ignore
//   List<ShiftCloserModal> getShiftCloserDeatil(String unitCode, String date, String shiftId);
//
//    @Query("select DA.ID,ED.isSelected, ED.REGNO,ED.EMP_NAME,ED.RANK,ED.UNIT_CODE, SI.SYMBOL as TEMP_SYMBOL,DA.DUTY_RANK,UPD.POST_NAME as dutyPostName,DA.SHIFT_START_TIME,DA.provideReason, MIN(DA.ACT_START_TIME) as ACT_START_TIME, MAX(DA.ACT_END_TIME) as ACT_END_TIME,DA.FINAL_START_TIME,DA.FINAL_END_TIME,ED.PICTURE,DA.SHIFT_START_TIME,DA.SHIFT_END_TIME,DA.EMP_RANK,DA.dUTYPOSTID as DUTY_POST_ID,ifnull(Cast ((JulianDay(ACT_END_TIME) - JulianDay(ACT_START_TIME)) * 24 As Integer),0) as dutyHours from EMPLOYEE_DETAIL_TABLE ED inner join SERVICE_INFO SI on ED.RANK = SI.SERVICE_CODE inner join DAILY_ATTENDANCE DA on DA.UNIT_CODE = ED.UNIT_CODE and ED.REGNO= DA.REGNO and date(SHIFT_START_TIME)=:date inner join UnitDuty_Post_Detail UPD on DA.dUTYPOSTID = UPD.ID where ED.UNIT_CODE=:unitCode and SHIFT_ID=:shiftId group by DA.REGNO order by dutyHours  desc")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Ignore
//    List<ShiftCloserModal> getShiftCloserDeatilInDescendingOrder(String unitCode, String date, String shiftId);
//
//
//    @Query("select DISTINCT SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE=:unitcode")
//    List<String> getEmployeeRankSymbol(String unitcode);
//
    @Query("SELECT SYMBOL from SERVICE_INFO where SERVICE_CODE=:serviceCode")
    String getSymbol(String serviceCode);

    @Query("SELECT SERVICE_NAME from SERVICE_INFO where SERVICE_CODE=:serviceCode")
    String getSymbolFullName(String serviceCode);

    @Query("SELECT max(DATE_MODIFIED) FROM SERVICE_INFO")
    String maxModifiedDate();
//
//    @Query("select SYMBOL from Service_info where SErvice_CODE in (select Service_code from Site_strength where unit_code=:unitcode)")
//    List<String> getServiceCodeNameList(String unitcode);
//
//    @Query("select SERVICE_CODE from Service_info where SErvice_CODE in (select Service_code from Site_strength where unit_code=:unitcode)")
//    List<String> getServiceCode_idList(String unitcode);
//
//    @Query("SELECT max(DATE_MODIFIED) FROM " + Constants.SERVICEINFO)
//    String maxModifiedDate();
//
//
//    @Query("select count(EMP_NAME) from EMPLOYEE_DETAIL_TABLE where UNIT_CODE =:unitCode and IsBankDetailUpdated =:bankDetailStatus")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    String isBankDetailsUpdated(String unitCode, String bankDetailStatus);
//
//    @Query("select count(EMP_NAME) from EMPLOYEE_DETAIL_TABLE where UNIT_CODE =:unitCode and IsUanNoUpdated =:uanNumberStatus")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    String isUanNumberUpdated(String unitCode, String uanNumberStatus);
//
//    @Query("select count(EMP_NAME) from EMPLOYEE_DETAIL_TABLE where  UNIT_CODE =:unitCode and date(UniformKitExpiryDate) < date(:date, '3 month')")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    String countUniformKitExpiring(String unitCode, String date);
//
//    @Query("select count(EMP_NAME) from EMPLOYEE_DETAIL_TABLE where  UNIT_CODE =:unitCode and date(GunLicenceExpiryDate) < date(:date, '3 month')")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    String countGunManExpiring(String unitCode, String date);
//
//    @Query("select count(EMP_NAME) from EMPLOYEE_DETAIL_TABLE where  UNIT_CODE =:unitCode and date(DLExpiryDate) < date(:date, '3 month')")
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    String countDriverLincesceExpiring(String unitCode, String date);
//
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE=:unitcode and date(UniformKitExpiryDate) < date(:date, '3 month')")
//    List<EmployeDetailsModel> getEmployeeDetailFiletrByUniformKit(String unitcode, String date);
//
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE=:unitcode and date(GunLicenceExpiryDate) < date(:date, '3 month')")
//    List<EmployeDetailsModel> getEmployeeDetailFiletrByGunMan(String unitcode, String date);
//
//    @Query("select EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE where EMPLOYEE_DETAIL_TABLE.UNIT_CODE=:unitcode and date(DLExpiryDate) < date(:date, '3 month')")
//    List<EmployeDetailsModel> getEmployeeDetailFiletrByDriverLinceces(String unitcode, String date);
//
//    @Query("SELECT EMPLOYEE_DETAIL_TABLE.*, SERVICE_INFO.SYMBOL as TEMP_SYMBOL from EMPLOYEE_DETAIL_TABLE inner join SERVICE_INFO on EMPLOYEE_DETAIL_TABLE.RANK = SERVICE_INFO.SERVICE_CODE WHERE UNIT_CODE not in (:unitCodeArray)")
//    List<EmployeDetailsModel> getOtherUnitEmployee(List<String> unitCodeArray);
//
//
//    @Query("select SITE_NAME from " + Constants.GETSITEDETAILS + " where UNIT_CODE=:unitCode" )
//    String getUnitName(String unitCode);
//
//    @Query("select POST_NAME from UnitDuty_Post_Detail where ID =:postId")
//    String getPostName(String postId);


}



